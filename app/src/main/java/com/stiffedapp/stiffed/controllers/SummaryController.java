package com.stiffedapp.stiffed.controllers;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.SummaryFragment;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryController {
    private StiffedApi stiffedApi;
    private static final String LOG_TAG = SummaryController.class.getSimpleName();

    public SummaryController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void summaryTips(final String uid, final String authToken, final SummaryFragment listFragment){
        final ArrayList<Double> summaries;
        summaries = new ArrayList<>();

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);
        Call<Tips> weeklyTips = stiffedApi.weeklyTips(uid);
        if(uid == null || uid.equals("")){
            summaries.add(522.08);
        }

        weeklyTips.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {
                List<Tip> tips = response.body().getTips();
                if (tips == null) {
                    Toast.makeText(listFragment.getActivity().getBaseContext(), "Something is wrong (cant get tips)", Toast.LENGTH_SHORT).show();
                }
                Double addedTips = 0.0;
                for(Tip tip : tips){
                    addedTips += tip.getAmount();
                }
                if (addedTips != null) {
                    summaries.add(addedTips);
                }
                summaries.add(82.08);
                summaries.add(42.08);
                summaries.add(23.412);
                if(listFragment.getListAdapter() != null) {
                    ArrayAdapter<Double> oldAdapter = (ArrayAdapter<Double>) listFragment.getListAdapter();
                    oldAdapter.clear();
                }

                ListAdapter adapter = new ArrayAdapter<>(listFragment.getContext(), R.layout.summary_list_item, R.id.tvNumber, summaries);
                listFragment.setListAdapter(adapter);
                Log.i(LOG_TAG, "listAdapter count: " + listFragment.getListAdapter().getCount());
                // populate chart
                // TODO: This should be the last six months. Currently it is all 4 summary entries (tips this week, wages this week, etc.)
                List<PointValue> values = new ArrayList<>();
                int i = 0;
                for(Double tip : summaries){
                    values.add(new PointValue(i, tip.floatValue()));
                    i = i + 1;
                }

                Line line = new Line(values).setColor(Color.parseColor("#8aacb8")).setCubic(false).setFilled(true).setHasPoints(false);
                List<Line> lines = new ArrayList<>();
                lines.add(line);

                LineChartData data = new LineChartData();
                data.setLines(lines);
                LineChartView chartView = (LineChartView) listFragment.getActivity().findViewById(R.id.summary_chart);
                chartView.setLineChartData(data);
            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {
                // This should be a log
                System.out.println("FAILED TO MAKE CALL");
            }
        });
    }
}
