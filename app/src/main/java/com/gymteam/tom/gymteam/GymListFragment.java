package com.gymteam.tom.gymteam;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;

import java.util.ArrayList;


public class GymListFragment extends ListFragment {


    OnGymSelectedListener mCallback;
    ArrayList<Gym> list;

    public GymListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        list = new ArrayList<>();

        for (Gym gym : Model.getInstance().gymsList.values()) {
            list.add(gym);
        }

        GymListAdapter arrayAdapter = new GymListAdapter(
                getContext(),
                list);

        setListAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnGymSelectedListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() +
                    "must implement OnGymSelectedListener");
        }
        getActivity().supportInvalidateOptionsMenu();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String name = this.list.get(position).getName();
        //Getting the name of the gym
        mCallback.onGymSelected(name);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.add_menu);
        menuItem.setVisible(false);

        Log.d("MENU", menuItem.getTitle().toString());


    }



    public interface OnGymSelectedListener {
        public void onGymSelected(String name);
    }

    public class GymListAdapter extends ArrayAdapter<Gym> {

        public GymListAdapter(Context context, ArrayList<Gym> values) {
            super(context, R.layout.gym_row_layout, values);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View theView = inflater.inflate(R.layout.gym_row_layout, parent, false);
            Gym gym = getItem(position);
            TextView textView = (TextView) theView.findViewById(R.id.invite_name);
            textView.setText(gym.getName());

            TextView textView2 = (TextView) theView.findViewById(R.id.gym_address);
            textView2.setText(gym.getAddress());


            return theView;
        }

    }
}
