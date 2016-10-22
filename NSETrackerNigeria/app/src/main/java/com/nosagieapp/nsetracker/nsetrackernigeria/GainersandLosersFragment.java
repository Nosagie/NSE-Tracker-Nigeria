package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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
    private static ListView gainersListView,losersListView;
    private static LinearLayout gainersAndLosersLinearLayout;

    private static ProgressBar progressBar;

    //ArrayAdapter to display List
    private static ArrayAdapter gainersListAdapter;
    private static ArrayAdapter losersListAdapter;

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

        //Initialize Adds TODO:UPDATE ID IN STRING FILE AND HERE
        MobileAds.initialize(getActivity().getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        new fetchGainersandLosersTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_gainersand_losers, container, false);

        gainersAndLosersErrorTextView = (TextView)rootView.findViewById(R.id.gainersloserserrorTextView);
        progressBar = (ProgressBar)rootView.findViewById(R.id.gainersandLosersProgressBar);
        gainersListView = (ListView)rootView.findViewById(R.id.gainersListView);
        losersListView = (ListView)rootView.findViewById(R.id.losersListView);
        gainersAndLosersLinearLayout = (LinearLayout)rootView.findViewById(R.id.gainersAndLosersContent);
        gainersAndLosersLinearLayout.setVisibility(View.GONE);

        //For ads
        AdView mAdView = (AdView)rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
                gainersAndLosersErrorTextView.setVisibility(View.GONE);
                gainersAndLosersLinearLayout.setVisibility(View.VISIBLE);

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

                    //Initialize and set list adapters
                    gainersListAdapter = new GainersAndLosersAdapter(getActivity(),R.layout.gainers_and_losers_list_item,topGainers,true);
                    gainersListView.setAdapter(gainersListAdapter);
                    losersListAdapter = new GainersAndLosersAdapter(getActivity(),R.layout.gainers_and_losers_list_item,topLosers,false);
                    losersListView.setAdapter(losersListAdapter);


                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG, MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    gainersAndLosersErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                }
            }

        }
    }

    private class GainersAndLosersAdapter extends ArrayAdapter{
        private final Context context;
        private final ArrayList<HashMap<String,String>> values;
        private  Boolean isGainer;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Set up custom list view
            View rowView = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.gainers_and_losers_list_item, null);


            TextView symbol = (TextView)rowView.findViewById(R.id.gainersandLosersSymbolListView);
            symbol.setText(values.get(position).get(SYMBOL_KEY));
            TextView prevPrice = (TextView)rowView.findViewById(R.id.prevPriceListText);
            prevPrice.setText(values.get(position).get(LAST_CLOSE_KEY));
            TextView perChange = (TextView)rowView.findViewById(R.id.percChangeListText);
            perChange.setText(values.get(position).get(PERCENTAGE_CHANGE_KEY));

            //Check if gainer or losers list and set color
            if(isGainer){
                perChange.setTextColor(Color.parseColor("#009933"));
            }else{
                perChange.setTextColor(Color.parseColor("#e60000"));
            }

            TextView todayPrice = (TextView)rowView.findViewById(R.id.todayPriceListText);
            todayPrice.setText(values.get(position).get(TODAYS_CLOSE_KEY));

            //If position is even
            if(position % 2 == 0){
                LinearLayout thisLinearLayout = (LinearLayout)rowView.findViewById(R.id.gainersandLosersListParentLinearLayout);
                thisLinearLayout.setBackgroundColor(Color.parseColor(MainContainerActivity.ALTERNATE_LIST_COLOR));
            }


            return  rowView;

        }

        public GainersAndLosersAdapter(Context context,int layout, ArrayList<HashMap<String,String>> values,Boolean isgainer) {
            super(context, layout, values);
            this.context = context;
            this.values = values;
            this.isGainer = isgainer;
        }

    }


}
