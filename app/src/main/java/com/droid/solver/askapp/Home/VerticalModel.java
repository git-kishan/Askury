package com.droid.solver.askapp.Home;

import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class VerticalModel {

    private ArrayList<Object> list;
    VerticalModel (){
        list = new ArrayList<>();
    }

    public void addObject(Object object){
        list.add(object);
    }

    public ArrayList<Object>getList() {
        return list;
    }
}
