package com.project.andredantas.memoro.ui.lembretes;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;
import com.project.andredantas.memoro.model.dao.HorarioDAO;
import com.project.andredantas.memoro.model.dao.LembreteDAO;
import com.project.andredantas.memoro.utils.AudioRecorder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CriarLembreteActivity extends AppCompatActivity {
    private String tipo;
    private Lembrete lembrete;

    @Bind(R.id.lembrete_toolbar)
    Toolbar toolbar;
    @Bind(R.id.lembrete_titulo)
    TextView lembreteTitulo;
    @Bind(R.id.lembrete_resumo)
    TextView lembreteResumo;
    @Bind(R.id.lembrete_imagem)
    ImageView lembreteImagem;
    @Bind(R.id.lembrete_imagem_layout)
    RelativeLayout lembreteImageLayout;
    @Bind(R.id.voice_recorder_layout)
    RelativeLayout voiceRecorderLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_lembrete);
        ButterKnife.bind(this);
        onIntentReceived();
        initView();
    }

    public void initView(){
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(lembrete != null ? "Editar Lembrete" : "Criar Lembrete");
        }

        if (tipo.equals("imagem")){
            voiceRecorderLayout.setVisibility(View.GONE);
            lembreteImageLayout.setVisibility(View.VISIBLE);
        }else if (tipo.equals("voz")){
            voiceRecorderLayout.setVisibility(View.VISIBLE);
            lembreteImageLayout.setVisibility(View.GONE);
        }else if (tipo.equals("texto")){
            voiceRecorderLayout.setVisibility(View.GONE);
            lembreteImageLayout.setVisibility(View.GONE);
        }
    }

    public void onIntentReceived(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("tipo") != null) {
                tipo = extras.getString("tipo");
            }
            if (extras.getLong("lembrete") != 0) {
                long lembreteId = extras.getLong("lembrete");
                lembrete = LembreteDAO.getById(lembreteId);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_horario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_check:
                if (lembreteTitulo.getText().toString().equals("") || lembreteTitulo.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), "Lembrete precisa de um título pelo menos", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (lembrete != null) {
                        //editar horario
//                        horario = setDataHorario(horario);
//                        HorarioDAO.updateHorario(realm, horario);
//                        finish();
//                        Toast.makeText(this, "Horario atualizado com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        //criar um horario
//                        horario = new Horario();
//                        horario = setDataHorario(horario);
//                        HorarioDAO.saveHorario(realm, horario);
//                        finish();
//                        Toast.makeText(this, "Horario criado com sucesso", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
