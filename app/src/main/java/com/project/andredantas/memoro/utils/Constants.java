package com.project.andredantas.memoro.utils;

import android.os.Environment;

public class Constants {
    public static final int NO_COLOR = 0;
    public static final int CYANO = 1;
    public static final int YELLOW = 2;
    public static final int PURPLE = 3;
    public static final int RED = 4;
    public static final int GREEN = 5;

    public static final String COLOR = "color";

    public static final String IMAGE_FOLDER = Environment.getExternalStorageDirectory() + "/memoro/image";
    public static final String AUDIO_FOLDER = Environment.getExternalStorageDirectory() + "/memoro/audio";
    public static final String FILE_EXT = ".mp3";
    public static final String IMG_EXT = ".jpg";

}