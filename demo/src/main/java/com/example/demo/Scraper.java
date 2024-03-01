package com.example.demo;
import org.springframework.stereotype.Component;

public interface Scraper {
    void findScore(String productName);
    GeneralProduct getProductInfo();
}
