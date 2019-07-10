package com.droid.solver.askapp.Notification;

public class ImagePollModel {

    private String imagePollId;
    private String question;
    private String askerId;
    private String askerName;
    private String askerBio;
    private String askerImageUrlLow;
    private String image1Url;
    private String image2Url;
    private int image1LikeNo;
    private int image2LikeNo;
    private boolean containVioloanceOrAdult;
    private boolean reported;
    private String type;
    private long notifiedTime;
    private boolean isStoredLocally;

    public ImagePollModel(){}

    public ImagePollModel(String imagePollId,
                          String question,
                          String askerId,
                          String askerName,
                          String askerBio,
                          String askerImageUrlLow,
                          String image1Url,
                          String image2Url,
                          int image1LikeNo,
                          int image2LikeNo,
                          boolean containVioloanceOrAdult,
                          boolean reported,
                          String type,
                          long notifiedTime,
                          boolean isStoredLocally) {
        this.imagePollId = imagePollId;
        this.question = question;
        this.askerId = askerId;
        this.askerName = askerName;
        this.askerBio = askerBio;
        this.askerImageUrlLow = askerImageUrlLow;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.image1LikeNo = image1LikeNo;
        this.image2LikeNo = image2LikeNo;
        this.containVioloanceOrAdult = containVioloanceOrAdult;
        this.reported = reported;
        this.type = type;
        this.notifiedTime = notifiedTime;
        this.isStoredLocally = isStoredLocally;
    }

    public String getType() {
        return type;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }

    public String getAskerId() {
        return askerId;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getQuestion() {
        return question;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public int getImage2LikeNo() {
        return image2LikeNo;
    }

    public int getImage1LikeNo() {
        return image1LikeNo;
    }

    public String getImagePollId() {
        return imagePollId;
    }

    public boolean isReported() {
        return reported;
    }

    public boolean isContainVioloanceOrAdult() {
        return containVioloanceOrAdult;
    }

    public boolean isStoredLocally() {
        return isStoredLocally;
    }

    public void setStoredLocally(boolean storedLocally) {
        isStoredLocally = storedLocally;
    }
}
