package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollContributionDao;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

@Repository
public class PayrollContributionDaoImpl implements PayrollContributionDao{
	
private static final Logger logger = LoggerFactory.getLogger(PayrollContributionDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PayrollContribution findPayrollContributionByUUID(String contributionUUID)
	{
		PayrollContribution payrollContribution = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollContribution payrollCon where payrollCon.contributionUUID =:contributionUUID";
			List<PayrollContribution> payrollContributionList = session.createQuery(query).setString("contributionUUID", contributionUUID).list();
			if (payrollContributionList != null && payrollContributionList.size() > 0) {
				payrollContribution = payrollContributionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollContribution;
	}
	
	@Override
	@Transactional
	public PayrollDeduction findById(long deductionTypeId)
	{
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			payrollDeduction = (PayrollDeduction) session.get(PayrollDeduction.class, deductionTypeId);
			return payrollDeduction;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}
	
	@Override
	@Transactional
	public PayrollContribution savePayrollContribution(PayrollContribution payrollContribution)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(payrollContribution);
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return  payrollContribution;
	}
	
	@Override
	@Transactional
	public ResponseList<PayrollContribution> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation)
	{
		ResponseList<PayrollContribution> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String qry = "";
			if (StringUtils.isNotBlank(payrollContributionDTO.getSortColumn())) {
				qry = " order by payrollContribution." + payrollContributionDTO.getSortColumn() + " "
						+ payrollContributionDTO.getSortOrder();
			}
			String query = "from PayrollContribution payrollContribution where payrollContribution.deductionTypeId.orgLocationId.id =:orgLocationId";
			String countQuery = "from PayrollContribution payrollContribution where payrollContribution.deductionTypeId.orgLocationId.id =:orgLocationId";
			StringBuilder searchQuery = new StringBuilder();
			if (StringUtils.isNoneBlank(payrollContributionDTO.getSearchValue())) {
				searchQuery.append(" and ( payrollContribution.payrollContributionMethod like '%"
						            + payrollContributionDTO.getSearchValue()+ "%' ")
					       .append("OR payrollContribution.contributionAmount like '%"
									+ payrollContributionDTO.getSearchValue() + "%' ")
					       .append("OR payrollContribution.description like '%"
	                                + payrollContributionDTO.getSearchValue() + "%' ")
					       .append("OR payrollContribution.deductionTypeId.payrollDeductionType like '%"
	                               + payrollContributionDTO.getSearchValue() + "%' ")
					       .append("OR payrollContribution.deductionTypeId.taxType like '%"
	                               + payrollContributionDTO.getSearchValue() + "%' ")
					       .append("OR payrollContribution.deductionTypeId.payrollScheduled like '%"
	                               + payrollContributionDTO.getSearchValue() + "%' ) "); 
			}
			Query queryObj = session.createQuery(query + searchQuery + qry);
			if (payrollContributionDTO != null && payrollContributionDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollContributionDTO.getStart());
				queryObj.setMaxResults(payrollContributionDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();
			}
			List<PayrollContribution> payrollDeductionList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollContributionDTO != null) {
				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
				if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			}
			else {
				response.setRecordsFiltered(filteredCount);
			}
			response.setData(payrollDeductionList);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PayrollContribution findPayrollContributionByID(long contributionID)
	{
		PayrollContribution payrollContribution = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollContribution payrollCon where payrollCon.id =:contributionID";
			List<PayrollContribution> payrollContributionList = session.createQuery(query).setLong("contributionID", contributionID).list();
			if (payrollContributionList != null && payrollContributionList.size() > 0) {
				payrollContribution = payrollContributionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollContribution;
	}

	@Override
	@Transactional
	public PayrollContribution findPayrollContributionByDeductionTypeId(long deductionTypeId,OrgLocations orgLocation)
	{
		PayrollContribution payrollContribution = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollContribution payrollCon where payrollCon.deductionTypeId.id =:deductionTypeId and payrollCon.orgLocationId.id=:orgLocationId";
			List<PayrollContribution> payrollContributionList = session.createQuery(query).setLong("deductionTypeId", deductionTypeId).setLong("orgLocationId", orgLocation.getId()).list();
			if (payrollContributionList != null && payrollContributionList.size() > 0) {
				payrollContribution = payrollContributionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollContribution;
	}

	@Override
	@Transactional
	public List<PayrollContribution> getAllContributionTypeByLocationId(OrgLocations orgLocation)
	{
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollContribution payrollCon where payrollCon.orgLocationId.id =:orgLocationId and payrollCon.status = 1";
			List<PayrollContribution> payrollContributionList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
			return payrollContributionList;
	}
	
	@Override
	@Transactional
	public PayrollContribution getContributionByDeductionTypeAndLocationId(String deductionType, Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollContribution pc where pc.deductionTypeId IN (Select pd.id from PayrollDeduction pd where pd.payrollDeductionType =:deductionType and pd.orgLocationId.id =:orgLocationId) and pc.status = 1";
		List<PayrollContribution> payrollContributionList = session.createQuery(query).setString("deductionType", deductionType).setLong("orgLocationId", locationId).list();
		if (!payrollContributionList.isEmpty() && payrollContributionList.size() != 0) {
			return payrollContributionList.get(0);
		}else{
			return null;
		}
	}


}
