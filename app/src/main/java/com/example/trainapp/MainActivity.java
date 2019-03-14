package com.example.trainapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    public static final String API_MESSAGE = "com.example.trainapp.MESSAGE";
    String selection = "HY";
    String[] selections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stations_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        selections = getResources().getStringArray(R.array.stations_array);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selection = selections[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selection = "Hyvinkää";
            }

        });
    }

    public void sendApiCall(View view) {
        Intent intent = new Intent(this, DisplayApiResolve.class);
        String message = getStationCode(selection);
        intent.putExtra(API_MESSAGE, message);
        startActivity(intent);
    }

    public String getStationCode (String station) {
        switch (station) {
            case "Hyvinkää":
                station = "HY";
                break;
            case "Järvenpää":
                station = "JP";
                break;
            case "Kerava":
                station = "KE";
                break;
            case "Tikkurila":
                station = "TKL";
                break;
            case "Riihimäki":
                station = "RI";
                break;
            default:
                station = "HY";
                break;
        }
        return station;
    }
}
