package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.Pojo.Pojo_categories;
import com.example.user.infyemart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by USER on 18-12-2017.
 */

public class Main_RecyclerAdapter extends RecyclerView.Adapter<Main_RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    ArrayList<Pojo_categories>category_call=new ArrayList<>();


    public Main_RecyclerAdapter(MainActivity mainActivity, ArrayList<Pojo_categories> categories_call) {
        this.mContext=mainActivity;
        this.category_call=categories_call;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent,false);

        return new MyViewHolder(view);
        }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pojo_categories pojo=category_call.get(position);

      holder.catName.setText(pojo.getCategory());
        Picasso.with(mContext).load(pojo.getIcon()).placeholder(R.drawable.loading).error(R.drawable.error_image)
                .into(holder.catImage);
    }

    @Override
    public int getItemCount() {
      return category_call.size();
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
