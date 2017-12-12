package com.roundarch.codetest.part3.model;

/**
 * Created by Ryan on 11/12/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Parcelable{

    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Zipcode")
    @Expose
    private String zipcode;
    @SerializedName("ZipClass")
    @Expose
    private String zipClass;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Latitude")
    @Expose
    private String latitude;

    protected Result(Parcel in) {
        longitude = in.readString();
        zipcode = in.readString();
        zipClass = in.readString();
        county = in.readString();
        city = in.readString();
        state = in.readString();
        latitude = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipClass() {
        return zipClass;
    }

    public void setZipClass(String zipClass) {
        this.zipClass = zipClass;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longitude);
        dest.writeString(zipcode);
        dest.writeString(zipClass);
        dest.writeString(county);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(latitude);
    }
}
