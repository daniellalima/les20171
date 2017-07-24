package com.example.android.espacod.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private int mId;
    private String mTitle;
    private String mDescription;
    private String mCategory;

    public Event(int mId, String mTitle, String mDescription, String mCategory) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
    }

    private Event(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mCategory = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mDescription);
        parcel.writeString(mCategory);
        parcel.writeInt(mId);
    }
}