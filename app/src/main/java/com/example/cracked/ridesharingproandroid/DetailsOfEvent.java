package com.example.cracked.ridesharingproandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cracked.ridesharingproandroid.Interfaces.Service;
import com.example.cracked.ridesharingproandroid.SqliteDBTrip.DBDriverConnection;
import com.example.cracked.ridesharingproandroid.alarmPk.Alarm_receiver;
import com.example.cracked.ridesharingproandroid.model.Trip;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsOfEvent extends AppCompatActivity {

    //Event Screen
    TextView tripName;
    TextView tripFrom;
    TextView tripTo;

    Context context;

    AlarmManager alarmManage;
    PendingIntent pending_intent;
    PendingIntent pending_intent_send_Again;
    DBDriverConnection dbDriverConnection;
    private static Retrofit retrofit = null;
    Service service;
    int pending_id;
    Intent alarm_intent;
    Trip trip;
    Button openMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //objects
        this.context=this;
        alarmManage = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent my_intent = this.getIntent();

        alarm_intent = new Intent(this.context,Alarm_receiver.class);

        final Intent alarm_intent_again = new Intent(this.context,Alarm_receiver.class);
        final Calendar calendar = Calendar.getInstance();

        pending_id = Integer.parseInt((String) my_intent.getExtras().get("id"));

        pending_intent=pending_intent.getBroadcast(DetailsOfEvent.this,pending_id
                ,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
        dbDriverConnection = new DBDriverConnection(this);

        //this is trip object to get all values from it
        trip = dbDriverConnection.getTrip(Integer.parseInt((String) my_intent.getExtras().get("id")));



        //resources
        tripName = findViewById(R.id.tripName);
        tripName.setText(""+trip.getTripName());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        openMap = findViewById(R.id.mapBtn);
        tripFrom = findViewById(R.id.tripFrom);
        tripFrom.setText(trip.getFrom());
        tripTo = findViewById(R.id.Tripto);
        tripTo.setText(trip.getTo());


        //actions
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTask(pending_id,alarm_intent);

                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartlatitude()+
                        ","+trip.getStartlongtiude()+"&daddr="+trip.getEndlatitude()+", "+trip.getEndlongtiude()+""));
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTask(pending_id,alarm_intent);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    @Override
    public void onBackPressed() {
        // hna han3ml alsho8l bta3 al cancel bardo
        Toast.makeText(context, "Please Press Cancel Button", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        doTask(pending_id,alarm_intent);
    }


    //to stop and update trip to be past
    public void doTask(int pending_id , Intent alarm_intent)
    {
        //act as aware of event

        //Update Local DB
        dbDriverConnection.setTripToBePast(pending_id);


        //Update WS DB
        //moshkela kbera lw ma3hosh net f alwa2t dh ?????????????????????????????????
        service.setTripToBePast( pending_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });



        //--------------------------

        alarm_intent.putExtra("Ex","off");

        alarmManage.cancel(pending_intent);

        sendBroadcast(alarm_intent);

        finish();


    }
    }
