package com.warehouse.Bot.JDBC;

import com.warehouse.Bot.RowMappers.ProductMapper;
import com.warehouse.Bot.objects.Product;
import com.warehouse.Bot.objects.ProtoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDAO {
    private final DataSource ds;
    private final JdbcClient jc;
    private final ProductMapper prm;

    @Autowired
    public ProductDAO(DataSource d){
        ds=d;
        jc = JdbcClient.create(ds);
        prm = new ProductMapper();
    }

    public List<Product> getAll(){
        String sql = "select * from Products";
        List<Product> res = jc.sql(sql).query(prm).list();
        return res;
    }
     public Product getByName(String name){
        String sql = "select * from Products where name like ?";
        return jc.sql(sql).param(name).query(prm).single();
     }
     public Product getById(int id){
        String sql = "select * from Products where id=?";
        return jc.sql(sql).param(id).query(prm).single();
     }
    public Product create(ProtoProduct p){
        String sql = "insert into Products(name,price,quantity,X,Y) values(?,?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY()).update(kh);
        return getById((Integer) kh.getKey());
    }
    public void update(Product p){
        String sql = "update Products set name=?,price=?,quantity=?,X=?,Y=? where id=?";

        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY(),p.getId()).update();

    }
    public Product update(ProtoProduct p, int id){
        String sql = "update Products set name=?,price=?,quantity=?,X=?,Y=? where id=?";
        KeyHolder kh = new GeneratedKeyHolder();
        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY(),id).update();
        return getById((Integer) kh.getKey());
    }
    public void delete(int id){
        String sql = "delete from Products where id=?";
        jc.sql(sql).param(id).update();
    }
    public void fulfillOrder(String order){
        String[] splitOrder = order.split(",");
        for (int i = 0; i < splitOrder.length ; i++) {
            Product temp = getByName(splitOrder[i].split("x")[0]);
            temp.setQuantity(temp.getQuantity()-Integer.parseInt(splitOrder[i].split("x")[1])); // find something better than this
            update(temp); //combine this all into 1 update is probably better
        }
    }

}
