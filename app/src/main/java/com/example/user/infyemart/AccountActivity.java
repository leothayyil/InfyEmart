package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {


    ImageView edt_address;

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

        edt_address=findViewById(R.id.iv_address_edit);
        edt_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

    }

}
