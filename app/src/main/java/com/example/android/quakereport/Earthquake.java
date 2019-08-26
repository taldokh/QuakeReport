package com.example.android.quakereport;

/**
 * Created by USER on 11/01/2019.
 */

public class Earthquake {

    private double magnitude;

    private String location;

    private long mTimeInMilliseconds;

    private String websiteUrl;

    public Earthquake(double magnitude, String location, long mTimeInMilliseconds, String websiteUrl){

        this.magnitude = magnitude;
        this.location = location;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.websiteUrl = websiteUrl;
    }

    public double getMagnitude(){ return magnitude; }

    public String getLocation(){
        return location;
    }

    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }

    public String getWebsiteUrl(){ return websiteUrl; }
}
