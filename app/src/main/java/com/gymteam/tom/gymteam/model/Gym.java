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
    public ArrayList<WorkoutInvite> workoutInvites;

    public HashMap<String, User> getUsersInGym() {
        return usersInGym;
    }

    public void setUsersInGym(HashMap<String, User> usersInGym) {
        this.usersInGym = usersInGym;
    }

    public Gym(String name){
        this.name = name;
        this.address = "";
        usersInGym = new HashMap<>();
        workoutInvites = new ArrayList<>();
    }

    public Gym(){

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


    public ArrayList<WorkoutInvite> getWorkoutInvites() {
        return workoutInvites;
    }

    public void setWorkoutInvites(ArrayList<WorkoutInvite> workoutInvites) {
        this.workoutInvites = workoutInvites;
    }
}
