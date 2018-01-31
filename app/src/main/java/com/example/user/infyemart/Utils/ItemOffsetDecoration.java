package com.example.user.infyemart.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.user.infyemart.MainActivity;

/**
 * Created by USER on 18-12-2017.
 */

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int offset;
    public ItemOffsetDecoration(MainActivity mainActivity, int offset){
        this.offset=offset;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) == -1) {
            outRect.right=offset;
            outRect.left=offset;
            outRect.top=offset;
            outRect.bottom=offset;
        }
    }
}
