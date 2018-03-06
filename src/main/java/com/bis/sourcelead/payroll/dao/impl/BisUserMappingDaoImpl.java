package com.bis.sourcelead.payroll.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.EmployeeListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.BisUserMappingDao;
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;


@Repository
public class BisUserMappingDaoImpl implements BisUserMappingDao {
	

	private static final Logger logger = LoggerFactory.getLogger(BisUserMappingDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(bisUserMapping);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return bisUserMapping;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId and bisUser.userId.id IS NULL";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BisUserMapping getBisUserById(Long importedUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> users = session.createQuery("from BisUserMapping where id=:id" ).setLong( "id", importedUserId ).list();
		logger.debug("getUserById end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId and bisUser.userId.id <> NULL";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Checks saveChecks(Checks checks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(checks);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return checks;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<BisUserMapping> getEmployeeList(EmployeeListDTO employeeListDTO,Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> employeesList = null;
		ResponseList<BisUserMapping> response = new ResponseList<>();
		String query = "from BisUserMapping bis  left join fetch bis.orgLocationId where bis.orgLocationId.id =:orgLocationId";
		String countQuery = "from BisUserMapping bis  where bis.orgLocationId.id =:orgLocationId";
		
		StringBuilder query1 = new StringBuilder("");
			if(employeeListDTO.getSearchValue() !=null && !"".equalsIgnoreCase(employeeListDTO.getSearchValue())) {
			query1.append(" and bis.company like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.branch like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.empId like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.candidateName like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.rate like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.fixedRate like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.payRate like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.fixedPay like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.insurance like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.ded like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.effectiveRate like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.payrollDifference like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.immigration like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.comments like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.advanceHcp like '%" + employeeListDTO.getSearchValue() + "%' ")
					.append("OR bis.balance like '%" + employeeListDTO.getSearchValue() + "%' ");
						
			}
		Query queryObj = session.createQuery(query + query1.toString());
		if (employeeListDTO != null && employeeListDTO.getLength() > 0) {
			queryObj.setFirstResult(employeeListDTO.getStart());
			queryObj.setMaxResults(employeeListDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocationId).list();
		}
		employeesList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (employeeListDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(employeesList);
			return response;
		
		// TODO do not delete @Rajashekar
		
		/*Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BisUserMapping> getMasterListByEmpId(Long empId, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId and bisUser.empId = :empId";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getBisUserMappingVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(bis.version)  FROM BisUserMapping bis where bis.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getChecksVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(ch.version)  FROM Checks ch where ch.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}


	

}
