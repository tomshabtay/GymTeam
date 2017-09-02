package com.gymteam.tom.gymteam.model;

import java.util.ArrayList;

/**
 * Created by tomshabtay on 18/08/2017.
 */

public class WorkoutInvite {
    public String name;
    public String description;
    public User creatorOfInvite;
    public String creator;
    public String creator_id;
    public ArrayList<String> party;
    public Gym gymOfInvite;

    public WorkoutInvite(String name, String description, User creator, Gym gym, ArrayList<String> participators){
        this.name = name;
        this.description = description;
        this.creatorOfInvite = creator;
        this.gymOfInvite = gym;
        this.party = participators;
    }

    public WorkoutInvite(){

    }

    public void addParticipator(String id){
        if (!party.contains(id)){
            party.add(id);
        }
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

    public User getCreatorOfInvite() {
        return creatorOfInvite;
    }

    public void setCreatorOfInvite(User creatorOfInvite) {
        this.creatorOfInvite = creatorOfInvite;
    }

    public Gym getGymOfInvite() {
        return gymOfInvite;
    }

    public void setGymOfInvite(Gym gymOfInvite) {
        this.gymOfInvite = gymOfInvite;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }


    public ArrayList<String> getParty() {
        return party;
    }


    public void setParty(ArrayList<String> party) {
        this.party = party;
    }
}
