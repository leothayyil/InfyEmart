package com.example.user.infyemart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.infyemart.Adapter.SubCat_Adapter;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoey_Activity extends AppCompatActivity {

    RecyclerView subRecyclerView;
    String action="sub_category";
    String categoryId="2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoey_);
        subRecyclerView=findViewById(R.id.recyclerSubCat);
        subRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(SubCategoey_Activity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        subRecyclerView.setLayoutManager(layoutManager);

        SubCat_Adapter adapter=new SubCat_Adapter(SubCategoey_Activity.this);
        subRecyclerView.setAdapter(adapter);

        new RetrofitHelper(SubCategoey_Activity.this).getApIs().sub_Category(action,categoryId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String sub_category=jsonObject.getString("sub_category");

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
