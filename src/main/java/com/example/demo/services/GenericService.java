package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;

import java.util.List;

public interface GenericService<T> {
    List<T> getAll();

    T create(T entity);


}
