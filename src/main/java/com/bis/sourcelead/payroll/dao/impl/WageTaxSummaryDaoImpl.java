package com.bis.sourcelead.payroll.dao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public ResponseList<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation,WagesTaxSummaryDTO wagesTaxSummaryDTO)
	{
		
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		ResponseList<WageTaxSummary> response = new ResponseList<>();
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId";
		String countQuery = "from WageTaxSummary wts  where wts.orgLocationId.id =:orgLocationId";
		
		StringBuilder query1 = new StringBuilder("");
			if(wagesTaxSummaryDTO.getSearchValue() !=null && !"".equalsIgnoreCase(wagesTaxSummaryDTO.getSearchValue())) {
//				query1 = " and wts.employeeId like %%";
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
		Query queryObj = session.createQuery(query + query1.toString());
		if (wagesTaxSummaryDTO != null && wagesTaxSummaryDTO.getLength() > 0) {
			queryObj.setFirstResult(wagesTaxSummaryDTO.getStart());
			queryObj.setMaxResults(wagesTaxSummaryDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		}
		wagesAndTaxlist = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
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
		} else {
			response.setRecordsFiltered(filteredCount);
		}

		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			response.setData(getSortWageTaxList(wagesAndTaxlist));
		}
		return response;
	}

	/**
	 * Hiding ssn number into unreadable format
	 * @param wagesAndTaxList
	 * @return List<WageTaxSummary>
	 */
	private List<WageTaxSummary> getSortWageTaxList(List<WageTaxSummary> wagesAndTaxList) {
		if(wagesAndTaxList != null){
			for(WageTaxSummary wts : wagesAndTaxList){
				StringBuilder sb = new StringBuilder();
				if(null !=  wts.getFromDate()){
					wts.setFromDateStr(conversionUtil.getDateAsString(wts.getFromDate()));
				}
				if(null != wts.getToDate()){
					wts.setToDateStr(conversionUtil.getDateAsString(wts.getToDate()));
				}
				
				if(wts.getCheckDate() !=null   ){
					wts.setCheckDateStr(conversionUtil.getDateAsString(wts.getCheckDate()));
				}
				if(wts.getCheckNo() != null){
					wts.setCheckNo(wts.getCheckNo());
				}
				String ssn = wts.getSsnNumber();
				String[] arr = ssn.split("-");
				sb.append("xxx").append("-").append("xx").append("-").append(arr[0]);
				wts.setSsnNumber(sb.toString());
			}
			
			Collections.sort(wagesAndTaxList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	WageTaxSummary wageTaxSummary1 = (WageTaxSummary)obj1;  
	            	WageTaxSummary wageTaxSummary2 = (WageTaxSummary)obj2; 
	            	return wageTaxSummary1.getEmployeeName().compareToIgnoreCase(wageTaxSummary2.getEmployeeName());  
	            }  
	        });
		}
		return wagesAndTaxList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<WageTaxSummary> wagesAndTaxlist = null;
		String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and wts.employeeId = :empId";
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId())
				.setLong("empId", empId).setCacheable(true).list();
		if (wagesAndTaxlist != null && wagesAndTaxlist.size() > 0) {
			return wagesAndTaxlist;
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
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setCacheable(true).list();
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
		wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", locationId).setCacheable(true).list();
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
	    wagesAndTaxlist = session.createQuery(query).setLong("orgLocationId", orgLocation.getId()).setCacheable(true).list();
		if(wagesAndTaxlist != null && wagesAndTaxlist.size() >0)
		{
			return wagesAndTaxlist;
		}
		return wagesAndTaxlist;
	}
	

}
