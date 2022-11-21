package oop;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class WrongDateException extends RuntimeException {

    public WrongDateException(Stage primaryStage) {
        createAlertWindowWrongDate(primaryStage);
    }

    public static void createAlertWindowWrongDate(Stage primaryStage) {
        primaryStage.hide();

        Alert alertWrongDate = new Alert(Alert.AlertType.WARNING);
        alertWrongDate.setTitle("Viga!");
        alertWrongDate.setHeaderText("Sisestasid vale kuupäeva!");
        alertWrongDate.setContentText("Proovi uuesti!");
        Stage stage = (Stage) alertWrongDate.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:warning.png"));
        alertWrongDate.showAndWait();

        TestClass.createInputWindow(primaryStage);
    }
}
