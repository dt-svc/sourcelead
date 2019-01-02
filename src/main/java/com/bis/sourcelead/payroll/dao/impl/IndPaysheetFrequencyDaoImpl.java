package com.bis.sourcelead.payroll.dao.impl;

import java.math.BigInteger;
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
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class IndPaysheetFrequencyDaoImpl implements IndPaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
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
		
		//List shorting order
		String sortingQry = "";
		if(StringUtils.isNotBlank(indPaysheetFrequencyDTO.getSortColumn())) {
			if(indPaysheetFrequencyDTO.getSortColumn().equalsIgnoreCase("groupName")) {
				sortingQry = " order by ipf.userGroup."+ indPaysheetFrequencyDTO.getSortColumn() +" "+indPaysheetFrequencyDTO.getSortOrder();
			}else {
				sortingQry = " order by ipf."+ indPaysheetFrequencyDTO.getSortColumn() +" "+indPaysheetFrequencyDTO.getSortOrder();
			}
			
		}
				
		Query queryObj = session.createQuery(qry + query1.toString()+sortingQry);
		frequencyList = queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		response.setData(frequencyList);
		return response;
	}
	

	@Override
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
	
	
	@Override
	public ResponseList<IndEmployeePayroll> getIndPayrollApprovedEmployeeListByPaysheetFrequencyUuidAndEmpApproveStatus(String paysheetFrequencyUuid, String empApproveStatus) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndEmployeePayroll iep where iep.indPaysheetFrequency.paySheetFrequencyUUID =:paysheetFrequencyUuid and iep.submitForAproverStatus =:empApproveStatus and iep.status = '1'";
		List<IndEmployeePayroll> empList = null;
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		Query queryObj = session.createQuery(qry);
		empList = queryObj.setString("paysheetFrequencyUuid", paysheetFrequencyUuid).setString("empApproveStatus", empApproveStatus).list();
		response.setData(empList);
		return response;
	}
	
	
	
	@Override
	public ResponseList<IndEmployeePayroll> getEmployeePaysByOrgLocationIdAndUserId(OrgLocations orgLocation, User user, IndEmployeePayrollDTO indEmployeePayrollDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> countlist = null;
		int filterCount = 0;
		String qry = "from IndEmployeePayroll iep where iep.candidate.consultantUser.id =:userId and iep.indPaysheetFrequency.orgLocation.id =:orgLocationId and iep.submitForAproverStatus = 'APPROVED' and iep.status = '1'";
		List<IndEmployeePayroll> frequencyList = null;
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(indEmployeePayrollDTO.getSearchValue())) {
			if (indEmployeePayrollDTO.getSearchValue().contains("/")
					|| indEmployeePayrollDTO.getSearchValue().contains("-")) {
				String convertedDate = indEmployeePayrollDTO.getSearchValue();
				if (indEmployeePayrollDTO.getSearchValue().contains("/")) {
					convertedDate = indEmployeePayrollDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
				LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
				DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String newDate = parsedDate.format(f2);
				Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
				java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
				String queryDate = "from IndEmployeePayroll iep where iep.candidate.consultantUser.id =:userId and iep.indPaysheetFrequency.orgLocation.id =:orgLocationId and iep.submitForAproverStatus = 'APPROVED' and ( iep.frequencyStartDate =:sqlDate or iep.frequencyEndDate =:sqlDate or iep.payCheckDate =:sqlDate)";
				Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId()).setLong("userId",user.getId());
				
				/*countlist = session.createQuery("SELECT COUNT(*) " + queryDate).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId()).setLong("userId",user.getId()).list();
				filterCount = countlist.iterator().next().intValue();
				
				int totalCount = 0;
				if (indEmployeePayrollDTO != null) {
					if(countlist != null && !countlist.isEmpty()) {
						totalCount = countlist.iterator().next().intValue();
					}
				} 
				response.setRecordsTotal(totalCount);
				
				if(query1.toString() != null && !query1.toString().isEmpty()){
					response.setRecordsFiltered(filterCount);
				}else {
					response.setRecordsFiltered(totalCount);
				}*/
				
				response.setData(queryObjDate.list());
				return response;
			}
			else {
				if (indEmployeePayrollDTO.getSearchValue() != null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
					query1.append(" and (iep.noOfDays like '%"
						+ indEmployeePayrollDTO.getSearchValue()
						+ "%' ").append("OR iep.noOfLeaves like '%"
						+ indEmployeePayrollDTO.getSearchValue()
						+ "%' ").append("OR iep.grossPay like '%"
						+ indEmployeePayrollDTO.getSearchValue()
						+ "%' ").append("OR iep.netPay like '%"
						+ indEmployeePayrollDTO.getSearchValue()
						+ "%' ").append("OR iep.tds like '%"
						+ indEmployeePayrollDTO.getSearchValue()
						+ "%' )");
				}
			}
		}
		Query queryObj = session.createQuery(qry + query1.toString());
		frequencyList = queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("userId",user.getId()).list();
		
		/*countlist = session.createQuery("SELECT COUNT(*) "+qry + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("userId",user.getId()).list();
		filterCount = frequencyList.size();*/
		
		
		/*int totalCount = 0;
		if (indEmployeePayrollDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}*/
		
		response.setData(frequencyList);
		return response;
	}


	
	@Override
	public IndEmployeePayroll indEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from IndEmployeePayroll iep where iep.indEmpPayrollUUID =:empPayrollUUID and iep.status = '1'";
		List<IndEmployeePayroll> empList = null;
		Query queryObj = session.createQuery(qry);
		empList = queryObj.setString("empPayrollUUID", empPayrollUUID).list();
		
		IndEmployeePayroll empPay = null;
		if (empList != null && !empList.isEmpty()) {
			empPay = (IndEmployeePayroll) empList.iterator().next();
		}
		return empPay;
	}
}
