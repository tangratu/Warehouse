package com.warehouse.Bot.JDBC;


import com.warehouse.Bot.PathAlgo.BotPathing;
import com.warehouse.Bot.RowMappers.ProductMapper;
import com.warehouse.Bot.objects.Cell;
import com.warehouse.Bot.objects.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class RouteDAO {
    private final DataSource ds;
    private final JdbcClient jc;
    private final ProductMapper prm;

    @Autowired
    public RouteDAO(DataSource d){
        ds=d;
        jc=JdbcClient.create(ds);
        prm = new ProductMapper();
    }
    public List<Product> getProductsAsList(Map<String,Integer> order){
        StringBuilder products = new StringBuilder();
        for (String str: order.keySet()){
            products.append('\'');
            products.append(str);
            products.append('\'');
            products.append(",");
        }
        products.deleteCharAt(products.length()-1);
        return jc.sql(String.format("select * from Products where name in (%s)",products.toString()))
                .query(prm).list();
    }

    public void create(Map<String,Integer> order, int id){


        List<Product> requestedProducts = getProductsAsList(order);
        Cell[] toVisit = new Cell[requestedProducts.size()+1];
        int i=1;
        Iterator<Product> j = requestedProducts.iterator();
        while(j.hasNext()){
            toVisit[i]=j.next().getLocation();
            i++;
        }
        toVisit[0] = new Cell(0,0);

        jc.sql(String.format("insert into routes(route,order_id) values(?,?)"))
                .param(BotPathing.PlanRoute(toVisit)).param(id).update();


    }
    public String getRoute(int orderId){
        String sql = "select route from routes where order_id =?";
        return (String) jc.sql(sql).param(orderId).query().singleValue();
    }

}
