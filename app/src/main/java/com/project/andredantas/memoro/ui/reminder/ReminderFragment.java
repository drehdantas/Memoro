package com.project.andredantas.memoro.ui.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.andredantas.memoro.ui.MainActivity;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Reminder;
import com.project.andredantas.memoro.model.dao.ReminderDAO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReminderFragment extends Fragment implements ReminderAdapter.OnReminderClickListener {
    @Bind(R.id.reminder_recycle)
    RecyclerView reminderRecycle;

    public List<Reminder> listReminder = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onReminderClick(Reminder reminder) {
        Intent intent = new Intent(getActivity(), CreateReminderActivity.class);
        intent.putExtra("reminder", reminder.getId());
        intent.putExtra("type", reminder.getType());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).closeMenu();
        listReminder = ReminderDAO.listReminders();
        reminderRecycle.setAdapter(new ReminderAdapter(getActivity(), listReminder, this));
        reminderRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
