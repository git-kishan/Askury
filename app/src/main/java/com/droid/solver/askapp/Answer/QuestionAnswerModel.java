package com.droid.solver.askapp.Answer;

import androidx.annotation.Keep;
import java.util.List;
@Keep
public class QuestionAnswerModel {
    public String askerId;
    public String askerName;
    public String askerBio;
    public String askerImageUrlLow;
    public List<String> questionType;
    public String questionId;
    public String question;
    public long timeOfAsking;
    public long timeOfAnswering;
    public String answererId;
    public String answererName;
    public String answererImageUrl;
    public String answererBio;
    public boolean helpful;
    public String answerId;
    public String answer;
    public boolean imageAttached;
    public String imageAttachedUrl;
    public boolean stringAttached;
    public int fontUsed;
    public boolean notifiedToAsker;
    public int answerLikeCount;
    public boolean anonymous;


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
