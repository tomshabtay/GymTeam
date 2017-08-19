package com.gymteam.tom.gymteam;


import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;

import java.util.ArrayList;


public class GymListFragment extends ListFragment {


    OnGymSelectedListener mCallback;
    ArrayList<String> list;

    public GymListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();

        for(Gym gym : Model.getInstance().gymsList.values()){
            list.add(gym.getName() + "\nusers: " + String.valueOf(gym.usersInGym.size())+ "\naddress: " + gym.getAddress());
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
            mCallback = (OnGymSelectedListener) getParentFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() +
                    "must implement OnGymSelectedListener");
        }
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String str = this.list.get(position);
        int place = str.indexOf("\n");
        //Getting the name of the gym
        mCallback.onGymSelected(str.substring(0,place));

    }

    public interface OnGymSelectedListener {
        public void onGymSelected(String name);
    }

}
