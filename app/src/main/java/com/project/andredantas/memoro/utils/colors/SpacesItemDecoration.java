package com.project.andredantas.memoro.utils.colors;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.andredantas.memoro.R;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSize;
    private Context mContext;
    private int margin;

    public SpacesItemDecoration(Context context, int size, int margin) {
        this.mSize = size;
        this.mContext = context;
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        int totalWidth = parent.getWidth();
        outRect.left = margin;
        outRect.right = margin;
        int width = (int) mContext.getResources().getDimension(R.dimen.circle_size);
        final int delta = (totalWidth / 2) - (width * width / 2) - (100 * (mSize - 1));
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {

            // Checking the Recycler View left bound, so the shape doesn't go out of it.
            if(delta>parent.getLeft())
                outRect.left = delta;
        }
    }
}
