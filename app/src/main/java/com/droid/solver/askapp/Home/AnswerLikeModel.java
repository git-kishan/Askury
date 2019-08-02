package com.droid.solver.askapp.Home;

import androidx.annotation.Keep;

@Keep
public class AnswerLikeModel{
    public String likerId;
    public String likerName;
    public String likerImageUrl;
    public String likerBio;
    public String questionId;
    public String askerId;
    public String answerId;
    public String answererId;
    public String type;
    public long notifiedTime;


    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
    }
    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio,String questionId,String askerId
    ,String answerId,String answererId){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
        this.questionId=questionId;
        this.askerId = askerId;
        this.answerId=answerId;
        this.answererId=answererId;
    }
    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio,String questionId,String askerId
            ,String answerId,String answererId,String type,long notifiedTime){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
        this.questionId=questionId;
        this.askerId = askerId;
        this.answerId=answerId;
        this.answererId=answererId;
        this.type=type;
        this.notifiedTime=notifiedTime;
    }

    public String getLikerImageUrl() {
        return likerImageUrl;
    }

    public String getLikerBio() {
        return likerBio;
    }

    public String getLikerId() {
        return likerId;
    }

    public String getLikerName() {
        return likerName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAskerId() {
        return askerId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getAnswererId() {
        return answererId;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }

    public String getType() {
        return type;
    }
}
