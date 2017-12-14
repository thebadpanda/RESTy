package com.example.arsenko.resty;

import android.content.Intent;
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
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    Button people;
    Button starships;
    Button planets;
    ProgressBar progressBar;
    TextView responseView;
    Button imdbButton;

    public static final String API_URL = "https://swapi.co/api/";
    public static String STRAIGHT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = (Button) findViewById(R.id.peopleBtn);
        planets = (Button) findViewById(R.id.planetsBtn);
        starships = (Button) findViewById(R.id.starshipsBtn);
        imdbButton = (Button) findViewById(R.id.imdbBtn);
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
        imdbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IMDbapi.class);
                startActivity(intent);

            }
        });
    }

    private class ForRestTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");

        }
        protected String doInBackground(Void... urls) {
            // make connection
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
        // parsing JSON
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

//                View.OnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for(int i=0; i<array.length(); i++){
//                            JSONObject jObject = array.getJSONObject(i);
//                            String name = jObject.optString("name");
//                            Log.i("log", "name: " + name);
//                            responseView.getText();
//                            responseView.setText(responseView.getText() + "\n" + name);
//
//                        }
//                    }
//                });
                for(int i=0; i<array.length(); i++){
                    JSONObject jObject = array.getJSONObject(i);
                    String name = jObject.optString("name");
                    Log.i("log", "name: " + name);
                    responseView.getText();
                    responseView.setText(responseView.getText() + "\n" + name);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}