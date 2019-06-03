package com.droid.solver.askapp.Answer;

import java.util.List;

public class UserAnswerModel {

    private String askerId;
    private String askerName;
    private String askerImageUrl;
    private String askerBio;
    private String questionId;
    private String question;
    private List<String> questionType;
    private long timeOfAsking;
    private long timeOfAnswering;
    private String answerId;
    private boolean helpful;
    private String answer;
    private boolean imageAttached;
    private String imageAttachedUrl;
    private int fontUsed;
    private int answerLikeCount;

    public UserAnswerModel(
            String askerId,
            String askerName,
            String askerImageUrl,
            String askerBio,
            String questionId,
            String question,
            List<String> questionType,
            long timeOfAsking,
            long timeOfAnswering,
            String answerId,
            boolean helpful,
            String answer,
            boolean imageAttached,
            String imageAttachedUrl,
            int fontUsed, int answerLikeCount) {
        this.askerId = askerId;
        this.askerName = askerName;
        this.askerImageUrl = askerImageUrl;
        this.askerBio = askerBio;
        this.questionId = questionId;
        this.question = question;
        this.questionType = questionType;
        this.timeOfAsking = timeOfAsking;
        this.timeOfAnswering = timeOfAnswering;
        this.answerId = answerId;
        this.helpful = helpful;
        this.answer = answer;
        this.imageAttached = imageAttached;
        this.imageAttachedUrl = imageAttachedUrl;
        this.fontUsed = fontUsed;
        this.answerLikeCount = answerLikeCount;
    }


    UserAnswerModel(){}


    public String getAskerId() {
        return askerId;
    }

    public String getAskerName() {
        return askerName;
    }

    public String getAskerImageUrl() {
        return askerImageUrl;
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

    public List<String> getQuestionType() {
        return questionType;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public long getTimeOfAnswering() {
        return timeOfAnswering;
    }

    public String getAnswerId() {
        return answerId;
    }

    public boolean isHelpful() {
        return helpful;
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

    public int getAnswerLikeCount() {
        return answerLikeCount;
    }




}
