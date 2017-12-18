package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.R;

/**
 * Created by USER on 18-12-2017.
 */

public class Main_RecyclerAdapter extends RecyclerView.Adapter<Main_RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private final String[] category;
    private final int[] catImage;
    private static final int TYPE_HEADER=0;
    public static final int TYPE_ITEM=1;
    private static final int TYPE_FOOTER = 2;


    public Main_RecyclerAdapter(Context mContext, String[] category, int[] catImage) {
        this.mContext = mContext;
        this.category = category;
        this.catImage = catImage;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {

            return TYPE_HEADER;

        }else if (isPositionFooter(position)){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position >category.length;
//                category.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_ITEM){
//            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent,false);
//
//            return new MyViewHolder(view);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent,false);

        return new MyViewHolder(view);
        }
//        throw new RuntimeException("there is no type that matches type"+viewType);
//    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            holder.catName.setText(category[position]);
            holder.catImage.setImageResource(catImage[position]);
        }
    }

    @Override
    public int getItemCount() {
        return category.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        ImageView catImage;
        public MyViewHolder(View itemView) {
            super(itemView);

            catName=itemView.findViewById(R.id.c_nameTv_grid);
            catImage=itemView.findViewById(R.id.c_imgIv_grid);
        }
    }
}
