package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class EmployeesDaoImpl implements EmployeesDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	private static final Logger logger = LoggerFactory.getLogger(EmployeesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees addEmployee(Employees employee) throws Exception {
	    logger.debug("In Employee Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(employee != null){
				 session.save(employee);
				 logger.info("Record saved successfully..!");
			}
		}catch(Exception exception)
		{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Employees"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees updateEmployee(Employees employee) throws Exception {
		logger.debug("In Employee Update method");
		Session session = this.sessionFactory.getCurrentSession();
			if(employee != null){
				 session.merge(employee);
				 logger.info("Record Updated successfully..!");
			}
			if(employee == null){
				StringBuffer message= new StringBuffer(MessageFormat.format(statusUpdate,"Employees"));
				throw new SourceLeadObjectNotFoundException(message.toString());
			}
	    return employee;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees getEmployee(String id) throws Exception {
		logger.debug("In Employee getEmployee method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Employees emp left join fetch emp.user where emp.id = :id ";
		@SuppressWarnings("unchecked")
		List<Employees> empList = (List<Employees>)session.createQuery(query.toString()).setLong("id", Long.parseLong(id)).list();
		Employees employees = null;
		if(empList != null && !empList.isEmpty()){
			employees = empList.iterator().next();
		}
		return employees;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteEmployee(String id) throws Exception {
		logger.debug("In Employee deleteEmployee method");
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		
		try{
			Employees employee = (Employees)session.get(Employees.class, Long.parseLong(id));
			employee.setEmployeeState("terminate");
			session.update(employee);
			isDeleted = true;
			logger.info("Successfully deleted !!!");
		}catch(Exception exception){
			StringBuffer message= new StringBuffer(MessageFormat.format(statusDelete,"Employees"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		
		return isDeleted;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Employees getEmployeeByUserId(Long userId) throws Exception {
		 logger.debug("In EmployeeDaoImpl class getEmployeeByUserId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Employees emp where emp.user.id = :userId";
			List<Employees> employeesList =  session.createQuery(query.toString()) .setLong("userId", userId).setCacheable(true).list();
			Employees employee = null;
			if(employeesList != null && !employeesList.isEmpty())
			{
				employee = (Employees) employeesList.iterator().next();
			}
			return employee;
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
				List<Employees> employeesList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setString("employeeId", employeeId).setCacheable(true).list();

				if (employeesList != null && employeesList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Employees getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(String employeeId, Long userId, Long orgLocationsId)
			throws Exception {
		 logger.debug("In EmployeesDaoImpl class getEmployeeByIdAndUserIdAndOrgLocationsId method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Employees emp left join fetch emp.user where emp.employeeId = :employeeId and  emp.user.id = :userId and emp.orgLocations = :orgLocationsId";
			List<Employees> employeesList =  session.createQuery(query.toString()) .setString("employeeId", employeeId).setLong("userId", userId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
			Employees employee = null;
			if(employeesList != null && !employeesList.isEmpty())
			{
				employee = (Employees) employeesList.iterator().next();
			}
			return employee;
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Employees> getEmployeesExcludeTeamMembers(Long projectId,Long orgLocationsId) throws Exception {
		logger.debug("In Employee getEmployeesExcludeTeamMembers method");
		Session session = this.sessionFactory.getCurrentSession();
		List<Employees> addItemsList = null;
		
		Query employeesListQuery= session.createQuery("from Employees emp left join fetch emp.user usr where emp.id NOT IN(SELECT employee from ProjectMembers where projects = :projectId) and emp.orgLocations = :orgLocationsId  and usr.userState <> 'INACTIVE' and usr.userType <>'Admin' ");
		addItemsList=employeesListQuery.setLong("projectId", projectId).setLong("orgLocationsId", orgLocationsId).list();
		return addItemsList;
	}

	
	/***
	 *  This Method is to get  All Employees in the Org  based on OrgLocation
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<Employees> getAllEmployeesByOrgLocationId(Long orgLocationId,Long level1Manager,Long level2Manager) throws Exception {
		logger.debug("In Employees getAllClientsByOrgLocationId method");
        Session session = this.sessionFactory.getCurrentSession();
        
        List<Employees> employeeList = null;
        if(level1Manager!=null && level2Manager!=null){
        	
        	Query employeeListQuery= session.createQuery("from Employees emp left join fetch emp.user user   where emp.orgLocations = :orgLocationId and emp.user.id <> :level1Manager and emp.user.id <> :level2Manager and user.userType <>'Admin'" );
    		employeeList=employeeListQuery.setLong("orgLocationId", orgLocationId).setLong("level1Manager", level1Manager).setLong("level2Manager", level2Manager).list(); 
        }else if(level1Manager!=null){
        	
        	Query employeeListQuery= session.createQuery("from Employees emp left join fetch emp.user user   where emp.orgLocations = :orgLocationId and emp.user.id <> :level1Manager and user.userType <>'Admin'");
    		employeeList=employeeListQuery.setLong("orgLocationId", orgLocationId).setLong("level1Manager", level1Manager).list();
        }else{
        	
        	Query employeeListQuery= session.createQuery("from Employees emp left join fetch emp.user user   where emp.orgLocations = :orgLocationId and user.userType <>'Admin'");
    		employeeList=employeeListQuery.setLong("orgLocationId", orgLocationId).list();
        }
		return employeeList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateEmployeeEmailId(Long userId, String emailId) {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(userId != null && emailId != null){
				String query = "from User user where user.email = :emailId and user.id != :userId ";
				List<Employees> userEmailIdList = session.createQuery(query).setLong("userId", userId).setString("emailId", emailId).setCacheable(true).list();

				if (userEmailIdList != null && userEmailIdList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}

}
