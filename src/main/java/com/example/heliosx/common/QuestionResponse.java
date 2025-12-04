package com.example.heliosx.common;

public class QuestionResponse {
    private String questionId;

    /**
     * nullable, answer to boolean type question
     */
    private Boolean boolValue;

    /**
     * nullable, answer to text type question
     */
    private String strValue;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }
}
