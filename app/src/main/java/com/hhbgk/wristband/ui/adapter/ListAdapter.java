package com.hhbgk.wristband.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.data.bean.CommonInfo;
import com.hhbgk.wristband.util.Constants;

import java.util.List;

/**
 * Created by bob on 17-1-9.
 */

public class ListAdapter extends BaseAdapter {
    private OnItemClickListener mOnItemClickListener;
    private List<CommonInfo> mData;

    public ListAdapter(Context context, List<CommonInfo> data) {
        this(context, data, 0, 0);
    }

    public ListAdapter(Context context, List<CommonInfo> data, int headerCount) {
        this(context, data, headerCount, 0);
    }

    public ListAdapter(Context context, List<CommonInfo> data, int headerCount, int footerCount){
        mData = data;
        mHeaderCount = headerCount;
        mFooterCount = footerCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal() == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ListViewHolder(view);
        } else if (ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal() == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_layout, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder){
            final CommonInfo commonInfo = mData.get(position);
            switch (commonInfo.getType()){
                case Constants.TYPE_BAND_STATE:
                    break;
                case Constants.TYPE_BAND_POWER:
                    break;
                case Constants.TYPE_BAND_ALERTED:
                    break;
                case Constants.TYPE_BAND_ALARM:
                    break;
                case Constants.TYPE_ABOUT:
                    break;
            }
            if (!TextUtils.isEmpty(commonInfo.getTitle()))
                ((ListViewHolder) holder).mTitle.setText(commonInfo.getTitle());
        } else if (holder instanceof FooterViewHolder) {
            //((FooterViewHolder) holder).mTextView.setText("Exit");
        }


        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(v, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getItemCount();
        /*if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else */
        if(mFooterCount != 0 && position >= (mHeaderCount + mData.size())) {//底部View
            return ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() <= 0) {
            return 0;
        }
        return (mHeaderCount + mData.size() + mFooterCount);
    }

    private static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ListViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

/*    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        HeaderViewHolder(View view) {
            super(view);
        }
    }*/

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        FooterViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.footer_text);
        }
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
