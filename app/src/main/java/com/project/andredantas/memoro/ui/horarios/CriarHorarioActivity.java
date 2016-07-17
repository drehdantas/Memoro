package com.project.andredantas.memoro.ui.horarios;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.reflect.TypeToken;
import com.project.andredantas.memoro.App;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CriarHorarioActivity extends AppCompatActivity {
    private String dia;
    private Horario horario;

    @Bind(R.id.horario_toolbar)
    Toolbar toolbar;
    @Bind(R.id.click_time)
    LinearLayout clickTime;
    @Bind(R.id.text_time)
    TextView textTime;
    @Bind(R.id.day_week)
    TextView dayWeek;
    @Bind(R.id.horario_titulo)
    TextView horarioTitulo;
    @Bind(R.id.horario_resumo)
    TextView horarioResumo;
    @Bind(R.id.apagar_horario)
    Button apagarHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_horario);
        ButterKnife.bind(this);
        onIntentReceived();
        initView();
    }

    private void onIntentReceived() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("dia") != null) {
                dia = extras.getString("dia");
            } if(extras.getString("horario") != null){
                String jsonHorario = extras.getString("horario");
                horario = App.gsonInstance().fromJson(jsonHorario, Horario.class);
            }
        }
    }

    public void initView(){
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(horario!= null? "Editar Horário" : "Criar Horário");
        }

        dayWeek.setText(dia);
        if (horario != null){
            textTime.setText(horario.getHora() + ":" + horario.getMinutos());
            horarioTitulo.setText(horario.getTitulo());
            horarioResumo.setText(horario.getDescricao());
            apagarHorario.setVisibility(View.VISIBLE);
        }else{
            apagarHorario.setVisibility(View.GONE);
            int selectedHour =  new Time(System.currentTimeMillis()).getHours();
            int selectedMinute =  new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);
        }

        clickTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CriarHorarioActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione a hora");
                mTimePicker.show();

            }
        });
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

//                if (horario)
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
