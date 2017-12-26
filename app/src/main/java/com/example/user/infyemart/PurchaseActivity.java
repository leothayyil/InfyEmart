package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.R;

public class PurchaseActivity extends AppCompatActivity {
    Button continuePayment;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        continuePayment=findViewById(R.id.continuePurchaseAct);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPurchase);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("Delivery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        continuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent=new Intent(PurchaseActivity.this, PaymentsActivity.class);
                startActivity(purchaseIntent);
            }
        });
    }
}
