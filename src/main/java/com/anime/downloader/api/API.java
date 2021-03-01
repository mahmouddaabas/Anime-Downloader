package com.anime.downloader.api;

import javafx.scene.control.Hyperlink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class API {

    private List<String> newestList = new ArrayList<String>();
    private List<String> searchedList = new ArrayList<String>();
    private List<String> downloadList = new ArrayList<String>();
    private List<String> episodeList = new ArrayList<String>();
    private String keyword;
    private String selectedItem;
    private String animeLink;

    private String synopsis;

    //Gets information from the anime website.
    public List<String> getNewestList() throws IOException {

        String url = "https://www13.9anime.to/newest";
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("ul.anime-list");
        for(Element element : link.select("li")) {

            String title = element.select("a.name").text();
            System.out.println("\nTitle: " + title);
            String linkT = element.select("a.name").attr("href");
            System.out.println("Link: " + "https://www13.9anime.to" + linkT);
            String episode = element.select("div.tag.ep").text();
            System.out.println("Episode: " + episode);
            String tag = element.select("div.taglist").text();
            System.out.println("Tag: " + tag);
            String image = element.select("img").attr("src");
            System.out.println("Image: " + image);

            newestList.add("\n" + title);
        }
        //System.out.println(titleList);

        return newestList;
    }

    public List<String> getSearchedList() throws IOException {

        String url = "https://www13.9anime.to/search?keyword=" + keyword;
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("ul.anime-list");
        for(Element element : link.select("li")) {

            String title = element.select("a.name").text();
            System.out.println("\nTitle: " + title);
            String linkT = element.select("a.name").attr("href");
            System.out.println("Link: " + "https://www13.9anime.to" + linkT);
            String episode = element.select("div.tag.ep").text();
            System.out.println("Episode: " + episode);
            String tag = element.select("div.taglist").text();
            System.out.println("Tag: " + tag);
            String image = element.select("img").attr("src");
            System.out.println("Image: " + image);

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

        //Gets the synopsis.
        link = doc.select("div.right");
        for(Element element : link.select("p")) {
            synopsis  = element.select("p").text();
            System.out.println("\nSynopsis: " + synopsis);
            break;
        }

        return this.episodeList;

    }


    public List <String> getDownloadLink() throws IOException {
        String url = "https://www7.animeseries.io/watch/detective-conan-episode-999.html";
        Document doc = Jsoup.connect(url).get();
        //Grabs the download link.
        Elements link = doc.select("div.plugins");
        for(Element element : link.select("li")) {
            if(element.select("a").attr("href").contains("//gogo-play")) {
                String downloadLink = element.select("a").attr("href");
                System.out.println("\nDownload_Link: " + "https:" + downloadLink);
            }
        }

        return this.downloadList;
    }

    //Get inserted word from browse textbox.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
