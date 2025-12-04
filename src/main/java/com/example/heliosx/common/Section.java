package com.example.heliosx.common;

import java.util.ArrayList;
import java.util.List;

public class Section extends QuestionnaireElement{
    private List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
