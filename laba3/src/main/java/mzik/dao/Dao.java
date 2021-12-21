package mzik.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> findAll();

    Optional<T> findById(int id);
}
