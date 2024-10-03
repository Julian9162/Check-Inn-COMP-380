package CheckInn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;

public class ScreenDisplay extends Application {

    public Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        window = primaryStage;
        window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(false);
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }
}