package com.example.heliosx;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HeliosxApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    // Utility to load JSON test files
    private String loadJson(String filename) throws Exception {
        return Files.readString(Path.of("src/test/resources/" + filename));
    }

    @Test
    void testSaveQuestionnaire() throws Exception {
        String questionnaireJson = loadJson("questionnaire.json");

        mockMvc.perform(post("/api/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionnaireJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sections").isArray());
    }

    @Test
    void testGetQuestionnaire() throws Exception {
        // First save a questionnaire
        String questionnaireJson = loadJson("questionnaire.json");

        mockMvc.perform(post("/api/questionnaire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionnaireJson))
                .andExpect(status().isOk());

        // Now retrieve it
        mockMvc.perform(get("/api/questionnaire/q1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("q1"));
    }

    @Test
    void testSaveLowRiskResponse() throws Exception {
        String lowRiskJson = loadJson("responseLowRisk.json");

        mockMvc.perform(post("/api/questionnaire/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lowRiskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.questionnaireId").exists());
    }

    @Test
    void testGetLowRiskResponse() throws Exception {
        String lowRiskJson = loadJson("responseLowRisk.json");

        // Save response
        mockMvc.perform(post("/api/questionnaire/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lowRiskJson))
                .andExpect(status().isOk());

        // Extract IDs from test file
        var node = mapper.readTree(lowRiskJson);
        String qId = node.get("questionnaireId").asText();
        String userId = node.get("userId").asText();

        // Retrieve response
        mockMvc.perform(get("/api/questionnaire/" + qId + "/response/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    void testHighRiskResponseAndRiskDetails() throws Exception {
        String questionnaireJson = loadJson("questionnaire.json");

        mockMvc.perform(post("/api/questionnaire")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionnaireJson));

        String highRiskJson = loadJson("responseHighRisk.json");

        // Save high-risk response
        mockMvc.perform(post("/api/questionnaire/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(highRiskJson))
                .andExpect(status().isOk());

        var node = mapper.readTree(highRiskJson);
        String qId = node.get("questionnaireId").asText();
        String userId = node.get("userId").asText();

        // Retrieve risk explanation
        mockMvc.perform(get("/api/questionnaire/" + qId + "/response/" + userId + "/risk-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.highRisk").value(true));
    }

    @Test
    void testLowRiskResponseAndRiskDetails() throws Exception {
        String questionnaireJson = loadJson("questionnaire.json");

        mockMvc.perform(post("/api/questionnaire")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionnaireJson));

        String highRiskJson = loadJson("responseLowRisk.json");

        // Save high-risk response
        mockMvc.perform(post("/api/questionnaire/response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(highRiskJson))
                .andExpect(status().isOk());

        var node = mapper.readTree(highRiskJson);
        String qId = node.get("questionnaireId").asText();
        String userId = node.get("userId").asText();

        // Retrieve risk explanation
        mockMvc.perform(get("/api/questionnaire/" + qId + "/response/" + userId + "/risk-details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.highRisk").value(false));
    }

}
