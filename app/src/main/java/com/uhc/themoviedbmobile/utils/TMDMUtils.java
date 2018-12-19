package com.uhc.themoviedbmobile.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by const on 12/17/18.
 */
public class TMDMUtils {
    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String formatDate(String data) {
        if (data != null && data.contains("-"))
            try {
                return new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(data));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return data;
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US).format(Calendar.getInstance().getTime());
    }
}
