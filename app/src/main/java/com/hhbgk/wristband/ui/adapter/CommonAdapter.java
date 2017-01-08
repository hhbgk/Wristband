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
import com.hhbgk.wristband.util.Dbug;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String tag = getClass().getSimpleName();

    public enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CONTENT,
        ITEM_TYPE_FOOTER
    }
    protected int mHeaderCount;
    protected int mFooterCount;

    private OnItemClickListener mOnItemClickListener;
    private List<CommonInfo> mData;

    public CommonAdapter(Context context, List<CommonInfo> data) {
        this(context, data, 0, 0);
    }

    public CommonAdapter(Context context, List<CommonInfo> data, int headerCount) {
        this(context, data, headerCount, 0);
    }

    public CommonAdapter(Context context, List<CommonInfo> data, int headerCount, int footerCount){
        mData = data;
        mHeaderCount = headerCount;
        mFooterCount = footerCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Dbug.e(tag, "view type="+ viewType);
        if (ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal() == viewType){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_item, parent, false);
            return new ListViewHolder(view);
        } else if (ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal() == viewType){
//            view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListViewHolder){
            //ListViewHolder holder = (ListViewHolder) viewHolder;
            final CommonInfo commonInfo = mData.get(position);
            if (!TextUtils.isEmpty(commonInfo.getTitle()))
                ((ListViewHolder) holder).mTitle.setText(commonInfo.getTitle());
            if (!TextUtils.isEmpty(commonInfo.getSubtitle()))
                ((ListViewHolder) holder).mSubtitle.setText(commonInfo.getSubtitle());
            if (commonInfo.getSubicon() != null)
                ((ListViewHolder) holder).mSubtitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, commonInfo.getSubicon());
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).mTextView.setText("Exit");
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
        if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else if(mFooterCount != 0 && position >= (mHeaderCount + mData.size())) {//底部View
            return ITEM_TYPE.ITEM_TYPE_FOOTER.ordinal();
        }
        return -1;
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
        return (mHeaderCount + mData.size() + mFooterCount);
    }

    private static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mSubtitle;
        ListViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.item_title);
            mSubtitle = (TextView) itemView.findViewById(R.id.item_subtitle);
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
            mTextView = (TextView) view.findViewById(android.R.id.text1);
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
