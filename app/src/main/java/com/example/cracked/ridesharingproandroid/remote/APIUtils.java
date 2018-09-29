package com.example.cracked.ridesharingproandroid.remote;

import com.example.cracked.ridesharingproandroid.Interfaces.Service;

public class APIUtils {
    private APIUtils(){}
    public static final String API_URL = "http://10.0.2.2:8080/RideSharingProWS/rest/user";
    public static Service getService(){
        return RetrofitClient.getClient(API_URL).create(Service.class);
    }
}
