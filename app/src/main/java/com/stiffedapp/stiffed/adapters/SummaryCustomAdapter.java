package com.stiffedapp.stiffed.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.controllers.SummaryController;

public class SummaryCustomAdapter extends ArrayAdapter<SummaryController> {
    public SummaryCustomAdapter(Context context, ArrayList<SummaryController> summaries) {
        super(context, 0, summaries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SummaryController summary = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvSummary= (TextView) convertView.findViewById(R.id.tvNumber);
        // Populate the data into the template view using the data object
        tvName.setText(summary.sName);
        tvSummary.setText(summary.sNumber);
        // Return the completed view to render on screen
        return convertView;
    }
}