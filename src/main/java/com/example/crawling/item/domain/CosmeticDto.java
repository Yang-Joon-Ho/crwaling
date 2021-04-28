package com.example.crawling.item.domain;

import com.example.crawling.Dto.ItemDto;
import com.example.crawling.constants.NaverCosmeticItemFeature;
import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CosmeticDto {

    private String skinType;
    private String shape;
    private String useArea;
    private String volume;
    private String mainFeature;
    private String detailFeature;
    private String useTime;
    private String paRating;
    private String sunBlockRating;
    private String color;
    private String effect;
    private String type;
    private List<String[]> categories;
    private List<Ingredient> ingredient;

    //성분 추가
    public CosmeticDto setData(List<Ingredient> ingredientList) {
        for(Ingredient ingredient : ingredientList) {
            this.ingredient.add(ingredient);
        }
        return this;
    }

    public CosmeticDto(List<String[]> parsedCategory, List<Map<String, String>> parsedDetail) {
        this.categories = parsedCategory;

        //item = parsedDetail의 하나
        parsedDetail.stream().forEach(item -> createCosmetic(item));
    }

    //parsedDetail을 통해서 Cosmetic 생성
    //CosmeticDto를 반환하는데 어떤 변수가 받는건지 모르겠다.
    //여기서는 return을 해야 할 이유를 모르겠는데 다른 클래스에서
    //반환해야 할 이유가 있나보다.
    public CosmeticDto createCosmetic(Map<String, String> cosmetic) {
        for (String itemKey : cosmetic.keySet()) {
            this.setData(itemKey, cosmetic.get(itemKey));
        }
        return this;
    }

    public CosmeticDto setData(String itemKey, String itemValue) {

        if (NaverCosmeticItemFeature.MAIN_FEATURE.getCode().equals(itemKey)) {
            this.setMainFeature(itemValue);
        }
        if (NaverCosmeticItemFeature.SKIN_TYPE.getCode().equals(itemKey)) {
            this.setSkinType(itemValue);
        }
        if (NaverCosmeticItemFeature.SHAPE.getCode().equals(itemKey)) {
            this.setShape(itemValue);
        }
        if (NaverCosmeticItemFeature.VOLUME.getCode().equals(itemKey)) {
            this.setVolume(itemValue);
        }
        if (NaverCosmeticItemFeature.USE_AREA.getCode().equals(itemKey)) {
            this.setUseArea(itemValue);
        }
        if (NaverCosmeticItemFeature.PA_RATING.getCode().equals(itemKey)) {
            this.setPaRating(itemValue);
        }
        if (NaverCosmeticItemFeature.SUN_BLOCK_RATING.getCode().equals(itemKey)) {
            this.setSunBlockRating(itemValue);
        }
        if (NaverCosmeticItemFeature.COLOR.getCode().equals(itemKey)) {
            this.setColor(itemValue);
        }
        if (NaverCosmeticItemFeature.EFFECT.getCode().equals(itemKey)) {
            this.setEffect(itemValue);
        }
        if (NaverCosmeticItemFeature.TYPE.getCode().equals(itemKey)) {
            this.setType(itemValue);
        }
        if (NaverCosmeticItemFeature.DETAIL_FEATURE.getCode().equals(itemKey)) {
            this.setDetailFeature(itemValue);
        }
        return this;
    }

    public Cosmetic toEntity(ItemDto itemDto){
        return Cosmetic.builder()
                .productId(itemDto.getProductId())
                .title(itemDto.getTitle())
                .lprice(itemDto.getLprice())
                .hprice(itemDto.getHprice())
                .brand(itemDto.getBrand())
                .maker(itemDto.getMaker())
                .image(itemDto.getImage())
                .link(itemDto.getLink())
                .skinType(skinType)
                .shape(shape)
                .volume(volume)
                .mainFeature(mainFeature)
                .detailFeature(detailFeature)
                .useTime(useTime)
                .paRating(paRating)
                .sunBlockRating(sunBlockRating)
                .color(color)
                .effect(effect)
                .type(type)
                .build();
    }
}
