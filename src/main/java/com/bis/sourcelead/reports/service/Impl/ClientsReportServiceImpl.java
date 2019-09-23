package com.bis.sourcelead.reports.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.reportsDTO.ClientReportsDTO;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ClientsReportServiceImpl implements ClientReportService{

	@Autowired
	ClientReportDao clientReportDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Override
	public Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		return clientReportDao.getAllClientsCountByOrgLocationIdAndUserId(locationId, userId);
	}

	@Override
	public Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		Map<String, Integer> spocCount = new HashMap<>();
		List<OrgRelations> orgRelationsList = getAllClientsByOrgLocationIdAndUserId(locationId, userId);
		int totalactiveCount = 0;
		int totalInactiveCount = 0;
		if(orgRelationsList != null){
			for(OrgRelations rel : orgRelationsList){
				Map<String, Integer> contactCount = clientReportDao.getAllClientsSpocCountByOrgLocationIdAndUserId(rel.getId(),locationId, userId);
				int spocActiveCount = contactCount.get("spocActiveCount");
				int spocInactiveCount = contactCount.get("spocInactiveCount");
				totalactiveCount += spocActiveCount;
				totalInactiveCount += spocInactiveCount;
			}
		}
		spocCount.put("totalactiveCount", totalactiveCount);
		spocCount.put("totalInactiveCount", totalInactiveCount);
		spocCount.put("totalSpocCount", totalactiveCount+totalInactiveCount);
		return spocCount;
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		return clientReportDao.getAllClientsByOrgLocationIdAndUserId(locationId, userId);
	}

	@Override
	public Map<String, Integer> getContractsListOFAllClients(OrgLocations orgLocation, User user,String userRoleName,ContractReportsDTO contractReportsDTO)  throws Exception
	{
		Map<String, Integer> conCount = new HashMap<>();
		List<OrgRelations> orgRelationsList = getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		List<Contracts> contractList = new ArrayList<>();
		int totalMsaCount = 0;
		int totalSowCount = 0;
		int totalNdaCount = 0;
		int totalNsaCount = 0;
		if(orgRelationsList != null) {
			for(OrgRelations orgRelation : orgRelationsList){
				List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation, user,orgRelation.getId(),userRoleName,contractReportsDTO);
//				contractList.add(conList.size());
				if(conList != null && conList.size() > 0){
					if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
						conCount.put(orgRelation.getResponderLocationId().getOrgLocationName(), conList.size());
					}else{
						conCount.put(orgRelation.getLocationId().getOrgLocationName(), conList.size());
					}
					for(Contracts con : conList){
						if(con.getContractType().contains("MSA")){
							totalMsaCount += 1;	
						}else if(con.getContractType().contains("SOW")){
							totalSowCount += 1;
						}else if(con.getContractType().contains("NDA")){
							totalNdaCount += 1;
						}else if(con.getContractType().contains("NSA")){
							totalNsaCount += 1;
						}
					}
					conCount.put("MsaCount",totalMsaCount);
					conCount.put("SowCount",totalSowCount);
					conCount.put("NdaCount",totalNdaCount);
					conCount.put("NsaCount",totalNsaCount);
				}
			}
		}
		return conCount;
	}

	@Override
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId, String invoiceType,Long contactUserId, Date[] freqDates) {
		return clientReportDao.getClientBasedInvoiceLists(orgRelationId,orgLocId,invoiceType,contactUserId,freqDates);
	}

	@Override
	public InvoiceReportDTO getTopFiveReceivsblesReports(OrgLocations orgLocation, User user,String type,String dateFrequency,String fromDate, String toDate) throws Exception {

		Map<String,Float> amountList = new LinkedHashMap<>();
		List<Entry<String, Float>> newList = null;
		List<OrgRelations> clientsList = getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(),user.getId());
		
		InvoiceReportDTO invoiceDto = new InvoiceReportDTO();
		Date todayDate = new Date();
		
		Float draftTotalAmount = 0f;
		Float dueAfterInvoiceAmount = 0f;
		Float openInvoiceTotal = 0f;
		Float allPaidTotalAmount = 0f;
		
		
		Date[] freqDates = new Date[2];
		if("Custom".equalsIgnoreCase(dateFrequency)){
			freqDates[0] = commonUtil.getDateFormat(fromDate);
			freqDates[1] = commonUtil.getDateFormat(toDate);
		}else{
			freqDates = reportsUtil.getJobFilterDate(dateFrequency,orgLocation);
		}
		
		if(clientsList != null && clientsList.size() > 0) {
		for(OrgRelations orgRelation : clientsList) {
			Float paidTotalAmount = 0f;
			List<Invoice> invoiceList = getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), type,null,freqDates);
			String clientName = null;
			if(invoiceList != null && invoiceList.size() > 0) {
				for (Invoice invoice : invoiceList) {
					
					if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
							!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
						Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
						paidTotalAmount = paidTotalAmount + paidAmount;
						invoiceDto.setPaidInvoiceTotal(paidTotalAmount);
						if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
							clientName = invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName();
						}
						if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							clientName = invoice.getOrgRelationID().getLocationId().getOrgLocationName();
						}
						
						allPaidTotalAmount += paidAmount;
						invoiceDto.setAllPaidInvoiceTotal(allPaidTotalAmount);
						
					}
					
					if(Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) || Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())){
						draftTotalAmount += Float.parseFloat(invoice.getDueAmount()) ;
						invoiceDto.setDraftInvoiceTotal(draftTotalAmount);
					}
					
					
					if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) &&
							(Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))){
						
						String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());
						Date invoiceDueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),dueDate);
						
						Float dueAmount = Float.parseFloat(invoice.getDueAmount());
						
						if(dueAmount > 0){
						
							if(todayDate.after(invoiceDueDate)){
								dueAfterInvoiceAmount = dueAfterInvoiceAmount + Float.parseFloat(invoice.getDueAmount());
								invoiceDto.setOverDueInvoicetotal(dueAfterInvoiceAmount);
							}else{
								openInvoiceTotal = openInvoiceTotal + Float.parseFloat(invoice.getDueAmount());
								invoiceDto.setOpenInvoiceTotal(openInvoiceTotal);
							}
						}
						
						
					}
					
				}
				
				if(clientName != null)
				amountList.put(clientName,paidTotalAmount);
			}
		}
		
		if(amountList != null && amountList.size() > 0) {
			Set<Entry<String, Float>> set = amountList.entrySet();
	        List<Entry<String, Float>> list = new ArrayList<Entry<String, Float>>(set);
	        Collections.sort(list, new Comparator<Map.Entry<String, Float>>()
	        {
	            public int compare( Map.Entry<String, Float> o1, Map.Entry<String, Float> o2 )
	            {
	                return (o2.getValue()).compareTo( o1.getValue() );
	            }
	        } );
	        
	        Map<String,Float> newAmountList =  new LinkedHashMap<>();
	        Map<String,Float> othersAmountList =  new LinkedHashMap<>();
	        if(list.size() > 5) {
	        	 newList =   list.subList(0, 5);
	        	    List<Entry<String, Float>> othersList =   list.subList(5,list.size());
	    	        Float otherAmt = 0f;
	     	        for (Entry<String, Float> entry : othersList) {
	     	        	Float calcOtherAmt = entry.getValue();
	     	        	otherAmt = otherAmt + calcOtherAmt;
	     	        	othersAmountList.put("Others", otherAmt);
	     	        	invoiceDto.setOthersAmountList(othersAmountList);
	     			}
	        }else {
	        	 newList =   list;
	        }
	        
	        
	        for(Map.Entry<String, Float> entry:newList){
//	            System.out.println(entry.getKey()+" ==== "+entry.getValue());
	            newAmountList.put(entry.getKey(), entry.getValue());
	            invoiceDto.setNewAmountList(newAmountList);
	        }
	        
		}
	}
	return invoiceDto;
	}
	
	@Override
	public List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationid, Long locationId, Long userId,Date[] freqDates)
	{
		return clientReportDao.getClientSpocListByOrgRelationid(orgRelationid, locationId, userId,freqDates);
	}

	@Override
	public List<Requirement> getClientJobsByOrgRelationId(Long orgRelationid, Long locationId, Long userId, Date[] freqDates,Long spocId)
	{
		return clientReportDao.getClientJobsByOrgRelationId(orgRelationid,locationId,userId,freqDates,spocId);
	}

	@Override
	public Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long orgLocationId, Long userId, Date[] freqDates)
	{
		return clientReportDao.getSubmissionListByRequirementId(requirementId,orgLocationId,userId,freqDates);
	}

	@Override
	public List<Contracts> getClientBasedContractsLists(OrgLocations orgLocation, User user, String userRoleName,
			Long orgRelationId, ContractReportsDTO contractReportsDTO) throws Exception  {
		return clientReportDao.getContractsListOFAllClients(orgLocation, user, orgRelationId, userRoleName, contractReportsDTO);
	}

}
