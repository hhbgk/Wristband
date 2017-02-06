package com.hhbgk.wristband;

import android.app.Application;

import com.inuker.bluetooth.library.BluetoothContext;

/**
 * Created by bob on 17-1-4.
 */

public class MainApplication extends Application {
    private static MainApplication instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BluetoothContext.set(this);
    }
}
