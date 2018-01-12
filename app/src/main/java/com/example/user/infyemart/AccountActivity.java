package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {


    ImageView edt_address;
    TextView myOrder,myNottif,myAddres;
    CardView cmyOrder,cmyNottif,cmyAddres;
    String action="my_account";
    TextView name,email,address;
    CardView accountCard;
    String user_id;
    LinearLayout accountLinear;
    private ProgressDialog dialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = findViewById(R.id.toolbarAccount);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);
        accountCard=findViewById(R.id.accountCardId);
        dialog = new ProgressDialog(this);
        name=findViewById(R.id.account_name);
        address=findViewById(R.id.account_address);
        email=findViewById(R.id.account_email);
        accountLinear=findViewById(R.id.account_linear);
        accountLinear.setVisibility(View.INVISIBLE);
        dialog.setMessage("Getting data, please wait...");
        dialog.show();

        SharedPreferences prefs = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        String restoredText = prefs.getString("user_id", null);

        if (restoredText != null) {
            user_id = prefs.getString("user_id", "0");
        }

        edt_address=findViewById(R.id.iv_address_edit);
        myOrder=findViewById(R.id.Et_account_myOrderId);
        myNottif=findViewById(R.id.Et_account_notificationsId);
        myAddres=findViewById(R.id.Et_account_myDeliveryAddId);
        cmyOrder=findViewById(R.id.card_account_myOrderId);
        cmyNottif=findViewById(R.id.card_account_notificationsId);
        cmyAddres=findViewById(R.id.card_account_myDeliveryAddId);
        cmyNottif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });

        cmyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,MyOrderList_Activity.class);
                startActivity(intent);
            }
        });

        cmyAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,AddressFieldsActivity.class);
                startActivity(intent);
            }
        });
        edt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        AsyncAccount account=new AsyncAccount();
        account.execute();

    }
    private class AsyncAccount extends AsyncTask{
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(AccountActivity.this).getApIs().myAccount(action,user_id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response.body().toString());
                                String status=jsonObject.getString("status");
                                String nameS=jsonObject.getString("name");
                                String emailS=jsonObject.getString("email");
                                String addressS=jsonObject.getString("address");

                                name.setText(nameS);
                                email.setText(emailS);
                                address.setText(addressS);

                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                    accountLinear.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

        }
    }
}
