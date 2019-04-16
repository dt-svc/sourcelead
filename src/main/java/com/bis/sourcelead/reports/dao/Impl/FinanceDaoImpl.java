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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.reports.dao.FinanceDao;
import com.bis.sourcelead.reportsDTO.APAgingDTO;
import com.bis.sourcelead.reportsDTO.AparagingDate;
import com.bis.sourcelead.reportsDTO.MissingInvoiceDTO;
import com.bis.sourcelead.reportsDTO.PaidBillDTO;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;

@Repository
public class FinanceDaoImpl implements FinanceDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
	TimeSheetDayEntriesDao timeSheetDayEntriesDao;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
    OrgLocationService orgLocationService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {
		
		APAgingDTO apagingdto = new APAgingDTO();
		ResponseList<APAgingDTO> response = new ResponseList<APAgingDTO>();
		try {
			List<Invoice> invoiceList = null;
			List<Long> idsList = new ArrayList<>();
			String query = "";
			if (!"null".equalsIgnoreCase(apAgingDTO.getOrgIds()) && StringUtils.isNotBlank(apAgingDTO.getOrgIds())) {
				String[] ids = apAgingDTO.getOrgIds().split(",");
				for (String relationId : ids) {
					idsList.add(Long.parseLong(relationId));
				}
				query = "from Invoice invoice  left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'DRAFT' and invoice.orgRelationID.id IN (:orgIds)";
			} else {
				query = "from Invoice invoice  left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'DRAFT' ";
			}

			Session session = this.sessionFactory.getCurrentSession();

			String countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked'";
			StringBuilder searchQuery = new StringBuilder("");
			if (apAgingDTO.getSearchValue() != null && !"".equalsIgnoreCase(apAgingDTO.getSearchValue())) {
				searchQuery
						.append(" and (invoice.responderLocationId.orgLocationName like '%"
								+ apAgingDTO.getSearchValue() + "%' ")
						.append("OR invoice.orgLocation.orgLocationName like '%" + apAgingDTO.getSearchValue()
								+ "%' )");
			}

			Query queryObj = session.createQuery(query + searchQuery.toString());
			if (apAgingDTO != null && apAgingDTO.getLength() > 0) {
				queryObj.setFirstResult(apAgingDTO.getStart());
				queryObj.setMaxResults(apAgingDTO.getLength());
			}
			if (!"null".equalsIgnoreCase(apAgingDTO.getOrgIds()) && StringUtils.isNotBlank(apAgingDTO.getOrgIds())) {
				invoiceList = queryObj.setLong("orglocationId", orgLocation.getId())
						.setString("invoiceType", apAgingDTO.getApagingType()).setParameterList("orgIds", idsList)
						.list();
			} else {
				invoiceList = queryObj.setLong("orglocationId", orgLocation.getId())
						.setString("invoiceType", apAgingDTO.getApagingType()).list();
			}
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = session.createQuery("select count(*) " + countQuery)
					.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType())
					.list();
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);

			if (apAgingDTO != null) {
				List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery)
						.setLong("orglocationId", orgLocation.getId())
						.setString("invoiceType", apAgingDTO.getApagingType()).list();
				if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
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
		    	   Date above365Days = reportsUtil.above365Days();
				 List<APAgingDTO> apaAgingList = new ArrayList<>();
				 List<AparagingDate> above365Dates = new ArrayList<>();
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
			    	   double abovethreeSixtyFiveDaysToatl = 0;
			    	   double total = 0;
			    	   for(Invoice invoice : invList) {
			    		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    		   AparagingDate aparagingdate = new AparagingDate();
			    		   Date dueDate = sdf.parse(sdf.format(invoice.getDueDate()));
			    		   if((dueDate.after(tenDays[0]) || dueDate.equals(tenDays[0])) && (dueDate.before(tenDays[1]) || dueDate.equals(tenDays[1]))) {
			    			   tenDaysTotal = tenDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(fifteenDays[0]) || dueDate.equals(fifteenDays[0])) && (dueDate.before(fifteenDays[1]) || dueDate.equals(fifteenDays[1]))) {
			    			   fifteenDaysTotal = fifteenDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(thirtyDays[0]) || dueDate.equals(thirtyDays[0])) && (dueDate.before(thirtyDays[1]) || dueDate.equals(thirtyDays[1]))) {
			    			   thirtyDaysTotal = thirtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(sixtyDays[0]) || dueDate.equals(sixtyDays[0])) && (dueDate.before(sixtyDays[1]) || dueDate.equals(sixtyDays[1]))) {
			    			   sixtyDaysTotal = sixtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(nintyDays[0]) || dueDate.equals(nintyDays[0])) && (dueDate.before(nintyDays[1]) || dueDate.equals(nintyDays[1]))) {
			    			   nintyDaysTotal = nintyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(oneEightyDays[0]) || dueDate.equals(oneEightyDays[0])) && (dueDate.before(oneEightyDays[1]) || dueDate.equals(oneEightyDays[1]))) {
			    			   oneEightyDaysTotal = oneEightyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }
			    		   if((dueDate.after(threeSictyDays[0]) || dueDate.equals(threeSictyDays[0])) && (dueDate.before(threeSictyDays[1]) || dueDate.equals(threeSictyDays[1]))) {
			    			   threeSixtyDaysTotal = threeSixtyDaysTotal + Double.parseDouble(invoice.getDueAmount());
			    		   }if(dueDate.before(above365Days)) {
			    			   abovethreeSixtyFiveDaysToatl = abovethreeSixtyFiveDaysToatl +  Double.parseDouble(invoice.getDueAmount());
			    			   aparagingdate.setDate(dueDate);
			    			   aparagingdate.setAmount(Double.parseDouble(invoice.getDueAmount()));
			    		   }
			    		   if(aparagingdate.getDate() !=null) {
			    			   above365Dates.add(aparagingdate);
			    		   }
			    		   
			    	   }
			    	   total = total + tenDaysTotal + fifteenDaysTotal + thirtyDaysTotal + sixtyDaysTotal + nintyDaysTotal + oneEightyDaysTotal + threeSixtyDaysTotal + abovethreeSixtyFiveDaysToatl;
			    	   OrgRelations orgRelations = orgRelationsService.getOrgRelationById(entry.getKey());
			    	   if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
			    		   apagingDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
						}
						if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							apagingDTO.setOrgName(orgRelations.getLocationId().getOrgLocationName());
						}
						   apagingDTO.setTenDays(tenDaysTotal);
						   apagingDTO.setFifteenDays(fifteenDaysTotal);
						   apagingDTO.setThirtyDays(thirtyDaysTotal);
						   apagingDTO.setSixtyDays(sixtyDaysTotal);
						   apagingDTO.setNintyDays(nintyDaysTotal);
						   apagingDTO.setOneeightyDays(oneEightyDaysTotal);
						   apagingDTO.setThreeSixtyFiveDays(threeSixtyDaysTotal);
						   apagingDTO.setAbovethreeSixtyFiveDays(abovethreeSixtyFiveDaysToatl);
						   apagingDTO.setTotal(total);
			    	       apaAgingList.add(apagingDTO);
			    	   }
				 response.setRecordsTotal(map.size());
				 apagingdto.setRecordsTotal(map.size());
				 apagingdto.setRecordsFiltered(map.size());
				 apagingdto.setApaAgingList(apaAgingList);
				 apagingdto.setAbove365Dates(above365Dates);
				 response.setRecordsFiltered(map.size());
				 response.setData(apaAgingList);
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			return apagingdto;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<Invoice> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO) {

		ResponseList<Invoice> response = new ResponseList<Invoice>();
		try {
			List<Invoice> invoiceList = null;
			Session session = this.sessionFactory.getCurrentSession();
			String query = "";
			String countQuery = "";
			java.sql.Date startDate = null;
			java.sql.Date endDate = null;
			if(StringUtils.isNotBlank(apAgingDTO.getFilterType())) {
				if("last_7days".equalsIgnoreCase(apAgingDTO.getFilterType())) {
					Date[] dates = reportsUtil.last7Days(orgLocation);
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}else if("last_15days".equalsIgnoreCase(apAgingDTO.getFilterType())) {
					Date[] dates = reportsUtil.last15DaysDates(orgLocation);
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}else if("1month".equalsIgnoreCase(apAgingDTO.getFilterType())) {
					Date[] dates =  reportsUtil.last1MonthDates(orgLocation);
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}else if("3months".equalsIgnoreCase(apAgingDTO.getFilterType())) {
					Date[] dates = reportsUtil.last3MonthDates(orgLocation);
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}else if("last_years".equalsIgnoreCase(apAgingDTO.getFilterType())) {
					Date[] dates = reportsUtil.lastYearDates();
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}else if("custom".equalsIgnoreCase(apAgingDTO.getFilterType())){
					Date[] dates = reportsUtil.getCustomDates(apAgingDTO.getFromDate(),apAgingDTO.getToDate(),orgLocation);
					
					startDate = new java.sql.Date(dates[0].getTime());
					endDate = new java.sql.Date(dates[1].getTime());
				}
				 query = "from Invoice invoice left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked' and (invoice.dueDate >= :startDate and invoice.dueDate <= :endDate)";
				 countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked' and (invoice.dueDate >= :startDate and invoice.dueDate <= :endDate) ";
			}else {
				 query = "from Invoice invoice left join fetch invoice.orgRelationID left join fetch invoice.responderLocationId left join fetch invoice.orgLocation where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked'";
				 countQuery = "from Invoice invoice where invoice.orgLocation.id = :orglocationId and invoice.invoiceType = :invoiceType and invoice.status = '1' and (invoice.paymentStatus = 'Unpaid' OR invoice.paymentStatus = 'Partial Paid') and invoice.invoiceStatus <> 'Linked'";
					
			}
			
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
			if(StringUtils.isNotBlank(apAgingDTO.getFilterType())) {
				invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
			}else {
				invoiceList = queryObj.setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
			}
			
			response.setData(invoiceList);
			int count = 0;
			int filteredCount = 0;
			List<Long> countlist = null;
			if(StringUtils.isNotBlank(apAgingDTO.getFilterType())) {
			countlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
			}else {
				countlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();	
			}
			if (countlist != null && !countlist.isEmpty()) {
				count = countlist.iterator().next().intValue();
			}
			response.setRecordsTotal(count);

			if (apAgingDTO != null) {
				List<Long> filteredCountlist = null;
				if(StringUtils.isNotBlank(apAgingDTO.getFilterType())) {
					filteredCountlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
				}else {
					filteredCountlist = session.createQuery("select count(*) " + countQuery).setLong("orglocationId", orgLocation.getId()).setString("invoiceType", apAgingDTO.getApagingType()).list();
				}
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<SubmissionApproverHierarchy> getMissingInvoiceData(MissingInvoiceDTO missingInvoiceDto, OrgLocations orgLocations) {
			
			ResponseList<SubmissionApproverHierarchy> response = new ResponseList<>();
			try {
				Session session = this.sessionFactory.getCurrentSession();
				String subQuery = "";
				String groupQuery = "";
				if("invoice".equalsIgnoreCase(missingInvoiceDto.getInvoiceType())) {
					subQuery = "(sah.invoiceStatus IS NULL OR sah.invoiceStatus = 'DRAFT')";
				}else {
					subQuery = "(sah.billingStatus IS NULL OR sah.billingStatus = 'DRAFT')";
				}
				List<Long> orgIds = new ArrayList<>();
				if(!"null".equalsIgnoreCase(missingInvoiceDto.getOrgIds()) && missingInvoiceDto.getOrgIds() != null) {
					String[] ids = missingInvoiceDto.getOrgIds().split(",");
					for(String id : ids) {
						orgIds.add(Long.parseLong(id));
					}
				}
				java.sql.Date startDate = null;
				java.sql.Date endDate = null;
				String dateQuery = "";
				if(StringUtils.isNotBlank(missingInvoiceDto.getFilterType()) && !"undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
					if("last_7days".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
						Date[] dates = reportsUtil.last7Days(orgLocations);
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}else if("last_15days".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
						Date[] dates = reportsUtil.last15DaysDates(orgLocations);
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}else if("1month".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
						Date[] dates =  reportsUtil.last1MonthDates(orgLocations);
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}else if("3months".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
						Date[] dates = reportsUtil.last3MonthDates(orgLocations);
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}else if("last_years".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
						Date[] dates = reportsUtil.lastYearDates();
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}else if("custom".equalsIgnoreCase(missingInvoiceDto.getFilterType())){
						Date[] dates = reportsUtil.getCustomDates(missingInvoiceDto.getFromDate(),missingInvoiceDto.getToDate(),orgLocations);
						startDate = new java.sql.Date(dates[0].getTime());
						endDate = new java.sql.Date(dates[1].getTime());
					}
					
					if("invoice".equalsIgnoreCase(missingInvoiceDto.getInvoiceType())) {
						dateQuery = " and ((sah.invoiceDate IS NOT NULL and (sah.invoiceDate >= :startDate and sah.invoiceDate <= :endDate)) OR (sah.invoiceDate IS  NULL and (sah.updatedDate >= :startDate and sah.updatedDate <= :endDate))) ";
					}else {
						dateQuery = " and ((sah.billDate IS NOT NULL and (sah.billDate >= :startDate and sah.billDate <= :endDate)) OR (sah.billDate IS  NULL and (sah.updatedDate >= :startDate and sah.updatedDate <= :endDate))) ";
					}
				}
				String inQuery = "";
				if(orgIds.size() >0) {
					inQuery = "and pclient.orgRelationId.id IN (:orgIds)";
				}
				/*StringBuilder searchCountQuery = new StringBuilder();
				if(StringUtils.isNotEmpty(missingInvoiceDto.getSearchValue()) && missingInvoiceDto.getSearchValue() != null) {
					searchCountQuery.append(" and (sah.hierarchyId.projectId.projectName like '%" + missingInvoiceDto.getSearchValue() + "%' )");
							.append("OR sah.hierarchyId.projectClientsId.orgRelationId. like '%" + searchValue + "%')");
				}
				*/
				
				StringBuilder searchQuery = new StringBuilder();
				if(StringUtils.isNotEmpty(missingInvoiceDto.getSearchValue()) && missingInvoiceDto.getSearchValue() != null) {
					
					Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
					   Matcher matcher = pattern.matcher(missingInvoiceDto.getSearchValue());
					   java.sql.Date sqlDate = null;
					    if(matcher.matches() == true) {
									if(missingInvoiceDto.getSearchValue().contains("/")||(missingInvoiceDto.getSearchValue().contains("-"))) {
										String replacedValue = missingInvoiceDto.getSearchValue().replaceAll("/", "-");
										    OrgLocations orgLoc = orgLocationService.getOrgLocations(orgLocations.getId());
											SimpleDateFormat format = new SimpleDateFormat(orgLoc.getDateFormat());
									        java.util.Date utilDate = format.parse(replacedValue);
									        sqlDate = new java.sql.Date(utilDate.getTime());
									}
					    }
					
					
					searchQuery.append(" and ( (sah.submissionId.projectMembers.projectMemberCandidteId.firstName like '%" + missingInvoiceDto.getSearchValue() + "%' OR  sah.submissionId.projectMembers.projectMemberCandidteId.firstName.lastName like '%" + missingInvoiceDto.getSearchValue() + "%')")
					           .append(" OR (concat(sah.submissionId.projectMembers.projectMemberCandidteId.firstName.firstName,sah.submissionId.projectMembers.projectMemberCandidteId.firstName.lastName) like '%" + missingInvoiceDto.getSearchValue().replaceAll("[\\s]+", "").trim() + "%')" )
					           .append(" OR sah.hierarchyId.projectId.projectName  like '%" + missingInvoiceDto.getSearchValue() + "%' )");
					if(sqlDate != null) {
						searchQuery.append("OR sah.frequencyStartDate like '%" + sqlDate + "%' OR sah.frequencyEndDate like '%" + sqlDate + "%' ");
						searchQuery.append("OR sah.updatedDate like '%" + sqlDate + "%' ");
					 }
				}
				
				String qry = "from  SubmissionApproverHierarchy sah left join fetch sah.invoiceId invoice left join fetch sah.billId billinvoice left join fetch sah.submissionId subId left join fetch subId.projectMembers pmember left join fetch pmember.projectMemberCandidteId can left join fetch can.orgRelationId left join fetch subId.projects prjcts left join fetch sah.hierarchyId hierarchy left join fetch sah.invoiceId invoice left join fetch sah.billId bill left join fetch hierarchy.projectId proj left join fetch hierarchy.projectClientsId pclient  left join fetch pclient.orgRelationId orgRelation "
						+ " where  pclient.orgLocationsId.id = :orgLocationId and  sah.submissionApprovalStatus = 'APPROVED' and sah.submissionId.status = '1' and "+subQuery;//OR (sah.invoiceStatus IS NOT NULL and sah.invoiceSatus = 'DRAFT')) ";
				
				String queryCount = " Select Count (*) from  SubmissionApproverHierarchy sah  where  sah.hierarchyId.projectClientsId.orgLocationsId.id = :orgLocationId and "
						   + " sah.submissionApprovalStatus = 'APPROVED' and sah.submissionId.status = '1' and "+ subQuery; //OR (sah.invoiceStatus IS NOT NULL and sah.invoiceSatus = 'DRAFT'))" ;
				
				String query = "";
				if(StringUtils.isNotBlank(missingInvoiceDto.getFilterType()) && !"undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType())) {
					query = qry+dateQuery;
				}if(orgIds.size() > 0 && StringUtils.isBlank(missingInvoiceDto.getFilterType()) || "undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType())){
					query = qry + inQuery;
				}if(orgIds.size() > 0 && StringUtils.isNotBlank(missingInvoiceDto.getFilterType())){
					query = qry+dateQuery+inQuery;
				}if(orgIds.size() == 0 && (StringUtils.isBlank(missingInvoiceDto.getFilterType()) || "undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType()))) {
					query = qry;
				}
				 groupQuery = " GROUP BY CONCAT(sah.submissionId.projectMembers.id,sah.frequencyStartDate,sah.frequencyEndDate)";
			
				Query query1 = session.createQuery(query+" "+searchQuery+" "+groupQuery);
				List<SubmissionApproverHierarchy> tslist = null;
				if(orgIds.size() == 0 && (StringUtils.isNotBlank(missingInvoiceDto.getFilterType()) && !"undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType()))) {
					 tslist =query1.setLong("orgLocationId",orgLocations.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(missingInvoiceDto.getStart()).setMaxResults(missingInvoiceDto.getLength()).list(); 
					response.setData(tslist);
				}if(orgIds.size() > 0 && StringUtils.isBlank(missingInvoiceDto.getFilterType()) || "undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType())){
					 tslist =query1.setLong("orgLocationId",orgLocations.getId()).setParameterList("orgIds", orgIds).setFirstResult(missingInvoiceDto.getStart()).setMaxResults(missingInvoiceDto.getLength()).list(); 
						response.setData(tslist);
				}if(orgIds.size() > 0 && StringUtils.isNotBlank(missingInvoiceDto.getFilterType()) && !"undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType())){
					 tslist =query1.setLong("orgLocationId",orgLocations.getId()).setParameterList("orgIds", orgIds).setParameter("startDate", startDate).setParameter("endDate", endDate).setFirstResult(missingInvoiceDto.getStart()).setMaxResults(missingInvoiceDto.getLength()).list(); 
						response.setData(tslist);
				}if(orgIds.size() == 0 && (StringUtils.isBlank(missingInvoiceDto.getFilterType()) || "undefined".equalsIgnoreCase(missingInvoiceDto.getFilterType()))) {
					 tslist =query1.setLong("orgLocationId",orgLocations.getId()).setFirstResult(missingInvoiceDto.getStart()).setMaxResults(missingInvoiceDto.getLength()).list(); 
					response.setData(tslist);
				}
				
				
				if(tslist != null && !tslist.isEmpty()) {
					for(SubmissionApproverHierarchy sah :tslist) {
						float totalHours = 0f;
	    			    float totalBillableHours = 0f;
	    				float totalNonBillableHours = 0f;
	    				List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyDao.getAllSubmissionApproverHierarchiesByProjectMemberId(sah.getFrequencyStartDate(), sah.getFrequencyEndDate(),sah.getSubmissionId().getProjectMembers().getId(),orgLocations.getId());
					    if(sahList != null && !sahList.isEmpty()) {
					    	 for(SubmissionApproverHierarchy tempSah :sahList) {
					    		 totalHours = CommonUtil.getCombinedHours(tempSah.getTotalHours(), totalHours);
					    		 totalBillableHours = CommonUtil.getCombinedHours(tempSah.getBillableHours(), totalBillableHours);
					    		 totalNonBillableHours = CommonUtil.getCombinedHours(tempSah.getNonBillableHours(), totalNonBillableHours);
					    	}
					    }
					    sah.setTotalHours(totalHours);
					    sah.setBillableHours(totalBillableHours);
					    sah.setNonBillableHours(totalNonBillableHours);
					}
				}
				
				 Query countQ = session.createQuery(queryCount+" "+searchQuery+" "+groupQuery);
				 countQ.setLong("orgLocationId", orgLocations.getId()).setCacheable(true);
				
				List<Long> countList  = null;
				if(countQ != null) {
					countList  = countQ.list();
				}
				int recordsTotal=0;
				if(countList != null && !countList.isEmpty()) {
					recordsTotal  =  countList.size();
				}
				response.setRecordsTotal(recordsTotal);
				
				int recordsFiltered = 0;
				if(tslist != null && !tslist.isEmpty()) {
					recordsFiltered = tslist.size();
				}
				if(searchQuery.toString() != null && !searchQuery.toString().isEmpty()){
					response.setRecordsFiltered(recordsFiltered);
				}
				else {
					response.setRecordsFiltered(recordsTotal);
				}
			}catch (Exception e) {
				e.getStackTrace();
			}
			
			return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> getRecentSowcontractListByAssociateId(Long candidateId, OrgLocations orgLocation,OrgRelations orgRelation, String contractType) {

		List<Contracts> contractsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Contracts contract left join fetch contract.requirementSubmission submission left join fetch submission.requirement where contract.createdDate = (SELECT MAX(con.createdDate) from Contracts con where con.templateSubmissionStatus = 'FINALIZED' and con.candidate.id = :candidateId) and contract.orgLocationId.id = :orgLocationId and "
					+ "contract.orgRelations.id = :orgRelationId  and contract.contractType = :contractType ";
		     contractsList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).setLong("orgRelationId", orgRelation.getId()).setString("contractType", contractType).setCacheable(false).list();
		  if(null != contractsList){
			  return contractsList;
		  }
		  return contractsList;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return contractsList;
		
		 
	
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<Invoice> getPaidVendorslist(PaidBillDTO paidBillDTO, OrgLocations orgLocations)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		List<Long> orgIds = new ArrayList<>();
		if(!"null".equalsIgnoreCase(paidBillDTO.getOrgId()) && paidBillDTO.getOrgId() != null) {
			String[] ids = paidBillDTO.getOrgId().split(",");
			for(String id : ids) {
				orgIds.add(Long.parseLong(id));
			}
		}
		
		if (paidBillDTO.getBillType().equalsIgnoreCase("Payables")) {
			subQuery = " and invoice.invoiceType = 'Payables' ";
		}
		else {
			subQuery = " and invoice.invoiceType = 'Receivable' ";
		}
		List<Invoice> invoiceList = null;
		ResponseList<Invoice> response = new ResponseList<>();
		
		String inQuery = "";
		if(orgIds.size() >0) {
			inQuery = "and invoice.orgRelationID.id IN (:orgIds)";
		}
		
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		String dateQuery = "";
		if(StringUtils.isNotBlank(paidBillDTO.getFilterType()) && !"undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			Date[] dates = getFilterDates(paidBillDTO,orgLocations);
			startDate = new java.sql.Date(dates[0].getTime());
			endDate = new java.sql.Date(dates[1].getTime());
			dateQuery = " and invoice.invoiceDate >= :startDate and invoice.invoiceDate <= :endDate";
		}
		String query = "from Invoice invoice  left join fetch  invoice.orgRelationID left join fetch invoice.projectMemberId pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user "
		 		+ "left join fetch invoice.expensesId left join fetch invoice.projectID where  invoice.orgLocation.id = :orgLocationId and invoice.status = '1' and (invoice.paymentStatus = 'Paid' OR invoice.paymentStatus = 'Partial Paid') "+subQuery;// and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		String countQuery = "";
		if(paidBillDTO.getBillType().equalsIgnoreCase(Constants.RECEIVABLE)){
			countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='Receivable' and invoice.status = '1'"; // and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}else{
		    countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='Payables' and invoice.status = '1'"; // and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}
		
		StringBuilder query1 = new StringBuilder("");
				if(paidBillDTO.getSearchValue() !=null && !"".equalsIgnoreCase(paidBillDTO.getSearchValue())) {
				query1.append(" and (invoice.responderLocationId.orgLocationName like '%" + paidBillDTO.getSearchValue() + "%' )");
			}
		
		
		String qry = "";
		if(orgIds.size() >0) {
			qry = query + inQuery;
		}if(StringUtils.isNotBlank(paidBillDTO.getFilterType()) && !"undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			qry = query + dateQuery;
		}if(orgIds.size() >0 && StringUtils.isNotBlank(paidBillDTO.getFilterType()) && !"undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			qry = query + inQuery + dateQuery;
		}if(orgIds.size() == 0 && StringUtils.isBlank(paidBillDTO.getFilterType()) || "undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			qry = query;
		}
		Query queryObj = session.createQuery(qry+query1.toString());
		if (paidBillDTO != null && paidBillDTO.getLength() > 0) {
			queryObj.setFirstResult(paidBillDTO.getStart());
			queryObj.setMaxResults(paidBillDTO.getLength());
//			queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}
		if(orgIds.size() > 0 && (StringUtils.isBlank(paidBillDTO.getFilterType()) || "undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) ) {
			invoiceList = queryObj.setLong("orgLocationId", orgLocations.getId()).setParameterList("orgIds", orgIds).list();
		}if(StringUtils.isNotBlank(paidBillDTO.getFilterType()) && !"undefined".equalsIgnoreCase(paidBillDTO.getFilterType()) && orgIds.size() == 0) {
			invoiceList = queryObj.setLong("orgLocationId", orgLocations.getId()).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		}if(orgIds.size() >0 && StringUtils.isNotBlank(paidBillDTO.getFilterType()) && !"undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			invoiceList = queryObj.setLong("orgLocationId", orgLocations.getId()).setParameterList("orgIds", orgIds).setParameter("startDate", startDate).setParameter("endDate", endDate).list();
		}if(orgIds.size() == 0 && StringUtils.isBlank(paidBillDTO.getFilterType()) || "undefined".equalsIgnoreCase(paidBillDTO.getFilterType())) {
			invoiceList = queryObj.setLong("orgLocationId", orgLocations.getId()).list();
		}
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocations.getId()).list(); // .setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (paidBillDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocations.getId()).list(); //.setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} 
			response.setData(invoiceList);
			return response;
		
		
	
	}

	/**
	 * Get filter start date and end date
	 * @param paidBillDTO
	 * @param orgLocations
	 * @return Date[]
	 */
	private Date[] getFilterDates(PaidBillDTO paidBillDTO, OrgLocations orgLocations)
	{
		try {
			if ("last_7days".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.last7Days(orgLocations);
			}
			else if ("last_15days".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.last15DaysDates(orgLocations);
			}
			else if ("1month".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.last1MonthDates(orgLocations);
			}
			else if ("3months".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.last3MonthDates(orgLocations);
			}
			else if ("last_years".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.lastYearDates();
			}
			else if ("custom".equalsIgnoreCase(paidBillDTO.getFilterType())) {
				return reportsUtil.getCustomDates(paidBillDTO.getFromDate(), paidBillDTO.getToDate(),orgLocations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<Invoice> getPaidBillDetailReport(PaidBillDTO paidBillDTO, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoiceList = null;
		ResponseList<Invoice> response = new ResponseList<>();
		String query = "from Invoice invoice where invoice.orgRelationID.id = :orgRelationId and invoice.invoiceType = :billType";
		String countQuery = "from Invoice invoice where invoice.orgRelationID.id = :orgRelationId and invoice.invoiceType = :billType";


		StringBuilder searchQuery = new StringBuilder("");
		 if(paidBillDTO.getSearchValue() !=null && !"".equalsIgnoreCase(paidBillDTO.getSearchValue())) {
						searchQuery.append(" and (invoice.projectID.projectName like '%" + paidBillDTO.getSearchValue() + "%' )");
//						.append("OR invoice.orgLocation.orgLocationName like '%" + paidBillDTO.getSearchValue() + "%' ")
//						.append("OR invoice.total like '%" + paidBillDTO.getSearchValue() + "%' ")
//						.append("OR invoice.invoiceId like '%" + paidBillDTO.getSearchValue() + "%' ")
//						.append("OR invoice.dueAmount like '%" + paidBillDTO.getSearchValue() + "%' )");
//					}
			}
		
		Query queryObj = session.createQuery(query + searchQuery.toString());
		if (paidBillDTO != null && paidBillDTO.getLength() > 0) {
			queryObj.setFirstResult(paidBillDTO.getStart());
			queryObj.setMaxResults(paidBillDTO.getLength());
		}
		
		invoiceList = queryObj.setLong("orgRelationId", paidBillDTO.getOrgRelationId()).setString("billType", paidBillDTO.getBillType()).list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) "+ countQuery).setLong("orgRelationId", paidBillDTO.getOrgRelationId()).setString("billType", paidBillDTO.getBillType()).list();
		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (paidBillDTO != null) {
			List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery+ searchQuery.toString()).setLong("orgRelationId", paidBillDTO.getOrgRelationId()).setString("billType", paidBillDTO.getBillType()).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(invoiceList);
		return response;
	}

	@Override
	@Transactional
	public Date getPaymentDateByInvoiceId(Long invoiceId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoicePayment ip where ip.id = (SELECT MAX(id) FROM InvoicePayment invoiocePayment where invoiocePayment.invoiceId =:invoiceId)";
		Query queryObj = session.createQuery(query);
		List<InvoicePayment> invoicePayList = queryObj.setLong("invoiceId", invoiceId).list();
		Date paymentDate = null;
		if (invoicePayList != null && invoicePayList.size() >0) {
			InvoicePayment invoicePayment = invoicePayList.get(0);
			paymentDate = invoicePayment.getPaymentDate();
		}
		return paymentDate;
	}

	@Override
	@Transactional
	public List<Invoice> getInvoicesByOrgRelationId(Long orgRelationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice where invoice.orgRelationID.id = :orgRelationId";
		Query queryObj = session.createQuery(query);
		List<Invoice> invoicePayList = queryObj.setLong("orgRelationId", orgRelationId).list();
		return invoicePayList;
	}

	@Override
	@Transactional
	public List<InvoicePayment> getInvoicePaidAmountByInvoiceIds(List<Long> invoiceIds) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoicePayment ip where ip.invoiceId.id IN (:invoiceIds)";
		Query queryObj = session.createQuery(query);
		List<InvoicePayment> invoicePayList = queryObj.setParameterList("invoiceIds", invoiceIds).list();
		return invoicePayList;
	}

}
