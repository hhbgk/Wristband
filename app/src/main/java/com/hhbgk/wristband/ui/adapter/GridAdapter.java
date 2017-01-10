package com.hhbgk.wristband.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhbgk.wristband.R;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mData;

    public GridAdapter(Context context, List<String> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).mTitle.setText(mData.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() <= 0) {
            return 0;
        }
        return (mData.size());
    }

    private static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ListViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
