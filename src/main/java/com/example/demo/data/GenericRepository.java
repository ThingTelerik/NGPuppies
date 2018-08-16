package com.example.demo.data;

import java.util.List;

public interface GenericRepository<T> {
    List<T> getAll();

    T getById(int id);

    T create(T entity);

    void delete(T entity);


}
