package com.codingarpan.filestoragemanager.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String name;
    @Column(unique = true)
    private String email;

    private String password;

}
