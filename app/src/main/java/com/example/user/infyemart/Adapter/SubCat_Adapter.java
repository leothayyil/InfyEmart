package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.infyemart.Pojo.Pojo_SubCat;
import com.example.user.infyemart.R;
import com.example.user.infyemart.SubCategoey_Activity;

import java.util.ArrayList;


public class SubCat_Adapter extends RecyclerView.Adapter<SubCat_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Pojo_SubCat>arrayList=new ArrayList<>();
    public SubCat_Adapter(SubCategoey_Activity subCategoey_activity, ArrayList<Pojo_SubCat> subArraylist) {
        this.context = subCategoey_activity;
        this.arrayList = subArraylist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_subcategory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pojo_SubCat pojo=arrayList.get(position);
        holder.textView.setText(pojo.getSubCategoryName());

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.tv_subCat);
        }
    }
}
