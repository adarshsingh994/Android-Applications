package com.adarsh.notes.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by adars on 10/18/2017.
 */

public class Note implements Parcelable {

    private long id;
    private String title;
    private String body;

    public Note(){

    }

    protected Note(Parcel in) {
        id = (long) in.readValue(UUID.class.getClassLoader());
        title = in.readString();
        body = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long uuiid) {
        this.id = uuiid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeString(title);
        dest.writeString(body);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}