package com.bis.sourcelead.payroll.dao.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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

import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class InvoiceDataDaoImpl implements InvoiceDataDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public InvoiceData saveInvoiceData(InvoiceData invoiceData) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(invoiceData);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoiceData;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, OrgLocations orgLocation) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDetailsList = null;
		ResponseList<InvoiceData> response = new ResponseList<>();
		
		String qry = "";
		if(StringUtils.isNotBlank(invoiceDetailsDTO.getSortColumn())) {
			qry = " order by ind."+ invoiceDetailsDTO.getSortColumn() +" "+invoiceDetailsDTO.getSortOrder();
		}
		
		String query = "from InvoiceData ind where ind.orgLocationId.id =:orgLocationId and (ind.status IS NULL or ind.status <> '0')";
		String countQuery = "from InvoiceData ind  where ind.orgLocationId.id =:orgLocationId and (ind.status IS NULL or ind.status <> '0')";
		
		if(invoiceDetailsDTO.getSearchValue().contains("/")){
			String convertedDate=invoiceDetailsDTO.getSearchValue();
			if(invoiceDetailsDTO.getSearchValue().contains("/")){
				convertedDate = invoiceDetailsDTO.getSearchValue().replaceAll("/", "-");
			}
			DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String newDate = parsedDate.format(f2);
            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
            String queryDate = "from InvoiceData ind where ind.orgLocationId.id =:orgLocationId and (ind.status IS NULL or ind.status <> '0') and ( ind.date =:sqlDate or ind.dueDate =:sqlDate)";
            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
            List<InvoiceData> temp = queryObjDate.list();
            for(InvoiceData invoiceData : temp)
            {
            	invoiceData.setDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoiceData.getDate()));
            	invoiceData.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), invoiceData.getDueDate()));
            	invoiceData.setLastModifiedStr(invoiceData.getLastmodified().toString());
            }
            response.setData(queryObjDate.list());

		}else{
		StringBuilder query1 = new StringBuilder("");
			if(invoiceDetailsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(invoiceDetailsDTO.getSearchValue())) {
			query1.append(" and (ind.sku like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.productService like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.empName like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.number like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.memoDescription like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.date like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.lastmodified like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.dueDate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.rate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.payRate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.qty like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.amount like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.arPaid like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.oldNew like '%" + invoiceDetailsDTO.getSearchValue() + "%') ");
			}
		Query queryObj = session.createQuery(query + query1.toString()+qry);
		if (invoiceDetailsDTO != null && invoiceDetailsDTO.getLength() > 0) {
			queryObj.setFirstResult(invoiceDetailsDTO.getStart());
			queryObj.setMaxResults(invoiceDetailsDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocation.getId()).list();
		}
		invoiceDetailsList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocation.getId()).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (invoiceDetailsDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocation.getId()).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 
//			response.setData(getInvoiceListSorting(invoiceDetailsList));
			response.setData(invoiceDetailsList);
		}
			return response;
	}
		
//		private List<InvoiceData> getInvoiceListSorting(List<InvoiceData> invoiceDataList)
//	{
//			
//			for (InvoiceData invoiceData : invoiceDataList) {
//				if (invoiceData.getDate() != null) {
//					invoiceData.setDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDate()));
//				}
//				if (invoiceData.getDueDate() != null) {
//					invoiceData.setDueDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDueDate()));
//				}
//				if (invoiceData.getLastmodified() != null) {
//					invoiceData.setLastModifiedStr(conversionUtil.getDateAsDD_MM_YYYY_HHMMSS(invoiceData.getLastmodified()));
//				}
//			}
//			Collections.sort(invoiceDataList, new Comparator() {  
//	            @Override  
//	            public int compare(Object obj1, Object obj2) {  
//	            	InvoiceData invoiceData1 = (InvoiceData)obj1;  
//	            	InvoiceData invoiceData2 = (InvoiceData)obj2; 
//	            	return invoiceData1.getEmpName().compareToIgnoreCase(invoiceData2.getEmpName());  
//	            }  
//	        });
//			return invoiceDataList;
//		
//	}

		/*Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
		invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		if (invoiceDataList != null && invoiceDataList.size() > 0) {
			return invoiceDataList;
		}
		return invoiceDataList;*/
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<InvoiceData> getInvoiceDetailsByEmpId(OrgLocations orgLocations,InvoiceDetailsDTO invoiceDetailsDTO) throws Exception {
		
		ResponseList<InvoiceData> response = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<InvoiceData> invoiceDataList = null;
			String qry = "";
			String query = "";
			if(StringUtils.isNotBlank(invoiceDetailsDTO.getSortColumn())) {
				qry = " order by iData."+ invoiceDetailsDTO.getSortColumn() +" "+invoiceDetailsDTO.getSortOrder();
			}
			if(StringUtils.isNoneBlank(invoiceDetailsDTO.getEmpId())){
				query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId and iData.sku =:empId";
				invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setParameter("empId", invoiceDetailsDTO.getEmpId()).list();
			}else{
				query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
				invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			}
//			String query = "from WageTaxSummary wts  left join fetch wts.orgLocationId where wts.orgLocationId.id =:orgLocationId and (wts.status IS NULL or wts.status <> '0')";
			String countQuery = "from InvoiceData iData  where iData.orgLocationId.id =:orgLocationId and iData.sku = :empId  and (iData.status IS NULL or iData.status <> '0')";
			
			if(StringUtils.isNoneBlank(invoiceDetailsDTO.getSearchValue()) && (invoiceDetailsDTO.getSearchValue().contains("/") || invoiceDetailsDTO.getSearchValue().contains("-"))){
				String convertedDate=invoiceDetailsDTO.getSearchValue();
				if(invoiceDetailsDTO.getSearchValue().contains("/")){
					convertedDate = invoiceDetailsDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
	            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
	            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            String newDate = parsedDate.format(f2);
	            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
	            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
	            String queryDate = "from InvoiceData iData where iData.orgLocationId.id =:orgLocationId and (iData.status IS NULL or iData.status <> '0') and ( iData.date =:sqlDate or iData.lastmodified =:sqlDate or iData.dueDate =:sqlDate)";
	            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocations.getId());
	            response.setData(queryObjDate.list());

			}else{
			StringBuilder query1 = new StringBuilder("");
				if(invoiceDetailsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(invoiceDetailsDTO.getSearchValue())) {
//					query1 = " and wts.employeeId like %%";
				query1.append(" and ( iData.sku like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.productService like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.empName like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.number like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.memoDescription like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.date like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.lastmodified like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.dueDate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.rate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.qty like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.amount like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.arPaid like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
						.append("OR iData.oldNew like '%" + invoiceDetailsDTO.getSearchValue() + "%' )");
							/*+ "OR wts.checkDate like '%" + wagesTaxSummaryDTO.getSearchValue() + "%'";*/
				}
			Query queryObj = session.createQuery(query + query1.toString()+qry);
			if (invoiceDetailsDTO != null && invoiceDetailsDTO.getLength() > 0) {
				queryObj.setFirstResult(invoiceDetailsDTO.getStart());
				queryObj.setMaxResults(invoiceDetailsDTO.getLength());
			}
			queryObj.setLong("orgLocationId", orgLocations.getId()).setLong("empId", Long.parseLong(invoiceDetailsDTO.getEmpId()));
			invoiceDataList = queryObj.list();
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setParameter("empId", invoiceDetailsDTO.getEmpId()).setLong("orgLocationId", orgLocations.getId()).list();
			if(countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);
			if (invoiceDetailsDTO != null) {
				List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocations.getId()).setParameter("empId", invoiceDetailsDTO.getEmpId()).list();
				if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			} 
				response.setData(invoiceDataList);
			}
				
		}catch (Exception e) {

		e.printStackTrace();
		}
		return response;
//	}
		
		
		/*Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "";
		if(empId != null){
			query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId and iData.sku=:empId";
			invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
			if (invoiceDataList != null && invoiceDataList.size() > 0) {
				return invoiceDataList;
			}
		}else{
			query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
			invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if (invoiceDataList != null && invoiceDataList.size() > 0) {
				return invoiceDataList;
			}
		}
		return invoiceDataList;*/
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getInvoiceDataVersionByOrgLocationId(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(inv.version) FROM InvoiceData inv where inv.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<InvoiceData> getAllEmployeeInvoiceDetailsByEmpIdAndOrgLocationId(Long orgLocationId, Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<InvoiceData> res = new ResponseList<>();
		String query = "from InvoiceData invoiceData where invoiceData.sku in (select distinct(wts.employeeId) from WageTaxSummary wts where wts.userId.id =:userId and wts.orgLocationId.id =:orgLocationId)";
		List<InvoiceData> list = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
		res.setData(list);
		return res;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations) {
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "";
		if(empId != null){
			query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId and iData.sku=:empId";
			invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).list();
			if (invoiceDataList != null && invoiceDataList.size() > 0) {
				return invoiceDataList;
			}
		}else{
			query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
			invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
			if (invoiceDataList != null && invoiceDataList.size() > 0) {
				return invoiceDataList;
			}
		}
		return invoiceDataList;
	}

}
