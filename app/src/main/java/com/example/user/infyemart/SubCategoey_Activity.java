package com.example.user.infyemart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.infyemart.Adapter.SubCat_Adapter;
import com.example.user.infyemart.Pojo.Pojo_SubCat;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoey_Activity extends AppCompatActivity {

    RecyclerView subRecyclerView;
    String action="sub_category";
    int categoryId;

    ArrayList<Pojo_SubCat>subArraylist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoey_);
        subRecyclerView=findViewById(R.id.recyclerSubCat);
        subRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(SubCategoey_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        subRecyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        categoryId=intent.getExtras().getInt("position");


        new RetrofitHelper(SubCategoey_Activity.this).getApIs().sub_Category(action,String.valueOf(categoryId))
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String status=jsonObject.getString("status");
                                String sub_category=jsonObject.getString("sub_category");
                                String sub_cat_id=jsonObject.getString("sub_cat_id");
                                String categoryId=jsonObject.getString("category");
                                Pojo_SubCat pojo=new Pojo_SubCat();
                                pojo.setCategoryId(categoryId);
                                pojo.setSubCategoryName(sub_category);
                                pojo.setSub_cat_id(sub_cat_id);
                                subArraylist.add(pojo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });

        SubCat_Adapter adapter=new SubCat_Adapter(SubCategoey_Activity.this,subArraylist);
        subRecyclerView.setAdapter(adapter);

    }
}
