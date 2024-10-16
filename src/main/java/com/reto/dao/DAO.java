package com.reto.dao;

import java.util.List;

/**
 * Interfaz que implementaran nuestras clases <T>Dao para realizar un crud básico
 * @param <T> Parámetro genérico sobre el que efectuaremos las operaciones en BD
 */
public interface DAO<T> {
    List<T> findAll();
    T findById(Integer id);
    boolean save(T t);
    void update(T t);
    boolean delete(T t);
    void deleteById(Integer id);
}
