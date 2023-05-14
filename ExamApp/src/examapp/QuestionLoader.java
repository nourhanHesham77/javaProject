package ExamApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the questions from the selected exam file and stores them in an ArrayList.
 *
 * @throws IOException if there is an error reading the exam file
 */
public class QuestionLoader {
        
    public static List<Question> loadQuestions(String fileName) throws IOException {
        // Create a new ArrayList to hold the questions
        List<Question> questions = new ArrayList<>();
        
        // Use try-with-resources to automatically close the reader when done
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into parts using commas as the delimiter
                String[] parts = line.split(",");
                
                // Extract the question text from the first part
                String questionText = parts[0];
                
                // Extract the answer choices from the remaining parts
                List<String> choices = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    choices.add(parts[i]);
                }
                
                // Extract the correct answer from the last part
                String correctAnswer = parts[5];
                
                // Create a new Question object with the extracted information
                // so that i can deal with them seperatly
                Question question = new Question(questionText, choices, correctAnswer);
                
                // Add the question as a full data (questionText, choices, correctAnswer) to the list of questions
                // to be able to controll the whole question which makes it easier in shufling
                questions.add(question);
            }
        }
        
        // Return the list of questions
        return questions;
    }
}