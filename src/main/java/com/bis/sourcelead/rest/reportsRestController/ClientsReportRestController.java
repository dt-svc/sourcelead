package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ContractsDTO;
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
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ClientsReportRestController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	ClientReportService clientReportService;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	ClientReportDao clientReportDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@RequestMapping(value = "/clientReports")
	public ModelAndView clientReports(HttpServletRequest request) throws Exception
	{
		OrgLocations location = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		Map<String, Integer> activeAndInactiveCount = clientReportService.getAllClientsCountByOrgLocationIdAndUserId(location.getId(), user.getId());
		int activeClientsCount = activeAndInactiveCount.get("activeClient");
		int inActiveClientsCount = activeAndInactiveCount.get("inActiveClient");
		int totalClientsCount = activeClientsCount + inActiveClientsCount;
		model.addObject("activeClientsCount", activeClientsCount);
		model.addObject("inActiveClientsCount", inActiveClientsCount);
		model.addObject("totalClientsCount", totalClientsCount);
		Map<String, Integer> totalSpocCount = clientReportService.getAllClientsSpocCountByOrgLocationIdAndUserId(location.getId(), user.getId());
		model.addObject("activeSpocCount", totalSpocCount.get("totalactiveCount"));
		model.addObject("inActiveSpocCount", totalSpocCount.get("totalInactiveCount"));
		model.addObject("totalSpocCount", totalSpocCount.get("totalSpocCount"));
		model.setViewName("reports/clientReports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTopFiveConracts", method = RequestMethod.GET)
	public List<ContractReportsDTO> getTopTenFiveConracts(ContractReportsDTO contractReportsDTO, HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<ContractReportsDTO> contractList = new ArrayList<>();
		Map<String, Integer> clientcontractList = clientReportService.getContractsListOFAllClients(orgLocation.getId(),user,userRoleName,contractReportsDTO);
		
		for(Map.Entry<String,Integer> entry : clientcontractList.entrySet()){
			ContractReportsDTO conDto = new ContractReportsDTO();
			if(!"MsaCount".equalsIgnoreCase(entry.getKey()) && !"SowCount".equalsIgnoreCase(entry.getKey())
					&& !"NdaCount".equalsIgnoreCase(entry.getKey()) && !"NsaCount".equalsIgnoreCase(entry.getKey())){
				conDto.setClientName(entry.getKey());
				conDto.setContractsCount(entry.getValue());
			}
			contractList.add(conDto);
		}
		
		Collections.sort(contractList, new Comparator() {
			@Override
			public int compare(Object obj1, Object obj2)
			{
				ContractReportsDTO condto1 = (ContractReportsDTO) obj1;
				ContractReportsDTO condto2 = (ContractReportsDTO) obj2;
				if(condto1.getContractsCount() > condto2.getContractsCount()) return -1;
				if(condto1.getContractsCount() < condto2.getContractsCount()) return 1;
				return 0;
			}
		});
		
		if(clientcontractList != null){
			ContractReportsDTO conDto = new ContractReportsDTO();
			if(clientcontractList.get("MsaCount") != null)
				conDto.setMsaCount(clientcontractList.get("MsaCount"));
			else
				conDto.setMsaCount(0);
			if(clientcontractList.get("SowCount") != null)
				conDto.setSowCount(clientcontractList.get("SowCount"));
			else
				conDto.setSowCount(0);
			if(clientcontractList.get("NdaCount") != null)
				conDto.setNdaCount(0);
			else
				conDto.setNdaCount(0);
			if(clientcontractList.get("NsaCount") != null)
				conDto.setNsaCount(0);
			else
				conDto.setNsaCount(0);	
			contractList.add(conDto);
		}
		
		return contractList;
	}
	
	@RequestMapping(value = "/getTopFiveReceivablesReports", method = RequestMethod.GET)
	public Map<String,Float> getTopFiveReceivsblesReports(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "dateFrequency", required = false) String dateFrequency,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		Map<String,Float> amountList = new LinkedHashMap<>();
		Map<String,Float> newAmountList = null;
		List<OrgRelations> clientsList = clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(),user.getId());
		
		if(clientsList != null && clientsList.size() > 0) {
		for(OrgRelations orgRelation : clientsList) {
			Float paidTotalAmount = 0f;
			List<Invoice> invoiceList = invoiceService.getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), type);
			String clientName = null;
			if(invoiceList != null && invoiceList.size() > 0) {
				for (Invoice invoice : invoiceList) {
					if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
							!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
						Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
						paidTotalAmount = paidTotalAmount + paidAmount;
						if(invoice.getOrgRelationID().getLocationId().getId().longValue() == orgLocation.getId().longValue()){
							clientName = invoice.getOrgRelationID().getResponderLocationId().getOrgLocationName();
						}
						if(invoice.getOrgRelationID().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							clientName =invoice.getOrgRelationID().getLocationId().getOrgLocationName();
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
	        
	        newAmountList =  new LinkedHashMap<>();
	        List<Entry<String, Float>> newList =   list.subList(0, 5);
	        for(Map.Entry<String, Float> entry:newList){
//	            System.out.println(entry.getKey()+" ==== "+entry.getValue());
	            newAmountList.put(entry.getKey(), entry.getValue());
	        }
	        
	        List<Entry<String, Float>> othersList =   list.subList(5,list.size());
	        Float otherAmt = 0f;
	        for (Entry<String, Float> entry : othersList) {
	        	Float calcOtherAmt = entry.getValue();
	        	otherAmt = otherAmt + calcOtherAmt;
	        	newAmountList.put("Others", otherAmt);
			}
		}
	}
	return newAmountList;
	}
	
	@RequestMapping(value = "/getTopTenClientReports", method = RequestMethod.GET)
	public List<ClientReportsDTO> getTopTenClientReports(@RequestParam(value = "frequency", required = false) String frequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<ClientReportsDTO> clientDtoList = new ArrayList<>();
		Date[] freqDates = new Date[2];
		if("Custom".equalsIgnoreCase(frequency)){
			freqDates[0] = commonUtil.getDateFormat(fromDate);
			freqDates[1] = commonUtil.getDateFormat(toDate);
		}else{
			freqDates = reportsUtil.getJobFilterDate(frequency);
		}
		List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		for(OrgRelations orgRelation : clientList){
			ClientReportsDTO clientDto = new ClientReportsDTO();
			if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
				clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
			}else{
				clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
			}
			List<OrgSpoc> spocList = clientReportService.getClientSpocListByOrgRelationid(orgRelation.getId(),orgLocation.getId(),user.getId(),freqDates);
			if(spocList != null && spocList.size() > 0)
				clientDto.setNoOfContacts(spocList.size());
			else
				clientDto.setNoOfContacts(0);
			
			List<Requirement> reqList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(),orgLocation.getId(),user.getId(),freqDates,null);
			if(reqList != null && reqList.size() > 0)
				clientDto.setReleasedJobs(reqList.size());
			else
				clientDto.setReleasedJobs(0);
			for(Requirement req : reqList){
				Map<String,Integer> subListCount = clientReportService.getSubmissionListByRequirementId(req.getId(),orgLocation.getId(),user.getId(),freqDates);
				clientDto.setSubmissionCount(subListCount.get("submissionCount"));
				clientDto.setSubmissionCount(subListCount.get("hiredCount"));
			}
			ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
			contractReportsDTO.setFrequency(frequency);
			if("Custom".equalsIgnoreCase(frequency)){
				contractReportsDTO.setFromDate(commonUtil.dateStrFromDate(freqDates[0]));
				contractReportsDTO.setToDate(commonUtil.dateStrFromDate(freqDates[1]));
			}
			
			List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation.getId(), user,orgRelation.getId(),userRoleName,contractReportsDTO);
			if(conList != null && conList.size() > 0){
				clientDto.setTotalContracts(conList.size());
			}else{
				clientDto.setTotalContracts(0);
			}
			
			clientDtoList.add(clientDto);
		}
		
		return clientDtoList;
	}
	
	@RequestMapping(value = "/getTopTenClientContactReports", method = RequestMethod.GET)
	public List<ClientReportsDTO> getTopTenClientContactReports(@RequestParam(value = "frequency", required = false) String frequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<ClientReportsDTO> clientDtoList = new ArrayList<>();
		Date[] freqDates = new Date[2];
		if("Custom".equalsIgnoreCase(frequency)){
			freqDates[0] = commonUtil.getDateFormat(fromDate);
			freqDates[1] = commonUtil.getDateFormat(toDate);
		}else{
			freqDates = reportsUtil.getJobFilterDate(frequency);
		}
		List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), loginUser.getId());
		for(OrgRelations orgRelation : clientList){
			ClientReportsDTO clientDto = new ClientReportsDTO();
			if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
				clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
			}else{
				clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
			}
			clientDto.setOrgRelationId(orgRelation.getId());
			List<OrgSpoc> spocList = clientReportService.getClientSpocListByOrgRelationid(orgRelation.getId(),orgLocation.getId(),loginUser.getId(),freqDates);
			if(spocList != null && spocList.size() > 0)
				clientDto.setNoOfContacts(spocList.size());
			else
				clientDto.setNoOfContacts(0);
			
			for(OrgSpoc spoc : spocList){
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(spoc.getClientUser().getFirstName()) && StringUtils.isNotBlank(spoc.getClientUser().getLastName()))
					sb.append(spoc.getClientUser().getFirstName()).append(" ").append(spoc.getClientUser().getLastName());
				else
					sb.append(spoc.getClientUser().getFirstName());
				clientDto.setClientContactName(sb.toString());
				clientDto.setOrgSpocId(spoc.getId());
				
				List<Requirement> reqList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(),orgLocation.getId(),loginUser.getId(),freqDates,spoc.getId());
				if(reqList != null && reqList.size() > 0)
					clientDto.setReleasedJobs(reqList.size());
				else
					clientDto.setReleasedJobs(0);
				
				for(Requirement req : reqList){
					Map<String,Integer> subListCount = clientReportService.getSubmissionListByRequirementId(req.getId(),orgLocation.getId(),loginUser.getId(),freqDates);
					clientDto.setSubmissionCount(subListCount.get("submissionCount"));
					clientDto.setSubmissionCount(subListCount.get("hiredCount"));
				}
				ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
				contractReportsDTO.setFrequency(frequency);
				if("Custom".equalsIgnoreCase(frequency)){
					contractReportsDTO.setFromDate(commonUtil.dateStrFromDate(freqDates[0]));
					contractReportsDTO.setToDate(commonUtil.dateStrFromDate(freqDates[1]));
				}
				contractReportsDTO.setContactUser(spoc.getClientUser().getId());
				List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation.getId(), loginUser,orgRelation.getId(),userRoleName,contractReportsDTO);
				if(conList != null && conList.size() > 0){
					clientDto.setTotalContracts(conList.size());
				}else{
					clientDto.setTotalContracts(0);
				}
				
			}
			
			clientDtoList.add(clientDto);
		}
		
		return clientDtoList;
	}
	
}
