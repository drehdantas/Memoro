package com.project.andredantas.memoro.ui.horarios;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre Dantas on 7/13/16.
 */
public class HorariosAdapter extends RecyclerView.Adapter<HorariosAdapter.MyViewHolder>{
    public static String SEG = "Segunda Feira";
    public static String TER = "Terca Feira";
    public static String QUA = "Quarta Feira";
    public static String QUI = "Quinta Feira";
    public static String SEX = "Sexta Feira";
    public static String SAB = "Sabado";
    public static String DOM = "Domingo";

    private String dia;
    private LayoutInflater layoutInflater;
    private OnHorarioClickListener listener;
    private List<Horario> horarioList = Collections.EMPTY_LIST;

    public HorariosAdapter(Context context, List<Horario> horarioList, String dia, OnHorarioClickListener listener) {
        if(context == null)
            return;
        this.listener = listener;
        this.horarioList = horarioList;
        this.dia = dia;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_horario_recycler_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Horario horario = horarioList.get(position);
        holder.horarioTitulo.setText(horario.getTitulo());
        holder.horarioHora.setText(horario.getTempo());
    }

    @Override
    public int getItemCount() {
        return horarioList == null ? 0 : horarioList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.horario_titulo)
        TextView horarioTitulo;
        @Bind(R.id.horario_hora)
        TextView horarioHora;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.horario_card)
        public void onClickImageTrending() {
            listener.onHorarioClick(horarioList.get(getAdapterPosition()), dia);
        }
    }

    public interface OnHorarioClickListener {
        void onHorarioClick(Horario horario, String dia);
    }
}
