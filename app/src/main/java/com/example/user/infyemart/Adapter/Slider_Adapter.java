package com.example.user.infyemart.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.infyemart.MainActivity;
import com.example.user.infyemart.Pojo.Pojo_Banner;
import com.example.user.infyemart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;


public class Slider_Adapter extends PagerAdapter{
    private ArrayList<Pojo_Banner>images;
    private LayoutInflater inflater;
    private Context context;
    String[] imagesArray;
    public Slider_Adapter(MainActivity mainActivity, ArrayList<Pojo_Banner> bannerImgsArray, String[] stringImgs) {
        this.images=bannerImgsArray;
        this.context=mainActivity;
        this.imagesArray=stringImgs;
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

        Pojo_Banner pojo=images.get(position);

        Log.e("loggg", "adapter "+imagesArray.length);
        for (int i=0;i<3;i++){
            Picasso.with(context).load(imagesArray[i]).placeholder(R.drawable.loading)
                    .error(R.drawable.error_image).into(imageView);
        }

        container.addView(myImageLayout,0);
        return  myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
