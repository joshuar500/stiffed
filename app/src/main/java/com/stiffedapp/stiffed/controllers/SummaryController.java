package com.stiffedapp.stiffed.controllers;

import android.graphics.Color;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.SummaryFragment;
import com.stiffedapp.stiffed.adapters.SummaryCustomAdapter;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.beans.User;
import com.stiffedapp.stiffed.models.SummaryModel;
import com.stiffedapp.stiffed.net.ServiceGenerator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.format;

public class SummaryController {
    private StiffedApi stiffedApi;
    private static final String LOG_TAG = SummaryController.class.getSimpleName();

    public SummaryController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void getSummary(final String uid, final String authToken, final String title, final String start_date, final String end_date, final ArrayList<SummaryModel> summary, final SummaryFragment listFragment, final List<PointValue> values) {

        HashMap<String, Object> paramz = new HashMap<>();
        paramz.put("start_date", start_date);
        paramz.put("end_date", end_date);

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(new JSONObject(paramz)).toString());

        Call<Tips> getTipsByDate = stiffedApi.getTipsByDate(uid, body);

        getTipsByDate.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {
                Log.i(LOG_TAG, "getSummary Response message: " + response.message());
                Log.i(LOG_TAG, "getSummary Response body: " + response.raw().body());
                // populate summary
                List<Tip> tips = response.body().getTips();
                if(response.code() != 200) {
                    Toast.makeText(listFragment.getActivity().getBaseContext(), "Something is wrong (cant get tips)", Toast.LENGTH_SHORT).show();
                }

                Double addedTips = 0.0;
                for (Tip tip : tips) {
                    addedTips += tip.getAmount();
                }

                if (addedTips != null && summary != null) {
                    summary.add(new SummaryModel(title, addedTips));
                    SummaryCustomAdapter adapter = new SummaryCustomAdapter(listFragment.getContext(), summary);
                    listFragment.setListAdapter(adapter);
                    Log.i(LOG_TAG, "listAdapter count: " + listFragment.getListAdapter().getCount());
                }

                // populate chart
                //TODO: this needs to be 6 months, currently doing every tip
                if (values != null) {

                    int i = 0;
                    Double january = 0.0;
                    Double february = 0.0;
                    Double march = 0.0;
                    Double april = 0.0;
                    Double may = 0.0;
                    Double june = 0.0;
                    Double july = 0.0;
                    Double august = 0.0;
                    Double september = 0.0;
                    Double october = 0.0;
                    Double november = 0.0;
                    Double december = 0.0;
                    for (Tip tip : tips) {
                        // create dates to check
                        Date date = DateTime.parse(tip.getTipDate(), DateTimeFormat.forPattern("EEE, dd MMM YYYY HH:mm:ss zzz").withLocale(Locale.US)).toDate();
                        DateTime dateTime = new DateTime(date);
                        if (dateTime.getMonthOfYear() == 1) {
                            january += tip.getAmount();
                            Log.i(LOG_TAG, "january tips: " + january);
                        } else if (dateTime.getMonthOfYear() == 2) {
                            february += tip.getAmount();
                            Log.i(LOG_TAG, "february tips: " + february);
                        } else if (dateTime.getMonthOfYear() == 3) {
                            march += tip.getAmount();
                            Log.i(LOG_TAG, "march tips: " + march);
                        } else if (dateTime.getMonthOfYear() == 4) {
                            april += tip.getAmount();
                            Log.i(LOG_TAG, "april tips: " + april);
                        } else if (dateTime.getMonthOfYear() == 5) {
                            may += tip.getAmount();
                            Log.i(LOG_TAG, "may tips: " + may);
                        } else if (dateTime.getMonthOfYear() == 6) {
                            june += tip.getAmount();
                            Log.i(LOG_TAG, "june tips: " + june);
                        } else if (dateTime.getMonthOfYear() == 7) {
                            july += tip.getAmount();
                            Log.i(LOG_TAG, "july tips: " + july);
                        } else if (dateTime.getMonthOfYear() == 8) {
                            august += tip.getAmount();
                            Log.i(LOG_TAG, "august tips: " + august);
                        } else if (dateTime.getMonthOfYear() == 9) {
                            september += tip.getAmount();
                            Log.i(LOG_TAG, "september tips: " + september);
                        } else if (dateTime.getMonthOfYear() == 10) {
                            october += tip.getAmount();
                            Log.i(LOG_TAG, "october tips: " + october);
                        } else if (dateTime.getMonthOfYear() == 11) {
                            november += tip.getAmount();
                            Log.i(LOG_TAG, "november tips: " + november);
                        } else if (dateTime.getMonthOfYear() == 12) {
                            december += tip.getAmount();
                            Log.i(LOG_TAG, "december tips: " + december);
                        }
                    }
                    // for now, these are updated manually
                    values.add(new PointValue(6, october.floatValue()));
                    values.add(new PointValue(5, september.floatValue()));
                    values.add(new PointValue(4, august.floatValue()));
                    values.add(new PointValue(3, july.floatValue()));
                    values.add(new PointValue(2, june.floatValue()));
                    values.add(new PointValue(1, may.floatValue()));
                    values.add(new PointValue(0, april.floatValue()));

                    Line line = new Line(values).setColor(Color.parseColor("#8aacb8")).setCubic(false).setFilled(true).setHasPoints(false);
                    List<Line> lines = new ArrayList<>();
                    lines.add(line);

                    LineChartData data = new LineChartData();
                    data.setLines(lines);
                    LineChartView chartView = (LineChartView) listFragment.getActivity().findViewById(R.id.summary_chart);
                    chartView.setLineChartData(data);
                }
            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {
                Log.i(LOG_TAG, "getSummary Failed to make call: " + call.toString());
                t.printStackTrace();
            }
        });
    }

    public void summaryTips(final String uid, final String authToken, final SummaryFragment listFragment){
        final ArrayList<SummaryModel> summary = new ArrayList<>();
        final ArrayList<Double> summaries = new ArrayList<>();

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);
        Call<Tips> weeklyTips = stiffedApi.weeklyTips(uid);
        if(uid == null || uid.equals("")){
            summaries.add(522.08);
        }

        weeklyTips.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {
                Log.i(LOG_TAG, "Response message: " + response.message());
                Log.i(LOG_TAG, "Response body: " + response.raw().body());

            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {
                // This should be a log
                Log.i(LOG_TAG, "Failed to make call: " + call.toString());
                t.printStackTrace();
            }
        });
    }
}
