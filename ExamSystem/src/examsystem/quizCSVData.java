/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public  class quizCSVData {
private String csvFile ;

 List<String[]> data;
public String question;
public String answer1;
public String answer2;
public String answer3;
public String answer4;
public String correct;

    public quizCSVData() {
        this.data = this.connect();
        generateQuestion();
    }

 private List<String[]>  connect () {
      List<String[]> dataa = new ArrayList<>(); 
      csvFile = "C:\\Users\\User\\Documents\\NetBeansProjects\\ExamSystem\\src\\questions.csv";
     try (BufferedReader bufferedReader =
             new BufferedReader(new FileReader(csvFile))) {
         
         String line;
         int iteration = 0;
          while ((line = bufferedReader.readLine()) != null) { 
              // to skip first line >> recommend json file instead
              if(iteration == 0) {
        iteration++;  
        continue;
    }
            String[] values = line.split(",");
             dataa.add(values);
        } 
    } catch (IOException e) {
         e.printStackTrace(); 
        } 

        return dataa;
     }

      private  void generateQuestion() {
         Random random = new Random(); 
         int rowIndex = random.nextInt(data.size()); 
         String[] row = data.get(rowIndex); 
         this.question = row[0];
         this.answer1 = row[1];
         this.answer2 = row[2];
         this.answer3 = row[3];
         this.answer4 = row[4];
         this.correct = row[5];
        
         } 
    
}
