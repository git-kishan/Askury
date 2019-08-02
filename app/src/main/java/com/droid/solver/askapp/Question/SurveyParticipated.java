package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.HashMap;

@Keep
public class SurveyParticipated{

    public HashMap<String,Integer> surveyMap;
    public ArrayList<HashMap<String,Integer>> surveyMapList;

    //key will be surveyParticipatedId
    //value will be optionChoosenInThatParticipation

    public SurveyParticipated(){}
    public SurveyParticipated(HashMap<String,Integer> surveyMap){
        this.surveyMap=surveyMap;
    }

    public SurveyParticipated(ArrayList<HashMap<String,Integer>> surveyMapList){
        this.surveyMapList=surveyMapList;
    }
    public HashMap<String, Integer> getSurveyMap() {
        return surveyMap;
    }

    public ArrayList<HashMap<String, Integer>> getSurveyMapList() {
        return surveyMapList;
    }
}
