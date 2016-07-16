package com.maxia.greendaodemo.model;

import com.maxia.greendaodemo.util.BaseDbHelper;

/**
 * Created by kurt on 1/5/16.
 */
public abstract class BaseModel<Key, T> {

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

    protected abstract Key getKey(T value);

    public void remove(Key key) {
        getDataStore().remove(key);
    }

    public T get(Key key) {
        try {
            return getDataStore().load(key);
        } catch (Throwable t) {
            return null;
        }
    }

}

