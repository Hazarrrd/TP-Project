package clientserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by lenovo on 30.12.2017.
 */
public class ServerController {


    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private int port = 8000;
    //input & output
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public void initialize() throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }
}
