package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.WagesTaxSummaryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.util.ConversionUtil;


@Repository
public class WageTaxSummaryDaoImpl implements WageTaxSummaryDao {

	private static final Logger logger = LoggerFactory.getLogger(WageTaxSummaryDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(wageTaxSummary);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return wageTaxSummary;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation,WagesTaxSummaryDTO wagesTaxSummaryDTO) throws Exception
	{
		
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		ResponseList<WageTaxSummary> response = new ResponseList<>();
	
		
		String qry = "";
		if(StringUtils.isNotBlank(wagesTaxSummaryDTO.getSortColumn())) {
			qry = " order by wts."+ wagesTaxSummaryDTO.getSortColumn() +" "+wagesTaxSummaryDTO.getSortOrder();
		}
		
		String query = "from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
		String countQuery = "from WageTaxSummary wts  where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
		
		if(wagesTaxSummaryDTO.getSearchValue().contains("/") || wagesTaxSummaryDTO.getSearchValue().contains("-")){
			String convertedDate=wagesTaxSummaryDTO.getSearchValue();
			if(wagesTaxSummaryDTO.getSearchValue().contains("/")){
				convertedDate = wagesTaxSummaryDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0') and ( wts.fromDate =:sqlDate or wts.toDate =:sqlDate or wts.checkDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(wagesTaxSummaryDTO.getSearchValue() !=null && !"".equalsIgnoreCase(wagesTaxSummaryDTO.getSearchValue())) {
//				query1 = " and wts.employeeId like %%";
			query1.append(" and (wts.employeeId like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.employeeName like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.ssnNumber like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.checkNo like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.gross like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.fwt like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.ssec like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.batchNumber like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.fromDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
					.append("OR wts.toDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%') ");
						/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (wagesTaxSummaryDTO != null && wagesTaxSummaryDTO.getLength() > 0) {
			queryObj.setFirstResult(wagesTaxSummaryDTO.getStart());
			queryObj.setMaxResults(wagesTaxSummaryDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		wagesAndTaxlist = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (wagesTaxSummaryDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 
			response.setData(wagesAndTaxlist);
		}
			return response;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "";
		if(empId != null){
			query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.employeeId = :empId";
			wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId())
					.setLong("empId", empId).list();
			if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
				return wagesAndTaxlist;
			}
		}else{
			query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId";
			wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
				return wagesAndTaxlist;
			}
		}
		
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int updateWageTaxSummary(Long empId, OrgLocations orgLocation, User slUser)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update WageTaxSummary set userId = :userId where employeeId = :empId");
		query.setParameter("userId", empId);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.userId.id IS NULL GROUP BY wts.employeeId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.userId.id <> NULL GROUP BY wts.employeeId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", locationId).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId  GROUP BY wts.employeeId";
	    wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String empId,Long locationId,Date startDate, Date endDate)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		Date date = new Date();
		String query = "from WageTaxSummary wt where wt.orgLocationId.id =:orgLocationId and wt.employeeId =:empId and ( wt.createdDate >= :startDateUTC)";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", locationId).setString("empId", empId).setParameter("startDateUTC", date).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getWageTaxSummaryVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(wts.version) FROM WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationIdAndPagination(OrgLocations orgLocation,
			PayrollBalance payrollBalance) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String subQuery = null;
		String searchValue = payrollBalance.getSearchValue();
		ResponseList<WageTaxSummary> response = new ResponseList<>();
		Query queryObj = null;
		Query countQueryObj = null;
		if(searchValue !=null && !"".equals(searchValue)){
		 subQuery = " and wts.employeeName like :searchValue";
		}
		String query = "from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId";
		String countQuery = "select count(*) from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId";
		if(searchValue !=null && !"".equals(searchValue)){
			 queryObj = session.createQuery(query.concat(subQuery).concat(" GROUP BY wts.employeeId"));
			 countQueryObj = session.createQuery(countQuery.concat(subQuery).concat(" GROUP BY wts.employeeId"));
			 if (payrollBalance != null && payrollBalance.getLength() > 0) {
					queryObj.setFirstResult(payrollBalance.getStart());
					queryObj.setMaxResults(payrollBalance.getLength());
					queryObj.setLong("orgLocationId", orgLocation.getId()).setParameter("searchValue", "%" + searchValue + "%").list();
					countQueryObj.setLong("orgLocationId", orgLocation.getId()).setParameter("searchValue", "%" + searchValue + "%");
				}
		}else{
		 queryObj = session.createQuery(query+" GROUP BY wts.employeeId");
		 countQueryObj = session.createQuery(countQuery.concat(" GROUP BY wts.employeeId"));
		 if (payrollBalance != null && payrollBalance.getLength() > 0) {
				queryObj.setFirstResult(payrollBalance.getStart());
				queryObj.setMaxResults(payrollBalance.getLength());
				queryObj.setLong("orgLocationId", orgLocation.getId()).list();
				countQueryObj.setLong("orgLocationId", orgLocation.getId());
			}
		}
		
		wagesAndTaxlist = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = countQueryObj.list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.size();
		}
		response.setRecordsTotal(count);
		if (payrollBalance != null) {
			List<Long> filteredCountlist = countQueryObj.list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.size();
			}
		} 

		response.setRecordsFiltered(filteredCount);
		response.setData(wagesAndTaxlist);
		return response;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public WageTaxSummary getEmployeeIdByUserId(User user) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		WageTaxSummary wageTaxSummary = null;
		String query = "from WageTaxSummary wts  where wts.userId.id =:userId GROUP BY wts.employeeId";
		List<WageTaxSummary> wageTaxSummaryLis = session.createQuery(query).setLong("userId", user.getId()).list();
		if(wageTaxSummaryLis != null && wageTaxSummaryLis.size() >0){
			wageTaxSummary = wageTaxSummaryLis.get(0);
		}
		return wageTaxSummary;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<WageTaxSummary> getWagesAndTaxByEmpId(OrgLocations orgLocation, WagesTaxSummaryDTO wagesTaxSummaryDTO)
	{
		ResponseList<WageTaxSummary> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<WageTaxSummary> wagesAndTaxlist = null;
			String qry = "";
			if(StringUtils.isNotBlank(wagesTaxSummaryDTO.getSortColumn())) {
				qry = " order by wts."+ wagesTaxSummaryDTO.getSortColumn() +" "+wagesTaxSummaryDTO.getSortOrder();
			}
			String query = "from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId and wts.employeeId = :empId ";
//			String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
			String countQuery = "from WageTaxSummary wts  where wts.orgLocationId.id =:orgLocationId and wts.employeeId = :empId  and (wts.status IS NULL or wts.status <> '0')";
			
			if(StringUtils.isNotBlank(wagesTaxSummaryDTO.getSearchValue()) && (wagesTaxSummaryDTO.getSearchValue().contains("/") || wagesTaxSummaryDTO.getSearchValue().contains("-"))){
				String convertedDate=wagesTaxSummaryDTO.getSearchValue();
				if(wagesTaxSummaryDTO.getSearchValue().contains("/")){
					convertedDate = wagesTaxSummaryDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from WageTaxSummary wts where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0') and ( wts.fromDate =:sqlDate or wts.toDate =:sqlDate or wts.checkDate =:sqlDate)";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder query1 = new StringBuilder("");
				if(wagesTaxSummaryDTO.getSearchValue() !=null && !"".equalsIgnoreCase(wagesTaxSummaryDTO.getSearchValue())) {
//					query1 = " and wts.employeeId like %%";
				query1.append(" and wts.employeeId like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.employeeName like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.ssnNumber like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.checkNo like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.gross like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.fwt like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.ssec like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.batchNumber like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.fromDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ")
						.append("OR wts.toDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%' ");
							/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
				}
			Query queryObj = session.createQuery(query + query1.toString()+qry);
			if (wagesTaxSummaryDTO != null && wagesTaxSummaryDTO.getLength() > 0) {
				queryObj.setFirstResult(wagesTaxSummaryDTO.getStart());
				queryObj.setMaxResults(wagesTaxSummaryDTO.getLength());
				
			}
			queryObj.setLong("orgLocationId", orgLocation.getId())
			.setLong("empId", Long.parseLong(wagesTaxSummaryDTO.getEmpId()));
			wagesAndTaxlist = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setLong("empId", Long.parseLong(wagesTaxSummaryDTO.getEmpId())).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (wagesTaxSummaryDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setLong("empId", Long.parseLong(wagesTaxSummaryDTO.getEmpId())).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(wagesAndTaxlist);
			}
				
		}catch (Exception e) {

		e.printStackTrace();}
		return response;
	}
	

}
