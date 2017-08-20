package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BrowseFragment extends Fragment
        implements GymListFragment.OnGymSelectedListener,
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

        InvitesListFragment invitesListFragment = new InvitesListFragment();
        Bundle args = new Bundle();
        invitesListFragment.setArguments(args);
        args.putString(InvitesListFragment.ARG_GYM_NAME, name);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, invitesListFragment)
                .addToBackStack(null)
                .commit();

    }

    public void back() {
        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof InvitesListFragment) {
            GymListFragment gymListFragment = new GymListFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.fragment_container, gymListFragment).commit();
        } else if (currentFragment instanceof GymListFragment) {

        } else if (currentFragment instanceof InviteDetailsFragment) {
            InviteDetailsFragment fragment = (InviteDetailsFragment) currentFragment;
            onGymSelected(fragment.selectedInvite.getGym().getName());

        } else {

        }


    }


    @Override
    public void onWorkoutInviteSelected(String gymName, int position) {
        InviteDetailsFragment inviteDetailsFragment = new InviteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(InviteDetailsFragment.ARG_POSITION, position);
        args.putString(InviteDetailsFragment.ARG_GYM_NAME, gymName);
        inviteDetailsFragment.setArguments(args);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, inviteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
