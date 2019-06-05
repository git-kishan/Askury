package com.droid.solver.askapp.Question;

public class Community{
    private String communityId;
    private String communityImageUrl;
    private String communityName;
    private int communityMemberCount;
    public Community(String communityId,String communityName,String communityImageUrl,int communityMemberCount){
        this.communityId=communityId;
        this.communityImageUrl=communityImageUrl;
        this.communityName=communityName;
        this.communityMemberCount=communityMemberCount;
    }

    public String getCommunityId() {
        return communityId;
    }

    public int getCommunityMemberCount() {
        return communityMemberCount;
    }

    public String getCommunityImageUrl() {
        return communityImageUrl;
    }

    public String getCommunityName() {
        return communityName;
    }

}
