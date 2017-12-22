package com.bis.sourcelead.reports.dao.Impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.reports.dao.FinanceDao;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.service.OrgRelationsService;

@Repository
public class FinanceDaoImpl implements FinanceDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<Invoice> getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {
		 ResponseList<Invoice> response = new ResponseList<Invoice>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			 String query = "from Invoice invoice left join fetch invoice.orgRelationID where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue'";
			 List<Invoice> invoiceList = session.createQuery(query).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setCacheable(true).list();
	       for(Invoice inv : invoiceList) {
	    	   OrgRelations orgRelations = orgRelationsService.getOrgRelationById(inv.getOrgRelationID().getId());
	    	 
	    	   
	    	   
	    	   if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
	    		   System.out.println(orgRelations.getResponderLocationId().getOrgLocationName());
	    		   System.out.println(orgRelations.getId());
				}
				if(inv.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					 System.out.println(orgRelations.getResponderLocationId().getOrgLocationName());
					System.out.println(orgRelations.getId());
				}
	    	  
	       }
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
//	    List<Invoice> invoiceList = null;
//		
//	    ResponseList<Invoice> response = new ResponseList<Invoice>();
//	    String query = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1'";
//	    String countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1'";
//		Query queryObj = session.createQuery(query);
//		if (apAgingDTO != null && apAgingDTO.getLength() > 0) {
//			queryObj.setFirstResult(apAgingDTO.getStart());
//			queryObj.setMaxResults(apAgingDTO.getLength());
//		}
//		invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setCacheable(true).list();
//		int count = 0;
//		int filteredCount = 0;
//		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
//		if(countlist != null && !countlist.isEmpty()) {
//			count = countlist.iterator().next().intValue();
//		}
//		response.setRecordsTotal(count);
//		if (apAgingDTO != null) {
//			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
//			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
//				filteredCount = filteredCountlist.iterator().next().intValue();
//			}
//			response.setRecordsFiltered(filteredCount);
//		} 
//			response.setData(invoiceList);
			return response;
		
	}
	

}
