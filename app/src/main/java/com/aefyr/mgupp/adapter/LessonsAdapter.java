package com.aefyr.mgupp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aefyr.mgupp.R;
import com.aefyr.mgupp.util.Util;
import com.aefyr.mgupp.api.model.Day;
import com.aefyr.mgupp.api.model.Lesson;

import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {
    private Day mDay;
    private boolean mOdd;
    private LayoutInflater mInflater;
    private SimpleDateFormat mSdf;

    public LessonsAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
        mSdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    public void setDay(Day day, boolean odd) {
        mDay = day;
        mOdd = odd;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lesson lesson = mDay.lessons(mOdd).get(position);


        holder.name.setText(lesson.name());
        holder.times.setText(mSdf.format(lesson.starts()) + " - " + mSdf.format(lesson.ends()));
        holder.additionalInfo.setText("\uD83C\uDFE0 " + lesson.room() + "\n\uD83D\uDC68\u200D\uD83C\uDFEB " + lesson.teacher());
    }

    @Override
    public int getItemCount() {
        return mDay == null ? 0 : mDay.lessons(mOdd).size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView additionalInfo;
        private TextView times;

        private ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_lesson_title);
            additionalInfo = itemView.findViewById(R.id.tv_lesson_additional);
            times = itemView.findViewById(R.id.tv_lesson_times);

        }
    }
}
