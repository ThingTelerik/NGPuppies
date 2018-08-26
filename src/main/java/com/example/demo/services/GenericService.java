package com.example.demo.services;

import com.example.demo.entities.Client;
import com.example.demo.entities.User;

import java.util.List;

public interface GenericService<T, P> {
    List<T> getAll();

    T create(T entity);

    void delete(T entity);

    void update(T entity, P param);




}
