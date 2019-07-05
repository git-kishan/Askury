package com.droid.solver.askapp.Question;

import java.util.List;

public class RootQuestionModel {

    private String askerId;
    private String askerName;
    private String askerImageUrlLow;
    private boolean anonymous;
    private String askerBio;
    private String questionId;
    private String question;
    private List <String>questionType;
    private long timeOfAsking;
    private long timeOfAnswering;
    private String recentAnswererId;
    private String recentAnswererImageUrlLow;
    private String recentAnswererName;
    private String recentAnswererBio;
    private String recentAnswerId;
    private String recentAnswer;
    private boolean recentAnswerImageAttached;
    private String recentAnswerImageUrl;
    private int answerCount;
    private int recentAnswerLikeCount;
    private int fontUsed;
    private int point;

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

}



