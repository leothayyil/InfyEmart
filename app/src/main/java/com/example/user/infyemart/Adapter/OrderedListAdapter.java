package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.infyemart.MyOrderList_Activity;
import com.example.user.infyemart.Pojo.Pojo_OrderedList;
import com.example.user.infyemart.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by USER on 11-01-2018.
 */

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.MyViewHolder> {
    Context context;
    ArrayList<Pojo_OrderedList> ordersList=new ArrayList<>();
    public OrderedListAdapter(MyOrderList_Activity myOrderList_activity, ArrayList<Pojo_OrderedList> orderedLists) {

        this.context=myOrderList_activity;
        this.ordersList=orderedLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_order_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Pojo_OrderedList pojo=ordersList.get(position);
        holder.status.setText("( "+pojo.getStatus()+" )");
        holder.totalCount.setText(pojo.getTotalCount()+ "- in list");
        holder.grandTotal.setText("Total Amount - "+pojo.getGrandTotal());
        holder.orderId.setText("Order Id - "+pojo.getOrderId());
        holder.bookedDate.setText(pojo.getBookedDate());


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status,bookedDate,orderId,grandTotal,totalCount;
        public MyViewHolder(View itemView) {
            super(itemView);

            status=itemView.findViewById(R.id.OL_status);
            bookedDate=itemView.findViewById(R.id.OL_date);
            orderId=itemView.findViewById(R.id.OL_orderId);
            grandTotal=itemView.findViewById(R.id.OL_amount);
            totalCount=itemView.findViewById(R.id.OL_count);
        }
    }
}
