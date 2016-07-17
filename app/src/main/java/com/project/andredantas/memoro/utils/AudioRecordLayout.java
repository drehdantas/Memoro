package com.project.andredantas.memoro.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.project.andredantas.memoro.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diogojayme on 7/17/16.
 */
public class AudioRecordLayout extends FrameLayout {

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

    @OnClick(R.id.record_audio_button) public void onAudioRecordClick(){
        if(audioRecorder.isRecording()){
            audioRecorder.stopRecording();
        }else{
            audioRecorder.startRecording();
        }
    }
}
