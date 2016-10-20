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


public class AllEquitiesFragment extends Fragment {

    private static TextView test;

    private static ProgressBar progressBar;


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

        test = (TextView)rootView.findViewById(R.id.alleqTextViewtest);
        test.setMovementMethod(new ScrollingMovementMethod());

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
            test.setText(s);
        }
    }

}
