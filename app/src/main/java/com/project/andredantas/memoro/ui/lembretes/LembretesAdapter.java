package com.project.andredantas.memoro.ui.lembretes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre Dantas on 7/13/16.
 */
public class LembretesAdapter extends RecyclerView.Adapter<LembretesAdapter.MyViewHolder>{
    private Context context;
    private LayoutInflater layoutInflater;
    private OnLembreteClickListener listener;
    private List<Lembrete> lembreteList = Collections.EMPTY_LIST;

    public LembretesAdapter(Context context, List<Lembrete> lembreteList, OnLembreteClickListener listener) {
        if(context == null)
            return;

        this.listener = listener;
        this.context = context;
        this.lembreteList = lembreteList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_lembrete_recycler_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lembreteList == null ? 0 : lembreteList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
//        @Bind(R.id.horario_titulo)
//        TextView horarioTitulo;
//        @Bind(R.id.horario_hora)
//        TextView horarioHora;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

//        @OnClick(R.id.horario_card)
//        public void onClickImageTrending() {
//            listener.onLembreteClick(lembreteList.get(getAdapterPosition()));
//            Toast.makeText(context, "clicou opa", Toast.LENGTH_LONG).show();
//        }
    }

    public interface OnLembreteClickListener {
        void onLembreteClick(Lembrete lembrete);
    }
}
