package com.warehouse.Bot.objects;

import lombok.Data;

@Data
public class Order {
    private int id;
    private String status;
    private String contents;
    private String message;
}
