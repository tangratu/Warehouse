package com.warehouse.Bot.Controllers;

import com.warehouse.Bot.JDBC.OrderDAO;
import com.warehouse.Bot.JDBC.RouteDAO;
import com.warehouse.Bot.objects.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "${api.url.start}/orders:/placeholder/orders")
public class ControllerOrderRoute {
    private final OrderDAO odao ;
    private final RouteDAO rdao;

    @Autowired
    public ControllerOrderRoute(OrderDAO o, RouteDAO r){
        odao=o;
        rdao=r;
    }

    @PutMapping()
    public ResponseEntity<Order> create(@RequestBody Map<String,Integer> order){
        return new ResponseEntity<>(odao.create(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable int id){
        return new ResponseEntity<>(odao.getById(id),HttpStatus.OK);
    }
    @GetMapping(value="/route")
    public ResponseEntity<String> getRoute(@RequestParam int order_id){
        return new ResponseEntity<>(rdao.getRoute(order_id),HttpStatus.OK);
    }


}
