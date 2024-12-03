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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Display price
        price = CheckInnInterface.empManager.calculateTotalcost(CheckInnInterface.reserve);
        Amount.setText("$" + price);
        errorText.setText("");
    }
}