package com.example.crawling.item.service;

import com.example.crawling.Dto.Category;
import com.example.crawling.Dto.Item;
import com.example.crawling.item.api.dto.ItemResponseDto;
import com.example.crawling.item.domain.CategoryRepository;
import com.example.crawling.item.domain.ItemRepository;
import com.example.crawling.item.domain.query.ItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemRepository itemRepository;
    private final ItemQueryRepository itemQueryRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List <ItemResponseDto> findItems() {
        return itemRepository.findItems()
                .stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findItemById(Long itemId) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if(findItem.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 상품입니다.");
        }
        return ItemResponseDto.from(findItem.get());
    }

    //카테고리로 조회
    @Transactional(readOnly = true)
    public List<ItemResponseDto> findItemsByItemCategoryId(String itemCategoryId) {
        List<Category> subCategories = findSubCategoriesByItemCategoryId(itemCategoryId);

        //하위 카테고리에 해당하는 상품을 조회한다.
        return itemRepository.findItemsByCategoryInSubCategories(subCategories)
                .stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findItemsPageByItemCategoryId(String itemCategoryId, int offset, int limit) {
        List<Category> subCategories = findSubCategoriesByItemCategoryId(itemCategoryId);
        return itemQueryRepository.findItemsPageByCategoryInSubCategories(subCategories, offset, limit)
                .stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    private List<Category> findSubCategoriesByItemCategoryId(String itemCategoryId) {
        Optional<Category> findCategory = categoryRepository.findCategoryByItemCategoryId(itemCategoryId);
        if(findCategory.isEmpty()) {
            throw new NoSuchElementException("카테고리 정보가 존재하지 않습니다.");
        }

        //찾으려는 카테고리가 자식을 가지면 자식을 가지지 앟ㄴ는 하위 카테고리를 조회한다
        Category category = findCategory.get();

        //자식이 있는 카테고리이면 자식 카테고리 반환
        if(category.getChild().size() > 0) {
            return getSubCategories(new ArrayList<>(), category.getChild());
        }

        //찾으려는 카테고리가 자식을 가지지 않으면 해당 카테고리 반환
        return Arrays.asList(category);
     }

     private List<Category> getSubCategories(List<Category> subCategories, List<Category> child) {
         child.stream().forEach(category -> {
             //하위 카테고리가 있다면 재귀
             if(category.getChild().size() > 0) {
                 getSubCategories(subCategories, category.getChild());
             } else {
                 subCategories.add(category);
             }
         });
         return subCategories;
     }

     @Transactional(readOnly = true)
    public List <ItemResponseDto> findItemsByTitleContains(String itemName) {
        List<Item> items = itemRepository.findTop100ByTitleContains(itemName);
        return items.stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
     }

     @Transactional (readOnly = true)
    public List <ItemResponseDto> findItemsPage(int offset, int limit) {
        return itemQueryRepository.findItemsPage(offset, limit)
                .stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }
}
