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
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchedulesFragment extends Fragment implements SchedulesAdapter.OnScheduleClickListener {
    public String MON;
    public String TUE;
    public String WED;
    public String THU;
    public String FRI;
    public String SAT;
    public String SUN;

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

        MON = getActivity().getString(R.string.monday);
        TUE = getActivity().getString(R.string.tuesday);
        WED = getActivity().getString(R.string.wednesday);
        THU = getActivity().getString(R.string.thursday);
        FRI = getActivity().getString(R.string.friday);
        SAT = getActivity().getString(R.string.saturday);
        SUN = getActivity().getString(R.string.sunday);

        return view;
    }

    @Override
    public void onScheduleClick(ScheduleRealm scheduleRealm, String day) {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("scheduleRealm", scheduleRealm.getId());
        intent.putExtra("day", day);
        startActivity(intent);
    }

    @OnClick(R.id.create_mon)
    public void onCreateScheduleMonClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", MON);
        startActivity(intent);
    }

    @OnClick(R.id.create_tue)
    public void onCreateScheduleTueClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", TUE);
        startActivity(intent);
    }

    @OnClick(R.id.create_wed)
    public void onCreateScheduleWedClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", WED);
        startActivity(intent);
    }

    @OnClick(R.id.create_thu)
    public void onCreateScheduleThuClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", THU);
        startActivity(intent);
    }

    @OnClick(R.id.create_fri)
    public void onCreateScheduleFriClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", FRI);
        startActivity(intent);
    }

    @OnClick(R.id.create_sat)
    public void onCreateScheduleSatClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", SAT);
        startActivity(intent);
    }

    @OnClick(R.id.create_sun)
    public void onCreateScheduleSunClick() {
        Intent intent = new Intent(getActivity(), CreateSchedulesActivity.class);
        intent.putExtra("day", SUN);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        List<ScheduleRealm> listSchedulesMon = ScheduleDAO.listSchedules(MON);
        scheduleMon.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesMon, MON, this));
        scheduleMon.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesTue = ScheduleDAO.listSchedules(TUE);
        scheduleTue.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesTue, TUE, this));
        scheduleTue.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesWed = ScheduleDAO.listSchedules(WED);
        scheduleWed.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesWed, WED, this));
        scheduleWed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesThu = ScheduleDAO.listSchedules(THU);
        scheduleThu.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesThu, THU, this));
        scheduleThu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesFri = ScheduleDAO.listSchedules(FRI);
        scheduleFri.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesFri, FRI, this));
        scheduleFri.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesSat = ScheduleDAO.listSchedules(SAT);
        scheduleSat.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesSat, SAT, this));
        scheduleSat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        List<ScheduleRealm> listSchedulesSun = ScheduleDAO.listSchedules(SUN);
        scheduleSun.setAdapter(new SchedulesAdapter(getActivity(), listSchedulesSun, SUN, this));
        scheduleSun.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }
}
