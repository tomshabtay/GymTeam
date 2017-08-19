package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gymteam.tom.gymteam.model.Model;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyTabsPagerAdapter adapter = new MyTabsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Adding sample data to the Model
        addSampleDataToModel();


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

    }
}
