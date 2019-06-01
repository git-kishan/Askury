package com.droid.solver.askapp.Question;

public class AskQuestionModel {
    private String askerName;
    private String askerUid;
    private String questionId;
    private long timeOfAsking;
    private String question;
    private String userImageUrl;
    private boolean imageAttached;
    private boolean anonymous;
    private String questionImageUrl;
    private int likeCount;
    private int answerCount;
    private int  satisfiedByUserAnswerCount;
    private String recentUserAnswer;
    private String recentUserUid;
    private String recentUserImageUrl;
    private String recentUserName;
    private String recentUserAbout;
    private String recentUserAnswerImageUrl;
    private boolean recentUserAnswerImageAttached;

    ////user who give answer to question is latest user
    public AskQuestionModel(){

    }

    public AskQuestionModel(String askerName,
                            String askerUid,
                            String questionId,
                            long timeOfAsking,
                            String question,
                            String userImageUrl,
                            boolean imageAttached,
                            String questionImageUrl,
                            boolean anonymous,
                            int likeCount,
                            int answerCount,
                            int satisfiedByUserAnswerCount,
                            String recentUserAnswer,
                            String recentUserUid,
                            String recentUserImageUrl,
                            String recentUserName,
                            String recentUserAbout,
                            String recentUserAnswerImageUrl,
                            boolean recentUserAnswerImageAttached){
        this.askerName=askerName;
        this.askerUid = askerUid;
        this.questionId=questionId;
        this.timeOfAsking=timeOfAsking;
        this.question=question;
        this.userImageUrl=userImageUrl;
        this.imageAttached=imageAttached;
        this.anonymous=anonymous;
        this.questionImageUrl=questionImageUrl;
        this.answerCount=answerCount;
        this.likeCount=likeCount;
        this.satisfiedByUserAnswerCount=satisfiedByUserAnswerCount;
        this.recentUserAnswer=recentUserAnswer;
        this.recentUserUid=recentUserUid;
        this.recentUserImageUrl=recentUserImageUrl;
        this.recentUserName=recentUserName;
        this.recentUserAbout=recentUserAbout;
        this.recentUserAnswerImageUrl=recentUserAnswerImageUrl;
        this.recentUserAnswerImageAttached=recentUserAnswerImageAttached;

    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public String getQuestion() {
        return question;
    }



    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerUid() {
        return askerUid;
    }

    public boolean isImageAttached() {
        return imageAttached;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getQuestionImageUrl() {
        return questionImageUrl;
    }

    public String getQuestionId() {
        return questionId;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getSatisfiedByUserAnswerCount() {
        return satisfiedByUserAnswerCount;
    }

    public String getRecentUserAbout() {
        return recentUserAbout;
    }

    public String getRecentUserAnswer() {
        return recentUserAnswer;
    }

    public String getRecentUserAnswerImageUrl() {
        return recentUserAnswerImageUrl;
    }

    public String getRecentUserImageUrl() {
        return recentUserImageUrl;
    }

    public String getRecentUserName() {
        return recentUserName;
    }

    public String getRecentUserUid() {
        return recentUserUid;
    }

    public boolean isRecentUserAnswerImageAttached() {
        return recentUserAnswerImageAttached;
    }

}


