package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AllEquitiesFragment extends Fragment {

    private TextView test;


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

            test.setText(s);

        }
    }

}
