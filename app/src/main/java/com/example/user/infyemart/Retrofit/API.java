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

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> product_listing(@Field("action") String action,@Field("category")String categoryId,@Field("sub_category")String sub_categoryId);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> myAccount(@Field("action") String action,@Field("user_id")String user_id);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> cart(@Field("action") String action,@Field("cart_id")String cart_id);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> delivery_details(@Field("action") String action,@Field("user_id")String user_id);


    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> addDelivery_details(@Field("action") String action,@Field("user_id")String user_id,@Field("name")String name,
                                          @Field("email")String email,@Field("address")String address,@Field("district")String district,
                                          @Field("city")String city,@Field("landmark")String landmark,@Field("pincode")String pincode);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> district(@Field("action") String action);

    @FormUrlEncoded
    @POST("api/android-api.php")
    Call<JsonElement> getPlace(@Field("action") String action,@Field("district")String district);
}
