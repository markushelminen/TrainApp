package com.example.trainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayApiResolve extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_api_resolve);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateAndTime = sdf.format(new Date());

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.API_MESSAGE);

        final TextView textView = findViewById(R.id.textView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://rata.digitraffic.fi/api/v1/train-tracking/station/"+message+"/"+currentDateAndTime;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String result = response;
                        String data = "";
                        String lastTrain = "";

                        try {
                            JSONArray JArray = new JSONArray(result);
                                for (int i = 0; i < JArray.length(); i++) {
                                    JSONObject jsonObject = JArray.getJSONObject(i);
                                    String trainNumber = jsonObject.optString("trainNumber");
                                    String station = jsonObject.optString("station");
                                    String nextStation = jsonObject.optString("nextStation");
                                    String type = jsonObject.optString("type");
                                    String dataTime = jsonObject.optString("timestamp");

                                    String date = slice_range(dataTime,0,10);
                                    String time = slice_range(dataTime,11,-8);

                                    if(!lastTrain.equals(trainNumber) && type.equals("OCCUPY")){
                                        data += " \n Train Number= " + trainNumber + " \n Train Station = " + decodeStation(station) + " \n Next Station = " + decodeStation(nextStation) + " \n Date of Departure = " + date + " \n Time of Departure = " + time + " \n\n\n\n";
                                        lastTrain = trainNumber;
                                    }
                                }
                                textView.setText(data);
                        } catch (JSONException e) {e.printStackTrace();}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public String slice_range(String s, int startIndex, int endIndex) {
        if (startIndex < 0) startIndex = s.length() + startIndex;
        if (endIndex < 0) endIndex = s.length() + endIndex;
        return s.substring(startIndex, endIndex);
    }

    public String decodeStation (String station) {
        switch (station) {
            case "HY":
                station = "Hyvinkää";
                break;
            case "JP":
                station = "Järvenpää";
                break;
            case "KE":
                station = "Kerava";
                break;
            case "TKL":
                station = "Tikkurila";
                break;
            case "RI":
                station = "Riihimäki";
                break;

            case "END":
                station = "No next station";
                break;
            case "START":
                station = "No previous station";
                break;
            case "SAV":
                station = "Savio";
                break;
            case "KYT":
                station = "Kytömaa";
                break;
            case "SAM":
                station = "Sammalisto";
                break;
            case "PLP":
                station = "Jokela";
                break;
            case "ARP":
                station = "Arolampi";
                break;
            case "PSL":
                station = "Pasila";
                break;
            case "PLA":
                station = "Puistola";
                break;
            case "HKH":
                station = "Hiekkaharju";
                break;
            case "AIN":
                station = "Ainola";
                break;
            case "SAU":
                station = "Saunakallio";
                break;
            case "OLLI":
                station = "Olli";
                break;
            case "VSA":
                station = "Vuosaari";
                break;
            case "KEK":
                station = "Kekomäki";
                break;
            default:
                station = "DEFAULT";
                break;
        }
        return station;
    }
}
