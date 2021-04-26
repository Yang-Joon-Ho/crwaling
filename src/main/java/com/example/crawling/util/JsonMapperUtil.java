package com.example.crawling.util;

import com.example.crawling.Dto.ItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Collections;

//제네릭 클래스
/*
* 제네릭 클래스로 선언하면 객체 생성할 때, List, 클래스 등 다양한 형태로 생성할 수 있다.
* */
public class JsonMapperUtil <T>{

    private static final Gson gson = new Gson();

    public List<ItemDto> getDataByKey(String jsonString, String itemKey) throws JsonSyntaxException, JsonProcessingException {

        /*
        * 매개변수로 받아온 itemkey는 "items"라는 문자열이다.
        * 네이버 api를 통해 받아온 데이터인 jsonString에서 여러가지 키 값이 존재하는데
        * 그 중, 키 "items"에 대한 값을 가져오기위한 문자열이다.
        * */

        if (jsonString == null || jsonString.isEmpty()) {
            return Collections.emptyList();
        }

        //gson.from
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        if(jsonObject.isJsonNull() || !jsonObject.isJsonObject()) {
            return Collections.emptyList();
        }

        //jasonObject의 메소드인 get()을 통해 키 "items"에 해당하는 값을 가져온다.
        // ex) "items" : {"명칭":"자이언트", "재질":"카본", "가격":"450000원"}
        JsonArray items = (JsonArray) jsonObject.get(itemKey);

        if(items == null || items.isJsonNull()) {
            return Collections.emptyList();
        }

        ////////////////////test
//        System.out.println("items : " + items);
        ////////////////////

        //items에는 productType과 productId가 다 들어가있다.

        //typeToken 저렇게 쓰는 이유는 접근제어자가 protected라서 사용하려면 익명 클래스를 만들어야 하기 때문.
        List<ItemDto> jsonList = gson.fromJson(items.getAsJsonArray().toString(), new TypeToken <List<ItemDto>>() {}.getType());
        //원래는 두 번째 인자가 new TypeToken<List<ItemDto>>(){}.getType() 이렇게 되어있었는데, 왜 그래야하는지 몰라서 그냥 List로 했다.
        //list로 주면 Json형태의 문자열이 List<ItemDto>로 제대로 된 변환이 이루어지지 않는다.

        return jsonList;

    }

}
