package com.stiffedapp.stiffed.controllers;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.SummaryFragment;
import com.stiffedapp.stiffed.adapters.SummaryAdapter;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Summary;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.models.SummaryModel;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryController {
    private StiffedApi stiffedApi;
    private static final String LOG_TAG = SummaryController.class.getSimpleName();

    public SummaryController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void getSummary(final String uid, final String authToken, final ArrayList<SummaryModel> summary, final SummaryFragment listFragment) {

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);

        Call<Summary> getSummaryCall = stiffedApi.getSummary(uid);

        getSummaryCall.enqueue(new Callback<Summary>() {
            @Override
            public void onResponse(Call<Summary> call, Response<Summary> response) {
                if(response.code() != 200) {
                    Toast.makeText(listFragment.getActivity().getBaseContext(), "Something is wrong (cant get tips)", Toast.LENGTH_SHORT).show();
                    // TODO: Close application
                }

                List<Tip> thisWeek = response.body().getThisWeek();
                List<Tip> lastWeek = response.body().getLastWeek();

                Double thisWeeksTips = 0.0;
                for (Tip tip : thisWeek) {
                    if(tip.getAmount() != null) {
                        thisWeeksTips += tip.getAmount();
                    }
                    if(tip.getTipOutAmount() != null) {
                        thisWeeksTips -= tip.getTipOutAmount();
                    }
                }
                Double lastWeeksTips = 0.0;
                for (Tip tip : lastWeek) {
                    if(tip.getAmount() != null) {
                        lastWeeksTips += tip.getAmount();
                    }
                    if(tip.getTipOutAmount() != null) {
                        lastWeeksTips -= tip.getTipOutAmount();
                    }
                }

                if (thisWeeksTips != null && summary != null) {
                    summary.add(new SummaryModel("This Week", thisWeeksTips));
                    summary.add(new SummaryModel("Last Week", lastWeeksTips));
                    SummaryAdapter adapter = new SummaryAdapter(listFragment.getContext(), summary);
                    listFragment.setListAdapter(adapter);
                }

                // get last 6 months
                List<Tip> lastMonth = response.body().getLastMonth();
                List<Tip> twoMonth = response.body().getTwoMonth();
                List<Tip> threeMonth = response.body().getThreeMonth();
                List<Tip> fourMonth = response.body().getFourMonth();
                List<Tip> fiveMonth = response.body().getFiveMonth();
                List<Tip> sixMonth = response.body().getSixMonth();

                // variables for added tips
                Double mOne = 0.0;
                Double mTwo = 0.0;
                Double mThree = 0.0;
                Double mFour = 0.0;
                Double mFive = 0.0;
                Double mSix = 0.0;

                for (Tip tip : lastMonth) {
                    mOne += tip.getAmount();
                }
                for (Tip tip : twoMonth) {
                    mTwo += tip.getAmount();
                }
                for (Tip tip : threeMonth) {
                    mThree += tip.getAmount();
                }
                for (Tip tip : fourMonth) {
                    mFour += tip.getAmount();
                }
                for (Tip tip : fiveMonth) {
                    mFive += tip.getAmount();
                }
                for (Tip tip : sixMonth) {
                    mSix += tip.getAmount();
                }

                // get months
                // TODO: probably want to do this server side in the future
                Calendar cal = Calendar.getInstance();  //Get current date/month i.e 27 Feb, 2012
                SimpleDateFormat formatter = new SimpleDateFormat("MMM");

                cal.add(Calendar.MONTH, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m1Date = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m2Date = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m3Date = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m4Date = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m5Date = cal.getTime();
                cal.add(Calendar.MONTH, -1);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                Date m6Date = cal.getTime();

                String m1Name = formatter.format(m1Date);
                String m2Name = formatter.format(m2Date);
                String m3Name = formatter.format(m3Date);
                String m4Name = formatter.format(m4Date);
                String m5Name = formatter.format(m5Date);
                String m6Name = formatter.format(m6Date);

                // update month names for summary graph
                TextView tvMonthOne = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthOne);
                TextView tvMonthTwo = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthTwo);
                TextView tvMonthThree = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthThree);
                TextView tvMonthFour = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthFour);
                TextView tvMonthFive = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthFive);
                TextView tvMonthSix = (TextView) listFragment.getActivity().findViewById(R.id.tvMonthSix);
                tvMonthOne.setText(m6Name.substring(0, 3).toUpperCase());
                tvMonthTwo.setText(m5Name.substring(0, 3).toUpperCase());
                tvMonthThree.setText(m4Name.substring(0, 3).toUpperCase());
                tvMonthFour.setText(m3Name.substring(0, 3).toUpperCase());
                tvMonthFive.setText(m2Name.substring(0, 3).toUpperCase());
                tvMonthSix.setText(m1Name.substring(0, 3).toUpperCase());

                // fill chart with correct values
                List<PointValue> values = new ArrayList<>();

                // for now, these are updated manually
                values.add(new PointValue(1, mSix.floatValue()));
                values.add(new PointValue(2, mFive.floatValue()));
                values.add(new PointValue(3, mFour.floatValue()));
                values.add(new PointValue(4, mThree.floatValue()));
                values.add(new PointValue(5, mTwo.floatValue()));
                values.add(new PointValue(6, mOne.floatValue()));

                Line line = new Line(values).setColor(Color.parseColor("#8aacb8")).setCubic(false).setFilled(true).setHasPoints(false);
                List<Line> lines = new ArrayList<>();
                lines.add(line);

                LineChartData data = new LineChartData();
                data.setLines(lines);
                LineChartView chartView = (LineChartView) listFragment.getActivity().findViewById(R.id.summary_chart);
                chartView.setLineChartData(data);

            }

            @Override
            public void onFailure(Call<Summary> call, Throwable t) {
                Log.e(LOG_TAG, "getSummary Failed to make call: " + call.toString());
                t.printStackTrace();
            }
        });
    }
}
