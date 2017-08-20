package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MyInvitesFragment extends Fragment
        implements InvitesListFragment.OnWorkoutInviteSelectedListener {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabFragment3 tabFragment2 = new TabFragment3();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, tabFragment2).commit();
    }



    public void back(){
        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.fragment_container);
        if(currentFragment instanceof InvitesListFragment) {
            GymListFragment gymListFragment = new GymListFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, gymListFragment).commit();
        } else if(currentFragment instanceof GymListFragment) { }
        else {

        }

    }


    @Override
    public void onWorkoutInviteSelected(String gymName, int position) {

    }
}
