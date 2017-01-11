package com.hhbgk.wristband.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.hhbgk.wristband.MainApplication;

public class BaseFragment extends Fragment {
    protected String tag = getClass().getSimpleName();
    private Toast mToastShort;
    private Toast mToastLong;
    public MainApplication mApplication;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() == null){
            return;
        }
        mApplication = (MainApplication) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mApplication == null){
            mApplication = (MainApplication) getActivity().getApplication();
        }
    }

    public void showToastShort(String msg) {
        if(getActivity() == null){
            return;
        }
        if (mToastShort != null) {
            mToastShort.setText(msg);
        } else {
            mToastShort = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        }
        mToastShort.show();
    }

    public void showToastShort(int msg) {
        showToastShort(getResources().getString(msg));
    }

    public void showToastLong(String msg) {
        if(getActivity() == null){
            return;
        }
        if (mToastLong != null) {
            mToastLong.setText(msg);
        } else {
            mToastLong = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
        }
        mToastLong.show();
    }

    public void showToastLong(int msg) {
        showToastLong(getResources().getString(msg));
    }

    public void changeFragment(int containerId, Fragment fragment, String fragmentTag) {
        if(fragment == null || getActivity() ==  null){
            return;
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerId, fragment, fragmentTag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, null);
    }
}
