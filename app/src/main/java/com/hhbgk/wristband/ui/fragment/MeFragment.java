package com.hhbgk.wristband.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.base.BaseFragment;
import com.hhbgk.wristband.data.bean.CommonInfo;
import com.hhbgk.wristband.ui.adapter.CommonAdapter;
import com.hhbgk.wristband.ui.widget.CustomDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bob on 2017/1/8.
 */

public class MeFragment extends BaseFragment {
    private RecyclerView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment, container, false);
        view.findViewById(R.id.top_title).setVisibility(View.GONE);
        view.findViewById(R.id.subtitle).setVisibility(View.GONE);
        mGridView = (RecyclerView) view.findViewById(R.id.grid_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mGridView.addItemDecoration(new CustomDivider(getActivity()));
        String[] grids = mApplication.getResources().getStringArray(R.array.me_lists);
        List<CommonInfo> list = new ArrayList<>();
        for (String s : grids){
            list.add(new CommonInfo(s, null, null));
        }

        CommonAdapter adapter = new CommonAdapter(getActivity(), list, 1);
        mGridView.setAdapter(adapter);
    }
}
