package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

import java.util.ArrayList;
import java.util.HashMap;

@Keep
public class AnswerLike {

    public ArrayList<String> answerLikeId;
    //answerId
    public AnswerLike(){}
    public AnswerLike(ArrayList<String> answerLikeId){
        this.answerLikeId=answerLikeId;
    }

    public ArrayList<String> getAnswerLikeId() {
        return answerLikeId;
    }
}

