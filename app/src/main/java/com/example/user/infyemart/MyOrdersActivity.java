package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    int[] CategoryImgs = {
            R.drawable.c_grocery,
            R.drawable.c_spices,
            R.drawable.c_households,
            R.drawable.c_instantfood,
            R.drawable.c_beauty,
            R.drawable.c_toys,
            R.drawable.c_vegitable,
            R.drawable.c_meat,
            R.drawable.c_mobiles,
            R.drawable.c_homeapplia,
            R.drawable.c_kitchenappl,
            R.drawable.c_leather,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView=findViewById(R.id.recycler_order);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        MyOrderAdapter adapter=new MyOrderAdapter(MyOrdersActivity.this,Category,CategoryImgs);
        recyclerView.setAdapter(adapter);
    }
}
