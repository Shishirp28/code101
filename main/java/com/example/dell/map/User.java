package com.example.dell.map;

/**
 * Created by yogesh on 12/11/17.
 */

public class User {

    private String name;
   // private String Location;

    public User() {

    }

    public User(String name) {

        this.name = name;
       // this.Location = Location;
    }

    public String getName() {

        return name;
    }

//    public String getLocation(){
//
//        return Location;
//    }

    public void setName(String name) {

        this.name = name;
    }

    //public void setLocation(String Location)
//    {
//        this.Location = Location;
//    }
}
