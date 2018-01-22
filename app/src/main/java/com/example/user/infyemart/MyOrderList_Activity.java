package com.example.user.infyemart;

import android.app.ProgressDialog;
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

import com.example.user.infyemart.Adapter.OrderedListAdapter;
import com.example.user.infyemart.Pojo.Pojo_OrderedList;
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

public class MyOrderList_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderedListAdapter adapter;
    String action="my_order";
    String user_id="8";
    ArrayList<Pojo_OrderedList> orderedLists=new ArrayList<>();
    TextView toolbarTit,cartCount;
    ImageView mainCart;
    ProgressDialog dialog;
    String order_id;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list_);
        recyclerView=findViewById(R.id.recyclerMyOrder);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dialog=new ProgressDialog(this);

        Toolbar toolbar =findViewById(R.id.toolbar_orderedList);
        setSupportActionBar(toolbar);
        toolbarTit = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
        mainCart=findViewById(R.id.mainToolbarCart);
        cartCount=findViewById(R.id.cartCountId);
        mainAccount.setVisibility(View.GONE);
        mainCart.setVisibility(View.GONE);
        toolbarTit.setText("My Orders");
        dialog.setTitle("Loading..");
        dialog.show();

        AsyncList list=new AsyncList();
        list.execute();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MyOrderList_Activity.this, recyclerView, new
                RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        int num=Integer.parseInt(orderedLists.get(position).getId());
                        Intent intent=new Intent(MyOrderList_Activity.this,MyOrdersActivity.class);
                        intent.putExtra("table_id",num);
                        intent.putExtra("order_id",order_id);
                        startActivity(intent);
                        Log.e("logg", "onItemClick: "+num);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }
    private  class AsyncList extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            new RetrofitHelper(MyOrderList_Activity.this).getApIs().order_list(action,user_id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray=new JSONArray(response.body().toString());
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String status=jsonObject.getString("status");
                                    String booked_date=jsonObject.getString("booked_date");
                                     order_id=jsonObject.getString("order_id");
                                    String grand_total=jsonObject.getString("grand_total");
                                    String total_count=jsonObject.getString("total_count");
                                    String id=jsonObject.getString("table_id");
                                    Pojo_OrderedList pojo=new Pojo_OrderedList();
                                    pojo.setBookedDate(booked_date);
                                    pojo.setGrandTotal(grand_total);
                                    pojo.setId(id);
                                    pojo.setOrderId(order_id);
                                    pojo.setStatus(status);
                                    pojo.setTotalCount(total_count);
                                    orderedLists.add(pojo);

                                    adapter=new OrderedListAdapter(MyOrderList_Activity.this,orderedLists);
                                    recyclerView.setAdapter(adapter);
                                    if (dialog.isShowing()){
                                        dialog.dismiss();

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
