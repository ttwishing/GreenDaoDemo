package de.greenrobot.daogenerator.my;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.PropertyType;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Created by kurt on 5/8/15.
 */
public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.maxia.greendaodemo.dao");
        schema.enableKeepSectionsByDefault();

        addOrder(schema);

        //你的目录
        new DaoGenerator().generateAll(schema, "/Users/kurt/AndroidStudioProjects/GreenDaoDemo/app/src/main/java");
    }

    private static void addOrder(Schema schema) {
        //用户表
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        //订单表
        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS");
        order.addIdProperty();

        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId); //一个订单对应着一个用户
        Property serializedCustomer = order.addProperty(PropertyType.ByteArray, "serializedCustomer").getProperty();
        order.addSerializedProperty(serializedCustomer, "customer2", "Customer");

        //一个用户对应着多个订单
        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
