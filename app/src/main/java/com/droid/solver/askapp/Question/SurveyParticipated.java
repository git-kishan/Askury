package com.droid.solver.askapp.Question;

import java.util.HashMap;

public class SurveyParticipated{

    private HashMap<String,Integer> surveyMap;
    //key will be surveyParticipatedId
    //value will be optionChoosenInThatParticipation
    public SurveyParticipated(HashMap<String,Integer> surveyMap){
        this.surveyMap=surveyMap;
    }

    public HashMap<String, Integer> getSurveyMap() {
        return surveyMap;
    }

}
