package gui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    public Button endTurn;
    public MenuItem exit;
    public MenuItem about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void aboutGame(ActionEvent actionEvent) {
    }
}
