package com.gymteam.tom.gymteam;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gymteam.tom.gymteam.R;
import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.WorkoutInvite;

import java.util.ArrayList;


public class InvitesListFragment extends ListFragment {
    final static String ARG_GYM_NAME = "gym_name";

    OnWorkoutInviteSelectedListener mCallback;
    ArrayList<String> list;
    Gym selectedGym;

    public InvitesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String gymName = getArguments().getString(ARG_GYM_NAME);
        list = new ArrayList<>();
        selectedGym = Model.getInstance().gymsList.get(gymName);

        for(WorkoutInvite invite : selectedGym.workoutInvitesInGym){
            list.add(invite.name + "\n" + invite.description);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list);

        setListAdapter(arrayAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnWorkoutInviteSelectedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() +
                    "must implement WorkoutInviteSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


    }

    public interface OnWorkoutInviteSelectedListener {
        public void onWorkoutInviteSelected(String name);
    }


}
