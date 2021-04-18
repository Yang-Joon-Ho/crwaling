package com.example.crwaling.openApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connect {
    public static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL is wrong. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("connection failed. : " + apiUrl, e);
        }
    }
}
