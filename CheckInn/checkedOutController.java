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
 * checkedOutController --- Checked Out Controller handles GUI to show a guest was properly checked out
 * Utilizes             --- checkedOut.fxml
 * @author                  Patrick Karamian
 */
public class checkedOutController implements Initializable{
    @FXML
    private Button closeButton;
    @FXML
    private Label reservationLabel;
    @FXML
    private Label reservationLabel1;
    @FXML
    private HBox topBar;

    private boolean checkedOut;
    private long reservationNumber;
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
     Initializes the window to show a confirmation when the guest is properly checked out
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        reservationNumber = CheckInnInterface.reserve.getReservationID();

        try {
            checkedOut = CheckInnInterface.empManager.checkOutCustomer(reservationNumber);
            if(checkedOut) {
                reservationLabel.setText("Reservation " + Long.toString(reservationNumber));
                reservationLabel1.setText("has been checked out");
            } else {
                reservationLabel.setText("Error, please try again.");
                reservationLabel1.setText("");
            }
            
        } catch (IOException e) {
            reservationLabel.setText("Error, please try again.");
            reservationLabel1.setText("");
            System.out.println("I/O error checkedOutController " + e.getMessage());
        }
    }
}
