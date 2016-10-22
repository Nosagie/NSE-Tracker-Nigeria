package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EquitiesDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquitiesDialogFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EQUITY_KEY = "com.nsetracker.equitydialog.nigeria";


    // TODO: Rename and change types of parameters
    private HashMap<String,String> equityHashMap;


    public EquitiesDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EquitiesDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EquitiesDialogFragment newInstance(HashMap<String,String> hashMap) {
        EquitiesDialogFragment fragment = new EquitiesDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EQUITY_KEY, hashMap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            equityHashMap = (HashMap)getArguments().getSerializable(ARG_EQUITY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView symbolTextView,marketTextView,sectorTextView,tradesTextView,volumeTextView,prevCloseTextView;
        TextView currentPriceTextView;

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_equities_dialog, container, false);

        symbolTextView = (TextView)rootView.findViewById(R.id.equityDialogSymbolTextView);
        symbolTextView.setText(equityHashMap.get(AllEquitiesFragment.SYMBOL_KEY));

        marketTextView = (TextView)rootView.findViewById(R.id.equityDialogMarketTextView);
        marketTextView.setText(equityHashMap.get(AllEquitiesFragment.MARKET_KEY));

        sectorTextView = (TextView)rootView.findViewById(R.id.equityDialogSectorTextView);
        sectorTextView.setText(equityHashMap.get(AllEquitiesFragment.SECTOR_KEY));

        tradesTextView = (TextView)rootView.findViewById(R.id.equityDialogTradesTextView);
        String toSet = "Trade(s): " + equityHashMap.get(AllEquitiesFragment.TRADES_KEY);
        tradesTextView.setText(toSet);

        volumeTextView = (TextView)rootView.findViewById(R.id.equityDialogVolumeTextView);
        if(equityHashMap.get(AllEquitiesFragment.VOLUME_KEY) == null || equityHashMap.get(AllEquitiesFragment.VOLUME_KEY).equals("null")){
            toSet = "Volume: 0";
        }else {
            Double num = Double.valueOf(equityHashMap.get(AllEquitiesFragment.VOLUME_KEY));
            toSet = "Volume : " + String.format("%,d",num.intValue()) + " units";
        }
        volumeTextView.setText(toSet);

        prevCloseTextView = (TextView)rootView.findViewById(R.id.prevCloseTextView);
        if(equityHashMap.get(AllEquitiesFragment.PREV_CLOSING_PRICE_KEY) == null || equityHashMap.get(AllEquitiesFragment.PREV_CLOSING_PRICE_KEY).equals("null")){
            toSet = "Prev Close Price Not Available";
        }else {
            Double num = Double.valueOf(equityHashMap.get(AllEquitiesFragment.PREV_CLOSING_PRICE_KEY));
            toSet = "Prev Close: " + MainContainerActivity.CURRENCY + String.format("%.2f",num);
        }
        prevCloseTextView.setText(toSet);

        currentPriceTextView = (TextView)rootView.findViewById(R.id.currentPriceTextView);
        if(equityHashMap.get(AllEquitiesFragment.CLOSE_PRICE_KEY) == null || equityHashMap.get(AllEquitiesFragment.CLOSE_PRICE_KEY).equals("null")){
            Double num = Double.valueOf(equityHashMap.get(AllEquitiesFragment.CLOSE_PRICE_KEY));
            toSet = "Today's Close: " + MainContainerActivity.CURRENCY + String.format("%.2f",num);
        }else {
            Double num = Double.valueOf(equityHashMap.get(AllEquitiesFragment.CLOSE_PRICE_KEY));
            toSet = "Today's Close " + MainContainerActivity.CURRENCY + String.format("%.2f",num);
        }
        currentPriceTextView.setText(toSet);





        return rootView;
    }




}
