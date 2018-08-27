package com.example.demo.services.base;

import java.util.List;

public interface GenericService<T, P> {
    List<T> getAll();

    T create(T entity);

    void delete(T entity);

    T update(T entity, P param);




}
