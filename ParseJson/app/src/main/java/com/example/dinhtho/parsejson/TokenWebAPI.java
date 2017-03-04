package com.example.dinhtho.parsejson;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by caoxuanphong on    4/27/16.
 */
public interface TokenWebAPI {
        @FormUrlEncoded
        @POST("/alpha/index.php/apis/ControllerApisGetTokenTable")
        Call<PostTokenResponse> getToken(@Field("serial_number") String serial_number,
                                         @Field("customer_id") String customer_id);

}
