package com.droid.solver.askapp.Question;

import java.util.List;

public class UserQuestionModel {
    private String questionId;
    private String question;
    private List<String> questionType;
    private long timeOfAsking;
    private String userId;
    private String userName;
    private String userImageUrlLow;
    private String userBio;
    private int answerCount;
    private boolean anonymous;
    private boolean userNotified;

    UserQuestionModel(String userId,String userName,String userImageUrlLow,String userBio,long timeOfAsking,String questionId,String question,
                      List<String> questionType,int answerCount,boolean anonymous,boolean userNotified){

        this.userId=userId;
        this.userName=userName;
        this.userImageUrlLow=userImageUrlLow;
        this.userBio=userBio;
        this.timeOfAsking=timeOfAsking;
        this.questionId=questionId;
        this.question=question;
        this.questionType=questionType;
        this.answerCount=answerCount;
        this.anonymous=anonymous;
        this.userNotified=userNotified;


    }

    public List<String> getQuestionType() {
        return questionType;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public String getUserBio() {
        return userBio;
    }

    public String getUserImageUrlLow() {
        return userImageUrlLow;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public boolean isUserNotified() {
        return userNotified;
    }
}
