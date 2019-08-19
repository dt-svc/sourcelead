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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.DeductionHistoryDao;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class DeductionHistoryDaoImpl implements DeductionHistoryDao{

private static final Logger logger = LoggerFactory.getLogger(DeductionHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DeductionHistory> getDedudctionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) {
		
		String subQuery = "";
		StringBuilder query1 = new StringBuilder("");
		if(Constants.DEDUCTION.equalsIgnoreCase(payrollReportDTO.getType())) {
			subQuery = " and dedHis.deductionId.deductionTypeId.orgLocationId.id = :orgLocationId";
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
				query1.append(" and dedHis.deductionId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ");
			}
		}else {
			subQuery = " and dedHis.contributionId.contributionTypeId.deductionTypeId.orgLocationId.id = :orgLocationId";
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
				query1.append(" and dedHis.contributionId.contributionTypeId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ");
			}
		}
		
		List<DeductionHistory> list = null;
		try {
			Date fDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFromDate());
			Date tDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getToDate());
			java.sql.Date startDate = new java.sql.Date(fDate.getTime());
			java.sql.Date endDate = new java.sql.Date(tDate.getTime());
			
			Session session = this.sessionFactory.getCurrentSession();
			String qry = "from DeductionHistory dedHis where dedHis.payroll.payCheckDate >= :startDate and dedHis.payroll.payCheckDate <= :endDate and dedHis.payroll.submitForAproverStatus = 'APPROVED'"+subQuery;   			
//			list = session.createQuery(qry).setParameter("startDate", startDate).setParameter("endDate", endDate).setParameter("orgLocationId", orgLocation.getId()).list();
			
			Query queryObj = session.createQuery(qry + query1.toString());
			queryObj.setFirstResult(payrollReportDTO.getStart());
			queryObj.setMaxResults(payrollReportDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId())
			.setParameter("startDate", startDate)
			.setParameter("endDate", endDate).list();
			
			list = queryObj.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
