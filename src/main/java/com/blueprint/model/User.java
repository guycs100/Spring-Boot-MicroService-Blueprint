package com.blueprint.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter @Setter @NoArgsConstructor
@Entity
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private int phoneNumber;

    public User(String name, int phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
