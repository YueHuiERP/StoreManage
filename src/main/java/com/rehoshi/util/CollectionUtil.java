package com.rehoshi.util;

import com.rehoshi.model.Product;
import com.rehoshi.model.ProductComposition;
import com.rehoshi.model.Stock;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CollectionUtil {

    public interface Foreach<T> {
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

    public static <T, V> void foreach(Collection<T>  cOne,  Collection<V>  cOther, BiConsumer<T, V> customer) {
        if (!isNullOrEmpty(cOne) && !isNullOrEmpty(cOther)) {
            Iterator<T> itOne = cOne.iterator();
            Iterator<V>  itOther = cOther.iterator() ;
            while (itOne.hasNext() && itOther.hasNext()){
                customer.accept(itOne.next(), itOther.next());
            }
        }
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
        Map<K, List<R>> group = new HashMap<>();

        foreach(collection, data -> {
            K key = groupBy.map(data);
            List<R> list = group.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(data);
        });

        return group;
    }

    public static <T> List<T> find(Collection<T> collection, Function<T, Boolean> filter) {
        List<T> list = new ArrayList<>();
        CollectionUtil.foreach(collection, data -> {
            if (filter.apply(data)) {
                list.add(data);
            }
        });
        return list;
    }

    public static <T> T first(Collection<T> collection, Function<T, Boolean> filter) {
        AtomicReference<T> resp = new AtomicReference<>();
        CollectionUtil.foreach(collection, data -> {
            if (filter.apply(data)) {
                resp.set(data);
            }
        });
        return resp.get();
    }

    public static int count(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> String concatString(Collection<T> collection, Function<T, String> mapper, String separator) {
        StringBuilder sb = new StringBuilder();
        foreach(collection, (data, index) -> {
            if (index > 0) {
                sb.append(separator);
            }
            sb.append(mapper.apply(data));
        });
        return sb.toString();
    }

    public static String concatString(T[] array, Function<T, String> mapper, String separator) {
        return concatString(Arrays.asList(array), mapper, separator);
    }

    public static <T, R> R aggregate(Collection<T> collection, BiFunction<T, R, R> mapper, R originValue) {
        R value = originValue ;
        if (!isNullOrEmpty(collection)) {
            for (Iterator<T> iterator = collection.iterator(); iterator.hasNext(); ) {
                T next = iterator.next();
                value = mapper.apply(next, value) ;
            }
        }
        return value ;
    }

    /**
     * 对齐元素数量
     */
    public static <T, V> void alignCount(Collection<T> one, List<V> other, Function<Integer, T> funcOne, Function<Integer, V> funcOther) {

        int oneCount = count(one);
        int otherCount = count(other);
        int alignCount = Math.max(oneCount, otherCount);

        for (int i = oneCount; i < alignCount; i++){
            one.add(funcOne.apply(i)) ;
        }

        for (int i = otherCount; i < alignCount; i++){
            other.add(funcOther.apply(i)) ;
        }
    }
}
