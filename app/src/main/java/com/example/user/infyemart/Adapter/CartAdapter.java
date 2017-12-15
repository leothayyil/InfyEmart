package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.R;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    String[] items;
    Context context;
    LayoutInflater mInflater;

    public CartAdapter(Context applicationContext, String[] cartItems) {
        this.items=cartItems;
        this.context=applicationContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.productName.setText(items[position]);
    }

    @Override
    public int getItemCount() {
            return items.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productName_cartId);
        }
    }
}
