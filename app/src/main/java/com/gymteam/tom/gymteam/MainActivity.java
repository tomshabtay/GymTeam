package com.gymteam.tom.gymteam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gymteam.tom.gymteam.model.Model;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding sample data to the Model
        addSampleDataToModel();

        Button btn_choose_gym = (Button) findViewById(R.id.button_choose_gym);
        btn_choose_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addSampleDataToModel(){
        Model m = Model.getInstance();
        int i = 0;
        for (String name : Ipusm.gymNames){
            m.addGym(name, Ipusm.gymAddresses[i]);
            i++;
        }

        for (int j = 0; j < Ipusm.userNames.length ; j++){
            m.addUserToGym(Ipusm.gymNames[j % Ipusm.gymNames.length], Ipusm.userNames[j],Ipusm.userIds[j]);

        }

        for (int j = 0; j < Ipusm.inviteNames.length ; j++){
            m.addWorkoutInviteToGym(Ipusm.inviteNames[j],
                    Ipusm.inviteDescriptions[j],
                    Ipusm.userIds[j % Ipusm.userIds.length],
                    Ipusm.gymNames[j % Ipusm.gymNames.length]);

        }

    }
}
