package com.hhbgk.wristband.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.hhbgk.wristband.MainApplication;

/**
 * Created by bob on 17-1-4.
 */

public class BaseActivity extends FragmentActivity {
    protected final String tag = getClass().getSimpleName();
    private Toast mToastShort, mToastLong;
    public MainApplication mApplication;

    public void showToastShort(String info) {
        if (mToastShort != null) {
            mToastShort.setText(info);
        } else {
            mToastShort = Toast.makeText(this, info, Toast.LENGTH_SHORT);
        }
        mToastShort.show();
    }

    public void showToastShort(int info) {
        showToastShort(getResources().getString(info));
    }

    public void showToastLong(String msg) {
        if (mToastLong != null) {
            mToastLong.setText(msg);
        } else {
            mToastLong = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        }
        mToastLong.show();
    }

    public void showToastLong(int msg) {
        showToastLong(getResources().getString(msg));
    }

    protected void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, null);
    }

    protected void changeFragment(int containerId, Fragment fragment, String tag) {
        if(fragment == null){return;}
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
