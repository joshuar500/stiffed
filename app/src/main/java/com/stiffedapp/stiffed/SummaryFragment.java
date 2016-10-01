package com.stiffedapp.stiffed;



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
import java.util.ArrayList;


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
        Log.i(LOG_TAG, "initiateRefresh");
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

        // fill the summary list and data chart
        SummaryController summaryController = new SummaryController();
        summaryController.summaryTips(userid, authToken, this);
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
                SummaryController summaryController = new SummaryController();
                summaryController.summaryTips(userid, authToken, sumFrag);
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
        Log.i(LOG_TAG, "onRefreshComplete");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


}
