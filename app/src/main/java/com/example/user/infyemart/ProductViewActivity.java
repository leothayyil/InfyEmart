package com.example.user.infyemart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewActivity extends AppCompatActivity {

    String TAG="logg";
    String productId;
    String action="product_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Bundle extras=getIntent().getExtras();
        if (extras !=null){
            productId=extras.getString("product_id");

            AsyncProductView async=new AsyncProductView();
            async.execute();
        }

    }

    private class AsyncProductView extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {

            productDetails();
            return null;
        }
    }

    private void productDetails() {
        new RetrofitHelper(ProductViewActivity.this).getApIs().product_details(action,productId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray  jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String product_name=jsonObject.getString("product_name");
                                String brand=jsonObject.getString("brand");
                                String image=jsonObject.getString("image");

                                JSONArray jsonArray1=jsonObject.getJSONArray("variant");
                                for (int i1=0;i<jsonArray1.length();i++){
                                    JSONObject jsonObject1=jsonArray1.getJSONObject(i1);
                                    String Quantity=jsonObject1.getString("Quantity");
                                    String offer=jsonObject1.getString("offer");
                                    String original_price=jsonObject1.getString("original_price");
                                    String margin_price=jsonObject1.getString("margin_price");
                                }



                            }

                        } catch (JSONException e) {


                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }
}