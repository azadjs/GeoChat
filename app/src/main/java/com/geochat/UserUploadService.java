package com.geochat;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserUploadService {
    @Multipart
    @POST("register")
    Call<ResponseBody> userSend(
            @Part("name") RequestBody name,
            @Part("birthdate") RequestBody birthdate,
            @Part("phone_number") RequestBody phone_number,
            @Part("gender") RequestBody gender,
            @Part("device_id") RequestBody device_id,
            @Part("photo") RequestBody photo);

}
