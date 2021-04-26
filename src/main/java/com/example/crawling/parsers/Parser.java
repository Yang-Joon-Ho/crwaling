package com.example.crawling.parsers;

import org.jsoup.nodes.Document;

public interface Parser <T> {
    public T parse(Document doc);
}
