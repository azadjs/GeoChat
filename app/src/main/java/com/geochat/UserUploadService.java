package com.geochat;



import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserUploadService {
    @Multipart
    @POST("register")
    Call<ResponseBody> UserSend(
            @Part("name") String name,
            @Part("birthdate") String birthdate,
            @Part("phone_number") String phone_number,
            @Part("gender") String gender,
            @Part("device_id") String device_id,
            @Part MultipartBody.Part image);

}
