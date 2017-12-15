package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.user.infyemart.Adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {
    String[] cartItems={"Kadala Big (കടല വലുത് )","Toor Dal (തുവര പരിപ്പ്) Sambar Parippu",
            "LG Compounded (കായം) Asafoetida Cake","Chandrika Handwash Essential Oil 215 Ml"};
    RecyclerView  recyclerView;
    CartAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Cart);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerCart);
        mAdapter=new CartAdapter(getApplicationContext(),cartItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartAdapter(this, cartItems);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }
}
