package com.example.myapplication;

public class FriendItem {
    private String name;
    private String number;
    private int resourceId;

    // Default constructor for Gson
    public FriendItem() {
    }

    // Additional constructor for manual initialization
    public FriendItem(int resourceId, String name, String number) {
        this.name = name;
        this.number = number;
        this.resourceId = resourceId;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getResourceId() {
        return resourceId;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
