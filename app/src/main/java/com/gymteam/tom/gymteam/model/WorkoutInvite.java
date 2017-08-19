package com.gymteam.tom.gymteam.model;

/**
 * Created by tomshabtay on 18/08/2017.
 */

public class WorkoutInvite {
    public String name;
    public String description;
    public User creator;
    public Gym gym;

    public WorkoutInvite(String name, String description, User creator, Gym gym){
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.gym = gym;
    }
}
