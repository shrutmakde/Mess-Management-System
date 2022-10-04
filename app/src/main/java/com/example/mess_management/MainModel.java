package com.example.mess_management;

public class MainModel {
    String name,count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    MainModel(){

    }

    public MainModel(String name, String count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}