package com.rehoshi.util;

import java.util.*;
import java.util.function.Function;

public class CollectionUtil {
    public interface Foreach<T>{
        void each(T data, int index);
    }

    public interface Customer<T> {
        void custom(T data);
    }

    public interface Mapper<T, R> {
        R map(T data);
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> void foreach(Collection<T> collection, Customer<T> customer) {
        if (!isNullOrEmpty(collection)) {
            for (T data : collection) {
                customer.custom(data);
            }
        }
    }
    public static <T> void foreach(Collection<T> collection, Foreach<T> foreach) {
        if (!isNullOrEmpty(collection)) {
            int index = 0;
            for (T data : collection) {
                foreach.each(data, index++);
            }
        }
    }

    public static <T, R> List<R> map(Collection<T> collection, Mapper<T, R> mapper) {
        List<R> list = new ArrayList<>();
        if (!isNullOrEmpty(collection)) {
            for (T data : collection) {
                R map = mapper.map(data);
                list.add(map);
            }
        }
        return list;
    }
    public static <K, R> Map<K, List<R>> group(Collection<R> collection, Mapper<R, K> groupBy) {
        Map<K, List<R>> group  = new HashMap<>() ;

        foreach(collection, data -> {
            K key = groupBy.map(data);
            List<R> list = group.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(data) ;
        });

        return group ;
    }
    public static <T> List<T> find(Collection<T> collection, Function<T, Boolean> filter) {
        List<T> list = new ArrayList<>() ;
        CollectionUtil.foreach(collection, data -> {
            if(filter.apply(data)){
                list.add(data) ;
            }
        });
        return list;
    }
}
