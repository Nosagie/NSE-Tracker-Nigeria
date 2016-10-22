package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private static final String ARG_DIRECTORY_COMPANY_POSITION = "com.nosagie.android.nsetracker.nigeria.position";

    private HashMap<String,String> companyInfo;

    private int position;


    public CompanyDirectoryDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    public static CompanyDirectoryDialogFragment newInstance(HashMap<String,String> companyInfo,int position) {
        CompanyDirectoryDialogFragment fragment = new CompanyDirectoryDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIRECTORY_COMPANY_KEY, companyInfo);
        args.putInt(ARG_DIRECTORY_COMPANY_POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyInfo = (HashMap)getArguments().getSerializable(ARG_DIRECTORY_COMPANY_KEY);
            position = getArguments().getInt(ARG_DIRECTORY_COMPANY_POSITION);
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
        final String website = companyInfo.get(CompanyDirectoryFragment.WEBSITE_KEY);
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
                //Browser Intent
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+website));
                startActivity(browserIntent);

            }
        });

        //For Telephone Call
        Button phoneCall = (Button)rootView.findViewById(R.id.callCompanyButton);
        final String phoneNumber = companyInfo.get(CompanyDirectoryFragment.TELEPHONE_KEY);
        if(phoneNumber == null || phoneNumber.equals("null")){
            phoneCall.setVisibility(View.GONE);
        }else {
            toSet = "Call " + companyInfo.get(CompanyDirectoryFragment.TELEPHONE_KEY);
            phoneCall.setText(toSet);
        }
        phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start (implicit)intent to make user call company
                Intent phoneCallIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel: " + phoneNumber));
                startActivity(phoneCallIntent);
            }
        });

        //View Company Address in Maps
        final Button companyAddressButton = (Button)rootView.findViewById(R.id.viewCompanyInMapsButton);
        final String companyAddress = companyInfo.get(CompanyDirectoryFragment.COMPANYADDRESS_KEY);
        if(companyAddress == null || companyAddress.equals("null")){
            companyAddressButton.setVisibility(View.GONE);
        }else{
            toSet = "View in Maps";
            companyAddressButton.setText(toSet);
        }
        companyAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start maps intent
                Uri parsed = Uri.parse("geo:0,0?q=" + Uri.encode(companyAddress));
                Intent maps = new Intent (Intent.ACTION_VIEW,parsed);
                if(maps.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(maps);
                }else{
                    Log.d("No handling","No maps app");
                    companyAddressButton.setClickable(false);
                }
            }
        });

        //For Detailed Company Information
        Button detailCompanyButton =  (Button)rootView.findViewById(R.id.companyDetailsButton);
        detailCompanyButton.setText("Details");
        detailCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start new Pager Activity with list
                Intent companyList = new Intent(getActivity(),CompanyDetailSlideActivity.class);
                companyList.putExtra(CompanyDetailSlideActivity.DIRECTORYDEATAIL_EXTRA,CompanyDirectoryFragment.getCompanyDirectory());
                companyList.putExtra(CompanyDetailSlideActivity.DIRECTORYDEATAILPOSITION_EXTRA,position);
                startActivity(companyList);
            }
        });

        return rootView;
    }

}
