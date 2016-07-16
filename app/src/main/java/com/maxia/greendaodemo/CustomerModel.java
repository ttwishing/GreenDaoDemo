package com.maxia.greendaodemo;

import com.maxia.greendaodemo.dao.Customer;
import com.maxia.greendaodemo.dao.CustomerDao;
import com.maxia.greendaodemo.model.BaseModel;
import com.maxia.greendaodemo.model.DaoDataStore;
import com.maxia.greendaodemo.model.DataStore;
import com.maxia.greendaodemo.util.BaseDbHelper;

import de.greenrobot.dao.query.DeleteQuery;

/**
 * Created by kurt on 7/16/16.
 */
public class CustomerModel extends BaseModel<Long, Customer> {


    CustomerDao dao;

    public CustomerModel(BaseDbHelper dbHelper) {
        super(dbHelper);
        dao = dbHelper.getDaoSession().getCustomerDao();
    }

    @Override
    protected DataStore createDataStore() {
        return new DaoDataStore(dao) {
            @Override
            protected DeleteQuery deleteMore(int size) {
                return null;
            }
        };
    }

    @Override
    protected Long getKey(Customer value) {
        return value.getId();
    }

}
