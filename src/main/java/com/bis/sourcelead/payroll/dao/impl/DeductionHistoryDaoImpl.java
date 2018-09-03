package com.bis.sourcelead.payroll.dao.impl;

import java.util.ArrayList;
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

import com.bis.sourcelead.payroll.dao.DeductionHistoryDao;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;

@Repository
public class DeductionHistoryDaoImpl implements DeductionHistoryDao{

private static final Logger logger = LoggerFactory.getLogger(DeductionHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId)
	{
		List<DeductionHistoryDTO> dedHis = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT assoDed.ID as id,")
			.append("assoDed.CANDIDATE_ID as candidateId,")
			.append("assoDed.DEDUCTION_TYPE_ID as deductionId,")
			.append("assoDed.DEDUCTION_METHOD as deductionMethod,")
			.append("assoDed.DEDUCTION_AMOUNT as amount,")
			.append("payDed.PAYROLL_SCHEDULED as scheduleType,")
			.append("payDed.TAX_TYPE as taxType,")
			.append("payDed.PAYROLL_DEDUCTION_TYPE as deductionType")
			.append(" FROM sourcelead.US_EMP_DEDUCTION_HIS dedHis,")
			.append("ASSOCIATE_DEDUCTION assoDed,")
			.append("PAYROLL_DEDUCTION payDed ")
			.append(" where dedHis.DEDUCTION_ID = assoDed.ID ")
			.append(" AND assoDed.DEDUCTION_TYPE_ID = payDed.ID ")
			.append(" AND assoDed.DED_STATUS = 'DEDUCTED' ")
			.append(" AND dedHis.PAYROLL_ID = :payrollId");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("payrollId", payrollId);
			queryObj.setResultTransformer(Transformers.aliasToBean(DeductionHistoryDTO.class));
			dedHis = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return dedHis;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ContributionHistoryDTO> getContributionsByPayrollId(Long payrollId)
	{
		List<ContributionHistoryDTO> his = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT con.ID as id,") 
			.append("con.CANDIDATE_ID as candidateId,")
			.append("con.CONTRIBUTION_TYPE_ID as contributionId,")
			.append("con.CONTRIBUTION_METHOD as contributionMethod,")
			.append("con.CONTRIBUTION_AMOUNT amount,")
			.append("ded.PAYROLL_SCHEDULED as scheduleType,")
			.append("ded.TAX_TYPE as taxType,")
			.append("ded.PAYROLL_DEDUCTION_TYPE as contributionType ")
			.append(" FROM sourcelead.US_EMP_DEDUCTION_HIS dedHis,")
			.append(" ASSOCIATE_CONTRIBUTION con,")
			.append(" PAYROLL_CONTRIBUTION payCon,")
			.append(" PAYROLL_DEDUCTION ded")
			.append(" where dedHis.CONTRIBUTION_ID = con.ID ")
			.append(" AND con.CONTRIBUTION_TYPE_ID = payCon.ID")
			.append(" AND payCon.DEDUCTION_TYPE_ID = ded.ID")
			.append(" AND con.CONTRIBUTION_STATUS = 'CONTRIBUTED'")
			.append(" AND dedHis.PAYROLL_ID = :payrollId ");
	
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("payrollId", payrollId);
			queryObj.setResultTransformer(Transformers.aliasToBean(ContributionHistoryDTO.class));
			his = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return his;
	}
}
