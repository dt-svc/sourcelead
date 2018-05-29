package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;
import com.bis.sourcelead.reportsDTO.ClientReportsDTO;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
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
	ReportsUtil reportsUtil;
	
	@Autowired
	ClientReportDao clientReportDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	JobsPipelineReportService jobsPipelineReportService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	
	@RequestMapping(value = "/perfomanceClientReports")
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
		model.setViewName("reports/perfomanceClientReports");
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
		Map<String, Integer> clientcontractList = clientReportService.getContractsListOFAllClients(orgLocation,user,userRoleName,contractReportsDTO);
		
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
	public InvoiceReportDTO getTopFiveReceivsblesReports(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "dateFrequency", required = false) String dateFrequency,@RequestParam(value = "fromDate", required = false) String fromDate,@RequestParam(value = "toDate", required = false) String toDate,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		return clientReportService.getTopFiveReceivsblesReports(orgLocation,user,type,dateFrequency,fromDate,toDate);
		
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
			freqDates = reportsUtil.getJobFilterDate(frequency,orgLocation);
		}
		List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		if(clientList != null && clientList.size() > 0) {
			for(OrgRelations orgRelation : clientList){
				ClientReportsDTO clientDto = new ClientReportsDTO();
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
					clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
				}else{
					clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
				}
				clientDto.setOrgRelationUUID(orgRelation.getUUID());
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
				
				List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation, user,orgRelation.getId(),userRoleName,contractReportsDTO);
				if(conList != null && conList.size() > 0){
					clientDto.setTotalContracts(conList.size());
				}else{
					clientDto.setTotalContracts(0);
				}
				
				List<Invoice> invoiceList = clientReportService.getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), Constants.RECEIVABLE,null,freqDates);
				if(invoiceList != null && invoiceList.size() > 0) {
					float paidTotalAmount = 0f;
					for (Invoice invoice : invoiceList) {
						if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
								!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
							Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
							paidTotalAmount = paidTotalAmount + paidAmount;
							clientDto.setTotalPaidAmount(paidTotalAmount);
						}
					}
					if(clientDto.getTotalPaidAmount() != 0)
					clientDtoList.add(clientDto);
				}
				
				
			}
		}
		
		clientDtoList.sort(new Comparator<ClientReportsDTO>() {
			@Override
			public int compare(ClientReportsDTO o1, ClientReportsDTO o2) {
				if(o1 != null && (Float)o1.getTotalPaidAmount() != null) {
					return ((Float)o2.getTotalPaidAmount()).compareTo((Float)o1.getTotalPaidAmount());
				}
				return 0;
			}
		});
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
			freqDates = reportsUtil.getJobFilterDate(frequency,orgLocation);
		}
		List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), loginUser.getId());
		if(clientList != null && clientList.size() > 0) {
			for(OrgRelations orgRelation : clientList){
				ClientReportsDTO clientDto = new ClientReportsDTO();
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
					clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
				}else{
					clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
				}
				clientDto.setOrgRelationUUID(orgRelation.getUUID());
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
					clientDto.setOrgSpocUUID(spoc.getUUID());
					
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
					List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocation, loginUser,orgRelation.getId(),userRoleName,contractReportsDTO);
					if(conList != null && conList.size() > 0){
						clientDto.setTotalContracts(conList.size());
					}else{
						clientDto.setTotalContracts(0);
					}
					
					List<Invoice> invoiceList = clientReportService.getClientBasedInvoiceLists(orgRelation.getId(), orgLocation.getId(), Constants.RECEIVABLE,spoc.getClientUser().getId(),freqDates);
					float paidTotalAmount = 0f;
					for (Invoice invoice : invoiceList) {
						if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
								!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
							Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
							paidTotalAmount = paidTotalAmount + paidAmount;
							clientDto.setTotalPaidAmount(paidTotalAmount);
						}
					}
					if(clientDto.getTotalPaidAmount() != 0)
					clientDtoList.add(clientDto);
				}
				
			}
		}
		
		clientDtoList.sort(new Comparator<ClientReportsDTO>() {
			@Override
			public int compare(ClientReportsDTO o1, ClientReportsDTO o2) {
				if(o1 != null && (Float)o1.getTotalPaidAmount() != null) {
					return ((Float)o2.getTotalPaidAmount()).compareTo((Float)o1.getTotalPaidAmount());
				}
				return 0;
			}
		});
		return clientDtoList;
	}
	
	@RequestMapping(value = "/getClientreportSummary", method = RequestMethod.POST)
	public ClientReportsDTO getClientreportSummary(@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,@RequestParam(value = "orgSpocUUID", required = false) String orgSpocUUID,@RequestParam(value = "invoiceType", required = false) String invoiceType,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		Float totalPaidAmount = 0f;
		Float totalDraftAmount = 0f;
		Float dueAfterInvoiceAmount = 0f;
		Float openInvoiceTotal = 0f;
		int totalPaidCount = 0;
		
		int draftCount = 0;
		int waitingCount = 0;
		int signCount = 0;
		int counterSignCount = 0;
		int finalizedCount = 0;
		int expiredCount = 0;
		int rejectedCount = 0;
		
		int openCount = 0;
		int onholdCount = 0;
		int cancelledCount = 0;
		int filledClosedCount = 0;
		int unfilledClosedCount = 0;
		int closedCount = 0;
		int totalPostedCount = 0;
		
		Date todayDate = new Date();
		ClientReportsDTO clientReportDTO = new ClientReportsDTO();
		OrgRelations orgRel = null;
		if(orgRelationUUID != null && orgRelationUUID !="") {
			orgRel = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
			if(orgRel.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				clientReportDTO.setClientName(orgRel.getResponderLocationId().getOrgLocationName());
			}
			if(orgRel.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				clientReportDTO.setClientName(orgRel.getLocationId().getOrgLocationName());
			}
		}
		OrgSpoc spoc = null;
		if(orgSpocUUID != null && orgSpocUUID != "") {
			 spoc = orgSpocService.getOrgSpocByUUID(orgSpocUUID);
			StringBuilder spocName = new StringBuilder();
				if(spoc.getClientUser().getLastName() != null) {
					clientReportDTO.setClientContactName(spocName.append(spoc.getClientUser().getFirstName()).append(" ").append(spoc.getClientUser().getLastName()).toString());
				}else {
					clientReportDTO.setClientContactName(spoc.getClientUser().getFirstName());
				}
		}
		
		List<Invoice> clientInvoiceList = null;
		if(orgRel != null) {
			 clientInvoiceList = clientReportService.getClientBasedInvoiceLists(orgRel.getId(), orgLocation.getId(), invoiceType,null,null);
		}else if(spoc != null) {
			 clientInvoiceList = clientReportService.getClientBasedInvoiceLists(spoc.getOrgRelations().getId(), orgLocation.getId(), invoiceType,spoc.getClientUser().getId(),null);
		}
		
		if(clientInvoiceList != null && clientInvoiceList.size() >0) {
			for(Invoice invoice : clientInvoiceList) {
				if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) && (Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus())) && 
						!Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus())){
					Float paidAmount = Float.parseFloat(invoice.getTotal()) - Float.parseFloat(invoice.getDueAmount());
					totalPaidAmount += paidAmount;
					if(Constants.INVOICE_STATUS_PAID.equalsIgnoreCase(invoice.getPaymentStatus())) {
						totalPaidCount += 1;
					}
				}
				
				if(Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) || Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus())){
					totalDraftAmount += Float.parseFloat(invoice.getDueAmount()) ;
				}
				
				
				if(!Constants.DRAFT.equalsIgnoreCase(invoice.getInvoiceStatus()) && !Constants.REJECTED.equalsIgnoreCase(invoice.getInvoiceStatus()) &&
						(Constants.INVOICE_STATUS_UNPAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.INVOICE_STATUS_PARTIAL_PAID.equalsIgnoreCase(invoice.getPaymentStatus()) || Constants.OVER_DUE.equalsIgnoreCase(invoice.getPaymentStatus()))){
					
					String dueDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),invoice.getDueDate());
					Date invoiceDueDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),dueDate);
					
					Float dueAmount = Float.parseFloat(invoice.getDueAmount());
					
					if(dueAmount > 0){
					
						if(todayDate.after(invoiceDueDate)){
							dueAfterInvoiceAmount += Float.parseFloat(invoice.getDueAmount());
						}else{
							openInvoiceTotal += Float.parseFloat(invoice.getDueAmount());
						}
					}
					
					
				}
			}
		}
		
		ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
		List<Contracts> clientContractsList = null;
		if(orgRelationUUID != null && orgRelationUUID !="") {
			 clientContractsList = clientReportService.getClientBasedContractsLists(orgLocation,user,userRoleName,orgRel.getId(),contractReportsDTO);
		}else if(orgSpocUUID != null && orgSpocUUID != "") {
			contractReportsDTO.setContactUser(spoc.getClientUser().getId());
			 clientContractsList = clientReportService.getClientBasedContractsLists(orgLocation,user,userRoleName,spoc.getOrgRelations().getId(),contractReportsDTO);
		}
		
		if(clientContractsList != null && clientContractsList.size() > 0) {
			for (Contracts contracts : clientContractsList) {
				
				if(Constants.DRAFT.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					draftCount += 1;
				}else if(Constants.WAITING_FOR_APPROVER.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					waitingCount += 1;
				}else if(Constants.WAITING_TO_BE_SIGNED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) || Constants.PENDING.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					signCount += 1;
				}else if(Constants.WAITING_TO_BE_VERIFY.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					counterSignCount += 1;
				}else if(Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					finalizedCount =+ 1;
				}else if(Constants.EXPIRED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					expiredCount += 1;
				}else if(Constants.REJECTED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
					rejectedCount += 1;
				}
			}
		}
		
		List<Requirement> reqList = null;
		if(orgRelationUUID != null && orgRelationUUID !="") {
			 reqList = clientReportService.getClientJobsByOrgRelationId(orgRel.getId(),orgLocation.getId(),user.getId(),null,null);
		}else if(orgSpocUUID != null && orgSpocUUID != "") {
			reqList = clientReportService.getClientJobsByOrgRelationId(spoc.getOrgRelations().getId(),orgLocation.getId(),user.getId(),null,spoc.getClientUser().getId());
		}
		if(reqList != null && reqList.size() > 0) {
			int submissionCount = 0;
			int shortListCount  = 0;
			int submitToClientCount  = 0;
			int interviewedCount  = 0;
			int offermadeCount  = 0;
			int offeracceptedCount  = 0;
			int rejectCount  = 0;
			for(Requirement req : reqList) {
				Map<String, Integer> reqSubmissionCount = clientReportService.getSubmissionListByRequirementId(req.getId(), orgLocation.getId(), user.getId(), null);
				
				submissionCount = submissionCount + reqSubmissionCount.get("submissionCount");
				shortListCount = shortListCount + reqSubmissionCount.get("shortListCount");
				submitToClientCount = submitToClientCount + reqSubmissionCount.get("submitToClientCount");
				interviewedCount = interviewedCount + reqSubmissionCount.get("interviewedCount");
				offermadeCount = offermadeCount + reqSubmissionCount.get("offermadeCount");
				offeracceptedCount = offeracceptedCount + reqSubmissionCount.get("offeracceptedCount");
				rejectCount = rejectCount + reqSubmissionCount.get("rejectedCount");
				
				if("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					openCount += 1;
				}else if("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					onholdCount +=1;
				}else if("cancel".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					cancelledCount +=1;
				}else if("filledClosed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					filledClosedCount +=1;
				}else if("unFilledClosed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					unfilledClosedCount +=1;
				}else if("closed".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())) {
					closedCount += 1;
				}
				if(Constants.YES.equalsIgnoreCase(req.getIsClientJob())) {
					totalPostedCount += 1;
				}
				}
				clientReportDTO.setApplicantsCount(submissionCount);
				clientReportDTO.setShortlistedCount(shortListCount);
				clientReportDTO.setSubmitToClientCount(submitToClientCount);
				clientReportDTO.setInterviewsCount(interviewedCount);
				clientReportDTO.setOfferedCount(offermadeCount);
				clientReportDTO.setOfferacceptedCount(offeracceptedCount);
				clientReportDTO.setRejectedCount(rejectCount);
			}

		clientReportDTO.setTotalPaidAmount(totalPaidAmount);
		clientReportDTO.setTotalPaidbillCount(totalPaidCount);
		clientReportDTO.setTotalDraftAmount(totalDraftAmount);
		clientReportDTO.setTotalOverdueAmount(dueAfterInvoiceAmount);
		clientReportDTO.setTotalSentAmount(openInvoiceTotal);
		clientReportDTO.setTotalReceivableAmount(totalDraftAmount+totalPaidAmount+dueAfterInvoiceAmount+openInvoiceTotal);
		
		clientReportDTO.setDraftCount(draftCount);
		clientReportDTO.setWaitingCount(waitingCount);
		clientReportDTO.setSignCount(signCount);
		clientReportDTO.setCounterSignCount(counterSignCount);
		clientReportDTO.setFinalizedCount(finalizedCount);
		clientReportDTO.setExpiredCount(expiredCount);
		clientReportDTO.setRejectedCount(rejectedCount);
		
		clientReportDTO.setOpenCount(openCount);
		clientReportDTO.setOnholdCount(onholdCount);
		clientReportDTO.setCancelledCount(cancelledCount);
		clientReportDTO.setFilledClosedCount(filledClosedCount);
		clientReportDTO.setUnfilledClosedCount(unfilledClosedCount);
		clientReportDTO.setClosedCount(closedCount);
		clientReportDTO.setTotalPostedCount(totalPostedCount);
		
		return clientReportDTO;
	}
}
