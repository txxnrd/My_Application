package com.example.myapplication;

public class FriendItem {
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
}
