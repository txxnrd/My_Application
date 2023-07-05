package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class BlogItem implements Parcelable {
    private String day;
    private String title;
    private String day_image;
    private String text;

    // Default constructor for Gson
    public BlogItem() {
    }

    // Additional constructor for manual initialization
    public BlogItem(String day_image, String day, String title, String text) {

        this.day_image = day_image;
        this.day = day;
        this.title = title;
        this.text = text;
    }

    // Constructor used for parcel
    protected BlogItem(Parcel in) {
        day_image = in.readString();
        day = in.readString();
        title = in.readString();
        text = in.readString();

    }

    // the static CREATOR field that implements Parcelable.Creator
    public static final Creator<BlogItem> CREATOR = new Creator<BlogItem>() {
        public BlogItem createFromParcel(Parcel in) {
            return new BlogItem(in);
        }

        public BlogItem[] newArray(int size) {
            return new BlogItem[size];
        }
    };

    // Getter methods
    public String getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDay_image() {return day_image;}

    // Setter methods
    public void setDay(String day) {
        this.day = day;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDay_image(String day_image) {
        this.day_image = day_image;
    }

    // write to Parcel method

    // describeContents method
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(day);
        dest.writeString(day_image);
    }
}
