package com.nosagieapp.nsetracker.nsetrackernigeria;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyDetailSlideActivity extends AppCompatActivity {

    private static Integer num_pages,companyIndex;
    private static ArrayList<HashMap<String,String>> directory;

    private static ViewPager pager;
    private static PagerAdapter pagerAdapter;

    public static final String DIRECTORYDEATAIL_EXTRA = "com.nosagie.nsetracker.directorydetailtag";
    public static final String DIRECTORYDEATAILPOSITION_EXTRA = "com.nosagie.nsetracker.directorydetailtag.position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO error handling
        directory = (ArrayList<HashMap<String,String>>)getIntent().getSerializableExtra(DIRECTORYDEATAIL_EXTRA);
        companyIndex = getIntent().getIntExtra(DIRECTORYDEATAILPOSITION_EXTRA, 0);

        num_pages = directory.size();

        setContentView(R.layout.activity_company_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(MainContainerActivity.ACTION_BAR_COLOR)));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("COMPANY DETAILS");

        //start viewpager and pageradapter
        pager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new companyDetailPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(companyIndex);
    }

    //Pager Adapter
    private class companyDetailPagerAdapter extends FragmentStatePagerAdapter{

        public companyDetailPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CompanyDetailFragment().newInstance(directory.get(position));
        }

        @Override
        public int getCount() {
            return num_pages;
        }

    }

}
