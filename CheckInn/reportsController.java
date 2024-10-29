package CheckInn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class reportsController implements Initializable{
    @FXML
    private HBox topBar;
    @FXML
    private TextArea textArea;

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
        Parent root = FXMLLoader.load(getClass().getResource("employeeHome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int caretPosition = textArea.caretPositionProperty().get();
        try{
            Scanner scanner = new Scanner(CheckInnInterface.file);
            while(scanner.hasNextLine()) {
                textArea.appendText(scanner.nextLine() + "\n");
            }
            scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found exception!");
        }
        textArea.positionCaret(caretPosition);
        textArea.setEditable(false);
        textArea.setMouseTransparent(false);
    }
}
