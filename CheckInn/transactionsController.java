package CheckInn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * transactionsController ---   Transactions Controller handles all the GUI for calculating the cost of a booking
 *                              and authorizing the payment through validating customer credentials
 * Utilizes               ---   transactions.fxml
 * @author                      Patrick Karamian
 */
public class transactionsController implements Initializable {
    @FXML
    private HBox topBar;
    @FXML
    private TextField FullName;
    @FXML
    private Label Amount;
    @FXML
    private Label errorText;

    private Stage stage;
    double x = 0, y = 0;

    private String name;
    private String customerName;
    private int price;

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
     Validates guest credentials when the button is clicked and confirms the transaction
     @param event     ActionEvent object for button click
     @return          none
     */
    public void submit(ActionEvent event) throws IOException {
        name = FullName.getText();
        customerName = CheckInnInterface.reserve.getCustomer().getFullName();

        if(name.equals(customerName)) {
            stage = (Stage) topBar.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("review.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            errorText.setText("Invalid name! Please try again.");
        }        
    }

    /**
     Initializes the window to show the price of the reservation that was just created
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Display price
        price = CheckInnInterface.empManager.calculateTotalcost(CheckInnInterface.reserve);
        Amount.setText("$" + price);
        errorText.setText("");
    }
}