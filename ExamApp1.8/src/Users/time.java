package Users;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;



/*
The for loop iterates over a series of time intervals, counting down from TIMER_DURATION_SEC to 1. 
Each iteration represents one second of the countdown.
Inside the loop, the variable countdownTime is calculated as the remaining time left in the countdown. 
This value is used to format the time as a string in minutes and seconds.
A new KeyFrame object is created for each second of the countdown.
The Duration of each KeyFrame is set to i + 1 seconds, where i is the current iteration of the loop.
This ensures that each KeyFrame is triggered at the correct time.
The value of the timerText object's textProperty() is set to the formatted time using the KeyValue class. 
This means that the text displayed by the timerText object will be updated every second to show the remaining time in the countdown.
Finally, each KeyFrame is added to the countdownTimeline object's list of key frames using the getKeyFrames().add() method.
*/

public class time extends GridPane {

   
    private Line line;
    private final Text timerText;
    private final Timeline countdownTimeline;

    public time(int TIMER_DURATION_SEC) {
        // Set the padding and alignment of the GridPane
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_LEFT);

        // Create the timer text and add it to the scene
        timerText = new Text();
        timerText.setFill(Color.BLUE);
        timerText.setFont(Font.font("Arial", 36));
        timerText.setTextOrigin(VPos.CENTER);
//        timerText.setAlignment(Pos.CENTER_LEFT);
        add(timerText, 0, 0);

        // Create the countdown timeline to update the timer text
        countdownTimeline = new Timeline();
        countdownTimeline.setCycleCount(1);

        // Add a KeyFrame to the countdown timeline for each second of the timer
        for (int i = 0; i < TIMER_DURATION_SEC; i++) {
            int countdownTime = TIMER_DURATION_SEC - i; 
            String formattedTime = String.format("%d:%02d", countdownTime / 60, countdownTime % 60);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i + 1), new KeyValue(timerText.textProperty(), formattedTime),
                   new KeyValue(timerText.fillProperty(), Color.WHEAT));
            countdownTimeline.getKeyFrames().add(keyFrame);

            // Add a KeyValue to change the color and font of the timer text and line to red when it reaches 0:10
            if (countdownTime <= 10) {
                keyFrame = new KeyFrame(Duration.seconds(i + 1),
                        new KeyValue(timerText.fillProperty(), Color.RED),
                        new KeyValue(timerText.fontProperty(), Font.font("Courier New", FontWeight.BOLD, 36)));
                countdownTimeline.getKeyFrames().add(keyFrame);
            }

        }
        
        // Add a final KeyFrame to set the timer text to "0:00" when the countdown is finished
        KeyFrame finalKeyFrame = new KeyFrame(Duration.seconds(TIMER_DURATION_SEC + 1), new KeyValue(timerText.textProperty(), "0:00"));
        countdownTimeline.getKeyFrames().add(finalKeyFrame);
        countdownTimeline.play();  // playing the timer as 0:00 to make const with time
        
    }

    public Line getLine() {
        return line;
    }

    public Text getTimerText() {
        return timerText;
    }
    
    public Timeline getCountdownTimeline() {
        return countdownTimeline;
    }
}
