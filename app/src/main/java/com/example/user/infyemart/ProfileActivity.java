package com.example.user.infyemart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Pojo.Pojo_district;
import com.example.user.infyemart.Pojo.Pojo_place;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

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
    EditText proName,proEmail,proNumber,proAddress,proPincode,proPassword;;
    Spinner proPlace,proDistrict;
    String sName,sEmail,sNumber,sAddress,sPincode,sDistrict,sPlace,sPassword;
    Button submit;
    String user_id;
    String action_reg2="register_second";
    SharedPreferences prefs;
    TextInputLayout password;

    ArrayList<String> arrayListDistrict=new ArrayList<>();
    ArrayList <String> arrayListPlace=new ArrayList<>();
    String[] stringDistrict;
    String[] stringPlace;
    String selectedDistrict,selectedPlace;


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
        password=findViewById(R.id.profile_passwordLay);


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

        arrayListDistrict.add("[ Select District ]");
        proPlace.setSelection(0);


        getDistrict();
        proDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                arrayListPlace.clear();;
                arrayListPlace.add("[ Select Place ]");
                selectedDistrict=String.valueOf(position);
                sDistrict=proDistrict.getSelectedItem().toString();
                sDistrict=arrayListDistrict.get(position);
                if (Integer.valueOf(selectedDistrict)>1){
                    String actionPlace="place";
                    getPlace(actionPlace,selectedDistrict);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        proPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPlace=String.valueOf(position);
                sPlace=proPlace.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editor = getSharedPreferences("SHARED_DATA", MODE_PRIVATE).edit();

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("user_id",null);
        if (restoredText !=null){

            user_id=prefs.getString("user_id","00");


           if (user_id!=("00")) {


               submit.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       sName=proName.getText().toString().toUpperCase();
                       sEmail=proEmail.getText().toString();
                       sAddress=proAddress.getText().toString();
                       sNumber=proNumber.getText().toString();
                       sPincode=proPincode.getText().toString();
//                       sPlace=proPlace.getText().toString();
//                       sDistrict=proDistrict.getText().toString();
                       updateProfile();
                   }
               });
           }
        }
        else {
            password.setVisibility(View.VISIBLE);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sName=proName.getText().toString().toUpperCase();
                    sEmail=proEmail.getText().toString();
                    sAddress=proAddress.getText().toString();
                    sNumber=proNumber.getText().toString();
                    sPincode=proPincode.getText().toString();
//                    sPlace=proPlace.getText().toString();
//                    sDistrict=proDistrict.getText().toString();
                    sPassword=proPassword.getText().toString();
                    createProfile();
                }
            });
        }
    }

    private void getPlace(String actionPlace, String selectedDistrict) {
        new RetrofitHelper(ProfileActivity.this).getApIs().getPlace(actionPlace,selectedDistrict).enqueue(
                new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                int id=jsonObject.getInt("id");
                                int districtId=jsonObject.getInt("district");
                                String place=jsonObject.getString("place");
                                Pojo_place pojo=new Pojo_place();
                                pojo.setId(id);
                                pojo.setDistrict(districtId);
                                pojo.setPlace(place);
                                arrayListPlace.add(place);
                                Object[] objectList=arrayListPlace.toArray();
                                stringPlace=Arrays.copyOf(objectList,objectList.length,String[].class);


                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ProfileActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item,stringPlace){
                                    @Override
                                    public boolean isEnabled(int position) {
                                        if (position==0){
                                            return  false;
                                        }
                                        return  true;
                                    }
                                };
                                proPlace.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                }
        );
    }

    private void getDistrict() {
        String actionDist="district";
        new RetrofitHelper(ProfileActivity.this).getApIs().district(actionDist).enqueue(new Callback<JsonElement>() {
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
                        arrayListDistrict.add(district);

                        Object[] objectsList=arrayListDistrict.toArray();
                        stringDistrict= Arrays.copyOf(objectsList,objectsList.length,String[].class);
                        ArrayAdapter<String>adapter=new ArrayAdapter<String>(ProfileActivity.this,
                                android.R.layout.simple_spinner_dropdown_item
                        ,stringDistrict){

                            @Override
                            public boolean isEnabled(int position) {
                                if (position==0){
                                    return  false;
                                }
                                return true;
                            }
                        };

                        proDistrict.setSelection(0);
                        proDistrict.setAdapter(adapter);
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

    private void updateProfile() {

        String actionUpdate="update_profile";
        new RetrofitHelper(ProfileActivity.this).getApIs().updateProfile(actionUpdate,sNumber,sAddress,selectedDistrict,
                selectedPlace,sPincode,sName,sEmail,user_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status,user_id,name,email,phone ,address,district,place,pincode;
                            status=jsonObject.getString("status");
                            user_id=jsonObject.getString("user_id");
                            name=jsonObject.getString("name");
                            phone=jsonObject.getString("phone");
                            email=jsonObject.getString("email");
                            address=jsonObject.getString("address");
                            district=jsonObject.getString("district");
                            place=jsonObject.getString("place");
                            pincode=jsonObject.getString("pincode");

                            String addressString = address + "\n" + district + "," + place + "\n" + phone;


                            Log.e("loggg", "onResponse of update: "+district+place );
                            if (status.equals("Success")){



                                    editor.putString("user_name", name);
                                    editor.putString("addressString", addressString);
                                    editor.apply();
                                    Log.e("loggg", "onResponse: "+user_id+"," +name +","+","+addressString );

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
