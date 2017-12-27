package com.bis.sourcelead.rest.reportsRestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
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
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;
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
		
		ContractReportsDTO conDto = new ContractReportsDTO();
		conDto.setMsaCount(clientcontractList.get("MsaCount"));
		conDto.setSowCount(clientcontractList.get("SowCount"));
		conDto.setNdaCount(clientcontractList.get("NdaCount"));
		conDto.setNsaCount(clientcontractList.get("NsaCount"));
		contractList.add(conDto);
		
		return contractList;
	}
	
	@RequestMapping(value = "/getTopFiveReceivablesReports", method = RequestMethod.GET)
	public InvoiceReportDTO getTopFiveReceivsblesReports(@RequestParam(value = "type", required = false) String type,@RequestParam(value = "dateFrequency", required = false) String dateFrequency,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		return clientReportService.getTopFiveReceivsblesReports(orgLocation,user,type);
		
	}
	
	@RequestMapping(value = "/getTopTenClientReports", method = RequestMethod.GET)
	public List<ClientReportsDTO> getTopTenClientReports(@RequestParam(value = "frequency", required = false) String frequency,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		List<ClientReportsDTO> clientDtoList = new ArrayList<>();
		List<OrgRelations> clientList =  clientReportService.getAllClientsByOrgLocationIdAndUserId(orgLocation.getId(), user.getId());
		for(OrgRelations orgRelation : clientList){
			ClientReportsDTO clientDto = new ClientReportsDTO();
			if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocation.getId().longValue()){
				clientDto.setClientName(orgRelation.getResponderLocationId().getOrgLocationName());
			}else{
				clientDto.setClientName(orgRelation.getLocationId().getOrgLocationName());
			}
			List<OrgSpoc> spocList = clientReportService.getClientSpocListByOrgRelationid(orgRelation.getId(),orgLocation.getId(),user.getId());
			if(spocList != null && spocList.size() > 0)
				clientDto.setNoOfContacts(spocList.size());
			else
				clientDto.setNoOfContacts(0);
			
			List<Requirement> reqList = clientReportService.getClientJobsByOrgRelationId(orgRelation.getId(),orgLocation.getId(),user.getId());
			if(reqList != null && reqList.size() > 0)
				clientDto.setReleasedJobs(reqList.size());
			else
				clientDto.setReleasedJobs(0);
			for(Requirement req : reqList){
				Map<String,Integer> subListCount = clientReportService.getSubmissionListByRequirementId(req.getId(),orgLocation.getId(),user.getId());
				clientDto.setSubmissionCount(subListCount.get("submissionCount"));
				clientDto.setSubmissionCount(subListCount.get("hiredCount"));
			}
			ContractReportsDTO contractReportsDTO = new ContractReportsDTO();
			contractReportsDTO.setFrequency(frequency);
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
	
}
