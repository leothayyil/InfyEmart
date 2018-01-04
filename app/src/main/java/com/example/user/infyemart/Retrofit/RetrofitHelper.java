package com.example.user.infyemart.Retrofit;

import com.example.user.infyemart.AccountActivity;
import com.example.user.infyemart.AddressFieldsActivity;
import com.example.user.infyemart.CartActivity;
import com.example.user.infyemart.LoginActivity;
import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.MainProductsActivity;
import com.example.user.infyemart.PurchaseActivity;
import com.example.user.infyemart.SubCategoey_Activity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 29-12-2017.
 */

public class RetrofitHelper {

    private static API apIs;





    public RetrofitHelper(LoginActivity loginActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(MainActivity mainActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(SubCategoey_Activity subCategoey_activity) {
        initResstAdapter();
    }

    public RetrofitHelper(MainProductsActivity mainProductsActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(AccountActivity accountActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(CartActivity cartActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(PurchaseActivity purchaseActivity) {
        initResstAdapter();
    }

    public RetrofitHelper(AddressFieldsActivity addressFieldsActivity) {
        initResstAdapter();
    }


    public static API getApIs() {
        return apIs;
    }

    public static void setApIs(API apIs) {
        RetrofitHelper.apIs = apIs;
    }

    private void initResstAdapter(){

        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("http://infyemart.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        setApIs(retrofit.create(API.class));
    }

    }