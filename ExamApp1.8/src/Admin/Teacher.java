package Admin;

import static Admin.ValidationSettingExam.validatePage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Start.*;
import static Start.Start.y;
import Users.LoginValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Teacher extends Application {
    private int row = 0;
    private Scene displayExams;
    private Spinner<Integer> noChoicesSpinner;
    private Spinner<Integer> numPagesSpinner;
    private TextField examTimeField;
    private TextField examNameField;
    private TextField markTextField;
    private TextField questionField;
    private TextField answerField;
    private Stage primaryStage;
    private int pageNum = 1;
    private int totalPages = 0;
    private Scene[] pages;
    private Scene initialScene;
    private ObservableList<Teacher.ExamQuestion> questionList;
    private TableView<Teacher.ExamQuestion> table;
    private ComboBox<String> examComboBox;
    private  int MAX_NUMBER_OF_OPTIONS;
    private String[] headers;
    private  int question_num; 
    
    
    @Override
     public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // set title & logo for the Quiz mobile app
        primaryStage.setTitle("Trivia");
        Image logo = new Image(getClass().getResourceAsStream("logo0.jpeg"));
        primaryStage.getIcons().add(logo);
        showNameScreen();
    }
     
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
            LoginValidator validator = new LoginValidator("Teachers.csv");
            boolean isValidLogin = validator.validateLogin(username, password);
           if (isValidLogin) {
                // Save the user's name and move on to the exam selection screen
                EditExams();
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

        
        Button closeBtn = new Button();
        closeBtn.setText("Close Teacher Mode");
        closeBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for closing teacher mode
            primaryStage.close();
            launchStart();
        });
        gridPane.addRow(11,closeBtn);
        GridPane.setHalignment(closeBtn, javafx.geometry.HPos.CENTER);

       
        container.getChildren().add(gridPane);
        container.setPrefSize(400, 550);

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(container);
        

        // Create a new scene with the GridPane and set it as the primary stage's scene
        @SuppressWarnings("LocalVariableHidesMemberVariable")
        Scene scene = new Scene(root, 550, 650);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     
     
     void EditExams() {
        // create a list of buttons in order to add animation to all of them with just one code 
        ObservableList<Button> buttonList = FXCollections.observableArrayList();
        Button addExamBtn = new Button();
        addExamBtn.setText("Add Exam");
        buttonList.add(addExamBtn);
        addExamBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for adding an exam
            AddExam(primaryStage);
        });
        
        Button editExamBtn = new Button();
        editExamBtn.setText("Edit Exam");
        buttonList.add(editExamBtn);
        editExamBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for editing an exam
            Edit_Exam();
        });
        
        Button deleteExamBtn = new Button();
        deleteExamBtn.setText("Delete Exam");
        buttonList.add(deleteExamBtn);
        deleteExamBtn.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for deleting an exam
            DeleteExam();
        });
        
        Button showResults = new Button();
        showResults.setText("Show Results");
        buttonList.add(showResults);
        showResults.setOnAction((ActionEvent event) -> {
            // Get the directory path for the exams folder
            File examsFolder = new File("Exams");

            // Get a list of all the files in the exams folder
            File[] examFiles = examsFolder.listFiles();

            // Create a new VBox to hold the buttons
            VBox buttonBox = new VBox();
            buttonBox.setSpacing(10);
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10));
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setAlignment(Pos.CENTER);

            // Loop through the exam files and create a button for each one
            for (File examFile : examFiles) {
                // Get the file name without the extension
                String fileName = examFile.getName().replaceFirst("[.][^.]+$", "");

                // Create a new button with the file name as the text
                Button examButton = new Button(fileName);
                examButton.setOpacity(0);
                examButton.getStyleClass().add("ctr-btns");
                
                Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(examButton.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(examButton.opacityProperty(), 1.0))
                );
                timeline.play();

                // Add an event handler to the button to load the exam
                examButton.setOnAction((ActionEvent e) -> {
                    ScoreScreen(examButton.getText());
                });

                // Add the button to the VBox
                buttonBox.getChildren().add(examButton);
            }
            
            Button back = new Button();
            back.setText("back");
            back.setOnAction((ActionEvent event1) -> {
                EditExams();
            });
            buttonBox.getChildren().add(back);
            
            grid.add(buttonBox, 0,0,2,1);

            // Create a new scene with the button VBox and show it
            displayExams = new Scene(grid, 550, 650);
            if (y == 1) {
                displayExams.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            } else {
                displayExams.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                displayExams.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            primaryStage.setScene(displayExams);
            primaryStage.show();

        });
        
        Button LogOut = new Button();
        LogOut.setText("LogOut");
        buttonList.add(LogOut);
        LogOut.setOnAction((ActionEvent event) -> {
            // TODO: Add logic for editing an exam
            showNameScreen();
            
        });
        
        for (Button button : buttonList) {
            button.setOpacity(0);
            button.getStyleClass().add("ctr-btns");
            Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(button.opacityProperty(), 0.0)),
            new KeyFrame(Duration.seconds(2), new KeyValue(button.opacityProperty(), 1.0))
          );
           timeline.play();
        }
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        grid.add(addExamBtn, 0, 0, 2,1);
        grid.add(editExamBtn, 0, 1);
        grid.add(deleteExamBtn, 0, 2);
        grid.add(showResults, 0, 3);
        grid.add(LogOut, 0, 5);
      
      
        
        Scene scene1 = new Scene(grid, 550, 650);
        if (y == 1) {
            scene1.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene1.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene1.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setTitle("Exam Modification");
        primaryStage.setScene(scene1);
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


    private void ScoreScreen(String examName) {
        VBox root = new VBox();
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("scroll-score");
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        
        // Create a new ScrollPane with the GridPane as its content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(0);
        scrollPane.scaleYProperty().add(0);
        
        
        HBox headText = new HBox();
        // ANIMATION
       // Create a text object
        Text text = new Text("Students score");
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
        
        headText.getChildren().add(text);
        

        // Read the participants' scores from the CSV file
        try {
            HashMap<String, Integer> scores = new HashMap<>();
            HashMap<String, String> dates = new HashMap<>();

            File resultsFolder = new File("ExamResults");
            File[] resultFiles = resultsFolder.listFiles();

            for (File resultFile : resultFiles) {
                if (resultFile.isFile() && resultFile.getName().equals("ResultsOF" + examName + ".csv")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {
                        String line;
                        String[] headers1 = null;
                        while ((line = reader.readLine()) != null) {
                            String[] fields = line.split(",");
                            // to store the first line in header var as to skip the header of the file (name, score) 
                            if (headers1 == null) {
                                headers1 = fields;
                            } else {
                                // extracting the name only from the field of name
                                String names = fields[0].trim();
                                int score = Integer.parseInt(fields[1].trim());
                                String date = fields[2].trim();
                                dates.put(names, date);
                                scores.put(names, score);
                            }
                        }
                    }
                }
            }
            
            // Sort the participants by score in descending order
            // converting the scores into a list to be able to order them 
            // map is used to make the every pair as a one value in the list 
            // map entry provides methods for accessing the key and value of the entry, as well as modifying the value.
            
            
            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            // using collections to compare every value with other values
            Collections.sort(sortedScores, (Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) -> b.getValue().compareTo(a.getValue()));

            // Add the participants' names and scores to the GridPane
            Label Name = new Label("Name");
            Label Score = new Label("Score");
            Label date = new Label("Date");
            Name.getStyleClass().add("scr-head");
            Score.getStyleClass().add("scr-head");
            date.getStyleClass().add("scr-head");
            gridPane.addRow(0, Name,Score,date);
            int rowIndex = 1;
            for (Map.Entry<String, Integer> entry : sortedScores) {
                String names = entry.getKey();
                int score = entry.getValue();
                String uDate = dates.get(names);
                
                rowIndex++;
                Label userName = new Label(names);
                Label userScore = new Label(Integer.toString(score));
                Label userDate = new Label(uDate);
                
                userName.getStyleClass().add("scr-content");
                userScore.getStyleClass().add("scr-content");
                userDate.getStyleClass().add("scr-content");
                
                //Creating a separator
                Separator line = new Separator();
                line.setHalignment(HPos.CENTER);
                line.getStyleClass().add("mline");
        
                gridPane.addRow(rowIndex, userName,userScore,userDate);
                gridPane.add(line, 0, ++rowIndex, 3, 1);
                userName.setPrefWidth(130);
                userScore.setPrefWidth(130);  
                userDate.setPrefWidth(130);  
            }

           
          

        } catch (IOException e) {
            System.out.println("Error reading result files: " + e.getMessage());
        }
        
        Button back = new Button();
        back.setText("back");
        back.setAlignment(Pos.BOTTOM_LEFT);
        back.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(displayExams);
        });
        
        root.setSpacing(25);
        root.getChildren().addAll(headText,scrollPane,back);
        
        // Create and show the scene
        Scene scene1 = new Scene(root, 550, 650);
        if (y == 1) {
            scene1.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene1.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene1.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    /**
    *  Handling Delete 
    */
    private void DeleteExam() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        List<String> fileNames = new ArrayList<>();
        File folder = new File("Exams");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName().replaceFirst("[.][^.]+$", "");
                fileNames.add(fileName);
            }
        } else {
            System.err.println("Folder does not exist or is not a folder");
        }

        // Create a button for each file name and add it to the grid
        for (String fileName : fileNames) {
            Button button = new Button(fileName);
            button.setOnAction((ActionEvent event) -> {               
                // Delete the file when the button is clicked                         
                File fileToDelete = new File("Exams/" + fileName + ".csv");
                File extraDelete = new File("ExamResults/ResultsOF" + fileName + ".csv");
                extraDelete.delete();
                if (fileToDelete.delete()) {
                    System.out.println("File deleted successfully: " + fileName);
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), button);
                    fadeTransition.setToValue(0);
                    fadeTransition.play();

                } else {
                    System.err.println("Error deleting file: " + fileName);
                }
                // Refresh the UI to remove the deleted file's button
                DeleteExam();
            });
//             button.setOpacity(0);
            button.getStyleClass().add("ctr-btns");
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(button.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(button.opacityProperty(), 1.0))
          );
            timeline.play();
            grid.add(button, 0, row++);
        }

        Button back = new Button();
        back.setText("back");
        back.setOnAction((ActionEvent event) -> {
            EditExams();
        });

        grid.add(back, 0, row+3);

        // Create and show the scene
        Scene scene1 = new Scene(grid, 550, 650);
        if (y == 1) {
            scene1.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene1.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene1.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    

    public void AddExam(Stage stage) {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.getStyleClass().add("add-contain");

        // Text field for entering the number of choices
        Label noChoicesText = new Label("No of Choices:");
        root.add(noChoicesText, 0, 4);
        noChoicesText.getStyleClass().add("add-txt");

        //spinner for choices
        noChoicesSpinner = new Spinner<>(2, 6, 1);
        root.add(noChoicesSpinner, 1, 4);
        noChoicesSpinner.getStyleClass().add("add-opt");

        // Text field for entering the number of pages
        Label numQuestionText = new Label("No of Questions :");
        root.add(numQuestionText,0, 2);
        numQuestionText.getStyleClass().add("add-txt");

        //spinner for question
        numPagesSpinner = new Spinner<>(2, 15, 1);
        root.add(numPagesSpinner, 1, 2);
        numPagesSpinner.getStyleClass().add("add-opt");

        Label examTimeText = new Label("Exam Time(per min):");
        examTimeText.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        root.add(examTimeText, 0, 8);
        examTimeField = new TextField();
        root.add(examTimeField, 1, 8);
        examTimeText.getStyleClass().add("add-txt");

        Label markText = new Label("Mark Per Question:");
        markText.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        root.add(markText, 0, 6);
        markTextField = new TextField();
        root.add(markTextField, 1, 6);
        markText.getStyleClass().add("add-txt");

        Label examNameText = new Label("Exam Name:");
        root.add(examNameText, 0, 0);
        examNameField = new TextField();
        root.add(examNameField, 1, 0);
        examNameText.getStyleClass().add("add-txt");

        // Button to set the number of pages and display the first page
        Button setQuestionNumButton = new Button("Confirm");
        setQuestionNumButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        // Create Text nodes to display the error messages
        Text examNameErrorText = new Text();
        examNameErrorText.setFill(Color.RED);
        examNameErrorText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        root.add(examNameErrorText, 1, 1);

        Text markErrorText = new Text();
        markErrorText.setFill(Color.RED);
        markErrorText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        root.add(markErrorText, 1, 7);

        Text examTimeErrorText = new Text();
        examTimeErrorText.setFill(Color.RED);
        examTimeErrorText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        root.add(examTimeErrorText, 1, 9);

        setQuestionNumButton.setOnAction((ActionEvent event) -> {
            String examName = examNameField.getText();
            String mark = markTextField.getText();
            String examTime = examTimeField.getText();

            boolean isValid = true;

            if (!ValidationSettingExam.isExamNameValid(examName)) {
                examNameErrorText.setText("Exam name cannot be empty or contain numbers");
                isValid = false;
            } else {
                examNameErrorText.setText("");
            }

            if (!ValidationSettingExam.isMarkValid(mark)) {
                markErrorText.setText("Mark per question must be a number");
                isValid = false;
            } else {
                markErrorText.setText("");
            }

            if (!ValidationSettingExam.isExamTimeValid(examTime)) {
                examTimeErrorText.setText("Exam time must be a number");
                isValid = false;
            } else {
                examTimeErrorText.setText("");
            }

            if (isValid) {
                totalPages = numPagesSpinner.getValue();
                pageNum = 1;
                createPages(stage);
                stage.setScene(pages[0]);
            }
        });

        root.add(setQuestionNumButton, 2, 10);

        // Add a "Back" button next to the "Confirm" button
        Button backButton = new Button("Back");
        backButton.setOnAction((ActionEvent event) -> {
            EditExams();
        });
        root.add(backButton, 0, 10);

        initialScene = new Scene(root, 550, 650); // Initialize the member variable initialScene
        if (y == 1) {
            initialScene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            initialScene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            initialScene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        stage.setScene(initialScene); 
        stage.show();
    }

    private void createPages(Stage stage) {
        int noChoices = noChoicesSpinner.getValue();
        pages = new Scene[totalPages];
        for (int i = 0; i < totalPages; i++) {
            VBox pageLayout = new VBox(10);
            pageLayout.setAlignment(Pos.CENTER);
            pageLayout.getStyleClass().add("pageLayout");

            // Add a Text node to display the error message
            Text errorMessage = new Text();
            errorMessage.setFill(Color.RED);
            pageLayout.getChildren().add(errorMessage);

            Text pageText = new Text("Question " + (i + 1));
            pageLayout.getChildren().add(pageText);

            TextField textFieldQuestion = new TextField();
            pageLayout.getChildren().add(textFieldQuestion);
            Text textChoices = new Text("Choices ");
            pageLayout.getChildren().add(textChoices);
            
            for (int j = 0; j < noChoices; j++) {
                TextField textFieldChoices = new TextField();
                textFieldChoices.setMaxWidth(350);
                pageLayout.getChildren().add(textFieldChoices);
            }
            
            Text textAns = new Text("Correct Answer");
            pageLayout.getChildren().add(textAns);
            TextField textFieldAnswer = new TextField();
            textFieldAnswer.setMaxWidth(350);
            pageLayout.getChildren().add(textFieldAnswer);

            // Buttons to navigate to the next and previous pages
            HBox buttonLayout = new HBox(30);
            buttonLayout.setAlignment(Pos.CENTER);
            buttonLayout.getStyleClass().add("buttonLayout");
            Button prevButton = new Button("Previous");
            if(i==0){
                prevButton.setText("Back");
            }
            prevButton.setOnAction(event -> {
                if (pageNum > 1) {
                    pageNum--;
                    stage.setScene(pages[pageNum - 1]);
                } else {
                    // Reset pageNum and totalPages
                    pageNum = 1;
                    totalPages = 0;
                    // Set the scene to the initial scene where the user can enter the number of pages
                    stage.setScene(initialScene);
                }
            });
            
            buttonLayout.getChildren().add(prevButton);

            if (i != totalPages - 1) { // Only add the Next button if it's not the last page
                Button nextButton = new Button("Next");
                nextButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
                nextButton.setOnAction((ActionEvent event) -> {
                    // casting the root node to a VBox object cuz it may be unknown 
                    VBox currentPageLayout = (VBox) pages[pageNum - 1].getRoot();
                    if (validatePage(currentPageLayout,noChoices)) {
                        Text nextButtonErrorMessage =(Text) currentPageLayout.getChildren().get(0);
                        nextButtonErrorMessage.setText("");
                        if (pageNum < totalPages) {
                            pageNum++;
                            stage.setScene(pages[pageNum - 1]);
                        }
                    } else {
                        // Display an error message to the user
                        Text nextButtonErrorMessage =(Text) currentPageLayout.getChildren().get(0);
                        nextButtonErrorMessage.setText("Please fill in all fields correctly and Make sure Correct Answer is identical.");
                    }
                });

                buttonLayout.getChildren().add(nextButton);
            }
            // Save button
            if (i == totalPages - 1) { // Only add the save button to the last page
                Button saveButton= new Button ("Save");
                saveButton.setOnAction((ActionEvent event) -> {
                    VBox currentPageLayout = (VBox) pages[totalPages - 1].getRoot();
                    if (validatePage(currentPageLayout, noChoices)) {
                        String examName = examNameField.getText();
                        String examTimeText = examTimeField.getText();
                        int examTime = Integer.parseInt(examTimeText);

                        String mark = markTextField.getText();
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Exams/" + examName + ".csv"))) {
                            writer.write(examName + "," + numPagesSpinner.getValue() + "," + noChoicesSpinner.getValue() + "," + mark + "," + examTime + "\n");
                            for (int k = 0; k < totalPages; k++) {
                                currentPageLayout = (VBox) pages[k].getRoot();
                                questionField = (TextField) currentPageLayout.getChildren().get(2);
                                String questionText = questionField.getText();
                                writer.write(questionText + ",");
                                for (int j = 4; j < currentPageLayout.getChildren().size() - 3; j++) {
                                    TextField choiceField = (TextField) currentPageLayout.getChildren().get(j);
                                    String choiceText = choiceField.getText();
                                    writer.write(choiceText + ",");
                                }
                                answerField = (TextField) currentPageLayout.getChildren().get(currentPageLayout.getChildren().size() - 2);
                                String answerText = answerField.getText();
                                writer.write(answerText + "\n");
                            }
                        } catch (IOException e) {
                        }
                        EditExams();
                    } else {
                        // Display an error message to the user
                        Text nextButtonErrorMessage =(Text) currentPageLayout.getChildren().get(0);
                        nextButtonErrorMessage.setText("Please fill in all fields correctly and Make sure Correct Answer is identical.");
                    }
                });
                buttonLayout.getChildren().add(saveButton);
            }

            pageLayout.getChildren().add(buttonLayout);
            pages[i] = new Scene(pageLayout, 550, 650);
            if (y == 1) {
                pages[i].getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            } else {
                pages[i].getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                pages[i].getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
        }
    }
    
    
    private void Edit_Exam() {
        primaryStage.setTitle("Exam Questions");

        // Initialize the question list
        questionList = FXCollections.observableArrayList();

        // Create exam selection window
        createExamSelectionWindow();

        // Create table columns
        TableColumn<ExamQuestion, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuestion()));


        // Create the tableview and add the columns to it
        table = new TableView<>();
        table.setItems(questionList);
        table.getColumns().add(questionColumn);



        // Create buttons
        Button backButton = new Button("back");
        backButton.setOnAction(e -> EditExams());
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEditButton());

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> handleDeleteButton());

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> handleAddButton());
        
        Button SaveButton = new Button("Save");
        SaveButton.setOnAction((ActionEvent e) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Save Changes");
            alert.setHeaderText("Do you want to save the changes you made?");

            ButtonType saveButton = new ButtonType("Save Changes");
            ButtonType cancel = new ButtonType("cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(saveButton, cancel);
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
            if (result.isPresent() && result.get() == saveButton) {
                saveExamsToFile();
                headers = null;
                EditExams();
            } 
            
        });

        // Create a horizontal layout for the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.getChildren().addAll(backButton,editButton, deleteButton, addButton, SaveButton);

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setTop(examComboBox);
        root.setCenter(table);
        root.setBottom(buttonBox);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 650, 650);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up application close event
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
        });
    }

    private void createExamSelectionWindow() {
        File examsDirectory = new File("Exams");
        File[] examFiles = examsDirectory.listFiles((dir, name) -> name.endsWith(".csv"));

        examComboBox = new ComboBox<>();
        examComboBox.getItems().addAll(getExamNames(examFiles));
        examComboBox.setPromptText("Select an exam");
        examComboBox.getStyleClass().add("menu");
        

        examComboBox.setOnAction((ActionEvent e) -> {
            String selectedExam = examComboBox.getValue();
            File selectedExamFile = new File("Exams/" + selectedExam + ".csv");
            if (selectedExamFile.exists()) {
                questionList.clear();
                try {
                    headers = null;
                    loadExamsFromFile(selectedExamFile);
                } catch (IOException ex) {
                    Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void loadExamsFromFile(File selectedExamFile) throws FileNotFoundException, IOException {
        
        // Clear the contents of the table
        table.getItems().clear();
        table.getColumns().clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedExamFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // to store the first line in header var as to skip the header of the file (name, score) 
                
                if (headers == null) {
                    headers = data;
                } else {
                    if (data.length >= 3) {
                        String question = data[0];
                        List<String> options = new ArrayList<>();
                        for (int i = 1; i < data.length - 1; i++) {
                            options.add(data[i]);
                        }
                        String correctAnswer = data[data.length - 1];
                        ExamQuestion examQuestion = new ExamQuestion(question, options, correctAnswer);
                        questionList.add(examQuestion);
                    }
                }
            }                  
        } catch (IOException e) {
            // Handle the exception
        }
        
        // Refreshing the table after adding the choices 
        MAX_NUMBER_OF_OPTIONS = Integer.parseInt(headers[2]);
        System.out.println(MAX_NUMBER_OF_OPTIONS);
        question_num = Integer.parseInt(headers[1]);
        List<TableColumn<ExamQuestion, String>> optionColumns = new ArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_OPTIONS; i++) {
            final int optionIndex = i;
            TableColumn<ExamQuestion, String> optionColumn = new TableColumn<>("Option " + (char) ('A' + i));
            optionColumn.setCellValueFactory(cellData -> {
                List<String> options = cellData.getValue().getOptions();
                if (optionIndex >= 0) {
                    return new SimpleStringProperty(options.get(optionIndex));
                } else {
                    return new SimpleStringProperty("");
                }
            });
            optionColumns.add(optionColumn);
        }
        TableColumn<ExamQuestion, String> questionColumn = new TableColumn<>("Question");
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        System.out.println(Arrays.toString(headers));
        TableColumn<ExamQuestion, String> correctAnswerColumn = new TableColumn<>("Correct Answer");
        correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        table.getColumns().add(questionColumn);
        table.getColumns().addAll(optionColumns);
        table.getColumns().add(correctAnswerColumn);
        table.refresh();
    }

    private void saveExamsToFile() {
        // Create the Exams directory if it does not already exist
        headers[1] = Integer.toString(question_num);
        File examsDirectory = new File("Exams");
        if (!examsDirectory.exists()) {
            examsDirectory.mkdir();
        }
        
        File examFile = new File("Exams/" + headers[0] + ".csv");
        if (examFile.exists()) {
            examFile.delete();
        }
        
        // Save the exam data to a file in the Exams directory
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(examFile))) {
            // Write the header as the first line
           for (int i = 0; i < headers.length; i++) {
                bw.write(headers[i]);
                if (i < headers.length - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();
            
            // Write the exam questions
            for (ExamQuestion examQuestion : questionList) {
                String csvString = examQuestion.toCSVString();
                bw.write(csvString);
                bw.newLine();
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }
    
    private void handleEditButton() {
        ExamQuestion selectedQuestion = table.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            // Create a dialog for editing the question
            TextInputDialog questionDialog = new TextInputDialog(selectedQuestion.getQuestion());
            questionDialog.setTitle("Edit Question");
            questionDialog.setHeaderText("Edit the question");
            // add Styling
            DialogPane dialogPaneq = questionDialog.getDialogPane();
            if(y==1){
                dialogPaneq.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            }else{
                dialogPaneq.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPaneq.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPaneq.getStyleClass().add("myDialog");

            // Create a list of dialogs for editing the answer options
            List<TextInputDialog> optionDialogs = new ArrayList<>();
            for (int i = 0; i < selectedQuestion.getOptions().size(); i++) {
                String option = selectedQuestion.getOptions().get(i);
                TextInputDialog optionDialog = new TextInputDialog(option);
                optionDialog.setTitle("Edit Option " + (char) ('A' + i));
                optionDialog.setHeaderText("Edit Option " + (char) ('A' + i));
                optionDialogs.add(optionDialog);
                 // add Styling
                DialogPane dialogPane = optionDialog.getDialogPane();
                if(y==1){
                    dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                }else{
                    dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                    dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                }
                dialogPane.getStyleClass().add("myDialog");
            }

            TextInputDialog correctAnswerDialog = new TextInputDialog(selectedQuestion.getCorrectAnswer());
            correctAnswerDialog.setTitle("Edit Correct Answer");
            correctAnswerDialog.setHeaderText("Edit Correct Answer");
             // add Styling
            DialogPane dialogPanec = correctAnswerDialog.getDialogPane();
            if(y==1){
                dialogPanec.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            }else{
                dialogPanec.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPanec.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPanec.getStyleClass().add("myDialog");


            // Show the dialogs and update the question if the user clicks OK
            if (questionDialog.showAndWait().isPresent()) {
                selectedQuestion.setQuestion(questionDialog.getResult());
                for (int i = 0; i < optionDialogs.size(); i++) {
                    TextInputDialog optionDialog = optionDialogs.get(i);
                    String newOptionValue = optionDialog.showAndWait().orElse("");
                    selectedQuestion.getOptions().set(i, newOptionValue);
                }
                selectedQuestion.setCorrectAnswer(correctAnswerDialog.showAndWait().orElse(""));
                table.refresh();
            }
        }
    }
    
    private void handleDeleteButton() {
        ExamQuestion selectedQuestion = table.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questionList.remove(selectedQuestion);
            question_num--;
            table.refresh();
        }
       
    }
    
    private void handleAddButton() {
        // Create a dialog for adding a new question
        TextInputDialog questionDialog = new TextInputDialog();
        questionDialog.setTitle("Add Question");
        questionDialog.setHeaderText("Add a new question");
         // add Styling
        DialogPane dialogPane = questionDialog.getDialogPane();
        if(y==1){
            dialogPane.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        }else{
            dialogPane.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            dialogPane.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }                  
        dialogPane.getStyleClass().add("myDialog");

        // Create a list of dialogs for adding the answer options
        List<TextInputDialog> optionDialogs = new ArrayList<>();
        for (int i = 0; i < MAX_NUMBER_OF_OPTIONS; i++) {
            TextInputDialog optionDialog = new TextInputDialog();
            optionDialog.setTitle("Add Option " + (char) ('A' + i));
            optionDialog.setHeaderText("Add Option " + (char) ('A' + i));
            optionDialogs.add(optionDialog);
            // add Styling
            DialogPane dialogPanea = optionDialog.getDialogPane();
            if(y==1){
                dialogPanea.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            }else{
                dialogPanea.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                dialogPanea.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            }
            dialogPanea.getStyleClass().add("myDialog");
            
        }

        TextInputDialog correctAnswerDialog = new TextInputDialog();
        correctAnswerDialog.setTitle("Add Correct Answer");
        correctAnswerDialog.setHeaderText("Add Correct Answer");
        // add Styling
        DialogPane dialogPanes = correctAnswerDialog.getDialogPane();
        if(y==1){
            dialogPanes.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        }else{
            dialogPanes.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            dialogPanes.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        dialogPanes.getStyleClass().add("myDialog");

        // Show the dialogs and add the new question if the user clicks OK
        if (questionDialog.showAndWait().isPresent()) {
            String question = questionDialog.getResult();
            List<String> options = new ArrayList<>();
            for (TextInputDialog optionDialog : optionDialogs) {
                String option = optionDialog.showAndWait().orElse("");
                options.add(option);
            }
            String correctAnswer = correctAnswerDialog.showAndWait().orElse("");

            ExamQuestion newQuestion = new ExamQuestion(question, options, correctAnswer);
            questionList.add(newQuestion);
            question_num++;
            table.refresh();
        }
    }
    
    public static class ExamQuestion {
        private String question;
        private List<String> options;
        private String correctAnswer;

        public ExamQuestion(String question, List<String> options, String correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
    }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
        
        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }
        
        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String toCSVString() {
            StringBuilder csvString = new StringBuilder();
            csvString.append(question).append(",");
            for (String option : options) {
                csvString.append(option).append(",");
            }
            csvString.append(correctAnswer);
            return csvString.toString();
        }
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
