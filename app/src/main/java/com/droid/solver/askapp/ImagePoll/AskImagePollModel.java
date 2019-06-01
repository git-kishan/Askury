package com.droid.solver.askapp.ImagePoll;

public class AskImagePollModel {

    private String askerUid;
    private String askerName;
    private String askerImageUrl;
    private String question;
    private String image1Url;
    private String image2Url;
    private long timeOfPolling;
    private int image1LikeNo;
    private int image2LikeNo;
    private String imagePollId;
    public AskImagePollModel(){

    }
    public AskImagePollModel(String askerUid,
                             String askerName,
                             String askerImageUrl,
                             String question,
                             String image1Url,
                             String image2Url,
                             long timeOfPolling,
                             int image1LikeNo,
                             int image2LikeNo,
                             String imagePollId
                             ){
        this.askerUid=askerUid;
        this.askerName=askerName;
        this.askerImageUrl=askerImageUrl;
        this.image2Url=image2Url;
        this.question=question;
        this.image1Url =image1Url;
        this.image1Url=image1Url;
        this.timeOfPolling=timeOfPolling;
        this.image1LikeNo=image1LikeNo;
        this.image2LikeNo=image2LikeNo;
        this.imagePollId=imagePollId;

    }

    public String getAskerImageUrl() {
        return askerImageUrl;
    }

    public String getQuestion() {
        return question;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerUid() {
        return askerUid;
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

