package com.viralandroid.androidtabsatbottom.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by NT on 12/8/2015.
 */
public class JsonUtils {

    /***
     * convert string from server to jsonArray for parse
     * @param data is String
     * @return
     */
    public static JSONArray getJsonArray(String data) {
        JSONArray jsonArray = null;
        try {
            Object json = new JSONTokener(data).nextValue();
            if (json instanceof JSONArray) {
                jsonArray = (JSONArray) json;
                return jsonArray;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    /***
     * convert string from server to json object for parse
     * @param data is String
     * @return
     */
    public static JSONObject getJsonObject(String data) {
        JSONObject jsonObject = null;
        try {
            Object json = new JSONTokener(data).nextValue();
            if (json instanceof JSONObject) {
                jsonObject = (JSONObject) json;
                return jsonObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
