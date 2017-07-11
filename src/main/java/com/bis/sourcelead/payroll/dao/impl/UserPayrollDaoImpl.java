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

import com.bis.sourcelead.payroll.dao.UserPayrollDao;
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
	public List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserPayroll> userPayrollList = null;
		String query = "from UserPayroll userPayroll  left join fetch userPayroll.userId where userPayroll.orgLocationId.id =:orgLocationId and userPayroll.userId.id = :userId";
		userPayrollList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).setCacheable(true).list();
		if(userPayrollList != null && userPayrollList.size() >0)
		{
			return userPayrollList;
		}
		return userPayrollList;
	}

	

}
