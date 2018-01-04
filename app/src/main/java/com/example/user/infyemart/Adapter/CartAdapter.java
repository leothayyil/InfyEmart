package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.CartActivity;
import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.PurchaseActivity;
import com.example.user.infyemart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {


    Context context;
    ArrayList<Pojo_Cart>arrayListCart;

    public CartAdapter(CartActivity cartActivity, ArrayList<Pojo_Cart> cart_arraylist) {
        this.arrayListCart=cart_arraylist;
        this.context=cartActivity;
    }

    public CartAdapter(PurchaseActivity purchaseActivity, ArrayList<Pojo_Cart> cartProducts) {
        this.arrayListCart=cartProducts;
        context=purchaseActivity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pojo_Cart pojo=arrayListCart.get(position);
        holder.total.setText(pojo.getTotal());
        holder.productName.setText(pojo.getProduct());
        holder.mPrice.setText(pojo.getmPrice());
        holder.oPrice.setText(pojo.getoPrice());
        Picasso.with(context).load(pojo.getImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error_image).into(holder.imageproduct);
    }

    @Override
    public int getItemCount() {
            return arrayListCart.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName,mPrice,oPrice,total;
        ImageView imageproduct;


        public MyViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.cart_productName);
            mPrice=itemView.findViewById(R.id.cart_mPrice);
            oPrice=itemView.findViewById(R.id.cart_oPrice);
            total=itemView.findViewById(R.id.cart_total);
            imageproduct=itemView.findViewById(R.id.cart_image);
        }
    }
}
