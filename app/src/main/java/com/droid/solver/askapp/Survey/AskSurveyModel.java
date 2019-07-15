package com.droid.solver.askapp.Survey;

public class AskSurveyModel {
    private String askerId;
    private String askerName;
    private String askerImageUrlLow;
    private String askerBio;
    private String question;
    private long timeOfSurvey;
    private boolean option1,option2,option3,option4;
    private String option1Value,option2Value,option3Value,option4Value;
    private int option1Count,option2Count,option3Count,option4Count;
    private int languageSelectedIndex;
    private String surveyId;
    private long lastTimeNotified;
    private int optionSelectedByMe;


    public AskSurveyModel(){}
    public AskSurveyModel(String askerId,
                          String askerName,
                          String askerImageUrlLow,
                          String askerBio,
                          String question,
                          long timeOfSurvey,
                          boolean option1,
                          boolean option2,
                          boolean option3,
                          boolean option4,
                          String option1Value,
                          String option2Value,
                          String option3Value,
                          String option4Value,
                          int option1Count,
                          int option2Count,
                          int option3Count,
                          int option4Count,
                          int languageSelectedIndex,
                          String surveyId){

        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.askerBio=askerBio;
        this.question=question;
        this.timeOfSurvey=timeOfSurvey;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.option1Value=option1Value;
        this.option2Value=option2Value;
        this.option3Value=option3Value;
        this.option4Value=option4Value;
        this.option1Count=option1Count;
        this.option2Count=option2Count;
        this.option3Count=option3Count;
        this.option4Count=option4Count;
        this.languageSelectedIndex=languageSelectedIndex;
        this.surveyId=surveyId;
    }
    public AskSurveyModel(String askerId,
                          String askerName,
                          String askerImageUrlLow,
                          String askerBio,
                          String question,
                          long timeOfSurvey,
                          boolean option1,
                          boolean option2,
                          boolean option3,
                          boolean option4,
                          String option1Value,
                          String option2Value,
                          String option3Value,
                          String option4Value,
                          int option1Count,
                          int option2Count,
                          int option3Count,
                          int option4Count,
                          int languageSelectedIndex,
                          String surveyId,
                          long lastTimeNotified){

        this.askerId=askerId;
        this.askerName=askerName;
        this.askerImageUrlLow=askerImageUrlLow;
        this.askerBio=askerBio;
        this.question=question;
        this.timeOfSurvey=timeOfSurvey;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.option4=option4;
        this.option1Value=option1Value;
        this.option2Value=option2Value;
        this.option3Value=option3Value;
        this.option4Value=option4Value;
        this.option1Count=option1Count;
        this.option2Count=option2Count;
        this.option3Count=option3Count;
        this.option4Count=option4Count;
        this.languageSelectedIndex=languageSelectedIndex;
        this.surveyId=surveyId;
        this.lastTimeNotified=lastTimeNotified;
    }



    public void setAskerId(String askerId) {
        this.askerId = askerId;
    }

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public void setAskerImageUrlLow(String askerImageUrlLow) {
        this.askerImageUrlLow = askerImageUrlLow;
    }

    public void setAskerBio(String askerBio) {
        this.askerBio = askerBio;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setTimeOfSurvey(long timeOfSurvey) {
        this.timeOfSurvey = timeOfSurvey;
    }

    public void setOption1(boolean option1) {
        this.option1 = option1;
    }

    public void setOption2(boolean option2) {
        this.option2 = option2;
    }

    public void setOption3(boolean option3) {
        this.option3 = option3;
    }

    public void setOption4(boolean option4) {
        this.option4 = option4;
    }

    public void setOption1Value(String option1Value) {
        this.option1Value = option1Value;
    }

    public void setOption2Value(String option2Value) {
        this.option2Value = option2Value;
    }

    public void setOption3Value(String option3Value) {
        this.option3Value = option3Value;
    }

    public void setOption4Value(String option4Value) {
        this.option4Value = option4Value;
    }

    public void setOption1Count(int option1Count) {
        this.option1Count = option1Count;
    }

    public void setOption2Count(int option2Count) {
        this.option2Count = option2Count;
    }

    public void setOption3Count(int option3Count) {
        this.option3Count = option3Count;
    }

    public void setOption4Count(int option4Count) {
        this.option4Count = option4Count;
    }

    public void setLanguageSelectedIndex(int languageSelectedIndex) {
        this.languageSelectedIndex = languageSelectedIndex; }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getAskerId() {
        return askerId;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public String getQuestion() {
        return question;
    }

    public long getTimeOfSurvey() {
        return timeOfSurvey;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
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

    public String getOption1Value() {
        return option1Value;
    }

    public String getOption2Value() {
        return option2Value;
    }

    public String getOption3Value() {
        return option3Value;
    }

    public String getOption4Value() {
        return option4Value;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public long getLastTimeNotified() {
        return lastTimeNotified;
    }

    public int getOptionSelectedByMe() {
        return optionSelectedByMe;
    }

    public void setOptionSelectedByMe(int optionSelectedByMe) {
        this.optionSelectedByMe = optionSelectedByMe;
    }
}

