package com.example.starterapp.POJO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer id;

    private String username;

    private String password;

    private Integer gender;
}
