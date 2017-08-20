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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }
}
