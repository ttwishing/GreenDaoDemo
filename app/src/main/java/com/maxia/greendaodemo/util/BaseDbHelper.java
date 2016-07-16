package com.maxia.greendaodemo.util;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.maxia.greendaodemo.App;
import com.maxia.greendaodemo.dao.DaoMaster;
import com.maxia.greendaodemo.dao.DaoSession;
import com.maxia.greendaodemo.model.DaoDataStore;

import java.util.Locale;

/**
 * Created by kurt on 6/4/16.
 */
public abstract class BaseDbHelper {

    private static final String DATABASE_NAME;

    private DaoMaster daoMaster;
    private DaoMaster.DevOpenHelper devOpenHelper;

    private SQLiteDatabase db;
    private DaoSession daoSession;

    static {
        String str = Locale.getDefault().toString();
        if (TextUtils.isEmpty(str)) {
            str = "en_US";
        }
        VersionUtil.Info info = VersionUtil.getInfo(App.getInstance());
        DATABASE_NAME = "demo_" + info.versionName + "_" + info.versionCode + "_" + str + ".db";
    }

    protected BaseDbHelper() {
        this.devOpenHelper = getDevOpenHelper();
        this.db = this.devOpenHelper.getWritableDatabase();
        this.daoMaster = new DaoMaster(this.db);
        this.daoSession = this.daoMaster.newSession();
    }

    public abstract DaoMaster.DevOpenHelper getDevOpenHelper();

    public DaoSession getDaoSession() {
        return this.daoSession;
    }

    public void runInTx(Runnable r) {
        DaoDataStore.lock();
        try {
            this.daoSession.runInTx(r);
        } finally {
            DaoDataStore.unlock();
        }
    }

    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

}
