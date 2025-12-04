package com.example.heliosx.repository;

import com.example.heliosx.common.Questionnaire;
import com.example.heliosx.common.QuestionnaireResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MemoryQuestionnaireRepository implements QuestionnaireRepository {

    Map<String, Questionnaire> questionnaires = new HashMap<>();
    Map<String, QuestionnaireResponse> questionnaireResponses = new HashMap<>();

    @Override
    public Questionnaire getQuestionnaire(String questionnaireId) {
        return questionnaires.get(questionnaireId);
    }

    @Override
    public Questionnaire saveQuestionnaire(Questionnaire questionnaire) {
        if (questionnaire.getId() == null) {
            questionnaire.setId(UUID.randomUUID().toString());
        }
        questionnaires.put(questionnaire.getId(), questionnaire);
        return questionnaire;
    }

    @Override
    public Questionnaire updateQuestionnaire(Questionnaire questionnaire) {
        questionnaires.replace(questionnaire.getId(), questionnaire);
        return questionnaire;
    }

    @Override
    public QuestionnaireResponse getResponse(String questionnaireId, String userId) {
        return questionnaireResponses.values().stream()
                .filter(response -> questionnaireId.equals(response.getQuestionnaireId()) &&
                        userId.equals(response.getUserId()))
                .findAny()
                .orElse(null);
    }

    @Override
    public QuestionnaireResponse saveResponse(QuestionnaireResponse response) {
        if (response.getId() == null) {
            response.setId(UUID.randomUUID().toString());
        }
        questionnaireResponses.put(response.getId(), response);
        return response;
    }

    @Override
    public QuestionnaireResponse updateResponse(QuestionnaireResponse response) {
        questionnaireResponses.replace(response.getId(), response);
        return response;
    }
}
