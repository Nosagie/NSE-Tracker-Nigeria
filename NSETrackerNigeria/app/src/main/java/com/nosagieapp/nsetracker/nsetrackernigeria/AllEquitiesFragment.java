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
    private final String Prev_Closing_Price_KEY = "PrevClosingPrice";
    private final String Opening_Price_KEY = "OpeningPrice";
    private final String High_Price_KEY = "HighPrice";
    private final String Low_Price_KEY = "LowPrice";
    private final String Close_Price_KEY = "ClosePrice";
    private final String Change_KEY = "Change";
    private final String Perc_Change_KEY = "PercChange";
    private final String Trades_KEY = "Trades";
    private final String Volume_KEY = "Volume";
    private final String Value_KEY = "Value";
    private final String Market_KEY = "Market";
    private final String Sector_KEY = "Sector";
    private final String Company2_KEY = "Company2"; //same as symbol but returned with API call

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
            allEquitiesErrorTextView.setText(s);

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
                        market = jsonSymbolToAdd.getString(Market_KEY);
                        sector = jsonSymbolToAdd.getString(Sector_KEY);
                        company2 = jsonSymbolToAdd.getString(Company2_KEY);
                        trades = jsonSymbolToAdd.getString(Trades_KEY);
                        volume = jsonSymbolToAdd.getString(Volume_KEY);
                        prevClosingPrice = jsonSymbolToAdd.getString(Prev_Closing_Price_KEY);
                        openingPrice = jsonSymbolToAdd.getString(Opening_Price_KEY);
                        highPrice = jsonSymbolToAdd.getString(High_Price_KEY);
                        lowPrice = jsonSymbolToAdd.getString(Low_Price_KEY);
                        change = jsonSymbolToAdd.getString(Change_KEY);
                        percChange = jsonSymbolToAdd.getString(Perc_Change_KEY);
                        value = jsonSymbolToAdd.getString(Value_KEY);

                        //Format Integers and Doubles

                        //Create HashMap and add to List
                        symbolToAdd = new HashMap<>();
                        symbolToAdd.put(SYMBOL_KEY,symbol);
                        symbolToAdd.put(Market_KEY,market);
                        symbolToAdd.put(Sector_KEY,sector);
                        symbolToAdd.put(Company2_KEY,company2);
                        symbolToAdd.put(Trades_KEY,trades.toString());
                        symbolToAdd.put(Volume_KEY,volume.toString());
                        symbolToAdd.put(Prev_Closing_Price_KEY,prevClosingPrice.toString());
                        symbolToAdd.put(Opening_Price_KEY,openingPrice.toString());
                        symbolToAdd.put(High_Price_KEY,highPrice.toString());
                        symbolToAdd.put(Low_Price_KEY,lowPrice.toString());
                        symbolToAdd.put(Change_KEY,change.toString());
                        symbolToAdd.put(Perc_Change_KEY,percChange.toString());
                        symbolToAdd.put(Value_KEY,value.toString());

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
