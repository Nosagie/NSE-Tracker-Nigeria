package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyDirectoryDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyDirectoryDialogFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DIRECTORY_COMPANY_KEY = "com.nosagie.android.nsetracker.nigeria";

    private HashMap<String,String> companyInfo;


    public CompanyDirectoryDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    public static CompanyDirectoryDialogFragment newInstance(HashMap<String,String> companyInfo) {
        CompanyDirectoryDialogFragment fragment = new CompanyDirectoryDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIRECTORY_COMPANY_KEY, companyInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyInfo = (HashMap)getArguments().getSerializable(ARG_DIRECTORY_COMPANY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String toSet;

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_company_directory_dialog_fragment, container, false);

        TextView companyNameLabel = (TextView)rootView.findViewById(R.id.directoryDialogCompanyLabel);
        companyNameLabel.setText(companyInfo.get(CompanyDirectoryFragment.COMPANYNAME_KEY));

        //For website
        Button companyWebsite = (Button)rootView.findViewById(R.id.visitWebsiteButton);
        String website = companyInfo.get(CompanyDirectoryFragment.WEBSITE_KEY);
        if(website == null || website.equals("null")){
            companyWebsite.setVisibility(View.GONE);
        }else {
            //Start (implicit)intent to make visit website TODO
            toSet = "Visit Website";
            companyWebsite.setText(toSet);

        }
        companyWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //For Telephone Call
        Button phoneCall = (Button)rootView.findViewById(R.id.callCompanyButton);
        if(website == null || website.equals("null")){
            toSet = "Phone Unavailable";
            phoneCall.setVisibility(View.GONE);
        }else {
            toSet = "Call " + companyInfo.get(CompanyDirectoryFragment.TELEPHONE_KEY);
            phoneCall.setText(toSet);
            //Start (implicit)intent to make user call company TODO
        }


        //For Detailed Company Information in Activity Pager
        Button detailCompanyButton =  (Button)rootView.findViewById(R.id.companyDetailsButton);
        detailCompanyButton.setText("Details");
        detailCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start new Pager Activity with list
            }
        });

        return rootView;
    }

}
