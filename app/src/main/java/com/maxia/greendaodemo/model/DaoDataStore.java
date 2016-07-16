package com.maxia.greendaodemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.DeleteQuery;

/**
 * Created by kurt on 6/6/16.
 *
 * CRUD模板
 */
public abstract class DaoDataStore<Key, Value> implements DataStore<Key, Value> {

    /**
     * 数据库查询公用锁
     */
    private static final ReentrantLock lock = new ReentrantLock(true);

    protected AbstractDao<Value, Key> dao;

    public DaoDataStore(AbstractDao<Value, Key> abstractDao) {
        this.dao = abstractDao;
    }

    public static void lock() {
        lock.lock();
    }

    public static void unlock() {
        lock.unlock();
    }

    @Override
    public Value load(Key key) {
        return this.dao.load(key);
    }

    /**
     * 根据所给出的key给出查询, 此处应该对abstractDao进行优化,添加多key查询方法
     *
     * @param keys
     * @return
     */
    @Override
    public List<Value> loadAll(Collection<Key> keys) {
        //此处必须优化greenDao
        if (keys == null || keys.size() < 1) {
            return new ArrayList<>();
        }
        if (keys.size() < 800) {
            return this.dao.loadAll(keys);
        } else {
            ArrayList values = new ArrayList(keys.size());

            //建立临时缓冲列表
            ArrayList bufferKeys = new ArrayList(800);
            int i = 0;
            for (Key key : keys) {
                if (i < 800) {
                    //添加
                    bufferKeys.add(key);
                    i++;
                } else {
                    //查询
                    values.addAll(this.dao.loadAll(bufferKeys));
                    bufferKeys.clear();
                    i = 0;
                }
            }
            values.addAll(this.dao.loadAll(bufferKeys));
            return values;
        }
    }

    @Override
    public Value insert(Key key, Value value) {
        lock.lock();
        try {
            this.dao.insertOrReplace(value);
            return value;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void remove(Key key) {
        lock.lock();
        try {
            this.dao.deleteByKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Value> updateAll(List<Value> values) {
        lock.lock();
        try {
            this.dao.insertOrReplaceInTx(values);
            return values;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Key, Value> updateAll(Map<Key, Value> map) {
        lock.lock();
        try {
            this.dao.insertOrReplaceInTx(map.values());
            return map;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void limit(int limit) {
        long count = this.dao.count();
        DeleteQuery deleteQuery;
        if (count > limit) {
            deleteQuery = deleteMore((int) (count - limit));
            if (deleteQuery == null) {
                lock.lock();
                try {
                    deleteQuery.executeDeleteWithoutDetachingEntities();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            this.dao.deleteAll();
        } finally {
            lock.unlock();
        }
    }

    protected abstract DeleteQuery<Value> deleteMore(int size);
}