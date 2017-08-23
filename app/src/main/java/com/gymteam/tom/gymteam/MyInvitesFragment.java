package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymteam.tom.gymteam.model.Model;


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
        setList();
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

    public void setList(){
        InvitesListFragment invitesListFragment = new InvitesListFragment();
        Bundle args = new Bundle();
        invitesListFragment.setArguments(args);
        args.putString(InvitesListFragment.ARG_USER_ID, Model.getInstance().activeUser.getId());
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, invitesListFragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onWorkoutInviteSelected(String gymName, int position) {

    }

    @Override
    public void addInviteToGym(String name) {
        //Nothing here
    }
}
