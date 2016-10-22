package com.nosagieapp.nsetracker.nsetrackernigeria;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainContainerActivity extends AppCompatActivity {

    public static final String DEVELOPER_EMAIL = "nosagie.a@gmail.com";
    public static final String LOG_TAG = "Fetcher.nsetracker";
    public static final String API_CALL_ERROR_STRING = "NSE site is down, retry later";
    public static final String PARSE_ERROR_STRING = "Error Reading Data, contact developer ";
    public static final String CURRENCY = "₦";
    public static final String ALTERNATE_LIST_COLOR =  "#d9d9d9";
    public static final String NOT_AVAILABLE = "N/A";

    //Titles
    private static final String MARKETSNAPSHOTTITLE = "Market Snapshot";
    private static final String GAINERSLOSERSTITLE = "Gainers & Losers";
    private static final String ALLEQUITIESTITLE = "All Equities";
    private static final String COMPANYDIRECTORYTITLE = "Company Directory";

    public static final String ACTION_BAR_COLOR = "#003300";

    //Buttons
    private Button mMarketSnapshotButton, mGainersandLosersButton, mAllEquitiesButton,mCompanyDirectoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(ACTION_BAR_COLOR)));
        setSupportActionBar(toolbar);

        //TODO:Check if WIFI is On/Connected

         // Add the fragment to the 'fragment_container' FrameLayout
         getSupportFragmentManager().beginTransaction()
               .add(R.id.mainFragmentContainer, new MarketSnapshotFragment()).commit();



        //Assign Buttons and Listeners
        mMarketSnapshotButton = (Button)findViewById(R.id.marketSnapshotButton);
        mMarketSnapshotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(MARKETSNAPSHOTTITLE);
                }
                setFragment(new MarketSnapshotFragment());
            }
        });

        mGainersandLosersButton = (Button)findViewById(R.id.gainersandLosersButton);
        mGainersandLosersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(GAINERSLOSERSTITLE);
                }
                setFragment(new GainersandLosersFragment());
            }
        });

        mAllEquitiesButton = (Button)findViewById(R.id.allEquitiesButton);
        mAllEquitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(ALLEQUITIESTITLE);
                }
                setFragment(new AllEquitiesFragment());
            }
        });

        mCompanyDirectoryButton = (Button)findViewById(R.id.companyDirectoryButton);
        mCompanyDirectoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(COMPANYDIRECTORYTITLE);
                }
                setFragment(new CompanyDirectoryFragment());
            }
        });

    }

    private void setFragment(Fragment toSet){
        FragmentManager fm =  getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.mainFragmentContainer, toSet)//not added to backStack
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_container, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Helper Method to display Toast Error to user
    public static void displayAPiCallErrorToast(Context c){
        Toast.makeText(c,"NSE site is down, please retry",Toast.LENGTH_SHORT);
    }
}
