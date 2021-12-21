package busdemo.service;

import java.util.Collection;

public interface DaoService<T> {
    Collection<T> findAll();
    T findById(int id);
    void save(T object);
    Collection<T> findByName(String name);
}
