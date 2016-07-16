package com.maxia.greendaodemo.model;

import com.maxia.greendaodemo.util.BaseDbHelper;

/**
 * Created by kurt on 1/5/16.
 */
public abstract class BaseModel<Key, T> {

    /**
     * 由其代理操作
     */
    private DataStore<Key, T> dataStore;

    protected final BaseDbHelper dbHelper;

    public BaseModel(BaseDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    protected synchronized DataStore<Key, T> getDataStore() {
        if (this.dataStore == null) {
            this.dataStore = createDataStore();
        }
        return dataStore;
    }

    protected abstract DataStore<Key, T> createDataStore();

    /**
     * 根据value获取key
     *
     * @param value
     * @return
     */
    protected abstract Key getKey(T value);

    /**
     * 删除
     *
     * @param key
     */
    public void remove(Key key) {
        getDataStore().remove(key);
    }

    /**
     * 查询
     *
     * @param key
     * @return
     */
    public T get(Key key) {
        try {
            return getDataStore().load(key);
        } catch (Throwable t) {
            return null;
        }
    }

}

