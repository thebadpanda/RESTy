package com.example.arsenko.resty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button people;
    Button starships;
    Button planets;
    ProgressBar progressBar;
    TextView responseView;
    public static final String API_URL = "https://swapi.co/api/?format=json";
    public static final String PEOPLE_URL = "people/";
    public static final String STARSHIPS_URL = "starships/";
    public static final String PLANET_URL = "planet/";
    public static String STRAIGHT_URL;
    public String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        people = (Button) findViewById(R.id.peopleBtn);
        starships = (Button) findViewById(R.id.starshipsBtn);
        planets = (Button) findViewById(R.id.planetsBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        responseView = (TextView)findViewById(R.id.responseView);

    }

    public String getConnection(){
        try {
            URL url = new URL(API_URL + STRAIGHT_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Toast.makeText(this, "open connection", Toast.LENGTH_SHORT).show();
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
            Log.i("log", e.getMessage(), e);
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return  null;
        }

    }


    public void getResponse(String response){
        if (response == null){
            response = "Error!";
        }
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "response:" + response, Toast.LENGTH_SHORT).show();
        Log.i("log", "response" + response);
        responseView.setText(response);
        try{
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
            String requestID = object.getString("requestID");
            JSONArray info = object.getJSONArray("info");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        responseView.setText("");

        switch (v.getId()) {
            case R.id.peopleBtn:
                STRAIGHT_URL = "people/?format=json";
                getResponse(getConnection());
                break;
            case R.id.starshipsBtn:
                STRAIGHT_URL = "starships/?format=json";
                break;
            case R.id.planetsBtn:
                STRAIGHT_URL = "planets/?format=json";
                break;
        }
    }

}
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
//                Log.e("log", "Authorization required");
//            }else if(statusCode != HttpURLConnection.HTTP_OK){
//                Log.e("log", "You have another error");
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
