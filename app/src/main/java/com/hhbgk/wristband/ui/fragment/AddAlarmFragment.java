package com.hhbgk.wristband.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;
import com.hhbgk.wristband.ui.widget.NumberPickerView;
import com.hhbgk.wristband.util.Dbug;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAlarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAlarmFragment extends BaseFragment implements NumberPickerView.OnValueChangeListener {
    private NumberPickerView mHourPicker;
    private NumberPickerView mMinutePicker;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddAlarmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAlarmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAlarmFragment newInstance(String param1, String param2) {
        AddAlarmFragment fragment = new AddAlarmFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_alarm, container, false);
        mHourPicker = (NumberPickerView) view.findViewById(R.id.hour_picker);
        mMinutePicker = (NumberPickerView) view.findViewById(R.id.minute_picker);
        initTime();
        return view;
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
