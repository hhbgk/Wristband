package com.hhbgk.wristband.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.ui.fragment.AddAlarmFragment;
import com.hhbgk.wristband.ui.fragment.AlarmSettingFragment;
import com.hhbgk.wristband.ui.widget.Toolbar;

public class AlarmActivity extends BaseActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        changeFragment(R.id.container, new AlarmSettingFragment(), AlarmSettingFragment.class.getSimpleName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Alarm Setting");
        mToolbar.setNavigationIcon(android.R.drawable.ic_media_previous);
        mToolbar.setOperationIcon(android.R.drawable.ic_menu_add);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setOperationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.container, new AddAlarmFragment(), AddAlarmFragment.class.getSimpleName());
            }
        });
    }


}
