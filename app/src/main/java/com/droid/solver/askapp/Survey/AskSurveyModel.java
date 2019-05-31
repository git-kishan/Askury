package com.droid.solver.askapp.Survey;

public class AskSurveyModel {
    private String askerUid;
    private String askerName;
    private String askerImageUrl;
    private String question;
    private long timeOfSurvey;
    private int maximumTimeOfSurvey;
    private boolean option1,option2,option3,option4;
    private int option1Count,option2Count,option3Count,option4Count;
    private int languageSelectedIndex;


    public AskSurveyModel(){}
    public AskSurveyModel(String askerUid,
                          String askerName,
                          String askerImageUrl,
                          String question,
                          long timeOfSurvey,
                          int maximumTimeOfSurvey,
                          boolean option1,boolean option2,
                          boolean option3,boolean option4,
                          int option1Count,int option2Count,
                          int option3Count,int option4Count,
                          int languageSelectedIndex){

        this.askerUid=askerUid;
        this.askerName=askerName;
        this.askerImageUrl=askerImageUrl;
        this.question=question;
        this.timeOfSurvey=timeOfSurvey;
        this.maximumTimeOfSurvey=maximumTimeOfSurvey;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.option1Count=option1Count;
        this.option2Count=option2Count;
        this.option3Count=option3Count;
        this.option4Count=option4Count;
        this.languageSelectedIndex=languageSelectedIndex;
    }

    public String getAskerUid() {
        return askerUid;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getQuestion() {
        return question;
    }

    public int getMaximumTimeOfSurvey() {
        return maximumTimeOfSurvey;
    }

    public long getTimeOfSurvey() {
        return timeOfSurvey;
    }

    public String getAskerImageUrl() {
        return askerImageUrl;
    }

    public boolean isOption1() {
        return option1;
    }

    public boolean isOption2() {
        return option2;
    }

    public boolean isOption3() {
        return option3;
    }

    public boolean isOption4() {
        return option4;
    }

    public int getOption1Count() {
        return option1Count;
    }

    public int getOption2Count() {
        return option2Count;
    }

    public int getOption3Count() {
        return option3Count;
    }

    public int getOption4Count() {
        return option4Count;
    }

    public int getLanguageSelectedIndex() {
        return languageSelectedIndex;
    }
}
