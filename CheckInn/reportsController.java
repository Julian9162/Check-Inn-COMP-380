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

/**
 * reportsController ---    Reports Controller handles all GUI for openening and viewing reports written by the system
 * Utilizes          ---    reports.fxml
 * @author                  Patrick Karamian
 */
public class reportsController implements Initializable{
    @FXML
    private HBox topBar;
    @FXML
    private Label windowName;
    @FXML
    private TextArea textArea;

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
        Parent root = FXMLLoader.load(getClass().getResource("employeeHome.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     Initializes the window to open the file picker and dispay the file contents in the application
     @param arg0     URL object to hold location of relative paths of objects in the stage
     @param arg1     ResourceBundle object to hold the objects in the stage 
     @return         none
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        windowName.setText("CheckInn | " + CheckInnInterface.file.getName());
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
