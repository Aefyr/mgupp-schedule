package com.aefyr.mgupp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aefyr.mgupp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayPickerAdapter extends RecyclerView.Adapter<DayPickerAdapter.ViewHolder> {
    private ArrayList<DayPickerItem> mData;
    private int mSelectedItem;
    private int mTodayItem;

    private LayoutInflater mInflater;
    private OnItemSelectedListener mListener;

    public DayPickerAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
    }

    public void setData(ArrayList<DayPickerItem> items, int selectedItem, int todayItemIndex) {
        mData = items;
        mSelectedItem = selectedItem;
        mTodayItem = todayItemIndex;
        notifyDataSetChanged();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_day_picker_day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mData.get(position).mTitle);
        holder.title.setTextColor(holder.title.getContext().getResources().getColor(position == mSelectedItem ? R.color.colorAccent : R.color.colorAlmostBlack));

        holder.dot.setVisibility(position == mTodayItem ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private View dot;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            dot = itemView.findViewById(R.id.iv_dot);

            itemView.setOnClickListener(v -> {
                int oldSelectedItem = mSelectedItem;
                mSelectedItem = getAdapterPosition();
                notifyItemChanged(oldSelectedItem);
                notifyItemChanged(mSelectedItem);

                if (mListener != null)
                    mListener.onItemSelected(mData.get(mSelectedItem));
            });
        }
    }

    public static class DayPickerItem {
        private String mTitle;
        private int mPos;

        public DayPickerItem(String title, int pos) {
            mTitle = title;
            mPos = pos;
        }

        public int pos() {
            return mPos;
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(DayPickerItem item);
    }
}
