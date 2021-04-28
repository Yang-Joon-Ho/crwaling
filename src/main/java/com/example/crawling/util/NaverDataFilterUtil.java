package com.example.crawling.util;

import com.example.crawling.Dto.ItemDto;

import java.util.stream.Collectors;
import java.util.List;

public class NaverDataFilterUtil {
    public NaverDataFilterUtil() {
    }

    public static List<ItemDto> itemFilter(List<ItemDto> naverItemList, String mallName, String category) {

        return naverItemList
                .stream()
                .filter(item -> !item.isEmpty() &&
                        item.isNormalProduct() &&
                        mallName.equals(item.getMallName()) &&
                        category.equals(item.getCategory1())
                ).collect(Collectors.toList());
    }
}
