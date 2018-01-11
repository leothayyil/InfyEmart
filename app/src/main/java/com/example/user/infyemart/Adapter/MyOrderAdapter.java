package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.MyOrdersActivity;
import com.example.user.infyemart.Pojo.Pojo_orderedListDetails;
import com.example.user.infyemart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by USER on 18-12-2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {

    Context context;
    ArrayList <Pojo_orderedListDetails> listDetails;
    public MyOrderAdapter(MyOrdersActivity myOrdersActivity, ArrayList<Pojo_orderedListDetails> listDetails) {

        this.context=myOrdersActivity;
        this.listDetails=listDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_order,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Pojo_orderedListDetails pojo=listDetails.get(position);
        holder.productName.setText(pojo.getProduct());
        holder.unit.setText("Qty "+pojo.getUnit()+" * "+pojo.getQuantity());
        Picasso.with(context).load(pojo.getImage()).placeholder(R.drawable.loading).error(R.drawable.error_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName,unit;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.orderProductId);
            unit=itemView.findViewById(R.id.order_unit);
            imageView=itemView.findViewById(R.id.order_itemIv);

        }
    }
}
