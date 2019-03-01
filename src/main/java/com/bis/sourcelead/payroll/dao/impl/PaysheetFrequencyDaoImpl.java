package com.bis.sourcelead.payroll.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.CommonPayrollDataDTO;
import com.bis.sourcelead.payroll.dto.IntiateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayScheduleDate;
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
	public List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation,String approverStatus,int periodYear)
	{
		List<EmployeePayroll> payrollList = null;
		try {
			Date[] ytdDates = payrollUtil.getYTDDatesPerYear(orgLocation,periodYear);
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
			String[] dts = dates.split("#");
			
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dts[0]);
			Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dts[1]);
			
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
			
			Date fDate = commonUtil.orgLocationDateFormat(payrollReportDTO.getOrgDateFormat(), payrollReportDTO.getFreqStartDate());
			Date tDate = commonUtil.orgLocationDateFormat(payrollReportDTO.getOrgDateFormat(), payrollReportDTO.getFreqEndDate());
			java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
			java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
			
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
			
			//department Ids
			Long[] data = null;
			String departmentQuery = "";
			if(payrollReportDTO.getDepartment() != null && !"null".equalsIgnoreCase(payrollReportDTO.getDepartment()) ) {
				
				String[] idsValues = payrollReportDTO.getDepartment().split(",");
				// convert string array to long array
				 data = new Long[idsValues.length];
				for (int i = 0; i < idsValues.length; i++) {
				  data[i] = Long.valueOf(idsValues[i]);
				}
				
				departmentQuery = " and payroll.employee.departmentId.id IN ( :ids )";
				
			}
			
			
			
			String query = "" , countQuery = "";
			if(payrollReportDTO.getPayGroup() != null && payrollReportDTO.getPayGroup() != 0l) {
				query = "from EmployeePayroll payroll left join fetch payroll.employee left join fetch payroll.employee.user where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'"+departmentQuery;
				countQuery = "from EmployeePayroll payroll where payroll.paysheetFrequency.userGroup.id = :groupId and payroll.paysheetFrequency.orgLocation.id = :orgLocationId and payroll.paysheetFrequency.frequencyStartDate = :freqStartDate and payroll.paysheetFrequency.frequencyEndDate = :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'";
			}else {
				query = "from EmployeePayroll payroll left join fetch payroll.employee left join fetch payroll.employee.user where payroll.paysheetFrequency.orgLocation.id = :orgLocationId and date(payroll.payCheckDate) BETWEEN :freqStartDate and :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'";
				countQuery = "from EmployeePayroll payroll where payroll.paysheetFrequency.orgLocation.id = :orgLocationId and date(payroll.payCheckDate) BETWEEN :freqStartDate and :freqEndDate and payroll.submitForAproverStatus = 'APPROVED'";
			}
			
			StringBuilder searchQuery = new StringBuilder("");
			if(payrollReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
					searchQuery.append(" and ((concat(payroll.candidate.firstName,payroll.candidate.lastName) like '%" + payrollReportDTO.getSearchValue() + "%' )")
						.append("OR  payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.employee.departmentId.departmentName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.employee.employeeId like '%" + payrollReportDTO.getSearchValue() + "%' )");
			}
			
			java.sql.Date fromDate = null, toDate = null;
			String subQuery = "";
			if(StringUtils.isNotBlank(payrollReportDTO.getFromDate()) && StringUtils.isNotBlank(payrollReportDTO.getToDate())) {
				fromDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFromDate()).getTime());
				toDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getToDate()).getTime());
				subQuery = "and date(payroll.payCheckDate) between :fromDate and :toDate ";
			}
			
			Query queryObj = session.createQuery(query + searchQuery.toString() + qry+subQuery);
			if (payrollReportDTO != null && payrollReportDTO.getLength() > 0) {
				queryObj.setFirstResult(payrollReportDTO.getStart());
				queryObj.setMaxResults(payrollReportDTO.getLength());
			}
			if(StringUtils.isNotBlank(payrollReportDTO.getFromDate()) && StringUtils.isNotBlank(payrollReportDTO.getToDate())) {
				queryObj.setParameter("fromDate", fromDate);
				queryObj.setParameter("toDate", toDate);
			}
			if(StringUtils.isNotBlank(payrollReportDTO.getDepartment()) && !"null".equalsIgnoreCase(payrollReportDTO.getDepartment())) {
				queryObj.setParameterList("ids", data);
			}
			queryObj.setLong("orgLocationId", payrollReportDTO.getOrgLocationid());
			if(payrollReportDTO.getPayGroup() != null && payrollReportDTO.getPayGroup() != 0l) {
				queryObj.setLong("groupId", payrollReportDTO.getPayGroup());
			}
			queryObj.setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate);
			payrollList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = null;
			Query countQueryObj = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", payrollReportDTO.getOrgLocationid());
			if(payrollReportDTO.getPayGroup() != null &&payrollReportDTO.getPayGroup() != 0l) {
				countQueryObj.setLong("groupId", payrollReportDTO.getPayGroup());
			}
			countQueryObj.setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate);
			countlist = countQueryObj.list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (payrollReportDTO != null) {
				List<Long> filteredCountlist  = null;
				Query filteredCountQueryObj =  session.createQuery("select count(*) " + countQuery + searchQuery.toString()).setLong("orgLocationId", payrollReportDTO.getOrgLocationid());
				if(payrollReportDTO.getPayGroup() != null && payrollReportDTO.getPayGroup() != 0l) {
					filteredCountQueryObj.setLong("groupId", payrollReportDTO.getPayGroup());
				}
				filteredCountQueryObj.setDate("freqStartDate", freqStartDate).setDate("freqEndDate",freqEndDate);
				filteredCountlist = filteredCountQueryObj.list();
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
		
		//passing frequency year to calculate ytd
		Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		
		YTDPayrollDTO yTDPayrollDTO = new YTDPayrollDTO();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			BigDecimal contributionYTD = BigDecimal.ZERO;
			BigDecimal contributionMTD = BigDecimal.ZERO;
			BigDecimal contributionQTD = BigDecimal.ZERO;
			// YTD Dates
			Date[] ytdDates = payrollUtil.getYTDDatesPerYear(orgLocation,year);
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
		
		
		//passing frequency year to calculate ytd
		Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		
		YTDPayrollDTO yTDPayrollDTO = new YTDPayrollDTO();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Date[] ytdDates = payrollUtil.getYTDDatesPerYear(orgLocation,year);
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
		
		
		//passing frequency year to calculate ytd
		Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		
		Session session = this.sessionFactory.getCurrentSession();
		Date[] ytdDates = payrollUtil.getYTDDatesPerYear(orgLocation,year);
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> getEmployeePayrollListByPayCheckDate(PayrollReportDTO payrollReportDTO,OrgLocations orgLocation) {
		List<EmployeePayroll> payrollList = null;
		
		try {
			
			//department Ids
			Long[] data = null;
			if(payrollReportDTO.getDepartment() != null && !"null".equalsIgnoreCase(payrollReportDTO.getDepartment()) ) {
				
				String[] idsValues = payrollReportDTO.getDepartment().split(",");
				// convert string array to long array
				 data = new Long[idsValues.length];
				for (int i = 0; i < idsValues.length; i++) {
				  data[i] = Long.valueOf(idsValues[i]);
				}
			}
			
			//employee Ids
			Long[] employeeIds = null;
			if(payrollReportDTO.getEmployees() != null && !"null".equalsIgnoreCase(payrollReportDTO.getEmployees())) {
				String[] employeeIdsValues = payrollReportDTO.getEmployees().split(",");
				// convert string array to long array
					employeeIds = new Long[employeeIdsValues.length];
				for (int i = 0; i < employeeIdsValues.length; i++) {
					employeeIds[i] = Long.valueOf(employeeIdsValues[i]);
				}
			}
			
			Date fDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getFromDate());
			Date tDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payrollReportDTO.getToDate());
			java.sql.Date startDate = new java.sql.Date(fDate.getTime());
			java.sql.Date endDate = new java.sql.Date(tDate.getTime());
			
			
			String qry = "from EmployeePayroll payroll where payroll.paysheetFrequency.orgLocation.id =:orgLocationId and payroll.payCheckDate >= :startDate and payroll.payCheckDate <= :endDate  and payroll.submitForAproverStatus = :status";
			String subQuery1 = " and payroll.employee.departmentId.id IN ( :ids )";
			String subQuery2 = " and payroll.employee.id IN ( :employeeIds )";
			
			Session session = this.sessionFactory.getCurrentSession();
			
			StringBuilder searchQuery = new StringBuilder("");
			if (StringUtils.isNotBlank(payrollReportDTO.getSearchValue())) {
					if (payrollReportDTO.getSearchValue() != null && !"".equalsIgnoreCase(payrollReportDTO.getSearchValue())) {
						searchQuery.append(" and (payroll.employee.user.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR payroll.employee.user.lastName like '%" + payrollReportDTO.getSearchValue() + "%' )");
					}
			}
			
			if(data != null && employeeIds != null) {
				payrollList = session.createQuery(qry + subQuery1 + subQuery2+searchQuery).setString("status", "APPROVED")
						   .setLong("orgLocationId", orgLocation.getId())
						   .setDate("startDate", startDate)
						   .setDate("endDate", endDate)
						   .setParameterList("ids", data)
						   .setParameterList("employeeIds", employeeIds).list();
			}else if(data != null){
				subQuery1 = " and payroll.employee.departmentId.id IN ( :ids )";
				payrollList = session.createQuery(qry + subQuery1+searchQuery).setString("status", "APPROVED")
						   .setLong("orgLocationId", orgLocation.getId())
						   .setDate("startDate", startDate)
						   .setDate("endDate", endDate)
						   .setParameterList("ids", data).list();
			} else {
				payrollList = session.createQuery(qry+searchQuery).setString("status", "APPROVED")
						   .setLong("orgLocationId", orgLocation.getId())
						   .setDate("startDate", startDate)
						   .setDate("endDate", endDate).list();
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payrollList;
	}

	@Override
	@Transactional
	public List<InvoicePayment> getContractEmployeeReportList(PayrollReportDTO payrollReportDTO)
	{
		Session session = this.sessionFactory.getCurrentSession();
		
		Date fDate = commonUtil.orgLocationDateFormat(payrollReportDTO.getOrgDateFormat(), payrollReportDTO.getFreqStartDate());
		Date tDate = commonUtil.orgLocationDateFormat(payrollReportDTO.getOrgDateFormat(), payrollReportDTO.getFreqEndDate());
		java.sql.Date freqStartDate = new java.sql.Date(fDate.getTime());
		java.sql.Date freqEndDate = new java.sql.Date(tDate.getTime());
		
//		java.sql.Date freqStartDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqStartDate()).getTime());
//		java.sql.Date freqEndDate = new java.sql.Date(commonUtil.dateFromElk(payrollReportDTO.getFreqEndDate()).getTime());
		String subQuery = "";
		if(StringUtils.isNotBlank(payrollReportDTO.getSearchValue())) {
			StringBuilder sb = new StringBuilder();
			sb.append(" and (invoice.orgLocation.orgLocationName like '%"+payrollReportDTO.getSearchValue()+"%' or invoice.responderLocationId.orgLocationName like '%"+payrollReportDTO.getSearchValue()+"%'");
			sb.append(" or invoice.invoiceId like '%"+payrollReportDTO.getSearchValue()+"%' ) ");
			subQuery = sb.toString();
		}
				
		String query = "from InvoicePayment invoicePayment  left join fetch invoicePayment.invoiceId invoice  where (invoice.orgLocation.id  = :orgLocationId or invoice.responderLocationId.id  = :orgLocationId) and invoicePayment.paymentDate >= :startDate and invoicePayment.paymentDate <= :endDate "+subQuery;
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("orgLocationId", payrollReportDTO.getOrgLocationid()).setDate("startDate", freqStartDate).setDate("endDate", freqEndDate).list();
		for(InvoicePayment p : invoicePaymentList) {
			p.setPaidAmount(Double.valueOf(p.getPaymentAmount()));
		}
		return invoicePaymentList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public CommonPayrollDataDTO getNextPayrollDate(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId,String orgDateFormat)
	{
		List<IntiateUSPayrollDTO> recentRunRecord = null;
		CommonPayrollDataDTO commonPayrollDataDTO = new CommonPayrollDataDTO();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ugr.GROUP_NAME as groupName,") 
			  .append("ugr.PAY_FREQUENCY as payFrequency,")
			  .append("psheet.FREQUENCY_START_DATE as payrollFreqStartDate,")
			  .append("psheet.FREQUENCY_END_DATE as payrollFreqEndDate ")
			  .append("FROM USER_GROUP ugr, PAY_SHEET_FREQUENCY psheet ")
			  .append("where ugr.ID = psheet.GROUP_ID ")
			  .append("AND ugr.PAY_FREQUENCY = :payFrequency ")
			  .append("AND psheet.ORG_LOCATION_ID = :orgLocationId  order by psheet.CREATED_DATE desc limit 1");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setString("payFrequency", intiateUSPayrollDTO.getPayFrequency());
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(IntiateUSPayrollDTO.class));
			recentRunRecord = queryObj.list();
//			System.out.println(recentRunRecord.get(0).getPayrollFreqStartDate() +" / " +recentRunRecord.get(0).getPayrollFreqEndDate());
			if(recentRunRecord != null && recentRunRecord.size() >0) {
				Calendar c = Calendar.getInstance(); 
				c.setTime(recentRunRecord.get(0).getPayrollFreqEndDate()); 
				c.add(Calendar.DATE, 1);
				Date nextForEndDate = c.getTime();
				java.sql.Date freqStartDate = new java.sql.Date(nextForEndDate.getTime());
				
				String qry = "from USPayScheduleDate scheduleDate where scheduleDate.payPeriodStartDate = :payrollFreqStartDate";
				List<USPayScheduleDate> uSPayScheduleDate = session.createQuery(qry).setDate("payrollFreqStartDate", freqStartDate).list();
				
				if(uSPayScheduleDate != null && uSPayScheduleDate.size() > 0) {
					StringBuilder payFreq = new StringBuilder();
					commonPayrollDataDTO.setPayrollFreqStartDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, uSPayScheduleDate.get(0).getPayPeriodStartDate()));
					commonPayrollDataDTO.setPayrollFreqEndDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, uSPayScheduleDate.get(0).getPayPeriodEndDate()));
					commonPayrollDataDTO.setPayrollCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, uSPayScheduleDate.get(0).getCheckDate()));
					commonPayrollDataDTO.setPayrollRunDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, uSPayScheduleDate.get(0).getPayRunDate()));
					payFreq.append(commonPayrollDataDTO.getPayrollFreqStartDate()).append(" to ").append(commonPayrollDataDTO.getPayrollFreqEndDate());
					commonPayrollDataDTO.setPayrollFrequency(payFreq.toString());
				}	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return commonPayrollDataDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PaysheetFrequency> getRecentlyRunPayrollListByFrequency(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String dateFormat)
	{
		List<PaysheetFrequency> paysheetFreqList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PaysheetFrequency freq where freq.orgLocation.id  =:orgLocationId and freq.userGroup.payFrequency =:payFrequency order by freq.createdDate desc"; //limit 10";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setString("payFrequency", intiateUSPayrollDTO.getPayFrequency());
			queryObj.setMaxResults(10);
			paysheetFreqList = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFreqList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PaysheetFrequency> getRecentlyRunPayrollData(Long orgLocationId)
	{
		List<PaysheetFrequency> paysheetFreqList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from PaysheetFrequency freq where freq.orgLocation.id  =:orgLocationId order by freq.createdDate desc"; //limit 10";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setMaxResults(10);
			paysheetFreqList = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFreqList;
	}
}
