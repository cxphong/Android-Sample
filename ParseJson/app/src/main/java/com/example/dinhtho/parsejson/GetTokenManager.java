package com.example.dinhtho.parsejson;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by caoxuanphong on    8/18/16.
 */
public class GetTokenManager implements retrofit2.Callback<PostTokenResponse> {
    private static final String TAG = "GetTokenManager";
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_INFO = 1;
    public static final int SUCCESS = 0;
    private static PostTokenResponse mTokenResponse = new PostTokenResponse();
    private Context mContext;
    private GetTokenManagerListener listener;
    public static final String kServerPOSTToGetTokenLink = "https://trendcloud.net/";

    public GetTokenManager(Context context) {
        this.mContext = context;
    }

    public static PostTokenResponse getTokenResponse() {
        return mTokenResponse;
    }

    public interface GetTokenManagerListener {
        void onGetToken(int result);
    }

//    public void getFreeToken(final String deviceId) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ArrayList<String> list = sendPostGetFreeToken(deviceId);
//
//                    /** Save token into database */
//                    if (list != null) {
//                        for (final String s : list) {
//                            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        BuyTokenHistory history = new BuyTokenHistory();
//                                        history.day = "1";
//                                        history.time = DateTimeUtils.currentTimeToEpochWithoutOffset() + "";
//                                        history.token = s;
//
//                                        RealmDatabase.getInstance().addBuyTokenHistoryData(history);
//                                    } catch (Exception e) {
//
//                                    }
//                                }
//                            });
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                Intent i = new Intent(ACTION_GET_FREE_TOKEN);
//                mContext.sendBroadcast(i);
//            }
//        }).start();
//    }

    private ArrayList<String> sendPostGetFreeToken(String deviceId) throws Exception {
            URL obj = new URL(kServerPOSTToGetTokenLink);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //LogPost.saveData(mContext, "Get free token");

            String param = "device_id=" + deviceId;
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(param);
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

            Log.i(TAG, "Response: " + response.toString());
            //LogPost.saveData(mContext, "Response get free token: " + response.toString());

            JSONObject reader = new JSONObject(response.toString());
            String result = reader.getString("result");

            // Save data
            if (result.equalsIgnoreCase("success")) {
                JSONArray arr = reader.getJSONArray("list_token");
                Log.i(TAG, "sendPostGetFreeToken: " + arr);

                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < arr.length(); i++) {
                    list.add(arr.get(i).toString());
                }

                for (String s : list) {
                    Log.i(TAG, "sendPostGetFreeToken: " + s);
                }

                return list;
            } else {
                return null;
            }
    }

    public void getToken(String serialNumber, String customerId, GetTokenManagerListener listener) {
        try {
            this.listener = listener;

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(kServerPOSTToGetTokenLink)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            // prepare call in Retrofit 2.0
            TokenWebAPI stackOverflowAPI = retrofit.create(TokenWebAPI.class);

            Call<PostTokenResponse> call = stackOverflowAPI.getToken(serialNumber, customerId);
            call.enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<PostTokenResponse> call, Response<PostTokenResponse> response) {
        Log.i(TAG, "onResponse: " + response.body());

        try {
            mTokenResponse.result = response.body().result;
            mTokenResponse.device_id = response.body().device_id;
            mTokenResponse.is_unlock = response.body().is_unlock;

            Log.i(TAG, "onResponse: " + mTokenResponse.result);
            Log.i(TAG, "onResponse: " + mTokenResponse.device_id);
            Log.i(TAG, "onResponse: " + mTokenResponse.is_unlock);

            if (mTokenResponse.result.equalsIgnoreCase("fail")) {
                if (listener != null) {
                    listener.onGetToken(ERROR_INFO);
                }
            } else if (mTokenResponse.result.equalsIgnoreCase("success")) {
                mTokenResponse.list_token = response.body().list_token;
                for (Token token : mTokenResponse.list_token) {
                    Log.i(TAG, "success onResponse: "+token.table_name);
                    Log.i(TAG, "success onResponse: "+token.list_token);
                    Log.i(TAG, "success onResponse: "+token.type);
                }

                if (listener != null) {
                    listener.onGetToken(SUCCESS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<PostTokenResponse> call, Throwable t) {
        StringWriter errors = new StringWriter();
        t.printStackTrace(new PrintWriter(errors));
        Log.i(TAG, "onFailure: " + errors);

        //LogPost.saveData(mContext, "Get token at setup \n" + errors.toString());

        if (listener != null) {
            listener.onGetToken(ERROR_NETWORK);
        }
    }

}
