package com.project.andredantas.memoro.ui.reminder;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
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
import com.project.andredantas.memoro.model.Reminder;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;
import com.project.andredantas.memoro.model.dao.ReminderDAO;
import com.project.andredantas.memoro.ui.schedules.CreateSchedulesActivity;
import com.project.andredantas.memoro.utils.Constants;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class CreateReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static int REQUEST_PICTURE = 1;
    private static int CAMERA_REQUEST_IMAGE_CAPTURE = 2;
    private static int selectedHour, selectedMinute;

    Calendar myCalendar = Calendar.getInstance();
    private String type, time;
    private int day, month;
    private Reminder reminder;
    private List<Schedule> listSchedules;
    private Schedule scheduleChosen;
    private ArrayList<String> listSheduleTitles = new ArrayList<>();
    private Bitmap mImage;
    private String fileImage;
    private Realm realm = Realm.getDefaultInstance();

    @Bind(R.id.reminder_toolbar)
    Toolbar toolbar;
    @Bind(R.id.reminder_title)
    EditText reminderTitle;
    @Bind(R.id.reminder_descript)
    EditText reminderDescript;
    @Bind(R.id.reminder_image)
    ImageView reminderImage;
    @Bind(R.id.reminder_image_layout)
    RelativeLayout reminderImageLayout;
    @Bind(R.id.schedules_spinner)
    MaterialSpinner schedulesSpinner;
    @Bind(R.id.click_time)
    LinearLayout clickTime;
    @Bind(R.id.click_data)
    LinearLayout clickData;
    @Bind(R.id.data_time)
    TextView dataTime;
    @Bind(R.id.text_time)
    TextView textTime;
    @Bind(R.id.delete_reminder)
    Button deleteReminder;

    @Bind(R.id.voice_recorder_layout)
    RelativeLayout voiceRecorderLayout;
    @Bind(R.id.activity_create_reminder_audio_layout)
    AudioRecordLayout audioRecordLayout;

    boolean notDeleteFile = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        ButterKnife.bind(this);
        onIntentReceived();
        initView();
    }

    public void initView() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(reminder != null ? getString(R.string.edit_reminder) : getString(R.string.create_reminder));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (type.equals("image")) {
            voiceRecorderLayout.setVisibility(View.GONE);
            reminderImageLayout.setVisibility(View.VISIBLE);
        } else if (type.equals("voice")) {
            voiceRecorderLayout.setVisibility(View.VISIBLE);
            reminderImageLayout.setVisibility(View.GONE);
        } else if (type.equals("text")) {
            voiceRecorderLayout.setVisibility(View.GONE);
            reminderImageLayout.setVisibility(View.GONE);
        }

        listSchedules = ScheduleDAO.listSchedules();
        if (listSchedules != null) {
            for (int i = 0; i < listSchedules.size(); i++) {
                listSheduleTitles.add(listSchedules.get(i).getTitle());
            }
        }

        schedulesSpinner.setItems(listSheduleTitles);
        schedulesSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                for (int i = 0; i < listSheduleTitles.size(); i++) {
                    if (listSheduleTitles.get(i).equals(item)) {
                        scheduleChosen = listSchedules.get(i);
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
                mTimePicker = new TimePickerDialog(CreateReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        CreateReminderActivity.selectedHour = selectedHour;
                        CreateReminderActivity.selectedMinute = selectedMinute;
                        time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textTime.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_hour));
                mTimePicker.show();

            }
        });

        if (reminder != null) {
            textTime.setText(reminder.getTime());
            reminderTitle.setText(reminder.getTitle());
            reminderDescript.setText(reminder.getDescript());
            for (int i = 0; i < listSheduleTitles.size(); i++) {
                if (reminder.getScheduleRelated() == listSchedules.get(i).getId()) {
                    schedulesSpinner.setSelectedIndex(i);
                }
            }
            audioRecordLayout.setFilePath(reminder.getAudio());
            if (reminder.getImage() != null) {
                reminderImage.setVisibility(View.VISIBLE);
                reminderImageLayout.setVisibility(View.GONE);
                File filePath = new File(reminder.getImage());

                Picasso.with(this)
                        .load(filePath)
                        .into(reminderImage);
            }
            deleteReminder.setVisibility(View.VISIBLE);
            CreateSchedulesActivity.setTextWithCursorFinal(reminderTitle);
            CreateSchedulesActivity.setTextWithCursorFinal(reminderDescript);
        } else {
            deleteReminder.setVisibility(View.GONE);
            selectedHour = new Time(System.currentTimeMillis()).getHours();
            selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);

            day = myCalendar.get(Calendar.DAY_OF_MONTH);
            month = myCalendar.get(Calendar.MONTH) + 1;
            setDataTime(day, month);
        }

        audioRecordLayout.prepareForPlay(reminder != null);


    }

    @OnClick(R.id.delete_reminder)
    public void deleteReminder() {
        ReminderDAO.deleteReminder(realm, reminder);
        finish();
        Toast.makeText(this, getString(R.string.reminder_deleted_message), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.click_data)
    public void onClickData() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CreateReminderActivity.this,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(R.id.click_galeria)
    public void pickClickGallery() {
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
            if (extras.getString("type") != null) {
                type = extras.getString("type");
            }
            if (extras.getLong("reminder") != 0) {
                long lembreteId = extras.getLong("reminder");
                reminder = ReminderDAO.getById(lembreteId);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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

                if (reminderTitle.getText().toString().equals("") || reminderTitle.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), "Reminder need a title", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (type.equals("voice")) {
                        if (!audioRecordLayout.isStopped()) {
                            Snackbar.make(this.findViewById(android.R.id.content), "Pause before saving", Snackbar.LENGTH_SHORT).show();
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
        if (reminder != null) {
            //edit reminder
            reminder = setDataReminder(reminder);
            ReminderDAO.updateReminder(realm, reminder);
            finish();
            Toast.makeText(this, getString(R.string.reminder_update_message), Toast.LENGTH_LONG).show();
        } else {
            //create a reminder
            reminder = new Reminder();
            reminder = setDataReminder(reminder);
            ReminderDAO.saveReminder(realm, reminder);
            finish();
            Toast.makeText(this, getString(R.string.reminder_create_message), Toast.LENGTH_LONG).show();
        }
    }

    public Reminder setDataReminder(Reminder reminder) {
        realm.beginTransaction();
        reminder.setTitle(reminderTitle.getText().toString());
        reminder.setDescript(reminderDescript.getText().toString());
        reminder.setScheduleRelated(scheduleChosen != null ? scheduleChosen.getId() : 1);

        reminder.setHour(selectedHour);
        reminder.setMinutes(selectedMinute);
        reminder.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        reminder.setDayAlarm(day);
        reminder.setMonthAlarm(month);

        if (audioRecordLayout.isStopped()) {
            reminder.setAudio(audioRecordLayout.getFilePath());
        }
        if (fileImage != null) {
            reminder.setImage(fileImage);
        }

        reminder.setType(type);
        reminder.setId(System.currentTimeMillis());
        realm.commitTransaction();

        return reminder;
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
        reminderImage.setVisibility(View.VISIBLE);
        reminderImageLayout.setVisibility(View.GONE);
        Picasso.with(this)
                .load(file)
                .into(reminderImage);
    }

    public void setDataTime(int day, int month){
        String dayText = String.valueOf(day);
        if (day < 10) {
            dayText = '0' + String.valueOf(day);
        }

        String monthText = String.valueOf(month);
        if (month < 10) {
            monthText = '0' + String.valueOf((month));
        }

        dataTime.setText(dayText + "/" + monthText);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth;
        month =  monthOfYear + 1;
        setDataTime(dayOfMonth, monthOfYear + 1);
    }
}
