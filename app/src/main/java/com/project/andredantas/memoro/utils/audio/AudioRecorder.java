package com.project.andredantas.memoro.utils.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.project.andredantas.memoro.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andre on 03/08/15.
 */
public class AudioRecorder {

    boolean recording, playing;
    private MediaRecorder mRecorder = null;
    private String mFileName;
    MediaPlayer mediaPlayer;
    static final String TAG = "Audio Record";
    List<File> tmpFiles = new ArrayList<>();

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

        tmpFiles.add(filePath);
        Log.d(TAG, "audio path " + filePath);
        mFileName = filePath.toString();
        recording = true;

        if(mRecorder != null){
            mRecorder.release();
            mRecorder = null;
        }

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
    }

    public void playAudio(String file){
        playing = true;
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public void stopRecording() {
        if (isRecording()) {
            mRecorder.stop();
            mRecorder.release();
        }

        mRecorder = null;
        recording = false;
    }

    public void deleteFiles() {
        for (int i = 0; i < tmpFiles.size(); i++) {
            Log.d(TAG, "New file deleted =>" + tmpFiles.get(i).getAbsolutePath());
            boolean delete = tmpFiles.get(i).delete();
        }

    }

    public String getmFileName(){
        return mFileName;
    }

    public boolean isRecording() {
        return recording;
    }



}