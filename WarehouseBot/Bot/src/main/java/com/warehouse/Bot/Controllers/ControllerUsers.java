package com.warehouse.Bot.Controllers;

import com.warehouse.Bot.JDBC.UserDAO;
import com.warehouse.Bot.objects.ProtoUser;
import com.warehouse.Bot.objects.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "${api.url.start}/users")
public class ControllerUsers {
    private final UserDAO udao;

    @Autowired
    public ControllerUsers(UserDAO u){
        udao=u;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SessionUser> getById(@PathVariable int id){
        return new ResponseEntity<>(udao.getById(id), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<SessionUser> create(@RequestBody ProtoUser pu){
        return new ResponseEntity<>(udao.create(pu),HttpStatus.CREATED);
    }
    @PostMapping(value = "/verify")
    public ResponseEntity<Optional<SessionUser>> verify(@RequestBody Map<String,String> info){
        return new ResponseEntity<>(udao.verify(info.get("username"),info.get("password")),HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<Void> deleteById(@PathVariable String uname){
        udao.delete(uname);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
