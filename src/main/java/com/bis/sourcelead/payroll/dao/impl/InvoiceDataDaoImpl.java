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

import com.bis.sourcelead.dto.InvoiceDetailsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.util.ConversionUtil;

@Repository
public class InvoiceDataDaoImpl implements InvoiceDataDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ConversionUtil conversionUtil;
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<InvoiceData> getInvoiceDetails(InvoiceDetailsDTO invoiceDetailsDTO, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDetailsList = null;
		ResponseList<InvoiceData> response = new ResponseList<>();
		String query = "from InvoiceData ind  left join fetch ind.orgLocationId where ind.orgLocationId.id =:orgLocationId";
		String countQuery = "from InvoiceData ind  where ind.orgLocationId.id =:orgLocationId";
		
		StringBuilder query1 = new StringBuilder("");
			if(invoiceDetailsDTO.getSearchValue() !=null && !"".equalsIgnoreCase(invoiceDetailsDTO.getSearchValue())) {
			query1.append(" and ind.sku like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.productService like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.empName like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.number like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.memoDescription like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.date like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.lastmodified like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.dueDate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.rate like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.qty like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.amount like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.arPaid like '%" + invoiceDetailsDTO.getSearchValue() + "%' ")
					.append("OR ind.oldNew like '%" + invoiceDetailsDTO.getSearchValue() + "%' ");
			}
		Query queryObj = session.createQuery(query + query1.toString());
		if (invoiceDetailsDTO != null && invoiceDetailsDTO.getLength() > 0) {
			queryObj.setFirstResult(invoiceDetailsDTO.getStart());
			queryObj.setMaxResults(invoiceDetailsDTO.getLength());
			queryObj.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		invoiceDetailsList = queryObj.list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (invoiceDetailsDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(getInvoiceListSorting(invoiceDetailsList));
			return response;
	}
		
		private List<InvoiceData> getInvoiceListSorting(List<InvoiceData> invoiceDataList)
	{
			
			for (InvoiceData invoiceData : invoiceDataList) {
				if (invoiceData.getDate() != null) {
					invoiceData.setDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDate()));
				}
				if (invoiceData.getDueDate() != null) {
					invoiceData.setDueDateStr(conversionUtil.getMM_DD_YYYAsString(invoiceData.getDueDate()));
				}
				if (invoiceData.getLastmodified() != null) {
					invoiceData.setLastModifiedStr(conversionUtil.getDateAsDD_MM_YYYY_HHMMSS(invoiceData.getLastmodified()));
				}
			}
			Collections.sort(invoiceDataList, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	InvoiceData invoiceData1 = (InvoiceData)obj1;  
	            	InvoiceData invoiceData2 = (InvoiceData)obj2; 
	            	return invoiceData1.getEmpName().compareToIgnoreCase(invoiceData2.getEmpName());  
	            }  
	        });
			return invoiceDataList;
		
	}

		/*Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
		invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if (invoiceDataList != null && invoiceDataList.size() > 0) {
			return invoiceDataList;
		}
		return invoiceDataList;*/
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId and iData.sku=:empId";
		invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).setCacheable(true).list();
		if (invoiceDataList != null && invoiceDataList.size() > 0) {
			return invoiceDataList;
		}
		return invoiceDataList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public int getInvoiceDataVersionByOrgLocationId(Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT MAX(inv.version) FROM InvoiceData inv where inv.orgLocationId.id =:orgLocationId";
		List<Integer> version = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		Integer count = 0;
		if(version != null && !version.isEmpty() && version.get(0) != null){
				count = version.get(0).intValue();
		}
		return count;
	}

}
