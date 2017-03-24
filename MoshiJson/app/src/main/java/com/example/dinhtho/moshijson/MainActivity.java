package com.example.dinhtho.moshijson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.ToJson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json=" {\n" +
                "            \"title\": \"Blackjack tournament\",\n" +
                "                \"begin_date\": \"20151010\",\n" +
                "                \"begin_time\": \"17:04\"\n" +
                "        }\n";


        Moshi moshi = new Moshi.Builder()
                .add(new EventJsonAdapter())
                .build();
        JsonAdapter<Event> jsonAdapter = moshi.adapter(Event.class);
        try {
            Event event = jsonAdapter.fromJson(json);

            Log.i(TAG, "onCreate: "+event.beginDateAndTime);
            Log.i(TAG, "onCreate: "+event.title);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    class EventJsonAdapter {
        @FromJson
        Event eventFromJson(EventJson eventJson) {
            Event event = new Event();
            event.title = eventJson.title;
            event.beginDateAndTime = eventJson.begin_date + " " + eventJson.begin_time;
            return event;
        }

        @ToJson
        EventJson eventToJson(Event event) {
            EventJson json = new EventJson();
            json.title = event.title;
            json.begin_date = event.beginDateAndTime.substring(0, 8);
            json.begin_time = event.beginDateAndTime.substring(9, 14);
            return json;
        }
    }
}
