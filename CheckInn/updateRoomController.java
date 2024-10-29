package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class updateRoomController{
    @FXML
    private HBox topBar;
    @FXML
    private TextField reserveInput;
    @FXML
    private Label errorLabel;

    private Stage stage;
    double x = 0, y = 0;

    //close button handler
    public void close(ActionEvent event) {
        stage = (Stage) topBar.getScene().getWindow();
        stage.close();
    }

    //saves position of window
    public void setXY(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    //moves window when dragged
    public void dragXY(MouseEvent event) {
        stage = (Stage) topBar.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    //home button handler
    public void homeButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("employeeHome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //search button handler
    public void search(ActionEvent event) throws IOException {
        CheckInnInterface.room = reserveInput.getText();
        CheckInnInterface.roomManager.updateRoomAvailability(CheckInnInterface.room, true);
        
        //switch scenes
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("roomUpdated.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
