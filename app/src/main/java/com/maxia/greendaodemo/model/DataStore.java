package com.maxia.greendaodemo.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kurt on 1/5/16.
 */
public interface DataStore<Key, T> {

    T insert(Key key, T t);

    T load(Key key);

    List<T> loadAll(Collection<Key> keys);

    List<T> updateAll(List<T> values);

    Map<Key, T> updateAll(Map<Key, T> map);

    void remove(Key key);

    void limit(int limit);

    void clear();
}
