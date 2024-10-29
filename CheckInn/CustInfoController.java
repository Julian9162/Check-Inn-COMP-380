package CheckInn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CustInfoController{
    @FXML
    private HBox topBar;
    @FXML
    private TextField FullName;
    @FXML
    private TextField Email;
    @FXML
    private TextField GroupSize;
    @FXML
    private Label loginLabel;

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

    //submit info button handler
    public void submitInfo(ActionEvent event) throws IOException {
        CheckInnInterface.reserve = CheckInnInterface.resManager.createReservation(FullName.getText(),CheckInnInterface.type,Integer.parseInt(GroupSize.getText()),
                                                        CheckInnInterface.checkin,CheckInnInterface.checkout,Email.getText());
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("review.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}