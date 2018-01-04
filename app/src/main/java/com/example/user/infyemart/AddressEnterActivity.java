package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Pojo.Pojo_district;
import com.example.user.infyemart.Pojo.Pojo_place;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressEnterActivity extends AppCompatActivity {

    EditText name,address,email,district,city,landmark,pincode;
    String sName,sAddress,sEmail,sDistrict,sCity,sPincode,sLandmark;
    Button updateBtn;
    String userId="4";
    String action="add_delivery_address";
    String action_district="district";
    String action_place="place";
    String selectedDistrict="5";
    ArrayList <Pojo_district> arrayListDistrict=new ArrayList<>();
    ArrayList <Pojo_place> arrayListPlace=new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_enter);

        Toolbar toolbar=findViewById(R.id.toolbar_addNewaddressid);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTit.setText("Add Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        name=findViewById(R.id.addDelivery_name);
        address=findViewById(R.id.addDelivery_address);
        email=findViewById(R.id.addDelivery_email);
        district=findViewById(R.id.addDelivery_district);
        city=findViewById(R.id.addDelivery_place);
        landmark=findViewById(R.id.addDelivery_landmark);
        pincode=findViewById(R.id.addDelivery_pincode);
        updateBtn=findViewById(R.id.addDelivery_button);

       getDistrict();
       getPlace();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName=name.getText().toString();
                sAddress=address.getText().toString();
                sEmail=email.getText().toString();
                sCity=city.getText().toString();
                sLandmark=landmark.getText().toString();
                sPincode=pincode.getText().toString();
                sDistrict=district.getText().toString();


                updateData();

                Intent purchaseIntent=new Intent(AddressEnterActivity.this, AccountActivity.class);
                startActivity(purchaseIntent);
            }
        });


    }

    private void getPlace() {
        new RetrofitHelper(AddressEnterActivity.this).getApIs().getPlace(action_place,selectedDistrict)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                int district=jsonObject.getInt("district");
                                String place=jsonObject.getString("place");
                                Pojo_place pojo=new Pojo_place();
                                pojo.setId(id);
                                pojo.setDistrict(district);
                                pojo.setPlace(place);
                                arrayListPlace.add(pojo);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    private void getDistrict() {
        new RetrofitHelper(AddressEnterActivity.this).getApIs().district(action_district)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                String district=jsonObject.getString("district");
                                Pojo_district pojo=new Pojo_district();
                                pojo.setId(id);
                                pojo.setDistrict(district);
                                arrayListDistrict.add(pojo);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    private void updateData() {
        new RetrofitHelper(AddressEnterActivity.this).getApIs().addDelivery_details
                (action,userId,sName,sEmail,sAddress,sDistrict,sCity,sLandmark,sPincode).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().toString());
                    String statusResp=jsonObject.getString("status");
                    String nameResp=jsonObject.getString("name");
                    String landmarkResp=jsonObject.getString("landmark");
                    String emailResp=jsonObject.getString("email");
                    String addressResp=jsonObject.getString("address");
                    String districtResp=jsonObject.getString("district");
                    String cityResp=jsonObject.getString("city");
                    String pincodeResp=jsonObject.getString("pincode");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
