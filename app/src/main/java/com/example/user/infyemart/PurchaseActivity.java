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
    Button continuePayment;
    Context context;
    RecyclerView recyclerView;
    ArrayList<Pojo_Cart>cartProducts=new ArrayList<>();

    String action ="cart";
    String cartId="9d2286553b0e45f736e19010a5f784c9";
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

        SharedPreferences prefs = getSharedPreferences("SHARED_DATA", MODE_PRIVATE);
        String restoredText = prefs.getString("user_id", null);

        if (restoredText != null) {
            user_id = prefs.getString("user_id", "0");
        }

        AsyncPurchase async=new AsyncPurchase();
        async.execute();

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
                                    cartProducts.add(pojo);

                                }
                                CartAdapter cartAdapter=new CartAdapter(PurchaseActivity.this,cartProducts);
                                recyclerView.setAdapter(cartAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            new RetrofitHelper(PurchaseActivity.this).getApIs().myAccount(action,user_id)
                                    .enqueue(new Callback<JsonElement>() {
                                        @Override
                                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                            try {
                                                JSONObject jsonObject=new JSONObject(response.body().toString());
                                                String status=jsonObject.getString("status");
                                                String nameS=jsonObject.getString("name");
                                                String emailS=jsonObject.getString("email");
                                                String addressS=jsonObject.getString("address");

//                                                name.setText(nameS);
//                                                email.setText(emailS);
//                                                address.setText(addressS);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<JsonElement> call, Throwable t) {

                                        }
                                    });
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                        }
                    });
            return null;
        }
    }

}
