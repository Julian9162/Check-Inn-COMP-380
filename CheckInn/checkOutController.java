package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * checkOutController ---   Check Out Controller handles GUI for checking out a guest by entering their reservation number
 * Utilizes           ---   checkOutSearch.fxml
 * @author                  Patrick Karamian
 */
public class checkOutController{
    @FXML
    private HBox topBar;
    @FXML
    private TextField reserveInput;
    @FXML
    private Label errorLabel;

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
        Parent root = FXMLLoader.load(getClass().getResource("employeeHome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Searches for reservation number and checks out guest from the hotel 
     @param event     ActionEvent object for button click
     @return          none
     */
    public void search(ActionEvent event) throws IOException {
        //search for reservation
        CheckInnInterface.reserve = CheckInnInterface.resManager.getReservation(Long.parseLong(reserveInput.getText().toString()));
        if (CheckInnInterface.reserve == null) {
            errorLabel.setText("Invalid reservation ID, please try again.");
        } else {
            //switch scenes
            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("checkedOut.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
