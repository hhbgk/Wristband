package com.hhbgk.wristband.ui.activity;

import android.os.Bundle;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.ui.fragment.AlarmSettingFragment;

public class AlarmActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        changeFragment(R.id.container, new AlarmSettingFragment(), AlarmSettingFragment.class.getSimpleName());

    }


}
