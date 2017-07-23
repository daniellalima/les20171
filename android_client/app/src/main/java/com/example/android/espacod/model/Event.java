package com.example.android.espacod.model;

public class Event {

    int mId;
    String mTitle;

    public Event(int mId, String mTitle) {
        this.mId = mId;
        this.mTitle = mTitle;
    }

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
}