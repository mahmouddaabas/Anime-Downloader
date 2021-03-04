package com.anime.downloader.main;


import com.anime.downloader.gui.MainView;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Application.launch(MainView.class, args);
    }
}
