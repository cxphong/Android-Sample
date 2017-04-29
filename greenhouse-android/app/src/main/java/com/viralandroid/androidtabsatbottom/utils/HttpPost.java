package com.viralandroid.androidtabsatbottom.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by caoxuanphong on    8/18/16.
 */
public class HttpPost {
    private static final String TAG = "HttpPost";

    public interface HttpPostListener {
        void onPosted(String result);
    }

    /**
     * Example: post(url, "customer_id=1&user=phong", listener)
     *
     * @param url
     * @param urlParameters
     * @param listener
     */
    public void post(final String url, final String urlParameters, final HttpPostListener listener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    //add reuqest header
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                    // Send post request
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.flush();
                    wr.close();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Log.i(TAG, "sendPost: " + response.toString());

                    listener.onPosted(response.toString());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                listener.onPosted(null);
            }
        }).start();
    }


}
