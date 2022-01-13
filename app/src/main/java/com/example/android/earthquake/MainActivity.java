package com.example.android.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity{
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list_item);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.earthquake_activity);
//
//        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//        // Create a new adapter that takes an empty list of earthquakes as input
//        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(mAdapter);
//
//        // Set an item click listener on the ListView, which sends an intent to a web browser
//        // to open a website with more information about the selected earthquake.
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // Find the current earthquake that was clicked on
//                Earthquake currentEarthquake = mAdapter.getItem(position);
//
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
//
//                // Create a new intent to view the earthquake URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the list of earthquakes in the response.
     *
     * AsyncTask has three generic parameters: the input type, a type used for progress updates, and
     * an output type. Our task will take a String URL, and return an Earthquake. We won't do
     * progress updates, so the second generic is just Void.
     *
     * We'll only override two of the methods of AsyncTask: doInBackground() and onPostExecute().
     * The doInBackground() method runs on a background thread, so it can run long-running code
     * (like network activity), without interfering with the responsiveness of the app.
     * Then onPostExecute() is passed the result of doInBackground() method, but runs on the
     * UI thread, so it can use the produced data to update the UI.
     */
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Earthquake}s as the result.
         */
        @Override
        protected List<Earthquake> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<Earthquake> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }

    }
}
//
//public class MainActivity extends AppCompatActivity {
//    public static final String LOG_TAG = MainActivity.class.getSimpleName();
//    public ArrayList<quaks> adder=new ArrayList<quaks>();
//
//    private static final String USGS_REQUEST_URL =
//            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Kick off an {@link AsyncTask} to perform the network request
//        EarthAsyncTask task = new EarthAsyncTask();
//        task.execute();
//    }
//    private void updateUi(quaks earthquake) {
//        adder.add(earthquake);
//        adder.add(new quaks(1.4,"abc","121314", 415L));
//        quaksAdapter adapter=new quaksAdapter(this,adder);
//        ListView lst=(ListView) findViewById(R.id.list_item);
//        lst.setAdapter(adapter);
//
////        // Display the earthquake title in the UI
////        TextView titleTextView =(TextView) findViewById(R.id.magnitude);
////        titleTextView.setText(String.valueOf(earthquake.magni));
////
////        // Display the earthquake date in the UI
////        TextView dateTextView = (TextView) findViewById(R.id.date);
////        dateTextView.setText(getDateString(earthquake.date));
////
////        // Display whether or not there was a tsunami alert in the UI
////        TextView tsunamiTextView = (TextView) findViewById(R.id.time);
////        tsunamiTextView.setText(getTsunamiAlertString(earthquake.getTimeinmili()));
//    }
//
//    private String getDateString(String timeInMilliseconds) {
//        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
//        return formatter.format(timeInMilliseconds);
//    }
//
//    /**
//     * Return the display string for whether or not there was a tsunami alert for an earthquake.
//     */
//    private String getTsunamiAlertString(double tsunamiAlert) {
//        int val=(int)Math.round(tsunamiAlert);
//        switch (val) {
//            case 0:
//                return getString(R.string.alert_no);
//            case 1:
//                return getString(R.string.alert_yes);
//            default:
//                return getString(R.string.alert_not_available);
//        }
//    }
//    private class EarthAsyncTask extends AsyncTask<URL, Void, quaks> {
//
//        @Override
//        protected quaks doInBackground(URL... urls) {
//            // Create URL object
//            URL url = createUrl(USGS_REQUEST_URL);
//
//            // Perform HTTP request to the URL and receive a JSON response back
//            String jsonResponse = "";
//            try {
//                jsonResponse = makeHttpRequest(url);
//
//            } catch (IOException e) {
//                // TODO Handle the IOException
//            }
//
//            // Extract relevant fields from the JSON response and create an {@link Event} object
//            quaks earthquake = extractFeatureFromJson(jsonResponse);
//
//            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
//            return earthquake;
//        }
//
//        /**
//         * Update the screen with the given earthquake (which was the result of the
//         * {@link
//         */
//        @Override
//        protected void onPostExecute(quaks earthquake) {
//            if (earthquake == null) {
//                return;
//            }
//
//            updateUi(earthquake);
//        }
//
//        /**
//         * Returns new URL object from the given string URL.
//         */
//        private URL createUrl(String stringUrl) {
//            URL url = null;
//            try {
//                url = new URL(stringUrl);
//            } catch (MalformedURLException exception) {
//                Log.e(LOG_TAG, "Error with creating URL", exception);
//                return null;
//            }
//            return url;
//        }
//
//        /**
//         * Make an HTTP request to the given URL and return a String as the response.
//         */
//        private String makeHttpRequest(URL url) throws IOException {
//            String jsonResponse = "";
//            HttpURLConnection urlConnection = null;
//            InputStream inputStream = null;
//            try {
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.setReadTimeout(10000 /* milliseconds */);
//                urlConnection.setConnectTimeout(15000 /* milliseconds */);
//                urlConnection.connect();
//
//                // if the request was successful (response code 200) ,
//                // then read the input stream and parse the response ,
//                if (urlConnection.getResponseCode() == 200) {
//                    inputStream = urlConnection.getInputStream();
//                    jsonResponse = readFromStream(inputStream);
//                }else {
//                    Log.e(LOG_TAG,"Error response code; " + urlConnection.getResponseCode() );
//                }
//            } catch (IOException e) {
//                Log.e(LOG_TAG,"problem retrieving the earthquake JSON results.", e);
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (inputStream != null) {
//                    // function must handle java.io.IOException here
//                    inputStream.close();
//                }
//            }
//            return jsonResponse;
//        }
//
//        /**
//         * Convert the {@link InputStream} into a String which contains the
//         * whole JSON response from the server.
//         */
//        private String readFromStream(InputStream inputStream) throws IOException {
//            StringBuilder output = new StringBuilder();
//            if (inputStream != null) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                String line = reader.readLine();
//                while (line != null) {
//                    output.append(line);
//                    line = reader.readLine();
//                }
//            }
//            return output.toString();
//        }
//
//        /**
//         * Return an {@link quaks} object by parsing out information
//         * about the first earthquake from the input earthquakeJSON string.
//         */
//        private quaks extractFeatureFromJson(String earthquakeJSON) {
//            try {
//                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
//                JSONArray featureArray = baseJsonResponse.getJSONArray("features");
//
//                // If there are results in the features array
//                if (featureArray.length() > 0) {
//                    // Extract out the first feature (which is an earthquake)
//                    JSONObject firstFeature = featureArray.getJSONObject(0);
//                    JSONObject properties = firstFeature.getJSONObject("properties");
//
////                     Extract out the title, time, and tsunami values
//                    Double magni=properties.getDouble("mag");
//                    String title = properties.getString("place");
//                    String url = properties.getString("url");
//                    Long time = properties.getLong("time");
//
//
//                    // Create a new {@link Event} object
//                    return new quaks(magni,title,url,time );
//                }
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
//            }
//            return null;
//        }
//    }
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        ArrayList<quaks> adder=new ArrayList<quaks>();
//////        adder.add(new quaks(1.4,"abc","121314", 415L));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(1.4,"abc","121314"));
//////        adder.add(new quaks(7.2, "San Francisco", "Feb 2, 2016"));
//////        adder.add(new quaks(7.2, "London", "July 20, 2015"));
//////        adder.add(new quaks(7.2, "Tokyo", "Nov 10, 2014"));
//////        adder.add(new quaks(7.2, "Mexico City", "May 3, 2014"));
//////        adder.add(new quaks(7.2, "Moscow", "Jan 31, 2013"));
//////        adder.add(new quaks(7.2, "Rio de Janeiro", "Aug 19, 2012"));
//////        adder.add(new quaks(7.2, "Paris", "Oct 30, 2011"));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        adder.add(new quaks(1.4,"abc","121314", 415L));
////        quaksAdapter adapter=new quaksAdapter(this,adder);
////        ListView lst=(ListView) findViewById(R.id.list);
////        lst.setAdapter(adapter);
////    }
//
//
//}