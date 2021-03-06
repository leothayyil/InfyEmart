package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.MyOrderAdapter;
import com.example.user.infyemart.Pojo.Pojo_orderedListDetails;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.Utils_status;
import com.google.gson.JsonElement;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int table_idd;
    String action="my_order_details";
    ArrayList<Pojo_orderedListDetails> listDetails=new ArrayList<>();
    MyOrderAdapter adapter;
    BottomNavigationView bottom;
    TextView bottomAmount,bottom1,bottom2,bottom3;
    RelativeLayout relativeLayout;
    ProgressDialog dialog;


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
        Toolbar toolbar = findViewById(R.id.myOrderToolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);
        relativeLayout=findViewById(R.id.relativeLayoutId);
        relativeLayout.setVisibility(View.GONE);
        toolbarTit.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottom=findViewById(R.id.cart_Bottombar);
        bottomAmount=findViewById(R.id.bottomAmount);
        bottom1=findViewById(R.id.bottomDetails1);
        bottom2=findViewById(R.id.bottomDetails2);
        bottom3=findViewById(R.id.bottomDetails3);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Getting data,Please wait..");
        dialog.show();
        Utils_status.darkenStatusBar(this,R.color.red);
        AsyncOrders asyncOrders=new AsyncOrders();
        asyncOrders.execute();
        Bundle extras=getIntent().getExtras();
        if (extras !=null)
        {
//             table_idd=extras.getInt("table_id");
            table_idd=55;
        }

    }
    private class AsyncOrders extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(MyOrdersActivity.this).getApIs().my_order_details(action,table_idd)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response.body().toString());
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String status=jsonObject.getString("status");
                                    String booked_date=jsonObject.getString("booked_date");
                                    String order_id=jsonObject.getString("order_id");
                                    String grand_total=jsonObject.getString("grand_total");
                                    String total_count=jsonObject.getString("total_count");

                                    JSONArray jsonArray1=jsonObject.getJSONArray("product");
                                    for (int ii=0;ii<jsonArray1.length();ii++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(ii);
                                        String productName=jsonObject1.getString("product");
                                        String unit=jsonObject1.getString("Unit");
                                        String quantity=jsonObject1.getString("quantity");
                                        String item_price=jsonObject1.getString("item_price");
                                        String image=jsonObject1.getString("image");

                                        Pojo_orderedListDetails pojo=new Pojo_orderedListDetails();
                                        pojo.setImage(image);
                                        pojo.setItem_price(item_price);
                                        pojo.setProduct(productName);
                                        pojo.setQuantity(quantity);
                                        pojo.setUnit(unit);
                                        listDetails.add(pojo);
                                        bottomAmount.setText("Grand Total : "+grand_total);
                                        bottom1.setText(status);
                                        bottom2.setText(booked_date+"  ( "+total_count+" Items )");
                                        bottom3.setText("Order id "+order_id);

                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                            relativeLayout.setVisibility(View.VISIBLE);
                                        }


                                    }
                                }
                                adapter=new MyOrderAdapter(MyOrdersActivity.this,listDetails);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                        }
                    });
            return null;
        }
    }
}
