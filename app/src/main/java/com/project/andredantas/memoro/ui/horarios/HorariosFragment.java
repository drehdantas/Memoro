package com.project.andredantas.memoro.ui.horarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.andredantas.memoro.App;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.ui.lembretes.CriarLembreteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HorariosFragment extends Fragment implements HorariosAdapter.OnHorarioClickListener{
    @Bind(R.id.horarios_seg)
    RecyclerView horariosSeg;
    @Bind(R.id.horarios_ter)
    RecyclerView horariosTer;
    @Bind(R.id.horarios_qua)
    RecyclerView horariosQua;
    @Bind(R.id.horarios_qui)
    RecyclerView horariosQui;
    @Bind(R.id.horarios_sex)
    RecyclerView horariosSex;
    @Bind(R.id.horarios_sab)
    RecyclerView horariosSab;
    @Bind(R.id.horarios_dom)
    RecyclerView horariosDom;

    public List<Horario> listHorariosSeg = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horarios, container, false);
        ButterKnife.bind(this, view);

        Horario horario = new Horario();
        listHorariosSeg.add(horario);
        listHorariosSeg.add(horario);
        listHorariosSeg.add(horario);

        horariosSeg.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosSeg.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosTer.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosTer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosQua.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosQua.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosQui.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosQui.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosSex.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosSex.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosSab.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosSab.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        horariosDom.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, this));
        horariosDom.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    @Override
    public void onHorarioClick(Horario horario) {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("horario", App.gsonInstance().toJson(horario));
        startActivity(intent);
    }

    @Override
    public void onCriarHorarioClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        startActivity(intent);
    }
}
