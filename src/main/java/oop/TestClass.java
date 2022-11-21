package oop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


public class TestClass extends Application {

    public Scene createWelcomeWindow(Stage primaryStage) {
        primaryStage.setTitle("Kinode koorija");
        Background background = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));


        Label welcomeText = new Label("Tere tulemast Tartu kinode veebikoorijasse! See programm suudab leida just Sinule sobiva filmi Tartu kolmest kinost ja Sina ei pea selleks lillegi liigutama!");
        welcomeText.setFont(new Font(20));
        welcomeText.setWrapText(true);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        Button goScrapeButton = new Button("Mine koori!");
        goScrapeButton.setAlignment(Pos.CENTER);

        goScrapeButton.setOnMouseClicked(me -> createAlertWindowScrape(primaryStage));

        AnchorPane anchor = new AnchorPane(welcomeText);
        AnchorPane.setTopAnchor(welcomeText, 20.0);
        AnchorPane.setLeftAnchor(welcomeText, 20.0);
        AnchorPane.setRightAnchor(welcomeText, 20.0);


        BorderPane borderPaneWelcome = new BorderPane();
        borderPaneWelcome.setCenter(goScrapeButton);
        borderPaneWelcome.setTop(anchor);
        goScrapeButton.setAlignment(Pos.CENTER);
        borderPaneWelcome.setBackground(background);


        Scene sceneWelcome = new Scene(borderPaneWelcome, 450, 200);
        primaryStage.getIcons().add(new Image("file:cinema.png"));
        borderPaneWelcome.prefWidthProperty().bind(sceneWelcome.widthProperty());
        borderPaneWelcome.prefHeightProperty().bind(sceneWelcome.heightProperty());

        return sceneWelcome;
    }

    public void createAlertWindowScrape(Stage primaryStage) {
        primaryStage.hide();

        Alert alertScraping = new Alert(Alert.AlertType.CONFIRMATION);
        alertScraping.setTitle("Kinnitus");
        alertScraping.setHeaderText("Info kogumine võib mõni hetk aega võtta.");
        alertScraping.setContentText("Klikka OK, kui soovid alustada.");

        Stage stage = (Stage) alertScraping.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:attention.png"));

        Optional<ButtonType> result = alertScraping.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK) {
                createWaitWindow(primaryStage);
            }
            else {
                Platform.exit();
                System.exit(0);
            }
        }
    }

    public void createWaitWindow(Stage primaryStage) {
        primaryStage.show();
        final ProgressBar progressBar = new ProgressBar(0);
        final Label statusLabel = new Label();
        statusLabel.setMinWidth(250);
        statusLabel.setTextFill(Color.BLUE);

        ScrapeTask scrapeTask = new ScrapeTask();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(scrapeTask.progressProperty());
        statusLabel.textProperty().unbind();
        statusLabel.textProperty().bind(scrapeTask.messageProperty());

        scrapeTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, t -> {
                    statusLabel.textProperty().unbind();
                    statusLabel.setText("Valmis!");
                    createInputWindow(primaryStage);
                });

        new Thread(scrapeTask).start();

        Label waitText = new Label("Andmete kogumine, palun oota... (see võib mõne minuti aega võtta)");
        waitText.setFont(new Font(15));
        waitText.setWrapText(true);
        waitText.setTextAlignment(TextAlignment.CENTER);

        BorderPane borderPaneWait = new BorderPane();

        AnchorPane anchor = new AnchorPane(waitText);
        AnchorPane.setTopAnchor(waitText, 20.0);
        AnchorPane.setLeftAnchor(waitText, 20.0);
        AnchorPane.setRightAnchor(waitText, 20.0);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(progressBar, statusLabel);
        statusLabel.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);

        borderPaneWait.setTop(anchor);
        borderPaneWait.setCenter(vBox);

        Scene sceneWait = new Scene(borderPaneWait, 300, 200);

        primaryStage.setTitle("Andmete koorimine");
        primaryStage.setScene(sceneWait);
        primaryStage.show();
    }


    public static void createInputWindow(Stage primaryStage) {
        primaryStage.setTitle("Andmete sisestamine");

        Label labelQuestion1 = new Label("Millisest kinost soovid filme otsida?");
        Label labelQuestion2 = new Label("Milliseid žanreid soovid vaadata?");
        Label labelQuestion3 = new Label("Sisesta kuupäev millisest alates soovid kinno minna. (DD.MM.YYYY)");


        Button searchButton = new Button("Otsi filme!");
        searchButton.setDisable(true);

        CheckBox checkbox1, checkbox2, checkbox3;
        checkbox1 = new CheckBox("Apollo kino");
        checkbox2 = new CheckBox("Cinamon");
        checkbox3 = new CheckBox("Forum Cinemas");

        checkbox1.setOnAction(e -> searchButton.setDisable(false));
        checkbox2.setOnAction(e -> searchButton.setDisable(false));
        checkbox3.setOnAction(e -> searchButton.setDisable(false));

        VBox layout = new VBox(20);
        VBox vBox1 = new VBox(5);
        VBox vBox2 = new VBox(5);
        VBox vBox3 = new VBox(5);

        TextField text = new TextField();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        LocalDateTime now = LocalDateTime.now();
        text.setText(dtf.format(now));

        ComboBox<String> combo1 = dropDown();
        ComboBox<String> combo2 = dropDown();
        ComboBox<String> combo3 = dropDown();

        vBox1.getChildren().addAll(labelQuestion1, checkbox1, checkbox2, checkbox3);
        vBox2.getChildren().addAll(labelQuestion2, combo1, combo2, combo3);
        vBox3.getChildren().addAll(labelQuestion3, text);

        layout.getChildren().addAll(vBox1, vBox2, vBox3, searchButton);

        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().add(layout);
        AnchorPane.setLeftAnchor(layout, 15.0);
        AnchorPane.setTopAnchor(layout, 15.0);
        AnchorPane.setBottomAnchor(layout, 15.0);
        AnchorPane.setRightAnchor(layout, 15.0);

        Scene sceneInput = new Scene(anchor);
        primaryStage.setScene(sceneInput);

        primaryStage.show();


        searchButton.setOnAction(e -> {
            String chooseCinema = "";
            if (checkbox1.isSelected()) {
                chooseCinema = chooseCinema + "Apollo Kino, ";
            }
            if (checkbox2.isSelected()) {
                chooseCinema = chooseCinema + "Cinamon, ";
            }
            if (checkbox3.isSelected()) {
                chooseCinema = chooseCinema + "Forum Cinemas, ";
            }


            String chooseGenre = combo1.getValue() + ", " + combo2.getValue() + ", " + combo3.getValue();
            String chooseDate = text.getText();

            if (!chooseDate.matches("\\d{2}.\\d{2}.\\d{4}")) {
                throw new WrongDateException(primaryStage);
            }

            InputFilm inputFilm = new InputFilm(chooseCinema.replaceAll(", $", ""), chooseDate, chooseGenre);

            createOutputWindow(primaryStage, inputFilm);

        });

    }

    public static ComboBox<String> dropDown() {
        String[] genres = {"üllata mind", "animatsioon", "komöödia", "draama", "seiklus", "põnevik", "märul", "kogupere", "fantaasia", "õudus", "sõda", "thriller", "ooper", "biograafia", "ballett", "krimi", "romantika", "muusika", "ulmefilm", "müsteerium", "teater"};
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(genres));
        comboBox.getSelectionModel().select(0);
        return comboBox;
    }

    public static void createOutputWindow(Stage primaryStage, InputFilm inputFilm) {
        primaryStage.setTitle("Otsingu tulemused");
        List<DataFilm> suitableFilms;
        try {
            List<DataFilm> filmsFromChosenCinemas = CheckCinemas.getFilms(inputFilm);
            List<DataFilm> filmsWithSuitableDates = CheckDates.findFilmsWithSuitableDates(filmsFromChosenCinemas, inputFilm);
            suitableFilms = CheckGenres.findFilmsWithSuitableGenres(filmsWithSuitableDates, inputFilm);
        } catch (Exception e) {
            primaryStage.hide();
            throw new DataException();
        }

        BorderPane borderPaneResults = new BorderPane();

        VBox inputInfo = new VBox(7);
        Label inputInf = new Label("Sisestatud info:");
        inputInf.setFont(Font.font(15));

        Label inputCinema = new Label("Kino: " + inputFilm.getCinemaName());
        Label inputDate = new Label("Kuupäev: " + inputFilm.getFilmDate());
        Label inputGenre = new Label("Žanrid: " + inputFilm.getFilmGenre());

        inputInfo.getChildren().addAll(inputInf, inputCinema, inputDate, inputGenre);
        inputInfo.setAlignment(Pos.CENTER);

        VBox films = new VBox(7);
        Label searchResults = new Label("Leidsin filmid:");
        searchResults.setFont(Font.font(15));
        films.getChildren().add(searchResults);
        if (suitableFilms.size() == 0) {
            Label noSuitableFilms = new Label("Kahjuks sobivaid filme ei leitud. Proovi uuesti!");
            films.getChildren().add(noSuitableFilms);
        }
        for (DataFilm dataFilm : suitableFilms) {
            Label filmInfo = new Label(dataFilm.toString());
            films.getChildren().add(filmInfo);
        }
        films.setAlignment(Pos.CENTER);

        AnchorPane anchorPaneFilms = new AnchorPane(films);
        AnchorPane.setLeftAnchor(films, 20.0);
        AnchorPane.setRightAnchor(films, 20.0);


        VBox options = new VBox(5);

        Label endProgram = new Label("Lõpetamiseks vajuta X!");
        Label continueProgram = new Label("Uue otsingu tegemiseks vajuta U!");
        endProgram.setStyle("-fx-font-weight: bold");
        continueProgram.setStyle("-fx-font-weight: bold");
        endProgram.setFont(Font.font(15));
        continueProgram.setFont(Font.font(15));

        options.setAlignment(Pos.CENTER);
        options.getChildren().addAll(continueProgram, endProgram);


        VBox vBoxBig = new VBox(inputInfo, anchorPaneFilms, options);
        vBoxBig.setSpacing(40);
        borderPaneResults.setCenter(vBoxBig);


        Scene sceneResults = new Scene(borderPaneResults);
        primaryStage.setScene(sceneResults);
        borderPaneResults.prefWidthProperty().bind(sceneResults.widthProperty());
        borderPaneResults.prefHeightProperty().bind(sceneResults.heightProperty());
        primaryStage.show();

        sceneResults.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.X) {
                Platform.exit();
            }
            if (keyEvent.getCode() == KeyCode.U) {
                createInputWindow(primaryStage);
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Scene sceneWelcome = createWelcomeWindow(primaryStage);
        primaryStage.setScene(sceneWelcome);
        primaryStage.show();

    }
}
