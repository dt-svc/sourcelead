package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.USPayrollDao;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

@Repository
public class USPayrollDaoImpl implements USPayrollDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LookUpData> findAllDeduction()
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from LookUpData lookupData where lookupData.category =:category";
		return session.createQuery(query).setString("category", Constants.PAYROLL_CATEGORY).list();
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
			searchQuery.append(" and payrollDed.payrollDeductionType like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.payrollDeductionMethod like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.payrollScheduled like '%" + payrollDeductionDTO.getSearchValue() + "%' ")
			.append("OR payrollDed.deductionAmount like '%" + payrollDeductionDTO.getSearchValue() + "%' ");
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
}
