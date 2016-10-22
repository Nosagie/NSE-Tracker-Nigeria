package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
            getActivity().setTitle(companyToDisplay.get(CompanyDirectoryFragment.COMPANYNAME_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_detail, container, false);

        TextView test = (TextView)rootView.findViewById(R.id.detailSymbolTextView);
        test.setText(companyToDisplay.get(CompanyDirectoryFragment.COMPANYNAME_KEY));


        return rootView;
    }

}
