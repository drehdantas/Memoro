package com.project.andredantas.memoro.utils.audio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.andredantas.memoro.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diogojayme on 7/17/16.
 */
public class AudioRecordLayout extends FrameLayout {

    @Bind(R.id.voice_record_message)
    TextView message;
    @Bind(R.id.voice_record_button)
    ImageView startRecord;
    @Bind(R.id.voice_record_chronometer)
    Chronometer chronometer;
    @Bind(R.id.play_pause_layout)
    View playPauseLayout;
    @Bind(R.id.record_layout)
    View recordLayout;
    @Bind(R.id.play_pause_button)
    ImageView playPause;

    AudioRecorder audioRecorder;
    String filePath;
    boolean stopped = false;

    public AudioRecordLayout(Context context) {
        super(context);
    }

    public AudioRecordLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AudioRecordLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        View audioView = LayoutInflater.from(context).inflate(R.layout.audio_record_layout, this, false);
        addView(audioView);
        ButterKnife.bind(this, this);
        audioRecorder = new AudioRecorder();
    }

    @OnClick(R.id.voice_record_button)
    public void onAudioRecordClick() {
        new TedPermission(getContext())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        boolean recording = audioRecorder.isRecording();
                        if (recording) {
                            setStopped();
                        } else {
                            setStartRecord();
                        }

                        updateUI(recording);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.RECORD_AUDIO)
                .check();
    }

    public void setStopped(){
        audioRecorder.stopRecording();
        filePath = audioRecorder.getmFileName();
        stopped = true;
    }

    public void setStartRecord(){
        audioRecorder.startRecording();
        stopped = false;
    }

    @OnClick(R.id.play_pause_button)
    public void onPlayAudioClick() {
        playPause.setImageResource(R.drawable.ic_av_pause);
        audioRecorder.playAudio(filePath);
        audioRecorder.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playPause.setImageResource(R.drawable.ic_play);
            }
        });
    }

    private void updateUI(boolean recording) {
        if (recording) {
            startRecord.setImageResource(R.drawable.ic_play);
            message.setText(getContext().getString(R.string.start_recording));
            chronometer.stop();//stop chronometer
            chronometer.setBase(SystemClock.elapsedRealtime());//reset chronometer
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime());
            startRecord.setImageResource(R.drawable.ic_av_pause);
            chronometer.start();//start chronometer
            message.setText(getContext().getString(R.string.recording));
        }
    }

    public AudioRecorder getAudioRecorder() {
        return audioRecorder;
    }

    public boolean isStopped() {
        return stopped;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String file) {
        filePath = file;
    }

    public void prepareForPlay(boolean play) {
        playPauseLayout.setVisibility(play ? View.VISIBLE : View.GONE);
        recordLayout.setVisibility(play ? View.GONE : View.VISIBLE);
    }
}
