package com.stiffedapp.stiffed;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.stiffedapp.stiffed.adapters.SummaryCustomAdapter;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.controllers.SummaryController;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;

public class SummaryFragment extends ListFragment implements OnItemClickListener {

    // Store instance variables
    private String title;
    private int page;
    private String userid;

    public String[] list = {"hey","2","hey","hey","5","6","hey","4","5","6","3","hey","5","hey"};
    SummaryCustomAdapter theAdapter;
    ArrayList<SummaryController> summaryArrayList;
    View mheaderView;

    public SummaryFragment(){}

    // newInstance constructor for creating fragment with arguments
    public static SummaryFragment newInstance(int page, String title, String userid) {
        SummaryFragment fragmentFirst = new SummaryFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("userid", userid);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        userid = getArguments().getString("userid");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.summary_main_fragment, container, false);
        mheaderView = inflater.inflate(R.layout.summary_chart_header, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(null);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.summary_chart_header, getListView(), false);
        if(headerView != null) this.getListView().addHeaderView(headerView);

        // Fill arraylist
        summaryArrayList = SummaryController.getSummary("{'asdf':'asdf'}");
        theAdapter = new SummaryCustomAdapter(getContext(), summaryArrayList);
        setListAdapter(theAdapter);

        System.out.println(userid);
        System.out.println(userid);
        System.out.println(userid);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 0));
        values.add(new PointValue(1, 6));
        values.add(new PointValue(2, 5));
        values.add(new PointValue(3, 6));
        values.add(new PointValue(4, 5));
        values.add(new PointValue(5, 6));

        Line line = new Line(values).setColor(Color.parseColor("#8aacb8")).setCubic(false).setHasPoints(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chartView = (LineChartView) getActivity().findViewById(R.id.summary_chart);
        chartView.setLineChartData(data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
;
    }
}
