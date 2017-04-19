package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Handles contract related functionalities
 * @author root
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ContractRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractRestController.class);
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		ContractsTemplate contractsTemplate = contractsTemplateService.saveContractTemplate(jsonObj, user, orgLocation);
		
			return contractsTemplate.getId().toString();
	} 
	@RequestMapping(value = "/generatePDF", method = RequestMethod.POST)
	public @ResponseBody String generatePDF(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		return contractsTemplateService.generatePDFContractTemplate(jsonObj, user, orgLocation);
		
	} 
	
	@RequestMapping(value = "/testController/{id}", method = RequestMethod.GET)
	public @ResponseBody  void testController(@PathVariable("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(id));		
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(contractsTemplate.getTemplateContent()));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    response.setContentLength(pdfBytes.length);
          ServletOutputStream sos = response.getOutputStream();
          sos.write(pdfBytes);
          sos.flush();
          sos.close();
	}
	

	
	@RequestMapping(value = "/getAllTemplatesByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getAllTemplates(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ContractsTemplate> contractsTemplate = contractsTemplateService.getAllContractsTemplatesByOrgLocationid(orgLocation);
		return commonUtil.toJSON(contractsTemplate);
	} 
	
	
//	/getVendorContactsByOrgRelationId
	
	
	@RequestMapping(value = "/getVendorContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		
		for(OrgSpoc orgSpoc : orgSpocList){
			StringBuilder sb = new StringBuilder();
			StringBuilder clientSb = new StringBuilder();
			if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
			sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}else{
			sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
			clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
			}else{
			clientSb.append(orgSpoc.getClientUser().getFirstName());
			}
			orgSpoc.setSpocName(sb.toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
			orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
			orgSpoc.setOwnerName(clientSb.toString());
			}
		return commonUtil.toJSON(orgSpocList);
	} 
	
	
	@RequestMapping(value = "/getContractsTemplatesByContractType", method = RequestMethod.GET)
	public @ResponseBody String getContractsTemplatesByContractType(@RequestParam(value = "contractType", required = false) String contractType,HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<ContractsTemplate> contractsTemplateList = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(),contractType);
		return commonUtil.toJSON(contractsTemplateList);
	} 
	
	
//	/saveNewContract
	
	@RequestMapping(value = "/saveNewContract", method = RequestMethod.POST)
	public @ResponseBody boolean saveNewContract(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,HttpServletRequest request) throws Exception {

		boolean isContractSaved = false;
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Contracts contracts = contractsTemplateService.saveNewContract(jsonObj,orgLocation,saveType,user,org);
	   if(null != contracts){
		   isContractSaved = true;
	   }
		return isContractSaved;
	}
	
	/**
	 * Get all contracts list by submission status
	 * @param contractType
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllContractsByOrgLocationIdandStatus", method = RequestMethod.GET)
	public @ResponseBody String getAllContractsByOrgLocationIdandStatus(@RequestParam(value = "contractSubmissionType", required = false) String contractSubmissionType,@RequestParam(value = "tabType", required = false) String tabType,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Contracts contracts = contractsTemplateService.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,tabType);
		return commonUtil.toJSON(contracts);
	} 
	
	@RequestMapping(value = "/getEditContractTemplateDetails", method = RequestMethod.GET)
	public @ResponseBody String getEditContractTemplateDetails(@RequestParam(value="templateId", required=false) String templateId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
		return commonUtil.toJSON(contractsTemplate);
	} 
	
	
	@RequestMapping(value = "/downloadContractsTemplate", method = RequestMethod.GET)
	public void downloadContractsTemplate(@RequestParam(value="templateId", required=false) String templateId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
   	    File file = new File(contractsTemplate.getTemplateName()+".pdf");
		OutputStream outputStream = new FileOutputStream(file);
   	    Document document = new Document();
   	    PdfWriter.getInstance(document, outputStream);
   	    document.open();
   	    HTMLWorker htmlWorker = new HTMLWorker(document);
   	    htmlWorker.parse(new StringReader(contractsTemplate.getTemplateContent()));
   	    document.close();
   	    outputStream.close();
    	 String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileName = new StringBuilder();
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"" ).append(file.getName()).append( "\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file != null){
			file.delete();
		}
	}
	
	@RequestMapping(value = "/updateContractStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateContractStatus(@RequestParam(value = "recordId", required = false) String recordId,
			@RequestParam(value = "status", required = false) String status,
			HttpServletRequest request)throws Exception{

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Contracts contracts  = contractsTemplateService.getContractById(Long.parseLong(recordId));
		if("Waiting to be signed".equalsIgnoreCase(status)){
			Contracts parentContracts  = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
			if(null != contracts.getParentContractId()){
				parentContracts.setId(contracts.getParentContractId().getId());
				parentContracts.setTemplateSubmissionStatus("Waiting To Be Verify");
				contracts.setId(contracts.getId());
				contracts.setTemplateSubmissionStatus("Send For Verification");
			}
		 contractsTemplateService.saveNewContract(parentContracts);
		 contractsTemplateService.saveNewContract(contracts);
		}
		
		
		
		
		
		if(("Waiting To Be Verify").equalsIgnoreCase(status)){
			Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
			if(null != childContarct.getParentContractId()){
				childContarct.setId(childContarct.getId());
				childContarct.setTemplateSubmissionStatus("Finalized");
				contracts.setId(contracts.getId());
				contracts.setTemplateSubmissionStatus("Finalized");
			}
			 contractsTemplateService.saveNewContract(childContarct);
			 contractsTemplateService.saveNewContract(contracts);
		}
		
		
		
		return "success";
}
	
	@RequestMapping(value = "/getRequirementsForSOWByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getRequirementsForSOWByOrgLocationId(@RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<Requirement> requirementList = requirementService.getRequirementsForSOWByOrgLocationId(loginOrgLocation.getId(), sowType);
		
		return commonUtil.toJSON(requirementList);
	}
	
	
	@RequestMapping(value = "/getSubmissionsForSOWByRequirementId", method = RequestMethod.GET)
	public @ResponseBody String getSubmissionsForSOWByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		List<RequirementSubmission> requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByRequirementId(Long.parseLong(requirementId), sowType);
		if(requirementSubmissionsList.size() > 0){
			for(RequirementSubmission requirementSubmission : requirementSubmissionsList){
				StringBuffer candidateName = new StringBuffer(); 
				if(null != requirementSubmission.getCandidate() && StringUtils.isNotBlank(requirementSubmission.getCandidate().getLastName())){
					requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(" ").append(requirementSubmission.getCandidate().getLastName()).toString());
				}else{
					requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).toString());
				}
			}
		}
		
		return commonUtil.toJSON(requirementSubmissionsList);
	}
	
	@RequestMapping(value = "/approveContract", method = RequestMethod.POST)
	@ResponseBody
	public String approveContract(@RequestParam(value = "recordId", required = false) String recordId,
			@RequestParam(value = "status", required = false) String status,
			HttpServletRequest request)throws Exception{
		String isAprroved = "APPROVED";
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Contracts updatedContract = contractsTemplateService.approveContracts(Long.parseLong(recordId),status,orgLocation,user,org);
		if("REJECTED".equalsIgnoreCase(updatedContract.getTemplateSubmissionStatus())){
			isAprroved = "REJECTED";
		}
		return isAprroved;
}

}
