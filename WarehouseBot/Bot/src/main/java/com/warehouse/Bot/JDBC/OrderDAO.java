package com.warehouse.Bot.JDBC;

import com.warehouse.Bot.RowMappers.OrderMapper;
import com.warehouse.Bot.RowMappers.ProductMapper;
import com.warehouse.Bot.objects.Order;
import com.warehouse.Bot.objects.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDAO {
    private final DataSource ds;
    private final JdbcClient jc;
    private final ProductMapper prm;
    private final OrderMapper orm;
    private final RouteDAO rdao;


    @Autowired
    public OrderDAO(DataSource d,RouteDAO r){
        ds=d;
        prm = new ProductMapper();
        orm = new OrderMapper();
        jc = JdbcClient.create(ds);
        rdao=r;
    }
    public boolean possibleOrder(Map<String,Integer> order){


        List<Product> requestedProducts = rdao.getProductsAsList(order);
        for(Product p: requestedProducts){
            if(p.getQuantity()<order.get(p.getName())){
                return false;
            }
        }
        return true;


    }
    public Order getById(int id){
        String sql = "Select id,status,message from orders where id=?";
        return jc.sql(sql).param(id).query(orm).single();
    }
    public Order create(Map<String,Integer> order){
        StringBuilder contents = new StringBuilder();
        KeyHolder kh = new GeneratedKeyHolder();
        for(String str: order.keySet()){
            contents.append(str);
            contents.append(":");
            contents.append(order.get(str));
            contents.append(",");
        }
        contents.deleteCharAt(contents.length()-1);

        if(possibleOrder(order)){
            String sql = "insert into Orders(status,contents,message) values(?,?,?)";
            jc.sql(sql).params("SUCCESS",contents,"All items available, order fulfilled").update(kh);
            rdao.create(order, (Integer) kh.getKey());
            return getById((Integer) kh.getKey()); //this whole keyholder getbyid can be replaced by a template method

        }
        else{
            String sql = "insert into Orders(status,contents,message) values(?,?,?)";
            jc.sql(sql).params("FAIL",order,"Not enough stock to fulfill the order").update(kh);
            return getById((Integer) kh.getKey());
        }
    }
    public String getStatus(int id){
        String sql = "select status from Orders where id=?";
        return (String) jc.sql(sql).param(id).query().singleValue();
    }


}
