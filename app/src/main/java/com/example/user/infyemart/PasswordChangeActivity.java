package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.Utils_status;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends AppCompatActivity {

    private static final String TAG = "LOGG";
    EditText current1,current2,new1,new2;
    LinearLayout linearLayout1,linearLayout2;
    Button button1,button2;
    String action_check="current_password";
    String user_id="8";
    String current_password;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        linearLayout1=findViewById(R.id.password_linear1);
        linearLayout2=findViewById(R.id.password_linear2);
        button1=findViewById(R.id.password_btn1);
        button2=findViewById(R.id.password_btn2);
        current1=findViewById(R.id.password_current1);
        current2=findViewById(R.id.password_current2);
        new1=findViewById(R.id.password_new1);
        new1.setVisibility(View.GONE);
        new2=findViewById(R.id.password_new2);
        linearLayout2.setVisibility(View.GONE);
        Utils_status.darkenStatusBar(this,R.color.red);


        dialog=new ProgressDialog(this);

        Bundle extras=getIntent().getExtras();
        if (extras != null){
            user_id=extras.getString("user_id");
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Verifying password.. Please wait!");
                dialog.show();

               current_password= current1.getText().toString();
                checkPassword(action_check,user_id,current_password);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Updating password..!");
                String action_newPass="new_password";
                current_password=current1.getText().toString();
                String newPass=new2.getText().toString();
                changePassword(action_newPass,current_password,user_id,newPass);
            }
        });

    }

    private void changePassword(String action_newPass, final String current_password, final String user_id, String newPass) {

        new  RetrofitHelper(PasswordChangeActivity.this).getApIs().changePassword(action_newPass,user_id,
                current_password,newPass).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().toString());
                    String status=jsonObject.getString("status");

                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                    if (status.equals("Success")){
                        Toast.makeText(PasswordChangeActivity.this, status, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }else {
                        Toast.makeText(PasswordChangeActivity.this, "Something wrong! try again.", Toast.LENGTH_SHORT).show();
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

    private void checkPassword(final String action_check, final String user_id, final String current_password) {
        new RetrofitHelper(PasswordChangeActivity.this).getApIs().checkPassword(action_check,user_id,current_password)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String status=jsonObject.getString("status");
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            if (status.equals("Success")){
                                linearLayout1.setVisibility(View.GONE);
                                linearLayout2.setVisibility(View.VISIBLE);
                                Toast.makeText(PasswordChangeActivity.this, "Change Password", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(PasswordChangeActivity.this, "Something wrong! try Again..", Toast.LENGTH_SHORT).show();
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
