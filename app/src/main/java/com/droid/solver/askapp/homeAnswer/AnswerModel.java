package com.droid.solver.askapp.homeAnswer;

import androidx.annotation.Keep;

import java.util.List;
@Keep
public class AnswerModel {

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

    public boolean isLikedByMe;

    public AnswerModel(){}
    public AnswerModel(String askerId,
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
                       boolean stringAttached,
                       String imageAttachedUrl,
                       int fontUsed,
                       boolean notifiedToAsker,
                       int answerLikeCount) {
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
    }


    public String getAskerId() {
        return askerId;
    }

    public void setAskerId(String askerId) {
        this.askerId = askerId;
    }

    public String getAskerName() {
        return askerName;
    }

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public String getAskerBio() {
        return askerBio;
    }

    public void setAskerBio(String askerBio) {
        this.askerBio = askerBio;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public void setAskerImageUrlLow(String askerImageUrlLow) {
        this.askerImageUrlLow = askerImageUrlLow;
    }

    public List<String> getQuestionType() {
        return questionType;
    }

    public void setQuestionType(List<String> questionType) {
        this.questionType = questionType;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public void setTimeOfAsking(long timeOfAsking) {
        this.timeOfAsking = timeOfAsking;
    }

    public long getTimeOfAnswering() {
        return timeOfAnswering;
    }

    public void setTimeOfAnswering(long timeOfAnswering) {
        this.timeOfAnswering = timeOfAnswering;
    }

    public String getAnswererId() {
        return answererId;
    }

    public void setAnswererId(String answererId) {
        this.answererId = answererId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    public String getAnswererImageUrl() {
        return answererImageUrl;
    }

    public void setAnswererImageUrl(String answererImageUrl) {
        this.answererImageUrl = answererImageUrl;
    }

    public String getAnswererBio() {
        return answererBio;
    }

    public void setAnswererBio(String answererBio) {
        this.answererBio = answererBio;
    }

    public boolean isHelpful() {
        return helpful;
    }

    public void setHelpful(boolean helpful) {
        this.helpful = helpful;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isImageAttached() {
        return imageAttached;
    }

    public void setImageAttached(boolean imageAttached) {
        this.imageAttached = imageAttached;
    }

    public String getImageAttachedUrl() {
        return imageAttachedUrl;
    }

    public void setImageAttachedUrl(String imageAttachedUrl) {
        this.imageAttachedUrl = imageAttachedUrl;
    }

    public boolean isStringAttached() {
        return stringAttached;
    }

    public void setStringAttached(boolean stringAttached) {
        this.stringAttached = stringAttached;
    }

    public int getFontUsed() {
        return fontUsed;
    }

    public void setFontUsed(int fontUsed) {
        this.fontUsed = fontUsed;
    }

    public boolean isNotifiedToAsker() {
        return notifiedToAsker;
    }

    public void setNotifiedToAsker(boolean notifiedToAsker) {
        this.notifiedToAsker = notifiedToAsker;
    }

    public int getAnswerLikeCount() {
        return answerLikeCount;
    }

    public void setAnswerLikeCount(int answerLikeCount) {
        this.answerLikeCount = answerLikeCount;
    }

    public boolean isLikedByMe() {
        return isLikedByMe;
    }

    public void setLikedByMe(boolean likedByMe) {
        isLikedByMe = likedByMe;
    }
}
@Keep
class QuestionModel{

    public String askerId;
    public String askerName;
    public String askerImageUrlLow;
    public String askerBio;
    public long timeOfAsking;
    public boolean anonymous;
    public String questionId;
    public String question;

    public String getAskerId() {
        return askerId;
    }

    public void setAskerId(String askerId) {
        this.askerId = askerId;
    }

    public String getAskerName() {
        return askerName;
    }

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public String getAskerImageUrlLow() {
        return askerImageUrlLow;
    }

    public void setAskerImageUrlLow(String askerImageUrlLow) {
        this.askerImageUrlLow = askerImageUrlLow;
    }


    public String getAskerBio() {
        return askerBio;
    }

    public void setAskerBio(String askerBio) {
        this.askerBio = askerBio;
    }

    public long getTimeOfAsking() {
        return timeOfAsking;
    }

    public void setTimeOfAsking(long timeOfAsking) {
        this.timeOfAsking = timeOfAsking;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public QuestionModel(){}
    public QuestionModel(String askerId,
                         String askerName,
                         String askerImageUrlLow,
                         String askerBio,
                         long timeOfAsking,
                         boolean anonymous,
                         String questionId,
                         String question) {
        this.askerId = askerId;
        this.askerName = askerName;
        this.askerImageUrlLow = askerImageUrlLow;
        this.askerBio = askerBio;
        this.timeOfAsking = timeOfAsking;
        this.anonymous = anonymous;
        this.questionId = questionId;
        this.question = question;
    }



}
