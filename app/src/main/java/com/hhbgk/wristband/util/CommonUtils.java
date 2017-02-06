package com.hhbgk.wristband.util;

import android.widget.Toast;

import com.hhbgk.wristband.MainApplication;

public class CommonUtils {

    public static void toast(String text) {
        Toast.makeText(MainApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }
}
