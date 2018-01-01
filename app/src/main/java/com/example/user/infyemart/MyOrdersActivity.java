package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.MyOrderAdapter;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] Category = {
            "Groceries Staples",
            "Spices Pickles",
            "House Holds",
            "Instant Foods Drinks",
            "Beauty Health",
            "Toys Baby Care",
            "Vegetables Fruits",
            "Fresh Meat Fish",
            "Mobiles Laptops",
            "Home Appliances",
            "Kitchen Appliances",
            "Leather Trends",
    } ;


    @Override
    public boolean onSupportNavigateUp() {
onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView=findViewById(R.id.recycler_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myOrderToolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("My Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
//        MyOrderAdapter adapter=new MyOrderAdapter(MyOrdersActivity.this,Category,CategoryImgs);
//        recyclerView.setAdapter(adapter);
    }
}
