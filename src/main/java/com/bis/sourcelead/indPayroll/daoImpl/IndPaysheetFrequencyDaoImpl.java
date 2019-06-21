package com.bis.sourcelead.indPayroll.daoImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dao.impl.AssociateTaxDaoImpl;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class IndPaysheetFrequencyDaoImpl implements IndPaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
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


	@Override
	public ResponseList<IndEmployeePayroll> getPayrollRegisterReports(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		
		Date fDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getFreqStartDate());
		Date tDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getFreqEndDate());
		java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
		java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
		
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (iep.employee.departmentId.departmentName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (iep.candidate.firstName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (iep.candidate.lastName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR concat(iep.candidate.firstName,iep.candidate.lastName) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
			   .append("OR iep.employee.employeeId like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		
		//department Ids
		Long[] data = null;
		String departmentQuery = "";
		if(indEmployeePayrollDTO.getDepartment() != null && !"null".equalsIgnoreCase(indEmployeePayrollDTO.getDepartment()) ) {
			
			String[] idsValues = indEmployeePayrollDTO.getDepartment().split(",");
			// convert string array to long array
			 data = new Long[idsValues.length];
			for (int i = 0; i < idsValues.length; i++) {
			  data[i] = Long.valueOf(idsValues[i]);
			}
			
			departmentQuery = " and iep.employee.departmentId.id IN ( :ids )";
			
		}
		List<IndEmployeePayroll> indEmployeePayrollList = null;
		List<Long> countlist  = null;
		//List Query
		String query = "from IndEmployeePayroll iep left join fetch iep.indPaysheetFrequency ipf where  ipf.userGroup.id =:groupId and ipf.frequencyStartDate =:frequencyStartDate and ipf.frequencyEndDate =:frequencyEndDate and ipf.approverStatus ='APPROVED' and ipf.orgLocation.id =:orgLoactionId "+departmentQuery+" "+query1+"";
		
		//count Query
		String countQuery  = "Select count (*) from IndEmployeePayroll iep where  iep.indPaysheetFrequency.userGroup.id =:groupId and iep.indPaysheetFrequency.frequencyStartDate =:frequencyStartDate and iep.indPaysheetFrequency.frequencyEndDate =:frequencyEndDate and iep.indPaysheetFrequency.approverStatus ='APPROVED' and iep.indPaysheetFrequency.orgLocation.id =:orgLoactionId "+departmentQuery+" "+query1+"";
		
		if(indEmployeePayrollDTO.getDepartment() != null && !"null".equalsIgnoreCase(indEmployeePayrollDTO.getDepartment()) ) {
			 indEmployeePayrollList = session.createQuery(query).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).setParameterList("ids", data).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
			 countlist  = session.createQuery(countQuery).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).setParameterList("ids", data).list();
		}else {
			 indEmployeePayrollList = session.createQuery(query).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
			 countlist  = session.createQuery(countQuery).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).list();
		}
		Integer filterCount = countlist.iterator().next().intValue();
		
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
		}
		
		response.setData(indEmployeePayrollList);
		return response;
	}


	@Override
	public List<IndEmployeePayroll> getYTDIndEmployeePayrollList(Long candidateId, String startMonth) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Date date = null;
		if(StringUtils.isNotBlank(startMonth)) {
			date = new SimpleDateFormat("MMMM").parse(startMonth);
		}else {
			date = new SimpleDateFormat("MMMM").parse("January");
		}
		
		int fysMonth = date.getMonth() + 1;
		Date cDate = new Date();
 		int cMonth = cDate.getMonth() + 1;
 		
 		int totalMonths = 0;
 		
 		if(cMonth < fysMonth) {
 			totalMonths = 12 - fysMonth + cMonth;
 		}
 		if(cMonth > fysMonth) {
 			totalMonths = cMonth - fysMonth;
 		}
 		
 		Calendar cal = Calendar.getInstance();
 		cal.setTime(cDate);
 		cal.add(Calendar.MONTH, -totalMonths);
 		cal.add(Calendar.DATE, -(cal.get(Calendar.DATE)-1));
 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String updatedDateStr     = dateFormat.format(cal.getTime());
        Date updatedDate = dateFormat.parse(updatedDateStr);
        java.sql.Date sqlDate = new java.sql.Date(updatedDate.getTime());
       
		List<IndEmployeePayroll> indEmpPayrollList = null;
		String query = "";
		query = "from IndEmployeePayroll iep where iep.candidate =:candidateId and iep.indPaysheetFrequency.frequencyStartDate <= :sqlDate";
		indEmpPayrollList = session.createQuery(query).setLong("candidateId", candidateId).setDate("sqlDate", sqlDate).list();
		return indEmpPayrollList;
	}


	@Override
	public ResponseList<IndEmployeePayroll> getPayrollBankReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,
			OrgLocations orgLocations) {
    Session session = this.sessionFactory.getCurrentSession();
		
		Date fDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getFreqStartDate());
		Date tDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getFreqEndDate());
		java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
		java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
		
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (iep.employee.departmentId.departmentName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (candidate.firstName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
	           .append("OR (candidate.lastName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
	           .append("OR concat(candidate.firstName,candidate.lastName) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
		       .append("OR (iep.paymentAccount.accountNumber like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (iep.paymentAccount.bankName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (iep.paymentAccount.routingNumber like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
			   .append("OR iep.employee.employeeId like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		
	 
		List<IndEmployeePayroll> indEmployeePayrollList = null;
		List<Long> countlist  = null;
		//List Query
		String query = "from IndEmployeePayroll iep  left join fetch iep.candidate candidate left join fetch iep.indPaysheetFrequency ipf where  ipf.userGroup.id =:groupId and ipf.frequencyStartDate =:frequencyStartDate and ipf.frequencyEndDate =:frequencyEndDate and ipf.approverStatus ='APPROVED' and ipf.orgLocation.id =:orgLoactionId  "+query1+"";
		
		//count Query
		String countQuery  = "Select count (*) from IndEmployeePayroll iep where  iep.indPaysheetFrequency.userGroup.id =:groupId and iep.indPaysheetFrequency.frequencyStartDate =:frequencyStartDate and iep.indPaysheetFrequency.frequencyEndDate =:frequencyEndDate and iep.indPaysheetFrequency.approverStatus ='APPROVED' and iep.indPaysheetFrequency.orgLocation.id =:orgLoactionId "+query1+"";
		
			 indEmployeePayrollList = session.createQuery(query).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
			 countlist  = session.createQuery(countQuery).setDate("frequencyStartDate", freqStartDate).setDate("frequencyEndDate", freqEndDate).setLong("groupId", indEmployeePayrollDTO.getPayGroup()).setLong("orgLoactionId", orgLocations.getId()).list();
		Integer filterCount = countlist.iterator().next().intValue();
		
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
		}
		
		response.setData(indEmployeePayrollList);
		return response;
	}


	@Override
	public ResponseList<IndEmployeePayroll> getPayrollYTDSummaryReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
	Session session = this.sessionFactory.getCurrentSession();
		
		Date fDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getStartDate());
		java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
		Date tDate = null;
		if(!indEmployeePayrollDTO.getEndDate().isEmpty()) {
			 tDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getEndDate());
		}else {
			tDate = new Date();
		}
		
		java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
		
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (iep.employee.departmentId.departmentName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (iep.candidate.firstName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (iep.candidate.lastName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR concat(iep.candidate.firstName,iep.candidate.lastName) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
			   .append("OR iep.employee.employeeId like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		
	 
		List<IndEmployeePayroll> indEmployeePayrollList = null;
		List<Long> countlist  = null;
		
		List<Long> employeeList = new ArrayList<>();
		if(indEmployeePayrollDTO.getEmployeeIds() != null && !indEmployeePayrollDTO.getEmployeeIds().isEmpty()) {
			String[] orgDepts = indEmployeePayrollDTO.getEmployeeIds().split(",");
			for(int i=0; i < orgDepts.length; i++) {
				employeeList.add(Long.parseLong(orgDepts[i]));
			}
		}
		
		//List Query
		String query = "from IndEmployeePayroll iep left join fetch iep.indPaysheetFrequency ipf where  date(ipf.frequencyStartDate) BETWEEN :startDate and :endDate  and ipf.approverStatus ='APPROVED' and ipf.orgLocation.id =:orgLoactionId and iep.employee.id IN(:employeeList) "+query1+"";
		indEmployeePayrollList = session.createQuery(query).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).setLong("orgLoactionId", orgLocations.getId()).setParameterList("employeeList", employeeList).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
		
		//count Query
		String countQuery  = "Select count (*) from IndEmployeePayroll iep where  date(iep.indPaysheetFrequency.frequencyStartDate) BETWEEN :startDate and :endDate and iep.indPaysheetFrequency.approverStatus ='APPROVED' and iep.indPaysheetFrequency.orgLocation.id =:orgLoactionId and iep.employee.id IN(:employeeList) "+query1+"";
		countlist  = session.createQuery(countQuery).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).setLong("orgLoactionId", orgLocations.getId()).setParameterList("employeeList", employeeList).list();
		Integer filterCount = countlist.iterator().next().intValue();
		
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
		}
		for(IndEmployeePayroll iep: indEmployeePayrollList) {
			iep.setCandidateId(iep.getCandidate().getId());
		}
		response.setData(indEmployeePayrollList);
		return response;
	}


	@Override
	public ResponseList<IndEmployeePayroll> getPayrollAnnualTaxReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception {
   Session session = this.sessionFactory.getCurrentSession();
		
		
		ResponseList<IndEmployeePayroll> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (iep.employee.departmentId.departmentName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (iep.candidate.firstName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (iep.candidate.lastName like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR concat(iep.candidate.firstName,iep.candidate.lastName) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
			   .append("OR iep.employee.employeeId like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		
	 
		List<IndEmployeePayroll> indEmployeePayrollList = null;
		List<Long> countlist  = null;
		
		List<Date> dates =  iNDPayrollCommonUtil.getFinancialYearStartAndEndDateByOrgLocations(orgLocations, indEmployeePayrollDTO.getFinancialYear());
		Date sDate = dates.get(0);
		Date eDate = dates.get(1);
		
		//List Query
		String query = "from IndEmployeePayroll iep left join fetch iep.indPaysheetFrequency ipf where  date(ipf.frequencyStartDate) BETWEEN :startDate and :endDate  and ipf.approverStatus ='APPROVED' and ipf.orgLocation.id =:orgLoactionId "+query1+"";
		indEmployeePayrollList = session.createQuery(query).setLong("orgLoactionId", orgLocations.getId()).setDate("startDate", sDate).setDate("endDate", eDate).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
		
		//count Query
		String countQuery  = "Select count (*) from IndEmployeePayroll iep where  date(iep.indPaysheetFrequency.frequencyStartDate) BETWEEN :startDate and :endDate and iep.indPaysheetFrequency.approverStatus ='APPROVED' and iep.indPaysheetFrequency.orgLocation.id =:orgLoactionId  "+query1+"";
		countlist  = session.createQuery(countQuery).setLong("orgLoactionId", orgLocations.getId()).setDate("startDate", sDate).setDate("endDate", eDate).list();
		Integer filterCount = countlist.iterator().next().intValue();
		
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
		}
		for(IndEmployeePayroll iep: indEmployeePayrollList) {
			iep.setCandidateId(iep.getCandidate().getId());
		}
		response.setData(indEmployeePayrollList);
		return response;
	}


	@Override
	public List<IndEmployeePayrollDTO> getPayrollSalaryStatementReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,	OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
			List<Date> dates =  iNDPayrollCommonUtil.getFinancialYearStartAndEndDateByOrgLocations(orgLocations, indEmployeePayrollDTO.getFinancialYear());
			Date sDate = dates.get(0);
			Calendar sFCal = Calendar.getInstance();
			sFCal.setTime(sDate);
			 
			Date eDate = dates.get(1);
			Calendar eFCal = Calendar.getInstance();
			eFCal.setTime(eDate);
		
			java.sql.Date freqStartDate = null;
			java.sql.Date freqEndDate = null;
			
			 Date startDate = new SimpleDateFormat("MMMM").parse(indEmployeePayrollDTO.getSalaryMonthName());
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(startDate);
			 cal.set(Calendar.YEAR, sFCal.get(Calendar.YEAR));
			 if(cal.getTime().after(sDate) && cal.getTime().before(eDate)) {
				 sDate = cal.getTime();
				 cal.add(Calendar.MONTH, 1);  
				 cal.set(Calendar.DAY_OF_MONTH, 1);  
				 cal.add(Calendar.DATE, -1);  
			     eDate = cal.getTime();
				 freqStartDate = new java.sql.Date(sDate.getTime());
				 freqEndDate = new java.sql.Date(eDate.getTime());
			 }else {
				 cal.set(Calendar.YEAR, eFCal.get(Calendar.YEAR));
				 sDate = cal.getTime();
				 cal.add(Calendar.MONTH, 1);  
				 cal.set(Calendar.DAY_OF_MONTH, 1);  
				 cal.add(Calendar.DATE, -1);  
			     eDate = cal.getTime();
			     freqStartDate = new java.sql.Date(sDate.getTime());
				 freqEndDate = new java.sql.Date(eDate.getTime());
			 }
			 
			
			
			
			
	     
		String query =  " SELECT  can.ID as canId, "+
						" employee.EMPLOYEE_ID as employeeid, "+
				        " department.DEPARTMENT_NAME as departmentName, "+
						" employee.HIRE_DATE as deteOfJoin, "+
					    " can.FIRST_NAME as firstName, "+
				        " can.LAST_NAME as lastName, "+
				        " payHead.NAME as payHeadName, "+
				        " his.MONTHLY as payHeadAmount, "+
				        " payroll.GROSS_PAY as grossPay, "+
				        " payroll.NET_PAY as netPay, "+
				        " payHead.TYPE as payHeadType "+
				        
						" FROM PAYROLL_PAYHEAD payHead, IND_PAY_SHEET_FREQUENCY freq, IND_EMPLOYEE_PAYROLL payroll, "
						+ " ASSOCIATE_PAYROLL_PAYHEAD_HISTORY his, CANDIDATE can, EMPLOYEE employee, DEPARTMENT department "+
						" where payroll.IND_PAY_SHEET_FREQUENCY_ID = freq.ID "+
						" AND his.IND_EMPLOYEE_PAYROLL_ID = payroll.ID "+
						" AND his.PAYROLL_PAYHEAD = payHead.ID  "+
						" AND payroll.CANDIDATE_ID = can.ID "+
						" AND payroll.EMPLOYEE_ID = employee.ID "+ 
						" AND employee.DEPARTMENT_ID = department.ID "+
						" AND freq.ORG_LOCATION_ID =:orgloationId "+
						" AND ( date(freq.FREQUENCY_START_DATE) BETWEEN :startDate and :endDate ) "+ 
						" AND his.UPDATED_DATE = (select max(UPDATED_DATE) from ASSOCIATE_PAYROLL_PAYHEAD_HISTORY assHis where  assHis.IND_EMPLOYEE_PAYROLL_ID = payroll.ID and assHis.PAYROLL_PAYHEAD = payHead.ID) ";

		               Query queryObj = session.createSQLQuery(query);
					   queryObj.setResultTransformer(Transformers.aliasToBean(IndEmployeePayrollDTO.class));
					   List<IndEmployeePayrollDTO> projectassignTaskList = queryObj.setLong("orgloationId", orgLocations.getId()).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).list();
					return projectassignTaskList;
	}


	@Override
	public ResponseList<IndEmployeePayrollDTO> getPayrollESIAndPFReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<IndEmployeePayrollDTO> response = new ResponseList<>();
		List<Long> countlist  = null;
		
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (employee.EMPLOYEE_ID like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (can.FIRST_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (can.LAST_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR concat(can.FIRST_NAME,can.LAST_NAME) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
		       .append("OR (pfEsi.UAN_NUMBER like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (pfEsi.PF_NUMBER like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (pfEsi.ESI_NUMBER like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
			   .append("OR department.DEPARTMENT_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		
		 String[] selectFromMonths = indEmployeePayrollDTO.getFromMonth().split("-");
		 Date startDate = new SimpleDateFormat("MMMM").parse(selectFromMonths[0]);
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(startDate);
		 cal.set(Calendar.YEAR, Integer.valueOf(selectFromMonths[1]));
		 Date sDate = cal.getTime();
		 java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
		 
		 
		 String[] selectToMonth = indEmployeePayrollDTO.getToMonth().split("-");
		 Date endDate = new SimpleDateFormat("MMMM").parse(selectToMonth[0]);
		 Calendar endCal = Calendar.getInstance();
		 endCal.setTime(endDate);
		 endCal.set(Calendar.YEAR, Integer.valueOf(selectToMonth[1]));
		 endCal.add(Calendar.MONTH, 1);  
		 endCal.set(Calendar.DAY_OF_MONTH, 1);  
		 endCal.add(Calendar.DATE, -1);  
	     Date eeDate = endCal.getTime();
	     java.sql.Date freqEndDate = new java.sql.Date(eeDate.getTime());
	     String typeQury = "";
	     if(indEmployeePayrollDTO.getType().equalsIgnoreCase("ESI")) {
	    	 typeQury = typeQury + " AND pfEsi.ELIGIBLE_FOR_ESI = 'Y' ";
		  }
		  if(indEmployeePayrollDTO.getType().equalsIgnoreCase("PF")) {
			  typeQury = typeQury + "AND pfEsi.ELIGIBLE_FOR_PF = 'Y' ";
		  }
		  
		  String query =  " SELECT  can.ID as canId, "+ 
						  "	employee.EMPLOYEE_ID as employeeid, "+
						  "	department.DEPARTMENT_NAME as departmentName, "+
						  "	employee.HIRE_DATE as deteOfJoin, "+
						  "	can.FIRST_NAME as firstName, "+
						  "	can.LAST_NAME as lastName,  "+
						  "	payHead.NAME as payHeadName, "+
						  "	his.PAYROLL_PAYHEAD as payrollPayheadId, "+
						  "	his.MONTHLY as payHeadAmount, "+
						  "	payHead.SHORT_CODE as shortCode, "+
						  "	pfESI.UAN_NUMBER as uanNumber, "+
						  "	pfESI.PF_NUMBER as pfNumber, "+
						  "	pfESI.ESI_NUMBER as esiNumber, "+
						  "	pfESI.ELIGIBLE_FOR_ESI as eligibleForEsi, "+
						  "	pfESI.ELIGIBLE_FOR_PF as eligibleForPf, "+
						  "	tax.EMPLOYEE_ESI as employeeEsi, "+
						  "	tax.EMPLOYER_ESI as employerEsi, "+
						  "	tax.EMPLOYEE_PF as employeePf, "+
						  "	tax.EMPLOYER_PF as employerPf  "+

						  "	FROM PAYROLL_PAYHEAD payHead, IND_PAY_SHEET_FREQUENCY freq, IND_EMPLOYEE_PAYROLL payroll, "+ 
						  "	ASSOCIATE_PAYROLL_PAYHEAD_HISTORY his, CANDIDATE can, EMPLOYEE employee, DEPARTMENT department, "+
						  "	ASSOCIATE_PF_ESI_DETAILS pfEsi,CENTRAL_TAX tax "+
						  "	where payroll.IND_PAY_SHEET_FREQUENCY_ID = freq.ID "+  
						  "	AND his.PAYROLL_PAYHEAD = payHead.ID  "+
						  "	AND payroll.CANDIDATE_ID = can.ID  "+
						  "	AND payroll.EMPLOYEE_ID = employee.ID "+  
						  "	AND employee.DEPARTMENT_ID = department.ID "+  
						  "	AND payroll.CANDIDATE_ID = pfESI.CANDIDATE_ID "+
						  "	AND freq.ORG_LOCATION_ID =:orgloationId  "+
						  "	AND ( date(freq.FREQUENCY_START_DATE) BETWEEN :startDate and :endDate) "+ 
						  "	AND tax.CT_FINANCIAL_YEAR  =:financialYear "+
						  "	AND  payHead.SHORT_CODE  =:type "+
						  " "+typeQury+" "+query1+" "+ 
						  "	AND his.MONTHLY > 0 "+
						  " AND department.ID =:departmentId"+
						  "	AND his.UPDATED_DATE = (select max(UPDATED_DATE) from ASSOCIATE_PAYROLL_PAYHEAD_HISTORY assHis where  assHis.IND_EMPLOYEE_PAYROLL_ID = payroll.ID and assHis.PAYROLL_PAYHEAD = payHead.ID)"; 
		
		  Query queryObj = session.createSQLQuery(query);
		   queryObj.setResultTransformer(Transformers.aliasToBean(IndEmployeePayrollDTO.class));
		   List<IndEmployeePayrollDTO> indPayrollESIAndPFList = queryObj.setBigInteger("departmentId", BigInteger.valueOf(Long.parseLong(indEmployeePayrollDTO.getDepartmentId()))).setString("financialYear", indEmployeePayrollDTO.getFinancialYear()).setString("type", indEmployeePayrollDTO.getType()).setLong("orgloationId", orgLocations.getId()).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).list();

		 //count Query
String countQuery  =  " select count(*) FROM PAYROLL_PAYHEAD payHead, IND_PAY_SHEET_FREQUENCY freq, IND_EMPLOYEE_PAYROLL payroll, "+ 
					  "	ASSOCIATE_PAYROLL_PAYHEAD_HISTORY his, CANDIDATE can, EMPLOYEE employee, DEPARTMENT department, "+
					  "	ASSOCIATE_PF_ESI_DETAILS pfEsi,CENTRAL_TAX tax "+
					  "	where payroll.IND_PAY_SHEET_FREQUENCY_ID = freq.ID "+  
					  "	AND his.PAYROLL_PAYHEAD = payHead.ID  "+
					  "	AND payroll.CANDIDATE_ID = can.ID  "+
					  "	AND payroll.EMPLOYEE_ID = employee.ID "+  
					  "	AND employee.DEPARTMENT_ID = department.ID "+  
					  "	AND payroll.CANDIDATE_ID = pfESI.CANDIDATE_ID "+
					  "	AND freq.ORG_LOCATION_ID =:orgloationId  "+
					  "	AND ( date(freq.FREQUENCY_START_DATE) BETWEEN :startDate and :endDate) "+ 
					  "	AND tax.CT_FINANCIAL_YEAR  =:financialYear "+
					  "	AND  payHead.SHORT_CODE  =:type "+
					  " "+typeQury+" "+query1+" "+ 
					  "	AND his.MONTHLY > 0 "+
					  " AND department.ID =:departmentId"+
					  "	AND his.UPDATED_DATE = (select max(UPDATED_DATE) from ASSOCIATE_PAYROLL_PAYHEAD_HISTORY assHis where  assHis.IND_EMPLOYEE_PAYROLL_ID = payroll.ID and assHis.PAYROLL_PAYHEAD = payHead.ID)"; 

			Query countQueryObj = session.createSQLQuery(countQuery);
			countlist = countQueryObj.setBigInteger("departmentId", BigInteger.valueOf(Long.parseLong(indEmployeePayrollDTO.getDepartmentId()))).setString("financialYear", indEmployeePayrollDTO.getFinancialYear()).setString("type", indEmployeePayrollDTO.getType()).setLong("orgloationId", orgLocations.getId()).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).list();
	        Integer filterCount = countlist.size();
			
			int totalCount = 0;
			if (indEmployeePayrollDTO != null) {
				if(countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.size();
				}
			} 
			response.setRecordsTotal(totalCount);
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				response.setRecordsFiltered(filterCount);
			}else {
				response.setRecordsFiltered(totalCount);
			}
			 
			response.setData(indPayrollESIAndPFList);
			return response;
	}
	
	
	@Override
	public ResponseList<IndEmployeePayrollDTO> getPayrollSalaryRegisterReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) {
	Session session = this.sessionFactory.getCurrentSession();
		
		Date fDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getStartDate());
		java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
		Date tDate = null;
		if(!indEmployeePayrollDTO.getEndDate().isEmpty()) {
			 tDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), indEmployeePayrollDTO.getEndDate());
		}else {
			tDate = new Date();
		}
		
		java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
		
		ResponseList<IndEmployeePayrollDTO> response = new ResponseList<>();
		StringBuilder query1 = new StringBuilder("");
		if(indEmployeePayrollDTO.getSearchValue() !=null && !"".equalsIgnoreCase(indEmployeePayrollDTO.getSearchValue())) {
		 query1.append("and (dept.DEPARTMENT_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' ")
		       .append("OR (can.FIRST_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR (can.LAST_NAME like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )")
		       .append("OR concat(can.FIRST_NAME,can.LAST_NAME) like '%" + indEmployeePayrollDTO.getSearchValue() + "%' ")
			   .append("OR emp.EMPLOYEE_ID like '%" + indEmployeePayrollDTO.getSearchValue().replace("'", "") + "%' )");
		}
		List<IndEmployeePayrollDTO> indEmployeePayrollList = null;
		List<Long> employeeList = new ArrayList<>();
		if(indEmployeePayrollDTO.getEmployeeIds() != null && !indEmployeePayrollDTO.getEmployeeIds().isEmpty()) {
			String[] orgDepts = indEmployeePayrollDTO.getEmployeeIds().split(",");
			for(int i=0; i < orgDepts.length; i++) {
				employeeList.add(Long.parseLong(orgDepts[i]));
			}
		}
		
		//List Query
		String query = " SELECT DISTINCT asih.ANNUAL_SALARY as annualSalary, "+
				       " emp.EMPLOYEE_ID as employeeid, "+
					   " can.FIRST_NAME as firstName, "+
					   " can.LAST_NAME as lastName, "+
					   " asih.INCREMENT_PERCENTAGE as incresePercentage, "+
					   " asih.EFFECTIVE_DATE as effectiveDate, "+
					   " dept.DEPARTMENT_NAME as departmentName "+
					   " FROM ASSOCIATE_SALARY_IND asi, CANDIDATE can,EMPLOYEE emp,DEPARTMENT dept,ASSOCIATE_SALARY_HISTORY_IND asih "+
					   " where can.ID = asi.CANDIDATE_ID AND asi.ORG_LOCATION_ID =:orgLoactionId "+
					   " AND can.CONSULTANT_USER_ID = emp.USER_ID "+
					   " AND emp.ID IN(:employeeList) "+
					   " AND emp.DEPARTMENT_ID = dept.ID "+
					   " AND  asi.ID =asih.ASSOCIATE_SALARY_IND  "+
					   " AND ( date(asih.EFFECTIVE_DATE) BETWEEN :startDate and :endDate) "+query1+" ";

		Query queryObj = session.createSQLQuery(query);
		queryObj.setResultTransformer(Transformers.aliasToBean(IndEmployeePayrollDTO.class));
		indEmployeePayrollList = queryObj.setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).setLong("orgLoactionId", orgLocations.getId()).setParameterList("employeeList", employeeList).setFirstResult(indEmployeePayrollDTO.getStart()).setMaxResults(indEmployeePayrollDTO.getLength()).list();
        Integer filterCount = indEmployeePayrollList.size();
		
		int totalCount = 0;
		if (indEmployeePayrollDTO != null) {
			if(indEmployeePayrollList != null && !indEmployeePayrollList.isEmpty()) {
				totalCount = indEmployeePayrollList.size();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		response.setData(indEmployeePayrollList);
		return response;
	}
}