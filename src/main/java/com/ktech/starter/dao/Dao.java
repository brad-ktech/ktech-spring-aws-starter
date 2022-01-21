package com.ktech.starter.dao;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.ktech.starter.enums.QueryEnum;
import org.apache.logging.log4j.Logger;



public interface Dao<T> {

    public Optional<T> find(Class<T> clazz, Object id);

    public Optional<T> find(Class<T> clazz, QueryParameters qp);

    public Optional<T> find(Class<T> clazz, String query, QueryParameters qp);

    public Optional<T> find(Class<T> clazz, QueryEnum hql, QueryParameters qp);

    public Optional<List<T>> findAll(Class<T> clazz);

    public Optional<List<T>> findAll(Class<T> clazz, Comparator<T> comparator);

    public Optional<List<T>> findByQuery(Class<T> clazz, String query, QueryParameters qp);

    public Optional<List<?>> findByQuery(String query, QueryParameters qp);

    public Optional<List<T>> findByQuery(Class<T> clazz, QueryEnum hql, QueryParameters qp);

    public Optional<List<T>> findByNativeQuery(Class<T> clazz, String query, QueryParameters qp);

    public Optional<List<T>> findByNativeQuery(Class<T> clazz, QueryEnum num, QueryParameters qp);

    public Optional<List<T>> findByParameters(Class<T> clazz, QueryParameters qp);

    public <T> int count(Class<T> clazz, QueryParameters qp);

    public <T> T save(T entity);

    public int executeNativeUpdate(String query);

    public <T> void remove(T entity);


    public EntityManager getEntityManager();

    public Logger getLogger();



}
