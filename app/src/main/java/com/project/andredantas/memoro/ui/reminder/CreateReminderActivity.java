package com.project.andredantas.memoro.ui.reminder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.project.andredantas.memoro.model.ReminderNormal;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;
import com.project.andredantas.memoro.model.dao.ReminderDAO;
import com.project.andredantas.memoro.ui.schedules.CreateSchedulesActivity;
import com.project.andredantas.memoro.utils.Utils;
import com.project.andredantas.memoro.utils.audio.AudioRecordLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CreateReminderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Calendar myCalendar = Calendar.getInstance();
    private String type, time;
    private int day = 99, month = 99, selectedHour = 99, selectedMinute = 99;
    private Reminder reminder;
    private List<Schedule> listSchedules;
    private Schedule scheduleChosen;
    private ArrayList<String> listSheduleTitles = new ArrayList<>();
    private Bitmap mImage;
    private Realm realm = Realm.getDefaultInstance();
    private Uri mCropImageUri;
    private String fileImage;

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
        configReminderType();

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
                        setSelectedHour(selectedHour);
                        setSelectedMinute(selectedMinute);
                        time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textTime.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_hour));
                mTimePicker.show();

            }
        });

        if (reminder != null) {
            configEditReminderView();
        } else {
            configCreateReminderView();
        }

        audioRecordLayout.prepareForPlay(reminder != null);
    }

    public void configCreateReminderView() {
        deleteReminder.setVisibility(View.GONE);
        selectedHour = new Time(System.currentTimeMillis()).getHours();
        selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
        String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
        textTime.setText(curTime);

        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH) + 1;
        dataTime.setText(Utils.setDataTime(day, month));
    }

    public void configEditReminderView() {
        textTime.setText(reminder.getTime());
        reminderTitle.setText(reminder.getTitle());
        dataTime.setText(Utils.setDataTime(reminder.getDayAlarm(), reminder.getMonthAlarm()));
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
    }

    public void configReminderType() {
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
    }

    public void setSelectedHour(int selectedHour) {
        this.selectedHour = selectedHour;
    }

    public void setSelectedMinute(int selectedMinute) {
        this.selectedMinute = selectedMinute;
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
                        CropImage.activity(mCropImageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(CreateReminderActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }


    public void onIntentReceived() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("type") != null) {
                type = extras.getString("type");
            }
            if (extras.getLong("reminder") != 0) {
                reminder = ReminderDAO.getById(extras.getLong("reminder"));
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
                    Snackbar.make(this.findViewById(android.R.id.content), getString(R.string.reminder_need_title), Snackbar.LENGTH_SHORT).show();
                } else {
                    if (type.equals("voice")) {
                        if (!audioRecordLayout.isStopped()) {
                            audioRecordLayout.setStopped();
                        }
                    } else if (type.equals("image")) {
                        fileImage = Utils.createDirectoryAndSaveFile(mImage);
                    }

                    editOrSave();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void editOrSave() {
        if (reminder != null) {
            //edit reminder
            ReminderDAO.updateReminder(realm, reminder.getId(), reminderTitle.getText().toString(), reminderDescript.getText().toString(), scheduleChosen, selectedHour, selectedMinute, day, month, audioRecordLayout, fileImage, type);
            finish();
            Toast.makeText(this, getString(R.string.reminder_update_message), Toast.LENGTH_LONG).show();
        } else {
            //create a reminder
            reminder = new Reminder();
            reminder = ReminderNormal.copyFromNormal(setDataReminder(new ReminderNormal()));

            ReminderDAO.saveReminder(realm, reminder);
            finish();
            Toast.makeText(this, getString(R.string.reminder_create_message), Toast.LENGTH_LONG).show();
        }
    }

    public ReminderNormal setDataReminder(ReminderNormal reminderNormal) {
        reminderNormal.setId(System.currentTimeMillis());

        reminderNormal.setTitle(reminderTitle.getText().toString());
        reminderNormal.setDescript(reminderDescript.getText().toString());
        reminderNormal.setScheduleRelated(scheduleChosen != null ? scheduleChosen.getId() : 1);

        if (selectedHour != 99) {
            reminderNormal.setHour(selectedHour);
            reminderNormal.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (selectedMinute != 99) {
            reminderNormal.setMinutes(selectedMinute);
            reminderNormal.setTime(String.format("%02d:%02d", selectedHour, selectedMinute));
        }

        if (day != 99) {
            reminderNormal.setDayAlarm(day);
        }

        if (month != 99) {
            reminderNormal.setMonthAlarm(month);
        }

        if (audioRecordLayout.isStopped()) {
            reminderNormal.setAudio(audioRecordLayout.getFilePath());
        }
        if (fileImage != null) {
            reminderNormal.setImage(fileImage);
        }

        reminderNormal.setType(type);

        return reminderNormal;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioRecordLayout != null && audioRecordLayout.getAudioRecorder().isPlaying()){
            audioRecordLayout.getAudioRecorder().stopAudio();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uriImage = result.getUri();
                reminderImage.setVisibility(View.VISIBLE);
                reminderImageLayout.setVisibility(View.GONE);
                reminderImage.setImageURI(uriImage);
                try {
                    mImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        day = dayOfMonth;
        month = monthOfYear + 1;
        dataTime.setText(Utils.setDataTime(dayOfMonth, monthOfYear + 1));
    }
}
