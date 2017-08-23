package com.gymteam.tom.gymteam.model;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    public HashMap<String,Gym> gymsList;
    public HashMap<String,User> usersList;
    public User activeUser;
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

    public void setActiveUser(User user){
        activeUser = user;

    }

    public void addInvite(WorkoutInvite invite){
        Gym gym = gymsList.get(invite.gym.getName());
        gym.workoutInvitesInGym.add(invite);

        User user = usersList.get(invite.creator.getId());
        user.invites.add(invite);


        Log.d("INV", activeUser.invites.get(0).getName());
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

    public void participateUserInInvite(WorkoutInvite selectedInvite) {
        activeUser.invites.add(selectedInvite);
        selectedInvite.participators.add(activeUser);
        for(WorkoutInvite invite : activeUser.invites){
            Log.d("INV" , invite.getName());
        }

    }

    public void dontParticipateUserInInvite(WorkoutInvite selectedInvite) {
        activeUser.invites.remove(selectedInvite);
        selectedInvite.participators.remove(activeUser);
    }
}
