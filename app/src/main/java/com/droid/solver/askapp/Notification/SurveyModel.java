package com.droid.solver.askapp.Notification;

public class SurveyModel {

    private String surveyId;
    private String askerId;
    private String askerName;
    private String askerBio;
    private String askerImageUrlLow;
    private String question;
    private long timeOfSurvey;
    private String option1Value;
    private String option2Value;
    private String option3Value;
    private String option4Value;
    private int option1Count;
    private int option2Count;
    private int option3Count;
    private int option4Count;
    private boolean option1;
    private boolean option2;
    private boolean option3;
    private boolean option4;
    private String type;
    private long notifiedTime;
    private boolean isStoredLocally;

    public SurveyModel(){}

    public SurveyModel(String askerBio,
                       String askerId,
                       String askerImageUrlLow,
                       String askerName,
                       String question,
                       String surveyId,
                       long timeOfSurvey,
                       String option1Value,
                       String option2Value,
                       String option3Value,
                       String option4Value,
                       int option1Count,
                       int option2Count,
                       int option3Count,
                       int option4Count,
                       boolean option1,
                       boolean option2,
                       boolean option3,
                       boolean option4,
                       String type,
                       long notifiedTime,
                       boolean isStoredLocally) {
        this.askerBio = askerBio;
        this.askerId = askerId;
        this.askerImageUrlLow = askerImageUrlLow;
        this.askerName = askerName;
        this.question = question;
        this.surveyId = surveyId;
        this.timeOfSurvey = timeOfSurvey;
        this.option1Value = option1Value;
        this.option2Value = option2Value;
        this.option3Value = option3Value;
        this.option4Value = option4Value;
        this.option1Count = option1Count;
        this.option2Count = option2Count;
        this.option3Count = option3Count;
        this.option4Count = option4Count;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.type = type;
        this.notifiedTime = notifiedTime;
        this.isStoredLocally=isStoredLocally;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }

    public String getAskerId() {
        return askerId;
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

    public long getTimeOfSurvey() {
        return timeOfSurvey;
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

    public String getType() {
        return type;
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

    public String getQuestion() {
        return question;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public boolean isStoredLocally() {
        return isStoredLocally;
    }

    public void setStoredLocally(boolean storedLocally) {
        isStoredLocally = storedLocally;
    }
}
