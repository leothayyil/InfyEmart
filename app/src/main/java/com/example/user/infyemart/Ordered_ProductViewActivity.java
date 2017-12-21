package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Ordered_ProductViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered__product_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOrderList);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("Order Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
