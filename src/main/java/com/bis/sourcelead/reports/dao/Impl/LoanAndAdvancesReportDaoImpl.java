package com.bis.sourcelead.reports.dao.Impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.LoanAndAdvancesReportDao;
import com.bis.sourcelead.service.OrgLocationService;

@Repository
@Transactional(rollbackFor = { Exception.class })
public class LoanAndAdvancesReportDaoImpl implements LoanAndAdvancesReportDao {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    OrgLocationService orgLocationService;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseList<LoanAndAdvanceReportDTO> getLoansAndAdvancesByLoanNameAndOrglocationId(Long orgLocations,
			LoanAndAdvanceReportDTO loanAndAdvanceReportDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<LoanAndAdvanceReportDTO> associateLoanAndAdvancesList = null;
		ResponseList<LoanAndAdvanceReportDTO> response = new ResponseList<>();
		String query = "";
		
		OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocations);
		SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
        java.util.Date fromUtilDate = format.parse(loanAndAdvanceReportDTO.getFromDate());
        java.sql.Date fromDate = new java.sql.Date(fromUtilDate.getTime());
        
        java.util.Date toUtilDate = format.parse(loanAndAdvanceReportDTO.getToDate());
        java.sql.Date toDate = new java.sql.Date(toUtilDate.getTime());
        
		
		
	    query = "SELECT  associateLoan.EFFECTIVE_DATE as startDate, " + 
	    		"       can.FIRST_NAME as firstName, " + 
				"       can.LAST_NAME as lastName, " + 
				"       can.DESIGNATION_TYPE as empType," +
				"       emp.EMPLOYEE_ID as employeeId, " + 
				"       associateLoan.END_DATE as loanEndDate, " + 
				"       associateLoan.AMOUNT as loanTotalAmount, " + 
				"       associateLoan.PAID_AMOUNT as loanPaidAmount, " + 
				"       associateLoan.PENDING_AMOUNT as loanPendingAmount, " + 
				"       associateLoan.NO_OF_SCHEDULES as loanNoOfSchedules, " + 
				"       associateLoan.DEDUCT_AMOUNT as loanDeductAmount, " + 
				"       associateLoan.LOAN_AND_ADVANCES_STATUS as loanAndAdvStatus, " + 
				"       associateLoan.OUTSTANDING_AMOUNT as outstandingAmount, " +
				"       associateLoan.RATE_OF_INTEREST as rateOfInterest, " +
				"       payrollLoan.NAME as loanName," +
				"	    payrollLoan.TYPE as loanAndAdvType" + 
				"       FROM sourcelead.ASSOCIATE_LOAN_ADVANCES associateLoan,PAYROLL_LOAN_AND_ADVANCES payrollLoan,CANDIDATE can,EMPLOYEE emp" + 
				"       where payrollLoan.ID = associateLoan.LOAN_AND_ADVANCES_ID" + 
				"       AND associateLoan.CANDIDATE_ID = can.ID" + 
				"       AND can.CONSULTANT_USER_ID = emp.USER_ID" + 
				"       AND associateLoan.CANDIDATE_ID = can.ID" +
				"	    AND associateLoan.ORG_LOCATION_ID =:orgLocationId " + 
				"       AND  associateLoan.EFFECTIVE_DATE >= :fromDate and associateLoan.END_DATE <= :toDate " + 
				"	    AND emp.ORG_LOCATIONS_ID =:orgLocationId ";
	    

	            if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getEmpType())){
	            	query = query + "       AND can.DESIGNATION_TYPE =:empType";
	            }
				if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanName())){
					query = query + "       AND payrollLoan.NAME =:loanName";
				}
				if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanAndAdvStatus())){
					query = query + "       AND associateLoan.LOAN_AND_ADVANCES_STATUS =:loanAndAdvStatus	";
				}
				
				
				StringBuilder searchQuery = new StringBuilder("");
				if(loanAndAdvanceReportDTO.getSearchValue() !=null && !"".equalsIgnoreCase(loanAndAdvanceReportDTO.getSearchValue())) {
						searchQuery.append(" and ( emp.EMPLOYEE_ID like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  can.FIRST_NAME like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  can.LAST_NAME like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  can.DESIGNATION_TYPE like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  payrollLoan.NAME like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
							.append("OR  associateLoan.EFFECTIVE_DATE like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.END_DATE like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.AMOUNT like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.PAID_AMOUNT like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.PENDING_AMOUNT like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.NO_OF_SCHEDULES like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.DEDUCT_AMOUNT like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%' ")
						    .append("OR  associateLoan.LOAN_AND_ADVANCES_STATUS like '%" + loanAndAdvanceReportDTO.getSearchValue() + "%') ");
				}
				
	     Query queryObj = session.createSQLQuery(query+searchQuery.toString());
	     
	     queryObj.setParameter("fromDate", fromDate).setParameter("toDate", toDate);
	     if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanAndAdvStatus())){
	    	 queryObj.setParameter("loanAndAdvStatus", loanAndAdvanceReportDTO.getLoanAndAdvStatus());
	     }
	     if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanName())){
	         queryObj.setParameter("loanName", loanAndAdvanceReportDTO.getLoanName());
	     }
	     if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getEmpType())){
	         queryObj.setParameter("empType", loanAndAdvanceReportDTO.getEmpType());
	     }
	     
	     if (loanAndAdvanceReportDTO != null && loanAndAdvanceReportDTO.getLength() > 0) {
				queryObj.setFirstResult(loanAndAdvanceReportDTO.getStart());
				queryObj.setMaxResults(loanAndAdvanceReportDTO.getLength());
				
			}
	     queryObj.setLong("orgLocationId", orgLocations);
	        queryObj.setResultTransformer(Transformers.aliasToBean(LoanAndAdvanceReportDTO.class));
	        associateLoanAndAdvancesList =  queryObj.list();
	        response.setData(associateLoanAndAdvancesList);
			response.setRecordsTotal(associateLoanAndAdvancesList.size());
		 	response.setRecordsFiltered(associateLoanAndAdvancesList.size());
			response.setData(associateLoanAndAdvancesList);
	     
		return response;
	}

}
