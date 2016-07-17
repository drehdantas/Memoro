package com.project.andredantas.memoro;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;
import com.project.andredantas.memoro.ui.horarios.HorariosFragment;
import com.project.andredantas.memoro.ui.lembretes.CriarLembreteActivity;
import com.project.andredantas.memoro.ui.lembretes.LembreteFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.fab_img)
    public void lembreteImage(){
        Intent intent = new Intent(this, CriarLembreteActivity.class);
        intent.putExtra("tipo", "imagem");
        startActivity(intent);
    }

    @OnClick(R.id.fab_voice)
    public void lembreteVoz(){
        Intent intent = new Intent(this, CriarLembreteActivity.class);
        intent.putExtra("tipo", "voz");
        startActivity(intent);
    }

    @OnClick(R.id.fab_text)
    public void lembreteTexto(){
        Intent intent = new Intent(this, CriarLembreteActivity.class);
        intent.putExtra("tipo", "texto");
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
                    fragment = new HorariosFragment();
                    break;
                case 1:
                    fragment = new LembreteFragment();
                    break;
                default:
                    fragment = new HorariosFragment();
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
}
