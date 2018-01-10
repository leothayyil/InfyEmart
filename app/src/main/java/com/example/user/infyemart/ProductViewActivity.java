package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ProductViewActivity extends AppCompatActivity {

    String TAG="logg";
    String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Bundle extras=getIntent().getExtras();
        if (extras !=null){
            productId=extras.getString("product_id");
        }
    }
}
