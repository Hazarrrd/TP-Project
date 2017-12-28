package gui;

/**
 * Created by lenovo on 28.12.2017.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstWindowController implements Initializable {

    private Main main;

    ObservableList<String> numberOfPlayersList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6");
    ObservableList<String> numberOfBotsList = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");

    @FXML
    public Button exit;
    @FXML
    public ChoiceBox numberOfPlayers;
    @FXML
    public ChoiceBox numberOfBots;
    @FXML
    public Button play;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfPlayers.setItems(numberOfPlayersList);
        numberOfBots.setItems(numberOfBotsList);
    }

    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void playGame() throws Exception {
        main.showGameWindowScene();
    }
}

