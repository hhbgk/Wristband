package com.hhbgk.wristband.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.ui.fragment.HeartRateFragment;
import com.hhbgk.wristband.ui.fragment.MeFragment;
import com.hhbgk.wristband.ui.fragment.SleepFragment;
import com.hhbgk.wristband.ui.fragment.SportFragment;
import com.hhbgk.wristband.ui.widget.Toolbar;
import com.hhbgk.wristband.util.Dbug;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity {
    String tag = getClass().getSimpleName();
    private Toolbar mToolbar;
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = (BottomBar) findViewById(R.id.bottombar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_sport:
                        Dbug.i(tag, "sport----------");
                        changeFragment(R.id.container, new SportFragment(), SportFragment.class.getSimpleName());
                        break;
                    case R.id.tab_heartrate:
                        changeFragment(R.id.container, new HeartRateFragment(), HeartRateFragment.class.getSimpleName());
                        break;
                    case R.id.tab_sleep:
                        changeFragment(R.id.container, new SleepFragment(), SleepFragment.class.getSimpleName());
                        break;
                    case R.id.tab_me:
                        changeFragment(R.id.container, new MeFragment(), MeFragment.class.getSimpleName());
                        break;
                }
            }
        });
        changeFragment(R.id.container, new SleepFragment(), SleepFragment.class.getSimpleName());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Title");
        mToolbar.setNavigationIcon(R.mipmap.ic_favorites);
        mToolbar.setOperationIcon(R.mipmap.ic_friends);
        mToolbar.setOperationText("OK");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
