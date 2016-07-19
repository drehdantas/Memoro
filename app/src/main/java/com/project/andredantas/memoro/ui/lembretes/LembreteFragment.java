package com.project.andredantas.memoro.ui.lembretes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.andredantas.memoro.App;
import com.project.andredantas.memoro.MainActivity;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;
import com.project.andredantas.memoro.model.dao.LembreteDAO;
import com.project.andredantas.memoro.ui.horarios.HorariosAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LembreteFragment extends Fragment implements LembretesAdapter.OnLembreteClickListener {
    @Bind(R.id.lembretes_recycle)
    RecyclerView lembretesRecycle;

    public List<Lembrete> listLembrete = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lembrete, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onLembreteClick(Lembrete lembrete) {
        Intent intent = new Intent(getActivity(), CriarLembreteActivity.class);
        intent.putExtra("lembrete", lembrete.getId());
        intent.putExtra("tipo", lembrete.getType());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).closeMenu();
        listLembrete = LembreteDAO.listLembretes();
        lembretesRecycle.setAdapter(new LembretesAdapter(getActivity(), listLembrete, this));
        lembretesRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
