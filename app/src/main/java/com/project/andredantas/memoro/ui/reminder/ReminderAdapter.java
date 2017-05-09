package com.project.andredantas.memoro.ui.reminder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.ReminderRealm;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;
import com.project.andredantas.memoro.utils.Utils;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre Dantas on 7/13/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder>{
    private Context context;
    private LayoutInflater layoutInflater;
    private OnReminderClickListener listener;
    private List<ReminderRealm> reminderRealmList = Collections.EMPTY_LIST;

    public ReminderAdapter(Context context, List<ReminderRealm> reminderRealmList, OnReminderClickListener listener) {
        if(context == null)
            return;

        this.listener = listener;
        this.context = context;
        this.reminderRealmList = reminderRealmList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reminder_recycler_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReminderRealm reminderRealm = reminderRealmList.get(position);
        holder.reminderTitle.setText(reminderRealm.getTitle());
        holder.reminderDescript.setText(reminderRealm.getTime() + " - " + Utils.setDataTime(reminderRealm.getDayAlarm(), reminderRealm.getMonthAlarm()));
        if (reminderRealm.getType().equals("text")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_text_format_white_24dp));
        }else if (reminderRealm.getType().equals("image")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_image_white_24dp));
        }else if (reminderRealm.getType().equals("voice")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_mic_white_24dp));
        }

        if (reminderRealm.getScheduleRelated() != 1){
            ScheduleRealm scheduleRealm = ScheduleDAO.getById(reminderRealm.getScheduleRelated());
            holder.backgroundColor.setBackgroundColor((int) scheduleRealm.getColorRealm().getColorNumber());
            holder.reminderImage.setColorFilter(ContextCompat.getColor(context,R.color.mdtp_white));
        }
    }

    @Override
    public int getItemCount() {
        return reminderRealmList == null ? 0 : reminderRealmList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.reminder_title)
        TextView reminderTitle;
        @Bind(R.id.reminderDescript)
        TextView reminderDescript;
        @Bind(R.id.reminder_image_type)
        ImageView reminderImage;
        @Bind(R.id.background_color)
        RelativeLayout backgroundColor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.reminder_card)
        public void clickReminder() {
            listener.onReminderClick(reminderRealmList.get(getAdapterPosition()));
        }
    }

    public interface OnReminderClickListener {
        void onReminderClick(ReminderRealm reminderRealm);
    }
}
