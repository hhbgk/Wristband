package com.hhbgk.wristband.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.data.bean.CommonInfo;

import java.util.List;

/**
 * Created by bob on 17-1-4.
 */

public class CommonAdapter extends BaseAdapter {
    String tag = getClass().getSimpleName();
    private LayoutInflater mInflater;
    private Context mContext;
//    private List<HashMap<String, Object>> mData;
    private List<CommonInfo> mData;

    public CommonAdapter(Context context, List<CommonInfo> data){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        if (mData == null || mData.size() <= 0) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mData == null){
            return 0;
        } else {
          return position;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = mInflater.inflate(R.layout.common_item, parent, false);
            viewHold.mTitle = (TextView) convertView.findViewById(R.id.item_title);
            viewHold.mSubtitle = (TextView) convertView.findViewById(R.id.item_subtitle);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        final CommonInfo commonInfo = mData.get(position);
        if (viewHold.mTitle != null){
            viewHold.mTitle.setText(commonInfo.getTitle());
        }
        viewHold.mSubtitle.setText(commonInfo.getSubtitle());
        viewHold.mSubtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, commonInfo.getSubicon());
        return convertView;
    }

    private static class ViewHold{
        TextView mTitle;
        TextView mSubtitle;
    }
}
