package com.anime.downloader.gui;

import com.anime.downloader.api.API;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    }

    public void episodeListClicked() throws URISyntaxException, IOException {

        System.out.println(listViewEpisodes.getSelectionModel().getSelectedItem());
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
