package com.example.user.infyemart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "loggg";
    Button signup;
    EditText inputName,inputPassword,inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        inputName=findViewById(R.id.input_name);
        inputEmail=findViewById(R.id.input_email);
        inputPassword=findViewById(R.id.input_password);

        signup=findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=inputName.getText().toString().toUpperCase();
                String emal=inputEmail.getText().toString().toLowerCase();
                String password=inputPassword.getText().toString();
                String action="register_first";

                registerFirst(action,name,emal,password);


            }
        });
    }

    private void registerFirst(String action, String name, String email, String password) {

        new RetrofitHelper(SignUpActivity.this).getApIs().registerFirst(action, name, email, password)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String user_id=jsonObject.getString("user_id");
                            String name=jsonObject.getString("name");
                            String email=jsonObject.getString("email");
                            String status=jsonObject.getString("status");
                            
                            if (status.equals("Success")){
                                Log.e(TAG, "onResponse "+user_id+" "+name+" "+email );

                                new AlertDialog.Builder(SignUpActivity.this)
                                        .setTitle("do you want to complete profile?")
                                        .setMessage("Go to..")
                                        .setPositiveButton("Profile", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(SignUpActivity.this,ProfileActivity.class);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("Shop", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();

                            }else {
                                Toast.makeText(SignUpActivity.this, "Something wrong..Try Again", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {

                            Toast.makeText(SignUpActivity.this, "Something wrong..Try Again", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }
}
