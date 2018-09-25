package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
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

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PaysheetFrequencyDaoImpl implements PaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> getMyPayrollListByCandidateId(Long candidateId)
	{
		List<EmployeePayroll> payrollList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			payrollList = session.createQuery("from EmployeePayroll payroll where payroll.candidate.id=:candidateId and payroll.submitForAproverStatus = 'APPROVED'")
													   .setLong("candidateId", candidateId)
													   .list();
		}catch (Exception e) {
		e.printStackTrace();
		}
		return payrollList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PaysheetFrequency> getPaysheetFrequencyDraftDataByOrgLocationId(Long orgLocationId) {
		
		List<PaysheetFrequency> paySheetFreqList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			paySheetFreqList = session.createQuery("from PaysheetFrequency pay where pay.orgLocation.id=:orgLocationId")
					   .setLong("orgLocationId", orgLocationId)
					   .list();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return paySheetFreqList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation,String approverStatus)
	{
		List<EmployeePayroll> payrollList = null;
		try {
			Date[] ytdDates = payrollUtil.getYTDDates(orgLocation);
			Session session = this.sessionFactory.getCurrentSession();
			payrollList = session.createQuery("from EmployeePayroll payroll where payroll.candidate.id=:candidateId and payroll.payCheckDate >= :startDate and payroll.payCheckDate <= :endDate  and payroll.submitForAproverStatus = :status")
													   .setLong("candidateId", candidate.getId())
													   .setString("status", "APPROVED")
													   .setDate("startDate", ytdDates[0])
													   .setDate("endDate", ytdDates[1])
													   .list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payrollList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String dates, OrgLocations orgLocation,Long groupId) {
		
		List<PaysheetFrequency> frequencyList = null;
		try {
			String[] dateas = dates.split(" - ");
			
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateas[0]);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateas[1]);
			
			Session session = this.sessionFactory.getCurrentSession();
			frequencyList = session.createQuery("from PaysheetFrequency paysheet where paysheet.userGroup.id=:groupId and paysheet.orgLocation.id=:orgLocationId and paysheet.frequencyStartDate = :startDate and paysheet.frequencyEndDate = :endDate")
											   .setLong("orgLocationId", orgLocation.getId())
											   .setLong("groupId", groupId)
											   .setParameter("startDate",startDate)
											   .setParameter("endDate", endDate)
											   .list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frequencyList.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<EmployeePayroll> getPayrollRegisterReports(PayrollReportDTO payrollReportDTO)
	{
		ResponseList<EmployeePayroll> response = new ResponseList<>();
//		List<PaysheetFrequency> payrollList = null;
		
		
		try {
			java.sql.Date freqStartDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqStartDate()).getTime());
			java.sql.Date freqEndDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqEndDate()).getTime());
			Session session = this.sessionFactory.getCurrentSession();
			List<EmployeePayroll> payrollList = null;
			
			String qry = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getSortColumn())) {
				if("empId".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by payroll.employee.employeeId "+payrollReportDTO.getSortOrder();
				}else if("empName".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by payroll.candidate.firstName "+payrollReportDTO.getSortOrder();
				}else if("department".equalsIgnoreCase(payrollReportDTO.getSortColumn())) {
					qry = " order by payroll.employee.departmentId.departmentName "+payrollReportDTO.getSortOrder();
				}
				
			}
			
			String query = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'";
			String countQuery = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'";
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ((concat(payroll.candidate.firstName,payroll.candidate.lastName) like '%" + payrollReportDTO.getSearchValue() + "%' )")
						.append("OR  payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%' )");
			}
			Query queryObj = session.createQuery(query + searchQuery.toString() + qry);
			if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollReportDTO.getStart());
				queryObj.setMaxResults(payrollReportDTO.getLength());
				queryObj.setLong("orgLocationId", payrollReportDTO.getOrgLocationid()).setLong("groupId", payrollReportDTO.getPayGroup()).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate);
			}
			payrollList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", payrollReportDTO.getOrgLocationid()).setLong("groupId", payrollReportDTO.getPayGroup()).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollReportDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + searchQuery.toString()).setLong("orgLocationId", payrollReportDTO.getOrgLocationid()).setLong("groupId", payrollReportDTO.getPayGroup()).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(payrollList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		/*
		try {
			java.sql.Date startDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqStartDate()).getTime());
			java.sql.Date endDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqEndDate()).getTime());
			Session session = this.sessionFactory.getCurrentSession();
			String subQuery = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getDepartment())) {
				subQuery = " and pay.userGroup.departmentId =:departmentId";
			}
			StringBuilder searchQuery = new StringBuilder();
			if(StringUtils.isNotBlank(payrollReportDTO.getSearchValue())) {
				searchQuery.append(" and (");
			}
			Query queryString = session.createQuery("from PaysheetFrequency pay where pay.userGroup.id =:groupId"+subQuery+" and (pay.frequencyStartDate =:startDate and pay.frequencyEndDate = :endDate)");
		    queryString.setParameter("startDate", startDate);
		    queryString.setParameter("endDate", endDate);
		    queryString.setParameter("groupId", payrollReportDTO.getPayGroup());
		    if(StringUtils.isNotBlank(payrollReportDTO.getDepartment())) {
				queryString.setString("departmentId", payrollReportDTO.getDepartment());
			}
//		    queryString.setResultTransformer(Transformers.aliasToBean(USPayrollDTO.class));
			payrollList  =  queryString.list();
			responseList.setData(payrollList);
			responseList.setRecordsTotal(payrollList.size());
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		return response;
	}
}
