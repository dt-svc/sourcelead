package com.bis.sourcelead.payroll.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollReportDao;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PayrollReportDaoImpl implements PayrollReportDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollReportDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired 
	CommonUtil commonUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	DepartmentService departmentService;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<EmployeePayroll> getPayrollSummaryByPayPeriodAndPayCheckDate(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		ResponseList<EmployeePayroll> response = new ResponseList<>();
		
		try {
			Date date = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getPayCheckDate());
			java.sql.Date payCheckDate = new java.sql.Date(date.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			List<EmployeePayroll> payrollList = null;
			
			String qry = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getSortColumn())) {
				qry = " order by payroll."+ payrollReportDTO.getSortColumn() +" "+payrollReportDTO.getSortOrder();
			}
			
			String query = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.payCheckDate = :payCheckDatde and payroll.submitForAproverStatus = 'APPROVED'";
			String countQuery = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.payCheckDate = :payCheckDatde and payroll.submitForAproverStatus = 'APPROVED'";
			
			if(payrollReportDTO.getSearchValue().contains("/") || payrollReportDTO.getSearchValue().contains("-")){
				String convertedDate = payrollReportDTO.getSearchValue();
				if(payrollReportDTO.getSearchValue().contains("/")){
					convertedDate = payrollReportDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.payCheckDate = :payCheckDatde and payroll.submitForAproverStatus = 'APPROVED'";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("payCheckDatde", sqlDate);
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder searchQuery = new StringBuilder("");
				if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%') ");
				}
			Query queryObj = session.createQuery(query + searchQuery.toString() + qry);
			if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollReportDTO.getStart());
				queryObj.setMaxResults(payrollReportDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("payCheckDatde", payCheckDate);
			}
			payrollList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("payCheckDatde", payCheckDate).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollReportDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + searchQuery.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("payCheckDatde", payCheckDate).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(payrollList);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		ResponseList<CheckRegisterReportDTO> response = new ResponseList<>();
		try {
			Date sDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());  //new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqStartDate());  
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			List<CheckRegisterReportDTO> payrollList = null;
			StringBuilder searchQuery = new StringBuilder("");
			/*String qry = "";
			if (StringUtils.isNotBlank(payrollReportDTO.getSortColumn())) {
				qry = " order by payroll." + payrollReportDTO.getSortColumn() + " "
						+ payrollReportDTO.getSortOrder();
			}*/
			StringBuilder query = new StringBuilder();
			query.append("select payroll.ID as empPayrollId,") 
			.append("payroll.EMPLOYEE_ID as empId,")
			.append("payroll.CANDIDATE_ID as candidateId,")
			.append("grp.DEPARTMENT_ID as departmentId,")
			.append("can.FIRST_NAME as firstName,") 
			.append("can.LAST_NAME as lastName,") 
			.append("payroll.PAY_TYPE as payType,") 
			.append("payroll.NET_PAY as netPay,")
			.append("payroll.TAXES as taxes,")
			.append("payroll.GROSS_PAY as grossPay,")
			.append("sheet.FREQUENCY_START_DATE as freqStartDate,")
			.append("sheet.FREQUENCY_END_DATE as freqEndDate,")
			.append("sheet.PAY_CHECK_DATE as payCheckDate ")
			.append(" from US_EMP_PAYROLL payroll,")
			.append(" ASSOCIATE_BANK_DATA bank, ")
			.append(" BANK_DATA_HIS bankHis, ")  
			.append(" PAY_SHEET_FREQUENCY sheet,")
			.append(" CANDIDATE can,USER_GROUP grp ")
			.append(" where sheet.ID = payroll.PAY_SHEET_FREQUENCY_ID ")
			.append(" and payroll.CANDIDATE_ID = bank.CANDIDATE_ID ")
			.append(" and payroll.ID = bankHis.PAYROLL_ID ")
			.append(" and bankHis.ASSOCIATE_BANK_ID = bank.ID ")
			.append(" and payroll.CANDIDATE_ID = can.ID ")
			.append(" and sheet.GROUP_ID = grp.ID ")
			.append(" and bankHis.ACCOUNT_TYPE = :depositType")
			.append(" and payroll.SUBMIT_FOR_APROVER_STATUS = 'APPROVED' ")
			.append(" and sheet.ORG_LOCATION_ID = :orgLocationId and sheet.GROUP_ID = :groupId and sheet.FREQUENCY_START_DATE = :freqStartDate ")
			.append(" and sheet.FREQUENCY_END_DATE = :freqEndDate ");
//			String query = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.payCheckDate = :payCheckDatde and payroll.submitForAproverStatus = 'APPROVED'";
			StringBuilder countQuery = new StringBuilder();
			countQuery.append("select count(*) from US_EMP_PAYROLL payroll,ASSOCIATE_BANK_DATA bank,PAY_SHEET_FREQUENCY sheet,CANDIDATE can,USER_GROUP grp ")
					.append(" where sheet.ID = payroll.PAY_SHEET_FREQUENCY_ID ") 
					.append(" and payroll.CANDIDATE_ID = bank.CANDIDATE_ID ")
					.append(" and payroll.CANDIDATE_ID = can.ID ")
					.append(" and sheet.GROUP_ID = grp.ID ")
					.append(" and bank.ACCOUNT_TYPE = :depositType")
					.append(" and sheet.ORG_LOCATION_ID = :orgLocationId and sheet.GROUP_ID = :groupId and sheet.FREQUENCY_START_DATE = :freqStartDate ") 
					.append(" and sheet.FREQUENCY_END_DATE = :freqEndDate ");
			//			String countQuery = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.payCheckDate = :payCheckDatde and payroll.submitForAproverStatus = 'APPROVED'";
			if (payrollReportDTO.getSearchValue() != null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( can.FIRST_NAME like '%"+ payrollReportDTO.getSearchValue()+ "%' ")
							  .append("OR  can.LAST_NAME like '%" + payrollReportDTO.getSearchValue() + "%')");
			}
//			else {

				Query queryObj = session.createSQLQuery(query.toString() + searchQuery.toString());
				if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
					queryObj.setFirstResult(payrollReportDTO.getStart());
					queryObj.setMaxResults(payrollReportDTO.getLength());
				}
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate", freqEndDate).setParameter("depositType", payrollReportDTO.getDepositType());
				queryObj.setResultTransformer(Transformers.aliasToBean(CheckRegisterReportDTO.class));
				payrollList = queryObj.list();
				int count = 0;
				int filteredCount = 0;
//				Query queryObj = session.createSQLQuery(query.toString() + searchQuery.toString());
				Query  countQry = session.createSQLQuery(countQuery.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate", freqEndDate).setParameter("depositType", payrollReportDTO.getDepositType());
//				countQry.setResultTransformer(Transformers.aliasToBean(CheckRegisterReportDTO.class));
				List<BigInteger> countlist = countQry.list();
				if (countlist != null && !countlist.isEmpty()) {
					count = countlist.iterator().next().intValue();
				}
				response.setRecordsTotal(count);
				if (payrollReportDTO != null) {
					Query  filteredCountQry= session.createSQLQuery(countQuery.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate", freqEndDate).setParameter("depositType", payrollReportDTO.getDepositType());
//					filteredCountQry.setResultTransformer(Transformers.aliasToBean(CheckRegisterReportDTO.class));
					List<BigInteger> filteredCountlist = filteredCountQry.list();
					if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
						filteredCount = filteredCountlist.iterator().next().intValue();
					}
					response.setRecordsFiltered(filteredCount);
				}
				for(CheckRegisterReportDTO reportDTo : payrollList) {
					if(reportDTo.getEmpId() != null) {
						Employees employee = employeesService.getEmployee(reportDTo.getEmpId().toString());
						reportDTo.setEmployeeId(employee.getEmployeeId());
					}
					if(StringUtils.isNotBlank(reportDTo.getDepartmentId())) {
						Department dept = departmentService.getDepartmentById(Long.parseLong(reportDTo.getDepartmentId()));
						reportDTo.setDepartmentId(dept.getDepartmentName());
					}
					reportDTo.setTotalChecks(count);
				}
				response.setData(payrollList);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<DeductionHistory> getContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
        ResponseList<DeductionHistory> response = new ResponseList<>();
		
		try {
			Date sDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());  //new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqStartDate());  
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			
			List<Long> contributionIds = Stream.of(payrollReportDTO.getContributionId().split(",")).map(Long::parseLong).collect(Collectors.toList());
			List<DeductionHistory> payrollList = null;
			
			String qry = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getSortColumn())) {
				if("departmentName".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.employee.departmentId."+ payrollReportDTO.getSortColumn() +" "+payrollReportDTO.getSortOrder();
				}else if("payrollDeductionType".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.contributionId.contributionTypeId.deductionTypeId.payrollDeductionType."+ payrollReportDTO.getSortColumn() +" "+payrollReportDTO.getSortOrder();
				}else if("employeeId".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.employee.employeeId."+payrollReportDTO.getSortColumn() + " "+payrollReportDTO.getSortOrder();
				}else if("firstName".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.candidate.firstName."+payrollReportDTO.getSortColumn() + " "+payrollReportDTO.getSortOrder();
				}
			}
			String query =      "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.contributionId.contributionTypeId.id IN (:contributionList)";
			String countQuery = "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.contributionId.contributionTypeId.id IN (:contributionList)";
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( ded.payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.contributionId.contributionTypeId.deductionTypeId.payrollScheduled like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.contributionId.contributionTypeId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
					    .append("OR  ded.payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%') ");
			}
			Query queryObj = session.createQuery(query + searchQuery.toString() + qry);
			if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollReportDTO.getStart());
				queryObj.setMaxResults(payrollReportDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("contributionList", contributionIds);
			}
			payrollList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("contributionList", contributionIds).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollReportDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + searchQuery.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("contributionList", contributionIds).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(payrollList);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	@Transactional
	public ResponseList<DeductionHistory> getDeductionReports(OrgLocations orgLocation,PayrollReportDTO payrollReportDTO) {
		
     ResponseList<DeductionHistory> response = new ResponseList<>();
		
		try {
			
			
			Date sDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			
			Session session = this.sessionFactory.getCurrentSession();
			
			List<Long> deductionIds = Stream.of(payrollReportDTO.getDeductionId().split(",")).map(Long::parseLong).collect(Collectors.toList());
			List<DeductionHistory> payrollList = null;
			
			String qry = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getSortColumn())) {
				if("departmentName".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.employee.departmentId."+ payrollReportDTO.getSortColumn() +" "+payrollReportDTO.getSortOrder();
				}else if("payrollDeductionType".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.deductionId.deductionTypeId.payrollDeductionType."+ payrollReportDTO.getSortColumn() +" "+payrollReportDTO.getSortOrder();
				}else if("employeeId".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.employee.employeeId."+payrollReportDTO.getSortColumn() + " "+payrollReportDTO.getSortOrder();
				}else if("firstName".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by ded.payroll.candidate.firstName."+payrollReportDTO.getSortColumn() + " "+payrollReportDTO.getSortOrder();
				}
			}
			String query =      "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.deductionId.deductionTypeId.id IN (:deductionList)";
			String countQuery = "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.deductionId.deductionTypeId.id IN (:deductionList)";
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( ded.payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.deductionId.deductionTypeId.payrollScheduled like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.deductionId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
					    .append("OR  ded.payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%') ");
			}
			Query queryObj = session.createQuery(query + searchQuery.toString() + qry);
			if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollReportDTO.getStart());
				queryObj.setMaxResults(payrollReportDTO.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("deductionList", deductionIds);
			}
			payrollList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("deductionList", deductionIds).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollReportDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + searchQuery.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("deductionList", deductionIds).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(payrollList);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CheckRegisterReportDTO> getexportToPdfCheckRegisterReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		List<CheckRegisterReportDTO> payrollList = null;
		try {
			Date sDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder searchQuery = new StringBuilder("");
			StringBuilder query = new StringBuilder();
		/*	query.append("select payroll.ID as empPayrollId,")
			.append("payroll.EMPLOYEE_ID as empId,")
			.append("grp.DEPARTMENT_ID as departmentId,")
			.append("can.FIRST_NAME as firstName,")
			.append("can.LAST_NAME as lastName,")
			.append("payroll.PAY_TYPE as payType,")
			.append(" payroll.NET_PAY as netPay,")
			.append("payroll.TAXES as taxes,")
			.append("payroll.GROSS_PAY as grossPay,")
			.append(" payroll.TYPE as type,")
			.append(" payroll.PAY_PERIOD_NUM as runNo,")
			.append(" payroll.CREATED_DATE as runDate,")
			.append("sheet.FREQUENCY_START_DATE as freqStartDate,")
			.append("sheet.FREQUENCY_END_DATE as freqEndDate,")
			.append("grp.GROUP_NAME as payGroupName,")
			.append("bankHis.BANK_NAME as bankName,")
			.append("bankHis.ACCOUNT_NUMBER as accountNum,")
			.append(" bankHis.ROUTING_NUMBER as routingNum,")
			.append(" grp.PAY_FREQUENCY as payFrequency,")
			.append(" sheet.PAY_CHECK_DATE as payCheckDate")
			.append(" from US_EMP_PAYROLL payroll, ASSOCIATE_BANK_DATA bank,")
		    .append(" BANK_DATA_HIS bankHis,  PAY_SHEET_FREQUENCY sheet,")
		    .append(" CANDIDATE can,USER_GROUP grp ")
		    .append(" where sheet.ID = payroll.PAY_SHEET_FREQUENCY_ID") 
		    .append(" and bankHis.ASSOCIATE_BANK_ID = bank.ID ")
		    .append(" and payroll.CANDIDATE_ID = bank.CANDIDATE_ID")
		    .append(" and payroll.CANDIDATE_ID = can.ID ")
		    .append(" and payroll.ID = bankHis.PAYROLL_ID")
		    .append(" and sheet.GROUP_ID = grp.ID")
		    .append(" and bankHis.ACCOUNT_TYPE = :depositType ") 
		    .append(" and payroll.SUBMIT_FOR_APROVER_STATUS = 'APPROVED'")
		    .append(" and sheet.ORG_LOCATION_ID = :groupId")
		    .append(" and sheet.GROUP_ID = :orgLocationId")
		    .append(" and sheet.FREQUENCY_START_DATE = :freqStartDate")
		    .append(" and sheet.FREQUENCY_END_DATE = :freqEndDate");*/
			query.append("select payroll.ID as empPayrollId,")
			.append("payroll.EMPLOYEE_ID as empId,")
			.append("grp.DEPARTMENT_ID as departmentId,")
			.append("can.FIRST_NAME as firstName,")
			.append("can.LAST_NAME as lastName,")
			.append("payroll.PAY_TYPE as payType,")
			.append("payroll.NET_PAY as netPay,")
			.append("payroll.TAXES as taxes,")
			.append("payroll.GROSS_PAY as grossPay,")
			.append("payroll.TYPE as type,")
			.append("payroll.PAY_PERIOD_NUM as runNo,")
			.append("payroll.CREATED_DATE as runDate,")
			.append("sheet.FREQUENCY_START_DATE as freqStartDate,")
			.append("sheet.FREQUENCY_END_DATE as freqEndDate,")
			.append("grp.GROUP_NAME as payGroupName,")
			.append("bankHis.BANK_NAME as bankName,")
			.append("bankHis.ACCOUNT_NUMBER as accountNum,")
			.append("bankHis.ROUTING_NUMBER as routingNum,")
			.append("grp.PAY_FREQUENCY as payFrequency,")
			.append("sheet.PAY_CHECK_DATE as payCheckDate ")
			.append(" from US_EMP_PAYROLL payroll,")
			.append(" ASSOCIATE_BANK_DATA bank, ")  
			.append(" BANK_DATA_HIS bankHis, ")  
			.append(" PAY_SHEET_FREQUENCY sheet,")
			.append(" CANDIDATE can,USER_GROUP grp ")
			/*.append(" where sheet.ID = payroll.PAY_SHEET_FREQUENCY_ID ")
			.append(" and bankHis.ASSOCIATE_BANK_ID = bank.ID ")
			.append(" and payroll.CANDIDATE_ID = bank.CANDIDATE_ID ")
			.append(" and payroll.CANDIDATE_ID = can.ID ")
			.append(" and sheet.GROUP_ID = grp.ID ")*/
			.append(" where sheet.ID = payroll.PAY_SHEET_FREQUENCY_ID") 
		    .append(" and bankHis.ASSOCIATE_BANK_ID = bank.ID ")
		    .append(" and payroll.CANDIDATE_ID = bank.CANDIDATE_ID")
		    .append(" and payroll.CANDIDATE_ID = can.ID ")
		    .append(" and payroll.ID = bankHis.PAYROLL_ID")
		    .append(" and sheet.GROUP_ID = grp.ID")
			.append(" and bankHis.ACCOUNT_TYPE = :depositType")
			.append(" and payroll.SUBMIT_FOR_APROVER_STATUS = 'APPROVED' ")
			.append(" and sheet.ORG_LOCATION_ID = :orgLocationId and sheet.GROUP_ID = :groupId and sheet.FREQUENCY_START_DATE = :freqStartDate ").append(" and sheet.FREQUENCY_END_DATE = :freqEndDate ");
			if (payrollReportDTO.getSearchValue() != null
					&& !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
				searchQuery.append(" and ( can.FIRST_NAME like '%"+ payrollReportDTO.getSearchValue()+ "%' ")
						   .append("OR  can.LAST_NAME like '%"+ payrollReportDTO.getSearchValue() + "%')");
			}
			Query queryObj = session.createSQLQuery(query.toString()+searchQuery.toString());
			queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate", freqEndDate).setParameter("depositType", payrollReportDTO.getDepositType());
			queryObj.setResultTransformer(Transformers.aliasToBean(CheckRegisterReportDTO.class));
			payrollList = queryObj.list();
			for (CheckRegisterReportDTO reportDTo : payrollList) {
				if (reportDTo.getEmpId() != null) {
					Employees employee = employeesService.getEmployee(reportDTo.getEmpId().toString());
					reportDTo.setEmployeeId(employee.getEmployeeId());
				}
				if (StringUtils.isNotBlank(reportDTo.getDepartmentId())) {
					Department dept = departmentService.getDepartmentById(Long.parseLong(reportDTo.getDepartmentId()));
					reportDTo.setDepartmentId(dept.getDepartmentName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payrollList;
	}

	@Override
	@Transactional
	public List<DeductionHistory> exportToPdfContributionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		List<DeductionHistory> payrollList = null;
		
		try {
			Date sDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqStartDate());  
			Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			
			List<Long> contributionIds = Stream.of(payrollReportDTO.getContributionId().split(",")).map(Long::parseLong).collect(Collectors.toList());
			
			String query =      "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.contributionId.contributionTypeId.id IN (:contributionList)";
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( ded.payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.contributionId.contributionTypeId.deductionTypeId.payrollScheduled like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.contributionId.contributionTypeId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
					    .append("OR  ded.payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%') ");
			}
			Query queryObj = session.createQuery(query + searchQuery.toString());
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("contributionList", contributionIds);
			payrollList = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return payrollList;
	}
	
	@Override
	@Transactional
	public List<DeductionHistory> exportToPdfDeductionReport(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) {
			List<DeductionHistory> payrollList = null;
		
		try {
			Date sDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqStartDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqStartDate());  
			Date eDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFreqEndDate());//new SimpleDateFormat("yyyy-MM-dd").parse(payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(sDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(eDate.getTime());
			Session session = this.sessionFactory.getCurrentSession();
			
			List<Long> deductionIds = Stream.of(payrollReportDTO.getDeductionId().split(",")).map(Long::parseLong).collect(Collectors.toList());
			
			String query =      "from DeductionHistory ded where ded.payroll.paysheetFrequency.userGroup.id = :groupId and ded.payroll.paysheetFrequency.orgLocation.id = :orgLocationId and ded.payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and ded.payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and ded.payroll.submitForAproverStatus = 'APPROVED' and ded.deductionId.deductionTypeId.id IN (:deductionList)";
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ( ded.payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.deductionId.deductionTypeId.payrollScheduled like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.deductionId.deductionTypeId.payrollDeductionType like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  ded.payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
					    .append("OR  ded.payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%') ");
			}
			Query queryObj = session.createQuery(query + searchQuery.toString());
				queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", Long.parseLong(payrollReportDTO.getGroupId())).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).setParameterList("deductionList", deductionIds);
			payrollList = queryObj.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return payrollList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<AssociateWithHoldingDTO> getAssociateWithHoldingReport(OrgLocations orgLocation, AssociateWithHoldingDTO assWithHoldingDTO)
	{
		ResponseList<AssociateWithHoldingDTO>  response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<AssociateWithHoldingDTO> assoWithHoldingList = null;
			List<Long> countlist = null;
			
			String deptQry = "";
			String empTypeQry = "";
			
			if(StringUtils.isNotBlank(assWithHoldingDTO.getDept())) {
				deptQry = " AND emp.DEPARTMENT_ID IN (:deptList)";
			}
			if(StringUtils.isNotBlank(assWithHoldingDTO.getEmpType())) {
				empTypeQry = " AND can.DESIGNATION_TYPE IN (:empTypeList)";
			}
			String qry = "";
				if (StringUtils.isNotBlank(assWithHoldingDTO.getSortColumn())) {
					if("employeeId".equalsIgnoreCase(assWithHoldingDTO.getSortColumn())) {
						qry = " order by emp.EMPLOYEE_ID "+ assWithHoldingDTO.getSortOrder();
					}else if("empName".equalsIgnoreCase(assWithHoldingDTO.getSortColumn())) {
						qry = " order by can.FIRST_NAME "+ assWithHoldingDTO.getSortOrder();
					}else if("workLocation".equalsIgnoreCase(assWithHoldingDTO.getSortColumn())) {
						qry = " order by loc.LOCATION "+ assWithHoldingDTO.getSortOrder();
					}else if("residenceStateCode".equalsIgnoreCase(assWithHoldingDTO.getSortColumn())) {
						qry = " order by can.RESIDENCE_STATE_CODE "+ assWithHoldingDTO.getSortOrder();
					}
			}
			StringBuilder query = new StringBuilder();
			query.append("SELECT can.ID as candidateId,")
			.append("can.FIRST_NAME as firstName,")
			.append("can.LAST_NAME as lastName,")
			.append("emp.EMPLOYEE_ID as employeeId,")
			.append("emp.DEPARTMENT_ID as departmentId,")
			.append("loc.LOCATION as workLocation,")
			.append("usr.SSN as ssnNumber,")
			.append("can.RESIDENCE_STATE_CODE residenceStateCode,")
			.append("can.DESIGNATION_TYPE employeeMentType,")
			.append("withHolding.FEDERAL_TAX_FILLIING_STATUS as federalFillingStatus,")
			.append("withHolding.FEDERAL_TAX_ALLOWANCES as federalTaxAllowances,")
			.append("withHolding.FEDERAL_TAX_ADDITIONNAL_WITH_HOLDING as federalAdditinalWithHolding,")
			.append("withHolding.RESIDENCE_TAX_FILLIING_STATUS as stateFillingStatus,")
			.append("withHolding.RESIDENCE_TAX_ALLOWANCES stateTaxAllowances,")
			.append("withHolding.RESIDENCE_TAX_ADDITIONNAL_WITH_HOLDING as stateAdditionalWithHolding")
			.append(" FROM sourcelead.CANDIDATE can")
			.append(" left join  ASSOCIATE_TAX withHolding ON withHolding.CANDIDATE_ID = can.ID") 
			.append(" left join EMPLOYEE emp ON emp.USER_ID = can.CONSULTANT_USER_ID")
			.append(" left join ORG_LOCATIONS loc ON loc.ID = can.WORK_LOCATION")
			.append(" left join USER usr ON usr.ID = can.CONSULTANT_USER_ID")
			.append(" where  can.IS_ASSOCIATE = 'Y' AND can.ORG_LOCATION_ID = :orgLocationId ");
			StringBuilder countQuery = new StringBuilder();
			
			countQuery.append("select count(*) FROM Candidate can where can.isAssociate = 'Y' and can.orgLocationId.id = :orgLocationId ");
			
			StringBuilder searchQuery = new StringBuilder("");
			if (assWithHoldingDTO.getSearchValue() != null && !"".equalsIgnoreCase(assWithHoldingDTO.getSearchValue())) {
					searchQuery.append(" and ( can.FIRST_NAME like '%"+ assWithHoldingDTO.getSearchValue()+ "%' ")
							  .append("OR  can.LAST_NAME like '%" + assWithHoldingDTO.getSearchValue() + "%'")
							  .append("OR  loc.LOCATION like '%" + assWithHoldingDTO.getSearchValue() + "%'")
							  .append("OR  withHolding.FEDERAL_TAX_FILLIING_STATUS like '%" + assWithHoldingDTO.getSearchValue() + "%'")
							  .append("OR  withHolding.RESIDENCE_TAX_FILLIING_STATUS like '%" + assWithHoldingDTO.getSearchValue() + "%'")
							  .append("OR  can.RESIDENCE_STATE_CODE like '%" + assWithHoldingDTO.getSearchValue() + "%'")
							  .append("OR  emp.EMPLOYEE_ID like '%" + assWithHoldingDTO.getSearchValue() + "%')");
			}

				Query queryObj = session.createSQLQuery(query.toString() + searchQuery.toString() + deptQry + empTypeQry +qry);
				if (assWithHoldingDTO != null && assWithHoldingDTO.getLength() > 0) {
					queryObj.setFirstResult(assWithHoldingDTO.getStart());
					queryObj.setMaxResults(assWithHoldingDTO.getLength());
				}
				queryObj.setLong("orgLocationId", orgLocation.getId());
				
				if(StringUtils.isNotBlank(assWithHoldingDTO.getDept())) {
					
					List<Long> deptList = Arrays.asList(assWithHoldingDTO.getDept().split(","))
							              .stream().map(Long::valueOf).collect(Collectors.toList());
					queryObj.setParameterList("deptList",deptList);
				}
				if(StringUtils.isNotBlank(assWithHoldingDTO.getEmpType())) {
					List<String> empTypeList = Arrays.asList(assWithHoldingDTO.getEmpType().split(","));
					queryObj.setParameterList("empTypeList",empTypeList);
				}
				queryObj.setResultTransformer(Transformers.aliasToBean(AssociateWithHoldingDTO.class));
				assoWithHoldingList = queryObj.list();
				
			
				
				int count = 0;
				
				Query  countQry = session.createQuery(countQuery.toString()).setLong("orgLocationId", orgLocation.getId());
				countlist = countQry.list();
				if (countlist != null && !countlist.isEmpty()) {
					if (assWithHoldingDTO.getSearchValue() != null && !"".equalsIgnoreCase(assWithHoldingDTO.getSearchValue())) {
						count = assoWithHoldingList.size();
					}else {
						count = assoWithHoldingList.size();;
					}
					
				}
				response.setRecordsTotal(count);
//				 filterCount = countlist.size();
				/*int totalCount = 0;
				if (assWithHoldingDTO != null) {
					if(countlist != null && !countlist.isEmpty()) {
						totalCount = countlist.size();
					}]
				} 
				
				if(searchQuery.toString() != null && !searchQuery.toString().isEmpty()){
					response.setRecordsFiltered(filterCount);
				}else {
					response.setRecordsFiltered(totalCount);
				}*/
				
				int filteredCount = 0;
				
				if (assWithHoldingDTO != null) {
					Query  filteredCountQry= session.createQuery(countQuery.toString()).setLong("orgLocationId", orgLocation.getId());
					List<Long> filteredCountlist = filteredCountQry.list();
					if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
						if (assWithHoldingDTO.getSearchValue() != null && !"".equalsIgnoreCase(assWithHoldingDTO.getSearchValue())) {
							filteredCount = assoWithHoldingList.size();
						}else {
							filteredCount = assoWithHoldingList.size();
						}
						
					}
					response.setRecordsFiltered(filteredCount);
				}
				
				for(AssociateWithHoldingDTO withHolding : assoWithHoldingList) {
					StringBuilder sb = new StringBuilder();
					if(withHolding.getSsnNumber() != null) {
						String ssn = withHolding.getSsnNumber();
						String[] arr = ssn.split("-");
						sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
						withHolding.setSsnNumber(sb.toString());
					}
					
				}
				response.setData(assoWithHoldingList);
		}catch (Exception e) {
				e.printStackTrace();
		}
		return response;
	}

	
}
