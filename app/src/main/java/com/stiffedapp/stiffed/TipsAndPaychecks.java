package com.stiffedapp.stiffed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class TipsAndPaychecks extends AppCompatActivity {

    private ProgressBar firstBar = null;
    private ProgressBar secondBar = null;
    private ProgressBar thirdbar = null;
    private ProgressBar fourthbar = null;
    private ProgressBar fifthbar = null;
    private ProgressBar sixthbar = null;
    private ProgressBar sevenbar = null;
    private Button myButton;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_and_paychecks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tips);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstBar = (ProgressBar) findViewById(R.id.firstBar);
        secondBar = (ProgressBar) findViewById(R.id.secondBar);
        thirdbar = (ProgressBar) findViewById(R.id.thirdbar);
        fourthbar = (ProgressBar) findViewById(R.id.fourthbar);
        fifthbar = (ProgressBar) findViewById(R.id.fifthbar);
        sixthbar = (ProgressBar) findViewById(R.id.sixthbar);
        sevenbar = (ProgressBar) findViewById(R.id.sevenbar);


        firstBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        secondBar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        thirdbar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        fourthbar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        fifthbar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        sixthbar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        sevenbar.getProgressDrawable().setColorFilter(
                Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);

        if (i == 0 || i == 10) {
            //make the progress bar visible
            firstBar.setVisibility(View.VISIBLE);
            firstBar.setMax(150);
            secondBar.setVisibility(View.VISIBLE);
            thirdbar.setVisibility(View.VISIBLE);
            fourthbar.setVisibility(View.VISIBLE);
            fifthbar.setVisibility(View.VISIBLE);
            sixthbar.setVisibility(View.VISIBLE);
            sevenbar.setVisibility(View.VISIBLE);
        } else if (i < firstBar.getMax()) {
            //Set first progress bar value
            firstBar.setProgress(i);
            //Set the second progress bar value
            firstBar.setSecondaryProgress(i + 10);
        } else {
            firstBar.setProgress(0);
            firstBar.setSecondaryProgress(0);
            i = 0;
            firstBar.setVisibility(View.GONE);
            secondBar.setVisibility(View.GONE);
            thirdbar.setVisibility(View.GONE);
            fourthbar.setVisibility(View.GONE);
            fifthbar.setVisibility(View.GONE);
            sixthbar.setVisibility(View.GONE);
            sevenbar.setVisibility(View.GONE);
        }
        i = i + 10;

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
