package ExamApp;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Menu_Bar {

    private MenuBar menuBar;
    private Label eventLabel;

    public Menu_Bar() {
        // Default constructor
    }
    
    public void createTimerMenuBar() {
        // Set the title for the stage (not implemented in this class)
        
        // Create a new menu
        Menu menu = new Menu("Menu");

        // Create menu items
        MenuItem menuItem1 = new MenuItem("Play Again");
        MenuItem menuItem2 = new MenuItem("Close Exam");
        MenuItem menuItem3 = new MenuItem("Save and Submit");

        // Add the menu items to the menu
        menu.getItems().addAll(menuItem1, menuItem2, menuItem3);

        // Create a new menu bar
        menuBar = new MenuBar();

        // Add the menu to the menu bar
        menuBar.getMenus().add(menu);

    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Label getEventLabel() {
        return eventLabel;
    }
}