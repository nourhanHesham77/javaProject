package Start;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Admin.ExamModification;
import Users.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class User extends Application {
    public static int y;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the buttons
        Button examButton = new Button("Teacher Mode");
        Button adminButton = new Button("User Mode");

        // Set the actions for the buttons
        examButton.setOnAction(e -> {
            primaryStage.close();
            launchExamModification();
        });

        adminButton.setOnAction(e -> {
            primaryStage.close();
            launchExamApp();
        });

        // Create a GridPane layout and set its properties
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(20);
        
        
        Label DarkMode = new Label("Dark Mode");
        root.add(DarkMode, 3, 0);
        GridPane.setValignment(DarkMode, javafx.geometry.VPos.TOP);
        SwitchButton switchButton = new SwitchButton();
        switchButton.switchOnProperty().addListener((observable, oldValue, newValue) -> {
             if (newValue) {
                 root.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
                 y = 1;
             } else {
                 root.getStylesheets().remove(getClass().getResource("dark.css").toExternalForm());
                 root.getStylesheets().add(getClass().getResource("light.css").toExternalForm());
                 y = 0;
             }
         });
        
        root.add(switchButton, 4, 0);
        // Add the buttons to the layout
        root.add(examButton, 0, 0);
        root.add(adminButton, 1, 0);

        // Create and show the scene
        Scene scene = new Scene(root, 300, 250);
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
        ExamModification examModification = new ExamModification();
        Stage examStage = new Stage();
        try {
            examModification.start(examStage);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Method to launch the ExamApp class
    private void launchExamApp() {
        Interface ExamApp = new Interface();
        Stage examStage = new Stage();
        try {
            ExamApp.start(examStage);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}