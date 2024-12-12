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

/**
 * CustInfoController ---   Customer Info Controller handles GUI for gathering customer information for creating
 *                          their reservation
 * Utilizes           ---   custInfo.fxml
 * @author                  Patrick Karamian
 */
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
     Creates a reservation for the guest with the information they entered
     @param event     ActionEvent object for button click
     @return          none
     */
    public void submitInfo(ActionEvent event) throws IOException {
        CheckInnInterface.reserve = 
                    CheckInnInterface.resManager.createReservation(FullName.getText(),
                                        CheckInnInterface.type,Integer.parseInt(GroupSize.getText()),
                                        CheckInnInterface.checkin,CheckInnInterface.checkout,Email.getText());

        if (CheckInnInterface.reserve == null) {
            //go to error page
            CheckInnInterface.returnFXML = "bookRoom.fxml";

            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("error.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("transactions.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }        
    }
}
