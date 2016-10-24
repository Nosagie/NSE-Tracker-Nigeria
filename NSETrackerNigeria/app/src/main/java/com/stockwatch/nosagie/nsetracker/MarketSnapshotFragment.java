package com.stockwatch.nosagie.nsetracker;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class MarketSnapshotFragment extends Fragment {

    private static TextView marketSnapshotErrorTextView,allShareIndexTextView,marketCapTextView;
    private static TextView totalTradesTextView,tradeValueTextView,tradeVolumeTextView,marketStatusTextView;

    private static LinearLayout marketSnapshotContentLinearLayout;

    private static ProgressBar progressBar;

    //JSON Keys
    private final String ASI_KEY = "ASI";
    private final String DEALS_KEY = "DEALS";
    private final String VOLUME_KEY = "VOLUME";
    private final String VALUE_KEY = "VALUE";
    private final String MARKET_CAP_KEY = "CAP";
    private final String MARKET_STATUS_KEY = "MktStatus";
    private final String END_OF_DAY_VALUE = "ENDOFDAY";
    private final String MARKET_OPEN = "OPEN";
    private final String MARKET_CLOSED = "CLOSED";

    private String marketStatus;


    public MarketSnapshotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainContainerActivity.isConnectedToInternet(getActivity())) {

            //Initialize Adds
            MobileAds.initialize(getActivity().getApplicationContext(), MainContainerActivity.ADMOBSAPPID);
            new fetchMarketStatus().execute();
            new fetchMarketSnapshot().execute();
        }else{
            Toast.makeText(getActivity(), MainContainerActivity.CONNECTTOINTERNET, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_market_snapshot, container, false);

        progressBar = (ProgressBar)rootView.findViewById(R.id.mrktSnapshotProgressBar);
        marketSnapshotErrorTextView = (TextView)rootView.findViewById(R.id.mrktSnapshotErrorTextView);

        allShareIndexTextView = (TextView)rootView.findViewById(R.id.allShareIndexTextView);
        totalTradesTextView = (TextView)rootView.findViewById(R.id.totalTradesTextView);
        tradeValueTextView = (TextView)rootView.findViewById(R.id.tradeValueTextView);
        tradeVolumeTextView = (TextView)rootView.findViewById(R.id.tradeVolumeTextView);
        marketCapTextView = (TextView)rootView.findViewById(R.id.marketCapTextView);
        marketStatusTextView = (TextView)rootView.findViewById(R.id.marketStatusTextView);
        marketSnapshotContentLinearLayout = (LinearLayout)rootView.findViewById(R.id.mrktSnapshotContent);
        marketSnapshotContentLinearLayout.setVisibility(View.GONE);

        //For ads
        AdView mAdView = (AdView)rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new fetchMarketStatus().execute();
        new fetchMarketSnapshot().execute();
    }

    //get market status
    private class fetchMarketStatus extends  AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            String unparsedJSON = Fetcher.fetchMarketStatus();

            return unparsedJSON.substring(4); //removes "null" at the beginning of resulting string
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s == null || s.equals("null")){
                //Show Error to User
                marketSnapshotErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
            }else {

                try {
                    //Parse JSON
                    JSONArray unparsedSnapshot = new JSONArray(s);
                    JSONObject lineOfData = unparsedSnapshot.getJSONObject(0);
                    marketStatus = lineOfData.getString(MARKET_STATUS_KEY);


                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG,MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    marketSnapshotErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING);
                }
            }
        }
    }

    private class fetchMarketSnapshot extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            String unparsedJSON = Fetcher.fetchMarketSnapshot();

            return unparsedJSON.substring(4); //removes "null" at the beginning of resulting string
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            marketSnapshotErrorTextView.setVisibility(View.GONE);
            marketSnapshotContentLinearLayout.setVisibility(View.VISIBLE);

            //checks if returned string is null, if yes - display error to user
            if (s == null || s.equals("null")){
                //Show Error to User
                marketSnapshotErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);

            }else{ //else update UI Thread with Parsed JSON Data

                try {
                    //Parse JSON
                    JSONObject unparsedSnapshot = new JSONObject(s);
                    Long allShareIndex = unparsedSnapshot.getLong(ASI_KEY);
                    Integer deals = unparsedSnapshot.getInt(DEALS_KEY);
                    Long volume = unparsedSnapshot.getLong(VOLUME_KEY);
                    Long value = unparsedSnapshot.getLong(VALUE_KEY);
                    Long marketCap = unparsedSnapshot.getLong(MARKET_CAP_KEY);

                    //Format Strings Appropriately


                    allShareIndexTextView.setText(String.format("%,d",allShareIndex));
                    marketCapTextView.setText(MainContainerActivity.CURRENCY + String.format("%,d",marketCap));
                    totalTradesTextView.setText(String.format("%,d", deals));
                    tradeVolumeTextView.setText(String.format("%,d",volume));
                    tradeValueTextView.setText(MainContainerActivity.CURRENCY + String.format("%,d",value));

                    //Format Strings Appropriately
                    if(marketStatus.equals(END_OF_DAY_VALUE)){
                        marketStatusTextView.setText(MARKET_CLOSED);
                        marketStatusTextView.setTextColor(Color.parseColor("#b30000"));
                    }else {
                        marketStatusTextView.setText(MARKET_OPEN);
                        marketStatusTextView.setTextColor(Color.parseColor("#006600"));
                    }


                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG,MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    marketSnapshotErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING);
                }
            }

        }
    }

}
