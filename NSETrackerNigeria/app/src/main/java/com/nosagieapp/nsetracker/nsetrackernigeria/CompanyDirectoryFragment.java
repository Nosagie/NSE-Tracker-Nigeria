package com.nosagieapp.nsetracker.nsetrackernigeria;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyDirectoryFragment extends Fragment {

    private static TextView companyDirectoryErrorTextView;

    private static GridView companyDirectoryGridView;

    private static ArrayAdapter<HashMap<String,String>> directoryListAdapter;

    private static ProgressBar progressBar;

    //JSON Keys
    public static final String INTERNATIONALSECIN_KEY = "InternationSecIN";
    public static final String SYMBOL_KEY = "Symbol";
    public static final String PREVCLOSE_KEY ="PrevClose";
    public static final String OPENPRICE_KEY = "OpenPrice";
    public static final String DAYSHIGH_KEY = "DaysHigh";
    public static final String DAYSLOW_KEY = "DaysLow";
    public static final String VOLUME_KEY = "Volume";
    public static final String VALUE_KEY = "Value";
    public static final String MARKETCAP_KEY = "MarketCap";
    public static final String SHARESOUTSTANDING_KEY = "SharesOutstanding";
    public static final String DIVIDEND_KEY = "Dividend";
    public static final String YIELD_KEY = "Yield";
    public static final String SECTOR_KEY = "Sector";
    public static final String SUBSECTOR_KEY = "SubSector";
    public static final String COMPANYNAME_KEY = "CompanyName";
    public static final String MARKETCLASSIFICATION_KEY = "MarketClassification";
    public static final String DATELISTED_KEY = "DateListed";
    public static final String DATEINCORPORATED_KEY = "DateOfIncorporation";
    public static final String WEBSITE_KEY = "Website";
    public static final String LOGOURL_KEY = "Logourl";
    public static final String STOCKPRICEPERCCHANGE_KEY = "StockPricePercChange";
    public static final String STOCKPRICECHANGE_KEY = "StockPriceChange";
    public static final String STOCKPRICECURRENT_KEY = "StockPriceCur";
    public static final String COMPANYPROFILESUMM_KEY = "CompanyProfileSummary";
    public static final String NATUREOFBUSINESS_KEY = "NatureofBusiness";
    public static final String COMPANYADDRESS_KEY = "CompanyAddress";
    public static final String TELEPHONE_KEY = "Telephone";
    public static final String FAX_KEY = "Fax";
    public static final String EMAIL_KEY = "Email";
    public static final String SECRETARY_KEY = "CompanySecretary";
    public static final String AUDITOR_KEY = "Auditor";
    public static final String REGISTRAR_KEY = "Registrars";
    public static final String BOARDOFDIRECTORS_KEY = "BoardOfDirectors";
    public static final String ID_KEY = "ID";
    public static final String ANNUALHIGHPRICE_KEY = "HIGH52WK_PRICE";
    public static final String ANNUALHIGHPRICEDATETIME_KEY = "HIGH52WK_DATETIME";
    public static final String ANNUALLOWPRICE_KEY = "LOW52WK_PRICE";
    public static final String ANNUALLOWPRICEDATETIME = "LOW52WK_DATETIME";

    //HashMap to store all Equities
    private static ArrayList<HashMap<String,String>> allCompanies;

    public CompanyDirectoryFragment() {
        // Required empty public constructor
    }

    public static ArrayList getCompanyDirectory(){
        return allCompanies;
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

        companyDirectoryGridView = (GridView)rootView.findViewById(R.id.directoryGridView);

        companyDirectoryErrorTextView = (TextView)rootView.findViewById(R.id.companyDirectoryErrorTextView);
        companyDirectoryErrorTextView.setMovementMethod(new ScrollingMovementMethod());

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

            if(s == null | s.equals("null")){
                companyDirectoryErrorTextView.setText(MainContainerActivity.API_CALL_ERROR_STRING);
            }else{
                companyDirectoryErrorTextView.setVisibility(View.GONE);

                try{

                    allCompanies = new ArrayList<>();
                    JSONArray companiesJSON = new JSONArray(s);
                    JSONObject companyToAddJSON;
                    HashMap<String,String> companyToAdd;
                    String internationalSECIN,symbol,prevClose,openPrice;
                    String daysHigh,daysLow,volume,value,marketCap,sharesOutstanding,dividend,yield;
                    String sector,subSector,companyName,marketClassification,dateListed,dateIncorporated,website,logoURL;
                    String stockPricePercChange,stockPriceCurrent,companyProfileSumm;
                    String natureOfBusiness,companyAddress,telephone,fax,email,secretary,auditor,registrar,boardOfDirectors,id;
                    String annualHighPrice,annualHighPriceDateTime,annualLowPrice,annualLowPriceDateTime,stockPriceChange;

                    for(int i = 0; i < companiesJSON.length();i++){

                        companyToAddJSON = companiesJSON.getJSONObject(i);
                        internationalSECIN = companyToAddJSON.getString(INTERNATIONALSECIN_KEY);
                        symbol = companyToAddJSON.getString(SYMBOL_KEY);
                        prevClose = companyToAddJSON.getString(PREVCLOSE_KEY);
                        openPrice = companyToAddJSON.getString(OPENPRICE_KEY);
                        daysHigh = companyToAddJSON.getString(DAYSHIGH_KEY);
                        daysLow = companyToAddJSON.getString(DAYSLOW_KEY);
                        volume = companyToAddJSON.getString(VOLUME_KEY);
                        value = companyToAddJSON.getString(VALUE_KEY);
                        marketCap = companyToAddJSON.getString(MARKETCAP_KEY);
                        sharesOutstanding = companyToAddJSON.getString(SHARESOUTSTANDING_KEY);
                        dividend = companyToAddJSON.getString(DIVIDEND_KEY);
                        yield = companyToAddJSON.getString(YIELD_KEY);
                        sector = companyToAddJSON.getString(SECTOR_KEY);
                        subSector = companyToAddJSON.getString(SUBSECTOR_KEY);
                        companyName = companyToAddJSON.getString(COMPANYNAME_KEY);
                        marketClassification = companyToAddJSON.getString(MARKETCLASSIFICATION_KEY);
                        dateListed = companyToAddJSON.getString(DATELISTED_KEY);
                        dateIncorporated = companyToAddJSON.getString(DATEINCORPORATED_KEY);
                        website = companyToAddJSON.getString(WEBSITE_KEY);
                        logoURL = companyToAddJSON.getString(LOGOURL_KEY);
                        stockPricePercChange = companyToAddJSON.getString(STOCKPRICEPERCCHANGE_KEY);
                        stockPriceCurrent = companyToAddJSON.getString(STOCKPRICECURRENT_KEY);
                        companyProfileSumm = companyToAddJSON.getString(COMPANYPROFILESUMM_KEY);
                        natureOfBusiness = companyToAddJSON.getString(NATUREOFBUSINESS_KEY);
                        companyAddress = companyToAddJSON.getString(COMPANYADDRESS_KEY);
                        telephone = companyToAddJSON.getString(TELEPHONE_KEY);
                        stockPriceChange = companyToAddJSON.getString(STOCKPRICECHANGE_KEY);
                        fax = companyToAddJSON.getString(FAX_KEY);
                        email = companyToAddJSON.getString(EMAIL_KEY);
                        secretary = companyToAddJSON.getString(SECRETARY_KEY);
                        auditor = companyToAddJSON.getString(AUDITOR_KEY);
                        registrar = companyToAddJSON.getString(REGISTRAR_KEY);
                        boardOfDirectors = companyToAddJSON.getString(BOARDOFDIRECTORS_KEY);
                        id = companyToAddJSON.getString(ID_KEY);
                        annualHighPrice = companyToAddJSON.getString(ANNUALHIGHPRICE_KEY);
                        annualHighPriceDateTime = companyToAddJSON.getString(ANNUALHIGHPRICEDATETIME_KEY);
                        annualLowPrice = companyToAddJSON.getString(ANNUALLOWPRICE_KEY);
                        annualLowPriceDateTime = companyToAddJSON.getString(ANNUALLOWPRICEDATETIME);

                        //Add to HashMap and Arraylist
                        companyToAdd = new HashMap<>();
                        companyToAdd.put(INTERNATIONALSECIN_KEY,internationalSECIN);
                        companyToAdd.put(SYMBOL_KEY,symbol);
                        companyToAdd.put(PREVCLOSE_KEY,prevClose);
                        companyToAdd.put(OPENPRICE_KEY,openPrice);
                        companyToAdd.put(DAYSHIGH_KEY,daysHigh);
                        companyToAdd.put(DAYSLOW_KEY,daysLow);
                        companyToAdd.put(VOLUME_KEY,volume);
                        companyToAdd.put(VALUE_KEY,value);
                        companyToAdd.put(MARKETCAP_KEY,marketCap);
                        companyToAdd.put(SHARESOUTSTANDING_KEY,sharesOutstanding);
                        companyToAdd.put(DIVIDEND_KEY,dividend);
                        companyToAdd.put(YIELD_KEY,yield);
                        companyToAdd.put(SECTOR_KEY,sector);
                        companyToAdd.put(SUBSECTOR_KEY,subSector);
                        companyToAdd.put(COMPANYNAME_KEY,companyName);
                        companyToAdd.put(MARKETCLASSIFICATION_KEY,marketClassification);
                        companyToAdd.put(DATELISTED_KEY,dateListed);
                        companyToAdd.put(DATEINCORPORATED_KEY,dateIncorporated);
                        companyToAdd.put(WEBSITE_KEY,website);
                        companyToAdd.put(LOGOURL_KEY,logoURL);
                        companyToAdd.put(STOCKPRICECHANGE_KEY,stockPriceChange);
                        companyToAdd.put(STOCKPRICEPERCCHANGE_KEY,stockPricePercChange);
                        companyToAdd.put(STOCKPRICECURRENT_KEY,stockPriceCurrent);
                        companyToAdd.put(COMPANYPROFILESUMM_KEY,companyProfileSumm);
                        companyToAdd.put(NATUREOFBUSINESS_KEY,natureOfBusiness);
                        companyToAdd.put(COMPANYADDRESS_KEY,companyAddress);
                        companyToAdd.put(TELEPHONE_KEY,telephone);
                        companyToAdd.put(FAX_KEY,fax);
                        companyToAdd.put(EMAIL_KEY,email);
                        companyToAdd.put(SECRETARY_KEY,secretary);
                        companyToAdd.put(AUDITOR_KEY,auditor);
                        companyToAdd.put(REGISTRAR_KEY,registrar);
                        companyToAdd.put(BOARDOFDIRECTORS_KEY,boardOfDirectors);
                        companyToAdd.put(ID_KEY,id);
                        companyToAdd.put(ANNUALHIGHPRICE_KEY,annualHighPrice);
                        companyToAdd.put(ANNUALHIGHPRICEDATETIME_KEY,annualHighPriceDateTime);
                        companyToAdd.put(ANNUALLOWPRICE_KEY,annualLowPrice);
                        companyToAdd.put(ANNUALLOWPRICEDATETIME,annualLowPriceDateTime);


                        allCompanies.add(companyToAdd);

                    }

                if(getActivity()!=null) {
                    progressBar.setVisibility(View.GONE);

                    directoryListAdapter = new directoryListAdapter(getActivity(), R.layout.directory_list_item, allCompanies);
                    companyDirectoryGridView.setAdapter(directoryListAdapter);

                    companyDirectoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            CompanyDirectoryDialogFragment directoryDialog = new CompanyDirectoryDialogFragment().newInstance(allCompanies.get(position), position);
                            directoryDialog.show(getFragmentManager(), allCompanies.get(position).get(COMPANYNAME_KEY));
                        }
                    });
                }


                }catch (JSONException e){
                    Log.e(MainContainerActivity.LOG_TAG, e.toString() + " " + MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                    companyDirectoryErrorTextView.setText(MainContainerActivity.PARSE_ERROR_STRING + MainContainerActivity.DEVELOPER_EMAIL);
                }
            }


        }
    }

    private class directoryListAdapter extends ArrayAdapter<HashMap<String,String>>{

        private ArrayList<HashMap<String,String>> values;
        private Context context;

        public directoryListAdapter(Context context, int resource, ArrayList<HashMap<String,String>> values) {
            super(context, resource, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.directory_list_item, null);

            TextView companyNameTextView = (TextView)rowView.findViewById(R.id.directoryListCompanyName);
            companyNameTextView.setText(values.get(position).get(COMPANYNAME_KEY));


            return rowView;
        }
    }

}
