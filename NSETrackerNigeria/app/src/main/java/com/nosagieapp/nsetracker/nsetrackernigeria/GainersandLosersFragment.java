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


/**
 *
 */
public class GainersandLosersFragment extends Fragment {

    private static TextView gainersAndLosersErrorTextView;

    private static ProgressBar progressBar;

    //JSON KEYS
    private final String SYMBOL_KEY = "SYMBOL";
    private final String LAST_CLOSE_KEY = "LAST_CLOSE";
    private final String TODAYS_CLOSE_KEY = "TODAYS_CLOSE";
    private final String PERCENTAGE_CHANGE_KEY = "PERCENTAGE_CHANGE";
    private final String SYMBOL2_KEY = "SYMBOL2"; //same as symbol but returned with api call

    //Data Structures to store info
    private ArrayList<HashMap<String,String>> topGainers;
    private ArrayList<HashMap<String,String>> topLosers;


    public GainersandLosersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new fetchGainersandLosersTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gainersand_losers, container, false);

        gainersAndLosersErrorTextView = (TextView)rootView.findViewById(R.id.gainersloserserrorTextView);
        gainersAndLosersErrorTextView.setMovementMethod(new ScrollingMovementMethod());

        progressBar = (ProgressBar)rootView.findViewById(R.id.gainersandLosersProgressBar);


        return rootView;
    }

    private class fetchGainersandLosersTask extends AsyncTask<String, Void,String[]>{
        @Override
        protected String[] doInBackground(String... params) {
            String[] unparsedJSON = Fetcher.fetchGainersandLosers();

            return unparsedJSON;
        }

        @Override
        protected void onPostExecute(String[] results) {
            super.onPostExecute(results);
            progressBar.setVisibility(View.GONE);

            if(results == null || results[0].equals("null") || results[1].equals("null") || results[0] == null || results[1] == null){
                gainersAndLosersErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
            }
            else {
                String gainers = results[0].substring(4);
                String losers = results[1].substring(4);

                try {

                    JSONArray  jsonArrayGainers = new JSONArray(gainers);
                    JSONArray  jsonArrayLosers = new JSONArray(losers);

                    //Variables for processing JSON
                    JSONObject gainerToAdd;
                    JSONObject loserToAdd;
                    String symbol,symbol2;
                    Double lastClose,todayClose,percentageChange;
                    HashMap<String,String> symbolToAdd;

                    //For Gainers
                    topGainers = new ArrayList<>();
                    for(int i = 0; i < jsonArrayGainers.length(); i++){
                        gainerToAdd = jsonArrayGainers.getJSONObject(i);

                        //Get Values
                        symbol = gainerToAdd.getString(SYMBOL_KEY);
                        lastClose = gainerToAdd.getDouble(LAST_CLOSE_KEY);
                        todayClose = gainerToAdd.getDouble(TODAYS_CLOSE_KEY);
                        percentageChange = gainerToAdd.getDouble(PERCENTAGE_CHANGE_KEY);

                        //Perform pre-processing of Numbers

                        //Create HashMap with elements and add to ArrayList
                        symbolToAdd = new HashMap<>();
                        symbolToAdd.put(SYMBOL_KEY,symbol);
                        symbolToAdd.put(LAST_CLOSE_KEY,lastClose.toString());
                        symbolToAdd.put(TODAYS_CLOSE_KEY,todayClose.toString());
                        symbolToAdd.put(PERCENTAGE_CHANGE_KEY,percentageChange.toString());

                        topGainers.add(symbolToAdd);
                    }

                    //For Losers
                    topLosers = new ArrayList<>();
                    for(int i = 0; i < jsonArrayLosers.length(); i++){
                        loserToAdd = jsonArrayLosers.getJSONObject(i);

                        //Get Values
                        symbol = loserToAdd.getString(SYMBOL_KEY);
                        lastClose = loserToAdd.getDouble(LAST_CLOSE_KEY);
                        todayClose = loserToAdd.getDouble(TODAYS_CLOSE_KEY);
                        percentageChange = loserToAdd.getDouble(PERCENTAGE_CHANGE_KEY);

                        //Perform pre-processing of Numbers

                        //Create HashMap with elements and add to ArrayList
                        symbolToAdd = new HashMap<>();
                        symbolToAdd.put(SYMBOL_KEY,symbol);
                        symbolToAdd.put(LAST_CLOSE_KEY,lastClose.toString());
                        symbolToAdd.put(TODAYS_CLOSE_KEY,todayClose.toString());
                        symbolToAdd.put(PERCENTAGE_CHANGE_KEY,percentageChange.toString());

                        topLosers.add(symbolToAdd);
                    }

                    //TODO:UPDATE UI
                    gainersAndLosersErrorTextView.setText(topGainers.get(0).get(SYMBOL_KEY)+ "\n\n" + topLosers.get(0).get(SYMBOL_KEY));

                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG, MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    gainersAndLosersErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                }
            }

        }
    }

}
