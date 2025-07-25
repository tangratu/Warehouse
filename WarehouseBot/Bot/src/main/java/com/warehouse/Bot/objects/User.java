package com.warehouse.Bot.objects;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String address;
    private String password;
}
