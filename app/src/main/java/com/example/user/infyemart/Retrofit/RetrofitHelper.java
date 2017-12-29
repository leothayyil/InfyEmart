package com.example.user.infyemart.Retrofit;

import com.example.user.infyemart.LoginActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 29-12-2017.
 */

public class RetrofitHelper {

    private static API apIs;

    public RetrofitHelper(LoginActivity loginActivity) {
        initRestAdapter();
    }


    public static API getApIs() {
        return apIs;
    }

    public static void setApIs(API apIs) {
        RetrofitHelper.apIs = apIs;
    }



    private void initRestAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://infyemart.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        setApIs();
    }


    }