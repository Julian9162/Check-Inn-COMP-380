package CheckInn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * checkedInController ---  Checked In Controller to display GUI to show that a guest was properly checked in
 * Utilizes            ---  checkedIn.fxml
 * @author                  Patrick Karamian
 */
public class checkedInController implements Initializable{
    @FXML
    private Button closeButton;
    @FXML
    private HBox topBar;
    @FXML
    private Label reservationLabel;

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
     Initializes the window to show the room the guest was checking into
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        try {
            if(CheckInnInterface.empManager.checkInCustomer(CheckInnInterface.reserve.getReservationID()).equals("Successful")) {
                reservationLabel.setText("Room " + CheckInnInterface.reserve.getRoomNumber());
            } else {
                reservationLabel.setText(CheckInnInterface.empManager.checkInCustomer(CheckInnInterface.reserve.getReservationID()));
            }
        } catch (IOException e) {
            reservationLabel.setText("Error, please try again.");
            System.out.println("I/O Exception checkedInController " + e.getMessage());
        }
    }
}
