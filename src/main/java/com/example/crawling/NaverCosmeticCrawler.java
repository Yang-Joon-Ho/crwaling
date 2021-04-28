package com.example.crawling;

import com.example.crawling.Dto.*;
import com.example.crawling.constants.NaverFilterCondition;
import com.example.crawling.item.domain.Category;
import com.example.crawling.item.domain.Cosmetic;
import com.example.crawling.item.domain.CosmeticDto;
import com.example.crawling.item.domain.Reputation;
import com.example.crawling.item.domain.cosmetic.CosmeticIngredient;
import com.example.crawling.item.domain.cosmetic.ingredient.Feature;
import com.example.crawling.item.domain.cosmetic.ingredient.Ingredient;
import com.example.crawling.item.domain.cosmetic.review.Review;
import com.example.crawling.item.domain.vo.*;
import com.example.crawling.item.service.ItemService;
import com.example.crawling.openApi.NaverOpenApi;
import com.example.crawling.parsers.*;
import com.example.crawling.util.JsonMapperUtil;
import com.example.crawling.util.JsoupUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.crawling.util.NaverDataFilterUtil.itemFilter;

@ComponentScan(basePackages={"com.example.crawling"})
@Component
public class NaverCosmeticCrawler {

    private final IngredientParser<Map<Ingredient, List<Feature>>> ingredientParser = new IngredientParser<>();
    private final DetailParser<CosmeticDto> detailParser = new DetailParser<>();
    private final ReviewParser<List<Review>> reviewParser = new ReviewParser<>();
    private final ReputationParser<Reputation> reputationParser = new ReputationParser<>();
    private final Map<String, ItemService> itemService;

    public NaverCosmeticCrawler(Map<String, ItemService> itemService) {
        this.itemService = itemService;
    }

    //3초마다 한번 씩 실행
    @Scheduled(cron = "*/3 * * * * *")
    public void callCrawling() {

        //////////////test
        System.out.println("in callCrawling");
        //////////////

        String searchWord = "영양제";

        String[] headerKey = new String[] {"wscYGh5pC6OYmPwTC_SQ", "0l2zINy5mT"};

        long before = System.currentTimeMillis();
        for(int i = 1; i <= 1000; i += 10) {
            //i는 가져올 품목의 개수
            //display=10 이건 한번에 10개 가져오겠다는 얘기, start="숫자" 몇번째부터 가져올지
            //display=10, i += 10이면 물품 리스트에서 1부터 10까지 가져오고 그다음 11부터 21까지 ... 이런식으로 데이터를 가져온다.
            String queryParameter = "&display=10&start=" + String.valueOf(i);
            crawling(queryParameter, searchWord, headerKey[0], headerKey[1]);
        }
        long after = System.currentTimeMillis();
        long result = after - before;

    }

    public void crawling (String queryParameter, String searchWord, String clientId, String clientSecret) {

        NaverOpenApi naverOpenApi = new NaverOpenApi();
        String responseBody = naverOpenApi.getData(clientId, clientSecret, queryParameter, searchWord);

        JsonMapperUtil jsonMapper = new JsonMapperUtil <List<ItemDto>>();
        List<ItemDto> list = null;

        try {
            list = jsonMapper.getDataByKey(responseBody, "items");
        } catch (JsonProcessingException e) {
        }

        //////////////
        //////////////
        //cannot filtering
        List<ItemDto> filteredList = itemFilter(list, NaverFilterCondition.NAVER_MALL_NAME.getCode(),
                NaverFilterCondition.NAVER_CATEGORY.getCode());

        //parser를 통해 상품 자세히 보기 링크 itemDto에 저장
        NaverParser naverParser = new NaverParser();
        List<ItemDto> naverItems = filteredList.stream().map(item -> {
            item.setLink(naverParser.getRedirectedLink(item));
            return item;
        }).collect(Collectors.toList());

        ///////////////////////여기까지가 itemDto 양식에 맞게 데이터를 가공하는거고
        ///////////////////////아래부터 DB에 저장한다.

        for (ItemDto itemDto : naverItems) {
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            save(itemDto);
        }
    }

    @Transactional
    public void save (ItemDto itemDto) {
        //html 파일 가져옴
        Document doc = JsoupUtil.getDocByUrl(itemDto.getLink());

        if(doc == null) return;

        ////////////////////
//        System.out.println("itemService : " + itemService + ", reputationService : " + itemService.get("reputationService").toString());
        ////////////////////

        //리뷰 & 평점 저장
        Reputation reputation = (Reputation) itemService.get("reputationService").save(
                new ReputationReviewVO(reputationParser.parse(doc), reviewParser.parse(doc)));

        //상세정보 + item 기본 정보 + 카테고리 + 평점 추가
        CosmeticDto cosmeticDto = detailParser.parse(doc);
        Cosmetic cosmetic = cosmeticDto.toEntity(itemDto);

        //카테고리
        Category category = (Category) itemService.get("categoryService")
                .save(new ItemCategoryVO(cosmeticDto.getCategories()));
        cosmetic.setCategory(category);
        cosmetic.setReputation(reputation);

        //성분, 특징 저장 및 성분-특징 리스트 생성
        List<Ingredient> ingredients = (List<Ingredient>) itemService.get("ingredientService")
                .save(new IngredientVO(ingredientParser.parse(doc)));

        //화장품-성분 리스트 생성
        List<CosmeticIngredient> cosmeticIngredients = (List<CosmeticIngredient>) itemService.get("cosmeticIngredientService")
                .save(new CosmeticIngredientVO(ingredients));

        //화장품 저장
        itemService.get("cosmeticService").save(new CosmeticVO(cosmetic, cosmeticIngredients));

        /////////////////
        System.out.println("저장 완료");
        /////////////////
    }
}
