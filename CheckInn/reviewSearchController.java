package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class reviewSearchController{
    @FXML
    private HBox topBar;
    @FXML
    private TextField reserveInput;

    private HotelManager hotel;
    private Reservation reservation;
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

    //home button handler
    public void search(ActionEvent event) throws IOException {
        //reservation = hotel.getReserve(Integer.parseInt(reserveInput.getText().toString()));

        //switch scenes
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("review.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
