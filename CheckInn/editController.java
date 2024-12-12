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
 * editController ---   Edit Controller handles all GUI for editing reservation details
 * Utilizes       ---   edit.fxml
 * @author              Patrick Karamian
 */
public class editController implements Initializable{
    @FXML
    private DatePicker checkInDate;
    @FXML
    private DatePicker checkOutDate;
    @FXML
    private Button closeButton;
    @FXML
    private Label reservationLabel;
    @FXML
    private HBox topBar;
    @FXML
    private ComboBox<String> roomType;
    @FXML
    private TextField groupSize;

    //reservation details
    private long reservationNumber;
    private String name;

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
     Edits the reservation with the entered details when the button is clicked
     @param event     ActionEvent object for button click
     @return          none
     */
    public void editReserve(ActionEvent event) throws IOException {
        //call edit function with values of new reservation details
        if (CheckInnInterface.resManager.editReservation(reservationNumber, roomType.getValue(),
                Integer.parseInt(groupSize.getText()), checkInDate.getValue().toString(), checkOutDate.getValue().toString())) {

                    //edit was successful, go to review page
                    stage = (Stage) topBar.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("transactions.fxml"));
                    stage.setScene(new Scene(root));
                    stage.show(); 
        } else {
            CheckInnInterface.returnFXML = "edit.FXML";
            //edit was successful, go to review page
            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("error.fxml"));
            stage.setScene(new Scene(root));
            stage.show(); 
        }  
    }

    /**
     Initializes the window to show the current date range, as well as the listeners to the DatePickers
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @SuppressWarnings("unused")
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        reservationNumber = CheckInnInterface.reserve.getReservationID();
        name = CheckInnInterface.reserve.getCustomer().getFirstName();
        roomType.getItems().addAll("Single", "Double", "Triple", "Connected", "Suite", "Penthouse");

        //Initialize page
        reservationLabel.setText("Editing Reservation: " + reservationNumber + " for " + name);
        checkInDate.setValue(CheckInnInterface.reserve.getCheckInDate().getLocalDate());
        checkOutDate.setValue(CheckInnInterface.reserve.getCheckOutDate().getLocalDate());

        //listeners to date pickers
        checkInDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if(newValue.isAfter(checkOutDate.getValue())) {
                checkOutDate.setValue(newValue.plusDays(1));
            }
        });
        checkOutDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if(newValue.isBefore(checkInDate.getValue())) {
                checkInDate.setValue(newValue.minusDays(1));
            }
        });
    }
}
