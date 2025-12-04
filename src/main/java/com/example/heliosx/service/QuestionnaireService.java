package com.example.heliosx.service;

import com.example.heliosx.common.*;
import com.example.heliosx.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionnaireService {

    QuestionnaireRepository repository;

    public QuestionnaireService(QuestionnaireRepository repository) {
        this.repository = repository;
    }

    public Questionnaire getQuestionnaire(String id) {
        return repository.getQuestionnaire(id);
    }

    public Questionnaire updateOrSaveQuestionnaire(Questionnaire questionnaire) {
        if (getQuestionnaire(questionnaire.getId()) == null) {
            questionnaire = repository.saveQuestionnaire(questionnaire);
        }
        else {
            questionnaire = repository.updateQuestionnaire(questionnaire);
        }
        return questionnaire;
    }

    public QuestionnaireResponse getQuestionnaireResponse(String questionnaireId, String userId) {
        QuestionnaireResponse response = repository.getResponse(questionnaireId, userId);
        if (response != null) return response;

        return createEmptyResponse(questionnaireId, userId);
    }

    public QuestionnaireResponse updateOrSaveQuestionnaireResponse(QuestionnaireResponse response) {
        if (repository.getResponse(response.getQuestionnaireId(), response.getUserId()) == null) {
            response = repository.saveResponse(response);
        }
        else {
            response = repository.updateResponse(response);
        }
        return response;
    }

    public RiskExplanation getRiskExplanation(String questionnaireId, String userId) {
        RiskExplanation riskExplanation = new RiskExplanation();
        Questionnaire questionnaire = getQuestionnaire(questionnaireId);
        QuestionnaireResponse questionnaireResponse = repository.getResponse(questionnaireId, userId);
        Map<Question, QuestionResponse> highRiskAnswers = getHighRiskAnswers(questionnaire, questionnaireResponse);

        if (highRiskAnswers.isEmpty()) {
            questionnaireResponse.setHighRisk(false);
            updateOrSaveQuestionnaireResponse(questionnaireResponse);
            riskExplanation.setHighRisk(false);
            riskExplanation.setDetails("You are likely to be able to receive the medicine.");
            return riskExplanation;
        }

        questionnaireResponse.setHighRisk(true);
        updateOrSaveQuestionnaireResponse(questionnaireResponse);
        StringBuilder fullResponse = new StringBuilder("You are unlikely to be able to receive the medicine for the following reasons:\n");
        for (Map.Entry<Question, QuestionResponse> answer : highRiskAnswers.entrySet()) {
            Question question = answer.getKey();
            String response = String.format("You answered %s to the question '%s'\n",
                    question.getAnswerLabel(answer.getValue().getBoolValue()), question.getText());
            fullResponse.append(response);
        }
        riskExplanation.setHighRisk(true);
        riskExplanation.setDetails(fullResponse.toString());
        return riskExplanation;
    }

    public Map<Question, QuestionResponse> getHighRiskAnswers(Questionnaire questionnaire, QuestionnaireResponse questionnaireResponse) {

        Map<Question, QuestionResponse> highRiskAnswers = new HashMap<>();
        Map<String, Question> questions = questionnaire.getQuestions();
        for (QuestionResponse response : questionnaireResponse.getResponses()) {
            Question question = questions.get(response.getQuestionId());
            if (question.isAnswerHighRisk(response.getBoolValue())) {
                highRiskAnswers.put(question, response);
            }
        }
        return highRiskAnswers;
    }

    private QuestionnaireResponse createEmptyResponse(String questionnaireId, String userId) {
        Questionnaire questionnaire = getQuestionnaire(questionnaireId);
        QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
        questionnaireResponse.setQuestionnaireId(questionnaireId);
        questionnaireResponse.setUserId(userId);
        for (Question question : questionnaire.getQuestions().values()) {
            QuestionResponse response = new QuestionResponse();
            response.setQuestionId(question.getId());
            questionnaireResponse.getResponses().add(response);
        }
        questionnaireResponse = repository.saveResponse(questionnaireResponse);
        return questionnaireResponse;
    }
}
