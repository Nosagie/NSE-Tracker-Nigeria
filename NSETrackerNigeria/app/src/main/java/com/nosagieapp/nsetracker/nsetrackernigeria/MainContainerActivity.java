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

    //Buttons
    private Button mMarketSnapshotButton, mGainersandLosersButton, mAllEquitiesButton,mCompanyDirectoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003300")));
        setSupportActionBar(toolbar);

         // Add the fragment to the 'fragment_container' FrameLayout
         getSupportFragmentManager().beginTransaction()
               .add(R.id.mainFragmentContainer, new MarketSnapshotFragment()).commit();



        //Assign Buttons and Listeners
        mMarketSnapshotButton = (Button)findViewById(R.id.marketSnapshotButton);
        mMarketSnapshotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new MarketSnapshotFragment());
            }
        });

        mGainersandLosersButton = (Button)findViewById(R.id.gainersandLosersButton);
        mGainersandLosersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new GainersandLosersFragment());
            }
        });

        mAllEquitiesButton = (Button)findViewById(R.id.allEquitiesButton);
        mAllEquitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new AllEquitiesFragment());
            }
        });

        mCompanyDirectoryButton = (Button)findViewById(R.id.companyDirectoryButton);
        mCompanyDirectoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        Toast.makeText(c,"NSE site is down, retrying data call",Toast.LENGTH_SHORT);
    }
}
