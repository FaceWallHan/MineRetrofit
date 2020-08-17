package com.net.mine.gson;

public class User {
    private String name,email;
    private int age;
    private String[] topics;
    private Integer id;//可空的？？？

    public Integer getId() {
        return id;
    }

    public User(String name, String email, int age, String[] topics) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.topics = topics;
    }
}
