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
 */
public class CompanyDirectoryFragment extends Fragment {

    TextView test;

    ProgressBar progressBar;

    public CompanyDirectoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new fetchCompanyDirectory().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_directory, container, false);

        progressBar = (ProgressBar)rootView.findViewById(R.id.companyDirectoryProgressBar);

        test = (TextView)rootView.findViewById(R.id.companyDireTextViewtest);
        test.setMovementMethod(new ScrollingMovementMethod());

        return rootView;
    }

    private class fetchCompanyDirectory extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {

            String unparsedJSON = Fetcher.fetchCompanyDirectory();

            return unparsedJSON.substring(4);//removes "null" at beginning of string
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);
            test.setText(s);

        }
    }

}
