package com.project.andredantas.memoro.ui.schedules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchedulesFragment extends Fragment implements SchedulesAdapter.OnScheduleClickListener {
    public String mon, tue, wed, thu, fri, sat, sun;

    @Bind(R.id.schedule_mon)
    RecyclerView scheduleMon;
    @Bind(R.id.schedule_tue)
    RecyclerView scheduleTue;
    @Bind(R.id.schedule_wed)
    RecyclerView scheduleWed;
    @Bind(R.id.schedule_thu)
    RecyclerView scheduleThu;
    @Bind(R.id.schedule_fri)
    RecyclerView scheduleFri;
    @Bind(R.id.schedule_sat)
    RecyclerView scheduleSat;
    @Bind(R.id.schedule_sun)
    RecyclerView scheduleSun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedules, container, false);
        ButterKnife.bind(this, view);

        mon = getActivity().getString(R.string.monday);
        tue = getActivity().getString(R.string.tuesday);
        wed = getActivity().getString(R.string.wednesday);
        thu = getActivity().getString(R.string.thursday);
        fri = getActivity().getString(R.string.friday);
        sat = getActivity().getString(R.string.saturday);
        sun = getActivity().getString(R.string.sunday);

        return view;
    }

    @Override
    public void onScheduleClick(ScheduleRealm scheduleRealm, int day) {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("scheduleRealm", scheduleRealm.getId());
        intent.putExtra("day", day);
        startActivity(intent);
    }

    @OnClick(R.id.create_mon)
    public void onCreateScheduleMonClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.MONDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_tue)
    public void onCreateScheduleTueClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.TUESDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_wed)
    public void onCreateScheduleWedClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.WEDNESDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_thu)
    public void onCreateScheduleThuClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.THURSDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_fri)
    public void onCreateScheduleFriClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.FRIDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_sat)
    public void onCreateScheduleSatClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.SATURDAY);
        startActivity(intent);
    }

    @OnClick(R.id.create_sun)
    public void onCreateScheduleSunClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", Schedule.SUNDAY);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        List<ScheduleRealm> listSchedulesMon = ScheduleDAO.listSchedules(Schedule.MONDAY);
        scheduleMon.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesMon, mon, this));
        scheduleMon.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesTue = ScheduleDAO.listSchedules(Schedule.TUESDAY);
        scheduleTue.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesTue, tue, this));
        scheduleTue.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesWed = ScheduleDAO.listSchedules(Schedule.WEDNESDAY);
        scheduleWed.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesWed, wed, this));
        scheduleWed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesThu = ScheduleDAO.listSchedules(Schedule.THURSDAY);
        scheduleThu.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesThu, thu, this));
        scheduleThu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesFri = ScheduleDAO.listSchedules(Schedule.FRIDAY);
        scheduleFri.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesFri, fri, this));
        scheduleFri.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesSat = ScheduleDAO.listSchedules(Schedule.SATURDAY);
        scheduleSat.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesSat, sat, this));
        scheduleSat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesSun = ScheduleDAO.listSchedules(Schedule.SUNDAY);
        scheduleSun.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesSun, sun, this));
        scheduleSun.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }
}
