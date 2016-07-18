package com.project.andredantas.memoro.utils.audio;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.andredantas.memoro.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diogojayme on 7/17/16.
 */
public class AudioRecordLayout extends FrameLayout {

    @Bind(R.id.voice_record_message) TextView message;
    @Bind(R.id.voice_record_button) ImageView startRecord;
    @Bind(R.id.voice_record_chronometer) Chronometer chronometer;

    AudioRecorder audioRecorder;

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

    private void init(Context context){
        View audioView = LayoutInflater.from(context).inflate(R.layout.voice_record_layout, this ,false);
        addView(audioView);
        ButterKnife.bind(this, this);
        audioRecorder = new AudioRecorder();
    }

    @OnClick(R.id.voice_record_button) public void onAudioRecordClick(){
        boolean recording = audioRecorder.isRecording();

        if(recording){
            audioRecorder.stopRecording();
        }else{
            audioRecorder.startRecording();
        }

        updateUI(recording);
    }

    private void updateUI(boolean recording){
        if(recording){
            startRecord.setImageResource(R.drawable.ic_play);
            message.setText("Começar a gravar");
            chronometer.stop();//stop chronometer
            chronometer.setBase(SystemClock.elapsedRealtime());//reset chronometer
        }else{
            startRecord.setImageResource(R.drawable.ic_av_pause);
            chronometer.start();//start chronometer
            message.setText("Gravando");
        }
    }

    public AudioRecorder getAudioRecorder(){
        return audioRecorder;
    }
}
