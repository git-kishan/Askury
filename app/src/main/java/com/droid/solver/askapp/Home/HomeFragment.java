package com.droid.solver.askapp.Home;


import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.Community.CommunityModel;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        VerticalModel verticalModel=new VerticalModel();
//        HorizontalModel horizontalModel=new HorizontalModel();
        verticalModel.addObject(new AskImagePollModel());
        verticalModel.addObject(new RootQuestionModel());
        verticalModel.addObject(new RootQuestionModel());
        verticalModel.addObject(new RootQuestionModel());
        verticalModel.addObject(new AskSurveyModel());
        ArrayList<Object> list = new ArrayList<>();
        list.add(verticalModel);
        HomeRecyclerViewAdapter adapter=new HomeRecyclerViewAdapter(getActivity(), list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
