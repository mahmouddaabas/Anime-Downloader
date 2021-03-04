package com.anime.downloader.api;

import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class API {

    private List<String> newestList = new ArrayList<String>();
    private List<String> searchedList = new ArrayList<String>();
    private List<String> episodeList = new ArrayList<String>();
    private String keyword;
    private String selectedItem;
    private String imageLink;
    private String country;
    private String status;
    private String released;
    private String genre;
    private String synopsis;
    private String downloadLink;
    private String downloadURL;
    private String directDLink;
    private String path;

    //Gets information from the anime website.
    public List<String> getPopularList() throws IOException {

        String url = "https://www7.animeseries.io/popular-anime";
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("ul.items");
        for(Element element : link.select("li")) {

            String title = element.select("div.name").text();
            System.out.println("\nTitle: " + title);
            String linkT = element.select("a").attr("href");
            System.out.println("Link: " + "https://www7.animeseries.io" + linkT);
            /*String episode = element.select("div.tag.ep").text();
            System.out.println("Episode: " + episode);
            String tag = element.select("div.taglist").text();
            System.out.println("Tag: " + tag);
            String image = element.select("div.thumb_anime").attr("href");
            System.out.println("Image: " + image);*/

            newestList.add("\n" + title);
        }
        //System.out.println(titleList);

        return newestList;
    }

    public List<String> getSearchedList() throws IOException {

        String url = "https://www7.animeseries.io/search?keyword=" + keyword;
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("ul.items");
        for(Element element : link.select("li")) {

            String title = element.select("a").text();
            System.out.println("\nTitle: " + title);
            String linkT = element.select("a").attr("href");
            System.out.println("Link: " + "https://www7.animeseries.io" + linkT);
            /*String episode = element.select("div.tag.ep").text();
            System.out.println("Episode: " + episode);
            String tag = element.select("div.taglist").text();
            System.out.println("Tag: " + tag);
            String image = element.select("img").attr("src");
            System.out.println("Image: " + image);*/

            searchedList.add("\n" + title);
        }
        //System.out.println(titleList);

        return this.searchedList;
    }


    public List<String> getEpisodeList() throws IOException {


        //Gets the selected anime and searches for it by completing the url.
        String firstURL = "https://www7.animeseries.io/search?keyword=" + selectedItem;
        String firstTitle = null;
        String url = null;
        Document firstDoc = Jsoup.connect(firstURL).get();
        Elements firstLink = firstDoc.select("ul.items");
        for(Element firstElement : firstLink.select("li")) {
            firstTitle ="\nAnime found: " +  "https://www7.animeseries.io" + firstElement.select("a").attr("href");
            System.out.println(firstTitle);
            url = "https://www7.animeseries.io" + firstElement.select("a").attr("href");
            break;
        }

        url = url;
        Document doc = Jsoup.connect(url).get();
        //Grabs the episodes.
        Elements link = doc.select("div.list_episode");
        for(Element element : link.select("li")) {
                String episode = element.select("a").attr("href");
                System.out.println("\nEpisode: " + "https://www7.animeseries.io" + episode);
                episodeList.add("https://www7.animeseries.io" + episode);
        }

        //Gets the synopsis for the series.
        link = doc.select("div.right");
        for(Element element : link.select("p")) {
            synopsis  = element.select("p").text();
            System.out.println("\nSynopsis: " + synopsis);
            break;
        }

        //Gets the image link.
        link = doc.select("div.left");
        for(Element element : link.select("img.img-responsive")) {
            imageLink  = element.select("img.img-responsive").attr("src");
            System.out.println("\nImage link: " + imageLink);
            break;
        }

        //Gets the Country, Status, Release year and the Genre.
        link = doc.select("div.right");
        for(Element element : link.select("p.des")) {
            //Only select the things we want.
            if(element.select("p.des").text().contains("Country")) {
                country = element.select("p.des").text().replace("J", " J");
                System.out.println(country);
            }
            if(element.select("p.des").text().contains("Status")) {
                status = element.select("p.des").text();
                System.out.println(status);
            }
            if(element.select("p.des").text().contains("Released")) {
                released = element.select("p.des").text().replace("ed", "ed:");
                System.out.println(released);
            }
            if(element.select("p.des").text().contains("Genre")) {
                genre = element.select("p.des").text();
                System.out.println(genre);
            }
        }

        return this.episodeList;

    }

    public void getDownloadLink() throws IOException {

        String animename;

        //Grabs the initial link from the episode page.
        String url = downloadURL;
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("div.plugins");
        for(Element element : link.select("li")) {
            if(element.select("a").attr("href").contains("//gogo-play")) {
                downloadLink = "https:" + element.select("a").attr("href");
                System.out.println("\nDownload_Link: " + downloadLink);
            }
        }

        //Grabs the anime name.
        url = downloadLink;
        doc = Jsoup.connect(url).get();
        link = doc.select("div.name");
        for(Element element : link.select("div.name")) {
            animename = element.select("div.name").text();
            System.out.println("\nAnime Name: " + animename);
            break;
        }

        //Grabs the first direct link it finds, usually the best quality one.
        url = downloadLink;
        doc = Jsoup.connect(url).get();
        link = doc.select("div.mirror_link");
        for(Element element : link.select("div.dowload")) {
            directDLink = element.select("a").attr("href");
            System.out.println("\nDirect_Download_Link: " + directDLink);
            break;
        }

        //Starts the download.
        Download download = new Download(getDirectDLink(), new File(path));
    }

    //Get inserted word from browse textbox.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //Get selected item from episode list.
    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    //Send synopsis to episode list.
    public String getSynopsis() {
        return synopsis;
    }

    //Send image link to episode list.
    public String getImageLink() {
        return imageLink;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public String getReleased() {
        return released;
    }

    public String getGenre() {
        return genre;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getDirectDLink() {
        return this.directDLink;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
