package com.dao;

import java.util.List;
import java.util.Map;

public interface IDao<T> {
    //CRUD operations
    boolean create(T o);
    boolean update(T o);
    boolean saveOrUpdate(T o);
    boolean delete(Map<String, Integer> keys);
    T getById(Map<String, Integer> keys);
    List<T> getAll();
}
