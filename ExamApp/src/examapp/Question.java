package ExamApp;

import java.util.List;

/**
 * A class representing a multiple-choice question.
 */
public class Question {

    private final String text;
    private final List<String> choices;
    private final String correctAnswer;

    /**
     * Constructs a new Question object with the given text, choices, and correct answer.
     *
     * @param text the text of the question
     * @param choices a list of the answer choices for the question
     * @param correctAnswer the correct answer to the question
     */
    public Question(String text, List<String> choices, String correctAnswer) {
        this.text = text;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Returns the text of the question.
     *
     * @return the text of the question
     */
    public String getText() {
        return text;
    }

    /**
     * Returns a list of the answer choices for the question.
     *
     * @return a list of the answer choices for the question
     */
    public List<String> getChoices() {
        return choices;
    }

    /**
     * Returns the correct answer to the question.
     *
     * @return the correct answer to the question
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

}