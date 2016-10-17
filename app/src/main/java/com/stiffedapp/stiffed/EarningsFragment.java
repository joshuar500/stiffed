package com.stiffedapp.stiffed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.stiffedapp.stiffed.dummy.DummyContent.DummyItem;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;


public class EarningsFragment extends Fragment implements AdapterView.OnItemClickListener, DatePickerDialog.OnDateSetListener {

    private static final String LOG_TAG = EarningsFragment.class.getSimpleName();

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private String title;
    private String userid;
    private String authToken;
    ListAdapter theAdapter;
    TextView dateRange;
    ImageButton dateRangeBtn;

    public String[] list = {"10","20","300","400","500","6000"};

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EarningsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EarningsFragment newInstance(int columnCount, String title, String userid, String authToken) {
        EarningsFragment fragment = new EarningsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("someTitle", title);
        args.putString("userid", userid);
        args.putString("authtoken", authToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            title = getArguments().getString("someTitle");
            userid = getArguments().getString("userid");
            authToken = getArguments().getString("authtoken");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earnings_main_fragment, container, false);

        // TODO: Move this to controller
        PieChartView pieChartView = (PieChartView) view.findViewById(R.id.earningsPieChart);
        pieChartView.setCircleFillRatio(1.0f);

        int numValues = 6;
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }
        PieChartData pieChartData = new PieChartData(values);
        pieChartView.setPieChartData(pieChartData);

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
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String startLastWeekDate = formatter.format(startLastWeek);
        String endLastWeekDate = formatter.format(endLastWeek);

        // set text
        dateRange = (TextView) view.findViewById(R.id.date_range);
        dateRange.setText("Earnings for " + startLastWeekDate + " – " + endLastWeekDate);

        // date range picker
        dateRangeBtn = (ImageButton) view.findViewById(R.id.date_range_button);
        dateRangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        EarningsFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        int sYear = year;
        int sMonth = monthOfYear+1;
        int sDay = dayOfMonth;
        int eYear = yearEnd;
        int eMonth = monthOfYearEnd+1;
        int eDay = dayOfMonthEnd;
        dateRange.setText("Earnings for " + sMonth + "/" + sDay + "/" + sYear + " – " + eMonth + "/" +  eDay + "/" + eYear);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
