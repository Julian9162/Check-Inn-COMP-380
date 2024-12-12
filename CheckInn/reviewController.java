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
 * reviewController --- Review Controller handles all GUI for the review a reservation page
 *                      where the user can view all the details of a previous reservation they made
 * Utilizes         --- review.fxml
 * @author              Patrick Karamian
 */
public class reviewController implements Initializable{
    @FXML
    private Label checkInDate;
    @FXML
    private Label checkOutDate;
    @FXML
    private Button closeButton;
    @FXML
    private Label reservationLabel;
    @FXML
    private HBox topBar;
    @FXML
    private Label roomType;
    @FXML
    private Label groupSize;

    //reservation details
    private long reservationNumber;
    private String checkIn;
    private String checkOut;
    private String type;
    private String name;
    private int size;

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
     Takes the user to the cancel reservation page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void cancelReserve(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("cancel.fxml"));
        stage.setScene(new Scene(root));
        stage.show();        
    }

    /**
     Takes the user to the edit reservation page when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void editReserve(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("edit.fxml"));
        stage.setScene(new Scene(root));
        stage.show();        
    }

    /**
     Initializes the window to show the reservation details of the reservation that was opened
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        reservationNumber = CheckInnInterface.reserve.getReservationID();
        checkIn = CheckInnInterface.reserve.getCheckInDate().dateString();
        checkOut = CheckInnInterface.reserve.getCheckOutDate().dateString();
        type = CheckInnInterface.reserve.getRoomType();
        size = CheckInnInterface.reserve.getGroupSize();
        name = CheckInnInterface.reserve.getCustomer().getFirstName();

        //Initialize page
        reservationLabel.setText("Reservation: " + reservationNumber + " for " + name);
        roomType.setText(type);
        groupSize.setText(Integer.toString(size));
        checkInDate.setText(checkIn);
        checkOutDate.setText(checkOut);
    }
}
