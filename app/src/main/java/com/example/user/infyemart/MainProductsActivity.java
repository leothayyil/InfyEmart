package com.example.user.infyemart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    String categoryId,sub_catId;
    ArrayList<Pojo_Products> productsArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    private String TAG="logg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);
        recyclerView=findViewById(R.id.recycler_products_Main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        Intent intent=getIntent();
        categoryId=intent.getExtras().getString("category_id");
        sub_catId=intent.getExtras().getString("sub_categoryId");

        Log.d(TAG, categoryId+","+sub_catId);


        AsycProductsList async=new AsycProductsList();
        async.execute();

        MainProductAdapter adapter= new MainProductAdapter(MainProductsActivity.this,productsArrayList);
        recyclerView.setAdapter(adapter);

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
                                    String offer=jsonObject.getString("offer");
                                    String option_name=jsonObject.getString("option_name");
                                    String original_price=jsonObject.getString("original_price");
                                    String margin_price=jsonObject.getString("margin_price");
                                    String item_id=jsonObject.getString("item_id");
                                    String product_id=jsonObject.getString("product_id");

                                    Pojo_Products pojo=new Pojo_Products();
                                    pojo.setItem_id(item_id);
                                    pojo.setMargin_price(margin_price);
                                    pojo.setOffer(offer);
                                    pojo.setOption_name(option_name);
                                    pojo.setOriginal_price(original_price);
                                    pojo.setProduct_id(product_id);
                                    pojo.setProduct_name(product_name);
                                    productsArrayList.add(pojo);
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
