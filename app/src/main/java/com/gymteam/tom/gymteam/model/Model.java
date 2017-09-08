package com.gymteam.tom.gymteam.model;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    private static final String TAG = "Model";
    public HashMap<String, Gym> gymsList;
    public HashMap<String, User> usersList;
    public User activeUser;


    //Singleton Model
    private static Model instance = null;

    protected Model() {

    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
            instance.gymsList = new HashMap<>();
            instance.usersList = new HashMap<>();
            instance.activeUser = new User("Beatriz Anastasio", "1346346666");//TODO FIX THIS


        }
        return instance;
    }

    public User getActiveUser() {
            return activeUser;


    }

    public void addUserToGym(String gymName, String name, String id) {
        User user = new User(name, id);
        Gym gym = gymsList.get(gymName);
        if (gym != null) {
            gym.usersInGym.put(user.getId(), user);
            usersList.put(user.getId(), user);
            Log.d("MODEL", "User:" + gymsList.get(gymName).usersInGym.get(id).getName() + " Added To Gym:" + gymName);
        } else {
            Log.d("ERROR", "Cannot add user, No gymOfInvite named " + gymName);
        }
    }

    public void addParticipatorToInvite(String gymName, String inviteName, String patcipatorId) {

        for (WorkoutInvite invite : gymsList.get(gymName).getWorkoutInvites()) {
            if (invite.name == inviteName) {
                invite.addParticipator(patcipatorId);
            }
        }

    }

    public void setActiveUser(User user) {
        activeUser = user;

    }

    public void addInvite(WorkoutInvite invite) {
        Gym gym = gymsList.get(invite.gymOfInvite.getName());
        gym.workoutInvites.add(invite);

        User user = getActiveUser();
        user.invites.add(invite);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gyms2");

        String gymName = invite.getGymOfInvite().getName();
        Log.d(TAG, "adding to " + gymName );

        myRef.child(gymName).child("invites").child(invite.getName()).setValue(invite.getName());
        myRef.child(gymName).child("invites").child(invite.getName()).child("name").setValue(invite.getName());
        myRef.child(gymName).child("invites").child(invite.getName()).child("description").setValue(invite.getDescription());
        myRef.child(gymName).child("invites").child(invite.getName()).child("creator").setValue(invite.getCreatorOfInvite().getName());
        myRef.child(gymName).child("invites").child(invite.getName()).child("creator_id").setValue(invite.getCreatorOfInvite().getId());
        myRef.child(gymName).child("invites").child(invite.getName()).child("gym").setValue(invite.getGymOfInvite().getName());


        Log.d("INV", activeUser.invites.get(0).getName());
    }

    public void addWorkoutInviteToGym(String name, String description, String userId, String gymName, ArrayList<String> participators) {
        User user = usersList.get(userId);
        Gym gym = gymsList.get(gymName);
        WorkoutInvite invite = new WorkoutInvite(name, description, user, gym, participators);
        gym.workoutInvites.add(invite);


    }

    public void addGym(String name, String address) {
        Gym gym = new Gym(name);
        gym.setAddress(address);
        gymsList.put(gym.getName(), gym);
        Log.d("MODEL", "Added Gym: " + gymsList.get(name).getName());

    }


    public void participateUserInInvite(WorkoutInvite selectedInvite) {
        activeUser.invites.add(selectedInvite);
        selectedInvite.addParticipator(activeUser.getId());
        for (WorkoutInvite invite : activeUser.invites) {
            Log.d("INV", invite.getName());
        }

    }

    public void dontParticipateUserInInvite(WorkoutInvite selectedInvite) {
        activeUser.invites.remove(selectedInvite);
        selectedInvite.party.remove(activeUser.getId());
    }

    public void setGymsList(HashMap<String, Gym> gyms) {
        instance.gymsList = gyms;
        for (Gym g : instance.gymsList.values()) {
            Log.d("DB", g.getName());
        }
    }


    public void loadDataBase() {

        getUsersFromDatabase();
        instance.gymsList = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gyms2");

        final HashMap<String, Gym> gymsList = new HashMap<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Gym g = snapshot.getValue(Gym.class);
                    gymsList.put(g.getName(), g);
                }

                setGymsList(gymsList);
                loadInvites();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loadInvites() {
        for (final Gym gym : instance.gymsList.values()) {

            final ArrayList<WorkoutInvite> invites = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("gyms2").child(gym.getName()).child("invites");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        WorkoutInvite wi = snapshot.getValue(WorkoutInvite.class);
//
//                        wi.setGymOfInvite(gym);
//                        wi.setCreatorOfInvite(new User(wi.getCreator(), wi.creator_id));
//
//                        loadParticipators(wi);
//
//                        invites.add(wi);
//
//                    }
                        final WorkoutInvite wi = dataSnapshot.getValue(WorkoutInvite.class);

                        wi.setGymOfInvite(gym);
                        wi.setCreatorOfInvite(new User(wi.getCreator(), wi.creator_id));

                        loadParticipators(wi);

                        invites.add(wi);


                    gym.setWorkoutInvites(invites);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    public void loadParticipators(final WorkoutInvite invite) {

        final ArrayList<String> party = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        if(invite == null)
            return;
        if(invite.getName() == null)
            return;
        if(invite.getGymOfInvite() == null)
            return;

        DatabaseReference myRef = database.getReference("gyms2").child(invite.getGymOfInvite().getName()).child("invites").child(invite.getName()).child("participators");
        Log.d("part", myRef.toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("part", snapshot.toString());
                    String id = snapshot.getValue().toString();

                    Log.d("part", id);
                    party.add(id);

                }

                invite.setParty(party);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void getUsersFromDatabase() {

        final HashMap<String, User> userHashMap = new HashMap<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    userHashMap.put(u.getId(), u);
                }

                setUserMap(userHashMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUserMap(HashMap<String, User> map) {
        instance.usersList = map;
    }


    public void fillDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gyms2");
        Model m = Model.getInstance();
        for (Gym g : m.gymsList.values()) {
            String name = g.getName();
            String address = g.getAddress();

            myRef.child(name).setValue(name);
            myRef.child(name).child("address").setValue(address);
            myRef.child(name).child("name").setValue(name);
            for (WorkoutInvite wi : g.getWorkoutInvites()) {
                myRef.child(name).child("invites").child(wi.getName()).setValue(wi.getName());
                myRef.child(name).child("invites").child(wi.getName()).child("name").setValue(wi.getName());
                myRef.child(name).child("invites").child(wi.getName()).child("description").setValue(wi.getDescription());
                myRef.child(name).child("invites").child(wi.getName()).child("creator").setValue(wi.getCreatorOfInvite().getName());
                myRef.child(name).child("invites").child(wi.getName()).child("creator_id").setValue(wi.getCreatorOfInvite().getId());
                myRef.child(name).child("invites").child(wi.getName()).child("gym").setValue(wi.getGymOfInvite().getName());

                for (String part : wi.party) {
                    myRef.child(name).child("invites").child(wi.getName()).child("participators").child(part).setValue(part);
                }
            }
            // done with gyms
        }
        DatabaseReference myRef2 = database.getReference("users");
        for (User user : m.usersList.values()) {
            String name = user.getName();
            int age = user.getAge();
            String id = user.getId();// this is actually an email
            myRef2.child(name).setValue(name);
            myRef2.child(name).child("id").setValue(id);
            myRef2.child(name).child("name").setValue(name);
            myRef2.child(name).child("age").setValue(age);
            for (WorkoutInvite wi : user.invites) {
                myRef2.child(name).child("invites").child(wi.getName()).setValue(wi.getName());
                myRef2.child(name).child("invites").child(wi.getName()).child("name").setValue(wi.getName());
                myRef2.child(name).child("invites").child(wi.getName()).child("description").setValue(wi.getDescription());
                myRef2.child(name).child("invites").child(wi.getName()).child("creator").setValue(wi.getCreatorOfInvite().getName());
                myRef2.child(name).child("invites").child(wi.getName()).child("creator_id").setValue(wi.getCreatorOfInvite().getId());
                myRef2.child(name).child("invites").child(wi.getName()).child("gym").setValue(wi.getGymOfInvite().getName());
            }

        }
        //done with users
    }
}
