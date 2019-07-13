package com.droid.solver.askapp.Notification;

public class AnswerModel {

    private String askerId;
    private String askerName;
    private String askerBio;
    private String questionId;
    private String question;
    private long timeOfAsking;
    private long timeOfAnswering;
    private String answererId;
    private String answererName;
    private String answererBio;
    private String answerId;
    private String answer;
    private boolean imageAttached;
    private String imageAttachedUrl;
    private int fontUsed;
    private boolean anonymous;
    private long notifiedTime;
    int answerLikeCount;
    private String type;
    private boolean isStoredLocally;

    public AnswerModel(){}

    public AnswerModel(String askerId, String askerName,
                       String askerBio, String questionId,
                       String question, long timeOfAsking,
                       long timeOfAnswering, String answererId,
                       String answererName, String answererBio,
                       String answerId, String answer,
                       boolean imageAttached, String imageAttachedUrl,
                       int fontUsed, boolean anonymous, long notifiedTime,
                       int answerLikeCount,
                       String type,boolean isStoredLocally) {
        this.askerId = askerId;
        this.askerName = askerName;
        this.askerBio = askerBio;
        this.questionId = questionId;
        this.question = question;
        this.timeOfAsking = timeOfAsking;
        this.timeOfAnswering = timeOfAnswering;
        this.answererId = answererId;
        this.answererName = answererName;
        this.answererBio = answererBio;
        this.answerId = answerId;
        this.answer = answer;
        this.imageAttached = imageAttached;
        this.imageAttachedUrl = imageAttachedUrl;
        this.fontUsed = fontUsed;
        this.anonymous = anonymous;
        this.notifiedTime = notifiedTime;
        this.answerLikeCount=answerLikeCount;
        this.type = type;
        this.isStoredLocally=isStoredLocally;
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

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public long getTimeOfAnswering() {
        return timeOfAnswering;
    }

    public String getAnswererId() {
        return answererId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public String getAnswererBio() {
        return answererBio;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isImageAttached() {
        return imageAttached;
    }

    public String getImageAttachedUrl() {
        return imageAttachedUrl;
    }

    public int getFontUsed() {
        return fontUsed;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public long getNotifiedTime() {
        return notifiedTime;
    }

    public int getAnswerLikeCount() {
        return answerLikeCount;
    }

    public String getType() {
        return type;
    }

    public boolean isStoredLocally() {
        return isStoredLocally;
    }

    public void setStoredLocally(boolean storedLocally) {
        isStoredLocally = storedLocally;
    }
}
