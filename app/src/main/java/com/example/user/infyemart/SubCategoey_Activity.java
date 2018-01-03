package com.example.user.infyemart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Adapter.SubCat_Adapter;
import com.example.user.infyemart.Pojo.Pojo_SubCat;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.RecyclerItemClickListener;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoey_Activity extends AppCompatActivity {

    private static final String TAG = "logg";
    RecyclerView subRecyclerView;
    String action="sub_category";
    int categoryId;
    String subCategory,sub_category,sub_cat_id,category_Id;

    ArrayList<Pojo_SubCat>subArraylist=new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoey_);
        subRecyclerView=findViewById(R.id.recyclerSubCat);
        subRecyclerView.setHasFixedSize(true);
        Toolbar toolbar =findViewById(R.id.toolbar_subCat);
        setSupportActionBar(toolbar);
        TextView toolbarTit = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        ImageView mainCart=findViewById(R.id.mainToolbarCart);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        subRecyclerView.setLayoutManager(layoutManager);
        Intent intent=getIntent();
        categoryId=intent.getExtras().getInt("position");
        subCategory=intent.getExtras().getString("subCategory");
        toolbarTit.setText(subCategory);
        AsyncClass asyncClass=new AsyncClass();
        asyncClass.execute();
        subRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SubCategoey_Activity.this, subRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent1=new Intent(SubCategoey_Activity.this,MainProductsActivity.class);
                        intent1.putExtra("sub_categoryId",subArraylist.get(position).getSub_cat_id());
                        intent1.putExtra("category_id",subArraylist.get(position).getCategoryId());
                        intent1.putExtra("sub_category",subArraylist.get(position).getSubCategoryName());
                        startActivity(intent1);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }



    private class AsyncClass extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(SubCategoey_Activity.this).getApIs().sub_Category(action,String.valueOf(categoryId))
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response.body().toString());
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String status=jsonObject.getString("status");
                                     sub_category=jsonObject.getString("sub_category");
                                     sub_cat_id=jsonObject.getString("sub_cat_id");
                                     category_Id=jsonObject.getString("category");

                                    Log.d(TAG, "json  "+category_Id+","+sub_cat_id+","+sub_category);

                                    Pojo_SubCat pojo=new Pojo_SubCat();
                                    pojo.setCategoryId(category_Id);
                                    pojo.setSubCategoryName(sub_category);
                                    pojo.setSub_cat_id(sub_cat_id);
                                    subArraylist.add(pojo);

                                    SubCat_Adapter adapter=new SubCat_Adapter(SubCategoey_Activity.this,subArraylist);
                                    subRecyclerView.setAdapter(adapter);

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
