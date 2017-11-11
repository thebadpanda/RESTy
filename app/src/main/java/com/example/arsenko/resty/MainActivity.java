package com.example.arsenko.resty;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button people;
    Button starships;
    Button planets;
    ProgressBar progressBar;
    TextView responseView;

    public static final String API_URL = "https://swapi.co/api/";
    //    public static final String PEOPLE_URL = "people/";
//    public static final String STARSHIPS_URL = "starships/";
//    public static final String PLANET_URL = "planet/";
    public static String STRAIGHT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = (Button) findViewById(R.id.peopleBtn);
        planets = (Button) findViewById(R.id.planetsBtn);
        starships = (Button) findViewById(R.id.starshipsBtn);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        responseView = (TextView) findViewById(R.id.responseView);


        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STRAIGHT_URL = "people/";
                new ForRestTask().execute();
            }

        });


        starships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STRAIGHT_URL = "starships/?format=json";
                new ForRestTask().execute();
            }
        });


        planets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STRAIGHT_URL = "planets/?format=json";
                new ForRestTask().execute();

            }
        });


    }


    private class ForRestTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");

        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL(API_URL + STRAIGHT_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                Log.i("log", "open connection");
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();

                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "Error!";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("log", "response" + response);
            //responseView.setText(response);

            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("results");
                Log.i("log", "results" + array);

                for(int i=0; i<array.length(); i++){
                    JSONObject jObject = array.getJSONObject(i);
                    String name = jObject.optString("name");
                    Log.i("log", "name: " + name);
                    responseView.getText();
                    responseView.setText(responseView.getText() + "\n" + name);

                }


                //JSONObject name = object.getJSONObject("name");
//                responseView.setText();

//                Iterator panelKeys = jObject.keys();
//                while (panelKeys.hasNext()) {
//                    String key = (String) panelKeys.next();
//                    Log.i("log", key);
//                }

              //  responseView.setText((object.getJSONArray("results")).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}



//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.peopleBtn:
//                STRAIGHT_URL = "people/?format=json";
//                ForRestTask peopleTask = new ForRestTask();
//                peopleTask.execute();
//                break;
//            case R.id.starshipsBtn:
//                STRAIGHT_URL = "starships/?format=json";
//                ForRestTask starshipsTask = new ForRestTask();
//                starshipsTask.execute();
//                break;
//            case R.id.planetsBtn:
//                STRAIGHT_URL = "planets/?format=json";
//                ForRestTask planetsTask = new ForRestTask();
//                planetsTask.execute();
//                break;
//        }
//    }


//    public static JSONObject jsonObject(String API_URL){
//        HttpURLConnection httpURLConnection = null;
//
//        try{
//            URL urlToRequest = new URL(API_URL);
//            httpURLConnection = (HttpURLConnection) urlToRequest.openConnection();
////            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
////            httpURLConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
//
//            //handle issues
//            int statusCode = httpURLConnection.getResponseCode();
//            if(statusCode == HttpURLConnection.HTTP_UNAUTHORIZED){
//                Log.e(TAG, "Authorization required");
//            }else if(statusCode != HttpURLConnection.HTTP_OK){
//                Log.e(TAG, "You have another error");
//            }
//
//            //create JSON
//            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
//            return new JSONObject(getResponseText(inputStream));
//
//
////        }catch(MalformedURLException e){
////
////        }catch(IOException e){
////
////        }catch(SocketTimeoutException e){
////
////        }catch(JSONException e){
//
//        }catch(Exception e){
//            Log.e("ERROR", e.getMessage(), e);
//        }
//        finally {
//            if (httpURLConnection != null){
//                httpURLConnection.disconnect();
//            }
//        }
//        return null;
//    }
//
//    private static String getResponseText(InputStream inStream){
//        return new Scanner(inStream).useDelimiter("\\A").next();
//    }
//}
