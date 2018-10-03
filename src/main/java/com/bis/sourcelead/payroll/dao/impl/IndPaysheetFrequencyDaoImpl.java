package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

@Repository
public class IndPaysheetFrequencyDaoImpl implements IndPaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndPaysheetFrequency ipf where ipf.orgLocation.id =:orgLocationId and ipf.status = '1'";
		List<IndPaysheetFrequency> frequencyList = null;
		ResponseList<IndPaysheetFrequency> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(indPaysheetFrequencyDTO.getSearchValue())) {
			if (indPaysheetFrequencyDTO.getSearchValue().contains("/")
					|| indPaysheetFrequencyDTO.getSearchValue().contains("-")) {
				String convertedDate = indPaysheetFrequencyDTO.getSearchValue();
				if (indPaysheetFrequencyDTO.getSearchValue().contains("/")) {
					convertedDate = indPaysheetFrequencyDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
				LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
				DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String newDate = parsedDate.format(f2);
				Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
				java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
				String queryDate = "from IndPaysheetFrequency ipf where ipf.orgLocation.id =:orgLocationId and ( ipf.frequencyStartDate =:sqlDate or ipf.frequencyEndDate =:sqlDate or ipf.payCheckDate =:sqlDate)";
				Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
				response.setData(queryObjDate.list());
				return response;
			}
			else {
				if (indPaysheetFrequencyDTO.getSearchValue() != null && !"".equalsIgnoreCase(indPaysheetFrequencyDTO.getSearchValue())) {
					query1.append(" and (ipf.userGroup.payFrequency like '%"
							+ indPaysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR ipf.userGroup.groupName like '%"
							+ indPaysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR ipf.approverStatus like '%"
							+ indPaysheetFrequencyDTO.getSearchValue()
							+ "%' ").append("OR ipf.paysheetFrequency.type like '%"
							+ indPaysheetFrequencyDTO.getSearchValue()
							+ "%' )");
				}
			}
		}
		Query queryObj = session.createQuery(qry + query1.toString());
		frequencyList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		response.setData(frequencyList);
		return response;
	}
	

	@Override
	@Transactional
	public IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (paySheetFreq != null) {
				session.saveOrUpdate(paySheetFreq);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return paySheetFreq;
	}
	
	
	@Override
	@Transactional
	public IndPaysheetFrequency getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndPaysheetFrequency ipf where ipf.paySheetFrequencyUUID =:payrollFrequencyUUID and ipf.status = '1'";
		List<IndPaysheetFrequency> freqList = null;
		Query queryObj = session.createQuery(qry);
		freqList = queryObj.setString("payrollFrequencyUUID", payrollFrequencyUUID).list();
		IndPaysheetFrequency freq = null;
		if (freqList != null && !freqList.isEmpty()) {
			freq = (IndPaysheetFrequency) freqList.iterator().next();
		}
		return freq;
	}
	
	
	@Override
	@Transactional
	public ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndEmployeePayroll iep where iep.indPaysheetFrequency.paySheetFrequencyUUID =:paysheetFrequencyUuid and iep.status = '1'";
		List<IndEmployeePayroll> empList = null;
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		Query queryObj = session.createQuery(qry);
		empList = queryObj.setString("paysheetFrequencyUuid", paysheetFrequencyUuid).list();
		response.setData(empList);
		return response;
	}
	
	
	@Override
	@Transactional
	public Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndEmployeePayroll iep where iep.indEmpPayrollUUID =:empPayrollUUID and iep.status = '1'";
		List<IndEmployeePayroll> empList = null;
		Response<IndEmployeePayroll> response = new Response<>();
		Query queryObj = session.createQuery(qry);
		empList = queryObj.setString("empPayrollUUID", empPayrollUUID).list();
		
		IndEmployeePayroll empPay = null;
		if (empList != null && !empList.isEmpty()) {
			empPay = (IndEmployeePayroll) empList.iterator().next();
		}
		
		response.setObject(empPay);
		return response;
	}

}
