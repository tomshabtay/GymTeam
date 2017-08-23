package com.gymteam.tom.gymteam;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.User;
import com.gymteam.tom.gymteam.model.WorkoutInvite;

import java.util.ArrayList;


public class InvitesListFragment extends ListFragment {
    public final static String ARG_GYM_NAME = "gym_name";
    public final static String ARG_USER_ID = "user_id";

    OnWorkoutInviteSelectedListener mCallback;
    ArrayList<WorkoutInvite> list;
    Gym selectedGym;
    User selectedUser;
    InviteListAdapter arrayAdapter;

    boolean fromUser;

    public InvitesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String gymName = getArguments().getString(ARG_GYM_NAME);
        String userId = getArguments().getString(ARG_USER_ID);

        list = new ArrayList<>();

        if (gymName != null) {
            selectedGym = Model.getInstance().gymsList.get(gymName);
            arrayAdapter = new InviteListAdapter(
                    getContext(),
                    selectedGym.workoutInvitesInGym);
            fromUser = false;


        } else if(userId != null){
            selectedUser = Model.getInstance().usersList.get(userId);
            arrayAdapter = new InviteListAdapter(
                    getContext(),
                    selectedUser.invites);
            fromUser = true;

        }

        setListAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invites_list, container, false);





        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_invite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.addInviteToGym(selectedGym.getName());
            }
        });

        if (fromUser){
            fab.hide();
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnWorkoutInviteSelectedListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() +
                    "must implement WorkoutInviteSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(!fromUser){
            mCallback.onWorkoutInviteSelected(selectedGym.getName(), position);
        }else if(fromUser){
            mCallback.onWorkoutInviteSelected(selectedUser.getId(), position);
        }


    }


    public interface OnWorkoutInviteSelectedListener {

        public void onWorkoutInviteSelected(String gymName, int position);

        public void addInviteToGym(String name);
    }


    public class InviteListAdapter extends ArrayAdapter<WorkoutInvite> {

        public InviteListAdapter(Context context, ArrayList<WorkoutInvite> values) {
            super(context, R.layout.invites_row_layout, values);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View theView = inflater.inflate(R.layout.invites_row_layout, parent, false);
            WorkoutInvite invite = getItem(position);
            TextView textView = (TextView) theView.findViewById(R.id.invite_name);
            textView.setText(invite.getName());

            TextView textView2 = (TextView) theView.findViewById(R.id.gym_name);
            textView2.setText(invite.getGym().getName());

            TextView textView3 = (TextView) theView.findViewById(R.id.creator_name);
            textView3.setText(invite.getCreator().getName());

            TextView textView4 = (TextView) theView.findViewById(R.id.description_invite);
            textView4.setText(invite.getDescription());



            return theView;
        }

    }


}
