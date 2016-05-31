package com.stiffedapp.stiffed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


public class FeedFragment extends ListFragment implements AdapterView.OnItemClickListener {

    // Store instance variables
    private String title;
    private int page;

    public String[] list = {"10","20","300","400","500","6000"};
    ListAdapter theAdapter;

    // newInstance constructor for creating fragment with arguments
    public static FeedFragment newInstance(int page, String title) {
        FeedFragment fragmentSecond = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_main_fragment, container, false);

        theAdapter = new ArrayAdapter<String>(getActivity(), R.layout.feed_list_item, list);

        setListAdapter(theAdapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
