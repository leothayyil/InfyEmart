package com.example.user.infyemart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Adapter.MainProductAdapter;
import com.example.user.infyemart.Adapter.Main_RecyclerAdapter;
import com.example.user.infyemart.Pojo.Pojo_Products;
import com.example.user.infyemart.Pojo.Pojo_Variant;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainProductsActivity extends AppCompatActivity {

    String action="product_listing";
    String categoryId,sub_catId,subCategoryName;
    ArrayList<Pojo_Products> productsArrayList=new ArrayList<>();
    ArrayList<Pojo_Variant>variantArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    private String TAG="logg";
    TextView cartCount;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);


        Toolbar toolbar =findViewById(R.id.toolbarMainProducts);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
         cartCount=findViewById(R.id.cartCountId);
        mainAccount.setVisibility(View.GONE);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("getItemId"));

        recyclerView=findViewById(R.id.recycler_products_Main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        Intent intent=getIntent();
        categoryId=intent.getExtras().getString("category_id");
        sub_catId=intent.getExtras().getString("sub_categoryId");
        subCategoryName=intent.getExtras().getString("sub_category");
        toolbarTit.setText(subCategoryName);

        AsycProductsList async=new AsycProductsList();
        async.execute();

    }
    private class AsycProductsList extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            new RetrofitHelper(MainProductsActivity.this).getApIs().product_listing(action,categoryId,sub_catId)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response.body().toString());
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String status=jsonObject.getString("status");
                                    String product_name=jsonObject.getString("product_name");
                                    String product_image=jsonObject.getString("image");
                                    String product_id=jsonObject.getString("product_id");


//                                    JSONArray jsonArray1=jsonObject.getJSONArray("variant");
//                                    for (int ii=0;ii<jsonArray1.length();ii++){
//                                        JSONObject jsonObject1=jsonArray1.getJSONObject(ii);

/*
                                        String offer=jsonObject1.getString("offer");
                                        String option_name=jsonObject1.getString("option_name");
                                        String original_price=jsonObject1.getString("original_price");
                                        String margin_price=jsonObject1.getString("margin_price");
                                        String item_id=jsonObject1.getString("item_id");
*/

                                        Pojo_Products pojo=new Pojo_Products();
                                        pojo.setProduct_id(product_id);
                                        pojo.setProduct_name(product_name);
                                        pojo.setProduct_image(product_image);


//                                        Pojo_Variant pojoV=new Pojo_Variant();
//                                        pojoV.setOffer(offer);
//                                        pojoV.setOptionName(option_name);
//                                        pojoV.setItemId(item_id);
//                                        pojoV.setMargin_price(margin_price);
//                                        pojoV.setOriginal_price(original_price);


//                                        variantArrayList.add(pojoV);
                                        productsArrayList.add(pojo);

//                                    }
                                }

                                MainProductAdapter adapter=new MainProductAdapter(
                                        MainProductsActivity.this,productsArrayList
//                                        ,variantArrayList
                                );
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
    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            String itemId=intent.getStringExtra("itemId");
            String itemId="12";
            String actionToCart="add_to_cart";
            String itemCount="25";
            addToCart(actionToCart,itemId,itemCount);
        }
    };

    private void addToCart(String actionToCart, String itemId, String itemCount) {
        new RetrofitHelper(MainProductsActivity.this).getApIs().addToCart(actionToCart,itemId,itemCount)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String resp_count=jsonObject.getString("item_count");
                            String cart_id=jsonObject.getString("cart_id");
                            cartCount.setText(resp_count);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }
}
