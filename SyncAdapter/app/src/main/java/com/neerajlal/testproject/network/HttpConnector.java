package com.neerajlal.testproject.network;

import android.util.Log;

import com.neerajlal.testproject.utils.IConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by n33raj on 06-03-2016.
 */
public class HttpConnector implements IConstants {

    /**
     * Method to process POST webservice
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, String param) {
        OutputStream outputStream;
        BufferedWriter bufferedWriter;
        StringBuffer response = new StringBuffer(EMPTY);
        int errorCocde;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod(POST);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty(CONTENT_TYPE, APPLICATION_X_WWW_FORM);
            outputStream = urlConnection.getOutputStream();

            if(param != null) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
                bufferedWriter.write(param);
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            outputStream.close();
            errorCocde = urlConnection.getResponseCode();
            if (errorCocde == HttpURLConnection.HTTP_OK) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                response = new StringBuffer();
                String line;
                try {
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    r.close();
                }
            } else {
                Log.e(LOG_TAG, "Failed : HTTP error code : " + errorCocde);
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
