package com.example.crawling.item.service;

public interface ItemService<T1, T2> {
    T1 save(T2 data);
}
