package Start;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;

public class App extends Application {

    private static final String EXAMS_DIRECTORY = "E:/Project/";
    private static final String UPDATED_EXAMS_FILE_PATH = "E:/Project/updated_exams.csv";

    private ObservableList<ExamQuestion> questionList;
    private TableView<ExamQuestion> table;
    private ComboBox<String> examComboBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Exam Questions");

        // Initialize the question list
        questionList = FXCollections.observableArrayList();

        // Create exam selection window
        createExamSelectionWindow();

        // Create table columns
        TableColumn<ExamQuestion, Integer> questionNumberColumn = new TableColumn<>("Question Number");
        questionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));

        TableColumn<ExamQuestion, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        TableColumn<ExamQuestion, String> optionAColumn = new TableColumn<>("Option A");
        optionAColumn.setCellValueFactory(new PropertyValueFactory<>("optionA"));

        TableColumn<ExamQuestion, String> optionBColumn = new TableColumn<>("Option B");
        optionBColumn.setCellValueFactory(new PropertyValueFactory<>("optionB"));

        TableColumn<ExamQuestion, String> optionCColumn = new TableColumn<>("Option C");
        optionCColumn.setCellValueFactory(new PropertyValueFactory<>("optionC"));

        TableColumn<ExamQuestion, String> optionDColumn = new TableColumn<>("Option D");
        optionDColumn.setCellValueFactory(new PropertyValueFactory<>("optionD"));

        TableColumn<ExamQuestion, String> correctAnswerColumn = new TableColumn<>("Correct Answer");
        correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        // Create the table view
        table = new TableView<>();
        table.setItems(questionList);

        // Create the list of columns
        List<TableColumn<ExamQuestion, ?>> columns = new ArrayList<>();
        columns.add(questionNumberColumn);
        columns.add(questionColumn);
        columns.add(optionAColumn);
        columns.add(optionBColumn);
        columns.add(optionCColumn);
        columns.add(optionDColumn);
        columns.add(correctAnswerColumn);

        // Set the columns on the table view
        table.getColumns().addAll(columns);

        // Create buttons
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEditButton());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> handleDeleteButton());

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> handleAddButton());

        // Create a horizontal layout for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(editButton, deleteButton, addButton);

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setTop(examComboBox);
        root.setCenter(table);
        root.setBottom(buttonBox);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up application close event
        primaryStage.setOnCloseRequest(e -> {
            // Save exams to CSV file before exiting the application
            saveExamsToFile();
            Platform.exit();
        });
    }

    private void createExamSelectionWindow() {
        File examsDirectory = new File(EXAMS_DIRECTORY);
        File[] examFiles = examsDirectory.listFiles((dir, name) -> name.endsWith(".csv"));

        examComboBox = new ComboBox<>();
        examComboBox.getItems().addAll(getExamNames(examFiles));
        examComboBox.setPromptText("Select an exam");

        examComboBox.setOnAction(e -> {
            String selectedExam = examComboBox.getValue();
            File selectedExamFile = new File(EXAMS_DIRECTORY + selectedExam + ".csv");
            if (selectedExamFile.exists()) {
                questionList.clear();
                loadExamsFromFile(selectedExamFile);
            }
        });
    }

    private String[] getExamNames(File[] examFiles) {
        String[] examNames = new String[examFiles.length];
        for (int i = 0; i < examFiles.length; i++) {
            examNames[i] = examFiles[i].getName().replace(".csv", "");
        }
        return examNames;
    }

    private void loadExamsFromFile(File selectedExamFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(selectedExamFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    int questionNumber = Integer.parseInt(data[0]);
                    String question = data[1];
                    String optionA = data[2];
                    String optionB = data[3];
                    String optionC = data[4];
                    String optionD = data[5];
                    String correctAnswer = data[6];

                    ExamQuestion examQuestion = new ExamQuestion(questionNumber, question, optionA, optionB,
                            optionC, optionD, correctAnswer);
                    questionList.add(examQuestion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExamsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(UPDATED_EXAMS_FILE_PATH))) {
            for (ExamQuestion examQuestion : questionList) {
                String csvString = examQuestion.toCSVString();
                bw.write(csvString);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditButton() {
        ExamQuestion selectedQuestion = table.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            // Create a dialog for editing the question
            TextInputDialog questionDialog = new TextInputDialog(selectedQuestion.getQuestion());
            questionDialog.setTitle("Edit Question");
            questionDialog.setHeaderText("Edit the question");

            // Create dialogs for editing the answer options
            TextInputDialog optionADialog = new TextInputDialog(selectedQuestion.getOptionA());
            optionADialog.setTitle("Edit Option A");
            optionADialog.setHeaderText("Edit Option A");

            TextInputDialog optionBDialog = new TextInputDialog(selectedQuestion.getOptionB());
            optionBDialog.setTitle("Edit Option B");
            optionBDialog.setHeaderText("Edit Option B");

            TextInputDialog optionCDialog = new TextInputDialog(selectedQuestion.getOptionC());
            optionCDialog.setTitle("Edit Option C");
            optionCDialog.setHeaderText("Edit Option C");

            TextInputDialog optionDDialog = new TextInputDialog(selectedQuestion.getOptionD());
            optionDDialog.setTitle("Edit Option D");
            optionDDialog.setHeaderText("Edit Option D");

            TextInputDialog correctAnswerDialog = new TextInputDialog(selectedQuestion.getCorrectAnswer());
            correctAnswerDialog.setTitle("Edit Correct Answer");
            correctAnswerDialog.setHeaderText("Edit Correct Answer");

            // Show the dialogs and update the question if the user clicks OK
            if (questionDialog.showAndWait().isPresent()) {
                selectedQuestion.setQuestion(questionDialog.getResult());
                selectedQuestion.setOptionA(optionADialog.showAndWait().orElse(""));
                selectedQuestion.setOptionB(optionBDialog.showAndWait().orElse(""));
                selectedQuestion.setOptionC(optionCDialog.showAndWait().orElse(""));
                selectedQuestion.setOptionD(optionDDialog.showAndWait().orElse(""));
                selectedQuestion.setCorrectAnswer(correctAnswerDialog.showAndWait().orElse(""));
                table.refresh();
                saveExamsToFile();
            }
        }
    }

    private void handleDeleteButton() {
        ExamQuestion selectedQuestion = table.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questionList.remove(selectedQuestion);
            table.refresh();
            saveExamsToFile();
        }
          // Update the question numbers based on the index in the questionList
    for (int i = 0; i < questionList.size(); i++) {
        ExamQuestion question = questionList.get(i);
        question.setQuestionNumber(i + 1);
    }
    }
    
    private void handleAddButton() {
        // Create a dialog for adding a new question
        TextInputDialog questionDialog = new TextInputDialog();
        questionDialog.setTitle("Add Question");
        questionDialog.setHeaderText("Add a new question");

        // Create dialogs for adding the answer options
        TextInputDialog optionADialog = new TextInputDialog();
        optionADialog.setTitle("Add Option A");
        optionADialog.setHeaderText("Add Option A");

        TextInputDialog optionBDialog = new TextInputDialog();
        optionBDialog.setTitle("Add Option B");
        optionBDialog.setHeaderText("Add Option B");

        TextInputDialog optionCDialog = new TextInputDialog();
        optionCDialog.setTitle("Add Option C");
        optionCDialog.setHeaderText("Add Option C");

        TextInputDialog optionDDialog = new TextInputDialog();
        optionDDialog.setTitle("Add Option D");
        optionDDialog.setHeaderText("Add Option D");

        TextInputDialog correctAnswerDialog = new TextInputDialog();
        correctAnswerDialog.setTitle("Add Correct Answer");
        correctAnswerDialog.setHeaderText("Add Correct Answer");

        // Show the dialogs and add the new question if the user clicks OK
        if (questionDialog.showAndWait().isPresent()) {
            int questionNumber = questionList.size() + 1;
            String question = questionDialog.getResult();
            String optionA = optionADialog.showAndWait().orElse("");
            String optionB = optionBDialog.showAndWait().orElse("");
            String optionC = optionCDialog.showAndWait().orElse("");
            String optionD = optionDDialog.showAndWait().orElse("");
            String correctAnswer = correctAnswerDialog.showAndWait().orElse("");

            ExamQuestion newQuestion = new ExamQuestion(questionNumber, question, optionA, optionB,
                    optionC, optionD, correctAnswer);
            questionList.add(newQuestion);
            table.refresh();
            saveExamsToFile();
        }
    }

    public static class ExamQuestion {
        private int questionNumber;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String correctAnswer;

        public ExamQuestion(int questionNumber, String question, String optionA, String optionB, String optionC,
                            String optionD, String correctAnswer) {
            this.questionNumber = questionNumber;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }

        public int getQuestionNumber() {
            return questionNumber;
        }

        public void setQuestionNumber(int questionNumber) {
            this.questionNumber = questionNumber;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String toCSVString() {
            return questionNumber + "," + question + "," + optionA + "," + optionB + ","
                    + optionC + "," + optionD + "," + correctAnswer;
        }
    }
}
