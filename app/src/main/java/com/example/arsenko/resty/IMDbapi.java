package com.example.arsenko.resty;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IMDbapi extends AppCompatActivity{

    EditText enteredText;
    TextView showInfo;
    ProgressBar progressBar;
    TextView infoView;

    public final static String IMDB_URL = "http://www.omdbapi.com/";
    public final static String API_KEY = "&apikey=e8c72fb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imdb_layout);

        Intent intent = getIntent();

        enteredText = (EditText) findViewById(R.id.search_by_name);
        showInfo = (TextView) findViewById(R.id.showInfo);

        infoView = (TextView) findViewById(R.id.infoView);

        enteredText.setFocusableInTouchMode(true);
        enteredText.requestFocus();

        enteredText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    String mTitle = "?t=" + enteredText.getText().toString();
                    new IMDBapiTask(mTitle).execute();
                    try {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(enteredText.getApplicationWindowToken(), 0);
                    }catch (NullPointerException e){
                        Log.e("Error log", e.getMessage(), e);
                    }
                }

                return false;
            }
        });
    }

    private class IMDBapiTask extends AsyncTask<String, Void, String>{

        String mTitle;

        public IMDBapiTask(String title) {
            mTitle = title;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            showInfo.setText("");
        }

        @Override
        protected String doInBackground(String... urls) {
            try{
                URL url = new URL(IMDB_URL+mTitle+API_KEY);
                Log.i("URL: ", url.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.i("log", "open connection");
                try{
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }

                }
            }catch (Exception e){
                Log.e("ERROR !!!", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if  (response == null){
                response = "ERROR!!!";
            }
           progressBar.setVisibility(View.GONE);
            Log.i("log", "response: " + response);
//            infoView.setText(response);
            try{
                JSONObject jsonObject = new JSONObject(response);
//                String opt = jsonObject.optString("Title");
//                String opt1 = jsonObject.optString("Year");
//                String opt2 = jsonObject.optString("Rated");

                enteredText.getText();
                infoView.setText("JSON:"+ "\n" + jsonObject);


            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}
