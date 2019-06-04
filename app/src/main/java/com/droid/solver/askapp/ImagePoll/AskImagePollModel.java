package com.droid.solver.askapp.ImagePoll;

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
                             String imagePollId
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
}

