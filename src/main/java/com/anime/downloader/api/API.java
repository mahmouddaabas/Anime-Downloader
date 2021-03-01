package com.anime.downloader.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class API {

    private List<String> titleList = new ArrayList<String>();

    public API() throws IOException {
        getInfoList();
    }

    public void getInfoList() throws IOException {

        String url = "https://www13.9anime.to/search?keyword=conan";
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

            titleList.add("\n" + title);
        }
        System.out.println("Added to list");
        //System.out.println(titleList);
    }

    public List<String> getListWithTitles(){
        return this.titleList;
    }
}
