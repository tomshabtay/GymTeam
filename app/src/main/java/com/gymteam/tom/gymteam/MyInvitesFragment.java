package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymteam.tom.gymteam.model.Model;


public class MyInvitesFragment extends Fragment
        implements InvitesListFragment.OnWorkoutInviteSelectedListener,
        InviteDetailsFragment.InviteDetailsActions{


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);




        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setList();
    }



    public void back(){
        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.fragment_container);
        if(currentFragment instanceof InvitesListFragment) {

        } else if(currentFragment instanceof InviteDetailsFragment) {
            setList();
        }
        else {

        }

    }

    public void setList(){
        InvitesListFragment invitesListFragment = new InvitesListFragment();
        Bundle args = new Bundle();
        invitesListFragment.setArguments(args);
        args.putString(InvitesListFragment.ARG_USER_ID, Model.getInstance().getActiveUser().getId());
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, invitesListFragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onWorkoutInviteSelected(String userId, int position) {
        InviteDetailsFragment inviteDetailsFragment = new InviteDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(InviteDetailsFragment.ARG_POSITION, position);
        args.putString(InviteDetailsFragment.ARG_USER_ID, userId);
        inviteDetailsFragment.setArguments(args);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, inviteDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addInviteToGym(String name) {
        //Nothing here
    }

    @Override
    public void goBackToList() {
        setList();
    }
}
