package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ItemClickCallback  itemClick;
    String itemId;

    public CartAdapter(CartActivity cartActivity, ArrayList<Pojo_Cart> cart_arraylist) {
        this.arrayListCart=cart_arraylist;
        this.context=cartActivity;
    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClick = itemClickCallback;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Pojo_Cart pojo=arrayListCart.get(position);
        holder.total.setText(pojo.getQuantity());
        holder.productName.setText(pojo.getProduct());
        holder.mPrice.setText(pojo.getmPrice());
        holder.oPrice.setText(pojo.getoPrice());
        Picasso.with(context).load(pojo.getImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error_image).into(holder.imageproduct);
//        holder.itemView.setOnClickListener((View.OnClickListener) context);
        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClick.onItemClick(pojo.getId());
            }
        });
    }
    public interface ItemClickCallback {

        void onItemClick(String value);

    }

    @Override
    public int getItemCount() {
            return arrayListCart.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName,mPrice,oPrice,total;
        ImageView imageproduct;
        ImageButton cartDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.cart_productName);
            mPrice=itemView.findViewById(R.id.cart_mPrice);
            oPrice=itemView.findViewById(R.id.cart_oPrice);
            total=itemView.findViewById(R.id.cart_total);
            imageproduct=itemView.findViewById(R.id.cart_image);
            cartDelete=itemView.findViewById(R.id.deleteItemCart);

        }
    }
}
