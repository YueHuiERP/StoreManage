package com.rehoshi.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static <K, V> Map<K, V> byPair(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
