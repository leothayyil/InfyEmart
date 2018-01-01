package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.Pojo.Pojo_Banner;
import com.example.user.infyemart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Slider_Adapter extends PagerAdapter{
    private ArrayList<Pojo_Banner>images;
    private LayoutInflater inflater;
    private Context context;

    public Slider_Adapter(MainActivity mainActivity, ArrayList<Pojo_Banner> imgsArray) {
        this.images=imgsArray;
        this.context=mainActivity;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View myImageLayout=inflater.inflate(R.layout.list_slide,container,false);
        ImageView imageView=myImageLayout.findViewById(R.id.iv_slide_image_id);
        Pojo_Banner pojo=new Pojo_Banner();
//        images.get(pojo.)
//        Picasso.with(context).load(images.get(pojo.getBaner1(),pojo.));
//        imageView.setImageResource(images.get(position));
        container.addView(myImageLayout,0);
        return  myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
