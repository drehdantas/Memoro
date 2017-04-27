package com.project.andredantas.memoro.utils.colors;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.project.andredantas.memoro.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewColorAdapter extends RecyclerView.Adapter {
    private static final int NO_COLOR = 1;
    private static final int COLOR   = 0;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Integer> colors;
    private int selectedPos = 0;

    public RecyclerViewColorAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NO_COLOR) {
            return new NoColorHolder(layoutInflater.inflate(R.layout.item_no_color_picker_adapter, parent,false));
        } else {
            return new ColorHolder(layoutInflater.inflate(R.layout.item_color_picker_adapter, parent,false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            NoColorHolder noColorHolder = (NoColorHolder) holder;
            noColorHolder.itemView.setSelected(selectedPos == position);
        }else{
            GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.color_circle);
            drawable.setStroke(1, context.getResources().getColor(R.color.darker_gray));

            ColorHolder colorHolder = (ColorHolder) holder;
            drawable.setColor(colors.get(position - NO_COLOR));
            colorHolder.imageView.setBackground(drawable);
            colorHolder.itemView.setSelected(selectedPos == position);
        }
    }

    @Override
    public int getItemCount() {
        return colors == null ? 0 : colors.size() + NO_COLOR;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? NO_COLOR : COLOR;
    }

    class ColorHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.color_imageview)
        ImageView imageView;

        ColorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click_animation));
                    notifyItemChanged(selectedPos);
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        selectedPos = position;
                        notifyItemChanged(selectedPos);
//                        ((ProductDetailActivity)context).setProductColor(colors.get(selectedPos));
                    }

                }
            });

        }
    }

    class NoColorHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.no_color_imageview)
        ImageView imageView;

         NoColorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click_animation));
                    notifyItemChanged(selectedPos);
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        selectedPos = position;
                        notifyItemChanged(selectedPos);
//                        ((ProductDetailActivity)context).setProductColor(colors.get(selectedPos));
                    }

                }
            });

        }
    }

}
