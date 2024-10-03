package CheckInn;

import java.io.IOException;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class homeController{
    @FXML
    private HBox topBar;

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

    //employee login button handler
    public void loginButton(ActionEvent event) throws IOException {
        stage = (Stage) topBar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("employeeLogin.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
