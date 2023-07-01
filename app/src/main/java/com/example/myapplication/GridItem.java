package com.example.myapplication;

public class GridItem {
    String num;
    String name;
    int resId;

    public GridItem(String num, String name, int resId){
        this.num = num;
        this.name = name;
        this.resId = resId;
    }
    public String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getIcon(){
        return resId;
    }
    public void setIcon(int resId){
        this.resId = resId;
    }
}
