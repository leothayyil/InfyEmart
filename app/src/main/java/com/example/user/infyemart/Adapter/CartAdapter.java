package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.infyemart.CartActivity;
import com.example.user.infyemart.Pojo.Pojo_Cart;
import com.example.user.infyemart.PurchaseActivity;
import com.example.user.infyemart.R;
import com.example.user.infyemart.Retrofit.RetrofitHelper;
import com.example.user.infyemart.Utils.ClickListener;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {


    Context context;
    ArrayList<Pojo_Cart>arrayListCart;
    ClickListener mClickListener;
    String updatedCount;

    public CartAdapter(CartActivity cartActivity, ArrayList<Pojo_Cart> cart_arraylist,ClickListener listener) {
        this.arrayListCart=cart_arraylist;
        this.context=cartActivity;
        this.mClickListener=listener;
    }
    public CartAdapter(PurchaseActivity purchaseActivity, ArrayList<Pojo_Cart> cartProducts) {
        this.arrayListCart=cartProducts;
        context=purchaseActivity;
    }

    public CartAdapter(String updated_count) {
        this.updatedCount=updated_count;
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

        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mClickListener.onClicked(pojo.getId());
            }
        });

        holder.cartPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.cart_plus(pojo.getId());
            }
        });
        holder.cartMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.cart_minus(pojo.getId());

            }
        });
    }


    @Override
    public int getItemCount() {
            return arrayListCart.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView productName, mPrice, oPrice, total;
        ImageView imageproduct,cartPlus;
        ImageButton cartMinus;
        ImageView cartDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_productName);
            mPrice = itemView.findViewById(R.id.cart_mPrice);
            oPrice = itemView.findViewById(R.id.cart_oPrice);
            total = itemView.findViewById(R.id.cart_total);
            imageproduct = itemView.findViewById(R.id.cart_image);
            cartDelete = itemView.findViewById(R.id.deleteItemCart);
            cartMinus=itemView.findViewById(R.id.minusItemCart);
            cartPlus=itemView.findViewById(R.id.plusItemCart);
        }

    }
}
