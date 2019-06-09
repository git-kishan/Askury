package com.droid.solver.askapp.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class AnswerLike {

    private ArrayList<String> answerLikeId;
    //answerId
    public AnswerLike(){}
    public AnswerLike(ArrayList<String> answerLikeId){
        this.answerLikeId=answerLikeId;
    }

    public ArrayList<String> getAnswerLikeId() {
        return answerLikeId;
    }
}

