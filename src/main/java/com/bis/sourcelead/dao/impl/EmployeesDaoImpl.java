package com.bis.sourcelead.dao.impl;

import java.util.Collection;
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
import com.bis.sourcelead.entity.Projects;

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
		return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees updateEmployee(Employees employee) throws Exception {
		logger.debug("In Employee Update method");
		Session session = this.sessionFactory.getCurrentSession();
	    session.merge(employee);
	    logger.info("Record updated successfully..!");
	    return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees getEmployee(String id) throws Exception {
		logger.debug("In Employee getEmployee method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Employees) session.get(Employees.class, Long.parseLong(id));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteEmployee(String id) throws Exception {
		logger.debug("In Employee deleteEmployee method");
		Session session = this.sessionFactory.getCurrentSession();
		Employees employee = (Employees)session.get(Employees.class, Long.parseLong(id));
		employee.setEmployeeState("prasad");
		session.update(employee);
		logger.info("Successfully deleted !!!");
		return null;
		
	}
	
	/**
	 * This method for validate employee by employeeId. It returns boolean value
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception {
		 logger.debug("In Employee getEmployeeByEmployeeId method");
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(employeeId != null && orgLocationsId != null){
				String query = "from Employees emp where emp.orgLocations = :orgLocationsId and emp.employeeId = :employeeId ";
				Collection<Employees> employeesList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("employeeId", employeeId).list();

				if (employeesList != null && employeesList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Employees> getAllEmployees() throws Exception {
		logger.debug("In Employee getAllEmployees method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Employees> addItemsList = null;
		Query employeesListQuery= session.createQuery("from Employees emp left join fetch emp.user");
		addItemsList=employeesListQuery.list();
		return addItemsList;
	}

}
