package com.gymteam.tom.gymteam.model;

import java.util.ArrayList;

/**
 * Created by tomshabtay on 18/08/2017.
 */

public class User {

    private String name;
    private String id;
    private int age;
    public ArrayList<WorkoutInvite> invites;

    public User(String name, String id){
        this.name = name;
        this.id = id;
        this.age = 0;
        invites = new ArrayList<WorkoutInvite>();
    }

    public User() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
