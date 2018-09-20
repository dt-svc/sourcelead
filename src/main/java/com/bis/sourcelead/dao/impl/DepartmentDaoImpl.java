package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssetDao;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class DepartmentDaoImpl implements DepartmentDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoImpl.class);


	@Override
	@Transactional
	public Department saveDepartment(Department department) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (department != null) {
				session.saveOrUpdate(department);
			}
			logger.debug("Saving Department");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Department"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return department;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Department getDepartmentById(Long departmentId) throws Exception {
		logger.debug("In DepartmentDaoImpl getDepartmentById method");
		Session session = this.sessionFactory.getCurrentSession();
		return (Department) session.get(Department.class, departmentId);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Department> getDepartmentByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Department department  where department.orgLocationId.id =:orgLocationId and department.status = '1' "; 
		List<Department> departmentList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return departmentList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Department validateDepartmentnameByDepartmentIdAndDepartmentName(Long departmentId, String departmentName)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Department department  where department.id =:departmentId and department.departmentName =:departmentName ";
		List<Department> departmentList =session.createQuery(query) .setString("departmentName", departmentName).setLong("departmentId", departmentId).list();
		Department department = null;
		if(departmentList != null && !departmentList.isEmpty())
		{
			department = (Department) departmentList.iterator().next();
		}
		return department;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateDepartmentnameByOrgLocationIdAndDepartmentName(Long orgLocationId, String departmentName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from Department department  where department.orgLocationId.id =:orgLocationId and department.departmentName =:departmentName ";
				Collection<Department> departmentList = session.createQuery(query) .setString("departmentName", departmentName).setLong("orgLocationId", orgLocationId).list();
				if (departmentList != null && departmentList.size() > 0) {
					flag = true;
				}
		return flag;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Department> getDepartmentByOrgLocationIdAndStatus(Long orgLocationId, String status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Department department  where department.orgLocationId.id =:orgLocationId and department.status =:status "; 
		List<Department> departmentList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("status", status).list();
		return departmentList;
	}

}
