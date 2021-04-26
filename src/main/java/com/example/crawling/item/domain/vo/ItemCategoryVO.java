package com.example.crawling.item.domain.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class ItemCategoryVO {

    private List<String[]> itemCategories;

    public ItemCategoryVO(List<String[]> itemCategories) {
        this.itemCategories = itemCategories;
    }
}
