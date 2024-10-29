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

public class checkedInController implements Initializable{
    @FXML
    private Button closeButton;
    @FXML
    private HBox topBar;
    @FXML
    private Label reservationLabel;

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
