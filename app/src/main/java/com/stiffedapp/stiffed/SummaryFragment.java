package com.stiffedapp.stiffed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.view.LineChartView;

public class SummaryFragment extends ListFragment implements OnItemClickListener {

    // Store instance variables
    private String title;
    private int page;

    public String[] list = {"hey","2","hey","hey","5","6","hey","4","5","6","3","hey","5","hey"};
    SummaryCustomAdapter theAdapter;
    ArrayList<Summary> summaryArrayList;
    View mheaderView;

    public SummaryFragment(){}

    // newInstance constructor for creating fragment with arguments
    public static SummaryFragment newInstance(int page, String title) {
        SummaryFragment fragmentFirst = new SummaryFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
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
        // Inflate view
        View view = inflater.inflate(R.layout.summary_main_fragment, container, false);
        mheaderView = inflater.inflate(R.layout.summary_chart_header, null);
        // Fill arraylist
        summaryArrayList = Summary.getSummary();

        // fill custom adapter
        theAdapter = new SummaryCustomAdapter(getContext(), summaryArrayList);

        setListAdapter(theAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // nothing works
//        LineChartView lineChartView = (LineChartView) view.findViewById(R.id.summary_chart);
//        getListView().addHeaderView(lineChartView);

//        View viewport = View.inflate(getContext(), R.layout.summary_chart_header, null);
//        ListView listView = (ListView) getListView().findViewById(android.R.id.list);
//        listView.addHeaderView(viewport);
//        listView.setAdapter(getListAdapter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.summary_chart_header, getListView(), false);
        if(headerView != null) this.getListView().addHeaderView(headerView);
        theAdapter = new SummaryCustomAdapter(getContext(), summaryArrayList);

        setListAdapter(theAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
;
    }
}
