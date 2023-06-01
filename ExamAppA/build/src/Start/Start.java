package Start;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Admin.Teacher;
import Users.User;
import Users.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Start extends Application {
    public static int y;
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        // set title & logo for the Quiz mobile app
        primaryStage.setTitle("Trivia");
        Image logo = new Image(getClass().getResourceAsStream("logo0.jpeg"));
        primaryStage.getIcons().add(logo);
        // Display app screen
        showAppScreen();
    }
    
    
      /**
     * Displays App screen for few sec. before switching to startMood scene
     */
    private void showAppScreen() {
        VBox root = new VBox();
        HBox root1 = new HBox();
        Text headline = new Text("Trivia");
        headline.autosize();
        Image img = new Image(getClass().getResourceAsStream("logo0.jpeg"));
        ImageView imageView = new ImageView(img);
        imageView.getStyleClass().add("image");
        imageView.setFitHeight(55);
        imageView.setFitWidth(55);
        root1.setAlignment(Pos.CENTER);
        root1.setSpacing(10);
        
        // spinning animation
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle("-fx-progress-color: purple;");
        HBox root2 = new HBox(progressIndicator);
        root2.setAlignment(Pos.CENTER);
        
        root1.getChildren().addAll(imageView,headline);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.getChildren().addAll(root1,root2);
        Scene scene = new Scene(root, 500, 600);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
            progressIndicator.setStyle("-fx-progress-color: #0297cf;");
            headline.getStyleClass().add("text-app");
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
            headline.getStyleClass().add("text-app");
        }
        
        // Setting timimng for the app Screen
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {
            startMood();
        }));
        timeline.play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
  
    
    
    // start screen to choose the mood
    private void startMood(){
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20);
          
        HBox head = new HBox();
        
        // Create the buttons
        Button examButton = new Button("Teacher Mode");
        Button adminButton = new Button("User Mode");
        
        examButton.getStyleClass().add("mood-btn");
        adminButton.getStyleClass().add("mood-btn");

        // Set the actions for the buttons
        examButton.setOnAction(e -> {
            primaryStage.close();
            launchExamModification();
        });

        adminButton.setOnAction(e -> {
            primaryStage.close();
            launchExamApp();
        });
        
        // add animation to options
        adminButton.setOpacity(0);
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(adminButton.opacityProperty(), 0.0)),
            new KeyFrame(Duration.seconds(2), new KeyValue(adminButton.opacityProperty(), 1.0))
        );
        timeline.play();

        // Create a GridPane layout and set its properties
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(20);
        root.setVgap(25);
        root.setPadding(new Insets(15));
       
        Label DarkMode = new Label("Dark Mode");
        DarkMode.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        SwitchButton switchButton = new SwitchButton();
        switchButton.switchOnProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue) {
                 container.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                 y = 1;
             } else {
                 container.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                 container.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                 y = 0;
             }
         });
        
        head.getChildren().addAll(DarkMode,switchButton);
        DarkMode.setPadding(new Insets(10));

        head.setAlignment(Pos.TOP_CENTER);
        head.setSpacing(220);
        // Add the buttons to the layout
        root.addRow(3,examButton);
        root.addRow(4,adminButton);
        
        container.getChildren().addAll(head,root);

        // Create and show the scene
        Scene scene = new Scene(container, 500, 600);
        if (y == 1) {
            scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to launch the ExamModification class
    private void launchExamModification() {
        Teacher examModification = new Teacher();
        Stage examStage = new Stage();
        try {
            examModification.start(examStage);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Method to launch the ExamApp class
    private void launchExamApp() {
        User ExamApp = new User();
        Stage examStage = new Stage();
        try {
            ExamApp.start(examStage);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}