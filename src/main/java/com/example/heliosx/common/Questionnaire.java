package com.example.heliosx.common;

import java.util.*;

public class Questionnaire extends QuestionnaireElement{

    /**
     * internal name
     */
    private String name;

    private List<Section> sections = new ArrayList<>();

    public Map<String, Question> getQuestions() {
        Map<String, Question> questions = new HashMap<>();
        for (Section section : sections) {
            addSection(questions, section, new HashSet<>());
        }
        return questions;
    }

    private void addSection(Map<String, Question> questions, Section section, Set<Section> guard) {
        if (section != null && guard.add(section)) {
            for (Question question : section.getQuestions()) {
                questions.put(question.getId(), question);
                addSection(questions, question.getTrueSection(), guard);
                addSection(questions, question.getFalseSection(), guard);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
