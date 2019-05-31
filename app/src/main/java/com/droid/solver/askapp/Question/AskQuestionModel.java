package com.droid.solver.askapp.Question;

public class AskQuestionModel {
    private String askerName;
    private String askerUid;
    private long timeOfAsking;
    private String question;
    private String userImageUrl;
    private boolean imageAttached;
    private boolean anonymous;
    private String questionImageUrl;

    public AskQuestionModel(){

    }

    public AskQuestionModel(String askerName,
                            String askerUid,
                            long timeOfAsking,
                            String question,
                            String userImageUrl,
                            boolean imageAttached,
                            String questionImageUrl,
                            boolean anonymous){
        this.askerName=askerName;
        this.askerUid = askerUid;
        this.timeOfAsking=timeOfAsking;
        this.question=question;
        this.userImageUrl=userImageUrl;
        this.imageAttached=imageAttached;
        this.anonymous=anonymous;
        this.questionImageUrl=questionImageUrl;
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
}


