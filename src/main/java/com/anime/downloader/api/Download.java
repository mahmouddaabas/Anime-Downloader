package com.anime.downloader.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.anime.downloader.gui.Browse;

//This class is used to download the files.
//It downloads the files in a background thread.
//The thread is started with new Thread(new Download(getDirectDLink(), new File(path))).start();

@SuppressWarnings("deprecation")
public class Download implements Runnable {
	
	public static int percentageDownloaded;
	public double fileSize;
	public double downloaded;
	private String url;
	private File path;
	
	public Download(String url, File path) throws IOException {
		this.url = url;
		this.path = path;
	}

	@Override
	public void run() {
		
		try {
		URL downloadUrl = new URL(url);
        HttpURLConnection http = (HttpURLConnection) downloadUrl.openConnection();
        fileSize = (double) http.getContentLength();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
        byte[] data = new byte[1024];
        downloaded = 0.00;
        int read = 0;
        
		Browse b = new Browse();
        while ((read = in.read(data, 0, 1024)) >= 0) {
            bout.write(data, 0, read);
            downloaded += read;

            //Calculates download progress.
            percentageDownloaded = (int) ((downloaded*100)/fileSize);
            System.out.println(percentageDownloaded);

        }       
        bout.close();
        in.close();
        System.out.println("Download complete.");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
