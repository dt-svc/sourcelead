package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.AccountDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AccountDeductionsDao;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.AccountDeductionsDetails;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class AccountDeductionsDaoImpl implements AccountDeductionsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AccountDeductionsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public AccountDeductions saveAccountDeductions(AccountDeductions payCycle)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(payCycle);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return payCycle;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<AccountDeductions> getAccountDeductionsListByOrgLocationId(OrgLocations orgLocation, AccountDeductionsDTO accountDeductionsDTO) throws Exception
	{
		
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductions> accountDeductionsList = null;
		ResponseList<AccountDeductions> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(accountDeductionsDTO.getSortColumn())) {
			qry = " order by acd."+ accountDeductionsDTO.getSortColumn() +" "+accountDeductionsDTO.getSortOrder();
		}
		
		String query = "from AccountDeductions acd  left join fetch acd.orgLocationId where acd.orgLocationId.id =:orgLocationId and (acd.status IS NULL or acd.status <> '0')";
		String countQuery = "from AccountDeductions acd  where acd.orgLocationId.id =:orgLocationId and (acd.status IS NULL or acd.status <> '0')";
		
		
		if(accountDeductionsDTO.getSearchValue().contains("/") || accountDeductionsDTO.getSearchValue().contains("-")){
			String convertedDate=accountDeductionsDTO.getSearchValue();
			if(accountDeductionsDTO.getSearchValue().contains("/")){
				convertedDate = accountDeductionsDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from AccountDeductions acd where acd.orgLocationId.id =:orgLocationId and (acd.status IS NULL or acd.status <> '0') and ( acd.fromDate =:sqlDate or acd.toDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            List<AccountDeductions> temp = queryObjDate.list();
            for(AccountDeductions accountDeductions : temp)
            {
            	accountDeductions.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), accountDeductions.getFromDate()));
            	accountDeductions.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), accountDeductions.getFromDate()));
            }
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(accountDeductionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(accountDeductionsDTO.getSearchValue())) {
			query1.append(" and acd.employeeId like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.employeeName like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.fromDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.toDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.dedAmount like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.batchNumber like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.requestSubDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.requestApprovedDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
					.append("OR acd.repaidStatus like '%" + accountDeductionsDTO.getSearchValue() + "%' ");
					
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (accountDeductionsDTO != null && accountDeductionsDTO.getLength() > 0) {
			queryObj.setFirstResult(accountDeductionsDTO.getStart());
			queryObj.setMaxResults(accountDeductionsDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		accountDeductionsList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (accountDeductionsDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

		response.setData(getAccountDeductionsListSorting(accountDeductionsList));
		}
		return response;
	}
		
		private List<AccountDeductions> getAccountDeductionsListSorting(List<AccountDeductions> accountDeductionsList)
	{
			
			
			for (AccountDeductions accountDeductions : accountDeductionsList) {
				if (accountDeductions.getFromDate() != null) {
					accountDeductions.setFromDateStr(conversionUtil.getDateAsString(accountDeductions.getFromDate()));
				}
				if (accountDeductions.getToDate() != null) {
					accountDeductions.setToDateStr(conversionUtil.getDateAsString(accountDeductions.getToDate()));
				}
				
			}
//			Collections.sort(accountDeductionsList, new Comparator() {  
//	            @Override  
//	            public int compare(Object obj1, Object obj2) {  
//	            	AccountDeductions accountDeductions1 = (AccountDeductions)obj1;  
//	            	AccountDeductions accountDeductions2 = (AccountDeductions)obj2; 
//	            	return accountDeductions1.getEmployeeName().compareToIgnoreCase(accountDeductions2.getEmployeeName());  
//	            }  
//	        });
			return accountDeductionsList;
		
	}
		
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AccountDeductionsDetails> getDeductionsDetailsbyId(Long deductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductionsDetails> accountDeductionslist = null;
		String query = "from AccountDeductionsDetails ad left join fetch ad.accountDeductions where ad.accountDeductions.id =:deductionId";
		accountDeductionslist = session.createQuery(query).setLong("deductionId", deductionId).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			return accountDeductionslist;
		}
		return accountDeductionslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public AccountDeductions getDeductionsbyId(Long deductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AccountDeductions accountDeductions = null;
		String query = "from AccountDeductions ad where ad.id =:deductionId";
		List<AccountDeductions> accountDeductionslist = session.createQuery(query).setLong("deductionId", deductionId).list();
		if(accountDeductionslist != null && accountDeductionslist.size() >0)
		{
			accountDeductions = accountDeductionslist.get(0);
		}
		return accountDeductions;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocations, Long empId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<AccountDeductions> accountDeductionslist = null;
		String query = "";
		if(empId != null && empId > 0){
			query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId and ad.employeeId = :empId";
			accountDeductionslist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
			if(accountDeductionslist != null && accountDeductionslist.size() >0)
			{
				return accountDeductionslist;
			}
		}else{
			query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId";
			accountDeductionslist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if(accountDeductionslist != null && accountDeductionslist.size() >0)
			{
				return accountDeductionslist;
			}
		}
		
		return accountDeductionslist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getAccountDeductionsVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(acc.version)  FROM AccountDeductions acc where acc.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<AccountDeductions> getAccountDeductionsByEmployeeId(OrgLocations orgLocation,AccountDeductionsDTO accountDeductionsDTO) {
		ResponseList<AccountDeductions> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<AccountDeductions> accountDeductionsList = null;
			String qry = "";
			String query = "";
			if(StringUtils.isNotBlank(accountDeductionsDTO.getSortColumn())) {
				qry = " order by ad."+ accountDeductionsDTO.getSortColumn() +" "+accountDeductionsDTO.getSortOrder();
			}
			if(StringUtils.isNoneBlank(accountDeductionsDTO.getEmployeeId())){
				query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId and ad.employeeId = :empId";
				accountDeductionsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameter("empId", accountDeductionsDTO.getEmployeeId()).list();
			}else{
				query = "from AccountDeductions ad left join fetch ad.orgLocationId where ad.orgLocationId.id =:orgLocationId";
				accountDeductionsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
			}
			
//			String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
			String countQuery = "from AccountDeductions ad  where ad.orgLocationId.id =:orgLocationId and ad.employeeId = :empId  and (ad.status IS NULL or ad.status <> '0')";
			
			if(StringUtils.isNotBlank(accountDeductionsDTO.getSearchValue()) && (accountDeductionsDTO.getSearchValue().contains("/") || accountDeductionsDTO.getSearchValue().contains("-"))){
				String convertedDate=accountDeductionsDTO.getSearchValue();
				if(accountDeductionsDTO.getSearchValue().contains("/")){
					convertedDate = accountDeductionsDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from AccountDeductions ad where ad.orgLocationId.id =:orgLocationId and (ad.status IS NULL or ad.status <> '0') and ( ad.date =:sqlDate or ad.lastmodified =:sqlDate or ad.dueDate =:sqlDate)";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder query1 = new StringBuilder("");
				if(accountDeductionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(accountDeductionsDTO.getSearchValue())) {
//					query1 = " and wts.employeeId like %%";
				query1.append(" and (ad.employeeId like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.employeeName like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
//						.append("OR ad.fromDateStr like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
//						.append("OR ad.toDateStr like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.dedAmount like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.batchNumber like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.requestSubDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.requestApprovedDate like '%" + accountDeductionsDTO.getSearchValue() + "%' ")
						.append("OR ad.repaidStatus like '%" + accountDeductionsDTO.getSearchValue() + "%') ");
							/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
				}
			Query queryObj = session.createQuery(query + query1.toString()+qry);
			if (accountDeductionsDTO != null && accountDeductionsDTO.getLength() > 0) {
				queryObj.setFirstResult(accountDeductionsDTO.getStart());
				queryObj.setMaxResults(accountDeductionsDTO.getLength());
			}
			queryObj.setLong("orgLocationId", orgLocation.getId())
			.setLong("empId", Long.parseLong(accountDeductionsDTO.getEmployeeId()));
			accountDeductionsList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setParameter("empId", accountDeductionsDTO.getEmployeeId()).setLong("orgLocationId", orgLocation.getId()).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (accountDeductionsDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameter("empId", accountDeductionsDTO.getEmployeeId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(accountDeductionsList);
			}
				
		}catch (Exception e) {

		e.printStackTrace();
		}
		return response;
	}

}
