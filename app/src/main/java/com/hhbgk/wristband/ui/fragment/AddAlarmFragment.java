package com.hhbgk.wristband.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;
import com.hhbgk.wristband.data.bean.CommonInfo;
import com.hhbgk.wristband.ui.adapter.AlarmSettingAdapter;
import com.hhbgk.wristband.ui.widget.CustomDivider;
import com.hhbgk.wristband.ui.widget.NumberPickerView;
import com.hhbgk.wristband.util.Dbug;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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

    private RecyclerView mListView;
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
        mListView = (RecyclerView) view.findViewById(R.id.list_view);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mListView.addItemDecoration(new CustomDivider(getActivity()));
        String[] grids = mApplication.getResources().getStringArray(R.array.alarm_setting_menu);
        List<CommonInfo> list = new ArrayList<>();
        for (String s : grids){
            list.add(new CommonInfo(s, null, null));
        }

        AlarmSettingAdapter adapter = new AlarmSettingAdapter(getActivity(), list, 0, 1);
        mListView.setAdapter(adapter);
    }
}
