package com.example.user.infyemart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.Utils_status;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewActivity extends AppCompatActivity {

    String productId;
    String actionPV="product_details";
    ScrollView scrollView;
    TextView productName,brand,quantity,offer,originalPrice,marginPrice,cartCount,toolbarTit;
    ImageView imageView,mainCart;
    String cartCountStr;
    ImageButton addBtn;
    String itemId,cartId;
    String actionToCart="add_to_cart";
    ProgressDialog dialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent=new Intent(ProductViewActivity.this,MainProductsActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        productName=findViewById(R.id.PV_productNmae);
        quantity=findViewById(R.id.PV_qty);
        offer=findViewById(R.id.PV_offer);
        originalPrice=findViewById(R.id.PV_originalPrice);
        marginPrice=findViewById(R.id.PV_marginPrice);
        imageView=findViewById(R.id.PV_imageView);
        scrollView=findViewById(R.id.scrollViewProductView);
        addBtn=findViewById(R.id.PV_add);
        Toolbar toolbar =findViewById(R.id.toolbarProductView);
        setSupportActionBar(toolbar);
         toolbarTit = findViewById(R.id.toolbar_title);
        Utils_status.darkenStatusBar(this,R.color.red);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView mainAccount=findViewById(R.id.mainToolbarAccount);
         mainCart=findViewById(R.id.mainToolbarCart);
         cartCount=findViewById(R.id.cartCountId);
        mainAccount.setVisibility(View.GONE);
        toolbarTit.setVisibility(View.GONE);
        dialog=new ProgressDialog(this);

        cartCount.setVisibility(View.INVISIBLE);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.image_click));
                String itemCount="1";
                addToCart(actionToCart,cartId,itemCount,itemId);

            }
        });

        scrollView.setVisibility(View.GONE);
        Bundle extras=getIntent().getExtras();
        if (extras !=null){
            productId=extras.getString("product_id");
            cartId=extras.getString("cart_id");
            itemId=extras.getString("item_id");
            dialog.setTitle("Getting product details..");
            dialog.show();
            AsyncProductView async=new AsyncProductView();
            async.execute();
        }
        mainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.image_click));
                Intent intent=new Intent(ProductViewActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addToCart(String actionToCart, String cartId, String itemCount, String itemId) {

        new RetrofitHelper(ProductViewActivity.this).getApIs().addToCart(actionToCart,itemId,itemCount,cartId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response.body().toString());
                            String item_count=jsonObject.getString("item_count");
                            cartCountStr=jsonObject.getString("total_count");
                            cartCount.setText(cartCountStr);
                            if (cartCountStr!=null&&cartCountStr!="0"){
                                cartCount.setVisibility(View.VISIBLE);
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


    private class AsyncProductView extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] objects) {

            productDetails();
            return null;
        }
    }

    private void productDetails() {
        new RetrofitHelper(ProductViewActivity.this).getApIs().product_details(actionPV,productId,cartId)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            JSONArray  jsonArray=new JSONArray(response.body().toString());
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                scrollView.setVisibility(View.VISIBLE);

                                String product_nameS=jsonObject.getString("product_name");
                                String brandS=jsonObject.getString("brand");
                                String imageS=jsonObject.getString("image");
                                cartCountStr=jsonObject.getString("cart_count");
                                cartCount.setText(cartCountStr);

                                if (cartCountStr.equals("null")){

                                    cartCount.setVisibility(View.GONE);
                                }else if (cartCountStr=="0"){

                                    cartCount.setVisibility(View.GONE);
                                }else {
                                    cartCount.setVisibility(View.VISIBLE);
                                    mainCart.setVisibility(View.VISIBLE);
                                }

                                JSONArray jsonArray1=jsonObject.getJSONArray("variant");
                                for (int ii=0;ii<jsonArray1.length();ii++){
                                    JSONObject jsonObject1=jsonArray1.getJSONObject(ii);
                                    String quantityS=jsonObject1.getString("quantity");
                                    String offerS=jsonObject1.getString("offer");
                                    String original_priceS=jsonObject1.getString("original_price");
                                    String margin_priceS=jsonObject1.getString("margin_price");
                                    productName.setText(product_nameS);
//                                    brand.setText(brandS);
                                    quantity.setText("Quantity "+quantityS+" \n"+brandS);
                                    offer.setText(offerS+" Off" );
                                    originalPrice.setText("Offer price ₹ "+original_priceS+"");
                                    marginPrice.setText("Original price ₹ "+margin_priceS+" ");
                                    Picasso.with(ProductViewActivity.this).load(imageS).placeholder(R.drawable.loading).
                                            error(R.drawable.error_image)
                                            .into(imageView);

                                    toolbarTit.setVisibility(View.VISIBLE);
                                    toolbarTit.setText(product_nameS);


                                    if (dialog.isShowing()){
                                        dialog.dismiss();
                                    }
                                }



                            }

                        } catch (JSONException e) {


                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
    }
}