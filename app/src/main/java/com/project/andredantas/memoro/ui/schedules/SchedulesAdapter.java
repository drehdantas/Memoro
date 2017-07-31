package com.project.andredantas.memoro.ui.schedules;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.utils.Constants;
import com.project.andredantas.memoro.utils.Utils;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre Dantas on 7/13/16.
 */
public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.MyViewHolder>{

    private String day;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnScheduleClickListener listener;
    private List<ScheduleRealm> scheduleRealmList = Collections.EMPTY_LIST;

    public SchedulesAdapter(Context context, List<ScheduleRealm> scheduleRealmList, String day, OnScheduleClickListener listener) {
        if(context == null)
            return;
        this.context = context;
        this.listener = listener;
        this.scheduleRealmList = scheduleRealmList;
        this.day = day;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_schedule_recycler_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ScheduleRealm scheduleRealm = scheduleRealmList.get(position);
        holder.scheduleTitle.setText(scheduleRealm.getTitle());
        holder.scheduleHour.setText(scheduleRealm.getTime());
        if (scheduleRealm.getColor() != 0){
            holder.scheduleCard.setBackgroundColor(Utils.getColors(scheduleRealm.getColor(), context));
        }
    }

    @Override
    public int getItemCount() {
        return scheduleRealmList == null ? 0 : scheduleRealmList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.schedule_title)
        TextView scheduleTitle;
        @Bind(R.id.horario_hora)
        TextView scheduleHour;
        @Bind(R.id.background_color)
        RelativeLayout scheduleCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.schedule_card)
        public void onClickImageTrending() {
            listener.onScheduleClick(scheduleRealmList.get(getAdapterPosition()), getAdapterPosition() + 1);
        }
    }

    interface OnScheduleClickListener {
        void onScheduleClick(ScheduleRealm scheduleRealm, int day);
    }
}
