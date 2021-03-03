package com.anime.downloader.gui;

import com.anime.downloader.api.API;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class Browse {

    ObservableList oblist = FXCollections.observableArrayList();
    @FXML
    private ListView<String> listViewTitles;
    @FXML
    private Label browseLabel;
    @FXML
    private ListView<String> listViewEpisodes;
    @FXML
    private TextField searchText;
    //Used by the refreshlist method to only use it one time.
    private boolean alreadyExecuted;
    @FXML
    private TextArea descriptionText;
    @FXML
    private ImageView animeImageView;
    @FXML
    private Label countryLabel, statusLabel, releasedLabel, genreLabel;

    public Browse() throws IOException {
        oblist = FXCollections.observableArrayList();
        listViewTitles = new ListView<>();
    }

    public void populateFromKeywordTitles() throws IOException {
        //Removes the empty string added in the refresh list method to initialize the list.
        //Clears the list.
        listViewTitles.getItems().clear();
        oblist.clear();
        API api = new API();
        api.setKeyword(searchText.getText());
        api.getSearchedList();
        oblist.addAll(api.getSearchedList());
        listViewTitles.getItems().addAll(oblist);
        listViewTitles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void getSelectedInTitlesList() throws IOException {
        listViewEpisodes.getItems().clear();
        String selected = listViewTitles.getSelectionModel().getSelectedItem().replace("\n", "");
        API api = new API();
        api.setSelectedItem(selected);
        listViewEpisodes.getItems().addAll(api.getEpisodeList());

        //Sets description text and labels.
        descriptionText.setText(api.getSynopsis());
        countryLabel.setText(api.getCountry());
        statusLabel.setText(api.getStatus());
        releasedLabel.setText(api.getReleased());
        genreLabel.setText(api.getGenre());

        //Set the image in the ImageView from a URL that is delivered from the API.
        String path = api.getImageLink();
        BufferedImage image;
        URL url = new URL(path);
        image = ImageIO.read(url);
        Image img = SwingFXUtils.toFXImage(image, null);
        animeImageView.setPreserveRatio(false);
        animeImageView.setFitWidth(170);
        animeImageView.setFitHeight(158);
        animeImageView.setImage(img);

    }

    public void episodeListClicked() throws URISyntaxException, IOException {

        System.out.println(listViewEpisodes.getSelectionModel().getSelectedItem());
        //Selects first in list so image is not null.
        listViewEpisodes.getSelectionModel().getSelectedItem();

        //Creates an alertbox.
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("What would you like to do?");
        ButtonType watch = new ButtonType("Watch", ButtonBar.ButtonData.YES);
        ButtonType download = new ButtonType("Download", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().addAll(watch, download, cancel);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get().getText() == "Watch") {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(listViewEpisodes.getSelectionModel().getSelectedItem()));
            }
        }

        else if(result.get().getText() == "OK_DONE") {
            System.out.println("Not implemented yet.");
        }
        else {
            System.out.println("Doing nothing.");
        }


    }

    public void handleLoadButton() throws IOException {
        populateFromKeywordTitles();
        //popListWithNewestTitles();
    }

    //Calls method when pane is opened to populate the list.
    public void refreshList() throws IOException {
        //Boolean is an instance variable so it doesnt reset everytime the method is run.
        //Runs code only once so it doesnt spam add the list.
        if(!alreadyExecuted) {
            API api = new API();
            //listViewTitles.getItems().add("");
            listViewTitles.getItems().addAll(api.getPopularList());
            listViewEpisodes.getItems().add("");
            alreadyExecuted = true;
        }
    }

}
