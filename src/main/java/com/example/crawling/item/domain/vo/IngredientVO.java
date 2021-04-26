package com.example.crawling.item.domain.vo;

import com.example.crawling.item.domain.cosmetic.ingredient.Feature;
import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class IngredientVO {
    private Map<Ingredient, List<Feature>> ingredientListMap;

    public IngredientVO(Map<Ingredient, List<Feature>> ingredientListMap) {
        this.ingredientListMap = ingredientListMap;
    }
}
