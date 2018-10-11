package com.bis.sourcelead.payroll.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class PaysheetFrequencyDaoImpl implements PaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ConversionUtil conversionUtil;
	
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
			paySheetFreqList = session.createQuery("from PaysheetFrequency pay where pay.orgLocation.id=:orgLocationId order by pay.id desc") 
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
			java.sql.Date startDate = new java.sql.Date(ytdDates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(ytdDates[1].getTime());
			Session session = this.sessionFactory.getCurrentSession();
			payrollList = session.createQuery("from EmployeePayroll payroll where payroll.candidate.id=:candidateId and payroll.payCheckDate >= :startDate and payroll.payCheckDate <= :endDate  and payroll.submitForAproverStatus = :status")
													   .setLong("candidateId", candidate.getId())
													   .setString("status", "APPROVED")
													   .setDate("startDate", startDate)
													   .setDate("endDate", endDate)
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
		
		PaysheetFrequency paysheetFrequency = null;
		try {
			String[] dts = dates.split(" ");
			
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(dts[0]);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(dts[1]);
			
			Session session = this.sessionFactory.getCurrentSession();
			List<PaysheetFrequency> frequencyList = session.createQuery("from PaysheetFrequency paysheet where paysheet.userGroup.id=:groupId and paysheet.orgLocation.id=:orgLocationId and paysheet.frequencyStartDate = :startDate and paysheet.frequencyEndDate = :endDate")
											   .setLong("orgLocationId", orgLocation.getId())
											   .setLong("groupId", groupId)
											   .setParameter("startDate",startDate)
											   .setParameter("endDate", endDate)
											   .list();
			
			if(frequencyList != null && frequencyList.size() >0) {
				paysheetFrequency = frequencyList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return paysheetFrequency;
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
			}
			queryObj.setLong("orgLocationId", payrollReportDTO.getOrgLocationid()).setLong("groupId", payrollReportDTO.getPayGroup()).setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate);
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
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public YTDPayrollDTO getContributionYTD(EmployeePayroll payroll,Long contributionId,OrgLocations orgLocation, String reportType)
	{
		YTDPayrollDTO yTDPayrollDTO = new YTDPayrollDTO();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			BigDecimal contributionYTD = BigDecimal.ZERO;
			BigDecimal contributionMTD = BigDecimal.ZERO;
			BigDecimal contributionQTD = BigDecimal.ZERO;
			// YTD Dates
			Date[] ytdDates = payrollUtil.getYTDDates(orgLocation);
			java.sql.Date startDate = new java.sql.Date(ytdDates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(ytdDates[1].getTime());
			String qry = "from DeductionHistory dedHis where dedHis.payroll.candidate.id= :candidateId and dedHis.contributionId.id = :contributionId and dedHis.deductionDate >= :startDate and dedHis.deductionDate <= :endDate ";
			Query queryObj = session.createQuery(qry);
					queryObj.setLong("candidateId", payroll.getCandidate().getId());
					queryObj.setLong("contributionId", contributionId);
					queryObj.setDate("startDate", startDate);
					queryObj.setDate("endDate", endDate);
					List<DeductionHistory> deductioHistory = queryObj.list();	
					
					contributionYTD = getTotalYTD(deductioHistory);
					yTDPayrollDTO.setContributionYTD(contributionYTD);
			
			if("report".equalsIgnoreCase(reportType)) {
				// MTD
				Date[] mtdDates = conversionUtil.getMonthStartDateEndDate();;
				Query mtdQueryObj = session.createQuery(qry);
				java.sql.Date mtdStartDate = new java.sql.Date(mtdDates[0].getTime());
				java.sql.Date mtdEndDate = new java.sql.Date(mtdDates[1].getTime());
				mtdQueryObj.setLong("candidateId", payroll.getCandidate().getId());
				mtdQueryObj.setLong("contributionId", contributionId);
				mtdQueryObj.setDate("startDate", mtdStartDate);
				mtdQueryObj.setDate("endDate", mtdEndDate);
				List<DeductionHistory> deductioHistoryMTD = mtdQueryObj.list();
				contributionMTD = getTotalYTD(deductioHistoryMTD);
				
				// QTD
				LocalDate today = LocalDate.now();
				LocalDate[] currentMonthQuarter  = conversionUtil.getThisMonthQuarterDates(today);
				Query qtdQueryObj = session.createQuery(qry);
				java.sql.Date qtdStartDate = new java.sql.Date(currentMonthQuarter[0].toDate().getTime());
				java.sql.Date qtdEndDate = new java.sql.Date(currentMonthQuarter[1].toDate().getTime());
				qtdQueryObj.setLong("candidateId", payroll.getCandidate().getId());
				qtdQueryObj.setLong("contributionId", contributionId);
				qtdQueryObj.setDate("startDate", qtdStartDate);
				qtdQueryObj.setDate("endDate", qtdEndDate);
				List<DeductionHistory> deductioHistoryQTD = qtdQueryObj.list();
				contributionQTD = getTotalYTD(deductioHistoryQTD);
			}
			yTDPayrollDTO.setContributionMTD(contributionMTD);
			yTDPayrollDTO.setContributionQTD(contributionQTD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return yTDPayrollDTO;
	}

	/**
	 * Summing total
	 * 
	 * @param deductioHistory
	 * @return BigDecimal
	 */
	private BigDecimal getTotalYTD(List<DeductionHistory> deductioHistory)
	{
		BigDecimal total = BigDecimal.ZERO;
		if (deductioHistory != null) {
			for (DeductionHistory dedHis : deductioHistory) {
				if (dedHis.getPreContributionAmount() != null) {
					total = total.add(dedHis.getPreContributionAmount());
				}
				if (dedHis.getPostContributionAmount() != null) {
					total = total.add(dedHis.getPostContributionAmount());
				}
			}
		}
		return total;
	}
	
	
	/**
	 * Summing total
	 * 
	 * @param deductioHistory
	 * @return BigDecimal
	 */
	private BigDecimal getTotalDeducationYTD(List<DeductionHistory> deductioHistory)
	{
		BigDecimal total = BigDecimal.ZERO;
		if (deductioHistory != null) {
			for (DeductionHistory dedHis : deductioHistory) {
				if (dedHis.getPreDeductionAmount() != null) {
					total = total.add(dedHis.getPreDeductionAmount());
				}
				if (dedHis.getPostDeductionAmount() != null) {
					total = total.add(dedHis.getPostDeductionAmount());
				}
			}
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public YTDPayrollDTO getDeductionYTD(EmployeePayroll payroll, Long deductionId, OrgLocations orgLocation,String reportType) {
		
		YTDPayrollDTO yTDPayrollDTO = new YTDPayrollDTO();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Date[] ytdDates = payrollUtil.getYTDDates(orgLocation);
			java.sql.Date startDate = new java.sql.Date(ytdDates[0].getTime());
			java.sql.Date endDate = new java.sql.Date(ytdDates[1].getTime());
			String qry = "from DeductionHistory dedHis where dedHis.payroll.candidate.id= :candidateId and dedHis.deductionId.id = :deductionId and dedHis.deductionDate >= :startDate and dedHis.deductionDate <= :endDate";
			Query queryObj = session.createQuery(qry);
			queryObj.setLong("candidateId", payroll.getCandidate().getId());
			queryObj.setLong("deductionId", deductionId);
			queryObj.setDate("startDate", startDate);
			queryObj .setDate("endDate", endDate);
			
			List<DeductionHistory> deductioHistory = queryObj.list();
			yTDPayrollDTO.setDeductionYTD(getTotalDeducationYTD(deductioHistory));
			
			
			
			BigDecimal QTDTotal = BigDecimal.ZERO;
			BigDecimal MTDTotal = BigDecimal.ZERO;
			
			if("report".equalsIgnoreCase(reportType)) {
				// MTD
				Date[] mtdDates = conversionUtil.getMonthStartDateEndDate();;
				Query mtdQueryObj = session.createQuery(qry);
				java.sql.Date mtdStartDate = new java.sql.Date(mtdDates[0].getTime());
				java.sql.Date mtdEndDate = new java.sql.Date(mtdDates[1].getTime());
				mtdQueryObj.setLong("candidateId", payroll.getCandidate().getId());
				mtdQueryObj.setLong("deductionId", deductionId);
				mtdQueryObj.setDate("startDate", mtdStartDate);
				mtdQueryObj.setDate("endDate", mtdEndDate);
				List<DeductionHistory> deductioHistoryMTD = mtdQueryObj.list();
				MTDTotal = getTotalDeducationYTD(deductioHistoryMTD);
				
				// QTD
				LocalDate today = LocalDate.now();
				LocalDate[] currentMonthQuarter  = conversionUtil.getThisMonthQuarterDates(today);
				Query qtdQueryObj = session.createQuery(qry);
				java.sql.Date qtdStartDate = new java.sql.Date(currentMonthQuarter[0].toDate().getTime());
				java.sql.Date qtdEndDate = new java.sql.Date(currentMonthQuarter[1].toDate().getTime());
				qtdQueryObj.setLong("candidateId", payroll.getCandidate().getId());
				qtdQueryObj.setLong("deductionId", deductionId);
				qtdQueryObj.setDate("startDate", qtdStartDate);
				qtdQueryObj.setDate("endDate", qtdEndDate);
				List<DeductionHistory> deductioHistoryQTD = qtdQueryObj.list();
				QTDTotal = getTotalDeducationYTD(deductioHistoryQTD);
				
			}
			
			yTDPayrollDTO.setDeductionMTD(MTDTotal);
			yTDPayrollDTO.setDeductionQTD(QTDTotal);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return yTDPayrollDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> getPayrollTaxesYTD(EmployeePayroll payroll, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Date[] ytdDates = payrollUtil.getYTDDates(orgLocation);
		java.sql.Date startDate = new java.sql.Date(ytdDates[0].getTime());
		java.sql.Date endDate = new java.sql.Date(ytdDates[1].getTime());
		String qry = "from EmployeePayroll payroll where payroll.id = :payrollId and payroll.payCheckDate >= :startDate and payroll.payCheckDate <= :endDate";
		Query queryObj = session.createQuery(qry);
//		queryObj.setLong("candidateId", payroll.getCandidate().getId());
		queryObj.setLong("payrollId", payroll.getId());
		queryObj.setDate("startDate", startDate);
		queryObj.setDate("endDate", endDate);
		return queryObj.list();
	}
}
