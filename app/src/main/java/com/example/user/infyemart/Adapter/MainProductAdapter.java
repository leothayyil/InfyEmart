package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.MainProductsActivity;
import com.example.user.infyemart.Pojo.Pojo_Products;
import com.example.user.infyemart.Pojo.Pojo_Variant;
import com.example.user.infyemart.R;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

/**
 * Created by USER on 14-12-2017.
 */

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pojo_Products>arrayListProducts;
    private ArrayList<Pojo_Variant>arrayListVariant;
    String [] variantsDum={"500 gms","1 kg","2 kg", "5 kg"};
    ClickListener mClickListener;


    public MainProductAdapter(MainProductsActivity mainProductsActivity, ArrayList<Pojo_Products> productsArrayList,
                              ArrayList<Pojo_Variant> variantArrayList,ClickListener listener) {
        this.arrayListProducts=productsArrayList;
        this.context=mainProductsActivity;
        this.arrayListVariant=variantArrayList;
        this.mClickListener=listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Pojo_Products pojo=arrayListProducts.get(position);
        final Pojo_Variant pojoV=arrayListVariant.get(position);

//        Log.e(TAG, "onBindViewHolder: "+String.valueOf(arrayListVariant.size() ));

        holder.productName.setText(pojo.getProduct_name());

        holder.marginPrice.setText(pojoV.getMargin_price());
        holder.originalPrice.setText(pojoV.getOriginal_price());
        holder.optionName.setText(pojoV.getOptionName());
        holder.offer.setText(pojoV.getOffer());

        Picasso.with(context).load(pojo.getProduct_image()).placeholder(R.drawable.loading)
                .error(R.drawable.error_image).into(holder.productImage);

        ArrayAdapter aa=new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,variantsDum);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.variantSpin.setAdapter(aa);
        holder.linearLayout.setVisibility(View.GONE);

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent("getPosition");
                String productId=pojo.getProduct_id();
                intent.putExtra("position",String.valueOf(position));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent("getItemId");
                String itemId=pojoV.getItemId();
                mClickListener.onClicked(itemId);
//                intent.putExtra("itemId",itemId);
//                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayListProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,offer,optionName,originalPrice,marginPrice;
        ImageButton addToCartBtn;
        ImageView  productImage;
        Spinner variantSpin;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productList_name);
            offer=itemView.findViewById(R.id.productList_offer);
            optionName=itemView.findViewById(R.id.productList_optionName);
            originalPrice=itemView.findViewById(R.id.productList_originalPrice);
            marginPrice=itemView.findViewById(R.id.productList_marginPrice);
            productImage=itemView.findViewById(R.id.productList_image);
            addToCartBtn=itemView.findViewById(R.id.addToCart_btn);
            variantSpin=itemView.findViewById(R.id.variant_spinnerId);
            linearLayout=itemView.findViewById(R.id.linearCount);
        }
    }
}
