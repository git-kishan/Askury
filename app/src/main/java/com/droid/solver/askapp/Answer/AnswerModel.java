package com.droid.solver.askapp.Answer;

public class AnswerModel {
    private String questionId;
    private String question;
    private String [] questionType ;
    private long timeOfAsking;
    private long timeOfAnswering;
    private String answererId;
    private String answererName;
    private String answererImageUrl;
    private boolean imageAttached;
    private String answer;
    private String answerImageUrl;
    private int fontUsed;
    boolean helpful;
    private int likeCount;

    AnswerModel(){}
    AnswerModel(String questionId,String question,String[] questionType,long timeOfAsking,long timeOfAnswering,String answererId,String answererName,
                String answererImageUrl,boolean imageAttached,String answer,String answerImageUrl,int fontUsed,boolean helpful,int likeCount){
        this.questionId=questionId;
        this.question=question;
        this.questionType=questionType;
        this.timeOfAsking=timeOfAsking;
        this.timeOfAnswering=timeOfAnswering;
        this.answererId=answererId;
        this.answererName=answererName;
        this.answererImageUrl=answererImageUrl;
        this.imageAttached=imageAttached;
        this.answer=answer;
        this.answerImageUrl=answerImageUrl;
        this.fontUsed=fontUsed;
        this.helpful=helpful;
        this.likeCount=likeCount;
    }

    public int getLikeCount() {
        return likeCount;
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

    public int getFontUsed() {
        return fontUsed;
    }

    public String getAnswer() {
        return answer;
    }

    public String getAnswererId() {
        return answererId;
    }

    public String getAnswererImageUrl() {
        return answererImageUrl;
    }

    public String getAnswererName() {
        return answererName;
    }

    public String getAnswerImageUrl() {
        return answerImageUrl;
    }

    public String[] getQuestionType() {
        return questionType;
    }

    public boolean isImageAttached() {
        return imageAttached;
    }

    public boolean isHelpful() {
        return helpful;
    }
}

