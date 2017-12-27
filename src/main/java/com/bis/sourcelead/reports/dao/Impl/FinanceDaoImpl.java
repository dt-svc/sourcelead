package com.bis.sourcelead.reports.dao.Impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.util.ReportsUtil;

@Repository
public class FinanceDaoImpl implements FinanceDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<APAgingDTO> getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {
		 ResponseList<APAgingDTO> response = new ResponseList<APAgingDTO>();
		try {
			List<Invoice> invoiceList = null;
			List<Long> idsList = new ArrayList<>();
			String query = "";
			if(!"null".equalsIgnoreCase(apAgingDTO.getOrgIds()) && StringUtils.isNotBlank(apAgingDTO.getOrgIds())) {
				String[] ids = apAgingDTO.getOrgIds().split(",");
				for(String relationId : ids) {
					idsList.add(Long.parseLong(relationId));
				}
				 query = "from Invoice invoice left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue' and invoice.orgRelationID.id IN (:orgIds)";
			}else {
				 query = "from Invoice invoice left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue' ";
			}
			
			Session session = this.sessionFactory.getCurrentSession();
			
			 String countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue'";
			StringBuilder searchQuery = new StringBuilder("");
			 if(apAgingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(apAgingDTO.getSearchValue())) {
				 searchQuery.append(" and (invoice.responderLocationId.orgLocationName like '%" + apAgingDTO.getSearchValue() + "%' ")
					.append("OR invoice.orgLocation.orgLocationName like '%" + apAgingDTO.getSearchValue() + "%' )");
				}
			 
			 Query queryObj = session.createQuery(query+searchQuery.toString());
				if (apAgingDTO != null && apAgingDTO.getLength() > 0) {
					queryObj.setFirstResult(apAgingDTO.getStart());
					queryObj.setMaxResults(apAgingDTO.getLength());
				}
				if(!"null".equalsIgnoreCase(apAgingDTO.getOrgIds()) && StringUtils.isNotBlank(apAgingDTO.getOrgIds())) {
				   invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setParameterList("orgIds", idsList).list();
				}else {
					invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();	
				}
				int count = 0;
				int filteredCount = 0;
				List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
				if(countlist != null && !countlist.isEmpty()) {
					count = countlist.iterator().next().intValue();
				}
				response.setRecordsTotal(count);
				
				if (apAgingDTO != null) {
					List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
					if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
						filteredCount = filteredCountlist.iterator().next().intValue();
					}
					response.setRecordsFiltered(filteredCount);
				}
			 
			 
			 if(invoiceList !=null) {
				 Map<Long,List<Invoice>> map = new HashMap<>();
				 for(Invoice inv : invoiceList) {
		    	   OrgRelations orgRelations = orgRelationsService.getOrgRelationById(inv.getOrgRelationID().getId());
		    	   if(map.containsKey(orgRelations.getId())) {
		    	   List<Invoice> apagingInvoiceList = map.get(orgRelations.getId());
		    	   apagingInvoiceList.add(inv);
		    	   map.put(orgRelations.getId(), apagingInvoiceList);
		    	   }else {
		    	   List<Invoice> apagingInvoiceList = new ArrayList<>();
		    	   apagingInvoiceList.add(inv);
		    	   map.put(orgRelations.getId(), apagingInvoiceList);
		    	   }
		       }
				   Date[] tenDays = reportsUtil.tenDays(10);
		    	   Date[] fifteenDays = reportsUtil.apAgingPeriodDays(11,15);
		    	   Date[] thirtyDays = reportsUtil.apAgingPeriodDays(16,30);
		    	   Date[] sixtyDays = reportsUtil.apAgingPeriodDays(31,60);
		    	   Date[] nintyDays = reportsUtil.apAgingPeriodDays(61,90);
		    	   Date[] oneEightyDays = reportsUtil.apAgingPeriodDays(91,180);
		    	   Date[] threeSictyDays = reportsUtil.apAgingPeriodDays(181,360);
				 List<APAgingDTO> apaAgingList = new ArrayList<>();
				 for(Map.Entry<Long, List<Invoice>> entry : map.entrySet()) {
					 APAgingDTO apagingDTO = new APAgingDTO();
			    	   List<Invoice> invList = entry.getValue();
			    	   double tenDaysTotal = 0;
			    	   double fifteenDaysTotal = 0;
			    	   double thirtyDaysTotal = 0;
			    	   double sixtyDaysTotal = 0;
			    	   double nintyDaysTotal = 0;
			    	   double oneEightyDaysTotal = 0;
			    	   double threeSixtyDaysTotal = 0;
			    	   double total = 0;
			    	   for(Invoice invoice : invList) {
			    		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    		   Date dueDate = sdf.parse(sdf.format(invoice.getDueDate()));
			    		   if(dueDate.after(tenDays[0]) && dueDate.before(tenDays[1])) {
			    			   tenDaysTotal = tenDaysTotal + Double.parseDouble(invoice.getTotal());
			    		   }
			    		   if(dueDate.after(fifteenDays[0]) && dueDate.before(fifteenDays[1])) {
			    			   fifteenDaysTotal = fifteenDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if(dueDate.after(thirtyDays[0]) && dueDate.before(thirtyDays[1])) {
			    			   thirtyDaysTotal = thirtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if(dueDate.after(sixtyDays[0]) && dueDate.before(sixtyDays[1])) {
			    			   sixtyDaysTotal = sixtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if(dueDate.after(nintyDays[0]) && dueDate.before(nintyDays[1])) {
			    			   nintyDaysTotal = nintyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if(dueDate.after(oneEightyDays[0]) && dueDate.before(oneEightyDays[1])) {
			    			   oneEightyDaysTotal = oneEightyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if(dueDate.after(threeSictyDays[0]) && dueDate.before(threeSictyDays[1])) {
			    			   threeSixtyDaysTotal = threeSixtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   
			    	   }
			    	   total = total + tenDaysTotal + fifteenDaysTotal + thirtyDaysTotal + sixtyDaysTotal + nintyDaysTotal + oneEightyDaysTotal + threeSixtyDaysTotal;
			    	   OrgRelations orgRelations = orgRelationsService.getOrgRelationById(entry.getKey());
			    	   if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
			    		   apagingDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
						}
						if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							apagingDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
						}
						   apagingDTO.setTenDays(tenDaysTotal);
						   apagingDTO.setFifteenDays(fifteenDaysTotal);
						   apagingDTO.setThirtyDays(thirtyDaysTotal);
						   apagingDTO.setSixtyDays(sixtyDaysTotal);
						   apagingDTO.setNintyDays(nintyDaysTotal);
						   apagingDTO.setOneeightyDays(oneEightyDaysTotal);
						   apagingDTO.setThreeSixtyFiveDays(threeSixtyDaysTotal);
						   apagingDTO.setTotal(total);
			    	       apaAgingList.add(apagingDTO);
			    	   }
				 response.setRecordsTotal(map.size());
				 response.setRecordsFiltered(map.size());
				 response.setData(apaAgingList);
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			return response;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<Invoice> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {

		ResponseList<Invoice> response = new ResponseList<Invoice>();
		try {
			List<Invoice> invoiceList = null;
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Invoice invoice left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue'";
			String countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and invoice.paymentStatus = 'Overdue'";
			
			
			StringBuilder searchQuery = new StringBuilder("");
			 if(apAgingDTO.getSearchValue() !=null && !"".equalsIgnoreCase(apAgingDTO.getSearchValue())) {
				
				/* if(apAgingDTO.getSearchValue().contains("/") || apAgingDTO.getSearchValue().contains("-")){
						String convertedDate=apAgingDTO.getSearchValue();
						if(apAgingDTO.getSearchValue().contains("/")){
							convertedDate = apAgingDTO.getSearchValue().replaceAll("/", "-");
						}
						DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocation.getDateFormat()).toFormatter();
			            LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
			            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			            String newDate = parsedDate.format(f2);
			            Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
			            java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
			            String queryDate = query + " ( invoice.invoiceDate =:sqlDate or invoice.dueDate =:sqlDate )";
			            Query queryObjDate = session.createQuery(queryDate.toString()).setDate("sqlDate", sqlDate).setLong("orgLocationId", orgLocation.getId());
			            response.setData(queryObjDate.list());
						}else {*/
							searchQuery.append(" and (invoice.responderLocationId.orgLocationName like '%" + apAgingDTO.getSearchValue() + "%' ")
							.append("OR invoice.orgLocation.orgLocationName like '%" + apAgingDTO.getSearchValue() + "%' ")
							.append("OR invoice.total like '%" + apAgingDTO.getSearchValue() + "%' ")
							.append("OR invoice.invoiceId like '%" + apAgingDTO.getSearchValue() + "%' ")
							.append("OR invoice.dueAmount like '%" + apAgingDTO.getSearchValue() + "%' )");
//						}
				}
			
			
			Query queryObj = session.createQuery(query+searchQuery);
			if (apAgingDTO != null && apAgingDTO.getLength() > 0) {
				queryObj.setFirstResult(apAgingDTO.getStart());
				queryObj.setMaxResults(apAgingDTO.getLength());
			}
			invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
			response.setData(invoiceList);
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType())
					.list();
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);

			if (apAgingDTO != null) {
				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
				if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
					filteredCount = filteredCountlist.iterator().next().intValue();
				}
				response.setRecordsFiltered(filteredCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
