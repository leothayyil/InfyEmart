package com.example.user.infyemart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView signup;
    String passWord,userName;
    String action;
    String addressString, user_name;
    SharedPreferences.Editor editor;
    EditText edtUserName,edtPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);
        edtUserName=findViewById(R.id.input_email_login);
        edtPassWord=findViewById(R.id.input_password_login);

        editor = getSharedPreferences("SHARED_DATA", MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName=edtUserName.getText().toString();
                passWord=edtPassWord.getText().toString();
                if (userName.equals("")){
                    Toast.makeText(LoginActivity.this, "login id field is blank!", Toast.LENGTH_SHORT).show();
                } else if (passWord.equals("")) {
                    Toast.makeText(LoginActivity.this, "Password field is blank!", Toast.LENGTH_SHORT).show();
                }else {
                    action = "login";
                    loginCall();
                }
               

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginCall() {
        new RetrofitHelper(LoginActivity.this)
                .getApIs().login(action, userName, passWord)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            String status = jsonObject.getString("status");
                            String user_id = jsonObject.getString("user_id");
                            String session_id = jsonObject.getString("session_id");
                            user_name = jsonObject.getString("name");
                            String address = jsonObject.getString("address");
                            String district = jsonObject.getString("district");
                            String place = jsonObject.getString("place");
                            String phone = jsonObject.getString("phone");

                            addressString = address + "\n" + district + "," + place + "\n" + phone;
                      
                            if (status.equals("Success")){
                                editor.putString("user_id", user_id);
                                editor.putString("session_id", session_id);
                                editor.putString("user_name", user_name);
                                editor.putString("addressString", addressString);
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Invalid login details!", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

}