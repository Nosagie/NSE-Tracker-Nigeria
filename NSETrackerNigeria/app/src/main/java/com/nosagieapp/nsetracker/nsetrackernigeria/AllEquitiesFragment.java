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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AllEquitiesFragment extends Fragment {

    private static TextView allEquitiesErrorTextView;
    private static ListView allEquitiesListView;

    private static ProgressBar progressBar;

    //HashMap to store all Equities
    private ArrayList<HashMap<String,String>> allEquities;

    private ArrayAdapter<HashMap<String,String>> allEquitiesListAdapter;

    //Keys for JSON API Call
    public static final String SYMBOL_KEY = "Symbol";
    public static final String PREV_CLOSING_PRICE_KEY = "PrevClosingPrice";
    public static final String OPENING_PRICE_KEY = "OpeningPrice";
    public static final String HIGH_PRICE_KEY = "HighPrice";
    public static final String LOW_PRICE_KEY = "LowPrice";
    public static final String CLOSE_PRICE_KEY = "ClosePrice";
    public static final String CHANGE_KEY = "Change";
    public static final String PERC_CHANGE_KEY = "PercChange";
    public static final String TRADES_KEY = "Trades";
    public static final String VOLUME_KEY = "Volume";
    public static final String VALUE_KEY = "Value";
    public static final String MARKET_KEY = "Market";
    public static final String SECTOR_KEY = "Sector";
    public static final String COMPANY2_KEY = "Company2"; //same as symbol but returned with API call

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

        allEquitiesListView = (ListView)rootView.findViewById(R.id.allEquitiesListView);

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
            allEquitiesErrorTextView.setVisibility(View.GONE);

            if(s == null || s.equals("null")){
                allEquitiesErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
            }else{

                try {
                    allEquities = new ArrayList<>();
                    JSONArray allEquitiesJSON = new JSONArray(s);
                    JSONObject jsonSymbolToAdd;
                    HashMap<String, String> symbolToAdd;
                    String symbol,market,sector,company2;
                    String prevClosingPrice,openingPrice,highPrice,lowPrice,change,percChange,value,trades,volume,closePrice;

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
                        closePrice = jsonSymbolToAdd.getString(CLOSE_PRICE_KEY);

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
                        symbolToAdd.put(CLOSE_PRICE_KEY,closePrice.toString());

                        allEquities.add(symbolToAdd);

                    }

                    //Update UI

                    allEquitiesListAdapter = new allEquitiesListAdapter(getActivity(),R.layout.all_equities_list_item,allEquities);
                    allEquitiesListView.setAdapter(allEquitiesListAdapter);

                    //Open new Dialog with info if item clicked
                    allEquitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            EquitiesDialogFragment equityDialog = new EquitiesDialogFragment().newInstance(allEquities.get(position));
                            equityDialog.show(getFragmentManager(),allEquities.get(position).get(SYMBOL_KEY));
                        }
                    });


                }catch(JSONException e ){
                    Log.e(MainContainerActivity.LOG_TAG, e.toString() + " " + MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    allEquitiesErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                }

            }
        }
    }

    private class allEquitiesListAdapter extends ArrayAdapter<HashMap<String,String>> {

        private ArrayList<HashMap<String,String>> values;
        private Context context;

        public allEquitiesListAdapter(Context context, int resource,ArrayList<HashMap<String,String>> values) {
            super(context, resource,values);
            this.values = values;
            this.context = context;
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            //set up custom rowview
            View rowView = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.all_equities_list_item, null);


            TextView symbolTextView = (TextView)rowView.findViewById(R.id.allEquitiesSymbolListTextView);
            symbolTextView.setText(values.get(position).get(SYMBOL_KEY));

            TextView marketTextView = (TextView)rowView.findViewById(R.id.allEquitiesMarketTextView);
            marketTextView.setText(values.get(position).get(MARKET_KEY));

            TextView sectorTextView = (TextView)rowView.findViewById(R.id.allEquitiesSectorTextView);
            sectorTextView.setText(values.get(position).get(SECTOR_KEY));

            //If position is even
            if(position % 2 == 0){
                LinearLayout thisLinearLayout = (LinearLayout)rowView.findViewById(R.id.allEquitiesListParentLinearLayout);
                thisLinearLayout.setBackgroundColor(Color.parseColor(MainContainerActivity.ALTERNATE_LIST_COLOR));
            }

            return rowView;
        }



    }

}
