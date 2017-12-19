package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.infyemart.MainProductsActivity;
import com.example.user.infyemart.R;

/**
 * Created by USER on 14-12-2017.
 */

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private Context context;
    private final String[] category;
    private final int[] catImage;

    public MainProductAdapter(MainProductsActivity mainProductsActivity, String[] category, int[] categoryImgs) {
        this.context=mainProductsActivity;
        this.category=category;
        this.catImage=categoryImgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_products,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(category[position]);
    }

    @Override
    public int getItemCount() {
        return category.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;

        public ViewHolder(View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.productName_cartId);
        }
    }
}
