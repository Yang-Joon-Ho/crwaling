package com.example.crawling.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TextEncodeUtil {
    public TextEncodeUtil() {
    }

    public static String textEncode(String searchWord) {
        String text = "";
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("search encoding fail.", e);
        }

        return text;
    }
}
