package com.stiffedapp.stiffed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.models.FeedModel;

import java.util.ArrayList;


public class FeedAdapter extends ArrayAdapter<FeedModel> {

    public FeedAdapter(Context context, ArrayList<FeedModel> feed) {
        super(context, 0, feed);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FeedModel sModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feed_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvAction = (TextView) convertView.findViewById(R.id.feed_item_action);
        TextView tvDate = (TextView) convertView.findViewById(R.id.feed_item_date);
        TextView tvEdit = (TextView) convertView.findViewById(R.id.feed_item_interact);
        // Populate the data into the template view using the data object
        // Return the completed view to render on screen
        tvAction.setText(sModel.action);
        tvDate.setText(sModel.date);
        tvEdit.setText(sModel.interact);

        return convertView;
    }
}
