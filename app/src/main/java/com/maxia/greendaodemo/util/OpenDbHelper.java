package com.maxia.greendaodemo.util;

import com.maxia.greendaodemo.App;
import com.maxia.greendaodemo.dao.DaoMaster;

/**
 * Created by kurt on 6/7/16.
 */
public class OpenDbHelper extends BaseDbHelper {
    @Override
    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        return new DaoMaster.DevOpenHelper(App.getInstance(), getDatabaseName(), null);
    }
}
