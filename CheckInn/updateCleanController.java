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
 * updateCleanController ---    Update Clean Controller handles all GUI for the window where an employee can select a room to
 *                              be set to the clean status
 * Utilizes              ---    updateClean.fxml
 * @author                      Patrick Karamian
 */
public class updateCleanController{
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
     Searches for the room when the button is clicked and updates its cleanliness status
     @param event     ActionEvent object for button click
     @return          none
     */
    public void search(ActionEvent event) throws IOException {
        CheckInnInterface.room = reserveInput.getText();
        CheckInnInterface.empManager.changeRoomCleanStatus(CheckInnInterface.room, true);

        //switch scenes
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("cleanUpdated.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
