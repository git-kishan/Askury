package com.droid.solver.askapp.Notification;

import androidx.annotation.Keep;

@Keep
public class QuestionModel{

    public String questionId;
    public String askerId;
    public String likerId;
    public String likerName;
    public String likerImageUrl;
    public String likerBio;
    public String answerId;
    public String answererId;
    public String type;
    public long notifiedTime;
    public boolean isStoredLocally;


    public QuestionModel(){}

    public QuestionModel(String likerId,
                         String likerName,
                         String likerImageUrl,
                         String likerBio,
                         String askerId,
                         String answerId,
                         String answererId,
                         String questionId,
                         String type,
                         long notifiedTime,
                         boolean isStoredLocally) {
        this.likerId = likerId;
        this.likerName = likerName;
        this.likerImageUrl = likerImageUrl;
        this.likerBio = likerBio;
        this.askerId = askerId;
        this.answerId=answerId;
        this.answererId=answererId;
        this.questionId = questionId;
        this.type = type;
        this.notifiedTime=notifiedTime;
        this.isStoredLocally=isStoredLocally;
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

    public String getAnswererId() {
        return answererId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public boolean isStoredLocally() {
        return isStoredLocally;
    }

    public void setStoredLocally(boolean storedLocally) {
        isStoredLocally = storedLocally;
    }
}
