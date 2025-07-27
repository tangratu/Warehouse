package com.warehouse.Bot.Controllers;

import com.warehouse.Bot.JDBC.ProductDAO;
import com.warehouse.Bot.objects.Product;
import com.warehouse.Bot.objects.ProtoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${api.url.start}/products")
public class ControllerProduct {
    private final ProductDAO pdao;

    @Autowired
    public ControllerProduct(ProductDAO p){
        pdao=p;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(pdao.getAll(), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Product> create(@RequestBody ProtoProduct protoProduct){
        return new ResponseEntity<>(pdao.create(protoProduct),HttpStatus.CREATED);
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> update(@RequestBody ProtoProduct protoProduct,@PathVariable int id){
        return new ResponseEntity<>(pdao.update(protoProduct,id),HttpStatus.OK);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int  id){
        pdao.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
