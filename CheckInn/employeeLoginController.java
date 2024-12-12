package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * employeeLoginController ---  Employee Login Controller handles all GUI for gathering employee credentials
 * Utilizes                ---  employeeLogin.fxml
 * @author                      Patrick Karamian
 */
public class employeeLoginController{
    @FXML
    private HBox topBar;
    @FXML
    private PasswordField passInput;
    @FXML
    private TextField userInput;
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
     Validates employee credentials for log in
     @param event     ActionEvent object for button click
     @return          none
     */
    public void login(ActionEvent event) throws IOException {
        if(CheckInnInterface.empManager.validateEmployee(Integer.parseInt(userInput.getText().toString()), passInput.getText().toString())) {
            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("employeeHome.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            loginLabel.setText("Incorrect login information");
        }
        
    }
}
