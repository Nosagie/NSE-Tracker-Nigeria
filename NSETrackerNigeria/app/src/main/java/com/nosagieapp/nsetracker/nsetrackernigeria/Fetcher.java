package com.nosagieapp.nsetracker.nsetrackernigeria;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nosagie on 10/18/16.
 * Fetches Unparsed JSON Strings from
 * API calls
 */
public class Fetcher {

    //Constant URLs
    private static final String NSE_BASE_URL = "http://www.nse.com.ng/rest/api";

/* FOR REFERENCE
    private static final String EQUITY_BASE_URL = "http://www.nse.com.ng/rest/api/statistics/equities/?market=&sector=&orderby=&pageSize=1000&pageNo=0";
    private static final String TOPTRADES_URL = "http://nse.com.ng/rest/api/mrkstat/toptrades";
    private static final String TOPSYMBOLS_URL = "http://nse.com.ng/rest/api/mrkstat/topsymbols";
    private static final String BOTTOMSYMBOLS_URL = "http://nse.com.ng/rest/api/mrkstat/bottomsymbols";
    private static final String MARKETSNAPSHOT_URL = "http://nse.com.ng/rest/api/mrkstat/mrksnapshot";
    private static final String COMPANYDIRECTORY_URL = "http://nse.com.ng/rest/api/issuers/companydirectory";
 */


    private static URL apiAddress;
    private static BufferedReader urlReader;
    private static String unparsedJSON=null;


    public static String fetchMarketSnapshot(){
        //Build Uri with queries
        Uri builtUri = Uri.parse(NSE_BASE_URL)
                .buildUpon()
                .appendPath("mrkstat")
                .appendPath("mrksnapshot")
                .build();

        try {
            apiAddress = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            //Exception already caught in getRawStringMethod()
        }

        return getRawString(apiAddress);

    }

    public static String fetchAllEquities(){
        //Build Uri with queries
        Uri builtUri = Uri.parse(NSE_BASE_URL)
                .buildUpon()
                .appendPath("statistics")
                .appendPath("equities")
                .appendQueryParameter("market","")
                .appendQueryParameter("sector","")
                .appendQueryParameter("orderby","")
                .appendQueryParameter("pageSize","1000")
                .appendQueryParameter("pageNo","0")
                .build();

        try {
            apiAddress = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            //Exception already caught in getRawStringMethod()
        }

        return getRawString(apiAddress);
    }

    public static String[] fetchGainersandLosers(){//index 0 is top gainers,1 is bottom losers
            String[] result = new String[2];
            //build URI to fetch Gainers
            Uri builtUri = Uri.parse(NSE_BASE_URL)
                    .buildUpon()
                    .appendPath("mrkstat")
                    .appendPath("topsymbols")
                    .build();

            try {
                apiAddress = new URL(builtUri.toString());
            }catch (MalformedURLException e){
                //Exception already caught in getRawStringMethod()
            }
            //store gainers in index 0 of result
            result[0] = getRawString(apiAddress);
            //Build Url and Fetch data for losers
            builtUri = Uri.parse(NSE_BASE_URL)
                    .buildUpon()
                    .appendPath("mrkstat")
                    .appendPath("bottomsymbols")
                    .build();

            try {
                apiAddress = new URL(builtUri.toString());
                getRawString(apiAddress);
            }catch (MalformedURLException e){
                //Exception already caught in getRawStringMethod()
            }
            //store gainers in index 0 of result
            result[1] = getRawString(apiAddress);

        return result;
    }

    public static String fetchTopTrades(){
        //build Uri to fetch top trades
        Uri builtUri = Uri.parse(NSE_BASE_URL)
                .buildUpon()
                .appendPath("mrkstat")
                .appendPath("toptrades")
                .build();

        try {
            apiAddress = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            //Exception already caught in getRawStringMethod()
        }

        return getRawString(apiAddress);
    }

    public static String fetchCompanyDirectory(){

        Uri builtUri = Uri.parse(NSE_BASE_URL)
                .buildUpon()
                .appendPath("issuers")
                .appendPath("companydirectory")
                .build();
        try {
            apiAddress = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            //Exception already caught in getRawStringMethod()
        }

        return getRawString(apiAddress);
    }

    private static String getRawString(URL api_address){
        try{
            apiAddress = new URL(api_address.toString());
            urlReader = new BufferedReader((new InputStreamReader(apiAddress.openStream())));
            unparsedJSON = null;
            String lineRead;

            while ((lineRead = urlReader.readLine()) != null){
                unparsedJSON += lineRead;
            }

        }catch (MalformedURLException e){
            Log.d(MainContainerActivity.LOG_TAG,"Malformed Call, contact developer at " + MainContainerActivity.DEVELOPER_EMAIL);
        }
        catch (IOException e){
            Log.d(MainContainerActivity.LOG_TAG,"IO Error,contact developer at " + MainContainerActivity.DEVELOPER_EMAIL);
        }

        if(unparsedJSON == null) {
            Log.e(MainContainerActivity.LOG_TAG,"Error getting data, contact developer at " + MainContainerActivity.DEVELOPER_EMAIL);
        }

        return unparsedJSON;
    }
}
