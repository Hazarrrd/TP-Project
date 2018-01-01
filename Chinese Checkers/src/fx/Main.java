package fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chinese Checkers");

        showFirstWindow();
    }

    private void showFirstWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("newWindow.fxml"));
        mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showGameWindowScene()throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("gameWindow.fxml"));
        BorderPane gamePlay = loader.load();

        Stage gameStage = new Stage();
        gameStage.setTitle("Chinese Checkers");
        gameStage.initModality(Modality.NONE);
        
        Scene scene = new Scene(gamePlay);
        gameStage.setScene(scene);
        gameStage.showAndWait();

    }

    public static void main(String[] args) {
        launch(args);

    }
}
