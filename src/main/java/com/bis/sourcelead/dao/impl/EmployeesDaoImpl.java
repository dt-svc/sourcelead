package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.entity.Employees;

@Repository
public class EmployeesDaoImpl implements EmployeesDao{

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(EmployeesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees addEmployee(Employees employee) throws Exception {
	    logger.debug("In Employee Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(employee);
		}
		catch(HibernateException exception)	{
		}
		
		logger.debug("Employee Saving end");
		
		return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees updateEmployee(Employees employee) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.merge(employee);
	    return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees getEmployee(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (Employees) session.get(Employees.class, Long.parseLong(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteEmployee(String id) throws Exception {
		// TODO Auto-generated method stub
		
		Session session = this.sessionFactory.getCurrentSession();
		Employees employee = (Employees)session.get(Employees.class, Long.parseLong(id));
		employee.setEmployeeState("prasad");
		session.update(employee);
		logger.info("Successfully deleted !!!");
		return null;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Employees> getAllEmployees() throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Employees> addItemsList = null;
		Query employeesListQuery= session.createQuery("from Employee");
		addItemsList=employeesListQuery.list();;
		return addItemsList;
	}

}
