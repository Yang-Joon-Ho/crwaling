package com.example.crawling.item.domain.vo;

import com.example.crawling.item.domain.Cosmetic;
import com.example.crawling.item.domain.cosmetic.CosmeticIngredient;
import lombok.Getter;

import java.util.List;

@Getter
public class CosmeticVO {
    private Cosmetic cosmetic;
    private List<CosmeticIngredient> cosmeticIngredients;

    public CosmeticVO(Cosmetic cosmetic, List<CosmeticIngredient> cosmeticIngredients) {
        this.cosmetic = cosmetic;
        this.cosmeticIngredients = cosmeticIngredients;
    }
}
