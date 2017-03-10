package com.project.andredantas.memoro.ui.schedules;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Schedule;

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
    private LayoutInflater layoutInflater;
    private OnScheduleClickListener listener;
    private List<Schedule> scheduleList = Collections.EMPTY_LIST;

    public SchedulesAdapter(Context context, List<Schedule> scheduleList, String day, OnScheduleClickListener listener) {
        if(context == null)
            return;
        this.listener = listener;
        this.scheduleList = scheduleList;
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
        Schedule schedule = scheduleList.get(position);
        holder.scheduleTitle.setText(schedule.getTitle());
        holder.scheduleHour.setText(schedule.getTime());
    }

    @Override
    public int getItemCount() {
        return scheduleList == null ? 0 : scheduleList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.schedule_title)
        TextView scheduleTitle;
        @Bind(R.id.horario_hora)
        TextView scheduleHour;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.schedule_card)
        public void onClickImageTrending() {
            listener.onScheduleClick(scheduleList.get(getAdapterPosition()), day);
        }
    }

    public interface OnScheduleClickListener {
        void onScheduleClick(Schedule schedule, String day);
    }
}
