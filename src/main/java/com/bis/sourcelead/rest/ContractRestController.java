package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractRequestDocumentsDao;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.helper.ExpensesDTOConverter;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Html2PdfUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
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
	ContractRequestDocumentsDao contractRequestDocumentsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private Html2PdfUtil html2PdfUtil;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementOffersService requirementOffersService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ContractsTemplateDao contractsTemplateDao;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	ContractRequestDocumentsService contractRequestDocumentsService;
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveClient(
			HttpServletRequest request) throws Exception {

		/*String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);*/
		
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		
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
	
	@RequestMapping(value = "/testController/{id}/{tempName}", method = RequestMethod.GET)
	public @ResponseBody  void testController(@PathVariable("id") String id,@PathVariable("tempName") String tempName,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(id));	
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateByTemplateUUID(id);	
		String header = "<div style='text-align: center;'>"+contractsTemplate.getTemplateName()+"<br></div><br>";
		String totalBody = header + contractsTemplate.getTemplateContent();
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(totalBody));
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
	
	@RequestMapping(value = "/viewContractPdf/{uuid}/{templateName}", method = RequestMethod.GET)
	public @ResponseBody  void viewContractPdf(@PathVariable("uuid") String uuid, @PathVariable("templateName") String templateName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Contracts contract =	contractsTemplateService.getContractById(Long.parseLong(id));	
		Contracts contract =	contractsTemplateService.getContractByContractUUID(uuid);		
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		byte[] pdfBytes = null ;
	
		if(null != contract.getEnvelopId()&& contract.getTypeOfContract().equalsIgnoreCase(Constants.NEW_TYPE)){
			if(null == contract.getContractsTemplate()){
				Contracts con = contractsTemplateService.getContractById(contract.getParentContractId().getId());
				pdfBytes = html2PdfUtil.getPdfBytes(con.getContractsTemplate(),contract,orgLocation,org);
			}
			else{
				pdfBytes = html2PdfUtil.getPdfBytes(contract.getContractsTemplate(),contract,orgLocation,org);
			}
			
		}
		/*if((Constants.NEW_TYPE).equalsIgnoreCase(contract.getTypeOfContract())&& "DRAFT".equalsIgnoreCase(contract.getTemplateSubmissionStatus())){*/
		if((Constants.NEW_TYPE).equalsIgnoreCase(contract.getTypeOfContract())){
			pdfBytes = html2PdfUtil.getPdfBytes(contract.getContractsTemplate(),contract,orgLocation,org);
		}

		if((Constants.CUSTOM_TYPE).equalsIgnoreCase(contract.getTypeOfContract())){
		//if(Constants.CUSTOM_TYPE.equalsIgnoreCase(contract.getTypeOfContract())){
			  pdfBytes = contract.getDocFile();
			  
			  
		}
		
		if(Constants.EXISTING_TYPE.equalsIgnoreCase(contract.getTypeOfContract())){
			  pdfBytes = contract.getDocFile();
		}
  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    
  	    ServletOutputStream sos = response.getOutputStream();
  	    if(null != pdfBytes){
  	    	response.setContentLength(pdfBytes.length);
  	    	sos.write(pdfBytes);
  	    }
          sos.flush();
          sos.close();
	}
	
	
	@RequestMapping(value = "/getAllTemplatesByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getAllTemplates(@RequestParam(value = "templateType", required = false) String templateType , HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ContractsTemplate> contractsTemplate = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(), templateType);
		return commonUtil.toJSON(contractsTemplate);
	} 
	
	
//	/getVendorContactsByOrgRelationId
	
	
	@RequestMapping(value = "/getVendorContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId,@RequestParam(value = "listName", required = false) String listName,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
			if("Vendor".equalsIgnoreCase(listName)){
				orgSpoc.setOwnerUserName(clientSb.toString());
			}
			else{
				orgSpoc.setOwnerUserName(sb.toString());
			}
			/*if(orgSpoc.getVendorUser().getId().longValue()== orgSpoc.getOwnerUser().getId().longValue()){
                orgSpoc.setOwnerUserName(clientSb.toString());
            }
            if(orgSpoc.getClientUser().getId().longValue()==orgSpoc.getOwnerUser().getId().longValue()){
                orgSpoc.setOwnerUserName(sb.toString());
            }*/
           /* if(null != orgSpoc.getOwnerUser()){
				orgSpoc.setOwnerUserName(orgSpoc.getOwnerUser().getFirstName());
			}*/
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
		
		MultipartFile multiFile = null;
		Contracts contracts = contractsTemplateService.saveNewContract(jsonObj,orgLocation,saveType,user,org,"",multiFile);
	   if(null != contracts){
		   isContractSaved = true;
	   }
		return isContractSaved;
	}
	
	
	@RequestMapping(value = "/saveCustomContract", method = RequestMethod.POST)
	public @ResponseBody boolean saveCustomContract(@RequestParam(value = "json", required = false) String json,@RequestParam(value = "saveType", required = false) String saveType,@RequestParam(value = "conType1", required = false) String conType,MultipartHttpServletRequest request) throws Exception {

		boolean isContractSaved = false;
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		} 
		Contracts contracts = contractsTemplateService.saveNewContract(jsonObj,orgLocation,saveType,user,org,conType,multiFile);
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
	public @ResponseBody String getAllContractsByOrgLocationIdandStatus(@RequestParam(value = "contractSubmissionType", required = false) String contractSubmissionType,
			@RequestParam(value = "tabType", required = false) String tabType,
			@RequestParam(value = "contractsType", required = false) String contractsType,
			@RequestParam(value = "contractStatus", required = false) String contractStatus, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		String  userRoleName = (String)SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		Contracts contracts = contractsTemplateService.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,tabType, contractsType,contractStatus,user,userRoleName);
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
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		
		Contracts contracts  = contractsTemplateService.getContractById(Long.parseLong(recordId));
/*		if("Waiting to be signed".equalsIgnoreCase(status)){
			Contracts parentContracts  = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
			if(null != contracts.getParentContractId()){
				parentContracts.setId(contracts.getParentContractId().getId());
				parentContracts.setTemplateSubmissionStatus("Waiting To Be Verify");
				contracts.setId(contracts.getId());
				contracts.setTemplateSubmissionStatus("Send For Verification");
			}
		 contractsTemplateService.saveNewContract(parentContracts);
		 contractsTemplateService.saveNewContract(contracts);
		}*/
		
		if((Constants.WAITING_TO_BE_VERIFY).equalsIgnoreCase(status)){
			Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
			String fromStage = childContarct.getTemplateSubmissionStatus();
			String parentFromStage = contracts.getTemplateSubmissionStatus();
			if(null != childContarct.getParentContractId()){
				childContarct.setId(childContarct.getId());
				childContarct.setTemplateSubmissionStatus(Constants.FINALIZED);
				contracts.setId(contracts.getId());
				contracts.setTemplateSubmissionStatus(Constants.FINALIZED);
			}
			 contractsTemplateService.saveNewContract(childContarct);
			 
			 ContractsHistory childContractsHistory = new ContractsHistory();
			 childContractsHistory.setFromStage(fromStage);
			 childContractsHistory.setContractsId(childContarct);
			 childContractsHistory.setToStage(Constants.FINALIZED);
			 childContractsHistory.setCreatedDate(new Date());
			 childContractsHistory.setUpdatedDate(new Date());
			 childContractsHistory.setCreatedBy(user.getUserCode());
			 childContractsHistory.setUpdatedBy(user.getUserCode());
			contractsTemplateDao.saveContractsHistory(childContractsHistory);
			 
			 contractsTemplateService.saveNewContract(contracts);
			 ContractsHistory parentContractsHistory = new ContractsHistory();
			 parentContractsHistory.setFromStage(parentFromStage);
			 parentContractsHistory.setContractsId(contracts);
			 parentContractsHistory.setToStage(Constants.FINALIZED);
			 parentContractsHistory.setCreatedDate(new Date());
			 parentContractsHistory.setUpdatedDate(new Date());
			 parentContractsHistory.setCreatedBy(user.getUserCode());
			 parentContractsHistory.setUpdatedBy(user.getUserCode());
			contractsTemplateDao.saveContractsHistory(parentContractsHistory);
		}
		
		
		
		return "success";
}
	
	@RequestMapping(value = "/getRequirementsForSOWByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getRequirementsForSOWByOrgLocationId(@RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<Requirement> requirementList = requirementService.getRequirementsForSOWByOrgLocationId(loginOrgLocation.getId(), sowType);
		if(requirementList.size() > 0){
			for(Requirement requirement : requirementList){
				if(null != requirement.getSpocId() && (requirement.getSpocId().getClientUser().getFirstName() != null && requirement.getSpocId().getClientUser().getFirstName() != "" && requirement.getSpocId().getClientUser().getLastName() != null && requirement.getSpocId().getClientUser().getLastName() != "" )){
					StringBuilder spocSB = new StringBuilder();
					requirement.setSpocName(spocSB.append(" (").append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName()).append(")").toString());
				}else if(null != requirement.getSpocId()){
					requirement.setSpocName(" ("+requirement.getSpocId().getClientUser().getFirstName()+")");
				}else{
					requirement.setSpocName("");
				}
			}
		}
		return commonUtil.toJSON(requirementList);
	}
	
	
	@RequestMapping(value = "/getSubmissionsForSOWByRequirementId", method = RequestMethod.GET)
	public @ResponseBody String getSubmissionsForSOWByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		List<RequirementSubmission> requirementSubmissionsList = new ArrayList<>();
		
		if(!"".equalsIgnoreCase(requirementId)){
			requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByRequirementId(Long.parseLong(requirementId), sowType,loginOrgLocation);
		}
		else{
			 requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(sowType,loginOrgLocation);
		}
		
		//List<RequirementSubmission> requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(sowType,loginOrgLocation);
		if(requirementSubmissionsList.size() > 0){
			for(RequirementSubmission requirementSubmission : requirementSubmissionsList){
				StringBuffer candidateName = new StringBuffer(); 
				if(null != requirementSubmission.getCandidate() && StringUtils.isNotBlank(requirementSubmission.getCandidate().getLastName())){
					requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(" ").append(requirementSubmission.getCandidate().getLastName()).toString());
				}else{
					requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).toString());
				}
				if(null != requirementSubmission.getVendorLocationId()){
					requirementSubmission.setVendorLocationCode(requirementSubmission.getVendorLocationId().getLocationCode());
				}
				if(null != requirementSubmission.getRequirement()){
					requirementSubmission.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
				}
			}
		}
		
		return commonUtil.toJSON(requirementSubmissionsList);
	}
	
	
	@RequestMapping(value = "/getVendorForSOWByRequirementSubmissionId", method = RequestMethod.GET)
	public @ResponseBody String getVendorForSOWByRequirementSubmissionId(@RequestParam(value = "reqSubId", required = false) String reqSubId, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(reqSubId));
		if(requirementSubmission != null){
			if(null != requirementSubmission.getVendorLocationId()){
				requirementSubmission.setVendorLocationCode(requirementSubmission.getVendorLocationId().getLocationCode());
			}
		}
		
		return commonUtil.toJSON(requirementSubmission);
	}
	
	
	@RequestMapping(value = "/getSubmissionOfferForSOWByRequirementSubmissionId", method = RequestMethod.GET)
	public @ResponseBody String getSubmissionOfferForSOWByRequirementSubmissionId(@RequestParam(value = "reqSubId", required = false) String reqSubId, HttpServletRequest request,HttpServletResponse response) throws Exception {
		RequirementOffers requirementOffers = null;
		if("" != reqSubId && StringUtils.isNumeric(reqSubId)){
			requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(Long.parseLong(reqSubId));
			if(requirementOffers != null){
				requirementOffers.setDateOfJoiningStr(Constants.DF_DMY.format(requirementOffers.getDateOfJoining()));
			}
		}
		return commonUtil.toJSON(requirementOffers);
	}
	
	@RequestMapping(value = "/validateRequirementSubmission", method = RequestMethod.GET)
	public @ResponseBody Boolean validateRequirementSubmission(@RequestParam(value = "reqSubId", required = false) String reqSubId,@RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Contracts contract = null;
		Boolean flag= false;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		if("" != reqSubId && StringUtils.isNumeric(reqSubId)){
			contract = contractsTemplateService.getContractByReqSubmissionSowTypeAndOrgLocId(Long.parseLong(reqSubId),loginOrgLocation,contractType);
		}
		if(contract!=null){
			flag = true;
		}
		return flag;
	}
	
	
	
	
	@RequestMapping(value = "/approveContract", method = RequestMethod.POST)
	@ResponseBody
	public String approveContract(@RequestParam(value = "recordId", required = false) String recordId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "rejectReason", required = false) String rejectReason,
			HttpServletRequest request)throws Exception{
		String isAprroved = Constants.APPROVED;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

		Contracts updatedContract = contractsTemplateService.approveContracts(Long.parseLong(recordId),status,orgLocation,user,org,rejectReason);
		if(Constants.REJECTED.equalsIgnoreCase(updatedContract.getTemplateSubmissionStatus())){
			isAprroved = Constants.REJECTED;

		}
		return isAprroved;
}

	 /**
	  * To  validate template Name
	  * @param template name
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateContractTemplateName" , method = RequestMethod.POST)
	 public @ResponseBody  String validateContractTemplateName(@RequestParam(value="templateName", required=false) String templateName,@RequestParam(value="contractType", required=false) String contractType,HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	ContractsTemplate  contractsTemplate = contractsTemplateService.findByContractTemplateName(templateName,contractType,orgLocation.getId());
		 	
		 	if (null != contractsTemplate) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
	 


	 /**
	  * To  validate Contract title
	  * @param contract title
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateContractTitle" , method = RequestMethod.POST)
	 public @ResponseBody  String validateContractTitle(@RequestParam(value="contractTitle", required=false) String contractTitle,@RequestParam(value="contractType", required=false) String contractType,@RequestParam(value="contractId", required=false) Long contractId, HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Contracts  contracts = contractsTemplateService.findContractTitleByOrgLocationAndContractType(contractTitle, contractType, orgLocation.getId(),contractId);
		 	
		 	if (null != contracts) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }

	 @RequestMapping(value = "/saveExistigContract", method = RequestMethod.POST)
		public @ResponseBody boolean saveExistigContract(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {

			boolean isContractSaved = false;
			String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			
			MultipartFile multiFile1 = null;
			Iterator<String> itrator = request.getFileNames();
			if(itrator.hasNext()){
		        multiFile1 = request.getFile(itrator.next());
			}
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Contracts contracts = contractsTemplateService.saveExistigContract(jsonObj,multiFile1,orgLocation,user,org);
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			final Map files = multiRequest.getFileMap();
			int maxCount = Integer.parseInt(jsonObj.getString("maxTaxRowNum"));
			
			for (long c = 1; c < maxCount; c++) {
					ContractRequestDocuments contractRequestDocuments = new ContractRequestDocuments();
					MultipartFile  multiFile = (MultipartFile)files.get("uploadBtn"+c);
					
						contractRequestDocuments.setContractId(contracts);
						if(jsonObj.has(c+"documentName") && !jsonObj.getString(c+"documentName").isEmpty()){
							contractRequestDocuments.setFileName(jsonObj.getString(c+"documentName"));
						if(jsonObj.has(c+"description") && !jsonObj.getString(c+"description").isEmpty()){
							contractRequestDocuments.setDescription(jsonObj.getString(c+"description"));
						}
						if (jsonObj.has(c + "candiDoc")
								&& !jsonObj.getString(c + "candiDoc").isEmpty()) {
							contractRequestDocuments.setDocType(jsonObj.getString(c + "candiDoc"));
							
						}
						}
						if(multiFile != null){
							contractRequestDocuments.setDocFile(multiFile.getBytes());
							contractRequestDocuments.setFileType(multiFile.getContentType());
						}
						contractRequestDocuments.setCreatedDate(new Date());
						contractRequestDocuments.setUpdatedDate(new Date());
						contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
						contractRequestDocuments.setCreatedBy(user.getUserCode());
						contractRequestDocuments.setUpdatedBy(user.getUserCode());
						contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
			}
		   if(null != contracts){
			   isContractSaved = true;
		   }
			return isContractSaved;
		}
	 
	 
	 @RequestMapping(value = "/getContractByContractUUID", method = RequestMethod.GET)
		public @ResponseBody String getContractByContractUUID(@RequestParam(value = "contractUUID", required = false) String contractUUID, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 	Contracts contract =	contractsTemplateService.getContractByContractUUID(contractUUID);
		 	
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	
		 	if(null != contract.getOwnerUserId()){
		 		StringBuilder owner = new StringBuilder();
		 		if(null != contract.getOwnerUserId().getLastName() && "" != contract.getOwnerUserId().getLastName()){
					contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).append(" ").append(contract.getOwnerUserId().getLastName()).toString());
				}else{
					contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).toString());
				}
		 	}
		 	if(null != contract.getOrgRelations()){
		 	OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contract.getOrgRelations().getId());
		 	if(null != orgRelations){
		 		if(orgRelations.getLocationId().getId().equals(orgLocation.getId())){
		 			contract.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
				}	
				else if(orgRelations.getResponderLocationId().getId() == orgLocation.getId()){
					contract.setContractWith(orgRelations.getLocationId().getOrgLocationName());
				}
		 	}
		 	
		 	}
		 	
		 	if(null != contract.getApproverUser()){
		 		StringBuilder approver = new StringBuilder();
		 		if(null != contract.getApproverUser().getLastName() && "" != contract.getApproverUser().getLastName()){
					contract.setApproverUserName(approver.append(contract.getApproverUser().getFirstName()).append(" ").append(contract.getApproverUser().getLastName()).toString());
				}else{
					contract.setApproverUserName(approver.append(contract.getApproverUser().getFirstName()).toString());
				}
		 	}
		 	if(null != contract.getContactUser()){
		 		StringBuilder approver = new StringBuilder();
		 		if(null != contract.getContactUser().getLastName() && "" != contract.getContactUser().getLastName()){
					contract.setContactUserName(approver.append(contract.getContactUser().getFirstName()).append(" ").append(contract.getContactUser().getLastName()).toString());
				}else{
					contract.setContactUserName(approver.append(contract.getContactUser().getFirstName()).toString());
				}
		 	}
		 	if(null != contract.getRequirementSubmission()){
				contract.setRequirementName(contract.getRequirementSubmission().getRequirement().getJobTitle());
		 	}
		 	
		 	if(null != contract.getRequirementSubmission()){
		 		
		 		StringBuilder applicant = new StringBuilder();
		 		if(null != contract.getRequirementSubmission().getCandidate() && null != contract.getRequirementSubmission().getCandidate().getLastName()){
		 			contract.setApplicantName(applicant.append(contract.getRequirementSubmission().getCandidate().getFirstName()).append(" ").append(contract.getRequirementSubmission().getCandidate().getLastName()).toString());
		 		}else{
		 			contract.setApplicantName(applicant.append(contract.getRequirementSubmission().getCandidate().getFirstName()).toString());
		 		}
				
		 	}
		 	
		 	if(null != contract.getContractsTemplate()){
				contract.setTemplateName(contract.getContractsTemplate().getTemplateName());
				contract.setTempUUID(contract.getContractsTemplate().getTemplateUUID());
		 	}
		 	
		 	if(null != contract.getRequirementSubmission()){
		 		RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(contract.getRequirementSubmission().getId());
		 		if(null != requirementOffers){
		 			StringBuilder amount = new StringBuilder();
		 			contract.setOfferValue(amount.append(requirementOffers.getPaymentType()).append(" ").append(requirementOffers.getAmount()).append(" / ").append(requirementOffers.getPaymentMode()).toString());
		 		}
			}
		 	
		 	DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			DateFormat standardFormat = new SimpleDateFormat ("dd-MM-yyyy");
			if(null != contract && null != contract.getStartDate()){
				String currentTimeStr = Constants.TZ_DF.format(contract.getStartDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = standardFormat.format(converteDate);
				contract.setStartDateStr(currentDateInTimezone);
			}
			if(null != contract && null != contract.getEndDate()){
				String endTimeStr = Constants.TZ_DF.format(contract.getEndDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = standardFormat.format(endconverteDate);
				contract.setEndDateStr(endDateInTimezone);
			}
			
			if (null != contract && null != contract.getJoinDate()) {
				String endTimeStr = Constants.TZ_DF.format(contract.getJoinDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = standardFormat.format(endconverteDate);
				contract.setJoinedDateStr(endDateInTimezone);
			}
			
			if(null != contract && null != contract.getCreatedDate()){
				String currentTimeStr = Constants.TZ_DF.format(contract.getCreatedDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = standardFormat.format(converteDate);
				contract.setCreatedDateStr(currentDateInTimezone);
			}
			if(null != contract && null != contract.getUpdatedDate()){
				String endTimeStr = Constants.TZ_DF.format(contract.getUpdatedDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = standardFormat.format(endconverteDate);
				contract.setUpdatedDateStr(endDateInTimezone);
			}
			
			User createdBy = userService.getUserByUserCode(contract.getCreatedBy());
			if(createdBy != null){
				StringBuilder created = new StringBuilder();
				if(null != createdBy.getLastName() && "" != createdBy.getLastName()){
					contract.setCreatedByName(created.append(createdBy.getFirstName()).append(" ").append(createdBy.getLastName()).toString());
				}else{
					contract.setCreatedByName(created.append(createdBy.getFirstName()).toString());
				}
			}
			
			User updatedBy = userService.getUserByUserCode(contract.getUpdatedBy());
			if(updatedBy != null){
				StringBuilder updated = new StringBuilder();
				if(null != updatedBy.getLastName() && "" != updatedBy.getLastName()){
					contract.setUpdatedByName(updated.append(updatedBy.getFirstName()).append(" ").append(updatedBy.getLastName()).toString());
				}else{
					contract.setUpdatedByName(updated.append(updatedBy.getFirstName()).toString());
				}
			}
			
			List<ContractRequestDocuments> contractRequestDocumentsList  = new ArrayList<>();
    		if(null != contract.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contract.getParentContractId().getId());
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
    		}else{
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
    		} 
		 	
    		contract.setContractRequestDocumentsList(contractRequestDocumentsList);
    		
			return commonUtil.toJSON(contract);
		}
	 
	 @RequestMapping(value = "/getContractStageCounts", method = RequestMethod.GET)
		public @ResponseBody String getContractStageCounts(HttpServletRequest request) throws Exception {
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		 String  userRoleName = (String)SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 Contracts contractsCount = contractsTemplateService.getAllContractsCountByLocationIdAndStatus(orgLocation, "", "", "", "", user, userRoleName);
			return commonUtil.toJSON(contractsCount);
		 
	 }
	 
	 @RequestMapping(value = "/getDocsByContractUUID", method = RequestMethod.GET)
		public @ResponseBody String getDocsByContractUUID(@RequestParam(value = "contractUUID", required = false) String contractUUID, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 	Contracts contract =	contractsTemplateService.getContractByContractUUID(contractUUID);
		 	
			List<ContractRequestDocuments> contractRequestDocumentsList  = new ArrayList<>();
    		if(null != contract.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contract.getParentContractId().getId());
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
    		}else{
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
    		} 
			return commonUtil.toJSON(contractRequestDocumentsList);
		}
	 @RequestMapping(value = "/saveDocContracts", method = RequestMethod.POST)
		public @ResponseBody String saveDocContracts(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {

			String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			 final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			final Map files = multiRequest.getFileMap();
			int maxCount = Integer.parseInt(jsonObj.getString("maxDocCount"));
			for (long c = 1; c <= maxCount; c++) {
				if(jsonObj.has("docId"+c) && !jsonObj.getString("docId"+c).isEmpty()){
					ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(jsonObj.getString("docId"+c)));
					MultipartFile  multiFile = (MultipartFile)files.get("uploadBtn"+jsonObj.getString("docId"+c));
					if(multiFile != null && multiFile.getSize()>0){
						contractRequestDocuments.setDocFile(multiFile.getBytes());
						contractRequestDocuments.setFileType(multiFile.getContentType());
						contractRequestDocuments.setCreatedDate(new Date());
						contractRequestDocuments.setUpdatedDate(new Date());
						contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
						contractRequestDocuments.setCreatedBy(user.getUserCode());
						contractRequestDocuments.setUpdatedBy(user.getUserCode());
						contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
						contractsTemplateService.documentsReceivedMailToOwner(contractRequestDocuments.getContractId(),user,org);
					}
				}
			}
			
			return "success";
		}
	 
	 @RequestMapping(value = "/terminateDocument", method = RequestMethod.POST)
	 public @ResponseBody String terminateDocument(@RequestParam(value = "docuId", required = false) String docuId, HttpServletRequest request) throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(docuId));
			contractRequestDocuments.setStatus(Constants.MEMBER_IN_ACTIVE);
			contractRequestDocuments.setUpdatedDate(new Date());
			contractRequestDocuments.setUpdatedBy(user.getUserCode());
			contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
			
			return "success";
		}
	 @RequestMapping(value = "/updateDocument", method = RequestMethod.POST)
	 public @ResponseBody String updateDocument(@RequestParam(value = "docuId", required = false) String docuId, HttpServletRequest request) throws Exception {
			ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(docuId));
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			contractRequestDocuments.setDocFile(new byte[0]);
			contractRequestDocuments.setFileType("");
			//contractRequestDocuments.setCreatedDate(new Date());
			contractRequestDocuments.setUpdatedDate(new Date());
			contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
			//contractRequestDocuments.setCreatedBy(user.getUserCode());
			contractRequestDocuments.setUpdatedBy(user.getUserCode());
			contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
			return "success";
		}
	 

	 @RequestMapping(value = "/downloadContractDocument",method = RequestMethod.GET)
	 public void downloadContractDocument(@RequestParam(value="contractUUID", required=false) String contractUUID, HttpServletResponse response) throws Exception {
			ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsByContractUUID(contractUUID);
			if(null != contractRequestDocuments){
				response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(contractRequestDocuments.getFileName()).append("\"").toString());
		        
		        InputStream inputStream = new ByteArrayInputStream(contractRequestDocuments.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 
			}

	 }
	 
	 @RequestMapping(value = "/updateContractEndDate", method = RequestMethod.POST)
	 public @ResponseBody String updateContractEndDate(@RequestParam(value = "contractId", required = false) String contractId,@RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) throws Exception {
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	Contracts contracts = contractsTemplateService.getContractById(Long.parseLong(contractId));
		 	Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 	if(!"".equalsIgnoreCase(endDate) && endDate != null){
		 		Date endTimeStr = commonUtil.getDateFormat(endDate);  
				String endDateStr = Constants.TZ_DF.format(endTimeStr);  
				String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDateStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
				
				String fromStage = "";
				
				ContractsHistory childContractsHistory = new ContractsHistory();
				if(contracts.getTemplateSubmissionStatus().equalsIgnoreCase(Constants.EXPIRED))
				{
					ContractsHistory contractsHistory = contractsTemplateService.getAllContractsHistoryByContractId(contracts.getId());
					fromStage = contractsHistory.getFromStage();
	        		Date date = new Date();
	        		String currentTimeStr = TZ_DF_TS.format(date);
					Date curDate = TZ_DF_TS.parse(currentTimeStr);
	        		if(convertedEndDate.after(curDate)|| convertedEndDate.equals(curDate)){
	        			childContractsHistory.setFromStage(contracts.getTemplateSubmissionStatus());
	        			childContractsHistory.setContractsId(contracts);
	        			contracts.setTemplateSubmissionStatus(fromStage);
	        			childContractsHistory.setToStage(fromStage);
	        			childContractsHistory.setCreatedDate(new Date());
	        			childContractsHistory.setUpdatedDate(new Date());
	        			contractsTemplateDao.saveContractsHistory(childContractsHistory);
	        		}
				}
				
				
			 	// updating parent contract end date if get the contract i is child contract
				if(contracts.getParentContractId()!=null){
				 	if(null != contracts.getParentContractId().getId()){
				 		Contracts parentContracts = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
				 		parentContracts.setEndDate(convertedEndDate);
				 		parentContracts.setUpdatedDate(new Date());
				 		parentContracts.setUpdatedBy(user.getUserCode());
						contractsTemplateDao.saveNewContract(parentContracts);
				 	}
				}
				
			 // updating child contract end date if get the parent contract
			 	if(null == contracts.getParentContractId()){
			 		Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
			 		if(null != childContarct){
				 		childContarct.setEndDate(convertedEndDate);
				 		childContarct.setUpdatedDate(new Date());
				 		childContarct.setUpdatedBy(user.getUserCode());
						contractsTemplateDao.saveNewContract(childContarct);
			 		}
			 	}
			 	
			 // updating contract end date 
			 	contracts.setEndDate(convertedEndDate);
			 	contracts.setUpdatedDate(new Date());
			 	contracts.setUpdatedBy(user.getUserCode());
				contractsTemplateDao.saveNewContract(contracts);
		 	}
			
			return "success";
		}
	 
	 
	 @RequestMapping(value = "/rest/getContractRequestDocumentsById")
	 public ContractRequestDocuments getContractRequestDocumentsById(@RequestParam(value = "docId", required = true) Long docId) throws Exception {
		 ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(docId);
		 return contractRequestDocuments;
	 }
	 
	 @RequestMapping(value = "/rest/getContractTemplateByTemplateUUID")
	 public ContractsTemplate getContractTemplateByTemplateUUID(@RequestParam(value = "uuid", required = true) String uuid) throws Exception {
		 ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateByTemplateUUID(uuid);
		 return contractsTemplate;
	 }

}
