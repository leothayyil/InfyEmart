package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {
    String[] cartItems={"Kadala Big (കടല വലുത് )","Toor Dal (തുവര പരിപ്പ്) Sambar Parippu",
            "LG Compounded (കായം) Asafoetida Cake","Chandrika Handwash Essential Oil 215 Ml"};
    RecyclerView  recyclerView;
    CartAdapter mAdapter;
    Button checkout;



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Cart);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("My Cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkout=findViewById(R.id.cart_checkoutId);

        recyclerView=findViewById(R.id.recyclerCart);
        mAdapter=new CartAdapter(getApplicationContext(),cartItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartAdapter(this, cartItems);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent=new Intent(CartActivity.this, PurchaseActivity.class);
                startActivity(purchaseIntent);
            }
        });
    }
}
