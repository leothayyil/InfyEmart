package com.example.user.infyemart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    SharedPreferences.Editor editor;
    EditText proName,proEmail,proNumber,proAddress,proPlace,proDistrict,proPincode,proPassword;
    String sName,sEmail,sNumber,sAddress,sPincode,sDistrict,sPlace,sPassword;
    Button submit;
    String user_id;
    String action_reg2="register_second";
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        proName=findViewById(R.id.profile_name);
        proEmail=findViewById(R.id.profile_email);
        proPincode=findViewById(R.id.profile_pinCode);
        proNumber=findViewById(R.id.profile_number);
        proAddress=findViewById(R.id.profile_address);
        submit=findViewById(R.id.profile_btn);
        proPlace=findViewById(R.id.profile_place);
        proDistrict=findViewById(R.id.profile_district);
        proPassword=findViewById(R.id.profile_password);


        Toolbar toolbar =findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        editor = getSharedPreferences("SHARED_DATA", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
            user_id=prefs.getString("session_id","0");
           if (!user_id.equals("")) {
               submit.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       sName=proName.getText().toString();
                       sEmail=proEmail.getText().toString();
                       sAddress=proAddress.getText().toString();
                       sNumber=proNumber.getText().toString();
                       sPincode=proPincode.getText().toString();
                       sPlace=proPlace.getText().toString();
                       sDistrict=proDistrict.getText().toString();
                       sPassword=proPassword.getText().toString();

                       updateProfile();
                   }
               });
           }

        }
        else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sName=proName.getText().toString();
                    sEmail=proEmail.getText().toString();
                    sAddress=proAddress.getText().toString();
                    sNumber=proNumber.getText().toString();
                    sPincode=proPincode.getText().toString();
                    sPlace=proPlace.getText().toString();
                    sDistrict=proDistrict.getText().toString();
                    sPassword=proPassword.getText().toString();

                    createProfile();
                }

            });
        }



    }

    private void updateProfile() {

        String actionUpdate="update_profile";
        new RetrofitHelper(ProfileActivity.this).getApIs().updateProfile(actionUpdate,sNumber,sAddress,sDistrict,sPlace,sPincode,sPassword,sName,sEmail,user_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status,user_id,name,email,phone,address,district,place,pincode;
                            status=jsonObject.getString("status");
                            user_id=jsonObject.getString("user_id");
                            name=jsonObject.getString("name");
                            email=jsonObject.getString("phone");
                            address=jsonObject.getString("address");
                            district=jsonObject.getString("district");
                            place=jsonObject.getString("place");
                            pincode=jsonObject.getString("pincode");


                            if (status.equals("Success")){

                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ProfileActivity.this,"Updated ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {


                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    private void createProfile() {

        new RetrofitHelper(ProfileActivity.this).getApIs().registerSecond(action_reg2,sNumber,
                sAddress,sDistrict,sPlace,sPincode,sPassword,sName,sEmail)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status=jsonObject.getString("status");
                            String user_id=jsonObject.getString("user_id");
                            String name=jsonObject.getString("name");
                            String email=jsonObject.getString("email");
                            String phone=jsonObject.getString("phone");
                            String address=jsonObject.getString("address");
                            String district=jsonObject.getString("district");
                            String place=jsonObject.getString("place");
                            String pincode=jsonObject.getString("pincode");
                            String session_id=jsonObject.getString("session_id");

                            String addressString = address + "\n" + district + "," + place + "\n" + phone;

                            if (status.equals("Success")){
                                editor.putString("user_id", user_id);
                                editor.putString("session_id", session_id);
                                editor.putString("user_name", name);
                                editor.putString("addressString", addressString);
                                editor.apply();
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(ProfileActivity.this,"Success ", Toast.LENGTH_SHORT).show();

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
}
