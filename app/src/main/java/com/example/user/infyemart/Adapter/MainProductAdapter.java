package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.infyemart.MainProductsActivity;
import com.example.user.infyemart.Pojo.Pojo_Products;
import com.example.user.infyemart.R;

import java.util.ArrayList;

/**
 * Created by USER on 14-12-2017.
 */

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pojo_Products>arrayListProducts;


    public MainProductAdapter(MainProductsActivity mainProductsActivity, ArrayList<Pojo_Products> productsArrayList) {
        this.arrayListProducts=productsArrayList;
        this.context=mainProductsActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pojo_Products pojo=arrayListProducts.get(position);

        holder.productName.setText(pojo.getProduct_name());
        holder.marginPrice.setText(pojo.getMargin_price());
        holder.originalPrice.setText(pojo.getOriginal_price());
        holder.optionName.setText(pojo.getOption_name());
        holder.offer.setText(pojo.getOffer());
    }

    @Override
    public int getItemCount() {
        return arrayListProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,offer,optionName,originalPrice,marginPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productList_name);
            offer=itemView.findViewById(R.id.productList_offer);
            optionName=itemView.findViewById(R.id.productList_optionName);
            originalPrice=itemView.findViewById(R.id.productList_originalPrice);
            marginPrice=itemView.findViewById(R.id.productList_marginPrice);

        }
    }
}
