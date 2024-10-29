package CheckInn;

import java.io.File;
import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class employeeHomeController{
    @FXML
    private HBox topBar;
    @FXML
    private PasswordField passInput;
    @FXML
    private TextField userInput;
    @FXML
    private Label loginLabel;

    private FileChooser fileChooser = new FileChooser();
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

    //check in button handler
    public void checkIn(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("checkInSearch.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //check out button handler
    public void checkOut(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("checkOutSearch.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    //view reports button handler
    public void viewReports(ActionEvent event) throws IOException {
        fileChooser.setInitialDirectory(new File("CheckInn\\reports\\"));
        CheckInnInterface.file = fileChooser.showOpenDialog(new Stage());

        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("reports.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
