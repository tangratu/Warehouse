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
import java.util.Map;
import java.util.Optional;

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
        String sql = "select * from products";
        List<Product> res = jc.sql(sql).query(prm).list();
        return res;
    }
     public Product getByName(String name){
        String sql = "select * from products where name like ?";
        return jc.sql(sql).param(name).query(prm).single();
     }
     public Product getById(int id){
        String sql = "select * from products where id=?";
        return jc.sql(sql).param(id).query(prm).single();
     }
    public Product create(ProtoProduct p){
        String sql = "select * from products where x=? AND y=?";
        KeyHolder kh = new GeneratedKeyHolder();
        Optional<Product> existing = jc.sql(sql).params(p.getLocation().getX(),p.getLocation().getY()).query(prm).optional();
        if(existing.isPresent()){
            if(p.getName().equals(existing.get().getName())){
                sql="update products set quantity = quantity+?";
                jc.sql(sql).param(p.getQuantity()).update(kh,"id");
                return getById((Integer) kh.getKey());
            }
            return existing.get(); //add proper return for already existing object at given location
        }
         sql = "insert into products(name,price,quantity,x,y) values(?,?,?,?,?)";

        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY()).update(kh,"id");
        return getById((Integer) kh.getKey());
    }
    public void update(Product p){
        String sql = "update products set name=?,price=?,quantity=?,x=?,y=? where id=?";

        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY(),p.getId()).update();

    }
    public Product update(ProtoProduct p, int id){
        String sql = "update products set name=?,price=?,quantity=?,x=?,y=? where id=?";
        KeyHolder kh = new GeneratedKeyHolder();
        jc.sql(sql).params(p.getName(),p.getPrice(),p.getQuantity(),p.getLocation().getX(),p.getLocation().getY(),id).update(kh,"id");
        return getById((Integer) kh.getKey());
    }
    public void delete(int id){
        String sql = "delete from products where id=?";
        jc.sql(sql).param(id).update();
    }
    public void fulfillOrder(Map<String,Integer> order){
        StringBuilder values = new StringBuilder();
        for(String str : order.keySet()){
            values.append('(');
            values.append('\'');
            values.append(str);
            values.append('\'');
            values.append(',');
            values.append(order.get(str));
            values.append("),");
        }
        values.deleteCharAt(values.length()-1);
        String sql = String.format("update \"Warehouse\".products p set quantity= p.quantity - d.quantity from" +
                " (values %s) as d(name,quantity) where p.name=d.name",values.toString());
        jc.sql(sql).update();
    }

}
