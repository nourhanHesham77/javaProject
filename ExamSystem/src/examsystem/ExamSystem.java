package examsystem;


import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class ExamSystem extends Application {
    

    private StackPane root1;
    private StackPane root3;
    Questions roots2;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        // window 1 (log in window // start window)
      
        root1 = new StackPane();
        Button start = new Button("start");
        root1.getChildren().add(start);
        Scene scene1 = new Scene(root1,600,650);
       
    
        
        // window 2 (Question view window)
        roots2 = new Questions();
        // add css file
        URL url1 =  this.getClass().getResource("style.css");
        Scene scene2 = new Scene(roots2.getRoot2());
        scene2.getStylesheets().add(url1.toExternalForm());
         scene1.getStylesheets().add(url1.toExternalForm());
        
         
         
        // window 3 (Score view window)
        root3 = new StackPane();
        Scene scene3 = new Scene(root3);
       
        
        // switch scene
          start.setOnAction(e ->    {
              primaryStage.setScene(scene2);
        });
          
        primaryStage.setTitle("Exam system");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
