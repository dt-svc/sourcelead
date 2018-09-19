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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollReportDao;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class PayrollReportDaoImpl implements PayrollReportDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollReportDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired 
	CommonUtil commonUtil;

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
					searchQuery.append(" and payroll.candidate.firstName like '%" + payrollReportDTO.getSearchValue() + "%' ")
						.append("OR  payroll.candidate.lastName like '%" + payrollReportDTO.getSearchValue() + "%' ");
//						.append("OR wts.checkDate like '%" + payrollReportDTO.getSearchValue() + "%' ");
//						.append("OR wts.ssnNumber like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.checkNo like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.gross like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.fwt like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.ssec like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.batchNumber like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.fromDate like '%" + payrollReportDTO.getSearchValue() + "%' ")
//						.append("OR wts.toDate like '%" + payrollReportDTO.getSearchValue() + "%' ");
							/*+ "OR wts.checkDate like '%" + payrollReportDTO.getSearchValue() + "%'";*/
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
	
}
