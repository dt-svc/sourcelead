package com.bis.sourcelead.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CustomerDao;
import com.bis.sourcelead.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void saveCustomer(Customer customer) {
		logger.debug("In Customer Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(customer);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public void updateCustomer() {
		logger.debug("In Customer update method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Customer customer = session.load(Customer.class,1);
			customer.setCustomerName("IVS");
			session.update(customer);
		} catch (HibernateException exception) {
             exception.printStackTrace();
		} finally {
			//session.close();
		}
		logger.debug("Customer Saving end");
	}

}
