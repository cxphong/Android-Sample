package com.example.dinhtho.parsejson;

import java.util.ArrayList;

/**
 * Created by caoxuanphong on    4/27/16.
 */
public class PostTokenResponse {
    public String result;
    public String device_id;
    public ArrayList<Token> list_token = new ArrayList<>();
    public boolean is_unlock;
}
