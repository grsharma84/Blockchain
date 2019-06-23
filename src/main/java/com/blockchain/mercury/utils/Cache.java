package com.blockchain.mercury.utils;

import com.blockchain.mercury.enums.Currency;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Cache {

    int MAX_CACHE_SIZE = 300;
    LRUCache<Integer, HashMap<Currency, Double>> cache;

    Cache() {
        cache = new LRUCache<>(MAX_CACHE_SIZE);
    }

    public HashMap<Currency, Double> get(int key) {
        return cache.get(key);
    }

    public void put(int key, HashMap<Currency, Double> value) {
        cache.put(key, value);
    }

    public void evict(int key){
        cache.evict(key);
    }

}
