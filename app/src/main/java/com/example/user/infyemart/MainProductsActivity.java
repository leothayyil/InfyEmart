package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.infyemart.Adapter.MainProductAdapter;

public class MainProductsActivity extends AppCompatActivity {


    String[] Category = {
            "Groceries Staples","Spices Pickles",
            "House Holds","Instant Foods Drinks","Beauty Health",
            "Toys Baby Care","Vegetables Fruits ", "Fresh Meat Fish",
            "Mobiles Laptops", "Home Appliances","Kitchen Appliances","Leather Trends"} ;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        recyclerView=findViewById(R.id.recycler_products_Main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
//        recyclerView.setAdapter( adapter );
    }
}
