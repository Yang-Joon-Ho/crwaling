package com.example.crawling.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
    private JsoupUtil() {}

    public static Document getDocByUrl(String url) {
        Document doc = new Document("");

        /////////////////////
//        System.out.println("url : " + url);
        /////////////////////

        try {
            doc = Jsoup.connect(url).timeout(10*1000).get();
        } catch (Exception e) {
            System.out.println(url);
            e.printStackTrace();
            reGetDocByUrl(url);
        }
        return doc;
    }

    public static Document reGetDocByUrl(String url) {
        Document doc = new Document("");
        try {
            //api 접근 횟수 제한때문에 sleep
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doc = Jsoup.connect(url).timeout(10*1000).get();
        } catch (Exception e) {
            System.out.printf(url);
            e.printStackTrace();
        }
        return doc;
    }
}
