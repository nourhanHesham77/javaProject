package Users;

import Start.Start;
import static Start.Start.y;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import static javafx.geometry.HPos.CENTER;
import static javafx.geometry.HPos.LEFT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.geometry.VPos.TOP;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;




public class User extends Application {

    private List<Question> questions;
    private Map<String, String> answers;
    private int currentQuestionIndex;
    private Stage primaryStage;
    private String name;
    private String exam;
    private time timerDisplay;
    private int UserScore;
    private int que_num;
    private final int row = 0;
    private String examName;
    private final LocalDate dt = LocalDate.now();
    private String[] header;
    private int time;
    private int scoree;
    private int score;
    

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        answers = new HashMap<>();
        showNameScreen();
    }
    
    /**
    * Displays the name screen for the exam application.
    */
    private void showNameScreen() {
        // Create a new GridPane to layout the name screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add a label and text field for the user's name
        Label nameLabel = new Label("Enter your name:");
        gridPane.add(nameLabel, 0, 0);
        TextField nameTextField = new TextField();
        gridPane.add(nameTextField, 1, 0);

        // Add a label and password field for the user's password
        Label passwordLabel = new Label("Enter the password:");
        gridPane.add(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 1);

        // Add a label to display status messages
        Label statusLabel = new Label();
        gridPane.add(statusLabel, 1, 3);

        
        // Add a button to start the exam
        Button startButton = new Button("Start");
        startButton.setOnAction((ActionEvent event) -> {
            // Validate the user's login credentials
            String password = passwordField.getText();
            String username = nameTextField.getText();
            LoginValidator validator = new LoginValidator("Users.csv");
            boolean isValidLogin = validator.validateLogin(username, password);
            if (isValidLogin) {
                // Save the user's name and move on to the exam selection screen
                name = nameTextField.getText();
                choseExam();
            }
            else {
                // Display an error message if the login credentials are invalid
                statusLabel.setText("Invalid username or password.");
                statusLabel.setStyle("-fx-text-fill: #Ff0000");
            }
        });
        gridPane.add(startButton, 1, 2);
        
        Button closeBtn = new Button();
        closeBtn.setText("Close User Mode");
        closeBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for closing teacher mode
            primaryStage.close();
            launchStart();
        });
        gridPane.add(closeBtn, 1, 4);
        

        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(gridPane, 400, 400);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
    * Displays the exam selection screen for the exam application.
    */
    private void choseExam() {
        // Create a new GridPane to layout the exam selection screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add a label for the exam selection prompt
        Label examLabel = new Label("Choose an exam:");
        examLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        GridPane.setHalignment(examLabel, CENTER);
        gridPane.add(examLabel, 0, 0);


        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
         List<String> fileNames = new ArrayList<>();
         File folder = new File("Exams");
         File[] files = folder.listFiles();
         if (files != null) {
             for (File file : files) {
                 String fileName = file.getName();
                 int dotIndex = fileName.lastIndexOf('.');
                 if (dotIndex > 0) {
                     fileName = fileName.substring(0, dotIndex);
                 }
                 fileNames.add(fileName.trim());
             }
         } else {
             System.err.println("Folder does not exist or is not a folder");
         }
         
        // Add radio buttons for each available exam
        List<Button> examButtons = new ArrayList<>();
        for (int i = 0; i < fileNames.size(); i++) {
            Button button = new Button(fileNames.get(i));
            examButtons.add(button);
            GridPane.setHalignment(button, HPos.CENTER);
            gridPane.add(button, 0, i + 1);
        }

        // Add an event handler for each exam button
        for (Button button : examButtons) {
            button.setOnAction((ActionEvent event) -> {
                // Get the name of the selected exam
                examName = button.getText();

                // Check if the user has previously taken this exam
                File examFolder = new File("ExamResults");
                boolean previouslyTaken = false;
                if (examFolder.exists() && examFolder.isDirectory()) {
                    File[] examFiles = examFolder.listFiles();
                    for (File file : examFiles) {
                        if (file.getName().equals("ResultsOF" + examName + ".csv")) {
                            try {
                                try (Scanner scanner = new Scanner(file)) {
                                    boolean isFirstLine = true;
                                    while (scanner.hasNextLine()) {
                                        String line = scanner.nextLine();
                                        if (isFirstLine) {
                                            isFirstLine = false;
                                            continue; // skip the header line
                                        }
                                        String[] parts = line.split(",");
                                        System.out.println(Arrays.toString(parts));
                                        if (parts.length > 0 && !parts[0].isEmpty() && parts[0].equals(name)) {
                                            previouslyTaken = true;
                                            UserScore = Integer.parseInt(parts[1].trim());
                                            break;
                                        }
                                    }
                                    scanner.close();
                                }
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        } 
                    }
                }

                // If the user has not previously taken the exam, start the exam
                if (!previouslyTaken) {
                    // Load the selected exam's questions and shuffle them
                    exam = examName + ".csv";
                    try {
                        questions = QuestionLoader.loadQuestions(exam);
                        header = QuestionLoader.header;
                        time = Integer.parseInt(header[4]);
                        scoree = Integer.parseInt(header[3]);
                        que_num = Integer.parseInt(header[1]);
                    } catch (IOException ex) {
                        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Collections.shuffle(questions);
                    currentQuestionIndex = 0;
                    showInstructionsScreen();
                } else {
                    // Inform the user that they have already taken this exam
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Exam Already Taken");
                    alert.setHeaderText(null);
                    alert.setContentText("You have already taken the " + examName + " exam. And your score was " + UserScore + ".");
                    alert.showAndWait();
                }
            });
        }
        
        
        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(gridPane, 400, 400);
        // Set the scene's stylesheet based on the current dark mode setting
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
    * Displays the instructions screen for the exam application.
    */
    private void showInstructionsScreen() {
        // Create a new GridPane to layout the instructions screen
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(20);

        // Add a TextArea to display the exam instructions
        TextArea instructionsTextArea = new TextArea();
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setWrapText(true);
        gridPane.add(instructionsTextArea, 0, 0, 2, 1);

        // Load the exam instructions from a file and display them in the TextArea
        try (BufferedReader reader = new BufferedReader(new FileReader("instructions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                instructionsTextArea.appendText(line + "\n");
            }
        } catch (IOException e) {
            instructionsTextArea.setText("Error loading instructions from file.");
        }

        // Add a "Start" button to the GridPane
        Button startButton = new Button("Start");
        gridPane.add(startButton, 1, 1);

        // Set the action for the "Start" button to show the questions screen
        startButton.setOnAction(event -> showQuestionScreen());

        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(gridPane);
        // Set the scene's stylesheet based on the current dark mode setting
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
    * Displays the current question on the screen along with the timer and menu bar.
    */
    private void showQuestionScreen() {
        // Get the current question
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Create the GridPane to display the question and choices
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the question label to the GridPane
        Label questionLabel = new Label(currentQuestion.getText());
        gridPane.add(questionLabel, 0, 0, 2, 1);

        // Add the timer text to the GridPane
        if (timerDisplay == null) {
            // Create a single instance of TimerDisplay
            timerDisplay = new time(time * 60);
        }
        gridPane.add(timerDisplay.getTimerText(), 2, 0);
        GridPane.setMargin(timerDisplay.getTimerText(), new Insets(20));
        GridPane.setValignment(timerDisplay.getTimerText(), javafx.geometry.VPos.TOP);
        timerDisplay.getTimerText().setTextAlignment(TextAlignment.RIGHT);

        // Set additional constraints for the timer text
        GridPane.setValignment(timerDisplay.getTimerText(), javafx.geometry.VPos.TOP);
        timerDisplay.getTimerText().setTextAlignment(TextAlignment.RIGHT);


        // if the timer is finished it shows an alert message to the user telling him that the time is finished
        // and opens to him conformation screen if he clicked save it will saves his answers
        // if not and closed the alert it won't save his answers 
        timerDisplay.getCountdownTimeline().setOnFinished((ActionEvent event) -> {
             Platform.runLater(() -> {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Time Out");
                 alert.setHeaderText(null);
                 alert.setContentText("Time Out");

                 ButtonType showScoreButton = new ButtonType("Save and submit");
                 alert.getButtonTypes().setAll(showScoreButton);
                 Optional<ButtonType> result = alert.showAndWait();

                 if (result.isPresent() && result.get() == showScoreButton) {
                     primaryStage.close();
                     showConfirmationScreen();
                 } else{
                     primaryStage.close();
                     answers.clear();
                     showConfirmationScreen();
                 }
             });
         });

        
        EventHandler<WindowEvent> closeRequestHandler = (WindowEvent event4) -> {
             event4.consume();

             // create a confirmation dialog
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Close the program");
             alert.setHeaderText(null);
             alert.setContentText("Are you sure you want to Exit?");

             // add "OK" and "Cancel" buttons
             ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
             ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
             alert.getButtonTypes().setAll(okButton, cancelButton);

             // show the dialog and wait for the user's response
             Optional<ButtonType> result = alert.showAndWait();
             if (result.isPresent() && result.get() == okButton) {
                 Platform.exit();
             }
     };

        primaryStage.setOnCloseRequest(closeRequestHandler);

        // Add the toggle group for the choices
        ToggleGroup toggleGroup = new ToggleGroup();
        for (int i = 0; i < currentQuestion.getChoices().size(); i++) {
            String choice = currentQuestion.getChoices().get(i);
            RadioButton radioButton = new RadioButton(choice);
            radioButton.setToggleGroup(toggleGroup);
            gridPane.add(radioButton, 0, i + 1);
            //if an answer has already been selected for this question (i.e., if the answers map contains a key matching the current question text 
            //and a value matching this choice), the radio button is pre-selected.
            if (answers.containsKey(currentQuestion.getText()) && answers.get(currentQuestion.getText()).equals(choice)) {
                radioButton.setSelected(true);
            }
        }

        // Add the previous button to the GridPane
        Button previousButton = new Button("Previous");
        gridPane.add(previousButton, 0, currentQuestion.getChoices().size() + 1);
        GridPane.setHalignment(previousButton, javafx.geometry.HPos.LEFT);
        if (currentQuestionIndex > 0) {
            // Add event handler to go to the previous question
            previousButton.setOnAction((ActionEvent event) -> {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                // making sure that the user had chosed to save his answer
                if (selectedRadioButton != null) {
                    answers.put(currentQuestion.getText(), selectedRadioButton.getText());
                }
                currentQuestionIndex--;
                if (currentQuestionIndex < 0) {
                    currentQuestionIndex = 0;
                }
                showQuestionScreen();
            });
        } else {
            // disabling the previous button if it the first page
            previousButton.setDisable(true);
        }

        // Add the next or finish button to the GridPane
        if (currentQuestionIndex < questions.size() - 1) {
            // Add the next button to go to the next question
            Button nextButton = new Button("Next");
            gridPane.add(nextButton, 1, currentQuestion.getChoices().size() + 1);
            GridPane.setHalignment(nextButton, javafx.geometry.HPos.RIGHT);
            nextButton.setOnAction((ActionEvent event) -> {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    answers.put(currentQuestion.getText(), selectedRadioButton.getText());
                }
                currentQuestionIndex++;
                showQuestionScreen();
            });
        } else {
            // Add the finish button to end the quiz and show the confirmation screen
            Button FinishButton = new Button("submit");
            gridPane.add(FinishButton, 1, currentQuestion.getChoices().size() + 1);
            GridPane.setHalignment(FinishButton, javafx.geometry.HPos.RIGHT);
            FinishButton.setOnAction((ActionEvent event) -> {
                RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
                if (selectedRadioButton != null) {
                    answers.put(currentQuestion.getText(), selectedRadioButton.getText());
                }
                // checking if it the last page and the  user had solved all the questions
                if (currentQuestionIndex + 1 == questions.size() && answers.size() == questions.size()) {
                    timerDisplay.getCountdownTimeline().stop();
                    showConfirmationScreen();
                } else {
                    // Ask the user to confirm if they want to end the quiz if he didn't solve all the questions
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("confirm");
                    alert.setResizable(false);
                    alert.setContentText("there is still some questions u didn't answer do you want to end?");
                    Optional<ButtonType> result = alert.showAndWait();
                    ButtonType button = result.orElse(ButtonType.CANCEL);

                    if (button == ButtonType.OK) {
                        timerDisplay.getCountdownTimeline().stop();
                        showConfirmationScreen();
                    } else {
                        showQuestionScreen();
                    }
                }
            });
        }

        // Set the scene and show the stage
        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        if (y == 1) {
            // Use the dark theme
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            // Use the light theme
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.show();
    }
   
    
    /**
    * Shows the confirmation screen when the quiz is completed.
    */
    private void showConfirmationScreen() {
        // Remove the close request handler from the primary stage
        // so that i can  be able to close the program with no proplem
        primaryStage.setOnCloseRequest(null);

        // Create a new VBox to hold the buttons
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedAnswer = answers.get(question.getText());
            // updating the score if the user's answer is equal to the correct answer 
            if (selectedAnswer != null && selectedAnswer.trim().equals(question.getCorrectAnswer())) {
                score += scoree;
            }
        }
     
        File file = new File("ExamResults/" + "ResultsOF" + exam);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String firstLine = reader.readLine();
                if (firstLine != null && firstLine.equals("name,score,date")) {
                    // The file already has a header, so skip printing it
                } else {
                    // The file does not have a header, so print it
                    try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                        writer.println("name" + "," + "score" + "," + "date");
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                // Display error message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Saving Results");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while saving the results. Please try again.");
                alert.showAndWait();
            }
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("name" + "," + "score" + "," + "date");
                writer.flush();
            } catch (IOException e) {
                // Display error message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Saving Results");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while saving the results. Please try again.");
                alert.showAndWait();
            }
        }

        // Append the participant's name, score, and date to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter("ExamResults/" + "ResultsOF" + exam, true))) {
            writer.println(name + "," + score + "," + dt);
            writer.flush();

            // Display success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Results Saved");
            alert.setHeaderText(null);
            alert.setContentText("The results have been saved to the file '" + "ResultsOF" + exam);
            alert.showAndWait();
        } catch (IOException e) {
            // Display error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Saving Results");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while saving the results. Please try again.");
            alert.showAndWait();
        }
        
        Button closeExamBtn = new Button("close Exam");
        closeExamBtn.setOnAction((ActionEvent event) -> {
            currentQuestionIndex = 0;
            answers.clear();
            Collections.shuffle(questions);
            timerDisplay.getCountdownTimeline().stop();
            // making the time null so that it doesn't start untill he opens question screen
            timerDisplay = null;
            choseExam();
        });
        vbox.getChildren().add(closeExamBtn);
        
        
        // Add a "Show Results" button
        Button showResultsButton = new Button("Show Results");
        showResultsButton.setOnAction((ActionEvent event) -> {
            // Show the results screen
            showResultsScreen();
        });
        vbox.getChildren().add(showResultsButton);

        // Create a new scene with the VBox and set it as the primary stage's scene
        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);

        // Set the scene's stylesheet based on the value of variable 'y'
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }

        // Show the primary stage
        primaryStage.show();
    }
    
    /**
    * Shows the results screen when the quiz is completed.
    */
    @SuppressWarnings("IncompatibleEquals")
    private void showResultsScreen() {
        // Create a new GridPane to hold the results
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create a label for the title and add it to the GridPane
        Label titleLabel = new Label("Results:");
        GridPane.setRowIndex(titleLabel, 1);
        GridPane.setColumnIndex(titleLabel, 0);
        GridPane.setColumnSpan(titleLabel, 2);
        gridPane.getChildren().add(titleLabel);

        // Create a new ScrollPane with the GridPane as its content
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setVvalue(10);
        scrollPane.setHvalue(10);
        scrollPane.setPadding(new Insets(5,189,5,150));

 
        int x = 1;

        // Loop through each question and add the question text, the user's answer (if they answered the question),
        // and the correct answer to the GridPane
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedAnswer = answers.get(question.getText());
            // updating the score if the user's answer is equal to the correct answer
            Label questionLabel = new Label(question.getText());
            GridPane.setRowIndex(questionLabel, i + 1 + x);
            GridPane.setColumnIndex(questionLabel, 0);
            gridPane.getChildren().add(questionLabel);
            // printing the user's answer if he chosed
            if (selectedAnswer != null) {
                Label answerLabel = new Label("Your answer: " + selectedAnswer);
                GridPane.setRowIndex(answerLabel, i + 2 + x);
                GridPane.setColumnIndex(answerLabel, 0);
                gridPane.getChildren().add(answerLabel);
            }
            // printing the correct answer
            Label correctAnswerLabel = new Label("Correct answer: " + question.getCorrectAnswer());
            GridPane.setRowIndex(correctAnswerLabel, i + 3 + x);
            GridPane.setColumnIndex(correctAnswerLabel, 0);
            gridPane.getChildren().add(correctAnswerLabel);
            gridPane.add(new Label(" "), 0, i + 4 + x);
            x = x + 4;
        }

        // Add a label for the score to the GridPane
        Label scoreLabel = new Label("Score: " + score + "/" + que_num * scoree);
        GridPane.setRowIndex(scoreLabel, 1);
        GridPane.setColumnIndex(scoreLabel, 1);
        GridPane.setHalignment(scoreLabel, LEFT);
        GridPane.setValignment(scoreLabel, TOP);
        gridPane.getChildren().add(scoreLabel);

        // Add a button to save the results to a CSV file and handle the button's action
        Button showOrderButton = new Button("show Order");
        GridPane.setRowIndex(showOrderButton, x + 8);
        GridPane.setColumnIndex(showOrderButton, 0);
        GridPane.setHalignment(showOrderButton, CENTER);
        gridPane.getChildren().add(showOrderButton);
       
        showOrderButton.setOnAction((ActionEvent event) -> {
            ScoreScreen();
        });


        // Create a new scene with the ScrollPane and set it as the primary stage's scene
        Scene scene = new Scene(scrollPane, 800, 800);
        primaryStage.setScene(scene);

        // Set the scene's stylesheet based on the value of variable 'y'
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            gridPane.setStyle("-fx-background-color: #222222");
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            gridPane.setStyle("-fx-background-color: #D9D7CB;");
        }

        // Show the primary stage
        primaryStage.show();
    }
    
    private void launchStart() {
        Start startScreen = new Start();
        Stage StartStage = new Stage();
        try {
            startScreen.start(StartStage);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void ScoreScreen() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Read the participants' scores from the CSV files
        try {
            HashMap<String, Integer> scores = new HashMap<>();
            HashMap<String, String> dates = new HashMap<>();

            File resultsFolder = new File("ExamResults");
            File[] resultFiles = resultsFolder.listFiles();

            for (File resultFile : resultFiles) {
                if (resultFile.isFile() && resultFile.getName().equals("ResultsOF" + examName + ".csv")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {
                        String line;
                        String[] headers = null;
                        while ((line = reader.readLine()) != null) {
                            String[] fields = line.split(",");
                            // to store the first line in header var as to skip the header of the file (name, score) 
                            if (headers == null) {
                                headers = fields;
                            } else {
                                // extracting the name only from the field of name
                                String names = fields[0].trim();
                                int score1 = Integer.parseInt(fields[1].trim());
                                String date = fields[2].trim();
                                dates.put(names, date);
                                scores.put(names, score1);
                            }
                        }
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            Collections.sort(sortedScores, (Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> b.getValue().compareTo(a.getValue()));

            // Add the participants' names and scores to the GridPane
            // stringBuilder is designed to address this inefficiency by providing a mutable string 
            // that can be modified in-place without creating new objects.
            StringBuilder scoresBuilder = new StringBuilder();
            scoresBuilder.append("names").append("\t").append("score").append("\t").append("date").append("\n");
            for (Map.Entry<String, Integer> entry : sortedScores) {
                String names = entry.getKey();
                int score1 = entry.getValue();
                String date = dates.get(names);
                scoresBuilder.append(names).append("\t").append(score1).append("\t").append(date).append("\n");
            }

            // Create a TextArea and set its text to the contents of the StringBuilder
            TextArea scoresTextArea = new TextArea(scoresBuilder.toString());
            scoresTextArea.setEditable(false);
            grid.add(scoresTextArea, 0, 0, 3, 1);

        } catch (IOException e) {
            System.out.println("Error reading result files: " + e.getMessage());
        }

        Button back = new Button();
        back.setText("back");
        back.setOnAction((ActionEvent event) -> {
            showResultsScreen();
        });

        grid.add(back, 1, row + 1);

        // Create and show the scene
        Scene scene = new Scene(grid, 400, 400);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}

