package com.stiffedapp.stiffed;



import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.stiffedapp.stiffed.controllers.SummaryController;
import com.stiffedapp.stiffed.dummy.DummyContent;
import com.stiffedapp.stiffed.models.SummaryModel;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.PointValue;


public class SummaryFragment extends ListFragment implements OnItemClickListener {

    private static final String LOG_TAG = SummaryFragment.class.getSimpleName();

    // Store instance variables
    private String title;
    private int page;
    private String userid;
    private String authToken;

    public String[] list = {"hey","2","hey","hey","5","6","hey","4","5","6","3","hey","5","hey"};
    View mheaderView;
    SwipeRefreshLayout swipeRefreshLayout;
    private SummaryFragment.OnListFragmentInteractionListener mListener;

    public SummaryFragment(){}

    // newInstance constructor for creating fragment with arguments
    public static SummaryFragment newInstance(int page, String title, String userid, String authToken, ArrayList<Double> summaryList) {
        SummaryFragment fragmentFirst = new SummaryFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("userid", userid);
        args.putString("authtoken", authToken);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        userid = getArguments().getString("userid");
        authToken = getArguments().getString("authtoken");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate view
        final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.summary_main_fragment, container, false);
        swipeRefreshLayout.addView(listFragmentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipeRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Log.i(LOG_TAG, "swiperefreshlayout: " + swipeRefreshLayout.getRootView().toString());
        return swipeRefreshLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SummaryFragment");
                initiateRefresh();
            }
        });
        createUI();
    }

    private void initiateRefresh() {
        new UpdateUITask(this).execute();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void createUI(){
        setListAdapter(null);
        mheaderView = getActivity().getLayoutInflater().inflate(R.layout.summary_chart_header, getListView(), false);
        if(mheaderView != null) getListView().addHeaderView(mheaderView);

        // get dates for this week
        DateTime dateTime = new DateTime( new java.util.Date());
        DateTime weekAgo = dateTime.minusDays( 7 );

        // get dates for last week
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date startLastWeek = c.getTime();
        c.add(Calendar.DATE, 6);
        Date endLastWeek = c.getTime();
        // format last weeks date for python
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startLastWeekDate = formatter.format(startLastWeek);
        String endLastWeekDate = formatter.format(endLastWeek);

        SummaryController summaryController = new SummaryController();
        // fill the summary list and data chart
        final ArrayList<SummaryModel> summary = new ArrayList<>();
        summaryController.getSummary(userid, authToken, summary, this);
    }

    private class UpdateUITask extends AsyncTask<Void, Void, Void> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds
        SummaryFragment sumFrag;

        public UpdateUITask(SummaryFragment sumFrag) {
            this.sumFrag = sumFrag;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // get summary array list from summarycontroller
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onRefreshComplete();
        }
    }

    private void onRefreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SummaryFragment.OnListFragmentInteractionListener) {
            mListener = (SummaryFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }

}
