package com.example.heliosx;

import com.example.heliosx.common.Questionnaire;
import com.example.heliosx.common.QuestionnaireResponse;
import com.example.heliosx.common.RiskExplanation;
import com.example.heliosx.service.QuestionnaireService;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionnaireController {

    QuestionnaireService service;

    public QuestionnaireController(QuestionnaireService service) {
        this.service = service;
    }

    @GetMapping("/api/questionnaire/{id}")
    public Questionnaire getQuestionnaire(@PathVariable String id) {
        return service.getQuestionnaire(id);
    }

    @PostMapping("/api/questionnaire")
    public Questionnaire updateOrSaveQuestionnaire(@RequestBody Questionnaire questionnaire) {
        return service.updateOrSaveQuestionnaire(questionnaire);
    }

    @GetMapping("/api/questionnaire/{questionnaireId}/response/{userId}")
    public QuestionnaireResponse getQuestionnaireResponse(@PathVariable String questionnaireId, @PathVariable String userId) {
        return service.getQuestionnaireResponse(questionnaireId, userId);
    }

    @PostMapping("/api/questionnaire/response")
    public QuestionnaireResponse updateOrSaveQuestionnaireResponse(@RequestBody QuestionnaireResponse questionnaire) {
        return service.updateOrSaveQuestionnaireResponse(questionnaire);
    }

    @GetMapping("/api/questionnaire/{questionnaireId}/response/{userId}/risk-details")
    public RiskExplanation getHighRiskInfo(@PathVariable String questionnaireId, @PathVariable String userId) {
        return service.getRiskExplanation(questionnaireId, userId);
    }

}
