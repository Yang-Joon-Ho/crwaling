package com.example.crwaling.openApi;

import com.example.crwaling.constants.OpenAPIUrl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static com.example.crwaling.openApi.Connect.connect;
import static com.example.crwaling.util.InputStreamReadUtil.convertInputStreamToString;
import static com.example.crwaling.util.TextEncodeUtil.textEncode;

public class NaverOpenApi implements OpenApi{

    public String url;
    public String query;

    public String getData(String clientId, String clientSecret, String searchFilter, String searchWord) {

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        url = getUrl();
        query = getQuery(searchFilter, searchWord);

        String responseBody = responseBody(url + query, requestHeaders);

        return responseBody;
    }

    private String responseBody(String apiUrl, Map<String, String> requestHeaders) {

        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
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
        return OpenAPIUrl.NAVER_API_JSON.getCode(); //result of json
    }
}
