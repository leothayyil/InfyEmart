package com.example.user.infyemart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.CartAdapter;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

     private RecyclerView  recyclerView;
    CartAdapter mAdapter;
    Button checkout;
    String action ="cart";
    String cartId="9d2286553b0e45f736e19010a5f784c9";

    SharedPreferences prefs;
    private ArrayList<Pojo_Cart>cart_arraylist=new ArrayList<>();


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar_Cart);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("My Cart");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        checkout=findViewById(R.id.cart_checkoutId);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
//            cartId=prefs.getString("session_id","0");
        }

        recyclerView=findViewById(R.id.recyclerCart);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        AsyncCart asyncCart=new AsyncCart();
        asyncCart.execute();


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent=new Intent(CartActivity.this, PurchaseActivity.class);
                startActivity(purchaseIntent);
            }
        });
    }

    private class AsyncCart extends AsyncTask{


        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(CartActivity.this).getApIs().cart(action,cartId)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response.body().toString());
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String status=jsonObject.getString("status");
                                    String product=jsonObject.getString("product");
                                    String m_price=jsonObject.getString("m_price");
                                    String total=jsonObject.getString("total");
                                    String o_price=jsonObject.getString("o_price");
                                    String image=jsonObject.getString("image");
                                    String id=jsonObject.getString("id");
                                    Pojo_Cart pojo = new Pojo_Cart();
                                    pojo.setId(id);
                                    pojo.setImage(image);
                                    pojo.setmPrice(m_price);
                                    pojo.setoPrice(o_price);
                                    pojo.setTotal(total);
                                    pojo.setProduct(product);
                                    cart_arraylist.add(pojo);

                                }
                                CartAdapter cartAdapter=new CartAdapter(CartActivity.this,cart_arraylist);
                                recyclerView.setAdapter(cartAdapter);


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
