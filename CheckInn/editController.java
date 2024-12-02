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

public class editController implements Initializable{
    @FXML
    private TextField checkInDate;
    @FXML
    private TextField checkOutDate;
    @FXML
    private Button closeButton;
    @FXML
    private Label reservationLabel;
    @FXML
    private HBox topBar;
    @FXML
    private TextField roomType;
    @FXML
    private TextField groupSize;
    @FXML
    private Label errorText;

    //reservation details
    private long reservationNumber;
    private String name;

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
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //submit button handler
    public void editReserve(ActionEvent event) throws IOException {
        //call edit function with values of new reservation details
        if (CheckInnInterface.resManager.editReservation(reservationNumber, roomType.getText(),
                Integer.parseInt(groupSize.getText()), checkInDate.getText(), checkOutDate.getText())) {

                    //edit was successful, go to review page
                    stage = (Stage) topBar.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("review.fxml"));
                    stage.setScene(new Scene(root));
                    stage.show(); 
        } else {
            errorText.setText("Error! Invalid reservation status!");
        }        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        reservationNumber = CheckInnInterface.reserve.getReservationID();
        name = CheckInnInterface.reserve.getCustomer().getFirstName();

        //Initialize page
        reservationLabel.setText("Editing Reservation: " + reservationNumber + " for " + name);
    }
}
