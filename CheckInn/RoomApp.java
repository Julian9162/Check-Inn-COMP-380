package com.example.bookroom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.stage.*;

import java.io.IOException;

public class RoomApp extends Application {

    public Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("book-room.fxml"));

        window = primaryStage;
        window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(false);
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }
}