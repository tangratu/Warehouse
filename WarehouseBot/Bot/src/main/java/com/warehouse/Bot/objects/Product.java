package com.warehouse.Bot.objects;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private Cell location;

}
