package com.license.StableMatchingApplication.services;

import java.util.List;

/**
 * @param <T> gives the Object's type
 *  This interface defines the basic CRUD methods: create, read, update and delete
 *  using save, findAll, findById and delete
 */
public interface CrudService<T> {

    T save(T entity);

    T findById(Long id);

    List<T> findAll();

    void delete(Long id);
}
