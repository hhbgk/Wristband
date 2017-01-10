package com.hhbgk.wristband.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.interfaces.DatePickerController;
import com.hhbgk.wristband.ui.adapter.GridAdapter;
import com.hhbgk.wristband.ui.adapter.SimpleMonthAdapter;
import com.hhbgk.wristband.ui.widget.DayPickerView;
import com.hhbgk.wristband.util.Dbug;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EventCalendarActivity extends BaseActivity implements DatePickerController{
    private DayPickerView mDayPickerView;
    private int mNumDays = 7;
    protected int mWeekStart = 1;
    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
    private Calendar mDayLabelCalendar;
    private RecyclerView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        mGridView = (RecyclerView) findViewById(R.id.grid_view);
        mGridView.setLayoutManager(new StaggeredGridLayoutManager(mNumDays, StaggeredGridLayoutManager.VERTICAL));
        mDayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        mDayPickerView.setController(this);

        List<String> list = new ArrayList<>();
        mDayLabelCalendar = Calendar.getInstance();
        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            String s = mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault());
            list.add(s);
            Dbug.w(tag, "s="+s);
        }
        GridAdapter gridAdapter = new GridAdapter(this, list);
        mGridView.setAdapter(gridAdapter);
    }

    @Override
    public int getMaxYear() {
        return 2017;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Dbug.i(tag, "onDayOfMonthSelected="+day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        Dbug.i(tag, "onDateRangeSelected="+selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }
}
