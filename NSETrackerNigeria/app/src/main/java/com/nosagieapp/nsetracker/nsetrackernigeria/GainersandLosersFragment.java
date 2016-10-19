package com.nosagieapp.nsetracker.nsetrackernigeria;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 *
 */
public class GainersandLosersFragment extends Fragment {

    TextView test;

    //JSON KEYS
    private final String SYMBOL_KEY = "SYMBOL";
    private final String LAST_CLOSE_KEY = "LAST_CLOSE";
    private final String TODAYS_CLOSE_KEY = "TODAYS_CLOSE";
    private final String PERCENTAGE_CHANGE_KEY = "PERCENTAGE_CHANGE";
    private final String SYMBOL2_KEY = "SYMBOL2"; //same as symbol but returned with api call


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

        test = (TextView)rootView.findViewById(R.id.gainersloserstestTextView);
        test.setMovementMethod(new ScrollingMovementMethod());

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

            String gainers = results[0].substring(4);
            String losers = results[1].substring(4);

            test.setText(gainers + "\n\n" + losers);

        }
    }

}
