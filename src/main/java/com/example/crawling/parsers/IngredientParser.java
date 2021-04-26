package com.example.crawling.parsers;

import com.example.crawling.item.domain.cosmetic.ingredient.Feature;
import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class IngredientParser <T> implements Parser<T>{

    private Map<Ingredient, List<Feature>> ingredientFeatureMap = new LinkedHashMap<>();

    public T parse(Document html) {

//        System.out.println("html : " + html);

        ingredientFeatureMap.clear();
        //select()를 통해서 원하는 내용을 Elements 클래스에 담는다.
        Elements ingredientEle = html.select("li[class^=analysisIngredient_ingredient]");
        if (ingredientEle == null) {
            return (T) ingredientFeatureMap;
        }

        //성분 정보 입력
        for (int i = 0, ingredientSize = ingredientEle.size(); i < ingredientSize; i++) {
            Elements titleEle = ingredientEle.get(i).select("div[class^=analysisIngredient_ingredient_title]");
            Elements featureEle = ingredientEle.get(i).select("div[class^=analysisIngredient_feature] div");
            Ingredient ingredient = Ingredient.builder()
                    .name(titleEle.text())
                    .build();

            //성분 특징 리스트
            List<Feature> featureList = new ArrayList<>();
            for (int j = 0, featureSize = featureEle.size(); j < featureSize; j++) {
                String gOrWClass = featureEle.get(j).attr("class").toString();
                Feature.Status status = gOrWClass.contains("good") ? Feature.Status.Good : Feature.Status.Warning;
                Feature feature = Feature.builder()
                        .name(featureEle.get(j).text())
                        .status(status)
                        .build();
                featureList.add(feature);
            }
            ingredientFeatureMap.put(ingredient, featureList);
        }

        return (T) ingredientFeatureMap;
    }
}
