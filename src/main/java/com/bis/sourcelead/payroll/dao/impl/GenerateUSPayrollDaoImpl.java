package com.bis.sourcelead.payroll.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.payroll.dto.ProjectRateDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.util.PayrollUtil;
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
	
	@Autowired
	PayrollUtil payrollUtil;
	
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BigDecimal payRateByCandidateLocation(Candidate candidate, OrgLocations orgLocation, Date freqStartDate, Date freqEndDate,String payFrequency)
	{
		List<ProjectRateDTO> projectRateList = null;
		BigDecimal payRate = BigDecimal.ZERO;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date sqlStartDate = new java.sql.Date(freqStartDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(freqEndDate.getTime());
			StringBuilder sb = new StringBuilder();
			sb.append("select pmrs.PROJECT_MEMBER_ID as projectMemId,") 
			.append("pmrs.BILL_RATE as billRate,")
			.append("pmrs.BILL_RATE_TYPE as billRateType,")
		    .append("pmrs.BILL_RATE_START_DATE as billRateStartDate,")
			.append("pmrs.BILL_RATE_END_DATE as billRateEndDate,")
			.append("pmrs.PAY_RATE as payRate,")
			.append("pmrs.PAY_RATE_TYPE as payRateType,")
			.append("pmrs.PAY_RATE_START_DATE as payRateStartDate,") 
			.append("pmrs.PAY_RATE_END_DATE as payRateEndDate,") 
			.append("pm.CANDIDATE_ID as candidateId,") 
			.append("pm.PROJECT_ID as projectId,") 
			.append("pm.PROJECT_CLIENTS_ID as projectClientId,")
			.append("pm.PROJECT_ASSIGNED_DATE as projectAssignedDate,")
			.append("pm.PROJECT_END_DATE as projectEndDate,")
			.append("pm.PAY_RATE as actualPayRate,")
			.append(" pm.PAY_RATE_TYPE as actualPayRateType ")
			.append(" from  PROJECT_MEMBERS pm ") 
			.append(" left join PROJECT_MEMBERS_RATE_HISTORY pmrs ON  pmrs.PROJECT_MEMBER_ID = pm.ID ")
			.append(" left join CANDIDATE can ON can.ID = pm.CANDIDATE_ID ")
			.append(" where can.ORG_LOCATION_ID = :locationId and pm.CANDIDATE_ID= :candidateId and ((:freqStartDate >= pmrs.PAY_RATE_START_DATE AND :freqEndDate <= pmrs.PAY_RATE_END_DATE) OR (pmrs.PAY_RATE_START_DATE IS NULL OR pmrs.PAY_RATE_END_DATE IS NULL)) group by pm.PROJECT_ID");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("candidateId", candidate.getId());
			queryObj.setLong("locationId", orgLocation.getId());
			queryObj.setDate("freqStartDate", sqlStartDate);
			queryObj.setDate("freqEndDate", sqlEndDate);
			queryObj.setResultTransformer(Transformers.aliasToBean(ProjectRateDTO.class));
			projectRateList = queryObj.list();
			if(projectRateList != null) {
				for(ProjectRateDTO pRate : projectRateList) {
					if(pRate.getPayRate() != null) {
						BigDecimal payAmount = payrollUtil.getHourlyRate(new BigDecimal(pRate.getPayRate()), pRate.getPayRateType());
						payRate = payRate.add(payAmount);
					}else {
						payRate.add(new BigDecimal(pRate.getActualPayRate()));
						BigDecimal payAmount = payrollUtil.getHourlyRate(new BigDecimal(pRate.getActualPayRate()), pRate.getActualPayRateType());
						payRate = payRate.add(payAmount);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payRate;
		
	}
	
}
