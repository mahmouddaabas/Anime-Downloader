package com.anime.downloader.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


//This class is used to download the files.

public class Download {

    public Download(String url, File path) throws IOException {

        URL downloadUrl = new URL(url);
        HttpURLConnection http = (HttpURLConnection) downloadUrl.openConnection();
        double fileSize = (double) http.getContentLength();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
        byte[] data = new byte[1024];
        double downloaded = 0.00;
        int read = 0;

        while ((read = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, read);
            downloaded += read;

            //Calculates download progress.
            final int percentageDownloaded = (int) ((downloaded*100)/fileSize);
            System.out.println(percentageDownloaded);

        }
        bout.close();
        in.close();
        System.out.println("Download complete.");

    }
}
