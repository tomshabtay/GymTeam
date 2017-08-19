package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class ListActivity extends Activity
        implements GymListFragment.OnGymSelectedListener ,
                   InvitesListFragment.OnWorkoutInviteSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        GymListFragment gymListFragment = new GymListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, gymListFragment).commit();
    }



    @Override
    public void onGymSelected(String name) {
        Log.d("GYM_SELECTED", name);
        InvitesListFragment invitesListFragment = new InvitesListFragment();
        Bundle args = new Bundle();
        invitesListFragment.setArguments(args);
        args.putString(InvitesListFragment.ARG_GYM_NAME,name);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,invitesListFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onWorkoutInviteSelected(String name) {
        Log.d("INVITE_SELECTED", name);
    }
}
