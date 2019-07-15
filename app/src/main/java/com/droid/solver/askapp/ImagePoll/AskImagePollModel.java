package com.droid.solver.askapp.ImagePoll;

import java.security.PublicKey;

public class AskImagePollModel {

    private String askerId;
    private String askerName;
    private String askerImageUrlLow;
    private String askerBio;
    private String question;
    private String image1Url;
    private String image2Url;
    private long timeOfPolling;
    private int image1LikeNo;
    private int image2LikeNo;
    private String imagePollId;
    private boolean reported;
    private boolean containVioloanceOrAdult;
    private long lastTimeNotified;
    private int optionSelectedByMe;


    public AskImagePollModel(){

    }

    public AskImagePollModel(String askerId,
                             String askerName,
                             String askerImageUrlLow,
                             String askerBio,
                             String question,
                             String image1Url,
                             String image2Url,
                             long timeOfPolling,
                             int image1LikeNo,
                             int image2LikeNo,
                             String imagePollId,
                             int optionSelectedByMe
                             ){
        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.askerBio=askerBio;
        this.image2Url=image2Url;
        this.question=question;
        this.image1Url =image1Url;
        this.image1Url=image1Url;
        this.timeOfPolling=timeOfPolling;
        this.image1LikeNo=image1LikeNo;
        this.image2LikeNo=image2LikeNo;
        this.imagePollId=imagePollId;
        this.optionSelectedByMe=optionSelectedByMe;

    }
    public AskImagePollModel(String askerId,
                             String askerName,
                             String askerImageUrlLow,
                             String askerBio,
                             String question,
                             String image1Url,
                             String image2Url,
                             long timeOfPolling,
                             int image1LikeNo,
                             int image2LikeNo,
                             String imagePollId,
                             boolean containVioloanceOrAdult,
                             boolean reported,
                             long lastTimeNotified
    ){
        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.askerBio=askerBio;
        this.image2Url=image2Url;
        this.question=question;
        this.image1Url =image1Url;
        this.image1Url=image1Url;
        this.timeOfPolling=timeOfPolling;
        this.image1LikeNo=image1LikeNo;
        this.image2LikeNo=image2LikeNo;
        this.imagePollId=imagePollId;
        this.containVioloanceOrAdult=containVioloanceOrAdult;
        this.reported=reported;
        this.lastTimeNotified=lastTimeNotified;

    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public String getQuestion() {
        return question;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerId() {
        return askerId;
    }

    public int getImage1LikeNo() {
        return image1LikeNo;
    }

    public int getImage2LikeNo() {
        return image2LikeNo;
    }

    public long getTimeOfPolling() {
        return timeOfPolling;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public String getImagePollId() {
        return imagePollId;
    }

    public boolean isContainVioloanceOrAdult() {
        return containVioloanceOrAdult;
    }

    public boolean isReported() {
        return reported;
    }

    public long getLastTimeNotified() {
        return lastTimeNotified;
    }

    public void setImage1LikeNo(int image1LikeNo) {
        this.image1LikeNo = image1LikeNo;
    }

    public void setImage2LikeNo(int image2LikeNo) {
        this.image2LikeNo = image2LikeNo;
    }

    public int getOptionSelectedByMe() {
        return optionSelectedByMe;
    }

    public void setOptionSelectedByMe(int optionSelectedByMe) {
        this.optionSelectedByMe = optionSelectedByMe;
    }
}

