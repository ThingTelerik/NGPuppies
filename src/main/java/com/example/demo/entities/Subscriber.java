package com.example.demo.entities;

import javax.persistence.*;
import java.util.Set;

public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToMany(mappedBy = "subscriber", fetch = FetchType.EAGER)
    private Set<Bill> bills;
}
