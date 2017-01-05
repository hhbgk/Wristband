package com.hhbgk.wristband.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhbgk.wristband.R;
import com.hhbgk.wristband.data.bean.CommonInfo;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    String tag = getClass().getSimpleName();
    private OnItemClickListener mOnItemClickListener;
    private List<CommonInfo> mData;

    public CommonAdapter(Context context, List<CommonInfo> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CommonInfo commonInfo = mData.get(position);
        holder.mTitle.setText(commonInfo.getTitle());
        holder.mSubtitle.setText(commonInfo.getSubtitle());
        holder.mSubtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, commonInfo.getSubicon());

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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() <= 0) {
            return 0;
        }
        return mData.size();
    }

    /*
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
    */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mSubtitle;

        ViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.item_title);
            mSubtitle = (TextView) itemView.findViewById(R.id.item_subtitle);
        }
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
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
