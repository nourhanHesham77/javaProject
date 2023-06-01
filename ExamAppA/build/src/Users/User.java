package Users;

import Admin.Teacher;
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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;




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
    private String examName;
    private final LocalDate dt = LocalDate.now();
    private String[] header;
    private int time;
    private int scoree;
    private int score;
    private int rank;
    private double percent;

    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
         // set title & logo for the Quiz mobile app
        primaryStage.setTitle("Trivia");
        Image logo = new Image(getClass().getResourceAsStream("logo0.jpeg"));
        primaryStage.getIcons().add(logo);
        answers = new HashMap<>();
        showNameScreen();
    }
    
    /**
    * Displays the name screen for the exam application.
    */
    private void showNameScreen() {
        // Add Headline 
        VBox root = new VBox();
        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setAlignment(Pos.CENTER);
        HBox h1 = new HBox();
        Label head = new Label("LOGIN");
        head.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(head);
        h1.getStyleClass().add("headline");
        
        //Creating a separator
        Separator line = new Separator();
        line.setMaxWidth(100);
        line.setHalignment(HPos.CENTER);
        line.getStyleClass().add("line");


        // Create a new GridPane to layout the name screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(6);
        gridPane.setVgap(10);

        
        gridPane.addRow(2, h1);
        gridPane.addRow(3, line);
        // Add a label and text field for the user's name
        Label nameLabel = new Label("Username:");
        gridPane.addRow(4, nameLabel);
        TextField nameTextField = new TextField();
        gridPane.addRow(5, nameTextField);
        nameTextField.setPromptText("Username");
        nameTextField.setFocusTraversable(false);

        // Add a label and password field for the user's password
        Label passwordLabel = new Label("Password:");
        gridPane.addRow(6, passwordLabel);
        PasswordField passwordField = new PasswordField();
        gridPane.addRow(7, passwordField);
        passwordField.setPromptText("Password");
        passwordField.setFocusTraversable(false);
        
       

        // Add a label to display status messages
        Label statusLabel = new Label();
        gridPane.addRow(8, statusLabel);

   
        // Add a button to start the exam
        Button startButton = new Button("LOGIN");
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

        gridPane.addRow(9, startButton);
        startButton.getStyleClass().add("login-btn");
        
        //setting id for styling
        head.getStyleClass().add("login-head");

        // Create a new scene with the GridPane and set it as the primary stage's scene
        //setting close mode btn
        Button closeBtn = new Button();
        closeBtn.setText("Close User Mode");
        closeBtn.getStyleClass().add("close-btn");
        closeBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for closing teacher mode
            primaryStage.close();
            launchStart();
        });
        gridPane.addRow(11,closeBtn);
        GridPane.setHalignment(closeBtn, javafx.geometry.HPos.CENTER);

        container.getChildren().add(gridPane);
        container.setPrefSize(400, 550);
        root.getChildren().add(container);
        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(root, 500, 600);
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
        VBox vBox = new VBox();
        
        // Create a new GridPane to layout the exam selection screen
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add a label for the exam selection prompt
        Label examLabel = new Label("Choose an Exam :");
        examLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        GridPane.setHalignment(examLabel, CENTER);
        gridPane.addRow(0,examLabel);


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
         
        // Add buttons for each available exam
        List<Button> examButtons = new ArrayList<>();
        for (int i = 0; i < fileNames.size(); i++) {
            Button button = new Button(fileNames.get(i));
            examButtons.add(button);
            GridPane.setHalignment(button, HPos.CENTER);
            button.getStyleClass().add("otp-btn");
            // to maintain button and text size withou cut
             button.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            // add animation to options
            button.setOpacity(0);
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(button.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2.2), new KeyValue(button.opacityProperty(), 1.0))
          );
            timeline.play();

            gridPane.addRow( i + 2,button);
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
                    
                    // add Styling
                    DialogPane dialogPane = alert.getDialogPane();
                    if (y == 1) {
                        dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                    } else {
                        dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                        dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                    }
                    dialogPane.getStyleClass().add("myDialog");

                    alert.showAndWait();
                }
            });
        }
        
        Button LogOut = new Button("Log Out");
        LogOut.getStyleClass().add("otp-btn");
        LogOut.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for editing an exam
            showNameScreen();
            
        });
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(LogOut.opacityProperty(), 0.0)),
            new KeyFrame(Duration.seconds(2.2), new KeyValue(LogOut.opacityProperty(), 1.0))
        );
        timeline.play();
        
        vBox.getStyleClass().add("ch-exam");
        vBox.getChildren().addAll(gridPane, LogOut);
        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(vBox, 500, 600);
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
        HBox head = new HBox();
        VBox root = new VBox();
        
        
        // ANIMATION
        // Create a text object
        Text text = new Text("Friendly Reminder");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.WHITE);
        text.setStroke(Color.WHITE);
        text.setStrokeWidth(1);

        // Create a translate transition for the text
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(2));
        translateTransition.setNode(text);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);

        // Set the y-coordinate of the translation animation
        translateTransition.setFromY(0);
        translateTransition.setToY(8);
       
        // Start the translate transition
        translateTransition.play();
        
        // add to head
        head.getChildren().add(text);

        // Add a TextArea to display the exam instructions
        TextArea instructionsTextArea = new TextArea();
        instructionsTextArea.setEditable(false);
        instructionsTextArea.setWrapText(true);
        instructionsTextArea.setFocusTraversable(false);
        instructionsTextArea.getStyleClass().add("instructions");
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
        gridPane.add(startButton, 2, 1);
        
        Button backButton = new Button("Back");
        gridPane.add(backButton, 1, 1);
        // add all in a root
        root.getChildren().addAll(head,gridPane);

        // Set the action for the "Start" button to show the questions screen
        startButton.setOnAction(event -> showQuestionScreen());
        backButton.setOnAction(event -> choseExam());

        // Create a new scene with the GridPane and set it as the primary stage's scene
        Scene scene = new Scene(root,500,600);
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
        
         // create panes 
        VBox v1 = new VBox();
        VBox head = new VBox();
        head.getStyleClass().add("quiz-head");
        HBox qcontain = new HBox();
        GridPane btnContain = new GridPane();
        btnContain.setAlignment(Pos.TOP_CENTER);
        btnContain.setHgap(320);
       

        // Create the GridPane to display the question and choices
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

         // Add the question label to the GridPane
        Label questionLabel = new Label(currentQuestion.getText());
        gridPane.addRow(0,questionLabel);
        questionLabel.setMinWidth(380);
        questionLabel.setMaxWidth(380);
        questionLabel.setMinHeight(50);
        questionLabel.setMaxHeight(80);
        questionLabel.getStyleClass().add("question-txt");
        questionLabel.setWrapText(true);

        // Add the timer text to the GridPane
        if (timerDisplay == null) {
            // Create a single instance of TimerDisplay
            timerDisplay = new time(time * 60);
        }
        timerDisplay.setAlignment(Pos.TOP_RIGHT);
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
                 
                // add Styling
                DialogPane dialogPane = alert.getDialogPane();
                if (y == 1) {
                    dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                } else {
                    dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                    dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                }
                dialogPane.getStyleClass().add("myDialog");

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
            //should not be passed on to any further event handlers or listeners.
            event4.consume();

            // create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close the program");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Exit?");
            alert.getDialogPane().setStyle("-fx-text-fill: black;");

            // add Styling
            DialogPane dialogPane = alert.getDialogPane();
            if (y == 1) {
                dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            } else {
                dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPane.getStyleClass().add("myDialog");

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
            radioButton.getStyleClass().add("radio-choice");
            radioButton.setWrapText(true);
            gridPane.addRow(i+2,radioButton);
            //if an answer has already been selected for this question (i.e., if the answers map contains a key matching the current question text 
            //and a value matching this choice), the radio button is pre-selected.
            if (answers.containsKey(currentQuestion.getText()) && answers.get(currentQuestion.getText()).equals(choice)) {
                radioButton.setSelected(true);
            }
        }

        // Add the previous button to the GridPane
        Button previousButton = new Button("Previous");
        btnContain.add(previousButton, 0, 0);
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
            nextButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            btnContain.add(nextButton, 1, 0);
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
            FinishButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            btnContain.add(FinishButton, 1, 0);
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
                    
                     // add Styling
                    DialogPane dialogPane = alert.getDialogPane();
                    if (y == 1) {
                        dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                    } else {
                        dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                        dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                    }
                    dialogPane.getStyleClass().add("myDialog");
                    
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

       // ANIMATION
       // Create a text object
        Text text = new Text("Question "+(currentQuestionIndex+1)+" *");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.WHITE);
        text.setStroke(Color.WHITE);
        text.setStrokeWidth(1);

        // Create a translate transition for the text
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(2));
        translateTransition.setNode(text);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setAutoReverse(true);

        // Set the y-coordinate of the translation animation
        translateTransition.setFromY(0);
        translateTransition.setToY(8);
       
        // Start the translate transition
        translateTransition.play();
        
        
       // Set the scene and show the stage

       qcontain.getChildren().add(gridPane);
       qcontain.getStyleClass().add("quiz-contain");
       qcontain.setAlignment(Pos.TOP_CENTER);
       gridPane.setMinWidth(480);
       gridPane.setMaxWidth(480);
       gridPane.setMinHeight(330);
       gridPane.setMaxHeight(330);
       head.getChildren().addAll(text,timerDisplay);
       v1.getChildren().addAll(head,qcontain,btnContain);
       v1.getStyleClass().add("root-quiz");
       Scene scene = new Scene(v1, 550, 650);
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
        // so that i can  be able to close the program with no problem
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
        // Calculate score percent
        int total = que_num * scoree;
        percent = ((double) score / total) * 100;

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
                        // to ensure that any buffered data is written to the file immediately.
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
            alert.setHeaderText("Thank you!");
            alert.setContentText("your submission has been sent");
             // add Styling
            DialogPane dialogPane = alert.getDialogPane();
            if (y == 1) {
                dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            } else {
                dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPane.getStyleClass().add("myDialog");
                    
            alert.showAndWait();
        } catch (IOException e) {
            // Display error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Saving Results");
            alert.setHeaderText(null);
             // add Styling
            DialogPane dialogPane = alert.getDialogPane();
            if (y == 1) {
                dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            } else {
                dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPane.getStyleClass().add("myDialog");
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
            showGIFScreen();
        });
        
        closeExamBtn.getStyleClass().add("mood-btn");
        showResultsButton.getStyleClass().add("mood-btn");
        vbox.getChildren().add(showResultsButton);

        // Create a new scene with the VBox and set it as the primary stage's scene
        Scene scene = new Scene(vbox, 500, 600);
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
    private void showResultsScreen() {
        // create VBox and HBox 
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getStyleClass().add("result-pg");
        HBox scoreContain = new HBox();
        scoreContain.getStyleClass().add("score-contain");
        
        // Create a new GridPane to hold the results
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getStyleClass().add("result-contain");

        // Create a label for the title and add it to the GridPane
        Label titleLabel = new Label("Results:");
        titleLabel.getStyleClass().add("titleLabel");
        GridPane.setRowIndex(titleLabel, 1);
        GridPane.setColumnIndex(titleLabel, 0);
        GridPane.setColumnSpan(titleLabel, 2);
        gridPane.getChildren().add(titleLabel);
        

        gridPane.setMaxWidth(Double.MAX_VALUE);

        // Create a new ScrollPane with the GridPane as its content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(0);
        scrollPane.scaleYProperty().add(0);
 
        int x = 1;

        // Loop through each question and add the question text, the user's answer (if they answered the question),
        // and the correct answer to the GridPane
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String selectedAnswer = answers.get(question.getText());
            // updating the score if the user's answer is equal to the correct answer
            Label questionLabel = new Label(question.getText());
            questionLabel.getStyleClass().add("question-scr");
            questionLabel.setWrapText(true);
            GridPane.setRowIndex(questionLabel, i + 1 + x);
            GridPane.setColumnIndex(questionLabel, 0);
            gridPane.getChildren().add(questionLabel);
            // printing the user's answer if he chosed
            if (selectedAnswer != null) {
                Label answerLabel = new Label("Your answer: " + selectedAnswer);
                answerLabel.setWrapText(true);
                // styling color of user answer whether wrong or true
                if(selectedAnswer.equals(question.getCorrectAnswer())){
                     answerLabel.setStyle("-fx-text-fill:black;");
                }else if(!selectedAnswer.equals(question.getCorrectAnswer())) {
                     answerLabel.setStyle("-fx-text-fill:red;");
                }
                GridPane.setRowIndex(answerLabel, i + 2 + x);
                GridPane.setColumnIndex(answerLabel, 0);
                gridPane.getChildren().add(answerLabel);
            }
            // printing the correct answer
            Label correctAnswerLabel = new Label("Correct answer: " + question.getCorrectAnswer());
            correctAnswerLabel.setWrapText(true);
            correctAnswerLabel.getStyleClass().add("correct-ans");
            // Creating a separator
            Separator line = new Separator();
            line.setMaxWidth(100);
            line.setHalignment(HPos.CENTER);
            line.getStyleClass().add("line");
            gridPane.addRow(i + 4 + x,line);
            GridPane.setRowIndex(correctAnswerLabel, i + 3 + x);
            GridPane.setColumnIndex(correctAnswerLabel, 0);
            gridPane.getChildren().add(correctAnswerLabel);
            gridPane.add(new Label(" "), 0, i + 4 + x);
            x = x + 4;
        }


        // Add a label for the score to the scoreContain HBox
        Label scoreLabel = new Label("Score: " + score + "/" + que_num * scoree);
        scoreLabel.getStyleClass().add("score");
        scoreContain.getChildren().add(scoreLabel);
        
       

        // Add a button to save the results to a CSV file and handle the button's action
        Button showRankButton = new Button("show Rank");
        GridPane.setRowIndex(showRankButton, x + 8);
        GridPane.setColumnIndex(showRankButton, 0);
        GridPane.setHalignment(showRankButton, CENTER);
        gridPane.getChildren().add(showRankButton);
       
        showRankButton.setOnAction((ActionEvent event) -> {
            ScorePScreen();
        });

        
        Button LogOutButton = new Button("Log Out");
        GridPane.setRowIndex(LogOutButton, x + 8);
        GridPane.setColumnIndex(LogOutButton, 0);
        GridPane.setHalignment(LogOutButton, LEFT);
        gridPane.getChildren().add(LogOutButton);
       
        LogOutButton.setOnAction((ActionEvent event) -> {
            currentQuestionIndex = 0;
            answers.clear();
            Collections.shuffle(questions);
            timerDisplay.getCountdownTimeline().stop();
            timerDisplay = null;
            showNameScreen();
        });
        
        Image icon = new Image(getClass().getResourceAsStream("img/quiz.jpg"));
        
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(460);
        imageView.setFitHeight(150);
        imageView.setSmooth(true);
        
        
        if(y==1){
           
        Image icondark = new Image(getClass().getResourceAsStream("img/quiz6.jpeg"));
        imageView.setImage(icondark);
        imageView.setFitWidth(460);
        imageView.setFitHeight(150);
        imageView.setSmooth(true);
        
        }

        vbox.getChildren().addAll(imageView,scoreContain,scrollPane);
        Scene scene = new Scene(vbox, 500, 600);
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
    
    private void launchStart() {
        Start examModification = new Start();
        Stage StartStage = new Stage();
        try {
            examModification.start(StartStage);
        } catch (Exception ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
    
    /**
     * Display User Score and Rank and GIF based on user's rank whether he is top 5 or not
     */
    private void ScorePScreen() {
         
         // Create Root
        VBox root = new VBox();
        root.getStyleClass().add("rank-conta");
        root.setAlignment(Pos.TOP_CENTER);
        HBox btnctr = new HBox();
        btnctr.setSpacing(120);
        btnctr.setAlignment(Pos.CENTER);
        Label uRank = new Label();
        Label uName = new Label(name);       
        Label uScore = new Label();
        
        
        uRank.getStyleClass().add("rank-txt");
        uName.getStyleClass().add("rank-txt");
        uScore.getStyleClass().add("rank-txt");
        
        // Read the participants' scores from the CSV files
        try {
            HashMap<String, Integer> scores = new HashMap<>();

            File resultsFolder = new File("ExamResults");
            File[] resultFiles = resultsFolder.listFiles();
            
            int counter = 0;                        
                        
            for (File resultFile : resultFiles) {
                if (resultFile.isFile() && resultFile.getName().equals("ResultsOF" + examName + ".csv")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {
                        String line;
                        String[] headers = null;
                        
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) {
                                continue; // Skip empty lines
                            }
                            String[] fields = line.split(",");
                            // to store the first line in header var as to skip the header of the file (name, score) 
                            if (headers == null) {
                                headers = fields;
                            } else {
                                // extracting the name only from the field of name
                                String names = fields[0].trim();
                                int score1 = Integer.parseInt(fields[1].trim());
                                scores.put(names, score1);
                            }
                        }
                    }
                }
            }
          
            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            Collections.sort(sortedScores, (Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> b.getValue().compareTo(a.getValue()));
          
            for (Map.Entry<String, Integer> entry : sortedScores) {
                String names = entry.getKey();
                int score1 = entry.getValue();
                counter++;
                if(names.equals(name)){
                    rank = counter;
                    uRank.setText("Rank : "+counter+"");
                    uScore.setText("Score : "+score1+"");
                }
            }


        } catch (IOException e) {
            System.out.println("Error reading result files: " + e.getMessage());
        }

        Button back = new Button();
        back.setText("back");
        //back.setAlignment(Pos.CENTER_LEFT);
        back.setOnAction((ActionEvent event) -> {
            showResultsScreen();
        });
        
        Button topScores = new Button();
        topScores.setText("Top Scores");
        topScores.setAlignment(Pos.CENTER_RIGHT);
        topScores.setOnAction((ActionEvent event) -> {
             ScoreScreen();
        });
        
        Image user = new Image(getClass().getResourceAsStream("img/user5.jpeg"));
        
        ImageView imageView = new ImageView(user);
        Circle circle = new Circle(60);
        ImagePattern pattern = new ImagePattern(user);
        circle.setFill(pattern);
        imageView.getStyleClass().add("user-img");
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setSmooth(true);
        
        
        // Display a gif based on user rank if his rank is within top 5 a dancing gif will appear
          
        if(rank >= 5){
            Image gif = new Image(getClass().getResource("/Users/img/dsad.gif").toExternalForm());
            // Create an image view to display the GIF
            ImageView gifView = new ImageView(gif);
            gifView.setFitHeight(200);
            gifView.setFitWidth(320);

            btnctr.getChildren().addAll(back,topScores);
            root.getChildren().addAll(circle,uName,uRank,uScore,btnctr,gifView);
            root.setSpacing(15);
            System.out.println(rank);
        }else{
            Image gif = new Image(getClass().getResource("/Users/img/dhappy.gif").toExternalForm());
            // Create an image view to display the GIF
            ImageView gifView = new ImageView(gif);
            gifView.setFitHeight(200);
            gifView.setFitWidth(320);

            btnctr.getChildren().addAll(back,topScores);
            root.getChildren().addAll(circle,uName,uRank,uScore,btnctr,gifView);
            root.setSpacing(15);
        }
          
        
     

        // Create and show the scene
        Scene scene = new Scene(root, 500, 600);
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
     * Display Top 5 Scores
     */
    
    private void ScoreScreen() {
        VBox root = new VBox();
        root.getStyleClass().add("scr-container");
        HBox img = new HBox();

        img.setAlignment(Pos.TOP_CENTER);
        img.prefWidth(root.getMaxWidth());
        img.setPrefHeight(200);
        img.getStyleClass().add("back-img");
        GridPane grid = new GridPane();
        grid.getStyleClass().add("sub-container");
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.BOTTOM_CENTER);
        GridPane gridPane = new GridPane();

        // Read the participants' scores from the CSV files
        try {
            HashMap<String, Integer> scores = new HashMap<>();

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
                                scores.put(names, score1);
                                
                            }
                        }
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            Collections.sort(sortedScores, (Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> b.getValue().compareTo(a.getValue()));

            gridPane.setHgap(20);
            gridPane.setVgap(10);

            // Add labels for each user's rank, name, and score to the GridPane
            int rowIndex = 1;
            // add counter for the rank
            int counter = 0;

            for (int i = 0; i < Math.min(sortedScores.size(), 5); i++) {
                Map.Entry<String, Integer> entry = sortedScores.get(i);
                String Name = entry.getKey();
                int Score = entry.getValue();

                counter++;

                gridPane.setAlignment(Pos.CENTER);

                Rectangle rect = new Rectangle(0, 0, 200, 50);
                rect.setStroke(Color.YELLOW);
                rect.setStrokeWidth(1);

                Label userName = new Label(Name);
                Label userScore = new Label("Scores : "+Score);
                Label userRank = new Label(Integer.toString(counter));

                userRank.setAlignment(Pos.CENTER_LEFT);
                userScore.setAlignment(Pos.CENTER_RIGHT);
                userName.setAlignment(Pos.CENTER);

                userRank.setPrefWidth(2);
                userName.setPrefWidth(100);
                userName.setMinWidth(100);
                userName.setMaxWidth(100);

                HBox hbox = new HBox(userRank,userName, userScore);
                hbox.setSpacing(20);
                hbox.setAlignment(Pos.CENTER);

                gridPane.add(rect, 0, rowIndex);
                gridPane.add(hbox, 0, rowIndex);

                rowIndex++;

            }
        }catch (IOException e) {
            System.out.println("Error reading result files: " + e.getMessage());
        }
        
        HBox backC = new HBox();

        Button back = new Button();
        back.setText("back");
        back.setOnAction((ActionEvent event) -> {
            ScorePScreen();
        });
        
        back.setAlignment(Pos.CENTER_LEFT);
        backC.getChildren().add(back);
        gridPane.getStyleClass().add("grid-pane");

        
        root.getChildren().addAll(img,gridPane,backC);

        // Create and show the scene
        Scene scene = new Scene(root, 500, 600);
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
     * Displays GIF screen depending on his percentage (success or fail) for few sec. before switching to result screen
     */
    private void showGIFScreen() {
        VBox root = new VBox();
        HBox root1 = new HBox();
      
        Image gif = new Image(getClass().getResource("/Users/img/good.gif").toExternalForm());
        Image bgif = new Image(getClass().getResource("/Users/img/bad.gif").toExternalForm());
        
        ImageView imageView = new ImageView(gif);
        // if user failed exam switch gif 
        if(percent<50){
         imageView.setImage(bgif);
        }
          
        // Create an image view to display the GIF
        
        imageView.setFitHeight(325);
        imageView.setFitWidth(320);
        root1.setAlignment(Pos.CENTER);
        root1.setSpacing(10);
       
        root1.getChildren().addAll(imageView);
        root.setSpacing(10);
        root.getChildren().addAll(root1);
        Scene scene = new Scene(root, 500, 600);
        
         if (y == 1) {
           root.setStyle("    -fx-background-color: #1e2634;"
                   + "-fx-alignment: center;");

        } else {
           root.setStyle("-fx-background-color: linear-gradient(#7c52d9, #a176ef, #a9cff3);"
                   + "-fx-alignment: center;");

        }

        // Setting timimng for the GIF Screen
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(2), event -> {
             showResultsScreen();
        }));
        
        timeline.play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    
}

