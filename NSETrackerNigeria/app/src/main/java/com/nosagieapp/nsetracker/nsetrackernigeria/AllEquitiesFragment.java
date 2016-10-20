package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AllEquitiesFragment extends Fragment {

    private static TextView allEquitiesErrorTextView;

    private static ProgressBar progressBar;

    //HashMap to store all Equities
    private ArrayList<HashMap<String,String>> allEquities;

    //Keys for JSON API Call
    private final String SYMBOL_KEY = "Symbol";
    private final String PREV_CLOSING_PRICE_KEY = "PrevClosingPrice";
    private final String OPENING_PRICE_KEY = "OpeningPrice";
    private final String HIGH_PRICE_KEY = "HighPrice";
    private final String LOW_PRICE_KEY = "LowPrice";
    private final String CLOSE_PRICE_KEY = "ClosePrice";
    private final String CHANGE_KEY = "Change";
    private final String PERC_CHANGE_KEY = "PercChange";
    private final String TRADES_KEY = "Trades";
    private final String VOLUME_KEY = "Volume";
    private final String VALUE_KEY = "Value";
    private final String MARKET_KEY = "Market";
    private final String SECTOR_KEY = "Sector";
    private final String COMPANY2_KEY = "Company2"; //same as symbol but returned with API call

    public AllEquitiesFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new fetchAllEquitiesTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_all_equities, container, false);

        progressBar = (ProgressBar)rootView.findViewById(R.id.allEquitiesProgressBar);

        allEquitiesErrorTextView = (TextView)rootView.findViewById(R.id.allEquitiesErrorTextView);
        allEquitiesErrorTextView.setMovementMethod(new ScrollingMovementMethod());

        return rootView;
    }

    private class fetchAllEquitiesTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            String unparsedJSON = Fetcher.fetchAllEquities();

            return unparsedJSON.substring(4);//removes "null" at beginning of result
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);

            if(s == null || s.equals("null")){
                allEquitiesErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
            }else{

                try {
                    allEquities = new ArrayList<>();
                    JSONArray allEquitiesJSON = new JSONArray(s);
                    JSONObject jsonSymbolToAdd;
                    HashMap<String, String> symbolToAdd;
                    String symbol,market,sector,company2;
                    String prevClosingPrice,openingPrice,highPrice,lowPrice,change,percChange,value,trades,volume;;

                    for (int i = 0 ; i < allEquitiesJSON.length();i++){
                        jsonSymbolToAdd = allEquitiesJSON.getJSONObject(i);

                        //Get Values
                        symbol = jsonSymbolToAdd.getString(SYMBOL_KEY);
                        market = jsonSymbolToAdd.getString(MARKET_KEY);
                        sector = jsonSymbolToAdd.getString(SECTOR_KEY);
                        company2 = jsonSymbolToAdd.getString(COMPANY2_KEY);
                        trades = jsonSymbolToAdd.getString(TRADES_KEY);
                        volume = jsonSymbolToAdd.getString(VOLUME_KEY);
                        prevClosingPrice = jsonSymbolToAdd.getString(PREV_CLOSING_PRICE_KEY);
                        openingPrice = jsonSymbolToAdd.getString(OPENING_PRICE_KEY);
                        highPrice = jsonSymbolToAdd.getString(HIGH_PRICE_KEY);
                        lowPrice = jsonSymbolToAdd.getString(LOW_PRICE_KEY);
                        change = jsonSymbolToAdd.getString(CHANGE_KEY);
                        percChange = jsonSymbolToAdd.getString(PERC_CHANGE_KEY);
                        value = jsonSymbolToAdd.getString(VALUE_KEY);

                        //Format Integers and Doubles

                        //Create HashMap and add to List
                        symbolToAdd = new HashMap<>();
                        symbolToAdd.put(SYMBOL_KEY,symbol);
                        symbolToAdd.put(MARKET_KEY,market);
                        symbolToAdd.put(SECTOR_KEY,sector);
                        symbolToAdd.put(COMPANY2_KEY,company2);
                        symbolToAdd.put(TRADES_KEY,trades.toString());
                        symbolToAdd.put(VOLUME_KEY,volume.toString());
                        symbolToAdd.put(PREV_CLOSING_PRICE_KEY,prevClosingPrice.toString());
                        symbolToAdd.put(OPENING_PRICE_KEY,openingPrice.toString());
                        symbolToAdd.put(HIGH_PRICE_KEY,highPrice.toString());
                        symbolToAdd.put(LOW_PRICE_KEY,lowPrice.toString());
                        symbolToAdd.put(CHANGE_KEY,change.toString());
                        symbolToAdd.put(PERC_CHANGE_KEY,percChange.toString());
                        symbolToAdd.put(VALUE_KEY,value.toString());

                        allEquities.add(symbolToAdd);

                    }

                    allEquitiesErrorTextView.setText(allEquities.get(0).get(SYMBOL_KEY) + "\n\n" + allEquities.get(5).get(SYMBOL_KEY));


                }catch(JSONException e ){
                    Log.e(MainContainerActivity.LOG_TAG, e.toString() + " " + MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    allEquitiesErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                }

            }
        }
    }

}
