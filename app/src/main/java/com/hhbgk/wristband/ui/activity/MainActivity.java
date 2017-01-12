package com.hhbgk.wristband.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.ui.fragment.HeartRateFragment;
import com.hhbgk.wristband.ui.fragment.MeFragment;
import com.hhbgk.wristband.ui.fragment.SleepFragment;
import com.hhbgk.wristband.ui.fragment.SportFragment;
import com.hhbgk.wristband.ui.widget.Toolbar;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity {
    String tag = getClass().getSimpleName();
    private Toolbar mToolbar;
    private BottomBar mBottomBar;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressedTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.sport);
        mToolbar.setNavigationIcon(R.mipmap.ic_favorites);
        mToolbar.setOperationIcon(R.mipmap.ic_friends);
        mToolbar.setOperationText("OK");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation();
            }
        });

        mBottomBar = (BottomBar) findViewById(R.id.bottombar);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_sport:
                        mToolbar.setTitle(R.string.sport);
                        changeFragment(R.id.container, new SportFragment(), SportFragment.class.getSimpleName());
                        break;
                    case R.id.tab_heartrate:
                        changeFragment(R.id.container, new HeartRateFragment(), HeartRateFragment.class.getSimpleName());
                        mToolbar.setTitle(R.string.heart_rate);
                        break;
                    case R.id.tab_sleep:
                        changeFragment(R.id.container, new SleepFragment(), SleepFragment.class.getSimpleName());
                        mToolbar.setTitle(R.string.sleep);
                        break;
                    case R.id.tab_me:
                        changeFragment(R.id.container, new MeFragment(), MeFragment.class.getSimpleName());
                        mToolbar.setTitle(R.string.mine);
                        break;
                }
            }
        });
        changeFragment(R.id.container, new SportFragment(), SportFragment.class.getSimpleName());
    }

    private void handleOperation(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof SportFragment){
            startActivity(new Intent(this, EventCalendarActivity.class));
        }
    }

    @Override
    public void onBackPressed()	{
        if (mBackPressedTimes + TIME_INTERVAL > System.currentTimeMillis()) {
            //super.onBackPressed();
            finish();
            return;
        }else {
            showToastShort(R.string.double_tap_to_exit);
        }

        mBackPressedTimes = System.currentTimeMillis();
    }
}
