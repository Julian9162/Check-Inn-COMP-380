package CheckInn;

import java.io.File;
import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * employeeHomeController ---   Employee Home Controller handles all GUI for the employee home page and all buttons in it
 * Utilizes               ---   employeeHome.fxml
 * @author                      Patrick Karamian
 */
public class employeeHomeController{
    @FXML
    private HBox topBar;
    @FXML
    private PasswordField passInput;
    @FXML
    private TextField userInput;
    @FXML
    private Label loginLabel;

    private FileChooser fileChooser = new FileChooser();
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
     Returns to the home page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void homeButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes the employee to the check in page
     @param event     ActionEvent object for button click
     @return          none
     */
    public void checkIn(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("checkInSearch.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes the employee to the check out page
     @param event     ActionEvent object for button click
     @return          none
     */
    public void checkOut(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("checkOutSearch.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes the employee to the update room cleanliness page
     @param event     ActionEvent object for button click
     @return          none
     */
    public void updateClean(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("updateClean.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes the employee to the view reports page
     @param event     ActionEvent object for button click
     @return          none
     */
    public void viewReports(ActionEvent event) throws IOException {
        fileChooser.setInitialDirectory(new File("CheckInn\\reports\\"));
        CheckInnInterface.file = fileChooser.showOpenDialog(new Stage());

        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("reports.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Takes the employee to the update room availability page
     @param event     ActionEvent object for button click
     @return          none
     */
    public void updateRoom(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("updateRoom.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
