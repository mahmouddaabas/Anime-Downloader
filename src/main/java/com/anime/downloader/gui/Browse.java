package com.anime.downloader.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Browse {

    @FXML
    private ListView<String> listViewTitles;

    public Browse() {
        listViewTitles = new ListView<>();
    }

    public void poplist() {
        listViewTitles.getItems().addAll("Jax", "Irelia", "Nasus");
    }


}
