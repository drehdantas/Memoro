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
import com.project.andredantas.memoro.model.dao.HorarioDAO;
import com.project.andredantas.memoro.ui.lembretes.CriarLembreteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


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

    private List<Horario> listHorariosSeg = new ArrayList<>();
    private List<Horario> listHorariosTer = new ArrayList<>();
    private List<Horario> listHorariosQua = new ArrayList<>();
    private List<Horario> listHorariosQui = new ArrayList<>();
    private List<Horario> listHorariosSex = new ArrayList<>();
    private List<Horario> listHorariosSab = new ArrayList<>();
    private List<Horario> listHorariosDom = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horarios, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onHorarioClick(Horario horario, String dia) {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("horario", horario.getId());
        intent.putExtra("dia", dia);
        startActivity(intent);
    }

    @OnClick(R.id.criar_seg)
    public void onCriarHorarioSegClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.SEG);
        startActivity(intent);
    }

    @OnClick(R.id.criar_ter)
    public void onCriarHorarioTerClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.TER);
        startActivity(intent);
    }

    @OnClick(R.id.criar_qua)
    public void onCriarHorarioQuaClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.QUA);
        startActivity(intent);
    }

    @OnClick(R.id.criar_qui)
    public void onCriarHorarioQuiClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.QUI);
        startActivity(intent);
    }

    @OnClick(R.id.criar_sex)
    public void onCriarHorarioSexClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.SEX);
        startActivity(intent);
    }

    @OnClick(R.id.criar_sab)
    public void onCriarHorarioSabClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.SAB);
        startActivity(intent);
    }

    @OnClick(R.id.criar_dom)
    public void onCriarHorarioDomClick() {
        Intent intent = new Intent(getActivity(), CriarHorarioActivity.class);
        intent.putExtra("dia", HorariosAdapter.DOM);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        listHorariosSeg = HorarioDAO.listHorarios(HorariosAdapter.SEG);
        horariosSeg.setAdapter(new HorariosAdapter(getActivity(), listHorariosSeg, HorariosAdapter.SEG, this));
        horariosSeg.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosTer = HorarioDAO.listHorarios(HorariosAdapter.TER);
        horariosTer.setAdapter(new HorariosAdapter(getActivity(), listHorariosTer, HorariosAdapter.TER, this));
        horariosTer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosQua = HorarioDAO.listHorarios(HorariosAdapter.QUA);
        horariosQua.setAdapter(new HorariosAdapter(getActivity(), listHorariosQua, HorariosAdapter.QUA, this));
        horariosQua.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosQui = HorarioDAO.listHorarios(HorariosAdapter.QUI);
        horariosQui.setAdapter(new HorariosAdapter(getActivity(), listHorariosQui, HorariosAdapter.QUI, this));
        horariosQui.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosSex = HorarioDAO.listHorarios(HorariosAdapter.SEX);
        horariosSex.setAdapter(new HorariosAdapter(getActivity(), listHorariosSex, HorariosAdapter.SEX, this));
        horariosSex.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosSab = HorarioDAO.listHorarios(HorariosAdapter.SAB);
        horariosSab.setAdapter(new HorariosAdapter(getActivity(), listHorariosSab, HorariosAdapter.SAB, this));
        horariosSab.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        listHorariosDom = HorarioDAO.listHorarios(HorariosAdapter.DOM);
        horariosDom.setAdapter(new HorariosAdapter(getActivity(), listHorariosDom, HorariosAdapter.DOM, this));
        horariosDom.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }
}
