package com.warehouse.Bot.objects;

import lombok.Data;

@Data
public class ProtoProduct {
    private String name;
    private int quantity;
    private double price;
    private Cell location;
}
