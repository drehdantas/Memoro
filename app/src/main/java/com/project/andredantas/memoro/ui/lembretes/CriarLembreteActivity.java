package com.project.andredantas.memoro.ui.lembretes;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;
import com.project.andredantas.memoro.model.dao.HorarioDAO;
import com.project.andredantas.memoro.model.dao.LembreteDAO;
import com.project.andredantas.memoro.ui.horarios.CriarHorarioActivity;
import com.project.andredantas.memoro.utils.DialogDateFragment;
import com.project.andredantas.memoro.utils.DialogDatePickerListener;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CriarLembreteActivity extends AppCompatActivity implements DialogDatePickerListener{
    private static int selectedHour, selectedMinute;
    Calendar myCalendar = Calendar.getInstance();

    private String tipo, tempo, dia, mes;
    private Lembrete lembrete;
    private List<Horario> listHorarios;
    private Horario horarioEscolhido;
    private ArrayList<String> listHorariosTitulos = new ArrayList<>();

    private Realm realm = Realm.getDefaultInstance();

    @Bind(R.id.lembrete_toolbar)
    Toolbar toolbar;
    @Bind(R.id.lembrete_titulo)
    EditText lembreteTitulo;
    @Bind(R.id.lembrete_resumo)
    EditText lembreteResumo;
    @Bind(R.id.lembrete_imagem)
    ImageView lembreteImagem;
    @Bind(R.id.lembrete_imagem_layout)
    RelativeLayout lembreteImageLayout;
    @Bind(R.id.horarios_spinner)
    MaterialSpinner horariosSpinner;
    @Bind(R.id.click_time)
    LinearLayout clickTime;
    @Bind(R.id.click_data)
    LinearLayout clickData;
    @Bind(R.id.data_time)
    TextView dataTime;
    @Bind(R.id.text_time)
    TextView textTime;
    @Bind(R.id.apagar_lembrete)
    Button apagarLembrete;

    @Bind(R.id.voice_recorder_layout) RelativeLayout voiceRecorderLayout;
    @Bind(R.id.activity_criar_lembrete_audio_layout) AudioRecordLayout audioRecordLayout;

    boolean notDeleteFile = true;

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

        listHorarios = HorarioDAO.listTodosHorarios();
        if (listHorarios != null){
            for (int i = 0; i < listHorarios.size(); i++) {
                listHorariosTitulos.add(listHorarios.get(i).getTitulo());
            }
        }

        horariosSpinner.setItems(listHorariosTitulos);
        horariosSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                for (int i = 0; i < listHorariosTitulos.size(); i++) {
                    if (listHorariosTitulos.get(i).equals(item)){
                        horarioEscolhido = listHorarios.get(i);
                    }
                }
            }

        });
        clickTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CriarLembreteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        CriarLembreteActivity.selectedHour = selectedHour;
                        CriarLembreteActivity.selectedMinute = selectedMinute;
                        tempo = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textTime.setText(tempo);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione a hora");
                mTimePicker.show();

            }
        });

        if (lembrete != null){
            textTime.setText(lembrete.getTempo());
            lembreteTitulo.setText(lembrete.getTitulo());
            lembreteResumo.setText(lembrete.getDescricao());
            for (int i = 0; i < listHorariosTitulos.size(); i++) {
                if (lembrete.getHorarioRelacionado() == listHorarios.get(i).getId()){
                    horariosSpinner.setSelectedIndex(i);
                }
            }
            apagarLembrete.setVisibility(View.VISIBLE);
            CriarHorarioActivity.setTextWithCursosFinal(lembreteTitulo);
            CriarHorarioActivity.setTextWithCursosFinal(lembreteResumo);
        }else{
            apagarLembrete.setVisibility(View.GONE);
            selectedHour = new Time(System.currentTimeMillis()).getHours();
            selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);
            setData(myCalendar);
        }

    }

    @OnClick(R.id.apagar_lembrete)
    public void apagarHorario(){
        LembreteDAO.deleteLembrete(realm, lembrete);
        finish();
        Toast.makeText(this, "Lembrete apagado com sucesso", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.click_data)
    public void onClickData(){
        FragmentManager fm = getSupportFragmentManager();
        DialogDateFragment popup = new DialogDateFragment();
        popup.setListenerDatePicker(CriarLembreteActivity.this, myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH) + 1) + "/" + myCalendar.get(Calendar.YEAR));
        popup.show(fm, "datanascimento");
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
                if(notDeleteFile){
                    audioRecordLayout.getAudioRecorder().deleteFiles();
                }
                onBackPressed();
                break;
            case R.id.action_check:
                notDeleteFile = false;

                if (lembreteTitulo.getText().toString().equals("") || lembreteTitulo.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), "Lembrete precisa de um título pelo menos", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (lembrete != null) {
                        //editar lembrete
                        lembrete = setDataLembrete(lembrete);
                        LembreteDAO.updateLembrete(realm, lembrete);
                        finish();
                        Toast.makeText(this, "Lembrete atualizado com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        //criar um lembrete
                        lembrete = new Lembrete();
                        lembrete = setDataLembrete(lembrete);
                        LembreteDAO.saveLembrete(realm, lembrete);
                        finish();
                        Toast.makeText(this, "Lembrete criado com sucesso", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Lembrete setDataLembrete(Lembrete lembrete) {
        realm.beginTransaction();
        lembrete.setTitulo(lembreteTitulo.getText().toString());
        lembrete.setDescricao(lembreteResumo.getText().toString());
        lembrete.setHorarioRelacionado(horarioEscolhido != null ? horarioEscolhido.getId(): 1);

        lembrete.setHora(selectedHour);
        lembrete.setMinutos(selectedMinute);
        lembrete.setTempo(String.format("%02d:%02d", selectedHour, selectedMinute));
        lembrete.setDiaAlarme(dia);
        lembrete.setMesAlarme(mes);

        lembrete.setType(tipo);
        lembrete.setId(System.currentTimeMillis());
        realm.commitTransaction();

        return lembrete;
    }

    @Override
    public void onFinishPopupDataHorario(Calendar c) {
        setData(c);
    }

    public void setData(Calendar c){
        dia = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf((c.get(Calendar.MONTH) + 1));
//        String ano = String.valueOf(c.get(Calendar.YEAR));

        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            dia = '0' + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        }
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            mes = '0' + String.valueOf((c.get(Calendar.MONTH) + 1));
        }

        dataTime.setText(dia + "/" + mes);
    }
}
