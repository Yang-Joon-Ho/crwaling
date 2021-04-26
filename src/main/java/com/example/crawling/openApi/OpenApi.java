package com.example.crawling.openApi;

public interface OpenApi {
    String getQuery(String searchFilter, String searchWord);
    String getUrl();
}
