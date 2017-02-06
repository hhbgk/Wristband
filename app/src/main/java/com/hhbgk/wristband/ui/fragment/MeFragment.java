package com.hhbgk.wristband.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;
import com.hhbgk.wristband.data.bean.CommonInfo;
import com.hhbgk.wristband.ui.activity.AlarmActivity;
import com.hhbgk.wristband.ui.activity.AlertedActivity;
import com.hhbgk.wristband.ui.activity.DeviceListActivity;
import com.hhbgk.wristband.ui.adapter.BaseAdapter;
import com.hhbgk.wristband.ui.adapter.ListAdapter;
import com.hhbgk.wristband.ui.widget.CustomDivider;
import com.hhbgk.wristband.util.Constants;
import com.hhbgk.wristband.util.Dbug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bob on 2017/1/8.
 */

public class MeFragment extends BaseFragment {
    private String tag = getClass().getSimpleName();
    private RecyclerView mGridView;
    private TextView mQuit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        view.findViewById(R.id.top_title).setVisibility(View.GONE);
        view.findViewById(R.id.subtitle).setVisibility(View.GONE);
        mGridView = (RecyclerView) view.findViewById(R.id.grid_view);
        mQuit = (TextView) view.findViewById(R.id.footer_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mGridView.addItemDecoration(new CustomDivider(getActivity()));
        String[] grids = mApplication.getResources().getStringArray(R.array.me_lists);
        List<CommonInfo> list = new ArrayList<>();
        for (int i = 0; i < grids.length; i++){
            if (i == 1)
                list.add(new CommonInfo(Constants.TYPE_BAND_STATE, grids[i], null, null));
            else if (i == 2)
                list.add(new CommonInfo(Constants.TYPE_BAND_POWER, grids[i], null, null));
            else if (i == 3)
                list.add(new CommonInfo(Constants.TYPE_BAND_ALERTED, grids[i], null, null));
            else if (i == 4)
                list.add(new CommonInfo(Constants.TYPE_BAND_ALARM, grids[i], null, null));
            else if (i == grids.length-1)
                list.add(new CommonInfo(Constants.TYPE_ABOUT, grids[i], null, null));
            else
                list.add(new CommonInfo(Constants.TYPE_NORMAL, grids[i], null, null));
        }

        final ListAdapter adapter = new ListAdapter(getActivity(), list);
        mGridView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CommonInfo item = (CommonInfo) adapter.getItem(position);
                Dbug.i(tag, "onItem "+position+", type="+ item.getType());
                switch (item.getType()){
                    case Constants.TYPE_BAND_STATE:
                        startActivity(new Intent(getActivity(), DeviceListActivity.class));
                        break;
                    case Constants.TYPE_BAND_ALERTED:
                        startActivity(new Intent(getActivity(), AlertedActivity.class));
                        break;
                    case Constants.TYPE_BAND_ALARM:
                        startActivity(new Intent(getActivity(), AlarmActivity.class));
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Dbug.i(tag, "onItemLong "+position);
            }
        });

        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastShort("Quit");
            }
        });
    }
}
