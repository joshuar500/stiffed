package com.stiffedapp.stiffed.controllers;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.stiffedapp.stiffed.FeedFragment;
import com.stiffedapp.stiffed.R;
import com.stiffedapp.stiffed.adapters.FeedAdapter;
import com.stiffedapp.stiffed.adapters.SummaryAdapter;
import com.stiffedapp.stiffed.api.StiffedApi;
import com.stiffedapp.stiffed.beans.Tip;
import com.stiffedapp.stiffed.beans.Tips;
import com.stiffedapp.stiffed.models.FeedModel;
import com.stiffedapp.stiffed.net.ServiceGenerator;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedController {

    private StiffedApi stiffedApi;
    private static final String LOG_TAG = FeedController.class.getSimpleName();

    public FeedController() {
        this.stiffedApi = ServiceGenerator.createService(StiffedApi.class);
    }

    public void getFeed(final String uid, final String authToken, final FeedFragment listFragment) {

        final ArrayList<FeedModel> feedArray = new ArrayList<>();

        StiffedApi stiffedApi = ServiceGenerator.createService(StiffedApi.class, authToken);

        Call<Tips> getFeedCall = stiffedApi.getFeed(uid);

        getFeedCall.enqueue(new Callback<Tips>() {
            @Override
            public void onResponse(Call<Tips> call, Response<Tips> response) {
                if(response.code() != 200) {
                    Toast.makeText(listFragment.getActivity().getBaseContext(), "Something is wrong (cant get tips)", Toast.LENGTH_SHORT).show();
                    // TODO: Close application or do something else
                }

                List<Tip> tips = response.body().getTips();

                for (Tip tip : tips) {
                    if (tip.getAmount() != null) {
                        feedArray.add(new FeedModel(tip.getTipDate(), tip.getAmount().toString(), "edit"));
                    }
                    if (tip.getTipOutAmount() != null) {
                        feedArray.add(new FeedModel(tip.getTipDate(), tip.getTipOutAmount().toString(), "delete"));
                    }
                }

                FeedAdapter adapter = new FeedAdapter(listFragment.getContext(), feedArray);
                listFragment.setListAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Tips> call, Throwable t) {
                Log.e(LOG_TAG, "getFeed Failed to make call: " + call.toString());
                t.printStackTrace();
            }
        });

    }

}
