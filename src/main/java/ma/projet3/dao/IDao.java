package ma.projet3.dao;

import java.util.List;

public interface IDao<T> {
    T create(T o);
    void delete(T o);
    T update(T o);
    List<T> findAll();
    T findById(Long id);
}