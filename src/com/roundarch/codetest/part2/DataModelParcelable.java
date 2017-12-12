package com.roundarch.codetest.part2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ryan on 10/12/2017.
 */

public class DataModelParcelable implements Parcelable {

    DataModel dataModel;


    protected DataModelParcelable(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataModelParcelable> CREATOR = new Creator<DataModelParcelable>() {
        @Override
        public DataModelParcelable createFromParcel(Parcel in) {
            return new DataModelParcelable(in);
        }

        @Override
        public DataModelParcelable[] newArray(int size) {
            return new DataModelParcelable[size];
        }
    };
}
