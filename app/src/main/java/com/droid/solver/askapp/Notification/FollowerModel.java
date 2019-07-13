package com.droid.solver.askapp.Notification;

public class FollowerModel {

    private String followerId;
    private String followerName;
    private String followerBio;
    private String followerImageUrl;
    private String selfId;
    private boolean isStoredLocally;
    private long notifiedTime;
    private String type;

    public FollowerModel(){}

    public FollowerModel(String followerId, String followerName,
                         String followerBio, String followerImageUrl,
                         String selfId, boolean isStoredLocally,
                         long notifiedTime, String type) {
        this.followerId = followerId;
        this.followerName = followerName;
        this.followerBio = followerBio;
        this.followerImageUrl = followerImageUrl;
        this.selfId = selfId;
        this.isStoredLocally = isStoredLocally;
        this.notifiedTime = notifiedTime;
        this.type = type;
    }



    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

    public String getFollowerBio() {
        return followerBio;
    }

    public void setFollowerBio(String followerBio) {
        this.followerBio = followerBio;
    }

    public String getFollowerImageUrl() {
        return followerImageUrl;
    }

    public void setFollowerImageUrl(String followerImageUrl) {
        this.followerImageUrl = followerImageUrl;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public boolean isStoredLocally() {
        return isStoredLocally;
    }

    public void setStoredLocally(boolean storedLocally) {
        isStoredLocally = storedLocally;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }

    public void setNotifiedTime(long notifiedTime) {
        this.notifiedTime = notifiedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
