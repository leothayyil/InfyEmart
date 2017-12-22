package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


public class AddressFieldsActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    TextView addNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_fields);
        addNew=findViewById(R.id.addNewAddressId);
        Toolbar toolbar=findViewById(R.id.toolbar_addressAdd);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbarTit.setText("Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    addNew.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent addressIntent=new Intent(AddressFieldsActivity.this,AddressEnterActivity.class);
            startActivity(addressIntent);
        }
    });
    }
}
