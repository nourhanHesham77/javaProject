/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examsystem;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author User
 */
class Questions extends quizCSVData {
    
    private StackPane roots;
//    private StackPane root1;
     private GridPane root2;
     public Parent getRoot2(){
        root2= new GridPane();
        roots = new StackPane();
        root2.setPrefSize(600, 450);
        
        // setting nodes of questions and options //
        
        // Displaying HeadLine text
        Text Headline = new Text("Question 1*");
        
        // Displaying Question text
        Text area = new Text();
        area.setText(question);
        
        
       
        // Displaying options using radio buttons
        RadioButton option1 = new RadioButton(answer1);
        RadioButton option2 = new RadioButton(answer2);
        RadioButton option3 = new RadioButton(answer3);
        RadioButton option4 = new RadioButton(answer4);
        
        // for Only one RadioButton can be selected we use togglegroup
        ToggleGroup group = new ToggleGroup();
        option1.setToggleGroup(group);
        option2.setToggleGroup(group);
        option3.setToggleGroup(group);
        option4.setToggleGroup(group);
        
        // Displaying control buttons
        Button backButton = new Button("back");
        Button nextButton = new Button("next");
        
        // functions of visibility
        backButton.setVisible(false);
        // in the next button other functions will be added
        nextButton.setOnAction(e->{
            backButton.setVisible(true);
        });
            
             
        
              HBox h1 = new HBox();
                  HBox h3 = new HBox();
        h3.getChildren().addAll(backButton,nextButton);
        h3.setSpacing(50);
        // Displaying in order : 
        root2.setHgap(10);  
        root2.setVgap(17); 
        root2.addRow(0, Headline);  
        root2.addRow(2, area);  
        root2.addRow(3, option1);    
        root2.addRow(4, option2);    
        root2.addRow(5, option3);    
        root2.addRow(6, option4); 
//        root2.add(backButton,0,9);
//        root2.add(nextButton,2, 9);
root2.addRow(9, h3);
        root2.setAlignment(Pos.CENTER);
        
        VBox v1 = new VBox();
       
        HBox h2 = new HBox();
  
        h2.getChildren().addAll( root2);
        v1.getChildren().addAll(h1,h2);
        h1.setAlignment(Pos.CENTER);
        v1.setSpacing(20);
        h2.setSpacing(20);
        h2.setAlignment(Pos.CENTER);
        
        //setting IDs for css handling
        h1.setId("headline");
        root2.setId("container");
//        nextButton.setId("next");
        area.setId("question");
        
//        root2.setMaxSize(500, 500);

        h1.setPrefHeight(200);
        
        roots.getChildren().addAll(v1);

        return roots;

    }

   

    
}
