package com.example.dinhtho.parsejson;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by caoxuanphong on    8/18/16.
 */
public class GetTokenManager implements retrofit2.Callback<PostTokenResponse> {
    private static final String TAG = "GetTokenManager";
    public static final int ERROR_NETWORK = 2;
    public static final int ERROR_INFO = 1;
    public static final int SUCCESS = 0;
    private static PostTokenResponse mTokenResponse = new PostTokenResponse();
    private GetTokenManagerListener listener;
    public static final String kServerPOSTToGetTokenLink = "https://trendcloud.net/";

    public interface GetTokenManagerListener {
        void onGetToken(int result);
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
                    Log.i(TAG, "success onResponse: " + token.table_name);
                    Log.i(TAG, "success onResponse: " + token.list_token);
                    Log.i(TAG, "success onResponse: " + token.type);
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

        if (listener != null) {
            listener.onGetToken(ERROR_NETWORK);
        }
    }

}

class Token {
    public String table_name;
    public ArrayList<String> list_token;
    public String type;
}

class PostTokenResponse {
    public String result;
    public String device_id;
    public ArrayList<Token> list_token = new ArrayList<>();
    public boolean is_unlock;
}

interface TokenWebAPI {
    @FormUrlEncoded
    @POST("/alpha/index.php/apis/ControllerApisGetTokenTable")
    Call<PostTokenResponse> getToken(@Field("serial_number") String serial_number,
                                     @Field("customer_id") String customer_id);

}