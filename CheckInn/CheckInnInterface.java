package CheckInn;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class CheckInnInterface extends Application {

    public Stage window;
    public static DateManager dateManager = new DateManager();
    public static CustomerManager cusManager = new CustomerManager();
    public static ReservationManager resManager = new ReservationManager();
    public static ArchiveManager arcManager = new ArchiveManager();
    public static ReportManager repManager = new ReportManager();
    public static RoomManager roomManager = new RoomManager();
    public static EmployeeManager empManager = new EmployeeManager();
    public static Reservation reserve;
    public static File file;
    public static String type;
    public static String room;
    public static String checkin;
    public static String checkout;
    public static String returnFXML;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        window = primaryStage;
        window.initStyle(StageStyle.UNDECORATED);
        window.setResizable(false);
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }
}