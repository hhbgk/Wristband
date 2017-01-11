package com.hhbgk.wristband.ui.activity;

import android.os.Bundle;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseActivity;
import com.hhbgk.wristband.ui.widget.NumberPickerView;
import com.hhbgk.wristband.util.Dbug;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmActivity extends BaseActivity implements NumberPickerView.OnValueChangeListener {
    private NumberPickerView mHourPicker;
    private NumberPickerView mMinutePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        mHourPicker = (NumberPickerView) this.findViewById(R.id.hour_picker);
        mMinutePicker = (NumberPickerView) this.findViewById(R.id.minute_picker);
        initTime();
    }

    private void initTime() {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        //h = h % 12;

        setData(mHourPicker, 0, 23, h);
        setData(mMinutePicker, 0, 59, m);
    }

    private void setData(NumberPickerView picker, int minValue, int maxValue, int value) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
        picker.setOnValueChangedListener(this);
    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        Dbug.i(tag, "Test oldVal="+oldVal+", newVal="+newVal);
    }
}
