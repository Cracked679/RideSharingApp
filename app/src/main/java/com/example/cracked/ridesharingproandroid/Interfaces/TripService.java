package com.example.cracked.ridesharingproandroid.Interfaces;

import com.example.cracked.ridesharingproandroid.model.Trip;
import com.example.cracked.ridesharingproandroid.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TripService {
    public static final String BASE_URL = "http://192.168.1.6:8080/RideSharingProWS/rest/";

    @POST("getAllUserReserverdTrips.json")
    Call<List<Trip>> getAllUserReserverdTrips(@Body User user);
}
