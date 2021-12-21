package com.mzik.laba5.service;

import java.util.Collection;
import java.util.Optional;

public interface DaoService<T> {
    Collection<T> findAll();
    T findById(int id);
    void save(T object);
    Collection<T> findByName(String name);
    void deleteById(int id);
}
