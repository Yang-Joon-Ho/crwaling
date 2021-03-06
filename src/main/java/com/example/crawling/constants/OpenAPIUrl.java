package com.example.crawling.constants;

public enum OpenAPIUrl implements EnumMapperType{

    NAVER_API_JSON("https://openapi.naver.com/v1/search/shop?query="),
    NAVER_SHOPPING("https://search.shopping.naver.com");

    private String url;

    OpenAPIUrl(String url) {this.url = url;}

    @Override
    public String getCode() {
        return url;
    }
}
