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

public class bookRoomController implements Initializable{
    @FXML
    private AnchorPane topBar;
    @FXML
    private DatePicker StartDate;
    @FXML
    private DatePicker EndDate;
    @FXML
    private Label loginLabel;
    @FXML
    private GridPane roomButtons;
    @FXML
    private Button singleButton;
    @FXML
    private Button doubleButton;
    @FXML
    private Button trippleButton;
    @FXML
    private Button connectedButton;
    @FXML
    private Button suiteButton;
    @FXML
    private Button penthouseButton;


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

    //single button handler
    public void single(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Single";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //double button handler
    public void doubleRoom(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Double";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //triple button handler
    public void triple(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Triple";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //connected button handler
    public void connected(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Connected";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //suite button handler
    public void suite(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Suite";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //penthouse button handler
    public void penthouse(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Penthouse";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @SuppressWarnings("unused")
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        roomButtons.getChildren().clear();
        //listeners to date pickers
        StartDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if((oldValue == null) || (newValue.isAfter(EndDate.getValue()))) {
                EndDate.setValue(newValue.plusDays(1));
            }
        });
        EndDate.valueProperty().addListener((e, oldValue, newValue) -> {
            if((newValue.isBefore(StartDate.getValue()))) {
                StartDate.setValue(newValue.minusDays(1));
            }
            roomButtons.getChildren().clear();
            roomButtons.getChildren().addAll(singleButton,doubleButton,trippleButton,connectedButton,suiteButton,penthouseButton);
        });
    }
}