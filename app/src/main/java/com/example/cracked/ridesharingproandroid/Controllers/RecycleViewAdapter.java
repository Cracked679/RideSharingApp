package com.example.cracked.ridesharingproandroid.Controllers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cracked.ridesharingproandroid.model.Trip;
import com.example.cracked.ridesharingproandroid.R;

import java.util.List;

public class RecycleViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecycleViewAdapter.TripViewHolder> {

    private Context context;
    private List<Trip> tripList;
    public RecycleViewAdapter(Context context, List<Trip> tripList) {
        this.context = context;
        this.tripList = tripList;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tripName;
        ImageView tripPhoto;

        TripViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            tripName = (TextView) itemView.findViewById(R.id.trip_name);
            tripPhoto = (ImageView) itemView.findViewById(R.id.Trip_photo);

        }


    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tripName.setText(trip.getTripName());

    }

    @Override
    public int getItemCount() {

        return tripList.size();


    }
}
