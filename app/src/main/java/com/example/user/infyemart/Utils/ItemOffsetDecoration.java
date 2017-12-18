package com.example.user.infyemart.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by USER on 18-12-2017.
 */

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int offset;
    public ItemOffsetDecoration(int offset){
        this.offset=offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildPosition(view) == 0) {
            outRect.right=offset;
            outRect.left=offset;
            outRect.top=offset;
            outRect.bottom=offset;
        }
    }
}
