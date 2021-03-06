package com.example.crawling.constants;

public enum NaverFilterCondition implements EnumMapperType{
    NAVER_MALL_NAME("네이버"),
    NAVER_CATEGORY("식품");

    private final String filterCondition;

    NaverFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public String getCode() {return filterCondition;}

}
