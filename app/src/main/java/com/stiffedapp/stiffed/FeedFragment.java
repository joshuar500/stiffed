package com.stiffedapp.stiffed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.stiffedapp.stiffed.controllers.SummaryController;
import com.stiffedapp.stiffed.controllers.TipController;
import com.stiffedapp.stiffed.dummy.DummyContent;

import java.util.ArrayList;


public class FeedFragment extends ListFragment implements AdapterView.OnItemClickListener {

    // Store instance variables
    private String title;
    private int page;
    private String userid;
    private String authToken;
    private FeedFragment.OnListFragmentInteractionListener mListener;

    private static final String LOG_TAG = FeedFragment.class.getSimpleName();

    public String[] list = {"10","20","300","400","500","6000"};
    ListAdapter theAdapter;

    // newInstance constructor for creating fragment with arguments
    public static FeedFragment newInstance(int page, String title, String userid, String authToken) {
        FeedFragment fragmentSecond = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putString("userid", userid);
        args.putString("authtoken", authToken);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
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
        View view = inflater.inflate(R.layout.feed_main_fragment, container, false);
        theAdapter = new ArrayAdapter<>(getActivity(), R.layout.feed_list_item, list);
        setListAdapter(theAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createUI();
    }

    private void createUI(){
        setListAdapter(null);
        // fill the feed list and data chart
        TipController tipController = new TipController();
        tipController.getAllTips(userid, authToken, this);
        Log.i(LOG_TAG, "createUI");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedFragment.OnListFragmentInteractionListener) {
            mListener = (FeedFragment.OnListFragmentInteractionListener) context;
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
