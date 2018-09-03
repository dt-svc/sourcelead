package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.GenerateUSPayrollDao;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.LCAWageDTO;
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
		String[] groupUUID = generateUSPayrollDTO.getGroupId().split(",");
		UserGroup userGroup = userGroupDao.findUserGroupById(groupUUID[0]);
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

	@Override
	@Transactional
	public LCAWageDTO getLcaWageByCandidateIdLcationId(Candidate candidate, OrgLocations orgLocation)
	{
		LCAWageDTO lcaWage = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT lcaHis.ID as lcaHisId,") 
			.append("lcaHis.CANDIDATE_ID as candidateId,") 
			.append("lcaHis.LCA_ID as lcaId,") 
			.append("lca.PRIVILING_WAGE_RATETYPE as lcaWageType,") 
			.append("lca.LCA_STATUS as lcaStatus,") 
			.append("lca.START_DATE as startDate,") 
		    .append("lca.END_DATE as endDate,") 
			.append("lca.PREVAILING_WAGE as lcaWage " )  
			.append(" FROM LCA_HISTORY lcaHis, LCA lca ")  
			.append(" where lcaHis.LCA_ID = lca.ID and lca.ORG_LOCATION_ID = :locationId and lcaHis.CANDIDATE_ID = :candidateId and lcaHis.IS_CURR = 'Y'");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("candidateId", candidate.getId());
			queryObj.setLong("locationId", orgLocation.getId());
			queryObj.setResultTransformer(Transformers.aliasToBean(LCAWageDTO.class));
			lcaWage = (LCAWageDTO) queryObj.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lcaWage;
	}
	
}
