package com.gymteam.tom.gymteam;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.User;
import com.gymteam.tom.gymteam.model.WorkoutInvite;

/**
 * Created by Tom on 8/19/2017.
 */

public class InviteDetailsFragment extends Fragment {
    public final static String ARG_POSITION = "position_details";
    public final static String ARG_GYM_NAME = "gym_name_details";
    public final static String ARG_USER_ID = "gym_user_id";

    InviteDetailsActions mCallback;

    WorkoutInvite selectedInvite;
    boolean userIsTheCreator;
    boolean userIsParticipating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_details_fragment, container, false);

        Bundle args = getArguments();
        Gym gym = Model.getInstance().gymsList.get(args.getString(ARG_GYM_NAME));
        if(gym != null){
            selectedInvite = gym.workoutInvitesInGym.get(args.getInt(ARG_POSITION));
        }else {
            User user = Model.getInstance().usersList.get(args.getString(ARG_USER_ID));
            selectedInvite = user.invites.get(args.getInt(ARG_POSITION));
        }

        userIsParticipating = checkIfUserParticipating();
        userIsTheCreator = checkIfUserIsTheCreator();


        TextView textView = (TextView) view.findViewById(R.id.invite_title);
        textView.setText(selectedInvite.getName());

        TextView textView2 = (TextView) view.findViewById(R.id.at_gym_details);
        textView2.setText(selectedInvite.getGym().getName());

        TextView textView3 = (TextView) view.findViewById(R.id.creator_name);
        textView3.setText(selectedInvite.getCreator().getName());

        TextView textView4 = (TextView) view.findViewById(R.id.description_invite);
        textView4.setText(selectedInvite.getDescription());

        Button participate_btn = (Button) view.findViewById(R.id.participate);
        if(userIsParticipating) {
            participate_btn.setText("Don't Participate");
        }

        participate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIsParticipating){
                    dontParticipate();
                } else{
                    participate();
                }
            }
        });



        //Button back_to_list_btn = (Button) view.findViewById(R.id.);




        return view;
    }

    public void participate(){
        Log.d("PAR","par called ");
        Model m = Model.getInstance();
        m.participateUserInInvite(selectedInvite);
        mCallback.goBackToList();
    }

    public void dontParticipate(){
        Log.d("PAR","dont par called ");
        Model m = Model.getInstance();
        m.dontParticipateUserInInvite(selectedInvite);
        mCallback.goBackToList();
    }



    private boolean checkIfUserIsTheCreator(){
        Model m = Model.getInstance();
        if(m.activeUser.getId().equals(this.selectedInvite.getCreator().getId())){
            return true;
        }
        return false;
    }

    private boolean checkIfUserParticipating(){
        Model m = Model.getInstance();
        for(WorkoutInvite invite : m.activeUser.invites){
            if(invite.getName().equals(selectedInvite.getName())){
                return true;
            }
        }
        return false;
    }

    public interface InviteDetailsActions{
        public void goBackToList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (InviteDetailsActions) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() +
                    "must implement ");
        }
    }
}
