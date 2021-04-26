package com.example.crawling.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamReadUtil {

    private InputStreamReadUtil() {
    }

    public static String convertInputStreamToString(InputStream body) {

        /*
        * inputStream : 바이트 기반 입력 스트림의 최상위 클래스로 추상 클래스.
        * 따라서 바이트 단위로 읽는다.
        *
        * 매개변수 body의 경우 네이버 api로부터 가져온 데이터
        * 이 메소드는 결국 api에서 가져온 데이터를 string으로 변환하는 메소드.
        *
        * inputStream은 바이트단위로 읽기 때문에 문자열을 온전히 읽을수가 없다.
        * 따라서 InputStreamReader를 통해 문자를 읽는데, 이를 문자스트림이라 한다.
        *
        * 그리고 bufferedReader로 이를 다시 라인 단위로 읽게되는 것이다.
        *
        * 마지막으로 StringBuilder로 한줄씩 읽은것을 합쳐서 리턴하는 것이다.
        *
        * inputstreamReader를 통해 body의 내용을 하나씩 문자로 읽는다.
        *
        * BufferedReader의 readline()을 통해 데이터를 라인 단위로 읽음.
        *
        * String builder로 한줄 씩 읽어들인 것을 합침.
        *
        * */
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API request reading fail.", e);
        }
    }
}
