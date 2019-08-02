package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

import java.util.List;
@Keep
public class UserQuestionModel{
    public String questionId;
    public String question;
    public List<String> questionType;
    public long timeOfAsking;
    public String userId;
    public String userName;
    public String userImageUrlLow;
    public String userBio;
    public int answerCount;
    public boolean anonymous;
    public boolean userNotified;

    public UserQuestionModel(){}

    public UserQuestionModel(String userId,String userName,String userImageUrlLow,String userBio,long timeOfAsking,String questionId,String question,
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
