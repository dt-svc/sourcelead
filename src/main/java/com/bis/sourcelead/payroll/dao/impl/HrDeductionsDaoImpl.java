package com.bis.sourcelead.payroll.dao.impl;


import java.text.ParseException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.HrDeductionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.HrDeductionsDao;
import com.bis.sourcelead.payroll.entity.AccountDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductions;
import com.bis.sourcelead.payroll.entity.HrDeductionsDetails;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class HrDeductionsDaoImpl implements HrDeductionsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(HrDeductionsDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HrDeductions saveHRDeductions(HrDeductions hrDeductions) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(hrDeductions);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return hrDeductions;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<HrDeductions> getHrDeductionsListByOrgLocationid(OrgLocations orgLocation, HrDeductionsDTO hrDeductionsDTO) throws Exception
	{
		
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductions> hrDeductionsList = null;
		ResponseList<HrDeductions> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(hrDeductionsDTO.getSortColumn())) {
			qry = " order by hrd."+ hrDeductionsDTO.getSortColumn() +" "+hrDeductionsDTO.getSortOrder();
		}
		
		String query = "from HrDeductions hrd  left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId and (hrd.status IS NULL or hrd.status <> '0')";
		String countQuery = "from HrDeductions hrd  where hrd.orgLocationId.id =:orgLocationId and (hrd.status IS NULL or hrd.status <> '0')";
		
		if(hrDeductionsDTO.getSearchValue().contains("/") || hrDeductionsDTO.getSearchValue().contains("-")){
			String convertedDate=hrDeductionsDTO.getSearchValue();
			if(hrDeductionsDTO.getSearchValue().contains("/")){
				convertedDate = hrDeductionsDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from HrDeductions hrd where hrd.orgLocationId.id =:orgLocationId and (hrd.status IS NULL or hrd.status <> '0') and ( hrd.fromDate =:sqlDate or hrd.toDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            List<HrDeductions> temp = queryObjDate.list();
            for(HrDeductions hrDeductions : temp)
            {
            	hrDeductions.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), hrDeductions.getFromDate()));
            	hrDeductions.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), hrDeductions.getToDate()));
            }
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(hrDeductionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(hrDeductionsDTO.getSearchValue())) {
			query1.append(" and hrd.employeeId like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.employeeName like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.fromDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.toDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.dedAmount like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.batchNumber like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.requestSubDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.requestApprovedDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
					.append("OR hrd.repaidStatus like '%" + hrDeductionsDTO.getSearchValue() + "%' ");
					
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (hrDeductionsDTO != null && hrDeductionsDTO.getLength() > 0) {
			queryObj.setFirstResult(hrDeductionsDTO.getStart());
			queryObj.setMaxResults(hrDeductionsDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		hrDeductionsList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (hrDeductionsDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

		if (hrDeductionsList != null) {
			response.setData(getHrDeductionsListSorting(hrDeductionsList));
		}
		}
		return response;
	}
		
		private List<HrDeductions> getHrDeductionsListSorting(List<HrDeductions> hrDeductionsList)
	{
			
			
			for (HrDeductions hrDeductions : hrDeductionsList) {
				if (hrDeductions.getFromDate() != null) {
					hrDeductions.setFromDateStr(conversionUtil.getDateAsString(hrDeductions.getFromDate()));
				}
				if (hrDeductions.getToDate() != null) {
					hrDeductions.setToDateStr(conversionUtil.getDateAsString(hrDeductions.getToDate()));
				}
				
			}
		/*	Collections.sort(hrDeductionsList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	HrDeductions hrDeductions1 = (HrDeductions)obj1;  
	            	HrDeductions hrDeductions2 = (HrDeductions)obj2; 
	            	return hrDeductions1.getEmployeeName().compareToIgnoreCase(hrDeductions2.getEmployeeName());  
	            }  
	        });*/
			return hrDeductionsList;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<HrDeductionsDetails> getHrDeductionsDetailsbyId(long hrDeductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductionsDetails> hrdeductionlist = null;
		String query = "from HrDeductionsDetails hrd  left join fetch hrd.hrDeductions where hrd.hrDeductions.id =:hrDeductionId";
		hrdeductionlist = session.createQuery(query).setLong("hrDeductionId", hrDeductionId).list();
		if(hrdeductionlist != null && hrdeductionlist.size() >0)
		{
			return hrdeductionlist;
		}
		return hrdeductionlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductions> hrdeductionlist = null;
		String query = "";
		if(empId != null && empId > 0){
			query = "from HrDeductions hrd  left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId and hrd.employeeId = :empId";
			hrdeductionlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
			if(hrdeductionlist != null && hrdeductionlist.size() >0)
			{
				return hrdeductionlist;
			}
		}else{
			query = "from HrDeductions hrd  left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId";
			hrdeductionlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if(hrdeductionlist != null && hrdeductionlist.size() >0)
			{
				return hrdeductionlist;
			}
		}
		
		return hrdeductionlist;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HrDeductions getDeductionsbyId(Long deductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		HrDeductions hrDeductions = null;
		String query = "from HrDeductions hr where hr.id =:deductionId";
		List<HrDeductions> hrDeductionsList = session.createQuery(query).setLong("deductionId", deductionId).list();
		if(hrDeductionsList != null && hrDeductionsList.size() >0)
		{
			hrDeductions = hrDeductionsList.get(0);
		}
		return hrDeductions;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HrDeductions getHrDeductionsByEmpIdandDuration(String empId, Long locationId, Date startDate, Date endDate)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<HrDeductions> hrDeductionslist = null;
		String query = "from HrDeductions hr where hr.orgLocationId.id =:orgLocationId and hr.employeeId =:empId and ( hr.createdDate >= :startDateUTC and hr.createdDate <= :endDateUTC )";
		hrDeductionslist = session.createQuery(query).setLong("orgLocationId", locationId).setString("empId", empId).setParameter("startDateUTC", startDate).setParameter("endDateUTC", endDate).list();
		if(hrDeductionslist != null && hrDeductionslist.size() >0)
		{
			return hrDeductionslist.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public HrDeductions saveAccountDeductions(HrDeductions hrDeductions) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(hrDeductions);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return hrDeductions;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getHrDeductionsVersionByLocationId(Long locationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(hrd.version)  FROM HrDeductions hrd where hrd.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", locationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<HrDeductions> getHrDeductionsByEmployeeId(OrgLocations orgLocation,HrDeductionsDTO hrDeductionsDTO) {
		ResponseList<HrDeductions> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<HrDeductions> hrDeductionsList = null;
			String qry = "";
			String query = "";
			if(StringUtils.isNotBlank(hrDeductionsDTO.getSortColumn())) {
				qry = " order by hrd."+ hrDeductionsDTO.getSortColumn() +" "+hrDeductionsDTO.getSortOrder();
			}
			if(StringUtils.isNoneBlank(hrDeductionsDTO.getEmployeeId())){
				query = "from HrDeductions hrd left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId and hrd.employeeId = :empId";
				hrDeductionsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setParameter("empId", hrDeductionsDTO.getEmployeeId()).list();
			}else{
				query = "from HrDeductions hrd left join fetch hrd.orgLocationId where hrd.orgLocationId.id =:orgLocationId";
				hrDeductionsList = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).list();
			}
			
//			String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
			String countQuery = "from HrDeductions hrd  where hrd.orgLocationId.id =:orgLocationId and hrd.employeeId = :empId  and (hrd.status IS NULL or hrd.status <> '0')";
			
			if(StringUtils.isNotBlank(hrDeductionsDTO.getSearchValue()) && (hrDeductionsDTO.getSearchValue().contains("/") || hrDeductionsDTO.getSearchValue().contains("-"))){
				String convertedDate=hrDeductionsDTO.getSearchValue();
				if(hrDeductionsDTO.getSearchValue().contains("/")){
					convertedDate = hrDeductionsDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from HrDeductions hrd where hrd.orgLocationId.id =:orgLocationId and (hrd.status IS NULL or hrd.status <> '0') and ( hrd.date =:sqlDate or hrd.lastmodified =:sqlDate or hrd.dueDate =:sqlDate)";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder query1 = new StringBuilder("");
				if(hrDeductionsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(hrDeductionsDTO.getSearchValue())) {
//					query1 = " and wts.employeeId like %%";
				query1.append(" and ( hrd.employeeId like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.employeeName like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
//						.append("OR hrd.fromDateStr like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
//						.append("OR hrd.toDateStr like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.dedAmount like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.batchNumber like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.requestSubDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.requestApprovedDate like '%" + hrDeductionsDTO.getSearchValue() + "%' ")
						.append("OR hrd.repaidStatus like '%" + hrDeductionsDTO.getSearchValue() + "%') ");
							/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
				}
			Query queryObj = session.createQuery(query + query1.toString()+qry);
			if (hrDeductionsDTO != null && hrDeductionsDTO.getLength() > 0) {
				queryObj.setFirstResult(hrDeductionsDTO.getStart());
				queryObj.setMaxResults(hrDeductionsDTO.getLength());
			}
			queryObj.setLong("orgLocationId", orgLocation.getId())
			.setLong("empId", Long.parseLong(hrDeductionsDTO.getEmployeeId()));
			hrDeductionsList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setParameter("empId", hrDeductionsDTO.getEmployeeId()).setLong("orgLocationId", orgLocation.getId()).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (hrDeductionsDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).setParameter("empId", hrDeductionsDTO.getEmployeeId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(hrDeductionsList);
			}
				
		}catch (Exception e) {

		e.printStackTrace();
		}
		return response;
	}

}
