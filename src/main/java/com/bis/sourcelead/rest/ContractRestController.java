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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractRequestDocumentsDao;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dto.ClientContractsDTO;
import com.bis.sourcelead.dto.ContractDocumentsDTO;
import com.bis.sourcelead.dto.ContractReqDocsDTO;
import com.bis.sourcelead.dto.ContractTemplateDTO;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.FullTimeContractsDTO;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.dto.VendorContractsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.NewContractCustomFieldDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.Html2PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * Handles contract related functionalities
 * 
 * @author root
 *
 */
@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ContractRestController {
	private static final Logger logger = LoggerFactory.getLogger(ContractRestController.class);
	@Value("${static.path}")
	private String staticPath;
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
	OrgLocationService orgLocationService;
	@Autowired
	ContractRequestDocumentsService contractRequestDocumentsService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	@Autowired
	private NewContractCustomFieldDataService newContractCustomFieldDataService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveContractTemplate(ContractTemplateDTO contractTemplateDTO, HttpServletRequest request) throws Exception
	{
		/*
		 * String json1 = "{" + json + "}"; JSONObject jsonObj = new
		 * JSONObject(json1);
		 */
		// JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ContractsTemplate contractsTemplate = contractsTemplateService.saveContractTemplate(contractTemplateDTO, null, user, orgLocation);
		return contractsTemplate.getId().toString();
	}
	
	@RequestMapping(value = "/generatePDF", method = RequestMethod.POST)
	public @ResponseBody String generatePDF(@RequestParam(value = "json", required = false) String json, HttpServletRequest request) throws Exception
	{
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return contractsTemplateService.generatePDFContractTemplate(jsonObj, user, orgLocation);
	}
	
	@RequestMapping(value = "/testController/{id}/{tempName}", method = RequestMethod.GET)
	public @ResponseBody void testController(@PathVariable("id") String id, @PathVariable("tempName") String tempName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ContractsTemplate contractsTemplate =
		// contractsTemplateService.getContractTemplateById(Long.parseLong(id));
		ContractsTemplate contractsTemplate = contractsTemplateService.getContractTemplateByTemplateUUID(id);
		try {
			String header = "<div style='text-align: center;'>"
					+ contractsTemplate.getTemplateName()
					+ "<br></br></div><br></br><br></br><br></br>";
			String totalBody = header + contractsTemplate.getTemplateContent();
			org.jsoup.nodes.Document doc = Jsoup.parse(totalBody);
			doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
			String finalString = doc.html();
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, byteArray);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
			document.close();
			byte[] pdfBytes = byteArray.toByteArray();
			response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
			response.setContentType("application/pdf");
			response.setContentLength(pdfBytes.length);
			ServletOutputStream sos = response.getOutputStream();
			sos.write(pdfBytes);
			sos.flush();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/previewContractTemplate/{id}/{tempName}", method = RequestMethod.GET)
	public @ResponseBody  void previewEmpTemplate(@PathVariable("id") String tempUUID,@PathVariable("tempName") String tempName,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate = contractsTemplateService.getContractTemplateByTemplateUUID(tempUUID);
		
			String header = "<div style='text-align: center;'>"+contractsTemplate.getTemplateName()+"<br></br></div><br></br><br></br><br></br>";
			String totalBody =header+ contractsTemplate.getTemplateContent();
			reportsUtil.viewCompleteletterheadData(totalBody, contractsTemplate, response);
    	
	}
	
	@RequestMapping(value = "/viewContractPdf/{uuid}/{templateName}", method = RequestMethod.GET)
	public @ResponseBody void viewContractPdf(@PathVariable("uuid") String uuid, @PathVariable("templateName") String templateName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Contracts contract =
		// contractsTemplateService.getContractById(Long.parseLong(id));
		Contracts contract = contractsTemplateService.getContractByContractUUID(uuid);
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		byte[] pdfBytes = null;
		if (null != contract.getEnvelopId()
				&& contract.getTypeOfContract().equalsIgnoreCase(Constants.NEW_TYPE)) {
			if (null == contract.getContractsTemplate()) {
				Contracts con = contractsTemplateService.getContractById(contract.getParentContractId().getId());
				pdfBytes = html2PdfUtil.getPdfBytes(con.getContractsTemplate(), contract, orgLocation, org);
			}
			else {
				pdfBytes = html2PdfUtil.getPdfBytes(contract.getContractsTemplate(), contract, orgLocation, org);
			}
		}
		/*
		 * if((Constants.NEW_TYPE).equalsIgnoreCase(contract.getTypeOfContract()
		 * )&&
		 * "DRAFT".equalsIgnoreCase(contract.getTemplateSubmissionStatus())){
		 */
		if ((Constants.NEW_TYPE).equalsIgnoreCase(contract.getTypeOfContract())) {
			pdfBytes = html2PdfUtil.getPdfBytes(contract.getContractsTemplate(), contract, orgLocation, org);
		}
		if ((Constants.CUSTOM_TYPE).equalsIgnoreCase(contract.getTypeOfContract())) {
			// if(Constants.CUSTOM_TYPE.equalsIgnoreCase(contract.getTypeOfContract())){
			String path = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			pdfBytes = CommonUtil.readBytesFromFile(path + contract.getFileURL());
		}
		if (Constants.EXISTING_TYPE.equalsIgnoreCase(contract.getTypeOfContract())) {
			String path = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			pdfBytes = CommonUtil.readBytesFromFile(path + contract.getFileURL());
		}
		response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
		if (null != pdfBytes) {
			response.setContentLength(pdfBytes.length);
			sos.write(pdfBytes);
		}
		sos.flush();
		sos.close();
	}
	
	@RequestMapping(value = "/getAllTemplatesByOrgLocationId", method = RequestMethod.POST)
	public ResponseList<ContractsTemplate> getAllTemplates(ContractTemplateDTO contractTemplateDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(contractTemplateDTO.getOrgLocationId()));
		String[] tableColumns = { "templateName", "templateType","letterHead", "description", "action" };
		int sortColumnIndex = 0; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (contractTemplateDTO.getOrder() != null && !contractTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(contractTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = contractTemplateDTO.getOrder().get(0).get("dir");
		}
		contractTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		contractTemplateDTO.setSortOrder(sortColumnOrder);
		// List<ContractsTemplate> contractsTemplate =
		// contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(),
		// templateType);
		return contractsTemplateService.getAllContractsTemplatesByOrgLocationid(orgLocation, contractTemplateDTO);
	}
	// /getVendorContactsByOrgRelationId
	
	@RequestMapping(value = "/getVendorContactsByOrgRelationId", method = RequestMethod.GET)
	public @ResponseBody String getVendorContactsByOrgRelationId(@RequestParam(value = "orgRelationId", required = false) String orgRelationId, @RequestParam(value = "listName", required = false) String listName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(orgRelationId));
		for (OrgSpoc orgSpoc : orgSpocList) {
			StringBuilder sb = new StringBuilder();
			StringBuilder clientSb = new StringBuilder();
			if (null != orgSpoc.getVendorUser().getFirstName()
					&& null != orgSpoc.getVendorUser().getLastName()) {
				sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
			}
			else {
				sb.append(orgSpoc.getVendorUser().getFirstName());
			}
			if (null != orgSpoc.getClientUser().getFirstName()
					&& null != orgSpoc.getClientUser().getLastName()) {
				clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
			}
			else {
				clientSb.append(orgSpoc.getClientUser().getFirstName());
			}
			orgSpoc.setSpocName(sb.toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
			orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
			orgSpoc.setSpocUserCode(orgSpoc.getVendorUser().getUserCode());
			orgSpoc.setOwnerName(clientSb.toString());
			if ("Vendor".equalsIgnoreCase(listName)) {
				orgSpoc.setOwnerUserName(clientSb.toString());
			}
			else {
				orgSpoc.setOwnerUserName(sb.toString());
			}
			/*
			 * if(orgSpoc.getVendorUser().getId().longValue()==
			 * orgSpoc.getOwnerUser().getId().longValue()){
			 * orgSpoc.setOwnerUserName(clientSb.toString()); }
			 * if(orgSpoc.getClientUser().getId().longValue()==orgSpoc.
			 * getOwnerUser().getId().longValue()){
			 * orgSpoc.setOwnerUserName(sb.toString()); }
			 */
			/*
			 * if(null != orgSpoc.getOwnerUser()){
			 * orgSpoc.setOwnerUserName(orgSpoc.getOwnerUser().getFirstName());
			 * }
			 */
		}
		return commonUtil.toJSON(orgSpocList);
	}
	
	@RequestMapping(value = "/getContractsTemplatesByContractType", method = RequestMethod.GET)
	public @ResponseBody String getContractsTemplatesByContractType(@RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ContractsTemplate> contractsTemplateList = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(), contractType);
		return commonUtil.toJSON(contractsTemplateList);
	}
	
	// /saveNewContract
	@RequestMapping(value = "/saveNewContract", method = RequestMethod.POST)
	public @ResponseBody boolean saveNewContract(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		boolean isContractSaved = false;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Long orgLocationId = (Long)SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		Contracts contracts = contractsTemplateService.saveNewContract(contractsDTO, orgLocation, "", user, org, "");
		if (null != contracts) {
			isContractSaved = true;
		}
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Successfully you have created a Contract");
		return isContractSaved;
	}
	
	@RequestMapping(value = "/saveCustomContract", method = RequestMethod.POST)
	public @ResponseBody boolean saveCustomContract(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		boolean isContractSaved = false;
		// String json1 = "{" + json + "}";
		// JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocation","Success new contract is created");
		// MultipartFile multiFile = null;
		// Iterator<String> itrator = request.getFileNames();
		// if(itrator.hasNext()){
		// multiFile = request.getFile(itrator.next());
		// }
		contractsDTO.setStaticPath(staticPath);
		Contracts contracts = contractsTemplateService.saveNewContract(contractsDTO, orgLocation, "", user, org, "");
		if (null != contracts) {
			isContractSaved = true;
		}
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Successfully you have created a Contract");
		return isContractSaved;
	}
	
	/**
	 * Get all contracts list by submission status
	 * 
	 * @param contractType
	 * @param request
	 * @param response
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllContractsByOrgLocationIdandStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseList<VendorContractsDTO> getAllContractsByOrgLocationIdandStatus(@RequestParam(value = "contractSubmissionType", required = false) String contractSubmissionType, @RequestParam(value = "tabType", required = false) String tabType, @RequestParam(value = "contractsType", required = false) String contractsType, @RequestParam(value = "contractStatus", required = false) String contractStatus, @RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		ResponseList<VendorContractsDTO> vendorContracts = contractsTemplateService.getAllContractsByLocationIdAndStatus(orgLocation, contractSubmissionType, tabType, contractsType, contractStatus, user, userRoleName, searchValue);
		return vendorContracts;
	}
	
	@RequestMapping(value = "/getAllClientContractsByOrgLocationIdandStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseList<ClientContractsDTO> getAllClientContractsByOrgLocationIdandStatus(@RequestParam(value = "contractSubmissionType", required = false) String contractSubmissionType
			, @RequestParam(value = "tabType", required = false) String tabType
			, @RequestParam(value = "contractsType", required = false) String contractsType
			, @RequestParam(value = "contractStatus", required = false) String contractStatus
			, @RequestParam(value = "searchValue", required = false) String searchValue
			, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		ResponseList<ClientContractsDTO> clientContracts = contractsTemplateService.getAllClientContractsByLocationIdAndStatus(orgLocation, contractSubmissionType, tabType, contractsType, contractStatus, user, userRoleName, searchValue);
		return clientContracts;
	}
	
	@RequestMapping(value = "/getAllApplicantContractsByOrgLocationIdandStatus", method = RequestMethod.GET)
	public @ResponseBody ResponseList<FullTimeContractsDTO> getAllApplicantContractsByOrgLocationIdandStatus(@RequestParam(value = "contractSubmissionType", required = false) String contractSubmissionType, @RequestParam(value = "tabType", required = false) String tabType, @RequestParam(value = "contractsType", required = false) String contractsType, @RequestParam(value = "contractStatus", required = false) String contractStatus, @RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		ResponseList<FullTimeContractsDTO> applicantContracts = contractsTemplateService.getAllApplicantContractsByLocationIdAndStatus(orgLocation, contractSubmissionType, tabType, contractsType, contractStatus, user, userRoleName, searchValue);
		return applicantContracts;
	}
	
	@RequestMapping(value = "/getEditContractTemplateDetails", method = RequestMethod.GET)
	public @ResponseBody String getEditContractTemplateDetails(@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate = contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
		return commonUtil.toJSON(contractsTemplate);
	}
	
	@RequestMapping(value = "/downloadContractsTemplate", method = RequestMethod.GET)
	public void downloadContractsTemplate(@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate = contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
		File file = new File(contractsTemplate.getTemplateName() + ".pdf");
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
		response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}
	
	@RequestMapping(value = "/updateContractStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateContractStatus(@RequestParam(value = "recordId", required = false) String recordId, @RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Contracts contracts = contractsTemplateService.getContractById(Long.parseLong(recordId));
		/*
		 * if("Waiting to be signed".equalsIgnoreCase(status)){ Contracts
		 * parentContracts = contractsTemplateService.getContractById(contracts.
		 * getParentContractId().getId()); if(null !=
		 * contracts.getParentContractId()){
		 * parentContracts.setId(contracts.getParentContractId().getId());
		 * parentContracts.setTemplateSubmissionStatus("Waiting To Be Verify");
		 * contracts.setId(contracts.getId());
		 * contracts.setTemplateSubmissionStatus("Send For Verification"); }
		 * contractsTemplateService.saveNewContract(parentContracts);
		 * contractsTemplateService.saveNewContract(contracts); }
		 */
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())) {
			
			if ((Constants.WAITING_TO_BE_VERIFY).equalsIgnoreCase(status)) {
//				Contracts childContarct = contractsTemplateService.getChildContractById(contracts.getId());
				String fromStage = contracts.getTemplateSubmissionStatus();
				contracts.setTemplateSubmissionStatus(Constants.FINALIZED);
				contractsTemplateService.saveNewContract(contracts);
				ContractsHistory contractsHistory = new ContractsHistory();
				contractsHistory.setFromStage(fromStage);
				contractsHistory.setContractsId(contracts);
				contractsHistory.setToStage(Constants.FINALIZED);
				contractsHistory.setCreatedDate(new Date());
				contractsHistory.setUpdatedDate(new Date());
				contractsHistory.setCreatedBy(user.getUserCode());
				contractsHistory.setUpdatedBy(user.getUserCode());
				contractsTemplateDao.saveContractsHistory(contractsHistory);
//				contractsTemplateService.sendFinalisedMailToReceiver(contracts, orgLocation, user);
			}
			
		}else {
			if ((Constants.WAITING_TO_BE_VERIFY).equalsIgnoreCase(status)) {
				Contracts childContarct = contractsTemplateService.getChildContractById(contracts.getId());
				String fromStage = childContarct.getTemplateSubmissionStatus();
				String parentFromStage = contracts.getTemplateSubmissionStatus();
				if (null != childContarct.getParentContractId()) {
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
				contractsTemplateService.sendFinalisedMailToReceiver(contracts, orgLocation, user);
			}
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/getRequirementsForSOWByOrgLocationId", method = RequestMethod.GET)
	public @ResponseBody String getRequirementsForSOWByOrgLocationId(@RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Requirement> requirementList = requirementService.getRequirementsForSOWByOrgLocationId(loginOrgLocation.getId(), sowType);
		if (requirementList.size() > 0) {
			for (Requirement requirement : requirementList) {
				if (null != requirement.getSpocId()
						&& (requirement.getManagerUserId().getFirstName() != null
								&& requirement.getManagerUserId().getFirstName() != ""
								&& requirement.getManagerUserId().getLastName() != null
								&& requirement.getManagerUserId().getLastName() != "")) {
					StringBuilder spocSB = new StringBuilder();
					requirement.setSpocName(spocSB.append(" (").append(requirement.getManagerUserId().getFirstName()).append(" ").append(requirement.getManagerUserId().getLastName()).append(")").toString());
				}
				else if (null != requirement.getSpocId()) {
					requirement.setSpocName(" (" + requirement.getManagerUserId().getFirstName()
							+ ")");
				}
				else {
					requirement.setSpocName("");
				}
			}
		}
		return commonUtil.toJSON(requirementList);
	}
	
	@RequestMapping(value = "/getSubmissionsForSOWByRequirementId", method = RequestMethod.GET)
	public @ResponseBody String getSubmissionsForSOWByRequirementId(@RequestParam(value = "requirementId", required = false) String requirementId, @RequestParam(value = "sowType", required = false) String sowType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<RequirementSubmission> requirementSubmissionsList = new ArrayList<>();
		if (!"".equalsIgnoreCase(requirementId)) {
			Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
			if (!"".equalsIgnoreCase(requirementId)) {
				requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByRequirementId(Long.parseLong(requirementId), sowType, loginOrgLocation);
			}
			else {
				requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(sowType, loginOrgLocation);
			}
			// List<RequirementSubmission> requirementSubmissionsList =
			// requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(sowType,loginOrgLocation);
			if (requirementSubmissionsList.size() > 0) {
				for (RequirementSubmission requirementSubmission : requirementSubmissionsList) {
					StringBuffer candidateName = new StringBuffer();
					if (null != requirementSubmission.getCandidate()
							&& StringUtils.isNotBlank(requirementSubmission.getCandidate().getLastName())) {
						requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(" ").append(requirementSubmission.getCandidate().getLastName()).toString());
					}
					else {
						requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).toString());
					}
					if (null != requirementSubmission.getVendorLocationId()) {
						requirementSubmission.setVendorLocationCode(requirementSubmission.getVendorLocationId().getLocationCode());
					}
					if (null != requirementSubmission.getRequirement()) {
						requirementSubmission.setRequirementName(requirementSubmission.getRequirement().getJobTitle());
					}
					if (req.getClientId() != null)
						requirementSubmission.setOrgRelationId(req.getClientId().getId());
				}
			}
		}
		return commonUtil.toJSON(requirementSubmissionsList);
	}
	
	@RequestMapping(value = "/getVendorForSOWByRequirementSubmissionId", method = RequestMethod.GET)
	public @ResponseBody RequirementSubmission getVendorForSOWByRequirementSubmissionId(@RequestParam(value = "reqSubId", required = false) String reqSubId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(reqSubId));
		if (requirementSubmission != null) {
			if (null != requirementSubmission.getVendorLocationId()) {
				requirementSubmission.setVendorLocationCode(requirementSubmission.getVendorLocationId().getLocationCode());
			}
			else {
				Candidate candi = requirementSubmission.getCandidate();
				if (candi != null) {
					if (candi.getParentCandidateId() != null) {
						OrgLocations location = candi.getParentCandidateId().getOrgLocationId();
						requirementSubmission.setVendorLocationCode(location.getLocationCode());
					}
				}
			}
		}
		return requirementSubmission;
	}
	
	@RequestMapping(value = "/getSubmissionOfferForSOWByRequirementSubmissionId", method = RequestMethod.GET)
	public @ResponseBody String getSubmissionOfferForSOWByRequirementSubmissionId(@RequestParam(value = "reqSubId", required = false) String reqSubId, @RequestParam(value = "locationId", required = false) Long locationId, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RequirementOffers requirementOffers = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		// SimpleDateFormat simpleDateFormat =
		// orgLocations.getLocFullFormDateFormatter();
		if ("" != reqSubId && StringUtils.isNumeric(reqSubId)) {
			requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(Long.parseLong(reqSubId));
			if (requirementOffers != null) {
				requirementOffers.setDateOfJoiningStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requirementOffers.getDateOfJoining()));
			}
		}
		return commonUtil.toJSON(requirementOffers);
	}
	
	@RequestMapping(value = "/validateRequirementSubmission", method = RequestMethod.GET)
	public @ResponseBody Boolean validateRequirementSubmission(@RequestParam(value = "reqSubId", required = false) String reqSubId, @RequestParam(value = "contractType", required = false) String contractType, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Contracts contract = null;
		Boolean flag = false;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if ("" != reqSubId && StringUtils.isNumeric(reqSubId)) {
			contract = contractsTemplateService.getContractByReqSubmissionSowTypeAndOrgLocId(Long.parseLong(reqSubId), loginOrgLocation, contractType);
		}
		if (contract != null) {
			flag = true;
		}
		return flag;
	}
	
	@RequestMapping(value = "/approveContract", method = RequestMethod.POST)
	@ResponseBody
	public String approveContract(@RequestParam(value = "recordId", required = false) String recordId, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "rejectReason", required = false) String rejectReason, HttpServletRequest request) throws Exception
	{
		String isAprroved = Constants.APPROVED;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Contracts updatedContract = contractsTemplateService.approveContracts(Long.parseLong(recordId), status, orgLocation, user, org, rejectReason);
		if (Constants.REJECTED.equalsIgnoreCase(updatedContract.getTemplateSubmissionStatus())) {
			isAprroved = Constants.REJECTED;
		}
		return isAprroved;
	}
	
	/**
	 * To validate template Name
	 * 
	 * @param template
	 *            name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateContractTemplateName", method = RequestMethod.POST)
	public @ResponseBody String validateContractTemplateName(@RequestParam(value = "templateName", required = false) String templateName, 
															 @RequestParam(value = "contractType", required = false) String contractType, 
															 @RequestParam(value = "templateUUID", required = false) String templateUUID,
															 HttpServletRequest request) throws Exception
	{
		String result = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ContractsTemplate contractsTemplate = contractsTemplateService.findByContractTemplateName(templateName, contractType, orgLocation.getId());
		
		if(contractsTemplate != null) {
			if(StringUtils.isNotBlank(templateUUID) && contractsTemplate.getTemplateUUID().equalsIgnoreCase(templateUUID)) {
				result = "false";
			}else {
				result = "true";
			}
		}else {
			result = "false";
		}
	
		return result;
	}
	
	/**
	 * To validate Contract title
	 * 
	 * @param contract
	 *            title
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateContractTitle", method = RequestMethod.POST)
	public @ResponseBody String validateContractTitle(@RequestParam(value = "contractTitle", required = false) String contractTitle, @RequestParam(value = "contractType", required = false) String contractType, @RequestParam(value = "contractId", required = false) Long contractId, HttpServletRequest request) throws Exception
	{
		String result = null;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Contracts contracts = contractsTemplateService.findContractTitleByOrgLocationAndContractType(contractTitle, contractType, orgLocation.getId(), contractId);
		if (null != contracts) {
			result = "true";
		}
		else {
			result = "false";
		}
		return result;
	}
	
	@RequestMapping(value = "/saveExistigContract", method = RequestMethod.POST)
	public @ResponseBody boolean saveExistigContract(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		boolean isContractSaved = false;
		contractsDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Contracts contracts = contractsTemplateService.saveExistigContract(contractsDTO, orgLocation, user, org);
		if (null != contracts) {
			isContractSaved = true;
		}
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsg", "Successfully you have created a Contract");
		return isContractSaved;
	}
	
	@RequestMapping(value = "/getContractByContractUUID", method = RequestMethod.GET)
	public @ResponseBody String getContractByContractUUID(@RequestParam(value = "contractUUID", required = false) String contractUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Contracts contract = contractsTemplateService.getContractByContractUUID(contractUUID);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (contract.getParentContractId() != null) {
			StringBuilder owner = new StringBuilder();
			if (null != contract.getOwnerUserId()) {
				if (null != contract.getOwnerUserId().getLastName()
						&& "" != contract.getOwnerUserId().getLastName()) {
					contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).append(" ").append(contract.getOwnerUserId().getLastName()).toString());
				}
				else {
					contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).toString());
				}
			}
			contract.setIsParentContract(Constants.NO);
		}
		else {
			StringBuilder owner = new StringBuilder();
			for (ContractOwnerHierarchy coh : contract.getOwnerHierarchyList()) {
				if (StringUtils.isNotBlank(coh.getOwnerUserId().getLastName()))
					owner.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
				else
					owner.append(coh.getOwnerUserId().getFirstName()).append(", ");
			}
			contract.setOwnerName(owner.toString().replaceAll("\\s*,\\s*$", ""));
			contract.setIsParentContract(Constants.YES);
		}
		if (null != contract.getOrgRelations()) {
			OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contract.getOrgRelations().getId());
			if (null != orgRelations) {
				if (orgRelations.getResponderLocationId().getId().equals(orgLocation.getId())) {
					contract.setContractWith(orgRelations.getLocationId().getOrgLocationName());
				}
				else if (orgRelations.getLocationId().getId().equals(orgLocation.getId())) {
					contract.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
				}
			}
			contract.setOrgRelationUUID(contract.getOrgRelations().getUUID());
		}
		if (null != contract.getApproverUser()) {
			StringBuilder approver = new StringBuilder();
			if (null != contract.getApproverUser().getLastName()
					&& "" != contract.getApproverUser().getLastName()) {
				contract.setApproverUserName(approver.append(contract.getApproverUser().getFirstName()).append(" ").append(contract.getApproverUser().getLastName()).toString());
			}
			else {
				contract.setApproverUserName(approver.append(contract.getApproverUser().getFirstName()).toString());
			}
		}
		if (contract.getParentContractId() != null) {
			StringBuilder contactUser = new StringBuilder();
			for (ContractOwnerHierarchy coh : contract.getParentContractId().getOwnerHierarchyList()) {
				if (StringUtils.isNotBlank(coh.getOwnerUserId().getLastName()))
					contactUser.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
				else
					contactUser.append(coh.getOwnerUserId().getFirstName()).append(", ");
			}
			contract.setContactUserName(contactUser.toString().replaceAll("\\s*,\\s*$", ""));
		}
		else {
			if (null != contract.getContactUser()) {
				StringBuilder contactUser = new StringBuilder();
				if (null != contract.getContactUser().getLastName()
						&& "" != contract.getContactUser().getLastName()) {
					contract.setContactUserName(contactUser.append(contract.getContactUser().getFirstName()).append(" ").append(contract.getContactUser().getLastName()).toString());
				}
				else {
					contract.setContactUserName(contactUser.append(contract.getContactUser().getFirstName()).toString());
				}
			}
		}
		if (null != contract.getRequirementSubmission()
				&& contract.getRequirementSubmission().getRequirement() != null) {
			contract.setRequirementName(contract.getRequirementSubmission().getRequirement().getJobTitle());
		}
		if (null != contract.getCandidate()) {
			StringBuilder applicant = new StringBuilder();
			if (null != contract.getCandidate().getFirstName()
					&& null != contract.getCandidate().getLastName()) {
				contract.setApplicantName(applicant.append(contract.getCandidate().getFirstName()).append(" ").append(contract.getCandidate().getLastName()).toString());
			}
			else {
				contract.setApplicantName(applicant.append(contract.getCandidate().getFirstName()).toString());
			}
		}
		if (null != contract.getContractsTemplate()) {
			contract.setTemplateName(contract.getContractsTemplate().getTemplateName());
			contract.setTempUUID(contract.getContractsTemplate().getTemplateUUID());
		}
		if (null != contract.getRequirementSubmission()) {
			RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferSubmissionById(contract.getRequirementSubmission().getId());
			if (null != requirementOffers) {
				StringBuilder amount = new StringBuilder();
				contract.setOfferValue(amount.append(requirementOffers.getPaymentType()).append(" ").append(requirementOffers.getAmount()).append(" / ").append(requirementOffers.getPaymentMode()).toString());
			}
		}
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		// SimpleDateFormat formatter =
		// orgLocation.getLocFullFormDateFormatter();
		if (null != contract && null != contract.getStartDate()) {
			String currentTimeStr = Constants.TZ_DF.format(contract.getStartDate());
			String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
			Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
			currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), converteDate);
			contract.setStartDateStr(currentDateInTimezone);
		}
		if (null != contract && null != contract.getEndDate()) {
			String endTimeStr = Constants.TZ_DF.format(contract.getEndDate());
			String endDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), endTimeStr);
			Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
			endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), endconverteDate);
			contract.setEndDateStr(endDateInTimezone);
		}
		if (null != contract && null != contract.getJoinDate()) {
			String endTimeStr = Constants.TZ_DF.format(contract.getJoinDate());
			String endDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), endTimeStr);
			Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
			endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), endconverteDate);
			contract.setJoinedDateStr(endDateInTimezone);
		}
		if (null != contract && null != contract.getCreatedDate()) {
			String currentTimeStr = Constants.TZ_DF.format(contract.getCreatedDate());
			String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
			Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
			currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), converteDate);
			contract.setCreatedDateStr(currentDateInTimezone);
		}
		if (null != contract && null != contract.getUpdatedDate()) {
			String endTimeStr = Constants.TZ_DF.format(contract.getUpdatedDate());
			String endDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), endTimeStr);
			Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
			endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), endconverteDate);
			contract.setUpdatedDateStr(endDateInTimezone);
		}
		User createdBy = userService.getUserByUserCode(contract.getCreatedBy());
		if (createdBy != null) {
			StringBuilder created = new StringBuilder();
			if (null != createdBy.getLastName() && "" != createdBy.getLastName()) {
				contract.setCreatedByName(created.append(createdBy.getFirstName()).append(" ").append(createdBy.getLastName()).toString());
			}
			else {
				contract.setCreatedByName(created.append(createdBy.getFirstName()).toString());
			}
		}
		User updatedBy = userService.getUserByUserCode(contract.getUpdatedBy());
		if (updatedBy != null) {
			StringBuilder updated = new StringBuilder();
			if (null != updatedBy.getLastName() && "" != updatedBy.getLastName()) {
				contract.setUpdatedByName(updated.append(updatedBy.getFirstName()).append(" ").append(updatedBy.getLastName()).toString());
			}
			else {
				contract.setUpdatedByName(updated.append(updatedBy.getFirstName()).toString());
			}
		}
		List<ContractRequestDocuments> contractRequestDocumentsList = new ArrayList<>();
		if (null != contract.getParentContractId()) {
			Contracts parentcontracts = contractsTemplateDao.getContractById(contract.getParentContractId().getId());
			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
		}
		else {
			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
		}
		contract.setContractRequestDocumentsList(contractRequestDocumentsList);
		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_ADD_NEW_CONTRACT);
		List<SourceleadSectionDTO> sections = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(), orgLocation.getId());
		List<CustomFormFieldDTO> formFieldId = new ArrayList<>();
		if (sections != null && !sections.isEmpty()) {
			for (SourceleadSectionDTO sec : sections) {
				for (OrglocationFormFieldsDTO customFields : sec.getCustomFieldsList()) {
					CustomFormFieldDTO custom = new CustomFormFieldDTO();
					custom.setCustomFormFieldId(customFields.getId());
					formFieldId.add(custom);
				}
			}
			if (formFieldId != null && !formFieldId.isEmpty() && contract.getId() != null) {
				Map<Long, String[]> contractData = newContractCustomFieldDataService.getNewContractCustomFieldDataByContractId(orgLocation.getOrg().getId(), formFieldId, contract.getId());
				Map<Long, String> dataMap = new HashMap<>();
				if (contractData != null && !contractData.isEmpty()) {
					for (Entry<Long, String[]> contractCustomData : contractData.entrySet()) {
						String labelName = contractCustomData.getValue()[0];
						String labelData = contractCustomData.getValue()[1];
						Long id = contractCustomData.getKey();
						StringBuilder keyValue = new StringBuilder();
						keyValue.append(labelName).append("$").append(labelData);
						dataMap.put(id, keyValue.toString());
						contract.setNewContractCustomFieldlabelNameAndValue(dataMap);
					}
				}
			}
		}
		return commonUtil.toJSON(contract);
	}
	
	@RequestMapping(value = "/getContractStageCounts", method = RequestMethod.GET)
	public @ResponseBody String getContractStageCounts(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Contracts contractsCount = contractsTemplateService.getAllContractsCountByLocationIdAndStatus(orgLocation, "", "", "", "", user, userRoleName);
		return commonUtil.toJSON(contractsCount);
	}
	
	@RequestMapping(value = "/getDocsByContractUUID", method = RequestMethod.GET)
	public @ResponseBody String getDocsByContractUUID(@RequestParam(value = "contractUUID", required = false) String contractUUID, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Contracts contract = contractsTemplateService.getContractByContractUUID(contractUUID);
		List<ContractRequestDocuments> contractRequestDocumentsList = new ArrayList<>();
		if (null != contract.getParentContractId()) {
			Contracts parentcontracts = contractsTemplateDao.getContractById(contract.getParentContractId().getId());
			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
		}
		else {
			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
		}
		return commonUtil.toJSON(contractRequestDocumentsList);
	}
	
	@RequestMapping(value = "/saveDocContracts", method = RequestMethod.POST)
	public @ResponseBody String saveDocContracts(ContractDocumentsDTO contractDocumentsDTO, @RequestParam(value = "json", required = false) String json, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request) throws Exception
	{
		// String json1 = "{" + json + "}";
		// JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		for (ContractReqDocsDTO reqDoc : contractDocumentsDTO.getDocfiles()) {
			ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(reqDoc.getDocId()));
			if (contractDocumentsDTO.getFileInfo() != null
					&& contractDocumentsDTO.getFileInfo().size() > 0) {
				for (FileInfo fileInfo : contractDocumentsDTO.getFileInfo()) {
					if (reqDoc.getDocFileName() != null
							&& reqDoc.getDocFileName().equalsIgnoreCase(fileInfo.getFileName())) {
						contractRequestDocuments.setDocFileName(fileInfo.getFileName());
						contractRequestDocuments.setFileType(fileInfo.getContentType());
						contractRequestDocuments.setFileURL(fileInfo.getFileURL());
						contractRequestDocuments.setCreatedDate(new Date());
						contractRequestDocuments.setUpdatedDate(new Date());
						contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
						contractRequestDocuments.setCreatedBy(user.getUserCode());
						contractRequestDocuments.setUpdatedBy(user.getUserCode());
						contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
						if (contractRequestDocuments.getContractId().getOrgLocationId().getId().longValue() != orgLocations.getId().longValue()) {
							contractsTemplateService.documentsReceivedMailToOwner(contractRequestDocuments.getContractId(), user, org);
						}
						if (fileInfo != null) {
							FileUploadUtil.moveFile(staticPath, fileInfo, "Requested_Documents");
						}
					}
				}
			}
		}
		/*
		 * final MultipartHttpServletRequest multiRequest =
		 * (MultipartHttpServletRequest) request; final Map files =
		 * multiRequest.getFileMap(); int maxCount =
		 * Integer.parseInt(jsonObj.getString("maxDocCount")); for (long c = 1;
		 * c <= maxCount; c++) { if(jsonObj.has("docId"+c) &&
		 * !jsonObj.getString("docId"+c).isEmpty()){ ContractRequestDocuments
		 * contractRequestDocuments =
		 * contractRequestDocumentsService.getContractRequestDocumentsById(Long.
		 * parseLong(jsonObj.getString("docId"+c))); MultipartFile multiFile =
		 * (MultipartFile)files.get("uploadBtn"+jsonObj.getString("docId"+c));
		 * if(multiFile != null && multiFile.getSize()>0){
		 * contractRequestDocuments.setDocFile(multiFile.getBytes());
		 * contractRequestDocuments.setFileType(multiFile.getContentType());
		 * contractRequestDocuments.setCreatedDate(new Date());
		 * contractRequestDocuments.setUpdatedDate(new Date());
		 * contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
		 * contractRequestDocuments.setCreatedBy(user.getUserCode());
		 * contractRequestDocuments.setUpdatedBy(user.getUserCode());
		 * contractRequestDocumentsService.saveContractRequestDocuments(
		 * contractRequestDocuments);
		 * if(contractRequestDocuments.getContractId().getOrgLocationId().getId(
		 * ).longValue() != orgLocations.getId().longValue()){
		 * contractsTemplateService.documentsReceivedMailToOwner(
		 * contractRequestDocuments.getContractId(),user,org); } } } }
		 */
		return "success";
	}
	
	@RequestMapping(value = "/terminateDocument", method = RequestMethod.POST)
	public @ResponseBody String terminateDocument(@RequestParam(value = "docuId", required = false) String docuId, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(docuId));
		contractRequestDocuments.setStatus(Constants.MEMBER_IN_ACTIVE);
		contractRequestDocuments.setUpdatedDate(new Date());
		contractRequestDocuments.setUpdatedBy(user.getUserCode());
		contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
		return "success";
	}
	
	@RequestMapping(value = "/updateDocument", method = RequestMethod.POST)
	public @ResponseBody String updateDocument(@RequestParam(value = "docuId", required = false) String docuId, HttpServletRequest request) throws Exception
	{
		ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(Long.parseLong(docuId));
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		// contractRequestDocuments.setDocFile(new byte[0]);
		contractRequestDocuments.setFileType(null);
		contractRequestDocuments.setDocFileName(null);
		contractRequestDocuments.setFileURL(null);
		contractRequestDocuments.setUpdatedDate(new Date());
		contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
		contractRequestDocuments.setUpdatedBy(user.getUserCode());
		contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
		return "success";
	}
	
	/*
	 * @RequestMapping(value = "/downloadContractDocument",method =
	 * RequestMethod.GET) public void
	 * downloadContractDocument(@RequestParam(value="contractUUID",
	 * required=false) String contractUUID, HttpServletResponse response) throws
	 * Exception { ContractRequestDocuments contractRequestDocuments =
	 * contractRequestDocumentsService.getContractRequestDocumentsByContractUUID
	 * (contractUUID); if(null != contractRequestDocuments){
	 * response.setContentType("application/octet-stream"); StringBuilder
	 * fileName = new StringBuilder(); response.setHeader("Content-Disposition",
	 * fileName.append("attachment; filename=\"").append(
	 * contractRequestDocuments.getFileName()).append("\"").toString());
	 * 
	 * InputStream inputStream = new
	 * ByteArrayInputStream(contractRequestDocuments.getDocFile()); try {
	 * OutputStream outStream = response.getOutputStream();
	 * 
	 * byte[] buffer = new byte[4096]; int bytesRead = -1;
	 * 
	 * while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outStream.write(buffer, 0, bytesRead); }
	 * 
	 * inputStream.close(); outStream.close();
	 * 
	 * } catch (FileNotFoundException e) {
	 * logger.error(ExceptionUtils.getFullStackTrace(e)); } }
	 * 
	 * }
	 */
	@RequestMapping(value = "/updateContractEndDate", method = RequestMethod.POST)
	public @ResponseBody String updateContractEndDate(@RequestParam(value = "contractId", required = false) String contractId, @RequestParam(value = "endDate", required = false) String endDate, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Contracts contracts = contractsTemplateService.getContractById(Long.parseLong(contractId));
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		// SimpleDateFormat
		// dateFormat=orgLocation.getLocFullFormDateFormatter();
		if (!"".equalsIgnoreCase(endDate) && endDate != null) {
			Date endTimeStr = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), endDate);
			String endDateStr = Constants.TZ_DF.format(endTimeStr);
			String endTimeUTC = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDateStr);
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
			String fromStage = "";
			ContractsHistory childContractsHistory = new ContractsHistory();
			if (contracts.getTemplateSubmissionStatus().equalsIgnoreCase(Constants.EXPIRED)) {
				ContractsHistory contractsHistory = contractsTemplateService.getAllContractsHistoryByContractId(contracts.getId());
				fromStage = contractsHistory.getFromStage();
				Date date = new Date();
				String currentTimeStr = TZ_DF_TS.format(date);
				Date curDate = TZ_DF_TS.parse(currentTimeStr);
				if (convertedEndDate.after(curDate) || convertedEndDate.equals(curDate)) {
					childContractsHistory.setFromStage(contracts.getTemplateSubmissionStatus());
					childContractsHistory.setContractsId(contracts);
					contracts.setTemplateSubmissionStatus(fromStage);
					childContractsHistory.setToStage(fromStage);
					childContractsHistory.setCreatedDate(new Date());
					childContractsHistory.setUpdatedDate(new Date());
					contractsTemplateDao.saveContractsHistory(childContractsHistory);
				}
			}
			// updating parent contract end date if get the contract i is child
			// contract
			if (contracts.getParentContractId() != null) {
				if (null != contracts.getParentContractId().getId()) {
					Contracts parentContracts = contractsTemplateService.getContractById(contracts.getParentContractId().getId());
					parentContracts.setEndDate(convertedEndDate);
					parentContracts.setUpdatedDate(new Date());
					parentContracts.setUpdatedBy(user.getUserCode());
					contractsTemplateDao.saveNewContract(parentContracts);
				}
			}
			// updating child contract end date if get the parent contract
			if (null == contracts.getParentContractId()) {
				Contracts childContarct = contractsTemplateService.getChildContractById(contracts.getId());
				if (null != childContarct) {
					childContarct.setEndDate(convertedEndDate);
					childContarct.setUpdatedDate(new Date());
					childContarct.setUpdatedBy(user.getUserCode());
					contractsTemplateDao.saveNewContract(childContarct);
				}
			}
			// updating contract end date
			Date previousEndDate = contracts.getEndDate();
			contracts.setEndDate(convertedEndDate);
			contracts.setUpdatedDate(new Date());
			contracts.setUpdatedBy(user.getUserCode());
			contractsTemplateDao.saveNewContract(contracts);
			if (contracts.getContractType().equalsIgnoreCase(Constants.CLIENT_MSA)
					|| contracts.getContractType().equalsIgnoreCase(Constants.VENDOR_MSA)) {
				contractsTemplateService.sendMSAEndDateModifiedMail(contracts, orgLocation, previousEndDate, user);
			}
			if (contracts.getContractType().equalsIgnoreCase(Constants.Client_SOW)
					|| contracts.getContractType().equalsIgnoreCase(Constants.Vendor_SOW)) {
				contractsTemplateService.sendSOWEndDateModifiedMail(contracts, orgLocation, user);
			}
		}
		return "success";
	}
	
	@RequestMapping(value = "/refreshClientList", method = RequestMethod.POST)
	public @ResponseBody List<OrgRelations> refreshClientList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}
			if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		return clientList;
	}
	
	@RequestMapping(value = "/refreshVendorList", method = RequestMethod.GET)
	public @ResponseBody List<OrgRelations> refreshVendorList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}
			if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		return orgRelationsList;
	}
	
	@RequestMapping(value = "/rest/getContractRequestDocumentsById")
	public ContractRequestDocuments getContractRequestDocumentsById(@RequestParam(value = "docId", required = true) Long docId) throws Exception
	{
		ContractRequestDocuments contractRequestDocuments = contractRequestDocumentsService.getContractRequestDocumentsById(docId);
		return contractRequestDocuments;
	}
	
	@RequestMapping(value = "/rest/getContractTemplateByTemplateUUID")
	public ContractsTemplate getContractTemplateByTemplateUUID(@RequestParam(value = "uuid", required = true) String uuid) throws Exception
	{
		ContractsTemplate contractsTemplate = contractsTemplateService.getContractTemplateByTemplateUUID(uuid);
		return contractsTemplate;
	}
	
	@RequestMapping(value = "/getAssociatesListForSow")
	public List<Candidate> getAssociatesListForSow(@RequestParam(value = "locationId", required = true) Long locationId, @RequestParam(value = "sowType", required = true) String sowType) throws Exception
	{
		List<Candidate> associateList = candidateService.getAssociatesListForSow(locationId, sowType);
		return associateList;
	}
	
	@RequestMapping(value = "/sendContractFromOwnertoReceiver")
	public String sendContractFromOwnertoReceiver(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
		boolean flag = contractsTemplateService.sendContractFromOwnertoReceiver(contractsDTO, loginUser, loginOrgLocation, roleName);
		return "success";
	}
	
	@RequestMapping(value = "/validateAlreadyGeneratedContract")
	public List<Contracts> validateAlreadyGeneratedContract(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return contractsTemplateService.validateAlreadyGeneratedContract(contractsDTO, loginOrgLocation);
	}
	
	@RequestMapping(value = "/reviseContract")
	public boolean reviseContract(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		return contractsTemplateService.reviseContract(contractsDTO, loginOrgLocation, loginUser);
	}
	
	@RequestMapping(value = "/updateContractSigner")
	public Response<Contracts> updateContractSigner(ContractsDTO contractsDTO, HttpServletRequest request) throws Exception
	{
		Response<Contracts> response = new Response<>();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (loginOrgLocation != null) {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Contracts contract = contractsTemplateService.updateContractSigner(contractsDTO, loginOrgLocation, loginUser);
			if (contract != null) {
				response.setResponse(Result.SUCCESS.name());
				response.setObject(contract);
			}
		}
		else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
		}
		return response;
	}
	
	@RequestMapping(value = "/getIndividualCounts", method = RequestMethod.GET)
	public @ResponseBody Contracts getIndividualCounts(@RequestParam(value = "contractStatus", required = false) String contractStatus,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Contracts indivisualCount = contractsTemplateService.getAllVendorClientAndApplicantCountByLocationIdAndStatus(orgLocation, "", "", "", contractStatus, user, userRoleName);
		return indivisualCount;
	}
}
