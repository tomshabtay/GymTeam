package com.gymteam.tom.gymteam.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    public HashMap<String,Gym> gymsList;
    public HashMap<String,User> usersList;
    User activeUser;
    Gym activeGym;

    public void addUserToGym(String gymName, String name, String id){
        User user = new User(name,id);
        Gym gym = gymsList.get(gymName);
        if(gym != null){
            gym.usersInGym.put(user.getId(),user);
            usersList.put(user.getId(),user);
            Log.d("MODEL","User:" + gymsList.get(gymName).usersInGym.get(id).getName() + " Added To Gym:" + gymName);
        } else{
            Log.d("ERROR","Cannot add user, No gym named " + gymName);
        }


    }

    public void addWorkoutInviteToGym(String name, String description, String userId, String gymName){
        User user = usersList.get(userId);
        Gym gym = gymsList.get(gymName);
        WorkoutInvite invite = new WorkoutInvite(name,description,user,gym);
        gym.workoutInvitesInGym.add(invite);


    }

    public void addGym(String name,String address){
        Gym gym = new Gym(name);
        gym.setAddress(address);
        gymsList.put(gym.getName(),gym);
        Log.d("MODEL", "Added Gym: " + gymsList.get(name).getName());

    }






    //Singleton Model
    private static Model instance = null;

    protected Model() {

    }
    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
            instance.gymsList = new HashMap<>();
            instance.usersList = new HashMap<>();


        }
        return instance;
    }

}
