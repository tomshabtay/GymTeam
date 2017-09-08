package com.gymteam.tom.gymteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gymteam.tom.gymteam.Firebase.EmailPasswordActivity;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAB_BROWSE = "Browse";
    public static final String TAB_MY_INVITES = "My Invites";
    private static final String TAG = "MainActivity";

    TabLayout tabLayout;
    MyTabsPagerAdapter myTabsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("mac");
        myRef.child("test2").setValue("good");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG,dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Adding sample data to the Model
        //addSampleDataToModel();
        //Model.getInstance().fillDataBase();

        Model.getInstance().loadDataBase();


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_BROWSE));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_MY_INVITES));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        myTabsPagerAdapter = new MyTabsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myTabsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                myTabsPagerAdapter.tabMyInvites.setList();
                myTabsPagerAdapter.tabBrowse.setList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int position = tabLayout.getSelectedTabPosition();
        String tab_name = tabLayout.getTabAt(position).getText().toString();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (tab_name == TAB_BROWSE) {
                    myTabsPagerAdapter.tabBrowse.back();
                } else if (tab_name == TAB_MY_INVITES) {
                    myTabsPagerAdapter.tabMyInvites.back();
                }

                return true;
            case R.id.profile_menu_item:
                showProfileActivity();
                return true;
            case R.id.signoutButton:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(this,EmailPasswordActivity.class);
                startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    private void showProfileActivity() {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int position = tabLayout.getSelectedTabPosition();
        String tab_name = tabLayout.getTabAt(position).getText().toString();

        if (tab_name == TAB_BROWSE) {
            myTabsPagerAdapter.tabBrowse.back();
        } else if (tab_name == TAB_MY_INVITES) {
            myTabsPagerAdapter.tabMyInvites.back();
        }


    }

    public void addSampleDataToModel() {
        Model m = Model.getInstance();
        int i = 0;
        for (String name : Ipusm.gymNames) {
            m.addGym(name, Ipusm.gymAddresses[i]);
            i++;
        }


        for (int j = 0; j < Ipusm.userNames.length; j++) {
            m.addUserToGym(Ipusm.gymNames[j % Ipusm.gymNames.length], Ipusm.userNames[j], Ipusm.userIds[j]);

        }

        ArrayList<String> party = new ArrayList<>();

        for (User user : Model.getInstance().usersList.values()){
            party.add(user.getId());
        }

        for (int j = 0; j < Ipusm.inviteNames.length; j++) {

            if(j-2 < Ipusm.inviteNames.length) {
                m.addWorkoutInviteToGym(Ipusm.inviteNames[j],
                        Ipusm.inviteDescriptions[j],
                        Ipusm.userIds[j % Ipusm.userIds.length],
                        Ipusm.gymNames[j % Ipusm.gymNames.length],
                        new ArrayList<String>(party.subList(j, j + 2)));
            }

            else  {
                m.addWorkoutInviteToGym(Ipusm.inviteNames[j],
                        Ipusm.inviteDescriptions[j],
                        Ipusm.userIds[j % Ipusm.userIds.length],
                        Ipusm.gymNames[j % Ipusm.gymNames.length],
                        new ArrayList<String>(party.subList(j-2, j)));
            }
        }



        User user = m.usersList.get(Ipusm.userIds[0]);
        m.setActiveUser(user);
        Log.d("ACTIVE USER", m.activeUser.getName());

    }
}
