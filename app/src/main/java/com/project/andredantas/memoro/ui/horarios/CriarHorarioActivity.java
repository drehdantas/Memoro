package com.project.andredantas.memoro.ui.horarios;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.project.andredantas.memoro.App;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.dao.HorarioDAO;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CriarHorarioActivity extends AppCompatActivity {
    private String dia, tempo;
    private Horario horario;
    private static int selectedHour, selectedMinute;
    private Realm realm = Realm.getDefaultInstance();

    @Bind(R.id.horario_toolbar)
    Toolbar toolbar;
    @Bind(R.id.click_time)
    LinearLayout clickTime;
    @Bind(R.id.text_time)
    TextView textTime;
    @Bind(R.id.day_week)
    TextView dayWeek;
    @Bind(R.id.horario_titulo)
    EditText horarioTitulo;
    @Bind(R.id.horario_resumo)
    EditText horarioResumo;
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
            }
            if (extras.getLong("horario") != 0) {
                long horarioId = extras.getLong("horario");
                horario = HorarioDAO.getById(horarioId);
            }
        }
    }

    public void initView() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(horario != null ? "Editar Horário" : "Criar Horário");
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dayWeek.setText(dia);
        if (horario != null) {
            textTime.setText(horario.getTempo());
            horarioTitulo.setText(horario.getTitulo());
            horarioResumo.setText(horario.getDescricao());
            apagarHorario.setVisibility(View.VISIBLE);
            setTextWithCursosFinal(horarioTitulo);
            setTextWithCursosFinal(horarioResumo);
        } else {
            apagarHorario.setVisibility(View.GONE);
            selectedHour = new Time(System.currentTimeMillis()).getHours();
            selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);
            tempo = curTime;
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
                        CriarHorarioActivity.selectedHour = selectedHour;
                        CriarHorarioActivity.selectedMinute = selectedMinute;
                        tempo = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textTime.setText(tempo);
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
                if (horarioTitulo.getText().toString().equals("") || horarioTitulo.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), "Horário precisa de um título pelo menos", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (horario != null) {
                        //editar horario
                        horario = setDataHorario(horario);
                        HorarioDAO.updateHorario(realm, horario);
                        finish();
                        Toast.makeText(this, "Horario atualizado com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        //criar um horario
                        horario = new Horario();
                        horario = setDataHorario(horario);
                        HorarioDAO.saveHorario(realm, horario);
                        finish();
                        Toast.makeText(this, "Horario criado com sucesso", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.apagar_horario)
    public void apagarHorario(){
        HorarioDAO.deleteHorario(realm, horario);
        finish();
        Toast.makeText(this, "Horario apagado com sucesso", Toast.LENGTH_LONG).show();
    }

    public Horario setDataHorario(Horario horario) {
        realm.beginTransaction();
        horario.setTitulo(horarioTitulo.getText().toString());
        horario.setDescricao(horarioResumo.getText().toString());
        horario.setDia(dia);

        horario.setHora(selectedHour);
        horario.setMinutos(selectedMinute);
        horario.setTempo(tempo);

        horario.setId(System.currentTimeMillis());
        realm.commitTransaction();

        return horario;
    }

    public static void setTextWithCursosFinal(EditText edittext) {
        //set cursos no final
        int position = edittext.length();
        Editable etext = edittext.getText();
        Selection.setSelection(etext, position);
    }

}
