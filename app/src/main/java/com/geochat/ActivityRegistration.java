package com.geochat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.geochat.User;

import com.google.gson.Gson;
import com.meg7.widget.CustomShapeImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityRegistration extends AppCompatActivity {
    private CustomShapeImageView customShapeImageView;
    Button buttonGallery ,login_button;
    private File file;
    private Uri uri;
    EditText registration_name;
    private byte[] bitmapdata;
    Intent GalIntent, CropIntent ;
    public  static final int RequestPermissionCode  = 1 ;
    int width, height;
    RadioButton male;
    RadioButton female;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        customShapeImageView = (CustomShapeImageView) findViewById(R.id.profile_photo);
        buttonGallery = (Button)findViewById(R.id.button1);
        registration_name = (EditText)findViewById(R.id.registration_name);
        EnableRuntimePermission();
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { GetImageFromGallery(); }
        });
        mDisplayDate = (TextView) findViewById(R.id.registration_date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ActivityRegistration.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                User.setBirthdate(date);
                mDisplayDate.setText(date);
            }
        };
        getID();
        navigateToMainActivity();
    }

    public void GetImageFromGallery(){

        GalIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            ImageCropFunction();

        }
        else if (requestCode == 2) {

            if (data != null) {

                uri = data.getData();

                ImageCropFunction();

            }
        }
        else if (requestCode == 1){
            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bitmapdata = bos.toByteArray();

                customShapeImageView.setImageBitmap(bitmap);
            }
        }
    }
//RETROFIT
public void sendUser() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://geochat.a2hosted.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserUploadService userApi = retrofit.create(UserUploadService.class);
    final String base64String = Base64.encodeToString(bitmapdata,Base64.URL_SAFE);
    RequestBody photo = RequestBody.create(MediaType.parse("text/plain"), base64String);
    RequestBody name = RequestBody.create(MediaType.parse("text/plain"),User.getName());
    RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"),User.getPhone_number());
    RequestBody birthdate = RequestBody.create(MediaType.parse("text/plain"),User.getBirthdate());
    RequestBody gender = RequestBody.create(MediaType.parse("text/plain"),User.getGender());
    RequestBody device_id = RequestBody.create(MediaType.parse("text/plain"),User.getDevice_id());
    Call<ResponseBody> call = userApi.userSend(name,birthdate,phone_number,gender,device_id,photo);
    call.enqueue(new Callback<ResponseBody> () {
        @Override
        public void onResponse (Call<ResponseBody> call, Response<ResponseBody> response){
            ResponseBody body = response.body();
            Log.v("RETROFIT",new Gson().toJson(response));
            if(bitmapdata==null){
                Log.v("BITMAP NULL EXCEPTION",bitmapdata.toString());
            }
            Log.v("Name",User.getName());
            Log.v("Phone",User.getPhone_number());
            Log.v("Birth",User.getBirthdate());
            Log.v("Gender",User.getGender());
            Log.v("Device",User.getDevice_id());
            Log.v("PHOTO",base64String);
        }
        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.v("RETROFIT ERROR",t.getMessage());
        }
    });
}
    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/jpeg");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 1000);
            CropIntent.putExtra("outputY", 1000);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityRegistration.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(ActivityRegistration.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ActivityRegistration.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ActivityRegistration.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ActivityRegistration.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                   User.setGender("m");
                    break;
            case R.id.radio_female:
                if (checked)
                    User.setGender("f");
                    break;
        }
    }

    public void getID(){
        String str = Build.MANUFACTURER + " " + Build.MODEL;
        User.setDevice_id(str);
    }


    public void navigateToMainActivity(){
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registration_name.getText().toString();
                User.setName(name);
                if (registration_name.getText().toString().trim().equalsIgnoreCase("")) {
                    registration_name.setError("This field can not be blank");
                }else {
                    sendUser();
                    startActivity(new Intent(ActivityRegistration.this,MainActivity.class));
                    finish();
                }

            }
        });

    }

}
