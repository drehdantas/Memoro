package com.project.andredantas.memoro.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.TextView;

import com.project.andredantas.memoro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andre Dantas on 3/28/17.
 */

public class Utils {

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

    public static List<Integer> getColors(Context context){
        List<Integer> colors = new ArrayList<>();
        colors.add(context.getColor(R.color.cyano));
        colors.add(context.getColor(R.color.yellow));
        colors.add(context.getColor(R.color.purple));
        colors.add(context.getColor(R.color.red));
        colors.add(context.getColor(R.color.green));
        return colors;
    }
}
