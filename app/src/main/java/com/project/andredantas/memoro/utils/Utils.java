package com.project.andredantas.memoro.utils;

import android.widget.TextView;

/**
 * Created by Andre Dantas on 3/28/17.
 */

public class Utils {
    public static String setDataTime(int day, int month){
        String dayText = String.valueOf(day);
        if (day < 10) {
            dayText = '0' + String.valueOf(day);
        }

        String monthText = String.valueOf(month);
        if (month < 10) {
            monthText = '0' + String.valueOf((month));
        }

        return dayText + "/" + monthText;
    }
}
