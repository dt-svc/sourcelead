package com.bis.sourcelead.reports.dao.Impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.reports.dao.LoanAndAdvancesReportDao;

@Repository
@Transactional(rollbackFor = { Exception.class })
public class LoanAndAdvancesReportDaoImpl implements LoanAndAdvancesReportDao {
	
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseList<LoanAndAdvanceReportDTO> getLoansAndAdvancesByLoanNameAndOrglocationId(Long orgLocations,
			LoanAndAdvanceReportDTO loanAndAdvanceReportDTO) {
		Session session = this.sessionFactory.getCurrentSession();
		List<LoanAndAdvanceReportDTO> associateLoanAndAdvancesList = null;
		ResponseList<LoanAndAdvanceReportDTO> response = new ResponseList<>();
		String query = "";
		
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
				"       AND associateLoan.CANDIDATE_ID = can.ID" ;
	    

	            if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getEmpType())){
	            	query = query + "       AND can.DESIGNATION_TYPE =:empType";
	            }
				if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanName())){
					query = query + "       AND payrollLoan.NAME =:loanName";
				}
				if(StringUtils.isNotBlank(loanAndAdvanceReportDTO.getLoanAndAdvStatus())){
					query = query + "       AND associateLoan.LOAN_AND_ADVANCES_STATUS =:loanAndAdvStatus	";
				}
				
				query = query + "       AND  associateLoan.EFFECTIVE_DATE >=:fromDate and associateLoan.END_DATE <=:toDate "+
				"       AND associateLoan.ORG_LOCATION_ID =:orgLocationId"+ 
				"       AND emp.ORG_LOCATIONS_ID =:orgLocationId ";
				
				
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
	     
	     queryObj.setParameter("fromDate", loanAndAdvanceReportDTO.getFromDate()).setParameter("toDate", loanAndAdvanceReportDTO.getToDate());
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
				queryObj.setLong("orgLocationId", orgLocations);
			}
	     queryObj.setResultTransformer(Transformers.aliasToBean(LoanAndAdvanceReportDTO.class));
	        associateLoanAndAdvancesList =  queryObj.list();
	        response.setData(associateLoanAndAdvancesList);
			int count = 0;
			BigInteger filteredCount = null;
			List<Long> countlist = session.createQuery("select count(*) from AssociateLoanAndAdvances asss where asss.orgLocationId.id =:orgLocationId").setLong("orgLocationId", orgLocations).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (loanAndAdvanceReportDTO != null) {
				List<LoanAndAdvanceReportDTO> filteredCountlist  = session.createSQLQuery("select count(*) as filterCount "
						+ "from ASSOCIATE_LOAN_ADVANCES associateLoan,PAYROLL_LOAN_AND_ADVANCES payrollLoan,CANDIDATE can,EMPLOYEE emp where associateLoan.ORG_LOCATION_ID =:orgLocationId AND "
						+ "payrollLoan.ID = associateLoan.LOAN_AND_ADVANCES_ID AND can.CONSULTANT_USER_ID = emp.USER_ID AND emp.ORG_LOCATIONS_ID =:orgLocationId  AND associateLoan.CANDIDATE_ID = can.ID"+searchQuery).setLong("orgLocationId", orgLocations).setResultTransformer(Transformers.aliasToBean(LoanAndAdvanceReportDTO.class)).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().getFilterCount();
				}
				response.setRecordsFiltered(filteredCount.intValue());
			} 
				response.setData(associateLoanAndAdvancesList);
	     
		return response;
	}

}
