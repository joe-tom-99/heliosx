package com.example.heliosx.common;

public class Question extends QuestionnaireElement {
    public enum QuestionType {
        BOOLEAN, TEXT
    }

    private QuestionType questionType;

    /**
     * label to display for true option (if BOOLEAN type) yes by default
     */
    private String trueLabel = "yes";

    /**
     * label to display for false option (if BOOLEAN type) no by default
     */
    private String falseLabel = "no";

    /**
     * optional subsection displayed when true is chosen (if BOOLEAN type)
     */
    private Section trueSection;

    /**
     * optional subsection displayed when false is selected
     */
    private Section falseSection;

    /**
     * marks if answering true to the question will result in non-provision
     */
    private boolean highRiskTrue = false;

    /**
     * marks if answering false to the question will result in non-provision
     */
    private boolean highRiskFalse = false;

    public boolean isAnswerHighRisk(Boolean answer) {
        if (!QuestionType.BOOLEAN.equals(questionType) || answer == null) return false;
        if (answer) return highRiskTrue;
        return highRiskFalse;
    }

    public String getAnswerLabel(Boolean answer) {
        if (answer == null) return "";
        return answer ? trueLabel : falseLabel;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getTrueLabel() {
        return trueLabel;
    }

    public void setTrueLabel(String trueLabel) {
        this.trueLabel = trueLabel;
    }

    public String getFalseLabel() {
        return falseLabel;
    }

    public void setFalseLabel(String falseLabel) {
        this.falseLabel = falseLabel;
    }

    public Section getTrueSection() {
        return trueSection;
    }

    public void setTrueSection(Section trueSection) {
        this.trueSection = trueSection;
    }

    public Section getFalseSection() {
        return falseSection;
    }

    public void setFalseSection(Section falseSection) {
        this.falseSection = falseSection;
    }

    public boolean isHighRiskTrue() {
        return highRiskTrue;
    }

    public void setHighRiskTrue(boolean highRiskTrue) {
        this.highRiskTrue = highRiskTrue;
    }

    public boolean isHighRiskFalse() {
        return highRiskFalse;
    }

    public void setHighRiskFalse(boolean highRiskFalse) {
        this.highRiskFalse = highRiskFalse;
    }
}
