package com.droid.solver.askapp.Question;

public class AskQuestionModel {
    private String askerName;
    private String askerUid;
    private long timeOfAsking;
    private String question;
    private String userImageUrl;
    private boolean isImageAttached;
    private boolean isAnonymous;
    private String imageEncodedString;
    public AskQuestionModel(){

    }

    public AskQuestionModel(String askerName,
                            String askerUid,
                            long timeOfAsking,
                            String question,
                            String userImageUrl,
                            boolean isImageAttached,
                            String imageEncodedString,
                            boolean isAnonymous){
        this.askerName=askerName;
        this.askerUid = askerUid;
        this.timeOfAsking=timeOfAsking;
        this.question=question;
        this.userImageUrl=userImageUrl;
        this.isImageAttached=isImageAttached;
        this.isAnonymous=isAnonymous;
        this.imageEncodedString=imageEncodedString;
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
        return isImageAttached;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public String getImageEncodedString() {
        return imageEncodedString;
    }
}


