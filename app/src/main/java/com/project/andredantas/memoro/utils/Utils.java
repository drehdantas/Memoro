package com.project.andredantas.memoro.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.project.andredantas.memoro.R;
import com.project.andredantas.memoro.model.ColorRealm;
import com.project.andredantas.memoro.model.Schedule;
import com.project.andredantas.memoro.model.dao.ColorDAO;
import com.project.andredantas.memoro.notification.NotificationPublisher;
import com.project.andredantas.memoro.ui.schedules.CreateSchedulesActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andre Dantas on 3/28/17.
 */

public class Utils {
    public static List<Integer> colorMap = new ArrayList<>();

    public static String setDataTime(int day, int month) {
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

    public static String uriToFilename(Uri uri, Context mContext) {
        // Log.variable("uri", uri.getPath());
        String filePath = "";
        if (DocumentsContract.isDocumentUri(mContext, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);
            //Log.variable("wholeID", wholeID);
            // Split at colon, use second item in the array
            String[] splits = wholeID.split(":");
            if (splits.length == 2) {
                String id = splits[1];

                String[] column = {MediaStore.Images.Media.DATA};
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";
                Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);
                int columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else {
            filePath = uri.getPath();
        }
        return filePath;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static String createDirectoryAndSaveFile(Bitmap imageToSave) {
        File directory = new File(Constants.IMAGE_FOLDER);

        if(!directory.exists())
            directory.mkdirs();

        File filePath = new File(directory + File.separator +  new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date()) + Constants.IMG_EXT);

        try {
            filePath.createNewFile();

            FileOutputStream out = new FileOutputStream(filePath);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return filePath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveColors(){
        colorMap.add(Constants.NO_COLOR);
        colorMap.add(Constants.CYANO);
        colorMap.add(Constants.YELLOW);
        colorMap.add(Constants.PURPLE);
        colorMap.add(Constants.RED);
        colorMap.add(Constants.GREEN);
    }

    public static int getColors(int position, Context context){
        switch (position){
            case Constants.NO_COLOR:
                return 0;
            case Constants.CYANO:
                return context.getColor(R.color.cyano);
            case Constants.YELLOW:
                return context.getColor(R.color.yellow);
            case Constants.PURPLE:
                return context.getColor(R.color.purple);
            case Constants.RED:
                return context.getColor(R.color.red);
            case Constants.GREEN:
                return context.getColor(R.color.green);
            default:
                return 0;
        }
    }

    public static String getDayofWeek(int day, Context context){
        if (day == Schedule.SUNDAY){
            return context.getString(R.string.sunday);
        }else if (day == Schedule.MONDAY){
            return context.getString(R.string.monday);
        }else if (day == Schedule.TUESDAY){
            return context.getString(R.string.tuesday);
        }else if (day == Schedule.WEDNESDAY){
            return context.getString(R.string.wednesday);
        }else if (day == Schedule.THURSDAY){
            return context.getString(R.string.thursday);
        }else if (day == Schedule.FRIDAY){
            return context.getString(R.string.friday);
        }else if (day == Schedule.SATURDAY){
            return context.getString(R.string.saturday);
        }else{
            return "";
        }
    }

    public static void scheduleNotification(Context context, Schedule schedule, int delay) {
        int hour = schedule.getHour();
        int minute = schedule.getMinutes() - delay;
        if (minute < 0){
            minute *= -1;
            minute = 60 - minute;
            hour--;
            if (hour < 0){
                hour++;
            }
        }

        Notification notification = getNotification(context, schedule);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.DAY_OF_WEEK, schedule.getDay());

        //Verifica se a hora é mais cedo que a atual. Caso seja mais cedo, adiciona 24horas para o alarme
        if(cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }

        if (schedule.getAlertType() == Schedule.ALWAYS_ALERT){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , 24*60*60*1000, pendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }

    private static Notification getNotification(Context context, Schedule schedule) {
        Intent resultIntent = new Intent(context, CreateSchedulesActivity.class);
        resultIntent.putExtra("scheduleRealm", schedule.getId());

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(schedule.getTitle());
        builder.setContentText(schedule.getDescript());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);
        builder.setColor(getColors(schedule.getColor(), context));
        builder.setVibrate(new long[] { 500, 500, 500, 500, 500 });

        return builder.build();
    }
}
