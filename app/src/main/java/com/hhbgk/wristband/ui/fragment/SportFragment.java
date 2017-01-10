package com.hhbgk.wristband.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.hhbgk.wristband.ui.widget.DonutProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SportFragment extends BaseFragment {
    private DonutProgress donutProgress;
    private RecyclerView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sport_fragment, container, false);
        donutProgress = (DonutProgress) view.findViewById(R.id.donut_progress);
        view.findViewById(R.id.top_title).setVisibility(View.GONE);
        view.findViewById(R.id.subtitle).setVisibility(View.GONE);
        mGridView = (RecyclerView) view.findViewById(R.id.grid_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.progress_anim);
        set.setInterpolator(new DecelerateInterpolator());
        set.setTarget(donutProgress);
        set.start();*/
        donutProgress.setProgress(30.0f);

        mGridView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mGridView.addItemDecoration(new CustomDivider(getActivity()));
        String[] grids = mApplication.getResources().getStringArray(R.array.sport_grids);

        Random r = new Random();
        List<CommonInfo> list = new ArrayList<>();
        for (String s : grids){
            Drawable drawable;
            if (Build.VERSION.SDK_INT >= 21)
                drawable = getActivity().getDrawable(R.mipmap.ic_favorites);
            else
                drawable = getActivity().getResources().getDrawable(R.mipmap.ic_favorites);
            list.add(new CommonInfo(Integer.toString(r.nextInt(100)), s, drawable));

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
