package com.bis.sourcelead.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Restrictions;

public interface GenericDao<T extends Serializable> {
	
	public void setEntityClass(Class<T> entityClass);
	
	public T findById(final long id);
	
	public List<T> findAll();
	
	public void save(T entity);
	
	public T update(final T entity);
	
	void delete(final T entity);
	
	void deleteById(final long id);

	public List<T> findByCriteria(String property, Long value);
	

}
