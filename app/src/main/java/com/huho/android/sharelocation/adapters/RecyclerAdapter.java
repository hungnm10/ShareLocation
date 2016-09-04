
package com.huho.android.sharelocation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.adapters.viewholder.RecyclerItemViewHolder;
import com.huho.android.sharelocation.utils.objects.Channel;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static OnItemClickListener mOnItemClickListener;

    private ArrayList<Channel> mItemList;

    public RecyclerAdapter(ArrayList<Channel> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent,
                false);
        return RecyclerItemViewHolder.newInstance(view, mOnItemClickListener);
    }

    /**
     * Binds a correct View for the Adapter
     * 
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        holder.setChannel(mItemList.get(position));
    }

    /**
     * Get total item count
     * 
     * @return
     */
    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    // our new getItemCount() that includes header View

    /**
     * Get total of item count and header
     * 
     * @return
     */
    @Override
    public int getItemCount() {
        return getBasicItemCount();
    }

    public void remove(int position) {
        mItemList.remove(position);
        notifyItemRemoved(position);
    }

    public Channel getItem(int position) {
        return mItemList.get(position);
    }

    public interface OnItemClickListener {
        public void onItemClick(int channelid);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
