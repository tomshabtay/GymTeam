package com.gymteam.tom.gymteam;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment
        implements GymListFragment.OnGymSelectedListener ,
                   InvitesListFragment.OnWorkoutInviteSelectedListener {



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GymListFragment gymListFragment = new GymListFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container, gymListFragment).commit();
    }

    @Override
    public void onGymSelected(String name) {
        Log.d("GYM_SELECTED", name);
        InvitesListFragment invitesListFragment = new InvitesListFragment();
        Bundle args = new Bundle();
        invitesListFragment.setArguments(args);
        args.putString(InvitesListFragment.ARG_GYM_NAME,name);
        getChildFragmentManager()
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
