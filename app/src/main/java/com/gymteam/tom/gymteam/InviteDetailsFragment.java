package com.gymteam.tom.gymteam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.WorkoutInvite;

/**
 * Created by Tom on 8/19/2017.
 */

public class InviteDetailsFragment extends Fragment {
    public final static String ARG_POSITION = "position_details";
    public final static String ARG_GYM_NAME = "gym_name_details";

    WorkoutInvite selectedInvite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_details_fragment, container, false);

        Bundle args = getArguments();
        Gym gym = Model.getInstance().gymsList.get(args.getString(ARG_GYM_NAME));
        selectedInvite = gym.workoutInvitesInGym.get(args.getInt(ARG_POSITION));


        TextView textView = (TextView) view.findViewById(R.id.invite_title);
        textView.setText(selectedInvite.getName());

        TextView textView2 = (TextView) view.findViewById(R.id.at_gym_details);
        textView2.setText(selectedInvite.getGym().getName());

        TextView textView3 = (TextView) view.findViewById(R.id.creator_name);
        textView3.setText(selectedInvite.getCreator().getName());

        TextView textView4 = (TextView) view.findViewById(R.id.description_invite);
        textView4.setText(selectedInvite.getDescription());








        return view;
    }
}
