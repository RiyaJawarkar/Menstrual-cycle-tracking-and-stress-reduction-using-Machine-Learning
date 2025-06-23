package com.example.menstrualapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.menstrualapp.retrofit.database.UserPeriodModel;

import java.util.List;

public class PeriodAdapter extends BaseAdapter {
    private Context context;
    private List<UserPeriodModel> userPeriodModelList;

    public PeriodAdapter(Context context, List<UserPeriodModel> userPeriodModelList) {
        this.context = context;
        this.userPeriodModelList = userPeriodModelList;
    }

    @Override
    public int getCount() {
        return userPeriodModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return userPeriodModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false);
        }

        TextView tvCycleNumber = convertView.findViewById(R.id.tvCycleNumber);
        TextView tvCycleLength = convertView.findViewById(R.id.tvCycleLength);
        TextView tvPeriodDate = convertView.findViewById(R.id.tvPeriodDate);

        UserPeriodModel period = userPeriodModelList.get(position);

        tvCycleNumber.setText("Period Cycle: " + period.getPeriodCycle());
        tvCycleLength.setText("Period Length: " + period.getPeriodLength() + " days");
        tvPeriodDate.setText("Period Date: " + period.getPeriodDate());

        return convertView;
    }
}
