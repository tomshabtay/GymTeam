package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.User;

public class MainActivity extends AppCompatActivity  {

    public static final String TAB_BROWSE = "Browse";
    public static final String TAB_MY_INVITES = "My Invites";

    TabLayout tabLayout;
    MyTabsPagerAdapter myTabsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Adding sample data to the Model
        addSampleDataToModel();


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

        switch (item.getItemId()){
            case android.R.id.home:
                if(tab_name == TAB_BROWSE) {
                    myTabsPagerAdapter.tabBrowse.back();
                }
                else if(tab_name == TAB_MY_INVITES){
                    myTabsPagerAdapter.tabMyInvites.back();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int position = tabLayout.getSelectedTabPosition();
        String tab_name = tabLayout.getTabAt(position).getText().toString();

        if(tab_name == TAB_BROWSE) {
            myTabsPagerAdapter.tabBrowse.back();
        }
        else if(tab_name == TAB_MY_INVITES){
            myTabsPagerAdapter.tabMyInvites.back();
        }


    }

    public void addSampleDataToModel(){
        Model m = Model.getInstance();
        int i = 0;
        for (String name : Ipusm.gymNames){
            m.addGym(name, Ipusm.gymAddresses[i]);
            i++;
        }

        for (int j = 0; j < Ipusm.userNames.length ; j++){
            m.addUserToGym(Ipusm.gymNames[j % Ipusm.gymNames.length], Ipusm.userNames[j],Ipusm.userIds[j]);

        }

        for (int j = 0; j < Ipusm.inviteNames.length ; j++){
            m.addWorkoutInviteToGym(Ipusm.inviteNames[j],
                    Ipusm.inviteDescriptions[j],
                    Ipusm.userIds[j % Ipusm.userIds.length],
                    Ipusm.gymNames[j % Ipusm.gymNames.length]);

        }

        User user = m.usersList.get(Ipusm.userIds[0]);
        m.setActiveUser(user);
        Log.d("ACTIVE USER", m.activeUser.getName());

    }
}
