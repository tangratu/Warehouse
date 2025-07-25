package com.warehouse.Bot.JDBC;

import com.warehouse.Bot.RowMappers.SessionUserMapper;
import com.warehouse.Bot.objects.ProtoUser;
import com.warehouse.Bot.objects.SessionUser;
import com.warehouse.Bot.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class UserDAO {
    private final DataSource ds;
    private final JdbcClient jc;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final SessionUserMapper sum;

    @Autowired
    public UserDAO(DataSource d){
        ds=d;
        jc = JdbcClient.create(ds);
        sum = new SessionUserMapper();
    }

    public SessionUser getById(int id){
        String sql = "select * from Users where id=?";
       return jc.sql(sql).param(id).query(sum).single();
    }

    public SessionUser create(ProtoUser u){
        KeyHolder kh = new GeneratedKeyHolder();
        String encodedPass = encoder.encode(u.getPassword());
        String sql = "insert into Users(name,email,address,password_hash) values(?,?,?,?)";
        jc.sql(sql).params(u.getName(),u.getEmail(),u.getAddress(),encodedPass).update(kh);
        return getById((Integer) kh.getKey()); //needs try-catch
    }

    public Optional<SessionUser> verify(String username, String password){
        String sql = "select password_hash from users  where name=?";
        String storedHash = (String) jc.sql(sql).param(username).query().singleRow().get("password_hash");
        if(encoder.matches(password,storedHash)){
           sql = "select name,email,address from users where name=?";
           Optional<SessionUser> res = jc.sql(sql).param(username).query(sum).optional();
            return res;
        }
        else {
            Optional<SessionUser> res = Optional.empty();
            return res;
        }

    }
    public void delete(String username){
        String sql = "delete from users where name=?";
        jc.sql(sql).param(username).update();
    }

}
