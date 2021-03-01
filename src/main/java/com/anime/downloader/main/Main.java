package com.anime.downloader.main;

import com.anime.downloader.api.API;
import com.anime.downloader.gui.MainView;
import javafx.application.Application;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Application.launch(MainView.class, args);
        //API api = new API();
        //api.getEpisodeList();
    }
}
