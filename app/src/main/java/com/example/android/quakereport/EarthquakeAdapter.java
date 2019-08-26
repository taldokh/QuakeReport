package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USER on 11/01/2019.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){

        super(context,0,earthquakes);
    }
    String locationOffset;
    String primaryLocation;


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake  = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        magnitudeTextView.setText(formatMagnitude(currentEarthquake.getMagnitude()));


        String location = currentEarthquake.getLocation();
        splitLocation(location);

        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetTextView.setText(locationOffset);

        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationTextView.setText(primaryLocation);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        String formattedDate = formatDate(dateObject);

        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        String formattedTime = formatTime(dateObject);

        timeView.setText(formattedTime);

        // so that it can be shown in the ListView
        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private void splitLocation(String location){

        int index = location.indexOf(" of ");
        if(index == -1){
            locationOffset = "Near the";
            primaryLocation = location;
        }
        else{
            locationOffset = location.substring(0,index + 4);
            primaryLocation = location.substring(index + 4);
        }
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
                default:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
