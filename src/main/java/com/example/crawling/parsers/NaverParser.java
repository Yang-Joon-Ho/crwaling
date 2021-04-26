package com.example.crawling.parsers;

import com.example.crawling.Dto.ItemDto;
import com.example.crawling.constants.OpenAPIUrl;
import com.example.crawling.util.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NaverParser {
    //상품 링크 접속
    public List<Map<String, Object>> getItemLinkDoc(List<ItemDto> itemList) {
        //상품 자세히보기 링크를 setLink하여 Dto에 추가함.
        List<ItemDto> redirectedLinkItemList = itemList.stream().map(item -> {
            item.setLink(getRedirectedLink(item));
            System.out.println("item.getLink : " + item.getLink());
            return item;
        }).collect(Collectors.toList());

        //for문 돌리지 않고
        Map<String, Object> itemDocMap = new HashMap<>();
        List<Map<String, Object>> itemDocList = redirectedLinkItemList.stream().map(item -> {
            Document doc = JsoupUtil.getDocByUrl(item.getLink());
            itemDocMap.put("productId", item.getProductId());
            itemDocMap.put("doc", doc);
            return itemDocMap;
        }).collect(Collectors.toList());

        //상품 자세히보기 링크 들어갔을 때 나오는 html파일 리스트
        return itemDocList;
    }

    //상품 링크 가져옴
    //doc 내용 분석.
    public String getRedirectedLink(ItemDto item) {
        String url = OpenAPIUrl.NAVER_SHOPPING.getCode();
        String itemLink = url +
                JsoupUtil.getDocByUrl(item.getLink())
                        .getElementsByTag("script")
                        .toString()
                        .split("'")[1];
        return itemLink;
    }
}

