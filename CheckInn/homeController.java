package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * homeController ---   Home Controller handles all GUI for the main home page of the application
 * Utilizes       ---   home.fxml
 * @author              Patrick Karamian
 */
public class homeController{
    @FXML
    private HBox topBar;

    private Stage stage;
    double x = 0, y = 0;

    /**
     Closes the window when the close button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void close(ActionEvent event) {
        stage = (Stage) topBar.getScene().getWindow();
        stage.close();
    }

    /**
     Saves position of the window when the mouse clicks on the drag bar
     @param event     MouseEvent object for mouse click
     @return          none
     */
    public void setXY(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    /**
     Drags position of the window when the mouse drags the drag bar
     @param event     MouseEvent object for mouse click
     @return          none
     */
    public void dragXY(MouseEvent event) {
        stage = (Stage) topBar.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    /**
     Takes user to the employee login page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void loginButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("employeeLogin.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes user to the review a reservation page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void reviewButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("reviewSearch.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes user to the book a room page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void reserveButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("bookRoom.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
