package com.stockwatch.nosagie.nsetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String COMPANY_PARAM1 = "com.nosegie.param1";

    private HashMap<String,String> companyToDisplay;


    public CompanyDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompanyDetailFragment.
     */
    public static CompanyDetailFragment newInstance(HashMap<String,String> company) {
        CompanyDetailFragment fragment = new CompanyDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(COMPANY_PARAM1, company);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyToDisplay = (HashMap)getArguments().getSerializable(COMPANY_PARAM1);
        }

        //Initialize Adds
        MobileAds.initialize(getActivity().getApplicationContext(), MainContainerActivity.ADMOBSAPPID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String toSet;
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_detail, container, false);


        //For ads
        AdView mAdView = (AdView)rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView companyNameTextView = (TextView)rootView.findViewById(R.id.detailCompanyNameTextView);
        companyNameTextView.setText(companyToDisplay.get(CompanyDirectoryFragment.COMPANYNAME_KEY) +
                        " ("+companyToDisplay.get(CompanyDirectoryFragment.SYMBOL_KEY)+")");

        TextView natureOfBusinessTextView = (TextView)rootView.findViewById(R.id.natureOfBusinessDetailTextView);
        String natureOfBusiness = companyToDisplay.get(CompanyDirectoryFragment.NATUREOFBUSINESS_KEY);
        if(natureOfBusiness == null || natureOfBusiness.equals("null")){
            natureOfBusinessTextView.setVisibility(View.GONE);
        }else{
            toSet = "OPERATIONS: \n" + natureOfBusiness;
            natureOfBusinessTextView.setText(toSet);
        }

        TextView companyProfileTextView = (TextView)rootView.findViewById(R.id.companySummDetailTextView);
        String companyProfile = companyToDisplay.get(CompanyDirectoryFragment.COMPANYPROFILESUMM_KEY);
        if(companyProfile == null || companyProfile.equals("null")){
            companyProfileTextView.setVisibility(View.GONE);
        }else{
            toSet = "PROFILE: \n" + companyProfile;
            companyProfileTextView.setText(toSet);
        }

        TextView companySectorTextView = (TextView)rootView.findViewById(R.id.sectorTextView);
        String companySector = companyToDisplay.get(CompanyDirectoryFragment.SECTOR_KEY);
        if(companySector == null || companySector.equals("null")){
            companySectorTextView.setVisibility(View.GONE);
        }else{
            toSet = "INDUSTRY: \n" + companySector;
            companySectorTextView.setText(toSet);
        }

        TextView addressTextView = (TextView)rootView.findViewById(R.id.addressDetailTextView);
        String address = companyToDisplay.get(CompanyDirectoryFragment.COMPANYADDRESS_KEY);
        if(address==null || address.equals("null")){
            addressTextView.setVisibility(View.GONE);
        }else{
            toSet = "ADDRESS: \n" + address;
            addressTextView.setText(toSet);
        }

        TextView auditorTextView = (TextView)rootView.findViewById(R.id.auditorDetailTextView);
        String auditor = companyToDisplay.get(CompanyDirectoryFragment.AUDITOR_KEY);
        if (auditor == null || auditor.equals("null")){
            auditorTextView.setVisibility(View.GONE);
        }else{
            toSet = "AUDITOR: \n"+auditor;
            auditorTextView.setText(toSet);
        }

        TextView registrarTextView = (TextView)rootView.findViewById(R.id.registrarDetailTextView);
        String registrar = companyToDisplay.get(CompanyDirectoryFragment.REGISTRAR_KEY);
        if(registrar == null || registrar.equals("null")){
            registrarTextView.setVisibility(View.GONE);
        }else{
            toSet = "REGISTRAR: \n" + registrar;
            registrarTextView.setText(toSet);
        }

        TextView secretaryTextView = (TextView)rootView.findViewById(R.id.secretaryDetailTextView);
        String secretary = companyToDisplay.get(CompanyDirectoryFragment.SECRETARY_KEY);
        if(secretary == null || secretary.equals("null")){
            secretaryTextView.setVisibility(View.GONE);
        }else{
            toSet = "SECRETARY: \n" + secretary;
            secretaryTextView.setText(toSet);
        }

        TextView directorsTextView = (TextView)rootView.findViewById(R.id.directorsDetailTextView);
        String directors = companyToDisplay.get(CompanyDirectoryFragment.BOARDOFDIRECTORS_KEY);
        if(directors == null||directors.equals("null")){
            directorsTextView.setVisibility(View.GONE);
        }else {
            toSet = "BOARD OF DIRECTORS: \n"+directors;
            directorsTextView.setText(toSet);
        }

        TextView annualHighTextView = (TextView)rootView.findViewById(R.id.annualHighDetailTextView);
        String annualHigh = companyToDisplay.get(CompanyDirectoryFragment.ANNUALHIGHPRICE_KEY);
        if(annualHigh == null || annualHigh.equals("null")){
            annualHighTextView.setVisibility(View.GONE);
        }else {
            toSet = "52 WEEK HIGH: \n" + annualHigh;
            annualHighTextView.setText(toSet);
        }

        TextView annualLowTextView = (TextView)rootView.findViewById(R.id.annualLowDetailTextView);
        String annualLow = companyToDisplay.get(CompanyDirectoryFragment.ANNUALLOWPRICE_KEY);
        if(annualLow == null || annualLow.equals("null")){
            annualLowTextView.setVisibility(View.GONE);
        }else {
            toSet = "52 WEEK LOW: \n" + annualLow;
            annualLowTextView.setText(toSet);
        }

        TextView marketCapTextView = (TextView)rootView.findViewById(R.id.marketCapDetailTextView);
        String marketCap = companyToDisplay.get(CompanyDirectoryFragment.MARKETCAP_KEY);
        if(marketCap == null || marketCap.equals("null")){
            marketCapTextView.setVisibility(View.GONE);
        }else {
            toSet = "MARKET CAPITALISATION: \n" + MainContainerActivity.CURRENCY + String.format("%,d",Double.valueOf(marketCap).intValue());
            marketCapTextView.setText(toSet);
        }


        return rootView;
    }


}
