package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Adapter.CartAdapter;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ClickListener;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity  {

    private static final String TAG = "logg";
    private RecyclerView  recyclerView;
    CartAdapter mAdapter;
    Button checkout;
    String action ="cart";
    String cartId,idGot;
    TextView totalAmount,totalCount,deliveryChargeTv;
    Spinner deliverySpin;

    SharedPreferences prefs;
    private ArrayList<Pojo_Cart>cart_arraylist=new ArrayList<>();
    private ArrayList<String>deliverySlot=new ArrayList<>();
    private ArrayList<String>deliveryCharge=new ArrayList<>();
    String[] deliverySlotStr;
    CardView cardPriceDetails;
    LinearLayout noItems;
    ProgressDialog dialog;
    ScrollView cartScrollView;
    String actionDlte;
    int dltPos;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent=new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
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
        cartScrollView=findViewById(R.id.scrollView_cart);
        noItems=findViewById(R.id.no_items);
        cartScrollView.setVisibility(View.GONE);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading cart..");
        dialog.show();

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
            cartId=prefs.getString("session_id","0");
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
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String slot_name=jsonObject.getString("slot_name");
                                String delivery_charge=jsonObject.getString("delivery_charge");

                                deliverySlot.add(slot_name);

                                Object[] objects=deliverySlot.toArray();
                                deliverySlotStr= Arrays.copyOf(objects,objects.length,String[].class);

                                ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(
                                        CartActivity.this,android.R.layout
                                .simple_spinner_dropdown_item,deliverySlotStr);
                                deliverySpin.setAdapter(arrayAdapter);
                            }
                        } catch (JSONException e) {
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
                            if (jsonArray.isNull(0)){
                                noItems.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String status=jsonObject.getString("status");
                                noItems.setVisibility(View.GONE);
                                String totalAmountS=jsonObject.getString("total_price");
                                String totalCountS=jsonObject.getString("total_count");

                                totalAmount.setText("Rs - "+totalAmountS);
                                totalCount.setText("Price of ("+totalCountS+" items)");

                                JSONArray jsonArray1=jsonObject.getJSONArray("cart");
                                for (int i1=0;i<jsonArray1.length();i1++){

                                    JSONObject jsonObject1=jsonArray1.getJSONObject(i1);
                                    String product=jsonObject1.getString("product");
                                    String m_price=jsonObject1.getString("m_price");
                                    String quantity=jsonObject1.getString("quantity");
                                    String o_price=jsonObject1.getString("o_price");
                                    String image=jsonObject1.getString("image");
                                    String id=jsonObject1.getString("id");

                                    final Pojo_Cart pojo = new Pojo_Cart();
                                    pojo.setId(id);
                                    pojo.setImage(image);
                                    pojo.setmPrice("Rs "+m_price);
                                    pojo.setoPrice("Rs "+o_price);
                                    pojo.setQuantity(quantity);
                                    pojo.setProduct(product);
                                    cart_arraylist.add(pojo);

                                    if (dialog.isShowing()){
                                        dialog.dismiss();
                                        cartScrollView.setVisibility(View.VISIBLE);
                                    }

                                    CartAdapter cartAdapter=new CartAdapter(CartActivity.this,
                                            cart_arraylist, new ClickListener() {
                                        @Override
                                        public void onClicked(String value) {
                                             actionDlte="cart_delete";
                                             dltPos= Integer.parseInt(value);
                                            deleteCart(actionDlte, Integer.parseInt(value),cartId);
                                        }

                                        @Override
                                        public void onClickedImage(String position) {
                                        }
                                        @Override
                                        public void cart_plus(String value) {
                                            String actionP="cart_update";
                                            int qty=1;
                                            plusCart(actionP, Integer.parseInt(value),cartId,qty);
                                        }
                                        @Override
                                        public void cart_minus(String value) {
                                            String actionD="cart_decrement";
                                            int qty=1;
                                            minusCart(actionD, Integer.parseInt(value),cartId,qty);
                                        }
                                    });
                                    recyclerView.setAdapter(cartAdapter);

                                }
                            }
                        } catch (JSONException e) {
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CartActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void minusCart(final String actionD, final int i, final String cartId, int qty) {
        new RetrofitHelper( CartActivity.this).getApIs().cart_minus(actionD,i,cartId,qty).enqueue(
                new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String updated_count=jsonObject.getString("updated_count");
                            String cart_countS=jsonObject.getString("cart_count");
                            Log.e(TAG, "onResponse:1 "+cart_countS );



                            if (!cart_countS.equals(0)){
                                Log.e(TAG, "onResponse:2 "+cart_countS );

                                deleteCart(actionDlte,dltPos,cartId);
                            }else {
                                CookieSyncManager.createInstance(CartActivity.this);
                                CookieManager cookieManager=CookieManager.getInstance();
                                cookieManager.removeAllCookie();
                                Intent intent=new Intent(CartActivity.this,CartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }


                        } catch (JSONException e) {}
                    }
                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                    }}
        );
    }
    private void plusCart(String actionP, int i, String cartId, int qty) {

        new RetrofitHelper(CartActivity.this).getApIs().cart_plus(actionP,i,cartId,qty).enqueue(
                new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String updated_count=jsonObject.getString("updated_count");
                            String cart_countS=jsonObject.getString("cart_count");

                            CookieSyncManager.createInstance(CartActivity.this);
                            CookieManager cookieManager=CookieManager.getInstance();
                            cookieManager.removeAllCookie();

                            Intent intent=new Intent(CartActivity.this,CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                }
        );
    }
    private void deleteCart(String actionDlte, int value, String cartId) {

        new RetrofitHelper(CartActivity.this).getApIs().cart_delete(actionDlte,value,cartId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        try {
                            JSONObject  jsonObject=new JSONObject(response.body().toString());
                            String status=jsonObject.getString("status");
                            CookieSyncManager.createInstance(CartActivity.this);
                            CookieManager cookieManager = CookieManager.getInstance();
                            cookieManager.removeAllCookie();
                            Intent intent= new Intent(CartActivity.this, CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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
