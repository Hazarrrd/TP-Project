package sample;

/**
 * Created by lenovo on 28.12.2017.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.net.Socket;
import java.io.*;



public class FirstWindowController {

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

    private int port = 8000;
    private String hostName="127.0.0.1";
    private Socket serverSocket = null;
    //input & ouput
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    public void initialize() throws Exception{
        serverSocket = new Socket(hostName, port);
        inputStream = new DataInputStream(serverSocket.getInputStream());
        outputStream = new DataOutputStream(serverSocket.getOutputStream());

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

