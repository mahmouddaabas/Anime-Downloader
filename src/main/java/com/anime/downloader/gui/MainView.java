package com.anime.downloader.gui;

import com.anime.downloader.api.API;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class MainView extends Application {

    private Parent root;
    private String image;
    @FXML
    private Label browseLabel;


    @Override
    public void start(Stage stage) throws Exception {

        //Sets the FXML file from the resources folder.
        root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));

        //Adds background image to the program. (This works)
        addBackground();

        //Sets the application logo and other default things.
        //stage.getIcons().add(new Image(View.class.getResourceAsStream("images/icon.jpg")));
        stage.setTitle("Anime-Downloader");
        stage.setResizable(true);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void handleBrowse() throws IOException {
        //Populates the list before switching scenes otherwise it wont update.
        Browse b = new Browse();
        b.poplist();
        root = FXMLLoader.load(getClass().getResource("/fxml/Browse.fxml"));
        addBackground();
        browseLabel.getScene().setRoot(root);
    }

    void addBackground() {
        //Sets background image and adjusts the size/placement.
        image = getClass().getResource("/image/background.png").toExternalForm();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;" +
                "-fx-background-size: cover;");
    }
}