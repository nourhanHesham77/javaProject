package Admin;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class ValidationSettingExam {
    public static boolean isExamNameValid(String examName) {
        if (examName == null || examName.isEmpty()) {
            return false;
        }
        for (char c : examName.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMarkValid(String mark) {
        if (mark == null || mark.isEmpty()) {
            return false;
        }
        try {
            Integer.valueOf(mark);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isExamTimeValid(String examTime) {
        if (examTime == null || examTime.isEmpty()) {
            return false;
        }
        try {
            Integer.valueOf(examTime);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean validatePage(VBox pageLayout, int noChoices) {
        TextField questionField = (TextField) pageLayout.getChildren().get(2);
        String questionText = questionField.getText();
        if (questionText.isEmpty()) {
            return false;
        }
        Set<String> choices = new HashSet<>();
        for (int j = 4; j < 4 + noChoices; j++) {
            TextField choiceField = (TextField) pageLayout.getChildren().get(j);
            String choiceText = choiceField.getText();
            if (choiceText.isEmpty()|| choices.contains(choiceText)) {
                return false;
            }
            choices.add(choiceText);
        }
        TextField answerField = (TextField) pageLayout.getChildren().get(pageLayout.getChildren().size() - 2);
        String answerText = answerField.getText();
        return !(answerText.isEmpty() || !choices.contains(answerText));
    }
}
