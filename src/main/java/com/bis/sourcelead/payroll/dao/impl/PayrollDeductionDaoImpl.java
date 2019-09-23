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
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollDeductionDao;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

@Repository
public class PayrollDeductionDaoImpl implements PayrollDeductionDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollDeductionDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public PayrollDeduction findPayrollDeductionByUUID(String deductionUUID)
	{
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollDeduction payrollDed where payrollDed.deductionUUID =:deductionUUID";
			List<PayrollDeduction> payrollDeductionList =  session.createQuery(query).setString("deductionUUID", deductionUUID).list();
			if(payrollDeductionList != null && payrollDeductionList.size() >0) {
				payrollDeduction = payrollDeductionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}
	
	@Override
	@Transactional
	public PayrollDeductionDTO savePayrollDeduction(PayrollDeduction payrollDeduction,PayrollDeductionDTO payrollDeductionDTO)
	{
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(payrollDeduction);
		return  payrollDeductionDTO;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	public ResponseList<PayrollDeduction> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<PayrollDeduction> response = new ResponseList<>();
		String qry = "";
		if (StringUtils.isNotBlank(payrollDeductionDTO.getSortColumn())) {
			qry = " order by payrollDed." + payrollDeductionDTO.getSortColumn() + " "
					+ payrollDeductionDTO.getSortOrder();
		}
		String query = "from PayrollDeduction payrollDed where payrollDed.orgLocationId.id =:orgLocationId";
		String countQuery = "from PayrollDeduction payrollDed where payrollDed.orgLocationId.id =:orgLocationId";
		StringBuilder searchQuery = new StringBuilder();
		if(StringUtils.isNoneBlank(payrollDeductionDTO.getSearchValue())) {
			searchQuery.append(" and ( payrollDed.payrollDeductionType like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.payrollDeductionMethod like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.payrollScheduled like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.deductionAmount like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.taxType like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.description like '%" + payrollDeductionDTO.getSearchValue() + "%' ) ");
		}
		
		
		Query queryObj = session.createQuery(query +searchQuery+ qry);
		if (payrollDeductionDTO != null && payrollDeductionDTO.getLength() > 0) {
			queryObj.setFirstResult(payrollDeductionDTO.getStart());
			queryObj.setMaxResults(payrollDeductionDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		List<PayrollDeduction> payrollDeductionList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (payrollDeductionDTO != null) {
			List<Long> filteredCountlist = session.createQuery("select count(*) "+ countQuery + searchQuery).setLong("orgLocationId", orgLocation.getId()).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		else {
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(payrollDeductionList);
		return response;
	}
	
	@Override
	@Transactional
	public List<PayrollDeduction> getAllDeductionTypeByLocationId(OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollDeduction payrollDed where payrollDed.orgLocationId.id =:orgLocationId and payrollDed.status = '1' "; 
		List<PayrollDeduction> payrollDeductionList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
		return payrollDeductionList;
			 
	}

	@Override
	@Transactional
	public PayrollDeduction findPayrollDeductionByID(Long deductionId)
	{
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollDeduction payrollDed where payrollDed.id =:deductionId";
			List<PayrollDeduction> payrollDeductionList = session.createQuery(query).setLong("deductionId", deductionId).list();
			if (payrollDeductionList != null && payrollDeductionList.size() > 0) {
				payrollDeduction = payrollDeductionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}

	@Override
	@Transactional
	public PayrollDeduction isValidDeductionType(String payrollDeductionType,OrgLocations orgLocation)
	{
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollDeduction payrollDed where payrollDed.payrollDeductionType =:payrollDeductionType and payrollDed.orgLocationId.id =:orgLocationId";
			List<PayrollDeduction> payrollDeductionList = session.createQuery(query).setString("payrollDeductionType", payrollDeductionType).setLong("orgLocationId", orgLocation.getId()).list();
			if (payrollDeductionList != null && payrollDeductionList.size() > 0) {
				payrollDeduction = payrollDeductionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}

	@Override
	@Transactional
	public PayrollDeduction getPayrollDeductionTypeById(long deductionId)
	{
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollDeduction payrollDed where payrollDed.id =:deductionId";
			List<PayrollDeduction> payrollDeductionList = session.createQuery(query).setLong("deductionId", deductionId).list();
			if (payrollDeductionList != null && payrollDeductionList.size() > 0) {
				payrollDeduction = payrollDeductionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}
	
	@Override
	@Transactional
	public PayrollDeduction getDeductionByDeductionTypeAndLocationId(String deductionType, Long orgLocationId) {
		PayrollDeduction payrollDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PayrollDeduction payrollDed where payrollDed.payrollDeductionType =:deductionType and payrollDed.orgLocationId.id =:orgLocationId";
			List<PayrollDeduction> payrollDeductionList = session.createQuery(query).setString("deductionType", deductionType).setLong("orgLocationId", orgLocationId).list();
			if (payrollDeductionList != null && payrollDeductionList.size() > 0) {
				payrollDeduction = payrollDeductionList.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return payrollDeduction;
	}

	
}
