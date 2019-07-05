package com.droid.solver.askapp.Home;

public class AnswerLikeModel {
    private String likerId;
    private String likerName;
    private String likerImageUrl;
    private String likerBio;
    private String questionId;
    private String askerId;

    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
    }
    public AnswerLikeModel(String likerId,String likerName,String likerImageUrl,String likeBio,String questionId,String askerId){
        this.likerId=likerId;
        this.likerName=likerName;
        this.likerImageUrl=likerImageUrl;
        this.likerBio=likeBio;
        this.questionId=questionId;
        this.askerId = askerId;
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
}
