package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class MarketSnapshotFragment extends Fragment {

    private static TextView errorTextView;

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

        errorTextView = (TextView)rootView.findViewById(R.id.mrktSnapshotErrorTextView);
        errorTextView.setMovementMethod(new ScrollingMovementMethod());

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

            //checks if returned string is null, if yes - display error toast to user
            if (s == null || s.equals("null")){
                //Use Helper method
                errorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
                MainContainerActivity.displayAPiCallErrorToast(getActivity());

            }else{ //else update UI Thread with Parsed JSON Data

                //Make Progress Bar and Error TextView Disappear and Make other UI Elements Visible
                progressBar.setVisibility(View.GONE);
                //errorTextView.setVisibility(View.GONE);

                errorTextView.setText(s);
            }

        }
    }

}
