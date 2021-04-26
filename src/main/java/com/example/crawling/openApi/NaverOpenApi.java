package com.example.crawling.openApi;

import com.example.crawling.constants.OpenAPIUrl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.example.crawling.openApi.Connect.connect;
import static com.example.crawling.util.InputStreamReadUtil.convertInputStreamToString;
import static com.example.crawling.util.TextEncodeUtil.textEncode;

public class NaverOpenApi implements OpenApi{

    public String url;
    public String query;

    public String getData(String clientId, String clientSecret, String searchFilter, String searchWord) {

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        url = getUrl();
        query = getQuery(searchFilter, searchWord);

        ////////////test
//        System.out.println("url : " + url);
        ////////////

        String responseBody = responseBody(url + query, requestHeaders);

        //NaverCosmeticCrwaler로 복귀
        return responseBody;
    }

    private String responseBody(String apiUrl, Map<String, String> requestHeaders) {

        //httpUrlConnection 객체 생성
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            //getResponseCode() 함수를 통해 요청 전송
            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                //request 요청을 보내서 가져온 데이터를 문자열로 변환하여 반환
                return convertInputStreamToString(con.getInputStream());
            } else {
                return convertInputStreamToString(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API request fail.", e);
        } finally {
            con.disconnect();
        }
    }

    @Override
    public String getQuery(String searchFilter, String searchWord) {
        return textEncode(searchWord) + searchFilter;
    }

    @Override
    public String getUrl() {
        //test t = new test();

        return OpenAPIUrl.NAVER_API_JSON.getCode(); //result of json
        //return
    }
}
