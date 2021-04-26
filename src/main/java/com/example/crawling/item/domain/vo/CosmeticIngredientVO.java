package com.example.crawling.item.domain.vo;

import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import lombok.Getter;

import java.util.List;

@Getter
public class CosmeticIngredientVO {
    private final List<Ingredient> ingredients;

    public CosmeticIngredientVO(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
