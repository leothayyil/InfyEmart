package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.infyemart.SubCategoey_Activity;

/**
 * Created by USER on 02-01-2018.
 */

public class SubCat_Adapter extends RecyclerView.Adapter<SubCat_Adapter.MyViewHolder> {

    Context context;
    public SubCat_Adapter(SubCategoey_Activity subCategoey_activity) {
        this.context=subCategoey_activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
