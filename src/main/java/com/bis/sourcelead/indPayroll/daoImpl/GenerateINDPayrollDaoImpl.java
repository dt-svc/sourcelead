package com.bis.sourcelead.indPayroll.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.indPayroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dao.impl.AccountDeductionsDaoImpl;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class GenerateINDPayrollDaoImpl implements GenerateINDPayrollDao{

	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Override
	@Transactional
	public IndPaysheetFrequency getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		String[] groupUUID = generateINDPayrollDTO.getGroupId().split(",");
		UserGroup userGroup = userGroupDao.findUserGroupById(groupUUID[0]);
		String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
		Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		Session session = this.sessionFactory.getCurrentSession();
		IndPaysheetFrequency paysheetFrequency = null;
		@SuppressWarnings("unchecked")
		List<IndPaysheetFrequency> list = session.createQuery("from IndPaysheetFrequency pf where pf.userGroup.id =:groupId and pf.orgLocation.id = :orgLocationId and pf.frequencyStartDate = :startDate and pf.frequencyEndDate = :endDate")
		.setLong("groupId", userGroup.getId())
		.setLong("orgLocationId", orgLocation.getId())
		.setDate("startDate", sqlStartDate)
		.setDate("endDate", sqlEndDate).list();
		if (list != null && list.size() > 0) {
			paysheetFrequency = list.get(0);
		}
		return paysheetFrequency;
	}

	@Override
	@Transactional
	public Long getMaxPayRunIdByOrgLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select MAX(payRunId) from IndPaysheetFrequency  pf  where pf.orgLocation.id = :orgLocationId ";
		Query indPaysheetFrequency = session.createQuery(query).setLong("orgLocationId", orgLocationId);
		Long count = (Long) indPaysheetFrequency.uniqueResult();
		return count;
	}

	
	
}
