package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.Adapter.MainProductAdapter;
import com.example.user.infyemart.Adapter.Main_RecyclerAdapter;
import com.example.user.infyemart.Pojo.Pojo_Products;
import com.example.user.infyemart.Pojo.Pojo_Variant;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ClickListener;
import com.example.user.infyemart.Utils.RecyclerItemClickListener;
import com.example.user.infyemart.Utils.Utils_status;
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
    String categoryId,sub_catId,subCategoryName,session_id,resp_count,total_count,position,cart_id;
    ArrayList<Pojo_Products> productsArrayList=new ArrayList<>();
    ArrayList<Pojo_Variant>variantArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    private String TAG="logg";
    TextView cartCount;
    String cartCountStr;
    SharedPreferences prefs,tempPrefs;
    ImageView addBtn;
    Button productCount,productAdd;
    LinearLayout noItems;
    LinearLayout linearLayoutCount;
    ProgressDialog dialog;
    JSONObject jsonObject;


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
        noItems=findViewById(R.id.no_items);
        addBtn=findViewById(R.id.addToCart_btn);
        Utils_status.darkenStatusBar(this,R.color.red);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.show();

        prefs=getSharedPreferences("SHARED_DATA",MODE_PRIVATE);
        tempPrefs=getSharedPreferences("TEMP_SHARED",MODE_PRIVATE);

        String restoredText=prefs.getString("session_id",null);
        if (restoredText !=null){
            session_id=prefs.getString("session_id","0");
            cart_id=session_id;
        }
        String tempRestored=tempPrefs.getString("sub_categoryId",null);
        if (tempRestored !=null){
            sub_catId=tempPrefs.getString("sub_categoryId",null);
            subCategoryName=tempPrefs.getString("sub_category",null);
            categoryId=tempPrefs.getString("category_id",null);
        }

        recyclerView=findViewById(R.id.recycler_products_Main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        toolbarTit.setText(subCategoryName);
        recyclerView.setVisibility(View.GONE);

        AsycProductsList async=new AsycProductsList();
        async.execute();

        mainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainProductsActivity.this,CartActivity.class);
                startActivity(intent1);
            }
        });
    }
    private class AsycProductsList extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            new RetrofitHelper(MainProductsActivity.this).getApIs().product_listing(action,
                    categoryId, sub_catId,cart_id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response.body().toString());
                                if (jsonArray.isNull(0)) {
                                    noItems.setVisibility(View.VISIBLE);
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                     jsonObject = jsonArray.getJSONObject(i);
                                    String status = jsonObject.getString("status");
                                    JSONArray Array1=jsonObject.getJSONArray("products");
                                    for (int iA=0;iA<Array1.length();iA++){
                                        JSONObject Objects=Array1.getJSONObject(iA);
                                        String product_name = Objects.getString("product_name");
                                        String product_image = Objects.getString("image");
                                        String product_id = Objects.getString("product_id");

                                        JSONArray jsonArray1 = Objects.getJSONArray("variant");
                                        for (int ii = 0; ii < jsonArray1.length(); ii++) {
                                            JSONObject jsonObject1 = jsonArray1.getJSONObject(ii);

                                            String offer = jsonObject1.getString("offer");
                                            String option_name = jsonObject1.getString("option_name");
                                            String original_price = jsonObject1.getString("original_price");
                                            String margin_price = jsonObject1.getString("margin_price");
                                            String item_id = jsonObject1.getString("item_id");
                                            Pojo_Products pojo = new Pojo_Products();
                                            pojo.setProduct_id(product_id);
                                            pojo.setProduct_name(product_name);
                                            pojo.setProduct_image(product_image);
                                            productsArrayList.add(pojo);

                                            Pojo_Variant pojoV = new Pojo_Variant();
                                            pojoV.setOffer(offer);
                                            pojoV.setOptionName(option_name);
                                            pojoV.setItemId(item_id);
                                            pojoV.setMargin_price("Price " + margin_price);
                                            pojoV.setOriginal_price("Offer price " + original_price);
                                            variantArrayList.add(pojoV);
                                            recyclerView.setVisibility(View.VISIBLE);
                                        }
                                }
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    if (productsArrayList.isEmpty()){
                                        noItems.setVisibility(View.VISIBLE);
                                    }

                                    cartCountStr=jsonObject.getString("cart_count");
                                    if (cartCountStr.equals("null")){
                                        cartCount.setVisibility(View.GONE);
                                    }else if (cartCountStr.equals("0")){
                                        cartCount.setVisibility(View.GONE);
                                    }else {
                                        cartCount.setText(cartCountStr);
                                        cartCount.setVisibility(View.VISIBLE);
                                    }
                                }
                                MainProductAdapter adapter = new MainProductAdapter(
                                        MainProductsActivity.this, productsArrayList,
                                        variantArrayList, new ClickListener() {
                                    @Override
                                    public void onClicked(String value) {

                                        String itemId=value;

                                        String actionToCart="add_to_cart";
                                        final  String itemCount="1";

                                        addToCart(actionToCart,itemId,itemCount);
                                    }

                                    @Override
                                    public void onClickedImage(String position) {

                                        Intent intent1=new Intent(MainProductsActivity.this,ProductViewActivity.class);

                                        String productId=productsArrayList.get(Integer.parseInt(position)).getProduct_id();
                                        String itemId=variantArrayList.get(Integer.parseInt(position)).getItemId();

                                        intent1.putExtra("product_id",productId);
                                        intent1.putExtra("cart_id",session_id);
                                        intent1.putExtra("item_id",itemId);
                                        startActivity(intent1);
                                    }

                                    @Override
                                    public void cart_plus(String value) {

                                    }

                                    @Override
                                    public void cart_minus(String value) {

                                    }
                                }
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
    private void addToCart(final String actionToCart, final String itemId, final String itemCount) {


        new RetrofitHelper(MainProductsActivity.this).getApIs().addToCart(actionToCart,itemId,itemCount,session_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body().toString());

                            resp_count=jsonObject.getString("item_count");
                             total_count=jsonObject.getString("total_count");
                            String cartId=jsonObject.getString("cart_id");
                            cartCount.setText(total_count);
                            cartCount.setVisibility(View.VISIBLE);
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
