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
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("review.fxml"));
        stage.setScene(new Scene(root));
        stage.show();        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Initialize variables
        reservationNumber = CheckInnInterface.reserve.getReservationID();

        //Initialize page
        reservationLabel.setText("Editing Reservation: " + reservationNumber + " for " + name);
    }
}
