package com.hhbgk.wristband.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.ui.adapter.AlarmAdapter;
import com.hhbgk.wristband.ui.widget.CustomDivider;

import java.util.ArrayList;
import java.util.List;

public class AlertedActivity extends Activity {
    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerted);

        mListView = (RecyclerView) findViewById(R.id.alerted_list);
        mListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mListView.addItemDecoration(new CustomDivider(this));

        String[] alerts = getResources().getStringArray(R.array.alerted_menu);
        List<String> list = new ArrayList<>();
        for (String s : alerts){
            list.add(s);
        }

        AlarmAdapter alarmAdapter = new AlarmAdapter(list);
        mListView.setAdapter(alarmAdapter);
    }
}
