package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendItem implements Parcelable {
    private String name;
    private String number;
    private String imageUrl;
    private String description;

    // Default constructor for Gson
    public FriendItem() {
    }

    // Additional constructor for manual initialization
    public FriendItem(String imageUrl, String name, String number, String description) {
        this.name = name;
        this.number = number;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    // Constructor used for parcel
    protected FriendItem(Parcel in) {
        name = in.readString();
        number = in.readString();
        imageUrl = in.readString();
        description = in.readString();
    }

    // the static CREATOR field that implements Parcelable.Creator
    public static final Parcelable.Creator<FriendItem> CREATOR = new Parcelable.Creator<FriendItem>() {
        public FriendItem createFromParcel(Parcel in) {
            return new FriendItem(in);
        }

        public FriendItem[] newArray(int size) {
            return new FriendItem[size];
        }
    };

    // Getter methods
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {return description;}

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // write to Parcel method

    // describeContents method
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(imageUrl);
        dest.writeString(description);
    }
}
