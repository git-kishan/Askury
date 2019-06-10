package com.droid.solver.askapp.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyParticipated{

    private HashMap<String,Integer> surveyMap;
    private ArrayList<HashMap<String,Integer>> surveyMapList;

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
