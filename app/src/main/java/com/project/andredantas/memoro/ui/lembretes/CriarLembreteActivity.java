package com.project.andredantas.memoro.ui.lembretes;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioRecord;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Horario;
import com.project.andredantas.memoro.model.Lembrete;
import com.project.andredantas.memoro.model.dao.HorarioDAO;
import com.project.andredantas.memoro.model.dao.LembreteDAO;
import com.project.andredantas.memoro.ui.horarios.CriarHorarioActivity;
import com.project.andredantas.memoro.utils.Constants;
import com.project.andredantas.memoro.utils.DialogDateFragment;
import com.project.andredantas.memoro.utils.DialogDatePickerListener;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;
import com.project.andredantas.memoro.utils.audio.AudioRecorder;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CriarLembreteActivity extends AppCompatActivity implements DialogDatePickerListener {
    private static int REQUEST_PICTURE = 1;
    private static int CAMERA_REQUEST_IMAGE_CAPTURE = 2;
    private static int selectedHour, selectedMinute;

    Calendar myCalendar = Calendar.getInstance();
    private String tipo, tempo, dia, mes;
    private Lembrete lembrete;
    private List<Horario> listHorarios;
    private Horario horarioEscolhido;
    private ArrayList<String> listHorariosTitulos = new ArrayList<>();
    private Bitmap mImage;
    private String fileImage;
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

    @Bind(R.id.voice_recorder_layout)
    RelativeLayout voiceRecorderLayout;
    @Bind(R.id.activity_criar_lembrete_audio_layout)
    AudioRecordLayout audioRecordLayout;

    boolean notDeleteFile = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_lembrete);
        ButterKnife.bind(this);
        onIntentReceived();
        initView();
    }

    public void initView() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(lembrete != null ? "Editar Lembrete" : "Criar Lembrete");
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (tipo.equals("imagem")) {
            voiceRecorderLayout.setVisibility(View.GONE);
            lembreteImageLayout.setVisibility(View.VISIBLE);
        } else if (tipo.equals("voz")) {
            voiceRecorderLayout.setVisibility(View.VISIBLE);
            lembreteImageLayout.setVisibility(View.GONE);
        } else if (tipo.equals("texto")) {
            voiceRecorderLayout.setVisibility(View.GONE);
            lembreteImageLayout.setVisibility(View.GONE);
        }

        listHorarios = HorarioDAO.listTodosHorarios();
        if (listHorarios != null) {
            for (int i = 0; i < listHorarios.size(); i++) {
                listHorariosTitulos.add(listHorarios.get(i).getTitulo());
            }
        }

        horariosSpinner.setItems(listHorariosTitulos);
        horariosSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                for (int i = 0; i < listHorariosTitulos.size(); i++) {
                    if (listHorariosTitulos.get(i).equals(item)) {
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

        if (lembrete != null) {
            textTime.setText(lembrete.getTempo());
            lembreteTitulo.setText(lembrete.getTitulo());
            lembreteResumo.setText(lembrete.getDescricao());
            for (int i = 0; i < listHorariosTitulos.size(); i++) {
                if (lembrete.getHorarioRelacionado() == listHorarios.get(i).getId()) {
                    horariosSpinner.setSelectedIndex(i);
                }
            }
            audioRecordLayout.setFilePath(lembrete.getAudio());
            if (lembrete.getImagem() != null) {
                lembreteImagem.setVisibility(View.VISIBLE);
                lembreteImageLayout.setVisibility(View.GONE);
                File filePath = new File(lembrete.getImagem());

                Picasso.with(this)
                        .load(filePath)
                        .into(lembreteImagem);
            }
            apagarLembrete.setVisibility(View.VISIBLE);
            CriarHorarioActivity.setTextWithCursosFinal(lembreteTitulo);
            CriarHorarioActivity.setTextWithCursosFinal(lembreteResumo);
        } else {
            apagarLembrete.setVisibility(View.GONE);
            selectedHour = new Time(System.currentTimeMillis()).getHours();
            selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);
            setData(myCalendar);
        }

        audioRecordLayout.prepareForPlay(lembrete != null);


    }

    @OnClick(R.id.apagar_lembrete)
    public void apagarHorario() {
        LembreteDAO.deleteLembrete(realm, lembrete);
        finish();
        Toast.makeText(this, "Lembrete apagado com sucesso", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.click_data)
    public void onClickData() {
        FragmentManager fm = getSupportFragmentManager();
        DialogDateFragment popup = new DialogDateFragment();
        popup.setListenerDatePicker(CriarLembreteActivity.this, myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (myCalendar.get(Calendar.MONTH) + 1) + "/" + myCalendar.get(Calendar.YEAR));
        popup.show(fm, "datanascimento");
    }

    @OnClick(R.id.click_galeria)
    public void pickClickGaleria() {
        new TedPermission(this)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        final Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_PICTURE);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @OnClick(R.id.click_camera)
    public void pickClickCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_IMAGE_CAPTURE);
    }


    public void onIntentReceived() {
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
                if (notDeleteFile) {
                    audioRecordLayout.getAudioRecorder().deleteFiles();
                }
                onBackPressed();
                break;
            case R.id.action_check:
                notDeleteFile = false;

                if (lembreteTitulo.getText().toString().equals("") || lembreteTitulo.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), "Lembrete precisa de um título pelo menos", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (tipo.equals("voz")) {
                        if (!audioRecordLayout.isStopped()) {
                            Snackbar.make(this.findViewById(android.R.id.content), "Pause antes de salvar", Snackbar.LENGTH_SHORT).show();
                        } else {
                            editOrSave();
                        }
                    } else {
                        editOrSave();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editOrSave() {
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

    public Lembrete setDataLembrete(Lembrete lembrete) {
        realm.beginTransaction();
        lembrete.setTitulo(lembreteTitulo.getText().toString());
        lembrete.setDescricao(lembreteResumo.getText().toString());
        lembrete.setHorarioRelacionado(horarioEscolhido != null ? horarioEscolhido.getId() : 1);

        lembrete.setHora(selectedHour);
        lembrete.setMinutos(selectedMinute);
        lembrete.setTempo(String.format("%02d:%02d", selectedHour, selectedMinute));
        lembrete.setDiaAlarme(dia);
        lembrete.setMesAlarme(mes);

        if (audioRecordLayout.isStopped()) {
            lembrete.setAudio(audioRecordLayout.getFilePath());
        }
        if (fileImage != null) {
            lembrete.setImagem(fileImage);
        }

        lembrete.setType(tipo);
        lembrete.setId(System.currentTimeMillis());
        realm.commitTransaction();

        return lembrete;
    }

    @Override
    public void onFinishPopupDataHorario(Calendar c) {
        setData(c);
    }

    public void setData(Calendar c) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        File croppedImageFile = new File(getFilesDir(), "tmp.jpg");
        if ((requestCode == REQUEST_PICTURE) && (resultCode == RESULT_OK)) {
            Uri croppedImage = Uri.fromFile(croppedImageFile);
            new Crop(data.getData()).withAspect(300, 300).output(croppedImage).start(this);
        } else if ((requestCode == CAMERA_REQUEST_IMAGE_CAPTURE) && (resultCode == RESULT_OK)) {
            mImage = (Bitmap) data.getExtras().get("data");
            bitMapImage();
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            try {
                mImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(croppedImageFile));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitMapImage();
        }
    }

    public void bitMapImage() {
        File file = null;

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            file = new File(Environment.getExternalStorageDirectory() + "/memoro/image");

            if (!file.exists()) {
                file.mkdir();
            }

            mImage.compress(Bitmap.CompressFormat.JPEG, 85, bytes);
            file = new File(file.getAbsolutePath(), new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date()) + Constants.IMG_EXT);
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.flush();
            fo.close();

            fileImage = file.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lembreteImagem.setVisibility(View.VISIBLE);
        lembreteImageLayout.setVisibility(View.GONE);
        Picasso.with(this)
                .load(file)
                .into(lembreteImagem);
    }
}
