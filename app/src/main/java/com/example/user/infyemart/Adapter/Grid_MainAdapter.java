package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.infyemart.R;

/**
 * Created by USER on 14-12-2017.
 */

public class Grid_MainAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] category;
    private final int[] catImage;

    public Grid_MainAdapter(Context mContext, String[] category, int[] catImage) {
        this.mContext = mContext;
        this.category = category;
        this.catImage = catImage;
    }

    @Override
    public int getCount() {
        return category.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            grid=new View(mContext);
            grid=inflater.inflate(R.layout.list_main,null);
            TextView textView=grid.findViewById(R.id.c_nameTv_grid);
            ImageView imageView=grid.findViewById(R.id.c_imgIv_grid);
            textView.setText(category[position]);
            imageView.setImageResource(catImage[position]);
        }else {
            grid=(View)convertView;

        }
        return grid;
    }
}
