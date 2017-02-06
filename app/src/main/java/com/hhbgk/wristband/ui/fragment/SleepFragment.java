package com.hhbgk.wristband.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;
import com.hhbgk.wristband.data.bean.CommonInfo;
import com.hhbgk.wristband.ui.adapter.CommonAdapter;
import com.hhbgk.wristband.ui.widget.CustomDivider;
import com.hhbgk.wristband.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SleepFragment extends BaseFragment {
    String tag = getClass().getSimpleName();
    private BarChart mChart;
    private RecyclerView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sleep_fragment, container, false);
        mChart = (BarChart) view.findViewById(R.id.bar_chart);
        mGridView = (RecyclerView) view.findViewById(R.id.grid_view);
        initStackedBarChart();
        return view;
    }

    private void initStackedBarChart() {
        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
/*
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);
*/
        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        leftAxis.setDrawGridLines(true);

        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTH_BOTTOM_LABEL);
        xLabels.setDrawLabels(true);
        xLabels.setDrawGridLines(true);
        xLabels.setDrawAxisLine(true);
        xLabels.setDrawLimitLinesBehindData(true);

        setData();
        Legend l = mChart.getLegend();
//        l.setForm(Legend.LegendForm.NONE);

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
    }

    private void setData(){
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 12 + 1; i++) {
            float mult = (100 + 1);
            float val1 = (float) (Math.random() * mult) + mult / 3;
            float val2 = (float) (Math.random() * mult) + mult / 3;
            float val3 = (float) (Math.random() * mult) + mult / 3;

            yVals1.add(new BarEntry(i, new float[]{val1, val2, val3}));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColors(getColors());
            //set1.setStackLabels(new String[]{"Births", "Divorces", "Marriages"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            //data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);

            mChart.setData(data);
        }

        mChart.setFitBars(true);
        mChart.invalidate();
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mGridView.addItemDecoration(new CustomDivider(getActivity()));
        String[] grids = getActivity().getResources().getStringArray(R.array.sleep_grids);

        Random r = new Random();
        List<CommonInfo> list = new ArrayList<>();
        for (String s : grids){
            Drawable drawable;
            if (Build.VERSION.SDK_INT >= 21)
                drawable = getActivity().getDrawable(R.mipmap.ic_favorites);
            else
                drawable = getActivity().getResources().getDrawable(R.mipmap.ic_favorites);
            list.add(new CommonInfo(Constants.TYPE_NORMAL,Integer.toString(r.nextInt(100)), s, drawable));
        }

        CommonAdapter adapter = new CommonAdapter(getActivity(), list);
        mGridView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToastShort("item click "+position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showToastShort("item long click "+position);
            }
        });
    }
}
