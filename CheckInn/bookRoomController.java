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
 * bookRoomController ---   Book Room Controller handles GUI for allowing a guest to enter their trip start and
 *                          end dates and select their desired room type
 * Utilizes           ---   bookRoom.fxml
 * @author                  Patrick Karamian
 */
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
     Sets the desired room type to Single when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void single(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Single";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Sets the desired room type to Double when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void doubleRoom(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Double";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Sets the desired room type to Triple when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void triple(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Triple";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Sets the desired room type to Connected when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void connected(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Connected";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Sets the desired room type to Suite when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void suite(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Suite";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Sets the desired room type to Penthouse when the guest clicks that button
     @param event     ActionEvent object for button click
     @return          none
     */
    public void penthouse(ActionEvent event) throws IOException {
        CheckInnInterface.type = "Penthouse";
        CheckInnInterface.checkin = StartDate.getValue().toString();
        CheckInnInterface.checkout = EndDate.getValue().toString();
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("custInfo.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Initializes the window to show the room types available for the current selected date range
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
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