package com.droid.solver.askapp.Community;

public class CommunityModel {



    private String communityName;
    private String communityImageUrl;
    private int memberInCommunity;

    public CommunityModel(String communityName, String communityImageUrl, int memberInCommunity) {
        this.communityName = communityName;
        this.communityImageUrl = communityImageUrl;
        this.memberInCommunity = memberInCommunity;
    }
    public CommunityModel(){}

    public String getCommunityName() {
        return communityName;
    }

    public String getCommunityImageUrl() {
        return communityImageUrl;
    }

    public int getMemberInCommunity() {
        return memberInCommunity;
    }

}
