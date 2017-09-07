package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dto.ContractDocumentsDTO;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SignatureRequestUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.web.LoginSessionController;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ContractsTemplateServiceImpl implements ContractsTemplateService{

	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	

/*	@Autowired
	ContractsTemplateService contractsTemplateService;*/
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private ContractRequestDocumentsService contractRequestDocumentsService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private SignatureRequestUtil signatureRequestUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Override
	public ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		
		
		ContractsTemplate contractsTemplate = null;
		if(jsonObj.has("templateId")&& null != jsonObj.getString("templateId") && !"".equalsIgnoreCase(jsonObj.getString("templateId"))){
//			if(){
				contractsTemplate = contractsTemplateDao.getContractTemplateById(Long.parseLong(jsonObj.getString("templateId")));
//			}else{
//				contractsTemplate = new ContractsTemplate();
//			}
		}else{
			contractsTemplate = new ContractsTemplate();
		}
		contractsTemplate.setOrgLocationId(orgLocation);
		
		contractsTemplate.setTemplateName(jsonObj.getString("templateName"));
		
		contractsTemplate.setTemplateType(jsonObj.getString("contractType"));
		
		if(jsonObj.has("letterHead") && !isNullOrEmpty(jsonObj.getString("letterHead"))){
			contractsTemplate.setLetterHead(jsonObj.getString("letterHead"));
		}
		
		if(jsonObj.has("description") && !isNullOrEmpty(jsonObj.getString("description"))){
			contractsTemplate.setDescription(jsonObj.getString("description").trim());
		}
		
		if(jsonObj.has("templateContent") && !isNullOrEmpty(jsonObj.getString("templateContent"))){
			String content = jsonObj.getString("templateContent");
		//	String header = "<div style='text-align: center;'>"+jsonObj.getString("templateName")+"<br></div><br>";
		//	String totalBody = header + content;
			contractsTemplate.setTemplateContent(content);
		}
		contractsTemplate.setCreatedDate(new Date());
		contractsTemplate.setCreatedBy(user.getUserCode());
		contractsTemplate.setUpdatedDate(new Date());
		contractsTemplate.setUpdatedBy(user.getUserCode());
		// for active templates status is 0
		contractsTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		contractsTemplate.setTemplateStatus("0");
		return contractsTemplateDao.saveContractTemplate(contractsTemplate);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public String generatePDFContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		String k = "<h5><b>Business Intelli Service aggrement</b></h5><br>THIS PROFESSIONAL SERVICES AGREEMENT (this 'Agreement') is "
				+ " Contractor which may render services to BIS or BIS's clients in the future. Contractor shall<br><div style='text-align: center;'><span style='font-weight: bold;'>Business Intelli Solutions Inc.</span><br>--------------------------------------------------------------------------------------------------------------------------------------------<br></div><div style='text-align: center;'><span style='color: rgb(61, 133, 198);'>3535 Victory Group Way, Building 4, Frisco, TX 75034.<br>PH: 214 295 5535 FAX: 972 559 3641<br>Email: info@businessintelli.com, www.businessintelli.com</span></div>";
    	    OutputStream file = new FileOutputStream(new File("C:\\Users\\BIS94\\Desktop\\Itext\\TestSample.pdf"));
    	    Document document = new Document();
    	    PdfWriter.getInstance(document, file);
    	    document.open();
    	    HTMLWorker htmlWorker = new HTMLWorker(document);
    	    htmlWorker.parse(new StringReader(k));
    	    document.close();
    	    file.close();
			return "Success";		
	}

	@Override
	public ContractsTemplate getContractTemplateById(Long contractTemplateId) throws Exception {
		return contractsTemplateDao.getContractTemplateById(contractTemplateId);
	}

	@Override
	public List<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByOrgLocationid(orgLocation);
	}

	@Override
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByContractTypeLandLocationId(orgLocationId,contractType);
	}

	@Override
	public Contracts saveNewContract(ContractsDTO contractsDTO,JSONObject jsonObj, OrgLocations orgLocation, String saveType,User user,Org org,String contractType, MultipartFile multiFile) throws Exception
	{
		Contracts contracts;
		Contracts vendorContracts = null;
		String contractId="";
		// date format
	//	SimpleDateFormat dateFormat = orgLocation.getLocFullFormDateFormatter();
		if(StringUtils.isNotBlank(contractsDTO.getContrId())){
			contractId = contractsDTO.getContrId();
			contracts = contractsTemplateDao.getContractById(Long.parseLong(contractId));
			if(null != contracts && null != contracts.getParentContractId()){
				vendorContracts = contractsTemplateDao.getChildContractById(contracts.getParentContractId().getId());
			}else if(Constants.PENDING.equalsIgnoreCase(saveType)){
				vendorContracts = new Contracts();
			}
			if(null != user){
				contracts.setUpdatedBy(user.getUserCode());
			}
			contracts.setUpdatedDate(new Date());
		}else{
			contracts = new Contracts();
			vendorContracts = new Contracts();
			contracts.setCreatedDate(new Date());
			if(null != user){
				contracts.setCreatedBy(user.getUserCode());
				contracts.setUpdatedBy(user.getUserCode());
			}
			contracts.setUpdatedDate(new Date());
		}
		
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		if(Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType())){
			contracts.setContractType("Vendor "+contractsDTO.getContractType());
		}
		else{
			contracts.setContractType(contractsDTO.getContractType());
        }
		ContractsTemplate contractsTemplate = null;
		if(StringUtils.isNotBlank(contractsDTO.getTemplate())){
			contractsTemplate = contractsTemplateDao.getContractTemplateById(Long.parseLong(contractsDTO.getTemplate()));
			if(null != contractsTemplate){
				contracts.setContractsTemplate(contractsTemplate);
				if(null != vendorContracts){
					vendorContracts.setContractsTemplate(contractsTemplate);
				}
			}
		}
		
		
		if (contractsDTO.getFileInfo() != null && contractsDTO.getFileInfo().size()>0) {
			FileInfo fileInfo = contractsDTO.getFileInfo().get(0);
			contracts.setFileName(fileInfo.getFileName());
			contracts.setFileType(fileInfo.getContentType());
			contracts.setFileURL(fileInfo.getFileURL());
			if(fileInfo != null){
				FileUploadUtil.moveFile(contractsDTO.getStaticPath(), fileInfo, "ContractDocs");
			}
		}
		
		/*if(multiFile != null && multiFile.getBytes() != null && !"".equalsIgnoreCase(multiFile.getOriginalFilename())){
			contracts.setDocFile(multiFile.getBytes());
			contracts.setFileName(multiFile.getOriginalFilename());
			contracts.setFileType(multiFile.getContentType());
		 }*/
		
		if(null != vendorContracts){
			if(null != contractType && !"".equalsIgnoreCase(contractType) && "custom".equalsIgnoreCase(contractType)){
				contracts.setTypeOfContract(Constants.CUSTOM_TYPE);
				vendorContracts.setTypeOfContract(Constants.CUSTOM_TYPE);
			}else{
				contracts.setTypeOfContract(Constants.NEW_TYPE);
				vendorContracts.setTypeOfContract(Constants.NEW_TYPE);
			}
		}
		/*if(null != vendorContracts){
			vendorContracts.setTypeOfContract(Constants.NEW_TYPE);
		}*/
		
		if(StringUtils.isNotBlank(contractsDTO.getSubmissionOffersList())){
			RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(contractsDTO.getSubmissionOffersList()));
			contracts.setRequirementSubmission(rs);
			contracts.setCandidate(rs.getCandidate());			
		}
		
		if(Constants.Client_SOW.equals(contractsDTO.getContractType()) || Constants.Vendor_SOW.equals(contractsDTO.getContractType())){
			if(StringUtils.isNotBlank(contractsDTO.getHoursPerWeek())){
				contracts.setHoursPerWeek(contractsDTO.getHoursPerWeek());
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getOverTimeHours())){
			contracts.setOverTimeHours(contractsDTO.getOverTimeHours());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTotalHoursEstimated())){
			contracts.setTotalHours(contractsDTO.getTotalHoursEstimated());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getOvertimeRate())){
			contracts.setOvertimeRate(contractsDTO.getOvertimeRate());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getOvertimeRateType())){
			contracts.setOvertimeRateType(contractsDTO.getOvertimeRateType());
		}
		
		contracts.setContractTitle(contractsDTO.getContractTitle().trim());
		
		if(null != vendorContracts){
			vendorContracts.setContractTitle(contractsDTO.getContractTitle().trim());
			vendorContracts.setContractsTemplate(contractsTemplate);
		}
		
		User ownerUser = null;
		if(StringUtils.isNotBlank(contractsDTO.getOwner())){
			ownerUser = userService.getUserById(Long.parseLong(contractsDTO.getOwner()));
			contracts.setOwnerUserId(ownerUser);
		}
		
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotBlank(contractsDTO.getStartDate())){
			Date startTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getStartDate());
			String currentTimeStr = Constants.TZ_DF.format(startTime);  
			String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setStartDate(convertedDate);
			if(null != vendorContracts){
				vendorContracts.setStartDate(convertedDate);
			}
		}
		
		//String currentTimeStr = Constants.TZ_DF.format(jsonObj.getString("startDate"));  
		
		if(StringUtils.isNotBlank(contractsDTO.getEndDate())){
			Date endTimeStr = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getEndDate());  
			String endDateStr = Constants.TZ_DF.format(endTimeStr);  
			String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDateStr);
			Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
			contracts.setEndDate(convertedEndDate);
			if(null != vendorContracts){
				vendorContracts.setEndDate(convertedEndDate);
			}
		}
		User approverUser = null;
		User vendorContactsUser = null;
		if(StringUtils.isNotBlank(contractsDTO.getApprover())){
			 approverUser = userService.getUserById(Long.parseLong(contractsDTO.getApprover()));
			contracts.setApproverUser(approverUser);
		}
		contracts.setOrgLocationId(orgLocation);
		if((Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType()))){
			contracts.setInitiatorType(Constants.CLIENT);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(contractsDTO.getOrgRelationId()));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(contractsDTO.getVendorContact()));
			contracts.setContactUser(vendorContactsUser);
		}
		if(Constants.CLIENT_MSA.equalsIgnoreCase(contractsDTO.getContractType()) ||  Constants.Client_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
			contracts.setInitiatorType(Constants.VENDOR);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(contractsDTO.getClientOrgRelationId()));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(contractsDTO.getClientContact()));
			contracts.setContactUser(vendorContactsUser);
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTaxPercentageReward())){
			contracts.setBillingAmount(contractsDTO.getTaxPercentageReward());
			if(null != vendorContracts){
				vendorContracts.setBillingAmount(contractsDTO.getTaxPercentageReward());
			}
				contracts.setBillingPer("Percentage");
				if(null != vendorContracts){
					vendorContracts.setBillingPer("Percentage");
				}
		}
		
		if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())){
			if(StringUtils.isNotBlank(contractsDTO.getCandiFlatReward())){
				contracts.setBillingAmount(contractsDTO.getCandiFlatReward());
				if(null != vendorContracts){
					vendorContracts.setBillingAmount(contractsDTO.getCandiFlatReward());
				}
				//jsonObj.getString("flatRateType")
				if(StringUtils.isNotBlank(contractsDTO.getTaxFlatRateType()) ){
					contracts.setBillingPer(contractsDTO.getTaxFlatRateType());
					if(null != vendorContracts){
						vendorContracts.setBillingPer(contractsDTO.getTaxFlatRateType());
					}
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getCandiRateType()) ){
				contracts.setBillingRateType(contractsDTO.getCandiRateType());
				if(null != vendorContracts){
					vendorContracts.setBillingRateType(contractsDTO.getCandiRateType());
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getCandiRatePer())){
				contracts.setBillingRatePer(contractsDTO.getCandiRatePer());
				if(null != vendorContracts){
					vendorContracts.setBillingRatePer(contractsDTO.getCandiRatePer());
				}
			}
		}
		else{
			if(StringUtils.isNotBlank(contractsDTO.getFlatReward())){
				contracts.setBillingAmount(contractsDTO.getFlatReward());
				if(null != vendorContracts){
					vendorContracts.setBillingAmount(contractsDTO.getFlatReward());
				}
				//jsonObj.getString("flatRateType")
				if(StringUtils.isNotBlank(contractsDTO.getTaxFlatRateType()) ){
					contracts.setBillingPer(contractsDTO.getTaxFlatRateType());
					if(null != vendorContracts){
						vendorContracts.setBillingPer(contractsDTO.getTaxFlatRateType());
					}
				}
			}
			
			
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRateType())){
				contracts.setBillingRateType(contractsDTO.getBillingRateType());
				if(null != vendorContracts){
					vendorContracts.setBillingRateType(contractsDTO.getBillingRateType());
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRatePer()) ){
				contracts.setBillingRatePer(contractsDTO.getBillingRatePer());
				if(null != vendorContracts){
					vendorContracts.setBillingRatePer(contractsDTO.getBillingRatePer());
				}
			}
		}
		if(StringUtils.isNotBlank(contractsDTO.getInvoiceFrequency())){
			contracts.setInvoiceFrequency(contractsDTO.getInvoiceFrequency());
			if(null != vendorContracts){
				vendorContracts.setInvoiceFrequency(contractsDTO.getInvoiceFrequency());
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getPaymentFrequency())){
			contracts.setPaymentTerm(contractsDTO.getPaymentFrequency());
			if(null != vendorContracts){
				vendorContracts.setPaymentTerm(contractsDTO.getPaymentFrequency());
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTaxPercentageReward())){
			contracts.setTax(contractsDTO.getTaxPercentageReward());
			if(null != vendorContracts){
				vendorContracts.setTax(contractsDTO.getTaxPercentageReward());
			}
			contracts.setTaxType("Percentage");
			if(null != vendorContracts){
				vendorContracts.setTaxType("Percentage");
			}
		}
		
		if (StringUtils.isNotBlank(contractsDTO.getWorkLocation())) {
			contracts.setWorkLocation(contractsDTO.getWorkLocation());
		}
		
		if (StringUtils.isNotBlank(contractsDTO.getJoinDate())) {
			Date joinTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getJoinDate());
			String currentTimeStr = Constants.TZ_DF.format(joinTime);
			String startTimeUTC = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setJoinDate(convertedDate);
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTaxFlatReward()) ){
			contracts.setTax(contractsDTO.getTaxFlatReward());
			if(null != vendorContracts){
				vendorContracts.setTax(contractsDTO.getTaxFlatReward());
			}
			contracts.setTaxType(contractsDTO.getTaxFlatRateType());
			if(null != vendorContracts){
				vendorContracts.setTaxType(contractsDTO.getTaxFlatRateType());
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getDescription())){
			contracts.setDescription(contractsDTO.getDescription().trim());
			if(null != vendorContracts){
				vendorContracts.setDescription(contractsDTO.getDescription().trim());
			}
		}
		if(StringUtils.isNotBlank(contractsDTO.getJurisdiction())){
			contracts.setStateJurisdiction(contractsDTO.getJurisdiction());
		}
		contracts.setStatus("0");
		if(null != vendorContracts){
			vendorContracts.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_SIGNED);
			vendorContracts.setStatus("0");
		}
		
		if(null != approverUser &&! Constants.DRAFT.equalsIgnoreCase(saveType)){
			contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
			sendMailToContractApprover(user,approverUser,contracts,orgLocation);
		}
		else if(Constants.DRAFT.equalsIgnoreCase(saveType)){
			contracts.setTemplateSubmissionStatus(Constants.DRAFT);
		}
		else{
			contracts.setTemplateSubmissionStatus(saveType);
		}
		
		contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		if(null != vendorContracts)
		{
		 vendorContracts.setContractUUID(contracts.getContractUUID());
		}
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		
		if (contractsDTO.getDocument() != null && contractsDTO.getDocument().size() > 0) {
			for (ContractDocumentsDTO doc : contractsDTO.getDocument()) {
				if (doc.getDocumentName() != null) {
					ContractRequestDocuments contractRequestDocuments = new ContractRequestDocuments();
					if (StringUtils.isNotBlank(doc.getDocumentName())) {
						contractRequestDocuments.setFileName(doc.getDocumentName());
					}
					if (StringUtils.isNotBlank(doc.getDescription())) {
						contractRequestDocuments.setDescription(doc.getDescription());
					}
					if (StringUtils.isNotBlank(doc.getCandiDoc())) {
						contractRequestDocuments.setDocType(doc.getCandiDoc());
					}
					contractRequestDocuments.setContractId(savedContracts);
					contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
					contractRequestDocuments.setCreatedDate(new Date());
					if (null != user) {
						contractRequestDocuments.setCreatedBy(user.getUserCode());
						contractRequestDocuments.setUpdatedBy(user.getUserCode());
					}
					contractRequestDocuments.setUpdatedDate(new Date());
					contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
				}
			}
		}
		/*if(jsonObj.has("maxTaxRowNum") && null != jsonObj.getString("maxTaxRowNum") && !"".equals(jsonObj.getString("maxTaxRowNum"))){		
		int maxCount = Integer.parseInt(jsonObj.getString("maxTaxRowNum"));
		for (long c = 1; c < maxCount; c++) {
				ContractRequestDocuments contractRequestDocuments = new ContractRequestDocuments();
				if(jsonObj.has(c+"documentName") && !jsonObj.getString(c+"documentName").isEmpty()){
					contractRequestDocuments.setFileName(jsonObj.getString(c+"documentName"));
				
				if(jsonObj.has(c+"description") && !jsonObj.getString(c+"description").isEmpty()){
					contractRequestDocuments.setDescription(jsonObj.getString(c+"description"));
				}
				if (jsonObj.has(c + "candiDoc")
						&& !jsonObj.getString(c + "candiDoc").isEmpty()) {
					contractRequestDocuments.setDocType(jsonObj.getString(c + "candiDoc"));
					
				}
				contractRequestDocuments.setContractId(savedContracts);
				contractRequestDocuments.setStatus(Constants.MEMBER_ACTIVE);
				contractRequestDocuments.setCreatedDate(new Date());
				if(null != user){
					contractRequestDocuments.setCreatedBy(user.getUserCode());
					contractRequestDocuments.setUpdatedBy(user.getUserCode());
				}
				contractRequestDocuments.setUpdatedDate(new Date());
				contractRequestDocumentsService.saveContractRequestDocuments(contractRequestDocuments);
				}
			}
		}*/
		
		ContractsHistory contractsHistory = contractsTemplateDao.getContractsHistoryByContractId(contracts.getId());
		if(contractsHistory !=null){
			if(!contractsHistory.getToStage().equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
				ContractsHistory ch = new ContractsHistory();
				ch.setContractsId(savedContracts);
				ch.setFromStage(contractsHistory.getToStage());
				ch.setToStage(savedContracts.getTemplateSubmissionStatus());
				ch.setCreatedDate(new Date());
				if(null != user){
					ch.setCreatedBy(user.getUserCode());
					ch.setUpdatedBy(user.getUserCode());
				}
				ch.setUpdatedDate(new Date());
				contractsTemplateDao.saveContractsHistory(ch);
			}
		}
		if("".equalsIgnoreCase(contractId)){
				ContractsHistory ch = new ContractsHistory();
				ch.setContractsId(savedContracts);
				ch.setFromStage("NEW");
				ch.setToStage(savedContracts.getTemplateSubmissionStatus());
				ch.setCreatedDate(new Date());
				if(null != user){
					ch.setCreatedBy(user.getUserCode());
					ch.setUpdatedBy(user.getUserCode());
				}
				ch.setUpdatedDate(new Date());
				contractsTemplateDao.saveContractsHistory(ch);
		}
		
		if(null != user && null != approverUser &&! Constants.DRAFT.equalsIgnoreCase(saveType)){
			if(!StringUtils.isNotBlank(contractsDTO.getApprover()) && null != ownerUser && ownerUser.getId().longValue() != user.getId().longValue()){
				sendMailToOwner(savedContracts,user,org,orgLocation);
			}
		}
		
		if(null == approverUser && !Constants.DRAFT.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())){
			if(null != vendorContracts){
				vendorContracts.setParentContractId(savedContracts);
				if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())){
					if(null != vendorContracts){
						vendorContracts.setContractType(contractsDTO.getContractType());	
						vendorContracts.setContractOrgLocationId(orgLocation);
						vendorContracts.setContactUser(savedContracts.getOwnerUserId());
					}
				}
			}
			
			if(Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType())){
				if(Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType())){
					if(null != vendorContracts){
						vendorContracts.setContractType(Constants.CLIENT_MSA);	
					}
				}else if(Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
					if(null != vendorContracts){
						vendorContracts.setContractType(Constants.Client_SOW);
					}
				}
				else if(Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType())){
					if(null != vendorContracts){
						vendorContracts.setContractType(Constants.CLIENT_NDA);
					}
				}
				else if(Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType())){
					if(null != vendorContracts){
						vendorContracts.setContractType(Constants.CLIENT_NSA);
					}
				}
				if(null != vendorContracts){
					vendorContracts.setInitiatorType(Constants.CLIENT);
					vendorContracts.setContractOrgLocationId(orgLocation);
					vendorContracts.setParentContractId(savedContracts);
					OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(contractsDTO.getOrgRelationId()));
					vendorContracts.setOrgRelations(orgRelations);
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getLocationId());
					}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
					}
					User vendorUser = userService.getUserById(Long.parseLong(contractsDTO.getVendorContact()));
					vendorContracts.setOwnerUserId(vendorUser);
					vendorContracts.setContactUser(ownerUser);
				}
			}
			
			if(Constants.CLIENT_MSA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.Client_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
				if(null != vendorContracts){
					if(Constants.CLIENT_MSA.equalsIgnoreCase(contractsDTO.getContractType())){
						vendorContracts.setContractType(Constants.VENDOR_MSA);	
					}else{
						vendorContracts.setContractType(Constants.Vendor_SOW);
					}
					
					vendorContracts.setInitiatorType(Constants.VENDOR);
					vendorContracts.setParentContractId(savedContracts);
					vendorContracts.setContractOrgLocationId(orgLocation);
					OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(contractsDTO.getClientOrgRelationId()));
					vendorContracts.setOrgRelations(orgRelations);
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getLocationId());
					}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
					}
					User vendorUser = userService.getUserById(Long.parseLong(contractsDTO.getClientContact()));
					vendorContracts.setOwnerUserId(vendorUser);
					vendorContracts.setContactUser(ownerUser);
				}
				
			}
			if(null != vendorContracts){
				vendorContracts.setContractUUID(contracts.getContractUUID());
				Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
				if("".equalsIgnoreCase(contractId)){
					ContractsHistory childContractsHistory = new ContractsHistory();
					childContractsHistory.setContractsId(savedChildContracts);
					childContractsHistory.setFromStage("NEW");
					childContractsHistory.setToStage(savedChildContracts.getTemplateSubmissionStatus());
					childContractsHistory.setCreatedDate(new Date());
					if(null != user){
						childContractsHistory.setCreatedBy(user.getUserCode());
						childContractsHistory.setUpdatedBy(user.getUserCode());
					}
					childContractsHistory.setUpdatedDate(new Date());
					contractsTemplateDao.saveContractsHistory(childContractsHistory);
				}
			}
			
			
			//  if(null != vendorContactsUser && null == approverUser && !Constants.DRAFT.equalsIgnoreCase(saveType)){
			 if(null == approverUser && !Constants.DRAFT.equalsIgnoreCase(saveType)){
				  Contracts con = null;
				  if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())){
					  if (null != contractType && !"".equalsIgnoreCase(contractType)
								&& "custom".equalsIgnoreCase(contractType)) {
							con = signatureRequestUtil.sendDocumentForSignatureRequestForCustom(new User(), contracts, orgLocation, user, savedContracts, org,contracts.getRequirementSubmission());
						}
						else {
							con = signatureRequestUtil.sendDocumentForSignatureRequest(new User(), contractsTemplate, orgLocation, user, savedContracts, org,contracts.getRequirementSubmission());
						}
				  }
				  else{
						 if(null != contractType && !"".equalsIgnoreCase(contractType) && "custom".equalsIgnoreCase(contractType)){
							con = signatureRequestUtil.sendDocumentForSignatureRequestForCustom(vendorContactsUser, contracts, orgLocation, user, savedContracts, org,new RequirementSubmission());				 
						 }else{
							 con = signatureRequestUtil.sendDocumentForSignatureRequest(vendorContactsUser, contractsTemplate, orgLocation, user, savedContracts, org,new RequirementSubmission());
						 }
				  }
		            savedContracts.setDocId(con.getTempdocId());
		            savedContracts.setEnvelopId(con.getTempEnvId());
		            savedContracts.setContractStatus(con.getContractStatus());
		            if(null != vendorContracts){
		            	vendorContracts.setDocId(con.getTempdocId());
			            vendorContracts.setEnvelopId(con.getTempEnvId());
			            vendorContracts.setContractStatus(con.getContractStatus());
		            }
		            
		        	Contracts updateEnvContracts = contractsTemplateDao.saveNewContract(savedContracts);
		        	if(null != vendorContracts){
		        		contractsTemplateDao.saveNewContract(vendorContracts);
		        	}
			  }
		}
		return savedContracts;
		
		
	}

	@Override
	public Contracts getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName) throws Exception
	{
		List<Contracts> vendorcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.VENDOR, contractsType, contractStatus, user, userRoleName);
		List<Contracts> clientcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.CLIENT, contractsType, contractStatus, user, userRoleName);
		List<Contracts> fullTimeoffercontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation, contractSubmissionType, Constants.FULL_TIME_OFFER, contractsType, contractStatus, user, userRoleName);
		Contracts contractsForList = new Contracts();
		
		for(Contracts contracts :vendorcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId()){
				contracts.setOwnerUser(contracts.getOwnerUserId().getId());
				if(null != contracts.getOwnerUserId().getFirstName() && null != contracts.getOwnerUserId().getLastName()){
					ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
				}else{
					ownerName.append(contracts.getOwnerUserId().getFirstName());
				}
			}else{
				ownerName.append("---");
			}
			
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser()){
				if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getLastName()){
					vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
				}else{
					vendorName.append(contracts.getContactUser().getFirstName());	
				}
			}else{
				vendorName.append("---");
			}
			contracts.setVendorContactName(vendorName.toString());
			
			if(null != contracts.getOrgRelations()){
			 	OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contracts.getOrgRelations().getId());
			 	if(null != orgRelations){
			 		if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
			 			contracts.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
					}	
					else if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						contracts.setContractWith(orgRelations.getLocationId().getOrgLocationName());
					}
			 	}
			}
			
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
			if(null != contracts && null != contracts.getStartDate()){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contracts.setStartDateStr(currentDateInTimezone);
			}
			if(null != contracts && null != contracts.getEndDate()){
				String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
				contracts.setEndDateStr(endDateInTimezone);
			}
			
			/*contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
			if(null != contracts.getEndDate()){
				contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
			}*/
			
			if(null != contracts.getParentContractId()){
			contracts.setParentId(contracts.getParentContractId().getId().toString());
			}
			else{
				contracts.setParentId("--");
			}

			if(null != contracts.getApproverUser() ){
				contracts.setApproverUserId(contracts.getApproverUser().getId());
				if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
					StringBuilder sb = new StringBuilder();
					contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
				}else{
					contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
				}
			}
			//Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
			Integer countOfDoc = 0;
    		if(null != contracts.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}else{
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}
			
			}
		for(Contracts contracts :clientcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId()){
				contracts.setOwnerUser(contracts.getOwnerUserId().getId());
				if(null != contracts.getOwnerUserId().getFirstName() && null != contracts.getOwnerUserId().getLastName()){
					ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
				}else{
					ownerName.append(contracts.getOwnerUserId().getFirstName());
				}
			}else{
				ownerName.append("---");
			}
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser()){
				if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getLastName()){
					vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
				}else{
					vendorName.append("---");
				}
			}else{
				vendorName.append("---");
			}
			contracts.setVendorContactName(vendorName.toString());
			
			if(null != contracts.getOrgRelations()){
			 	OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contracts.getOrgRelations().getId());
			 	if(null != orgRelations){
			 		if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
			 			contracts.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
					}	
					else if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						contracts.setContractWith(orgRelations.getLocationId().getOrgLocationName());
					}
			 	}
			}
			
			/*contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
			if(null != contracts.getEndDate()){
				contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
			}*/
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
			
			if(null != contracts && null != contracts.getStartDate()){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contracts.setStartDateStr(currentDateInTimezone);
			}
			if(null != contracts && null != contracts.getEndDate()){
				String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
				contracts.setEndDateStr(endDateInTimezone);
			}
			if(null != contracts.getParentContractId()){
			contracts.setParentId(contracts.getParentContractId().getId().toString());
			}
			else{
				contracts.setParentId("--");
			}
			
			if(null != contracts.getApproverUser()){
				contracts.setApproverUserId(contracts.getApproverUser().getId());
				if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
					StringBuilder sb = new StringBuilder();
					contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
				}else{
					contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
				}
			}
			Integer countOfDoc = 0;
    		if(null != contracts.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}else{
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}
		}
		//fullTimeoffercontractsList 
		for(Contracts contracts : fullTimeoffercontractsList ){
			StringBuilder ownerName = new StringBuilder();
			if(null != contracts.getOwnerUserId()){
				contracts.setOwnerUser(contracts.getOwnerUserId().getId());
				if(null != contracts.getOwnerUserId().getFirstName() && null != contracts.getOwnerUserId().getLastName()){
					ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
				}else{
					ownerName.append(contracts.getOwnerUserId().getFirstName());
				}
			}else{
				ownerName.append("---");
			}
			contracts.setOwnerName(ownerName.toString());
		
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		//	SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
			
			if(null != contracts && null != contracts.getJoinDate()){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getJoinDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contracts.setJoinedDateStr(currentDateInTimezone);
			}
			else{
				contracts.setJoinedDateStr("--");
			}
			
			if(null != contracts.getParentContractId()){
			contracts.setParentId(contracts.getParentContractId().getId().toString());
			}
			else{
				contracts.setParentId("--");
			}
			RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
        	if(null != reqSub.getCandidate().getLastName()){
        		contracts.setEmpName(reqSub.getCandidate().getFirstName()+" "+reqSub.getCandidate().getLastName());	
			}else{
				contracts.setEmpName(reqSub.getCandidate().getFirstName());
			}
			
			if(null != contracts.getApproverUser()){
				contracts.setApproverUserId(contracts.getApproverUser().getId());
				if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
					StringBuilder sb = new StringBuilder();
					contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
				}else{
					contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
				}
			}
			Integer countOfDoc = 0;
    		if(null != contracts.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}else{
    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
    			contracts.setReqDocCount(countOfDoc);
    		}
		}
		
		contractsForList.setVendorcontractsList(vendorcontractsList);
		contractsForList.setClientcontractsList(clientcontractsList);
		contractsForList.setFullTimeoffercontractsList(fullTimeoffercontractsList);
		return contractsForList;
	}
	
	@Override
	public Contracts getAllContractsCountByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName) throws Exception
	{
		Integer totalDraftCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, Constants.DRAFT, user, userRoleName);
		Integer totalWTACount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, Constants.WAITING_FOR_APPROVER, user, userRoleName);
		Integer totalWTSCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, Constants.WAITING_TO_BE_SIGNED, user, userRoleName);
		Integer totalWTVCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "WAITING_TO_BE_VERIFY", user, userRoleName);
		Integer totalFinalizedCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "Finalized", user, userRoleName);
		Integer totalExpiredCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "EXPIRED", user, userRoleName);
		Integer totalRejectedCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, Constants.REJECTED, user, userRoleName);
		
		Integer totalDraftVendorCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.VENDOR, contractsType, Constants.DRAFT, user, userRoleName);
		Integer totalDraftClientCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.CLIENT, contractsType, Constants.DRAFT, user, userRoleName);
		
		Contracts contractsForList = new Contracts();
		
		contractsForList.setTotalDraftCount(totalDraftCount);
		contractsForList.setTotalWTACount(totalWTACount);
		contractsForList.setTotalWTSCount(totalWTSCount);
		contractsForList.setTotalWTVCount(totalWTVCount);
		contractsForList.setTotalFinalizedCount(totalFinalizedCount);
		contractsForList.setTotalExpiredCount(totalExpiredCount);
		contractsForList.setTotalRejectedCount(totalRejectedCount);
		
		contractsForList.setTotalDraftVendorCount(totalDraftVendorCount);
		contractsForList.setTotalDraftClientCount(totalDraftClientCount);
		return contractsForList;
	}

	@Override
	public Contracts getContractById(Long contractId)
	{
		return contractsTemplateDao.getContractById(contractId);
	}
	
	@Override
	public Contracts getContractByContractUUID(String contractUUID)
	{
		return contractsTemplateDao.getContractByContractUUID(contractUUID);
	}

	@Override
	public Contracts saveNewContract(Contracts updateContracts) throws Exception
	{
		return contractsTemplateDao.saveNewContract(updateContracts);
	}

	@Override
	public Contracts getChildContractById(Long contractId)
	{
		return contractsTemplateDao.getChildContractById(contractId);
	}

	@Override
	public Contracts approveContracts(long recordId, String status, OrgLocations orgLocation,User user,Org org,String rejectReason) throws Exception
	{
		Contracts contracts  = contractsTemplateDao.getContractById(recordId);
		Contracts upadatedContracts = null;
		if(!Constants.REJECTED.equalsIgnoreCase(status)){
			contracts.setTemplateSubmissionStatus(Constants.PENDING);
			//upadatedContracts.setTypeOfContract(Constants.NEW_TYPE);
			 upadatedContracts = contractsTemplateDao.saveNewContract(contracts);
			 
				ContractsHistory contractsHistory = new ContractsHistory();
				contractsHistory.setFromStage(Constants.WAITING_FOR_APPROVER);
				contractsHistory.setContractsId(contracts);
				contractsHistory.setToStage(Constants.PENDING);
				contractsHistory.setCreatedDate(new Date());
				contractsHistory.setUpdatedDate(new Date());
				contractsHistory.setCreatedBy(user.getUserCode());
				contractsHistory.setUpdatedBy(user.getUserCode());
				contractsTemplateDao.saveContractsHistory(contractsHistory);
			 
			Contracts childContract = new Contracts();
			childContract.setContractTitle(contracts.getContractTitle());
			childContract.setStartDate(contracts.getStartDate());
			childContract.setEndDate(contracts.getEndDate());
			childContract.setJoinDate(contracts.getJoinDate());
			childContract.setWorkLocation(contracts.getWorkLocation());
			childContract.setBillingAmount(contracts.getBillingAmount());
			childContract.setBillingPer(contracts.getBillingPer());
			childContract.setInvoiceFrequency(contracts.getInvoiceFrequency());
			childContract.setPaymentTerm(contracts.getPaymentTerm());
			childContract.setParentContractId(upadatedContracts);
			if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(upadatedContracts.getContractType())){
				if(null != childContract){
					childContract.setContractType(upadatedContracts.getContractType());	
					childContract.setContractsTemplate(upadatedContracts.getContractsTemplate());	
					childContract.setContractOrgLocationId(orgLocation);
					childContract.setContactUser(upadatedContracts.getOwnerUserId());
				}
			}
			childContract.setTax(contracts.getTax());
			childContract.setTaxType(contracts.getTaxType());
			childContract.setDescription(contracts.getDescription());
			childContract.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_SIGNED);
			childContract.setStatus("0");
				if(null != contracts.getTypeOfContract() && !"".equalsIgnoreCase(contracts.getTypeOfContract()) && "custom".equalsIgnoreCase(contracts.getTypeOfContract())){
					childContract.setTypeOfContract(Constants.CUSTOM_TYPE);
				}else{
					childContract.setTypeOfContract(Constants.NEW_TYPE);
				}
		//	childContract.setTypeOfContract(Constants.NEW_TYPE);
			childContract.setContractUUID(contracts.getContractUUID());
				if(Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())|| Constants.VENDOR_NDA.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_NSA.equalsIgnoreCase(contracts.getContractType())){
					if(Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType(Constants.CLIENT_MSA);	
					}else if(Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType(Constants.Client_SOW);
					}
					else if(Constants.VENDOR_NDA.equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType(Constants.CLIENT_NDA);
					}
					else if(Constants.VENDOR_NSA.equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType(Constants.CLIENT_NSA);
					}
					childContract.setInitiatorType(Constants.CLIENT);
					childContract.setContractOrgLocationId(orgLocation);
					childContract.setParentContractId(upadatedContracts);
					OrgRelations orgRelations = contracts.getOrgRelations();//orgRelationsService.getOrRelationsById(contracts.getOrgRelations());
					childContract.setOrgRelations(orgRelations);
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						childContract.setOrgLocationId(orgRelations.getLocationId());
					}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						childContract.setOrgLocationId(orgRelations.getResponderLocationId());
					}
					
					childContract.setOwnerUserId(contracts.getContactUser());
					childContract.setContactUser(contracts.getOwnerUserId());
				}
				
				if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType()) || Constants.Client_SOW.equalsIgnoreCase(contracts.getContractType())){
					if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType(Constants.VENDOR_MSA);	
					}else{
						childContract.setContractType(Constants.Vendor_SOW);
					}
					childContract.setInitiatorType(Constants.VENDOR);
					childContract.setParentContractId(upadatedContracts);
					childContract.setContractOrgLocationId(orgLocation);
					OrgRelations orgRelations = contracts.getOrgRelations();//orgRelationsService.getOrRelationsById(contracts.getOrgLocationId().getId());
					childContract.setOrgRelations(orgRelations);
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						childContract.setOrgLocationId(orgRelations.getLocationId());
					}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						childContract.setOrgLocationId(orgRelations.getResponderLocationId());
					}
					childContract.setOwnerUserId(contracts.getContactUser());
					childContract.setContactUser(contracts.getOwnerUserId());
				}
				
				Contracts savedChildContracts = contractsTemplateDao.saveNewContract(childContract);
				
				ContractsHistory childContractsHistory = new ContractsHistory();
				childContractsHistory.setFromStage("NEW");
				childContractsHistory.setContractsId(savedChildContracts);
				childContractsHistory.setToStage(Constants.WAITING_TO_BE_SIGNED);
				childContractsHistory.setCreatedDate(new Date());
				childContractsHistory.setUpdatedDate(new Date());
				childContractsHistory.setCreatedBy(user.getUserCode());
				childContractsHistory.setUpdatedBy(user.getUserCode());
				contractsTemplateDao.saveContractsHistory(childContractsHistory);
				
					  Contracts con = null;
					  if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())){
						  if (null != contracts.getContractType() && !"".equalsIgnoreCase(contracts.getContractType())
									&& "custom".equalsIgnoreCase(contracts.getContractType())) {
								con = signatureRequestUtil.sendDocumentForSignatureRequestForCustom(new User(), contracts, orgLocation, user, upadatedContracts, org,contracts.getRequirementSubmission());
							}
							else {
								con = signatureRequestUtil.sendDocumentForSignatureRequest(new User(), upadatedContracts.getContractsTemplate(), orgLocation, user, upadatedContracts, org,contracts.getRequirementSubmission());
							}
					  }
					  else{
							 if(null != contracts.getTypeOfContract()  && !"".equalsIgnoreCase(contracts.getTypeOfContract() ) && "custom".equalsIgnoreCase(contracts.getTypeOfContract() )){
								con = signatureRequestUtil.sendDocumentForSignatureRequestForCustom(upadatedContracts.getContactUser(),contracts,orgLocation,user,upadatedContracts,org,new RequirementSubmission());				 
							 }else{
								 con = signatureRequestUtil.sendDocumentForSignatureRequest(upadatedContracts.getContactUser(),upadatedContracts.getContractsTemplate(),orgLocation,user,upadatedContracts,org,new RequirementSubmission());
							 }
					  }
					  upadatedContracts.setDocId(con.getTempdocId());
					  upadatedContracts.setEnvelopId(con.getTempEnvId());
					  upadatedContracts.setContractStatus(con.getContractStatus());
					  savedChildContracts.setDocId(con.getTempdocId());
					  savedChildContracts.setEnvelopId(con.getTempEnvId());
					  savedChildContracts.setContractStatus(con.getContractStatus());
					  contractsTemplateDao.saveNewContract(upadatedContracts);
			        	Contracts updateEnvContracts = contractsTemplateDao.saveNewContract(savedChildContracts);
				contractApproveEmail(contracts,user,org,orgLocation);
				//sendMailToOwner(contracts,user,org,orgLocation);
		}else{

			contracts.setRejectedReason(rejectReason);
			contracts.setTemplateSubmissionStatus(Constants.REJECTED);

			 upadatedContracts = contractsTemplateDao.saveNewContract(contracts);
			 ContractsHistory contractsHistory = new ContractsHistory();
			 contractsHistory.setFromStage(Constants.WAITING_FOR_APPROVER);
			 contractsHistory.setContractsId(contracts);
			 contractsHistory.setToStage(Constants.REJECTED);
			 contractsHistory.setCreatedDate(new Date());
			 contractsHistory.setUpdatedDate(new Date());
			 contractsHistory.setCreatedBy(user.getUserCode());
			 contractsHistory.setUpdatedBy(user.getUserCode());
			contractsTemplateDao.saveContractsHistory(contractsHistory);
			 contractRejectEmail(contracts,user,org,orgLocation);
		}
		
		return upadatedContracts;
	}
	
	private void contractRejectEmail(Contracts contracts, User loginUser, Org org, OrgLocations orgLocation) throws Exception {
		Timezone timezone = null;
		if(null != orgLocation.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 }
		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(createdUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName()); 
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("ContractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("ContractTitle","--");
			 }
		 
			if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName()) && contracts.getOwnerUserId().getLastName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())){
				StringBuilder ownerSb = new StringBuilder();
				globalVarsMap.put("OwnerName", ownerSb.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
			}else if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName())){
				globalVarsMap.put("OwnerName",contracts.getOwnerUserId().getFirstName());
			}else{
				globalVarsMap.put("OwnerName","--");
			}
			
			 if(contracts.getRejectedReason()!= null && !"".equalsIgnoreCase(contracts.getRejectedReason())){
				 globalVarsMap.put("rejectReason", contracts.getRejectedReason());
				 }else{
					 globalVarsMap.put("rejectReason","--");
				 }
			 
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 
				 StringBuilder builder=new StringBuilder();
				 globalVarsMap.put("clientContact",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString()); 
			 }
			 else{
			 globalVarsMap.put("clientContact",contracts.getContactUser().getFirstName());
			 }
			 StringBuilder linkBuild = new StringBuilder();
			 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			
		//	SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
			if(contracts.getStartDate() != null){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
				if(null != timezone && null != timezone.getTimeZoneId()){
					String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					globalVarsMap.put("StartDate",currentDateInTimezone);	
				}
		    }else{
				globalVarsMap.put("StartDate","--");
			}

			
			if(contracts.getEndDate() != null){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
				if(null != timezone && null != timezone.getTimeZoneId()){
					String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					globalVarsMap.put("EndDate",currentDateInTimezone);	
				}
		    }else{
				globalVarsMap.put("EndDate","--");
			}
		
		if(createdUser.getFirstName()!= null && !"".equalsIgnoreCase(createdUser.getFirstName()) && createdUser.getLastName()!= null && !"".equalsIgnoreCase(createdUser.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("ContractCreator", creatorSb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
		}else if(createdUser.getFirstName()!= null && !"".equalsIgnoreCase(createdUser.getFirstName())){
			globalVarsMap.put("ContractCreator",createdUser.getFirstName());
		}else{
			globalVarsMap.put("ContractCreator","--");
		}
		
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("ApproverUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("ApproverUser",loginUser.getFirstName());
			}
		 
			 globalVarsMap.put("LoginOrg",org.getOrgName());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,createdUser, orgLocation, notificationScenario);
		
		
	}

	private void contractApproveEmail(Contracts contracts, User loginUser, Org org,OrgLocations orgLocation) throws Exception {
		Timezone timezone = null;
		if(null != orgLocation.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 }
		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(createdUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName()); 
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("ContractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("ContractTitle","--");
			 }
		
		if(createdUser.getFirstName()!= null && !"".equalsIgnoreCase(createdUser.getFirstName()) && createdUser.getLastName()!= null && !"".equalsIgnoreCase(createdUser.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("ContractCreator", creatorSb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
		}else if(createdUser.getFirstName()!= null && !"".equalsIgnoreCase(createdUser.getFirstName())){
			globalVarsMap.put("ContractCreator",createdUser.getFirstName());
		}else{
			globalVarsMap.put("ContractCreator","--");
		}
		if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName()) && contracts.getOwnerUserId().getLastName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())){
			StringBuilder ownerSb = new StringBuilder();
			globalVarsMap.put("OwnerName", ownerSb.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
		}else if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName())){
			globalVarsMap.put("OwnerName",contracts.getOwnerUserId().getFirstName());
		}else{
			globalVarsMap.put("OwnerName","--");
		}
		if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
			 
			 StringBuilder builder=new StringBuilder();
			 globalVarsMap.put("clientContact",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString()); 
		 }
		 else{
		 globalVarsMap.put("clientContact",contracts.getContactUser().getFirstName());
		 }
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		//SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
		if(contracts.getStartDate() != null){
			String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
			if(null != timezone && null != timezone.getTimeZoneId()){
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				globalVarsMap.put("StartDate",currentDateInTimezone);	
			}
	    }else{
			globalVarsMap.put("StartDate","--");
		}

		
		if(contracts.getEndDate() != null){
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			if(null != timezone && null != timezone.getTimeZoneId()){
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				globalVarsMap.put("EndDate",currentDateInTimezone);	
			}
	    }else{
			globalVarsMap.put("EndDate","--");
		}
		
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("ApproverUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("ApproverUser",loginUser.getFirstName());
			}
		 
			 globalVarsMap.put("LoginOrg",org.getOrgName());
			 
			
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,createdUser, orgLocation, notificationScenario);
		
	}

	private void sendMailToContractApprover(User loginUser, User approverUser, Contracts contracts, OrgLocations orgLocation) throws Exception
	{
		Timezone timezone = null;
		if(null != orgLocation.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 }
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(approverUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("ContractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("ContractTitle","--");
			 }
		
		if(approverUser.getFirstName()!= null && !"".equalsIgnoreCase(approverUser.getFirstName()) && approverUser.getLastName()!= null && !"".equalsIgnoreCase(approverUser.getLastName())){
			StringBuilder approverSb = new StringBuilder();
			globalVarsMap.put("ApproverName", approverSb.append(approverUser.getFirstName()).append(" ").append(approverUser.getLastName()).toString());
		}else if(approverUser.getFirstName()!= null && !"".equalsIgnoreCase(approverUser.getFirstName())){
			globalVarsMap.put("ApproverName",approverUser.getFirstName());
		}else{
			globalVarsMap.put("ApproverName","--");
		}
		
		if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName()) && contracts.getOwnerUserId().getLastName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())){
			StringBuilder ownerSb = new StringBuilder();
			globalVarsMap.put("OwnerName", ownerSb.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
		}else if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName())){
			globalVarsMap.put("OwnerName",contracts.getOwnerUserId().getFirstName());
		}else{
			globalVarsMap.put("OwnerName","--");
		}
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
	//	SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
		if(contracts.getStartDate() != null){
			String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
			if(null != timezone && null != timezone.getTimeZoneId()){
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				globalVarsMap.put("StartDate",currentDateInTimezone);	
			}
	    }else{
			globalVarsMap.put("StartDate","--");
		}

		
		if(contracts.getEndDate() != null){
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			if(null != timezone && null != timezone.getTimeZoneId()){
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				globalVarsMap.put("EndDate",currentDateInTimezone);	
			}
	    }else{
			globalVarsMap.put("EndDate","--");
		}
		 
		if(contracts.getContractType()!=null && !"".equalsIgnoreCase(contracts.getContractType())){
			globalVarsMap.put("ContractType",contracts.getContractType());
			}else{
				globalVarsMap.put("ContractType","--");
		}
	
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUserName",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUserName",loginUser.getFirstName());
			}
		 if(null != contracts.getContractOrgLocationId()){
		 if(null != contracts.getContractOrgLocationId().getOrgLocationName() && !"".equalsIgnoreCase(contracts.getContractOrgLocationId().getOrgLocationName())){
			 globalVarsMap.put("ClientVendorName",contracts.getContractOrgLocationId().getOrgLocationName());
			 }
		 }else{
				 globalVarsMap.put("ClientVendorName","--");
			}
			
			/*if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType())){
				List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
				for (OrgRelations orgRelations : clientList) {
				String orgName = "";
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrgLocationName();
				}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrgLocationName();
				}
				orgRelations.setOrgName(orgName);

			 if(orgRelations.getResponderLocationId().getOrgLocationName() != null && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrgLocationName()) && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrgLocationName())){
					 globalVarsMap.put("ClientVendorName",orgRelations.getResponderLocationId().getOrgLocationName());
					 }else{
						 globalVarsMap.put("ClientVendorName","--");
					}
			}	
			}
			if(Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
				List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
				for (OrgRelations orgRelations : vendorList) {
					String orgName = "";
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						orgName = orgRelations.getLocationId().getOrgLocationName();
					}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						orgName = orgRelations.getResponderLocationId().getOrgLocationName();
					}
					orgRelations.setOrgName(orgName);

					if(orgRelations.getResponderLocationId().getOrgLocationName() != null && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrgLocationName()) && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrgLocationName())){
						 globalVarsMap.put("ClientVendorName",orgRelations.getResponderLocationId().getOrgLocationName());
						 }else{
							 globalVarsMap.put("ClientVendorName","--");
						}
			}
			}*/
		 
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		// StringBuilder actionLinkStringBuilder = new StringBuilder();
		// String actionLink= actionLinkStringBuilder.append(sourceleadUrl).append("/requirementSummary?requirementUUID=").append(requirement.getRequirementUUID()).toString();
//		 String actionLink= sourceleadUrl"/requirementSummary?requirementUUID="+requirement.getRequirementUUID()+"";
		//calling the sendEmail method of emailScenerioUtil
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario);
		
	}
	
	private void sendMailToOwner(Contracts contracts, User loginUser, Org org, OrgLocations orgLocation) throws Exception {
		User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(owner.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
	//	 SimpleDateFormat dateFormat=orgLocation.getLocFullFormDateFormatter();
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName()); 
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("ContractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("ContractTitle","--");
			 }
		 
		 if(contracts.getContractType()!= null && !"".equalsIgnoreCase(contracts.getContractType())){
			 globalVarsMap.put("ContractType", contracts.getContractType());
			 }else{
				 globalVarsMap.put("ContractType","--");
			 }
		
		 if(contracts.getEndDate() != null && !"".equalsIgnoreCase(contracts.getEndDate().toString())){
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,orgLocation.getTimezone().getTimeZoneId(), currentTimeStr);
			Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
			interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),interviewConverteDate);
			globalVarsMap.put("EndDate", interviewDateInTimezone);
		    }else{
				 globalVarsMap.put("EndDate","--");
			 }
		 
		 if(contracts.getStartDate()!= null && !"".equalsIgnoreCase(contracts.getStartDate().toString())){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,orgLocation.getTimezone().getTimeZoneId(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),interviewConverteDate);
				globalVarsMap.put("StartDate", interviewDateInTimezone);
			    }else{
					 globalVarsMap.put("StartDate","--");
				 }
		 
		if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("OwnerUser", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		}else if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName())){
			globalVarsMap.put("OwnerUser",owner.getFirstName());
		}else{
			globalVarsMap.put("OwnerUser","--");
		}
		
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("ContractCreator",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("ContractCreator",loginUser.getFirstName());
			}
		 
			 globalVarsMap.put("LoginOrg",org.getOrgName());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_MAIL_TO_OWNER,Constants.ENTITY_TYPE_EMAIL);
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		/* String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
//		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario);
		
	}


	@Override
	public ContractsTemplate saveContractTemplate(ContractsTemplate contractsTemplate) throws Exception {
		return contractsTemplateDao.saveContractTemplate(contractsTemplate);
	}
	
	@Override
	public ContractsTemplate findByContractTemplateName(String templateName,String contractType,Long orgLocationId) throws Exception {
		return contractsTemplateDao.findByContractTemplateName(templateName,contractType,orgLocationId);
	}

	@Override
	public List<Contracts> getListOfContractsEnvId() {
		return  contractsTemplateDao.getListOfContractsEnvId();
	}

	@Override
	public Contracts findContractTitleByOrgLocationAndContractType(String contractTitle, String contractType,
			Long orgLocationId,Long contractId) {
		return contractsTemplateDao.findContractTitleByOrgLocationAndContractType(contractTitle, contractType, orgLocationId,contractId);
			}
			
	@Override
	public Contracts saveExistigContract(JSONObject jsonObj,MultipartFile multiFile, OrgLocations orgLocation, User user, Org org) throws Exception {
		Contracts contracts = new Contracts();
		Contracts vendorContracts = new Contracts();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        
        Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		
		if(Constants.NDA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.NSA.equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setContractType("Vendor "+jsonObj.getString("contractType"));
		}
		else{
			contracts.setContractType(jsonObj.getString("contractType"));
        }
		
		if(jsonObj.has("submissionOffersList") && null != jsonObj.getString("submissionOffersList") && !"".equals(jsonObj.getString("submissionOffersList"))){
			if(Constants.Vendor_SOW.equalsIgnoreCase(jsonObj.getString("contractType")) ||  Constants.Client_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))){
				Candidate can = candidateService.getCandidateByCandidateId(Long.parseLong(jsonObj.getString("submissionOffersList")));
				contracts.setCandidate(can);
			}else{
				RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(jsonObj.getString("submissionOffersList")));
				contracts.setRequirementSubmission(rs);
				contracts.setCandidate(rs.getCandidate());	
			}
		}
		
		if(jsonObj.has("hoursPerWeek") && null != jsonObj.getString("hoursPerWeek") && !"".equals(jsonObj.getString("hoursPerWeek"))){
			contracts.setHoursPerWeek(jsonObj.getString("hoursPerWeek"));
		}
		
		if(jsonObj.has("overTimeHours") && null != jsonObj.getString("overTimeHours") && !"".equals(jsonObj.getString("overTimeHours"))){
			contracts.setOverTimeHours(jsonObj.getString("overTimeHours"));
		}
		
		if(jsonObj.has("totalHoursEstimated") && null != jsonObj.getString("totalHoursEstimated") && !"".equals(jsonObj.getString("totalHoursEstimated"))){
			contracts.setTotalHours(jsonObj.getString("totalHoursEstimated"));
		}
		
		if(jsonObj.has("overtimeRate") && null != jsonObj.getString("overtimeRate") && !"".equals(jsonObj.getString("overtimeRate"))){
			contracts.setOvertimeRate(jsonObj.getString("overtimeRate"));
		}
		
		if(jsonObj.has("overtimeRateType") && null != jsonObj.getString("overtimeRateType") && !"".equals(jsonObj.getString("overtimeRateType"))){
			contracts.setOvertimeRateType(jsonObj.getString("overtimeRateType"));
		}
		
		contracts.setTypeOfContract(Constants.EXISTING_TYPE);
		vendorContracts.setTypeOfContract(Constants.EXISTING_TYPE);
		contracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		vendorContracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		User ownerUser = userService.getUserById(Long.parseLong(jsonObj.getString("owner")));
		contracts.setOwnerUserId(ownerUser);
		
		
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
		if(jsonObj.has("startDate") && null != jsonObj.getString("startDate") && !"".equals(jsonObj.getString("startDate"))){
			Date startTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("startDate"));
			String currentTimeStr = Constants.TZ_DF.format(startTime);  
			String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setStartDate(convertedDate);
			if(null != vendorContracts){
				vendorContracts.setStartDate(convertedDate);
			}
		}
		if (jsonObj.has("workLocation") && null != jsonObj.getString("workLocation")
				&& !"".equals(jsonObj.getString("workLocation"))) {
			contracts.setWorkLocation(jsonObj.getString("workLocation"));
			if(null != vendorContracts){
				vendorContracts.setWorkLocation(jsonObj.getString("workLocation"));
			}
		}
		
		if (jsonObj.has("joinDate") && null != jsonObj.getString("joinDate")
				&& !"".equals(jsonObj.getString("joinDate"))) {
			Date joinTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("joinDate"));
			String currentTimeStr = Constants.TZ_DF.format(joinTime);
			String startTimeUTC = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setJoinDate(convertedDate);
			if(null != vendorContracts){
				vendorContracts.setJoinDate(convertedDate);
			}
		}
		
		if(jsonObj.has("endDate")){
			if(null != jsonObj.getString("endDate") && !"".equals(jsonObj.getString("endDate"))){
				Date endTimeStr = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("endDate"));  
				String endDateStr = Constants.TZ_DF.format(endTimeStr);  
				String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", endDateStr);
				Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
				contracts.setEndDate(convertedEndDate);
				vendorContracts.setEndDate(convertedEndDate);
			}
		}
		User approverUser = null;
		User vendorContactsUser = null;
		if((Constants.VENDOR_MSA.equalsIgnoreCase(jsonObj.getString("contractType"))|| Constants.Vendor_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))|| Constants.NDA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.NSA.equalsIgnoreCase(jsonObj.getString("contractType")))){
			contracts.setInitiatorType(Constants.CLIENT);
			contracts.setOrgLocationId(orgLocation);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
			contracts.setContactUser(vendorContactsUser);
		}
		if(Constants.CLIENT_MSA.equalsIgnoreCase(jsonObj.getString("contractType"))||Constants.Client_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setInitiatorType(Constants.VENDOR);
			contracts.setOrgLocationId(orgLocation);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
			contracts.setContactUser(vendorContactsUser);
		}
		
		if(jsonObj.has("percentageReward") && null != jsonObj.getString("percentageReward") && !"".equals(jsonObj.getString("percentageReward")) ){
			contracts.setBillingAmount(jsonObj.getString("percentageReward"));
			vendorContracts.setBillingAmount(jsonObj.getString("percentageReward"));
				contracts.setBillingPer("Percentage");
				vendorContracts.setBillingPer("Percentage");
		}
		/*if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(jsonObj.getString("contractType"))){
			if(jsonObj.has("candiFlatReward") && null != jsonObj.getString("candiFlatReward") && !"".equals(jsonObj.getString("candiFlatReward")) ){
				contracts.setBillingAmount(jsonObj.getString("candiFlatReward"));
				if(null != vendorContracts){
					vendorContracts.setBillingAmount(jsonObj.getString("candiFlatReward"));
				}
				
				if(jsonObj.has("flatRateType") && null != jsonObj.getString("flatRateType") && !"".equals(jsonObj.getString("flatRateType")) ){
					contracts.setBillingPer(jsonObj.getString("flatRateType"));
					if(null != vendorContracts){
						vendorContracts.setBillingPer(jsonObj.getString("flatRateType"));
					}
				}
			}
			
			if(jsonObj.has("candiRateType") && null != jsonObj.getString("candiRateType") && !"".equals(jsonObj.getString("candiRateType")) ){
				contracts.setBillingRateType(jsonObj.getString("candiRateType"));
				if(null != vendorContracts){
					vendorContracts.setBillingRateType(jsonObj.getString("candiRateType"));
				}
			}
			
			if(jsonObj.has("candiRatePer") && null != jsonObj.getString("candiRatePer") && !"".equals(jsonObj.getString("candiRatePer")) ){
				contracts.setBillingRatePer(jsonObj.getString("candiRatePer"));
				if(null != vendorContracts){
					vendorContracts.setBillingRatePer(jsonObj.getString("candiRatePer"));
				}
			}
		}
		else{*/
			if(jsonObj.has("flatReward") && null != jsonObj.getString("flatReward") && !"".equals(jsonObj.getString("flatReward")) ){
				contracts.setBillingAmount(jsonObj.getString("flatReward"));
				if(null != vendorContracts){
					vendorContracts.setBillingAmount(jsonObj.getString("flatReward"));
				}
				
				if(jsonObj.has("flatRateType") && null != jsonObj.getString("flatRateType") && !"".equals(jsonObj.getString("flatRateType")) ){
					contracts.setBillingPer(jsonObj.getString("flatRateType"));
					if(null != vendorContracts){
						vendorContracts.setBillingPer(jsonObj.getString("flatRateType"));
					}
				}
			}
			
			if(jsonObj.has("billingRateType") && null != jsonObj.getString("billingRateType") && !"".equals(jsonObj.getString("billingRateType")) ){
				contracts.setBillingRateType(jsonObj.getString("billingRateType"));
				if(null != vendorContracts){
					vendorContracts.setBillingRateType(jsonObj.getString("billingRateType"));
				}
			}
			
			if(jsonObj.has("billingRatePer") && null != jsonObj.getString("billingRatePer") && !"".equals(jsonObj.getString("billingRatePer")) ){
				contracts.setBillingRatePer(jsonObj.getString("billingRatePer"));
				if(null != vendorContracts){
					vendorContracts.setBillingRatePer(jsonObj.getString("billingRatePer"));
				}
			}
//		}
		
		if(multiFile != null && multiFile.getBytes() != null && !"".equalsIgnoreCase(multiFile.getOriginalFilename())){
			contracts.setDocFile(multiFile.getBytes());
			contracts.setFileName(multiFile.getOriginalFilename());
			contracts.setFileType(multiFile.getContentType());
		 }
		
		contracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		vendorContracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		
		if(jsonObj.has("paymentFrequency") && null != jsonObj.getString("paymentFrequency") && !"".equals(jsonObj.getString("paymentFrequency")) ){
			contracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
			vendorContracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
		}
		
		if(jsonObj.has("taxPercentageReward") && null != jsonObj.getString("taxPercentageReward") && !"".equals(jsonObj.getString("taxPercentageReward")) ){
			contracts.setTax(jsonObj.getString("taxPercentageReward"));
			vendorContracts.setTax(jsonObj.getString("taxPercentageReward"));
			contracts.setTaxType("Percentage");
			vendorContracts.setTaxType("Percentage");
		}
		if(jsonObj.has("taxFlatReward") && null != jsonObj.getString("taxFlatReward") && !"".equals(jsonObj.getString("taxFlatReward")) ){
			contracts.setTax(jsonObj.getString("taxFlatReward"));
			vendorContracts.setTax(jsonObj.getString("taxFlatReward"));
			contracts.setTaxType(jsonObj.getString("taxFlatRateType"));
			vendorContracts.setTaxType(jsonObj.getString("taxFlatRateType"));
		}
		
		
		if(jsonObj.has("description") && null != jsonObj.getString("description") && !"".equals(jsonObj.getString("description"))){
			contracts.setDescription(jsonObj.getString("description").trim());
			vendorContracts.setDescription(jsonObj.getString("description").trim());
		}
		if(jsonObj.has("jurisdiction") && null != jsonObj.getString("jurisdiction") && !"".equals(jsonObj.getString("jurisdiction"))){
			contracts.setStateJurisdiction(jsonObj.getString("jurisdiction"));
		}
		contracts.setStatus("0");
		contracts.setOrgLocationId(orgLocation);
		vendorContracts.setTemplateSubmissionStatus(Constants.FINALIZED);
		contracts.setTemplateSubmissionStatus(Constants.FINALIZED);
		vendorContracts.setStatus("0");
		contracts.setCreatedDate(new Date());
		contracts.setCreatedBy(user.getUserCode());
		contracts.setUpdatedDate(new Date());
		contracts.setUpdatedBy(user.getUserCode());
		contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(jsonObj.getString("contractType"))){
			if(null != vendorContracts){
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractType(jsonObj.getString("contractType"));	
				vendorContracts.setContractOrgLocationId(orgLocation);
				vendorContracts.setContactUser(savedContracts.getOwnerUserId());
			}
		}
			//if(Constants.VENDOR_MSA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.Vendor_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))){
		if(Constants.VENDOR_MSA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.Vendor_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))|| Constants.NDA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.NSA.equalsIgnoreCase(jsonObj.getString("contractType"))){
			if(Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.CLIENT_MSA);	
			}else if(Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.Client_SOW);
			}
			else if(Constants.NDA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.VENDOR_NDA);
			}
			else if(Constants.NSA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.VENDOR_NSA);
			}
				vendorContracts.setContractType(Constants.CLIENT_MSA);
				vendorContracts.setInitiatorType(Constants.CLIENT);
				vendorContracts.setContractOrgLocationId(orgLocation);
				vendorContracts.setParentContractId(savedContracts);
				OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
				vendorContracts.setOrgRelations(orgRelations);
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				
				User vendorUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
				vendorContracts.setOwnerUserId(vendorUser);
				vendorContracts.setContactUser(ownerUser);
			}
			if(Constants.CLIENT_MSA.equalsIgnoreCase(jsonObj.getString("contractType"))||Constants.Client_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))){
				if(Constants.VENDOR_MSA.equalsIgnoreCase(jsonObj.getString("contractType"))){
					vendorContracts.setContractType(Constants.VENDOR_MSA);
				}
				else{
					vendorContracts.setContractType(Constants.Vendor_SOW);
				}
				vendorContracts.setInitiatorType(Constants.VENDOR);
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractOrgLocationId(orgLocation);
				OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
				vendorContracts.setOrgRelations(orgRelations);
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				User vendorUser = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
				vendorContracts.setOwnerUserId(vendorUser);
				vendorContracts.setContactUser(ownerUser);
			}
			vendorContracts.setContractUUID(contracts.getContractUUID());
			Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
		return savedContracts;
	}

	@Override
	public ContractsTemplate getContractTemplateByTemplateUUID(String tempUUID) {
		return contractsTemplateDao.getContractTemplateByTemplateUUID(tempUUID);
	}

	@Override
	public List<Contracts> getAllContractsBasedOnTimeFrequency(Date startDateUTC, Date endDateUTC) throws Exception
	{
		return contractsTemplateDao.getAllContractsBasedOnTimeFrequency(startDateUTC,endDateUTC);
	}

	@Override
	public void documentsReceivedMailToOwner(Contracts contracts,User loginUser,Org loginOrg) throws Exception
	{
		User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(owner.getFirstName());
		 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
			 emailMessage.setLastName(owner.getLastName());
		 }else{
			 emailMessage.setLastName("");
		 }
		 emailMessage.setToEmail(owner.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder sb = new StringBuilder();
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName()); 
		 }
		 
		 
		 /**
		  * 
			contractOwner', 
			loginUser', '{{c
			loginOrg', '{{co
			loginUrl', '{{co
		  * 
		  */
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(null != contracts.getContractTitle() && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("contractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("contractTitle","--");
			 }
		 
		 if(null != loginOrg.getOrgName() && !"".equalsIgnoreCase(loginOrg.getOrgName())){
			 globalVarsMap.put("loginOrg", loginOrg.getOrgName());
			 }else{
				 globalVarsMap.put("loginOrg","--");
			 }
		 
		if(null != owner.getFirstName() && !"".equalsIgnoreCase(owner.getFirstName()) && null != owner.getLastName() && !"".equalsIgnoreCase(owner.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("contractOwner", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		}else if(null != owner.getFirstName() && !"".equalsIgnoreCase(owner.getFirstName())){
			globalVarsMap.put("contractOwner",owner.getFirstName());
		}else{
			globalVarsMap.put("contractOwner","--");
		}
		
		if(null != loginUser.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName()) && null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			StringBuilder creatorSb1 = new StringBuilder();
			globalVarsMap.put("loginUser", creatorSb1.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}else if(null != owner.getFirstName() && !"".equalsIgnoreCase(loginUser.getFirstName())){
			globalVarsMap.put("loginUser",loginUser.getFirstName());
		}else{
			globalVarsMap.put("loginUser","--");
		}
		
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		 if(contracts.getContactUser().getFirstName() != null && contracts.getContactUser().getLastName()!=null && !"".equalsIgnoreCase(contracts.getContactUser().getFirstName()) && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("receiverParty",userBuild.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
			 }else{
				 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
			}
		 
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_DOCUMENTS_RECEIVED,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_DOCUMENTS_RECEIVED,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,owner, contracts.getOrgLocationId(), notificationScenario);
		
	}

	@Override
	public List<Contracts> getAllClientOrVendorContractsBasedOnLocationId(OrgLocations orgLocations, String responderType, User user, String userRoleName,String contractStatus,Long orgRelationId) throws Exception
	{
		List<Contracts> contractList = contractsTemplateDao.getAllClientOrVendorContractsBasedOnLocationId(orgLocations, responderType, user, userRoleName, contractStatus,orgRelationId);
		for (Contracts contracts : contractList) {
			if (Constants.VENDOR.equalsIgnoreCase(responderType)) {
				StringBuilder ownerName = new StringBuilder();
				StringBuilder vendorName = new StringBuilder();
				if (null != contracts.getOwnerUserId()) {
					contracts.setOwnerUser(contracts.getOwnerUserId().getId());
					if (null != contracts.getOwnerUserId().getFirstName()
							&& null != contracts.getOwnerUserId().getLastName()) {
						ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
					}
					else if (null != contracts.getOwnerUserId().getFirstName()) {
						ownerName.append(contracts.getOwnerUserId().getFirstName());
					}
					else {
						ownerName.append("---");
					}
				}
				else {
					ownerName.append("---");
				}
				contracts.setOwnerName(ownerName.toString());
				if (null != contracts.getContactUser()) {
					if (null != contracts.getContactUser().getFirstName()
							&& null != contracts.getContactUser().getLastName()) {
						vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
					}
					else {
						vendorName.append(contracts.getContactUser().getFirstName());
					}
				}
				else {
					vendorName.append("---");
				}
				contracts.setVendorContactName(vendorName.toString());
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
				  // SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
				if (null != contracts && null != contracts.getStartDate()) {
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
					String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),converteDate);
					contracts.setStartDateStr(currentDateInTimezone);
				}
				if (null != contracts && null != contracts.getEndDate()) {
					String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
					String endDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), endTimeStr);
					Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
					endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endconverteDate);
					contracts.setEndDateStr(endDateInTimezone);
				}
				/*
				 * contracts.setStartDateStr(Constants.DF_DMY.format(contracts.
				 * getStartDate())); if(null != contracts.getEndDate()){
				 * contracts.setEndDateStr(Constants.DF_DMY.format(contracts.
				 * getEndDate())); }
				 */
				if (null != contracts.getParentContractId()) {
					contracts.setParentId(contracts.getParentContractId().getId().toString());
				}
				else {
					contracts.setParentId("--");
				}
				if (null != contracts.getApproverUser()) {
					contracts.setApproverUserId(contracts.getApproverUser().getId());
					if (null != contracts.getApproverUser().getFirstName()
							&& null != contracts.getApproverUser().getLastName()) {
						StringBuilder sb = new StringBuilder();
						contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());
					}
					else {
						contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
					}
				}
				// Contracts childContarct =
				// contractsTemplateService.getChildContractById(contracts.getId());
				Integer countOfDoc = 0;
				if (null != contracts.getParentContractId()) {
					Contracts parentcontracts = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
					countOfDoc = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
					contracts.setReqDocCount(countOfDoc);
				}
				else {
					countOfDoc = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
					contracts.setReqDocCount(countOfDoc);
				}
			}
			else {
				StringBuilder ownerName = new StringBuilder();
				StringBuilder vendorName = new StringBuilder();
				if (null != contracts.getOwnerUserId()) {
					contracts.setOwnerUser(contracts.getOwnerUserId().getId());
					if (null != contracts.getOwnerUserId().getFirstName()
							&& null != contracts.getOwnerUserId().getLastName()) {
						ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
					}
					else if (null != contracts.getOwnerUserId().getFirstName()) {
						ownerName.append(contracts.getOwnerUserId().getFirstName());
					}
					else {
						ownerName.append("---");
					}
				}
				else {
					ownerName.append("---");
				}
				contracts.setOwnerName(ownerName.toString());
				if (null != contracts.getContactUser()) {
					if (null != contracts.getContactUser().getFirstName()
							&& null != contracts.getContactUser().getLastName()) {
						vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
					}
					else if (null != contracts.getContactUser().getFirstName()) {
						vendorName.append(contracts.getContactUser().getFirstName());
					}
					else {
						vendorName.append("---");
					}
				}
				else {
					vendorName.append("---");
				}
				contracts.setVendorContactName(vendorName.toString());
				/*
				 * contracts.setStartDateStr(Constants.DF_DMY.format(contracts.
				 * getStartDate())); if(null != contracts.getEndDate()){
				 * contracts.setEndDateStr(Constants.DF_DMY.format(contracts.
				 * getEndDate())); }
				 */
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
				//SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
				if (null != contracts && null != contracts.getStartDate()) {
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
					String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),converteDate);
					contracts.setStartDateStr(currentDateInTimezone);
				}
				if (null != contracts && null != contracts.getEndDate()) {
					String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
					String endDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), endTimeStr);
					Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
					endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),endconverteDate);
					contracts.setEndDateStr(endDateInTimezone);
				}
				if (null != contracts.getParentContractId()) {
					contracts.setParentId(contracts.getParentContractId().getId().toString());
				}
				else {
					contracts.setParentId("");
				}
				if (null != contracts.getApproverUser()) {
					contracts.setApproverUserId(contracts.getApproverUser().getId());
					if (null != contracts.getApproverUser().getFirstName()
							&& null != contracts.getApproverUser().getLastName()) {
						StringBuilder sb = new StringBuilder();
						contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());
					}
					else {
						contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
					}
				}
				Integer countOfDoc = 0;
				if (null != contracts.getParentContractId()) {
					Contracts parentcontracts = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
					countOfDoc = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
					contracts.setReqDocCount(countOfDoc);
				}
				else {
					countOfDoc = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
					contracts.setReqDocCount(countOfDoc);
				}
			}
		}
		return contractList;
	}

	@Override
	public ContractsHistory getAllContractsHistoryByContractId(Long contractId)
	{
		return contractsTemplateDao.getAllContractsHistoryByContractId(contractId);
	}

	@Override
	public Contracts getContractByReqSubmissionSowTypeAndOrgLocId(Long reqSub, OrgLocations loginOrgLocation, String contractType)
	{
		return contractsTemplateDao.getContractByReqSubmissionSowTypeAndOrgLocId(reqSub, loginOrgLocation, contractType);
	}
	
	@Override
	public void sendFinalisedMailToReceiver(Contracts contracts, OrgLocations orgLocation, User loginUser) throws Exception
	{
		Timezone timezone = null;
		if(null != orgLocation.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		 }
//		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(contracts.getContactUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		// SimpleDateFormat simpleDateFormat = orgLocation.getLocFullFormDateFormatter();
		 
		 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder msgBuild = new StringBuilder();
			 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString()); 
		 }else{
			 emailMessage.setFromName(loginUser.getFirstName()); 
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("contractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("contractTitle","--");
			 }
		 
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 StringBuilder builder=new StringBuilder();
				 globalVarsMap.put("receiverParty",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString()); 
			 }
			 else{
				 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
			 }
			
			 StringBuilder durationSb = new StringBuilder();
			 
			if(contracts.getStartDate() != null){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
				if(null != timezone && null != timezone.getTimeZoneId()){
					String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					durationSb.append(currentDateInTimezone);
//					globalVarsMap.put("StartDate",currentDateInTimezone);	
				}
			}
			
			if(contracts.getEndDate() != null){
				String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
				if(null != timezone && null != timezone.getTimeZoneId()){
					String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					durationSb.append(" - ").append(currentDateInTimezone);
				}
		    }
			globalVarsMap.put("duration",durationSb.toString());	
			
		if(!Constants.NDA.equalsIgnoreCase(contracts.getContractType()) && !Constants.NSA.equalsIgnoreCase(contracts.getContractType())){
			if(contracts.getInvoiceFrequency() != null && !"".equalsIgnoreCase(contracts.getInvoiceFrequency())){
				globalVarsMap.put("billingCycle", contracts.getInvoiceFrequency());
			 }else{
				 globalVarsMap.put("billingCycle","--");
			 }
			
			if(contracts.getPaymentTerm() != null && !"".equalsIgnoreCase(contracts.getPaymentTerm())){
				globalVarsMap.put("paymentFrequency", contracts.getPaymentTerm());
			 }else{
				 globalVarsMap.put("paymentFrequency","--");
			 }
			
			if(contracts.getBillingAmount() != null && !"".equalsIgnoreCase(contracts.getBillingAmount()) && contracts.getBillingRatePer() != null && !"".equalsIgnoreCase(contracts.getBillingRatePer())){
				StringBuilder billigSb = new StringBuilder();
				globalVarsMap.put("billingAmount", billigSb.append(contracts.getBillingAmount()).append(" / ").append(contracts.getBillingRatePer()).toString());
			}else if(contracts.getBillingAmount() != null && !"".equalsIgnoreCase(contracts.getBillingAmount())){
				globalVarsMap.put("billingAmount",contracts.getBillingAmount());
			}else{
				globalVarsMap.put("billingAmount","--");
			}
			
			if(!Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType()) && !Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType()) && !Constants.NDA.equalsIgnoreCase(contracts.getContractType()) && !Constants.NSA.equalsIgnoreCase(contracts.getContractType())){
				if(contracts.getRequirementSubmission() != null && contracts.getRequirementSubmission().getRequirement().getJobTitle() != null && !"".equalsIgnoreCase(contracts.getRequirementSubmission().getRequirement().getJobTitle())){
					globalVarsMap.put("Job", contracts.getRequirementSubmission().getRequirement().getJobTitle());
				 }else{
					 globalVarsMap.put("Job","--");
				 }
				
				if(contracts.getCandidate() != null && contracts.getCandidate().getFirstName() != null && !"".equalsIgnoreCase(contracts.getCandidate().getFirstName()) && contracts.getCandidate().getLastName()!= null && !"".equalsIgnoreCase(contracts.getCandidate().getLastName())){
					StringBuilder creatorSb = new StringBuilder();
					globalVarsMap.put("Associate", creatorSb.append(contracts.getCandidate().getFirstName()).append(" ").append(contracts.getCandidate().getLastName()).toString());
				}else if(contracts.getCandidate().getFirstName() != null && !"".equalsIgnoreCase(contracts.getCandidate().getFirstName())){
					globalVarsMap.put("Associate",contracts.getCandidate().getFirstName());
				}else{
					globalVarsMap.put("Associate","--");
				}
			}
		}
		
		
		if(loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("loginUser", creatorSb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}else if(loginUser.getFirstName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			globalVarsMap.put("loginUser",loginUser.getFirstName());
		}else{
			globalVarsMap.put("loginUser","--");
		}
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		EmailScenario  emailScenario = null;
		if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MSA_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
		}else if(Constants.CLIENT_SOW.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_SOW.equalsIgnoreCase(contracts.getContractType())){
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SOW_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
		}else if(Constants.NDA.equalsIgnoreCase(contracts.getContractType()) || Constants.NSA.equalsIgnoreCase(contracts.getContractType())){
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NDA_NSA_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
		}
		 
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		/* String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
//		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario);
		

	}

	@Override
	public Boolean getVendorMsaCreatedOrNot(Long vendorLocationId, Long loginLocatonId)
	{
		return contractsTemplateDao.getVendorMsaCreatedOrNot(vendorLocationId, loginLocatonId);
	}

	@Override
	public void sendMSAEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation, Date previousEndDate) throws Exception
	{
		User ownerUser = userService.getUserById(contracts.getOwnerUserId().getId());
		User contactUser = userService.getUserById(contracts.getContactUser().getId());
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(contactUser.getFirstName());
		emailMessage.setToEmail(contactUser.getEmail());
		emailMessage.setFromEmail(ownerUser.getEmail());
		if (ownerUser.getLastName() != null && !"".equalsIgnoreCase(ownerUser.getLastName())) {
			emailMessage.setFromName(sb.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
		}
		else {
			emailMessage.setFromName(ownerUser.getFirstName());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		emailMessage.setCreatedBy(ownerUser.getUserCode());
		emailMessage.setOrgLocationId(contracts.getOrgLocationId().getId());
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("contractTitle", contracts.getContractTitle());
		if (ownerUser.getLastName() != null && !"".equalsIgnoreCase(ownerUser.getLastName())) {
			StringBuilder ownerBuilder = new StringBuilder();
			globalVarsMap.put("contractOwner", ownerBuilder.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contractOwner", ownerUser.getFirstName());
		}
		if (contactUser.getLastName() != null && !"".equalsIgnoreCase(contactUser.getLastName())) {
			StringBuilder contactBuilder = new StringBuilder();
			globalVarsMap.put("contactUser", contactBuilder.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contactUser", contactUser.getFirstName());
		}
		if (previousEndDate != null) {
			String previousEndTimeStr = Constants.TZ_DF.format(previousEndDate);
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractPreviousEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimezone().getTimeZoneId(), previousEndTimeStr);
			Date contractConvertPreviousEndDate = TZ_DF_TS.parse(contractPreviousEndDate);
			String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertPreviousEndDate);
			globalVarsMap.put("previousEndDate", contractConvertDateInTimezone);
		}
		if (contracts.getStartDate() != null) {
			String startDate = Constants.TZ_DF.format(contracts.getStartDate());
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractStartDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimezone().getTimeZoneId(), startDate);
			Date contractConvertStartDate = TZ_DF_TS.parse(contractStartDate);
			String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertStartDate);
			globalVarsMap.put("startDate", contractConvertDateInTimezone);
		}
		if (contracts.getEndDate() != null) {
			String presentEndDate = Constants.TZ_DF.format(contracts.getEndDate());
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimezone().getTimeZoneId(), presentEndDate);
			Date contractConvertEndDate = TZ_DF_TS.parse(contractEndDate);
			String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertEndDate);
			globalVarsMap.put("presentEndDate", contractConvertDateInTimezone);
		}
		globalVarsMap.put("receiverOrg", contracts.getContractOrgLocationId().getOrgLocationName());
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginUrl", loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFY_CONTRACT_ENDDATE, Constants.ENTITY_TYPE_EMAIL);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, contracts.getOrgLocationId().getId(), iAdressArray, new ArrayList<EmailAttachment>());
	}

	@Override
	public void sendSOWEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation) throws Exception
	{
		StringBuilder projectName = new StringBuilder();
		StringBuilder projectId = new StringBuilder();
		if (contracts.getCandidate() != null) {
			if (contracts.getCandidate().getId() != null) {
				Long candidateId = contracts.getCandidate().getId();
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				if (candidate.getConsultantUser() != null) {
					if (candidate.getConsultantUser().getId() != null) {
						Employees emp = employeesService.getEmployeeByUserId(candidate.getConsultantUser().getId());
						if (emp.getId() != null) {
							List<ProjectMembers> pMember = projectMembersService.getAllProjectsbyEmployeeId(emp.getId());
							pMember.forEach(pm -> {
								projectName.append(pm.getProjects().getProjectName()).append(",");
								projectId.append(pm.getProjects().getId());
							});
						}
					}
				}
			}
		}
		User ownerUser = userService.getUserById(contracts.getOwnerUserId().getId());
		User contactUser = userService.getUserById(contracts.getContactUser().getId());
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(contactUser.getFirstName());
		emailMessage.setToEmail(contactUser.getEmail());
		emailMessage.setFromEmail(ownerUser.getEmail());
		if (ownerUser.getLastName() != null && !"".equalsIgnoreCase(ownerUser.getLastName())) {
			emailMessage.setFromName(sb.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
		}
		else {
			emailMessage.setFromName(ownerUser.getFirstName());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		emailMessage.setCreatedBy(ownerUser.getUserCode());
		emailMessage.setOrgLocationId(contracts.getOrgLocationId().getId());
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("contractTitle", contracts.getContractTitle());
		globalVarsMap.put("lastName", emailMessage.getLastName());
		if (ownerUser.getLastName() != null && !"".equalsIgnoreCase(ownerUser.getLastName())) {
			StringBuilder ownerBuilder = new StringBuilder();
			globalVarsMap.put("contractOwner", ownerBuilder.append(ownerUser.getFirstName()).append(" ").append(ownerUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contractOwner", ownerUser.getFirstName());
		}
		if (contactUser.getLastName() != null && !"".equalsIgnoreCase(contactUser.getLastName())) {
			StringBuilder contactBuilder = new StringBuilder();
			globalVarsMap.put("contactUser", contactBuilder.append(contactUser.getFirstName()).append(" ").append(contactUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("contactUser", contactUser.getFirstName());
		}
		if (contracts.getRequirementSubmission().getRequirement().getJobId() != null
				&& !"".equalsIgnoreCase(contracts.getRequirementSubmission().getRequirement().getJobId())) {
			globalVarsMap.put("jobId", contracts.getRequirementSubmission().getRequirement().getJobId());
		}
		else {
			globalVarsMap.put("jobId", "--");
		}
		globalVarsMap.put("JobTitle", contracts.getRequirementSubmission().getRequirement().getJobTitle());
		globalVarsMap.put("InitiatorLocation", contracts.getOrgLocationId().getOrgLocationName());
		if (projectId.toString() != null && !"".equalsIgnoreCase(projectId.toString())) {
			globalVarsMap.put("projectId", projectId.toString());
		}
		else {
			globalVarsMap.put("projectId", "--");
		}
		if (projectName != null && !"".equalsIgnoreCase(projectName.toString())) {
			globalVarsMap.put("projectTitle", projectName.substring(0, projectName.length()
					- 1).toString());
		}
		else {
			globalVarsMap.put("projectTitle", "--");
		}
		if (contracts.getBillingAmount() != null
				&& !"".equalsIgnoreCase(contracts.getBillingAmount())) {
			StringBuilder contractBuilder = new StringBuilder();
			contractBuilder.append(contracts.getBillingRateType()).append(" ").append(contracts.getBillingAmount()).append(" / ").append(contracts.getBillingRatePer());
			globalVarsMap.put("Rate", contractBuilder.toString());
		}
		else {
			globalVarsMap.put("Rate", "--");
		}
		String startTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String contractStartDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimezone().getTimeZoneId(), startTimeStr);
		Date contractConverteDate = TZ_DF_TS.parse(contractStartDate);
		String contractDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConverteDate);
		globalVarsMap.put("StartDate", contractDateInTimezone);
		if (contracts.getEndDate() != null) {
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			DateFormat TZ_DF_ETS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String contractEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimezone().getTimeZoneId(), currentTimeStr);
			Date contractConvertedDate = TZ_DF_ETS.parse(contractEndDate);
			String contractEndDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertedDate);
			globalVarsMap.put("EndDate", contractEndDateInTimezone);
		}
		else {
			globalVarsMap.put("EndDate", "--");
		}
		globalVarsMap.put("PartnerOrganisation", contracts.getContractOrgLocationId().getOrgLocationName());
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginUrl", loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFY_SOW_CONTRACT_ENDDATE, Constants.ENTITY_TYPE_EMAIL);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, contracts.getOrgLocationId().getId(), iAdressArray, new ArrayList<EmailAttachment>());
	}
	
}
