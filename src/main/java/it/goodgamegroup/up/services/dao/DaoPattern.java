package it.goodgamegroup.up.services.dao;

import java.util.List;
import java.util.Optional;

public interface DaoPattern < T , K> {

    Optional<T> get(K id);

    List<T> getAll();

    T put(T t);

    void update(T t);

    void delete(T t);

    void deleteById(K id);

}
