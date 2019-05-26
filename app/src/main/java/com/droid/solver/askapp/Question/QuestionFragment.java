package com.droid.solver.askapp.Question;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.ImagePoll.ImagePollActivity;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.LanguageSelectionActivity;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;


public class QuestionFragment extends Fragment {


    SpeedDialView speedDialView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_question, container, false);
        speedDialView=view.findViewById(R.id.speedDial);
        addFabItem();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_action1:
                        startActivity(new Intent(getActivity(), ImagePollActivity.class));
                        return false;
                    case R.id.fab_action2:
                        startActivity(new Intent(getActivity(), LanguageSelectionActivity.class));
                        return false;
                    case R.id.fab_action3:
                        startActivity(new Intent(getActivity(),QuestionActivity.class));
                        return false; // true to keep the floating button Speed Dial open

                    default:
                        return false;
                }
            }
        });
    }

    private void addFabItem(){

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action1, R.drawable.ic_imagepoll)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.google_color,null))
                        .setLabel("Image poll")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action2, R.drawable.ic_survey)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary,null))
                        .setLabel("Survey")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action3, R.drawable.ic_ask)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow,null))
                        .setLabel("Ask")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

    }

}
