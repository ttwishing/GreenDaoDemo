package com.maxia.greendaodemo.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by kurt on 1/5/16.
 */
public interface DataStore<Key, T> {

    //插入
    T insert(Key key, T t);

    //获取
    T load(Key key);

    //获取
    List<T> loadAll(Collection<Key> keys);

    //更新
    List<T> updateAll(List<T> values);

    //更新
    Map<Key, T> updateAll(Map<Key, T> map);

    //删除
    void remove(Key key);

    //限量
    void limit(int limit);

    //清空
    void clear();
}
