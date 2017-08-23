package com.gymteam.tom.gymteam;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gymteam.tom.gymteam.model.Gym;
import com.gymteam.tom.gymteam.model.Model;
import com.gymteam.tom.gymteam.model.WorkoutInvite;


public class AddInviteFragment extends Fragment {
    public static final String ARG_GYM_NAME = "gym_name";

    AddInviteFragment.OnButtonClick mCallback;


    public AddInviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_invite, container, false);
        Button send = (Button) v.findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInviteToModel();
                mCallback.onButtonClick();
            }
        });

        Button cancel = (Button) v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonClick();
            }
        });


        return v;
    }

    private void saveInviteToModel() {
        Model m = Model.getInstance();
        EditText inviteNameEdit = (EditText) getView().findViewById(R.id.edit_invite_name);
        String invite_name = inviteNameEdit.getText().toString();

        EditText inviteDescriptionEdit = (EditText) getView().findViewById(R.id.description_edit);
        String invite_description = inviteDescriptionEdit.getText().toString();

        Gym gym = m.gymsList.get(getArguments().getString(ARG_GYM_NAME));

        WorkoutInvite invite = new WorkoutInvite(invite_name,invite_description,m.activeUser,gym);
        m.addInvite(invite);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnButtonClick) getParentFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() +
                    "must implement OnButtonClick");
        }
    }

    public interface OnButtonClick {

        public void onButtonClick();

    }

}
