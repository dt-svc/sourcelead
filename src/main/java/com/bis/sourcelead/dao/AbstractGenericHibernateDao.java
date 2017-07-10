package com.bis.sourcelead.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractGenericHibernateDao<T extends Serializable> implements GenericDao<T> {
	
	private Class<T> entityClass;

	@Autowired
	private SessionFactory sessionFactory;
	
	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void setEntityClass(Class<T> entityClass){
		this.entityClass = entityClass;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public T findById(long id) {
		return getCurrentSession().get(this.entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<T> findAll() {
		return getCurrentSession().createQuery( "from " + this.entityClass.getName() ).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<T> findByCriteria(String property, Long value){
		Criteria criteria = getCurrentSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(property, value));
		return criteria.list();
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void save(T entity) {
		getCurrentSession().save(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public T update(T entity) {
		return (T) getCurrentSession().merge(entity);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void delete(T entity) {
		getCurrentSession().delete(entity);

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void deleteById(long id) {
		T entity = findById(id);
		delete(entity);

	}

}
