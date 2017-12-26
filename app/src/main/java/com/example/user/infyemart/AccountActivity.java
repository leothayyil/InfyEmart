package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {


    ImageView edt_address;
    TextView myOrder,myNottif,myAddres;
    CardView cmyOrder,cmyNottif,cmyAddres;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAccount);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

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
                Intent intent=new Intent(AccountActivity.this,MyOrdersActivity.class);
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

    }

}
