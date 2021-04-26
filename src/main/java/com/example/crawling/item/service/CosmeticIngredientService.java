package com.example.crawling.item.service;

import com.example.crawling.item.domain.cosmetic.CosmeticIngredient;
import com.example.crawling.item.domain.cosmetic.CosmeticIngredientRepository;
import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import com.example.crawling.item.domain.vo.CosmeticIngredientVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CosmeticIngredientService implements ItemService<List<CosmeticIngredient>, CosmeticIngredientVO>{
    private final CosmeticIngredientRepository cosmeticIngredientRepository;

    @Override
    public List<CosmeticIngredient> save(CosmeticIngredientVO cosmeticIngredientVO) {
        List<Ingredient> ingredients = cosmeticIngredientVO.getIngredients();
        List<CosmeticIngredient> cosmeticIngredients = new ArrayList<>();

        for(Ingredient ingredient : ingredients) {
            cosmeticIngredients.add(CosmeticIngredient.builder()
            .ingredient(ingredient)
            .build());
        }
        return cosmeticIngredients;
    }
}
