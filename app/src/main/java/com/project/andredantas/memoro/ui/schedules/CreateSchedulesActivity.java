package com.project.andredantas.memoro.ui.schedules;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.ScheduleNormal;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;
import com.project.andredantas.memoro.utils.Utils;
import com.project.andredantas.memoro.utils.colors.RecyclerViewColorAdapter;
import com.project.andredantas.memoro.utils.colors.SpacesItemDecoration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class CreateSchedulesActivity extends AppCompatActivity {
    private String day, time;
    private Schedule schedule;
    private TimePickerDialog mTimePicker;
    private int selectedHour, selectedMinute;
    private Realm realm = Realm.getDefaultInstance();
    private RecyclerViewColorAdapter colorAdapter;
    private List<Integer> mColors;

    @Bind(R.id.schedule_toolbar)
    Toolbar toolbar;
    @Bind(R.id.click_time)
    LinearLayout clickTime;
    @Bind(R.id.text_time)
    TextView textTime;
    @Bind(R.id.day_week)
    TextView dayWeek;
    @Bind(R.id.schedule_title)
    EditText scheduleTitle;
    @Bind(R.id.schedule_descript)
    EditText scheduleDescript;
    @Bind(R.id.delete_schedule)
    Button deleteSchedule;

    @Bind(R.id.color_picker_recycler_view)
    RecyclerView colorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);
        ButterKnife.bind(this);
        onIntentReceived();
        initView();
    }

    private void onIntentReceived() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("day") != null) {
                day = extras.getString("day");
            }
            if (extras.getLong("schedule") != 0) {
                schedule = ScheduleDAO.getById(extras.getLong("schedule"));
            }
        }
    }

    public void initView() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(schedule != null ? getString(R.string.edit_schedule) : getString(R.string.create_schedule));
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dayWeek.setText(day);

        //edit schedule
        if (schedule != null) {
            textTime.setText(schedule.getTime());
            scheduleTitle.setText(schedule.getTitle());
            scheduleDescript.setText(schedule.getDescript());
            deleteSchedule.setVisibility(View.VISIBLE);
            setTextWithCursorFinal(scheduleTitle);
            setTextWithCursorFinal(scheduleDescript);
        } else {
            //create schedule
            deleteSchedule.setVisibility(View.GONE);
            selectedHour = new Time(System.currentTimeMillis()).getHours();
            selectedMinute = new Time(System.currentTimeMillis()).getMinutes();
            String curTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            textTime.setText(curTime);
            time = curTime;
        }

        clickTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(CreateSchedulesActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        mColors = Utils.getColors(this);

        // Color Recycler View
        colorAdapter = new RecyclerViewColorAdapter(this);
        colorAdapter.setColors(mColors);
        LinearLayoutManager colorLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(colorLayoutManager);
        colorRecyclerView.addItemDecoration(new SpacesItemDecoration(this, mColors.size(), 20));
        colorRecyclerView.setHasFixedSize(true);
        colorRecyclerView.setAdapter(colorAdapter);
    }

    public void setSelectedHour(int selectedHour){
        this.selectedHour = selectedHour;
    }

    public void setSelectedMinute(int selectedMinute){
        this.selectedMinute = selectedMinute;
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
                onBackPressed();
                break;
            case R.id.action_check:
                if (scheduleTitle.getText().toString().equals("") || scheduleTitle.getText() == null) {
                    Snackbar.make(this.findViewById(android.R.id.content), getString(R.string.schedule_need_title), Snackbar.LENGTH_SHORT).show();
                } else {
                    if (schedule != null) {
                        //edit schedule
                        ScheduleDAO.updateSchedule(realm, schedule.getId(), scheduleTitle.getText().toString(), scheduleDescript.getText().toString(), selectedHour, selectedMinute, time);
                        finish();
                        Toast.makeText(this, getString(R.string.schedule_update_message), Toast.LENGTH_LONG).show();
                    } else {
                        //create schedule
                        schedule = new Schedule();
                        schedule = ScheduleNormal.copyFromNormal(setSchedule(new ScheduleNormal()));

                        ScheduleDAO.saveSchedule(realm, schedule);
                        finish();
                        Toast.makeText(this, getString(R.string.schedule_create_message), Toast.LENGTH_LONG).show();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.delete_schedule)
    public void deleteSchedule() {
        ScheduleDAO.deleteSchedule(realm, schedule);
        finish();
        Toast.makeText(this, getString(R.string.schedule_deleted_message), Toast.LENGTH_LONG).show();
    }

    public ScheduleNormal setSchedule(ScheduleNormal schedule) {
        schedule.setId(System.currentTimeMillis());

        schedule.setTitle(scheduleTitle.getText().toString());
        schedule.setDescript(scheduleDescript.getText().toString());
        schedule.setDay(day);

        schedule.setHour(selectedHour);
        schedule.setMinutes(selectedMinute);
        schedule.setTime(time);

        return schedule;
    }

    public static void setTextWithCursorFinal(EditText edittext) {
        //set cursor in the end
        int position = edittext.length();
        Editable etext = edittext.getText();
        Selection.setSelection(etext, position);
    }

}
