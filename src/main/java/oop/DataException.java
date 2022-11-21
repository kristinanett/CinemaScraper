package oop;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DataException extends RuntimeException {


    public DataException() {
        createAlertWindowWrongData();
    }


    public static void createAlertWindowWrongData() {
        Alert alertScraping = new Alert(Alert.AlertType.WARNING);
        alertScraping.setTitle("Viga!");
        alertScraping.setHeaderText("Andmete kogumisel esines viga!");
        alertScraping.setContentText("Ava programm uuesti!");
        Stage stage = (Stage) alertScraping.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:warning.png"));
        alertScraping.showAndWait();
        Platform.exit();
    }
}
