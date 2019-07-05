package com.droid.solver.askapp.Notification;

public class QuestionModel {

    private String questionId;
    private String askerId;
    private String likerId;
    private String likerName;
    private String likerImageUrl;
    private String likerBio;
    private String type;
    private long notifiedTime;

    public QuestionModel(){}

    public QuestionModel(String likerId,
                         String likerName,
                         String likerImageUrl,
                         String likerBio,
                         String askerId,
                         String questionId,
                         String type,
                         long notifiedTime) {
        this.likerId = likerId;
        this.likerName = likerName;
        this.likerImageUrl = likerImageUrl;
        this.likerBio = likerBio;
        this.askerId = askerId;
        this.questionId = questionId;
        this.type = type;
        this.notifiedTime=notifiedTime;
    }

    public String getAskerId() {
        return askerId;
    }

    public String getType() {
        return type;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getLikerName() {
        return likerName;
    }

    public String getLikerId() {
        return likerId;
    }

    public String getLikerBio() {
        return likerBio;
    }

    public String getLikerImageUrl() {
        return likerImageUrl;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }
}
