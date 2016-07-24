package com.stiffedapp.stiffed;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.stiffedapp.stiffed.beans.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText getAddTip;
    private DatePicker getDatePicker;
    private static final int REQUEST_GET_USER_ID = 0;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = preferences.getString("userid", "");

        if(userID != null || userID.equals("")) {
            // get user details and update
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_GET_USER_ID);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up logo for actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_logo_stiffed);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Setup Viewpager for tabed layout
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        vpPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Setup tabs and position them appropriately
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vpPager);


        ContextThemeWrapper context = new ContextThemeWrapper(this, R.style.MenuButtonsStyle);

        // Floating action button with menu using 3rd party library
        FloatingActionMenu fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        FloatingActionButton fab1 = new FloatingActionButton(context);
        fab1.setLabelText("Add Tip");
        fab1.setImageResource(R.drawable.ic_fab_add);
        fam.addMenuButton(fab1);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.layout_custom_addtip, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setView(alertLayout);

                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Add Tip", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getAddTip = (EditText) alertLayout.findViewById(R.id.et_addedtips);

                        // get date and format it to custom string
                        getDatePicker = (DatePicker) alertLayout.findViewById(R.id.dp_addtips);
                        int day = getDatePicker.getDayOfMonth();
                        int month = getDatePicker.getMonth();
                        int year = getDatePicker.getYear();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                        final String dateString = formatDate.format(calendar.getTime());

                        // TODO: add tip to database via API

                        Toast.makeText(getBaseContext(), "Added tip!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Disable "summary" in navigation menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_summary).setEnabled(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userID = data.getStringExtra("userid");
        System.out.println("MAIN ONACTIVITYRESULT: " + userID);
    }

    public class MyFragmentPageAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Summary", "Feed" };
        private Context context;

        public MyFragmentPageAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SummaryFragment.newInstance(0, "Summary", userID);
                case 1:
                    return FeedFragment.newInstance(0, "Feed");
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Feed";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        //TextView tvNavEmail = (TextView) findViewById(R.id.tv_navEmail);
        //tvNavEmail.setText(user.getEmail());


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_summary) {

        } else if (id == R.id.nav_tips) {
            Intent intent = new Intent(this, TipsAndPaychecks.class);
            startActivity(intent);
        } else if (id == R.id.nav_tip_outs) {

        } else if (id == R.id.nav_earnings) {

        } else if (id == R.id.nav_reports) {

        } else if (id == R.id.nav_calendar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        // TODO: sign user out
        // send back to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userid", userID);
        editor.apply();
    }
}
