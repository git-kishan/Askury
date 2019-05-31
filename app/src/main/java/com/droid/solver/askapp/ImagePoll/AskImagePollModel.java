package com.droid.solver.askapp.ImagePoll;

public class AskImagePollModel {

    private String askerUid;
    private String askerName;
    private String askerImageUrl;
    private String question;
    private String image1Encoded;
    private String image2Encoded;
    private long timeOfPolling;
    private int image1LikeNo;
    private int image2LikeNo;
    public AskImagePollModel(){

    }
    public AskImagePollModel(String askerUid,
                             String askerName,
                             String askerImageUrl,
                             String question,
                             String image1Encoded,
                             String image2Encoded,
                             long timeOfPolling,
                             int image1LikeNo,
                             int image2LikeNo
                             ){
        this.askerUid=askerUid;
        this.askerName=askerName;
        this.askerImageUrl=askerImageUrl;
        this.question=question;
        this.image1Encoded=image1Encoded;
        this.image2Encoded=image2Encoded;
        this.timeOfPolling=timeOfPolling;
        this.image1LikeNo=image1LikeNo;
        this.image2LikeNo=image2LikeNo;

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

    public String getImage1Encoded() {
        return image1Encoded;
    }

    public String getImage2Encoded() {
        return image2Encoded;
    }


}

