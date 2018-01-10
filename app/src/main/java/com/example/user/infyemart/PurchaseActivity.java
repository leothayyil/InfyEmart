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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.Adapter.CartAdapter;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.R;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseActivity extends AppCompatActivity {
    Button continuePayment,changeAddress;
    Context context;
    RecyclerView recyclerView;
    ArrayList<Pojo_Cart>cartProducts=new ArrayList<>();
    TextView purcahserName,purchaserAddress;
    TextView purchaseAmount,purchaseCount;

    String action ="cart";
    String action_account="my_account";
    String cartId;
    String user_id;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        continuePayment=findViewById(R.id.continuePurchaseAct);

        recyclerView=findViewById(R.id.recyclerPurchase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPurchase);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        toolbarTit.setText("Delivery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);
        context=PurchaseActivity.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purcahserName=findViewById(R.id.purchaser_name);
        purchaserAddress=findViewById(R.id.purchaser_address);
        changeAddress=findViewById(R.id.btn_changeAddress);
        purchaseAmount=findViewById(R.id.purchase_amount);
        purchaseCount=findViewById(R.id.purchase_count);

        SharedPreferences prefs = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        String restoredText = prefs.getString("user_id", null);

        if (restoredText != null) {
            user_id = prefs.getString("user_id", "0");
            cartId=prefs.getString("session_id","0");
        }

        AsyncPurchase async=new AsyncPurchase();
        async.execute();
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PurchaseActivity.this,AddressFieldsActivity.class);
                startActivity(intent);
            }
        });

        new RetrofitHelper(PurchaseActivity.this).getApIs().myAccount(action_account,user_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObjectt=new JSONObject(response.body().toString());
                            String status=jsonObjectt.getString("status");
                            String nameS=jsonObjectt.getString("name");
                            String emailS=jsonObjectt.getString("email");
                            String addressS=jsonObjectt.getString("address");

                                                purcahserName.setText(nameS);
                                                purchaserAddress.setText(addressS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });

        continuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent=new Intent(PurchaseActivity.this, PaymentsActivity.class);
                startActivity(purchaseIntent);
            }
        });
    }

    private class AsyncPurchase extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(PurchaseActivity.this).getApIs().cart(action,cartId)
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

                                purchaseAmount.setText("Rs - "+totalAmountS);
                                purchaseCount.setText("Price of ("+totalCountS+" items)");

                                JSONArray jsonArray1=jsonObject.getJSONArray("cart");
                                for (int i1=0;i<jsonArray1.length();i1++) {

                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i1);
                                    String product = jsonObject1.getString("product");
                                    String m_price = jsonObject1.getString("m_price");
                                    String quantity = jsonObject1.getString("quantity");
                                    String o_price = jsonObject1.getString("o_price");
                                    String image = jsonObject1.getString("image");
                                    String id = jsonObject1.getString("id");

                                    Pojo_Cart pojo = new Pojo_Cart();
                                    pojo.setId(id);
                                    pojo.setImage(image);
                                    pojo.setmPrice("Rs " + m_price);
                                    pojo.setoPrice("Rs " + o_price);
                                    pojo.setQuantity("Qty " + quantity);
                                    pojo.setProduct(product);
                                    cartProducts.add(pojo);

                                    CartAdapter cartAdapter = new CartAdapter(PurchaseActivity.this, cartProducts);
                                    recyclerView.setAdapter(cartAdapter);

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
            return null;
        }
    }

}
