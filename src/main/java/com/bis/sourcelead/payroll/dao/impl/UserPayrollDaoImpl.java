package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.UserPayrollDao;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Repository
public class UserPayrollDaoImpl implements UserPayrollDao{
	private static final Logger logger = LoggerFactory.getLogger(UserPayrollDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UserPayroll saveUserPayroll(UserPayroll userPayroll) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(userPayroll);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return userPayroll;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate)throws Exception {
		String subQuery = "";
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocationID){
			params.put("orgLocationId", orgLocationID);
		}
		
		if(null != startDate && null != endDate){
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
			params.put("startDate", sqlStartDate);
			params.put("endDate", sqlEndDate);
			subQuery = "and userPayroll.payrollStartDate = :startDate OR userPayroll.payrollEndDate = :endDate";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UserPayroll userPayroll left join fetch userPayroll.userId left join fetch userPayroll.orgLocationId  where userPayroll.orgLocationId.id = :orgLocationId "+subQuery;
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationId") ){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if(str.equalsIgnoreCase("startDate") ){
				qry.setDate(str, (Date) params.get("startDate"));
			}
			if(str.equalsIgnoreCase("endDate") ){
				qry.setDate(str, (Date) params.get("endDate"));
			}
        }
		return qry.setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId,Date startDay, Date endDay) throws Exception {
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();
		List<UserPayroll> userPayrollList = null;
		if(startDay != null && endDay != null){
			subQuery = "and userPayroll.payrollStartDate >=  :startDay and userPayroll.payrollEndDate <=  :endDay";
		}
		String query = "from UserPayroll userPayroll  left join fetch userPayroll.userId where userPayroll.orgLocationId.id =:orgLocationId and userPayroll.userId.id = :userId "+subQuery+"";
		if(startDay != null && endDay != null){
			userPayrollList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setParameter("startDay", startDay).setParameter("endDay", endDay).setCacheable(true).list();
		}else{
			userPayrollList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		}
		if(userPayrollList != null && userPayrollList.size() >0)
		{
			return userPayrollList;
		}
		return userPayrollList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Checks> getChecksList(ChecksDTO checksDTO, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checksList = null;
		ResponseList<Checks> response = new ResponseList<>();
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId";
		String countQuery = "from Checks checks  where checks.orgLocationId.id =:orgLocationId";
		
		StringBuilder query1 = new StringBuilder("");
			if(checksDTO.getSearchValue() !=null && !"".equalsIgnoreCase(checksDTO.getSearchValue())) {
			query1.append(" and checks.empId like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.empName like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.date like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.typeOfExpense like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.bankName like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.month like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.legalFees like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.advance like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.generalExpenses like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.vendorPayments like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.electronicWithdrawals like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.payrollChecks like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.amount like '%" + checksDTO.getSearchValue() + "%' ")
					.append("OR checks.checkNo like '%" + checksDTO.getSearchValue() + "%' ");
						
			}
		Query queryObj = session.createQuery(query + query1.toString());
		if (checksDTO != null && checksDTO.getLength() > 0) {
			queryObj.setFirstResult(checksDTO.getStart());
			queryObj.setMaxResults(checksDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		checksList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (checksDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(checksList);
			return response;
		
		// TODO do not delete @Rajashekar
		/*Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checkslList = null;
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId";
		checkslList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if (checkslList != null && checkslList.size() > 0) {
			return checkslList;
		}
		return checkslList;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Checks> checkslList = null;
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId";
		checkslList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).setCacheable(true).list();
		if (checkslList != null && checkslList.size() > 0) {
			return checkslList;
		}
		return checkslList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Checks getUniqueChecksListByEmployeeId(String empId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Checks checks = null;
		String query = "from Checks checks  left join fetch checks.orgLocationId where checks.orgLocationId.id =:orgLocationId and checks.empId = :empId";
		List<Checks> checkslList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setParameter("empId", empId).setCacheable(true).list();
		if (checkslList != null && checkslList.size() > 0) {
			checks =  checkslList.get(0);
		}
		return checks;
	}

	

}
