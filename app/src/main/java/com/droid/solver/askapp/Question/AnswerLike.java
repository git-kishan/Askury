package com.droid.solver.askapp.Question;

import java.util.HashMap;

public class AnswerLike {

    private HashMap<String,String> answerLikeMap;
    //questionId
    //answerId
    public AnswerLike(HashMap<String,String> answerMap){
        this.answerLikeMap=answerMap;
    }

    public HashMap<String, String> getAnswerLikeMap() {
        return answerLikeMap;
    }
}
class SurveyParticipated{

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
class ImagePollLike{

    private HashMap<String,Integer> imagePollMap;
    //key will be imagePollId
    // value will be optionSelected

   public  ImagePollLike(HashMap<String,Integer> imagePollMap){
       this.imagePollMap=imagePollMap;
    }

    public HashMap<String, Integer> getImagePollMap() {
        return imagePollMap;
    }
}

