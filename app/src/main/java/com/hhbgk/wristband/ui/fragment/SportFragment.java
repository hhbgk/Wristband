package com.hhbgk.wristband.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;

/**
 * Created by bob on 17-1-4.
 */

public class SportFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sport_fragment, container, false);
        return view;
    }
}
