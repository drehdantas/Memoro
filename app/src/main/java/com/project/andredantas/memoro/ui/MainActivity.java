package com.project.andredantas.memoro.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.ScheduleRealm;
import com.project.andredantas.memoro.model.dao.ScheduleDAO;
import com.project.andredantas.memoro.ui.reminder.CreateReminderActivity;
import com.project.andredantas.memoro.ui.schedules.SchedulesFragment;
import com.project.andredantas.memoro.ui.reminder.ReminderFragment;
import com.project.andredantas.memoro.utils.TinyDB;
import com.project.andredantas.memoro.utils.Utils;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private String[] tab_names;
    private List<String> mTabNames;
    public PagerAdapter mPagerAdapter;

    @Bind(R.id.view_pager)
    ViewPager mPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.menu)
    FloatingActionMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
        initView();
        configApp();
    }

    public void initView(){
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        menu.setClosedOnTouchOutside(true);

    }

    public void initViewPager() {
        tab_names = getResources().getStringArray(R.array.tabs);
        mTabNames = Arrays.asList(tab_names);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPager.onTouchEvent(event);
                return false;
            }
        });

        tabLayout.setupWithViewPager(mPager);
    }

    public void configApp(){
        ScheduleDAO.createScheduleNone(this);
        Utils.saveColors();

        if (!TinyDB.getInstance(this).getBoolean("newlogin")) {
            //SHOW SHOWCASE
            TinyDB.getInstance(this).putBoolean("newlogin", true);
        }
    }

    @OnClick(R.id.fab_img)
    public void reminderImage(){
        Intent intent = new Intent(this, CreateReminderActivity.class);
        intent.putExtra("type", "image");
        startActivity(intent);
    }

    @OnClick(R.id.fab_voice)
    public void reminderVoice(){
        Intent intent = new Intent(this, CreateReminderActivity.class);
        intent.putExtra("type", "voice");
        startActivity(intent);
    }

    @OnClick(R.id.fab_text)
    public void reminderText(){
        Intent intent = new Intent(this, CreateReminderActivity.class);
        intent.putExtra("type", "text");
        startActivity(intent);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new SchedulesFragment();
                    break;
                case 1:
                    fragment = new ReminderFragment();
                    break;
                default:
                    fragment = new SchedulesFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames.get(position);
        }

    }

    @Override
    public void onBackPressed(){
        if (menu.isOpened())
            menu.close(true);
        else
            super.onBackPressed();
    }

    public void closeMenu(){
        menu.close(true);
    }
}
