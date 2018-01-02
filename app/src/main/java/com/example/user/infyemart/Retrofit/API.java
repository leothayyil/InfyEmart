package com.example.user.infyemart.Retrofit;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> login(@Field("action") String action,@Field("user_name")
            String userName,@Field("password") String password);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> categoryMain(@Field("action") String action);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> bannerMain(@Field("action") String action);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> sub_Category(@Field("action") String action,@Field("category")String categoryId);
}
