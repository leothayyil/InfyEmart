package com.example.user.infyemart;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.user.infyemart.Adapter.MainProductAdapter;
import com.example.user.infyemart.Adapter.Main_RecyclerAdapter;
import com.example.user.infyemart.Pojo.Pojo_Products;
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
    RecyclerView recyclerView;
    private String TAG="logg";

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
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

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

                                    JSONArray jsonArray1=jsonObject.getJSONArray("variant");
                                    for (int ii=0;ii<jsonArray1.length();ii++){
                                        JSONObject jsonObject1=jsonArray1.getJSONObject(ii);

                                        String offer=jsonObject1.getString("offer");
                                        String option_name=jsonObject1.getString("option_name");
                                        String original_price=jsonObject1.getString("original_price");
                                        String margin_price=jsonObject1.getString("margin_price");
                                        String item_id=jsonObject1.getString("item_id");
                                        String product_id=jsonObject.getString("product_id");


                                        Pojo_Products pojo=new Pojo_Products();
                                        pojo.setItem_id(item_id);
                                        pojo.setMargin_price(margin_price);
                                        pojo.setOffer(offer);
                                        pojo.setOption_name(option_name);
                                        pojo.setOriginal_price(original_price);
                                        pojo.setProduct_id(product_id);
                                        pojo.setProduct_name(product_name);
                                        pojo.setProduct_image(product_image);
                                        productsArrayList.add(pojo);

                                    }
                                }

                                MainProductAdapter adapter=new MainProductAdapter(MainProductsActivity.this,productsArrayList);
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
