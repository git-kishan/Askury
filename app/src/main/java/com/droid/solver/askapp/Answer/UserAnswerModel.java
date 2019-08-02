package com.droid.solver.askapp.Answer;

import androidx.annotation.Keep;
import java.util.List;
@Keep
public class UserAnswerModel {

    public String askerId;
    public String askerName;
    public String askerImageUrl;
    public String askerBio;
    public String questionId;
    public String question;
    public List<String> questionType;
    public long timeOfAsking;
    public long timeOfAnswering;
    public String answerId;
    public String answererId;
    public String answererName;
    public boolean helpful;
    public String answer;
    public boolean imageAttached;
    public String imageAttachedUrl;
    public int fontUsed;
    public int answerLikeCount;
    public boolean anonymous;



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
            String answererId,
            String answererName,
            boolean helpful,
            String answer,
            boolean imageAttached,
            String imageAttachedUrl,
            int fontUsed, int answerLikeCount,
            boolean anonymous) {
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
        this.answererId=answererId;
        this.answererName=answererName;
        this.helpful = helpful;
        this.answer = answer;
        this.imageAttached = imageAttached;
        this.imageAttachedUrl = imageAttachedUrl;
        this.fontUsed = fontUsed;
        this.answerLikeCount = answerLikeCount;
        this.anonymous=anonymous;
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

    public String getAnswererId() {
        return answererId;
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

    public boolean isAnonymous() {
        return anonymous;
    }

    public String getAnswererName() {
        return answererName;
    }
}
