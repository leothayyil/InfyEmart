package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Pojo.Pojo_Delivery_Address;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressFieldsActivity extends AppCompatActivity {

    String action="delivery_details";
    String userId="4";
    TextView namee,emaill,addresss,districtt,cityy,landmarkk,pincodee;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    TextView addNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_fields);
        addNew=findViewById(R.id.addNewAddressId);
        Toolbar toolbar=findViewById(R.id.toolbar_addressAdd);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTit.setText("Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);
        namee=findViewById(R.id.delvery_name);
        landmarkk=findViewById(R.id.delvery_landmark);
        emaill=findViewById(R.id.delvery_email);
        addresss=findViewById(R.id.delvery_address);
        districtt=findViewById(R.id.delvery_district);
        cityy=findViewById(R.id.delvery_city);
        pincodee=findViewById(R.id.delvery_pincode);


        new RetrofitHelper(AddressFieldsActivity.this).getApIs().delivery_details(action,userId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status=jsonObject.getString("status");
                            String name=jsonObject.getString("name");
                            String landmark=jsonObject.getString("landmark");
                            String email=jsonObject.getString("email");
                            String address=jsonObject.getString("address");
                            String district=jsonObject.getString("district");
                            String city=jsonObject.getString("city");
                            String pincode=jsonObject.getString("pincode");


                            namee.setText(name);
                            landmarkk.setText(landmark);
                            emaill.setText(email);
                            addresss.setText(address);
                            districtt.setText(district);
                            cityy.setText(city);
                            pincodee.setText(pincode);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });


    addNew.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent addressIntent=new Intent(AddressFieldsActivity.this,AddressEnterActivity.class);
            startActivity(addressIntent);
            finish();

        }
    });
    }
}
