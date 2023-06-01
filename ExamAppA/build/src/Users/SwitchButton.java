package Users;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import static Start.Start.y;

/**
 * A custom switch button that can be toggled on and off.
 */
public class SwitchButton extends Label {
    private final SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);
 /**
     * Constructs a new SwitchButton object.
     */
    public SwitchButton() {
        // Create a new button to use as the switch
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(30);
        
        switchBtn.setStyle("-fx-background-radius:50;");
        
        // Add an event handler to toggle the switch
        // reverse the state if the button is clicked
        switchBtn.setOnAction((ActionEvent t) -> {
            switchedOn.set(!switchedOn.get());
        });

        // Set the button as the graphic for the label
        setGraphic(switchBtn);

        // Add a listener to update the label text and style when the switch is toggled
        switchedOn.addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
            if (t1) {
                setText("ON");
                setStyle("-fx-background-color: #eee;-fx-text-fill:black;-fx-background-radius:50; -fx-padding:3;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 25, 0, 0, 10);");

                setContentDisplay(ContentDisplay.RIGHT);
            } else {
                setText("OFF");
                setStyle("-fx-background-color: #eee;-fx-text-fill:black;-fx-background-radius:50;-fx-padding:3;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 25, 0, 0, 10);");
                setContentDisplay(ContentDisplay.LEFT);
            }
        });

        // Set the initial state of the switch to off
        if(y == 1){
            setText("ON");
            setStyle("-fx-background-color: #eee;-fx-text-fill:black;-fx-background-radius:50; -fx-padding:3;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 25, 0, 0, 10);");

            setContentDisplay(ContentDisplay.RIGHT);
        }else{
            switchedOn.set(false);
        }
    }

    /**
     * Returns the SimpleBooleanProperty that controls the state of the switch.
     *
     * @return the SimpleBooleanProperty that controls the state of the switch
     */
    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }
}