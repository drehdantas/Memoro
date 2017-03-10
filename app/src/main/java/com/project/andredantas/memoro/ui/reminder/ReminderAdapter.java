package com.project.andredantas.memoro.ui.reminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Reminder;

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
    private List<Reminder> reminderList = Collections.EMPTY_LIST;

    public ReminderAdapter(Context context, List<Reminder> reminderList, OnReminderClickListener listener) {
        if(context == null)
            return;

        this.listener = listener;
        this.context = context;
        this.reminderList = reminderList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_reminder_recycler_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.reminderTitle.setText(reminder.getTitle());
        holder.reminderDescript.setText(reminder.getTime() + " - " + reminder.getDayAlarm() + "/" + reminder.getMonthAlarm());
        if (reminder.getType().equals("text")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_text_format_white_24dp));
        }else if (reminder.getType().equals("image")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_image_white_24dp));
        }else if (reminder.getType().equals("voice")){
            holder.reminderImage.setImageDrawable(context.getDrawable(R.drawable.ic_mic_white_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return reminderList == null ? 0 : reminderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.reminder_title)
        TextView reminderTitle;
        @Bind(R.id.reminderDescript)
        TextView reminderDescript;
        @Bind(R.id.reminder_image_type)
        ImageView reminderImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.reminder_card)
        public void clickReminder() {
            listener.onReminderClick(reminderList.get(getAdapterPosition()));
        }
    }

    public interface OnReminderClickListener {
        void onReminderClick(Reminder reminder);
    }
}
