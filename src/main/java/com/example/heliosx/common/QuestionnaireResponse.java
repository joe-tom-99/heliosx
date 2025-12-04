package com.example.heliosx.common;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireResponse {
    private String id;

    private String userId;

    private String questionnaireId;

    private List<QuestionResponse> responses = new ArrayList<>();

    boolean highRisk = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<QuestionResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<QuestionResponse> responses) {
        this.responses = responses;
    }

    public boolean isHighRisk() {
        return highRisk;
    }

    public void setHighRisk(boolean highRisk) {
        this.highRisk = highRisk;
    }
}
