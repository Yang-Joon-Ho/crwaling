package com.example.crwaling;

import com.example.crwaling.openApi.NaverOpenApi;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Component
public class NaverCosmeticCrwaler {

//    private final Map<String, ItemService> itemService;
//
//    public NaverCosmeticCrwaler(Map<String, ItemService> itemService) {
//        this.itemService = itemService;
//    }

    @Scheduled(cron = "*/1 * * * * *")
    public void callCrawling() {

        //////////////test
        System.out.println("in callCrawling");
        //////////////

        String searchWord = "자전거";

        String[] headerKey = new String[] {"wscYGh5pC6OYmPwTC_SQ", "0l2zINy5mT"};

        int i = 1;
        while((i += 10) <= 1000) {
            String queryParameter = "&display=10&start=" + String.valueOf(i);
            crawling(queryParameter, searchWord, headerKey[0], headerKey[1]);
        }
    }

    public void crawling (String queryParameter, String searchWord, String clientId, String clientSecret) {

        //////////////////test
        System.out.println("queryParameter : " + queryParameter);
        System.out.println("searchWord : " + searchWord);
        System.out.println("clientId : " + clientId);
        System.out.println("clientSecret : " + clientSecret);
        /////////////////

        NaverOpenApi naverOpenApi = new NaverOpenApi();
        String responseBody = naverOpenApi.getData(clientId, clientSecret, queryParameter, searchWord);

        ///////////////test
        System.out.println("in crwaling, responsebody : " + responseBody);
        //////////////
    }
}
