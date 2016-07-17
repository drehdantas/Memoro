package com.project.andredantas.memoro.utils;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andre on 03/08/15.
 */
public class AudioRecorder {

    boolean recording;
    private MediaRecorder mRecorder = null;
    private String mFileName;
    static final String TAG = "Audio Record";

    public AudioRecorder() {}

    public void startRecording(){
        File directory = new File(Constants.AUDIO_FOLDER);

        if(!directory.exists())
            directory.mkdirs();

        File filePath = new File(directory + File.separator +  new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date()) + Constants.FILE_EXT);

        if(!filePath.exists()){
            try {
                filePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "audio path " + filePath);

        mFileName = filePath.toString();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setAudioSamplingRate(44100);
        mRecorder.setAudioEncodingBitRate(96000);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecorder.start();
        recording = true;
    }

    public void stopRecording() {
        if (isRecording()) {
            mRecorder.stop();
            mRecorder.release();
        }

        mRecorder = null;
        recording = false;
    }

    public void finish() {
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        recording = false;
    }

    public void delete() {
        File dir = new File(mFileName);
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
        recording = false;

        if (dir.delete()) {
            Log.e("EXCLUIU", "EXCLUIU");
        }
    }

    public boolean isRecording() {
        return recording;
    }

}