package com.gymteam.tom.gymteam.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tomshabtay on 18/08/2017.
 */

public class Gym {
    private String name;
    private String address;

    public HashMap<String,User> usersInGym;
    public ArrayList<WorkoutInvite> workoutInvitesInGym;

    public Gym(String name){
        this.name = name;
        this.address = "";
        usersInGym = new HashMap<>();
        workoutInvitesInGym = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
