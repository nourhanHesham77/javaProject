package ExamApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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

public class ExamApp extends Application {

    private List<Question> questions;
    private Map<String, String> answers;
    private int currentQuestionIndex;
    private Stage primaryStage;
    private int y;
    private String name;
    private String exam;
    private static final int NUM_EXAMS = 3;
    private time timerDisplay;
    
            
            
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

        // Add a switch button to toggle dark mode
        Label DarkMode = new Label("Dark Mode");
        gridPane.add(DarkMode, 3, 0);
        GridPane.setValignment(DarkMode, javafx.geometry.VPos.TOP);
        SwitchButton switchButton = new SwitchButton();
        // getting the status of the switch button and updating the style by it 
        // making a var called y to get the new status to be able to add to the other screens
        switchButton.switchOnProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue) {
                 gridPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                 y = 1;
             } else {
                 gridPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                 y = 0;
             }
         });
        gridPane.add(switchButton, 4, 0);
        GridPane.setValignment(switchButton, javafx.geometry.VPos.TOP);

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

        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(gridPane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
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

       // Add radio buttons for each available exam
       ToggleGroup examToggleGroup = new ToggleGroup();
       List<String> examNames = Arrays.asList("Exam1", "Exam2", "Exam3");
       List<RadioButton> examRadioButtons = new ArrayList<>();
       for (int i = 0; i < NUM_EXAMS; i++) {
           RadioButton radioButton = new RadioButton(examNames.get(i));
           radioButton.setToggleGroup(examToggleGroup);
           examRadioButtons.add(radioButton);
           GridPane.setHalignment(radioButton, HPos.CENTER);
           gridPane.add(radioButton, 0, i + 1);
       }
       // chosing the first exam by default
       examToggleGroup.selectToggle(examRadioButtons.get(0));

       // Add a button to start the selected exam
       Button startButton = new Button("Start Exam");
       startButton.setOnAction((ActionEvent event) -> {
           // Load the selected exam's questions and shuffle them
           RadioButton selectedRadioButton = (RadioButton) examToggleGroup.getSelectedToggle();
           exam = selectedRadioButton.getText() + ".csv";
           try {
               questions = QuestionLoader.loadQuestions(exam);
           } catch (IOException ex) {
               Logger.getLogger(ExamApp.class.getName()).log(Level.SEVERE, null, ex);
           }
           Collections.shuffle(questions);
           currentQuestionIndex = 0;
           showInstructionsScreen();
       });
       gridPane.add(startButton, 0, 5);
       GridPane.setHalignment(startButton, HPos.CENTER);

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
           timerDisplay = new time();
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
       timerDisplay.getCountdownTimeline().setOnFinished(event -> {
            Platform.runLater(() -> {
                primaryStage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Time Out");
                alert.setHeaderText(null);
                alert.setContentText("Time Out");

                ButtonType showScoreButton = new ButtonType("Save and submit");
                alert.getButtonTypes().setAll(showScoreButton);
                showConfirmationScreen();
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == showScoreButton) {
                    showConfirmationScreen();
                } else{
                    answers.clear();
                    showConfirmationScreen();
                }
            });
        });

       // Add the timer menu bar to the GridPane
       Menu_Bar timerMenuBar = new Menu_Bar();
       timerMenuBar.createTimerMenuBar();
       gridPane.add(timerMenuBar.getMenuBar(), 0, 0, 2, 1);
       GridPane.setValignment(timerMenuBar.getMenuBar(), javafx.geometry.VPos.TOP);

       // Add the event handlers for the menu items
       EventHandler<ActionEvent> event1 = (ActionEvent e) -> {
           // Restart the quiz
           currentQuestionIndex = 0;
           answers.clear();
           Collections.shuffle(questions);
           timerDisplay.getCountdownTimeline().stop();
           timerDisplay = new time();
           showQuestionScreen();
       };

       EventHandler<ActionEvent> event2 = (ActionEvent e) -> {
           // Choose a different exam
           currentQuestionIndex = 0;
           answers.clear();
           Collections.shuffle(questions);
           timerDisplay.getCountdownTimeline().stop();
           // making the time null so that it doesn't start untill he opens question screen
           timerDisplay = null;
           choseExam();
       };

       EventHandler<ActionEvent> event3 = (ActionEvent e) -> {
           // End the quiz and show the confirmation screen
           timerDisplay.getCountdownTimeline().stop();
           showConfirmationScreen();
       };

       // Add the event handlers to the menu items
       timerMenuBar.getMenuBar().getMenus().get(0).getItems().get(0).setOnAction(event1);
       timerMenuBar.getMenuBar().getMenus().get(0).getItems().get(1).setOnAction(event2);
       timerMenuBar.getMenuBar().getMenus().get(0).getItems().get(2).setOnAction(event3);
       
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

       // Add a "Play Again" button
       Button playAgainButton = new Button("Play Again");
       playAgainButton.setOnAction((ActionEvent event) -> {
           // Reset the quiz and show the Question screen again
           currentQuestionIndex = 0;
           answers.clear();
           Collections.shuffle(questions);
           timerDisplay = new time();
           showQuestionScreen();
       });
       vbox.getChildren().add(playAgainButton);

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

        // Initialize variables for the score and for positioning the labels in the GridPane
        int score = 0;
        final int score2;
        int x = 1;

        // Loop through each question and add the question text, the user's answer (if they answered the question),
        // and the correct answer to the GridPane
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedAnswer = answers.get(question.getText());
            // updating the score if the user's answer is equal to the correct answer
            if (selectedAnswer != null && selectedAnswer.equals(question.getCorrectAnswer())) {
                score++;
            }
            System.out.println(score);
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
        Label scoreLabel = new Label("Score: " + score + "/" + questions.size());
        GridPane.setRowIndex(scoreLabel, 1);
        GridPane.setColumnIndex(scoreLabel, 1);
        GridPane.setHalignment(scoreLabel, LEFT);
        GridPane.setValignment(scoreLabel, TOP);
        gridPane.getChildren().add(scoreLabel);

        // Add a button to save the results to a CSV file and handle the button's action
        Button saveButton = new Button("Save Results");
        GridPane.setRowIndex(saveButton, x + 8);
        GridPane.setColumnIndex(saveButton, 0);
        GridPane.setHalignment(saveButton, CENTER);
        gridPane.getChildren().add(saveButton);
        // getting the data to print it
        score2 = score;
        LocalDateTime dt = LocalDateTime.now();
        final LocalDateTime dt2;
        dt2 = dt;

        saveButton.setOnAction((ActionEvent event) -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv", true))) {
                writer.println(name + "," + score2 + "," + dt2);
                writer.flush();

                // Display success message
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Results Saved");
                alert.setHeaderText(null);
                alert.setContentText("The results have been saved to the file 'results.csv'.");
                alert.showAndWait();
            } catch (IOException e) {
                // Display error message
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Saving Results");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while saving the results. Please try again.");
                alert.showAndWait();
            }
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
}
