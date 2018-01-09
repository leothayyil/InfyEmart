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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.CartAdapter;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.Pojo.Pojo_deliverySlot;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "logg";
    private RecyclerView  recyclerView;
    CartAdapter mAdapter;
    Button checkout;
    String action ="cart";
    String cartId;
    TextView totalAmount,totalCount;
    Spinner deliverySpin;

    SharedPreferences prefs;
    private ArrayList<Pojo_Cart>cart_arraylist=new ArrayList<>();
    private ArrayList<String>deliverySlot=new ArrayList<>();
    private ArrayList<String>deliveryCharge=new ArrayList<>();
    String[] deliverySlotStr;



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
        totalAmount=findViewById(R.id.cart_totalAmount);
        totalCount=findViewById(R.id.cart_totalCount);
        mainCart.setVisibility(View.GONE);
        deliverySpin=findViewById(R.id.deliverySpin);


        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
            cartId=prefs.getString("session_id","0");
            Log.e(TAG, "cartId  "+cartId);
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
            delverySpin();
            showcart();
            return null;
        }
    }

    private void delverySpin() {
        new RetrofitHelper(CartActivity.this).getApIs().delivery_slot_details("delivery_slot_details")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=1;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String slot_name=jsonObject.getString("slot_name");
                                String delivery_charge=jsonObject.getString("delivery_charge");
                                Log.e(TAG, "slot name and charge "+slot_name+delivery_charge );

                                deliverySlot.add(slot_name);

                                Object[] objects=deliverySlot.toArray();
                                deliverySlotStr= Arrays.copyOf(objects,objects.length,String[].class);

                                ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(CartActivity.this,android.R.layout
                                .simple_spinner_dropdown_item,deliverySlotStr);
                                deliverySpin.setAdapter(arrayAdapter);

                            }
                        } catch (JSONException e) {

                            Log.e(TAG, "onResponse: "+ e );
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    private void showcart() {
        new RetrofitHelper(CartActivity.this).getApIs().cart(action,cartId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String status=jsonObject.getString("status");
                                String totalAmountS=jsonObject.getString("total_price");
                                String totalCountS=jsonObject.getString("total_count");

                                totalAmount.setText("Rs - "+totalAmountS);
                                totalCount.setText("Price of ("+totalCountS+" items)");

                                JSONArray jsonArray1=jsonObject.getJSONArray("cart");
                                for (int i1=1;i<jsonArray1.length();i1++){

                                    JSONObject jsonObject1=jsonArray1.getJSONObject(i1);
                                    String product=jsonObject1.getString("product");
                                    String m_price=jsonObject1.getString("m_price");
                                    String quantity=jsonObject1.getString("quantity");
                                    String o_price=jsonObject1.getString("o_price");
                                    String image=jsonObject1.getString("image");
                                    String id=jsonObject1.getString("id");

                                    Pojo_Cart pojo = new Pojo_Cart();
                                    pojo.setId(id);
                                    pojo.setImage(image);
                                    pojo.setmPrice("Rs "+m_price);
                                    pojo.setoPrice("Rs "+o_price);
                                    pojo.setQuantity("Qty "+quantity);
                                    pojo.setProduct(product);
                                    cart_arraylist.add(pojo);

                                    CartAdapter cartAdapter=new CartAdapter(CartActivity.this,cart_arraylist);
                                    recyclerView.setAdapter(cartAdapter);
                                    Log.e(TAG, cartId+","+cart_arraylist.size());


                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }
}
