package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.util.List;

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
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class GenerateUSPayrollDaoImpl implements GenerateUSPayrollDao{

	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Override
	@Transactional
	public PaysheetFrequency getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser)
	{
		UserGroup userGroup = userGroupDao.findUserGroupById(generateUSPayrollDTO.getGroupId());
		String dates[] = generateUSPayrollDTO.getPayPeriod().split(",");
		Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		Session session = this.sessionFactory.getCurrentSession();
		PaysheetFrequency paysheetFrequency = null;
		@SuppressWarnings("unchecked")
		List<PaysheetFrequency> list = session.createQuery("from PaysheetFrequency pf where pf.userGroup.id =:groupId and pf.orgLocation.id = :orgLocationId and pf.frequencyStartDate = :startDate and pf.frequencyEndDate = :endDate and pf.type = 'PAYROLL'")
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
	public EmployeePayroll getEmployeePayrollByUUID(String payrollUUID)
	{
		EmployeePayroll employeePayroll = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			@SuppressWarnings("unchecked")
			List<EmployeePayroll> list = session.createQuery("from EmployeePayroll payroll where payroll.usEmpPayrollUUID =:payrollUUID").setString("payrollUUID", payrollUUID).list();
			if (list != null && list.size() > 0) {
				employeePayroll = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeePayroll;
	}
	
}
