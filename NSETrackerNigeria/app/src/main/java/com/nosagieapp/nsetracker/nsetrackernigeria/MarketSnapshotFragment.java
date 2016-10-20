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

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class MarketSnapshotFragment extends Fragment {

    private static TextView marketSnapshotErrorTextView;

    private static ProgressBar progressBar;

    //JSON Keys
    private final String ASI_KEY = "ASI";
    private final String DEALS_KEY = "DEALS";
    private final String VOLUME_KEY = "VOLUME";
    private final String VALUE_KEY = "VALUE";
    private final String MARKET_CAP_KEY = "CAP";


    public MarketSnapshotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new fetchMarketSnapshot().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_market_snapshot, container, false);

        progressBar = (ProgressBar)rootView.findViewById(R.id.mrktSnapshotProgressBar);

        marketSnapshotErrorTextView = (TextView)rootView.findViewById(R.id.mrktSnapshotErrorTextView);
        marketSnapshotErrorTextView.setMovementMethod(new ScrollingMovementMethod());

        return rootView;
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
            //checks if returned string is null, if yes - display error to user
            if (s == null || s.equals("null")){
                //Show Error to User
                marketSnapshotErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);

            }else{ //else update UI Thread with Parsed JSON Data

                //Make Progress Bar and Error TextView Disappear and Make other UI Elements Visible
                //marketSnapshotErrorTextView.setVisibility(View.GONE);

                try {
                    //Parse JSON
                    JSONObject unparsedSnapshot = new JSONObject(s);
                    Double allShareIndex = unparsedSnapshot.getDouble(ASI_KEY);
                    Integer deals = unparsedSnapshot.getInt(DEALS_KEY);
                    Long volume = unparsedSnapshot.getLong(VOLUME_KEY);
                    Long value = unparsedSnapshot.getLong(VALUE_KEY);
                    Long marketCap = unparsedSnapshot.getLong(MARKET_CAP_KEY);

                    //TODO:UPDATE UI ELEMENTS
                    marketSnapshotErrorTextView.setText(allShareIndex + "\n" + deals + "\n" + volume + "\n" + value + "\n" + marketCap);


                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG,MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    marketSnapshotErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING);
                }
            }

<<<<<<< HEAD
=======
            //Check if result is null else parse JSON
            if(s == null || s.equals("null")){

            }else {
                test.setText(s);

            }
>>>>>>> 2599b05c795d04882e65bf27a2690e8bc5fb6808
        }
    }

}
