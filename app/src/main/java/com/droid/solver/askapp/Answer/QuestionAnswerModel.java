package com.droid.solver.askapp.Answer;

import java.util.List;

public class QuestionAnswerModel {
    private String askerId;
    private String askerName;
    private String askerBio;
    private String askerImageUrlLow;
    private List<String> questionType;
    private String questionId;
    private String question;
    private long timeOfAsking;
    private long timeOfAnswering;
    private String answererId;
    private String answererName;
    private String answererImageUrl;
    private String answererBio;
    private boolean helpful;
    private String answerId;
    private String answer;
    private boolean imageAttached;
    private String imageAttachedUrl;
    private boolean stringAttached;
    private int fontUsed;
    private boolean notifiedToAsker;
    private int answerLikeCount;
    private boolean anonymous;


    QuestionAnswerModel(){}

    public QuestionAnswerModel(
            String askerId,
            String askerName,
            String askerBio,
            String askerImageUrlLow,
            List<String> questionType,
            String questionId,
            String question,
            long timeOfAsking,
            long timeOfAnswering,
            String answererId,
            String answererName,
            String answererImageUrl,
            String answererBio,
            boolean helpful,
            String answerId,
            String answer,
            boolean imageAttached,
            String imageAttachedUrl,
            boolean stringAttached,
            int fontUsed,
            boolean notifiedToAsker,
            int answerLikeCount,
            boolean anonymous) {
        this.askerId = askerId;
        this.askerName = askerName;
        this.askerBio = askerBio;
        this.askerImageUrlLow = askerImageUrlLow;
        this.questionType = questionType;
        this.questionId = questionId;
        this.question = question;
        this.timeOfAsking = timeOfAsking;
        this.timeOfAnswering = timeOfAnswering;
        this.answererId = answererId;
        this.answererName = answererName;
        this.answererImageUrl = answererImageUrl;
        this.answererBio = answererBio;
        this.helpful = helpful;
        this.answerId = answerId;
        this.answer = answer;
        this.imageAttached = imageAttached;
        this.imageAttachedUrl=imageAttachedUrl;
        this.stringAttached = stringAttached;
        this.fontUsed = fontUsed;
        this.notifiedToAsker = notifiedToAsker;
        this.answerLikeCount = answerLikeCount;
        this.anonymous=anonymous;
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

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public List<String> getQuestionType() {
        return questionType;
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

    public String getAnswererImageUrl() {
        return answererImageUrl;
    }

    public String getAnswererBio() {
        return answererBio;
    }

    public boolean isHelpful() {
        return helpful;
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


    public boolean isStringAttached() {
        return stringAttached;
    }

    public int getFontUsed() {
        return fontUsed;
    }

    public boolean isNotifiedToAsked() {
        return notifiedToAsker;
    }

    public int getAnswerLikeCount() {
        return answerLikeCount;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public boolean isNotifiedToAsker() {
        return notifiedToAsker;
    }

}
