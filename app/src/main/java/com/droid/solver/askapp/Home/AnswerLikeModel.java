package com.droid.solver.askapp.Home;

public class AnswerLikeModel {
    private String likerId;
    private String likerName;
    private String likerImageUrl;
    private String likerBio;
    private String questionId;
    private String askerId;
    private String answerId;
    private String answererId;
    private String type;
    private long notifiedTime;


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
