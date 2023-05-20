package Users;

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
    public static String[] header;
        
    public String[] getMyVariable() {
        return header;
    }
    
    public static List<Question> loadQuestions(String fileName) throws IOException {
        // Create a new ArrayList to hold the questions
        List<Question> questions = new ArrayList<>();
        String fullPath = "Exams/" + fileName;
        int choice_num = 0;
        

        // Use try-with-resources to automatically close the reader when done
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                // Skip the first line as it is the header
                if (lineCount == 0) {
                    header = line.split(",");
                    choice_num = Integer.parseInt(header[2]);
                    lineCount++;
                    continue;
                }

                // Split the line into parts using commas as the delimiter
                String[] parts = line.split(",");

                // Extract the question text from the first part
                String questionText = parts[0].trim();

                // Extract the answer choices from the remaining parts
                List<String> choices = new ArrayList<>();
                for (int i = 1; i <= choice_num; i++) {
                    choices.add(parts[i].trim());
                }

                // Extract the correct answer from the last part
                String correctAnswer = parts[choice_num + 1].trim();

                // Create a new Question object with the extracted informationand add it to the list of questions
                Question question = new Question(questionText, choices, correctAnswer);
                questions.add(question);
            }
        }

        // Return a Pair object containing the header and the list of questions
        return questions;
    }
}