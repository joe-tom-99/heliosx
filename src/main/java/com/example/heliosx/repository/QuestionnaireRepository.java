package com.example.heliosx.repository;

import com.example.heliosx.common.Questionnaire;
import com.example.heliosx.common.QuestionnaireResponse;

public interface QuestionnaireRepository {

    Questionnaire getQuestionnaire(String questionnaireId);

    Questionnaire saveQuestionnaire(Questionnaire questionnaire);

    Questionnaire updateQuestionnaire(Questionnaire questionnaire);

    QuestionnaireResponse getResponse(String questionaireId, String userId);

    QuestionnaireResponse saveResponse(QuestionnaireResponse response);

    QuestionnaireResponse updateResponse(QuestionnaireResponse response);
}
