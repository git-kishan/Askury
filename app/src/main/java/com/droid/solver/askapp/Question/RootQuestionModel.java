package com.droid.solver.askapp.Question;

import androidx.annotation.Keep;

import java.util.List;
@Keep
public class RootQuestionModel{

    public String askerId;
    public String askerName;
    public String askerImageUrlLow;
    public boolean anonymous;
    public String askerBio;
    public String questionId;
    public String question;
    public List <String>questionType;
    public long timeOfAsking;
    public long timeOfAnswering;
    public String recentAnswererId;
    public String recentAnswererImageUrlLow;
    public String recentAnswererName;
    public String recentAnswererBio;
    public String recentAnswerId;
    public String recentAnswer;
    public boolean recentAnswerImageAttached;
    public String recentAnswerImageUrl;
    public int answerCount;
    public int recentAnswerLikeCount;
    public int fontUsed;
    public int point;
    public boolean isAnswered;

    private boolean likedByMe;

    public RootQuestionModel(){

    }

    public RootQuestionModel(
            String askerId,
            String askerName,
            String askerImageUrlLow,
            boolean anonymous,
            String askerBio,
            String questionId,
            String question,
            List<String> questionType,
            long timeOfAsking,
            String recentAnswererId,
            String recentAnswererImageUrlLow,
            String recentAnswererName,
            String recentAnswererBio,
            String recentAnswerId,
            String recentAnswer,
            boolean recentAnswerImageAttached,
            String recentAnswerImageUrl,
            int answerCount,
            int recentAnswerLikeCount,
            int fontUsed){
        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.anonymous=anonymous;
        this.askerBio=askerBio;
        this.questionId=questionId;
        this.question=question;
        this.questionType=questionType;
        this.timeOfAsking=timeOfAsking;
        this.recentAnswererId=recentAnswererId;
        this.recentAnswererImageUrlLow=recentAnswererImageUrlLow;
        this.recentAnswererName=recentAnswererName;
        this.recentAnswererBio=recentAnswererBio;
        this.recentAnswerId=recentAnswerId;
        this.recentAnswer=recentAnswer;
        this.recentAnswerImageAttached=recentAnswerImageAttached;
        this.recentAnswerImageUrl=recentAnswerImageUrl;
        this.answerCount=answerCount;
        this.recentAnswerLikeCount=recentAnswerLikeCount;
        this.fontUsed=fontUsed;

    }

    public RootQuestionModel(
            String askerId,
            String askerName,
            String askerImageUrlLow,
            boolean anonymous,
            String askerBio,
            String questionId,
            String question,
            List<String> questionType,
            long timeOfAsking,
            long timeOfAnswering,
            String recentAnswererId,
            String recentAnswererImageUrlLow,
            String recentAnswererName,
            String recentAnswererBio,
            String recentAnswerId,
            String recentAnswer,
            boolean recentAnswerImageAttached,
            String recentAnswerImageUrl,
            int answerCount,
            int recentAnswerLikeCount,
            int fontUsed,
            int point){
        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.anonymous=anonymous;
        this.askerBio=askerBio;
        this.questionId=questionId;
        this.question=question;
        this.questionType=questionType;
        this.timeOfAsking=timeOfAsking;
        this.timeOfAnswering=timeOfAnswering;
        this.recentAnswererId=recentAnswererId;
        this.recentAnswererImageUrlLow=recentAnswererImageUrlLow;
        this.recentAnswererName=recentAnswererName;
        this.recentAnswererBio=recentAnswererBio;
        this.recentAnswerId=recentAnswerId;
        this.recentAnswer=recentAnswer;
        this.recentAnswerImageAttached=recentAnswerImageAttached;
        this.recentAnswerImageUrl=recentAnswerImageUrl;
        this.answerCount=answerCount;
        this.recentAnswerLikeCount=recentAnswerLikeCount;
        this.fontUsed=fontUsed;
        this.point=point;

    }

    public String getAskerId() {
        return askerId;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getQuestionType() {
        return questionType;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public long getTimeOfAnswering() {
        return timeOfAnswering;
    }

    public String getRecentAnswererId() {
        return recentAnswererId;
    }

    public String getRecentAnswererImageUrlLow() {
        return recentAnswererImageUrlLow;
    }

    public String getRecentAnswererName() {
        return recentAnswererName;
    }

    public String getRecentAnswererBio() {
        return recentAnswererBio;
    }

    public String getRecentAnswerId() {
        return recentAnswerId;
    }

    public String getRecentAnswer() {
        return recentAnswer;
    }

    public boolean isRecentAnswerImageAttached() {
        return recentAnswerImageAttached;
    }

    public String getRecentAnswerImageUrl() {
        return recentAnswerImageUrl;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getRecentAnswerLikeCount() {
        return recentAnswerLikeCount;
    }

    public int getFontUsed() {
        return fontUsed;
    }

    public void setRecentAnswerLikeCount(int recentAnswerLikeCount) {
        this.recentAnswerLikeCount = recentAnswerLikeCount;
    }

    public int getPoint() {
        return point;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isLikedByMe() {
        return likedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        this.likedByMe = likedByMe;
    }
}





