package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.dto.LineOfBusinessDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LineOfBusiness;
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
		String query = "from Department department  where department.id =:departmentId and department.departmentName =:departmentName and department.status = '1'";
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
			 String query = "from Department department  where department.orgLocationId.id =:orgLocationId and department.departmentName =:departmentName and department.status = '1'";
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<String> getDepartmentList(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query ="SELECT "
				+" DEPT.DEPARTMENT_NAME "
				+" FROM DEPARTMENT DEPT "
				+" WHERE "
				+" DEPT.ORG_LOCATION_ID = :orgLocationId AND DEPT.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);	
		List<String> deptList = (List<String>)queryObj.list();
		
		return deptList;
	
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Department getDepartmentByOrgLocationIdAndDepartmentName(Long orgLocationId, String departmentName)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Department department  where department.orgLocationId.id = :orgLocationId and department.departmentName = :departmentName and department.status = '1' ";
		List<Department> departmentList =session.createQuery(query) .setString("departmentName", departmentName).setLong("orgLocationId", orgLocationId).list();
		Department department = null;
		if(departmentList != null && !departmentList.isEmpty())
		{
			department = (Department) departmentList.iterator().next();
		}
		return department;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<DepartmentDTO> getDepartmentsListByIds(List<Long> id) throws Exception {
		List<DepartmentDTO> departmentDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT DEPT.ID AS deptId, "
				+ " DEPT.DEPARTMENT_NAME AS departmentName "
				+ " FROM DEPARTMENT DEPT " + " WHERE " + " DEPT.ID IN (:id) ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setParameterList("id", id);
		queryObj.setResultTransformer(Transformers.aliasToBean(DepartmentDTO.class));
		departmentDTOList = queryObj.list();
		return departmentDTOList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<DepartmentDTO> getDepartmentsListByOrgLocationId(Long orgLocationId) throws Exception {
		List<DepartmentDTO> departmentDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT DEPT.ID AS deptId, "
				+ " DEPT.DEPARTMENT_NAME AS departmentName "
				+ " FROM DEPARTMENT DEPT WHERE DEPT.ORG_LOCATION_ID = :orgLocationId AND DEPT.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(DepartmentDTO.class));
		departmentDTOList = queryObj.list();
		return departmentDTOList;
	}

	@Override
	@Transactional
	public LineOfBusiness saveLineOfBusiness(LineOfBusiness lineOfBusiness) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (lineOfBusiness != null) {
				session.saveOrUpdate(lineOfBusiness);
			}
			logger.debug("Saving LineOfBusiness");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"LineOfBusiness"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return lineOfBusiness;
	}

	@Override
	@Transactional
	public List<LineOfBusiness> getLineOfBusinessListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LineOfBusiness lob left join fetch lob.departmentId where lob.orgLocationId.id =:orgLocationId and lob.status = '1' "; 
		List<LineOfBusiness> lobList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		return lobList;
	}

	@Override
	@Transactional
	public LineOfBusinessDTO getLineOfBusinessDTOByLOBId(Long id) throws Exception {
		List<LineOfBusinessDTO> lobDTOList = null;
		LineOfBusinessDTO lobDTO = new LineOfBusinessDTO();
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT LOB.ID AS lobId, "
				+ " LOB.LOB_NAME AS lobName, "
				+ " LOB.DESCRIPTION AS description, "
				+ " LOB.DEPARTMENT_ID AS deptId "
				+ " FROM LINE_OF_BUSINESS LOB  WHERE LOB.ID = :id ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("id", id);
		queryObj.setResultTransformer(Transformers.aliasToBean(LineOfBusinessDTO.class));
		lobDTO = (LineOfBusinessDTO) queryObj.list().get(0);
		return lobDTO;
	}

	@Override
	@Transactional
	public List<LineOfBusinessDTO> getLineOfBusinessDTOList(Long orgLocationId) throws Exception {
		List<LineOfBusinessDTO> lobDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT LOB.ID AS lobId, "
				+ " LOB.LOB_NAME AS lobName "
				+ " FROM LINE_OF_BUSINESS LOB WHERE LOB.ORG_LOCATION_ID = :orgLocationId AND LOB.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(LineOfBusinessDTO.class));
		lobDTOList = queryObj.list();
		return lobDTOList;
	}

}
