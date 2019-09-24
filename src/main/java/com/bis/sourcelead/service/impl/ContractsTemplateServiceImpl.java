package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dto.ClientContractsDTO;
import com.bis.sourcelead.dto.ContractDocumentsDTO;
import com.bis.sourcelead.dto.ContractTemplateDTO;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.FullTimeContractsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.VendorContractsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContractEditHistory;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.NewContractCustomFieldData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.NewContractCustomFieldDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SignatureRequestUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
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
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${vmsUrl}")
	private String vmsUrl;
	
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
	
	@Autowired
	private NewContractCustomFieldDataService newContractCustomFieldDataService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	LetterHeadService letterHeadService;
	
	@Override
	public ContractsTemplate saveContractTemplate(ContractTemplateDTO contractTemplateDTO, JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		
		
		ContractsTemplate contractsTemplate = null;
		if(StringUtils.isNotBlank(contractTemplateDTO.getTemplateId())){
//			if(){
				contractsTemplate = contractsTemplateDao.getContractTemplateById(Long.parseLong(contractTemplateDTO.getTemplateId()));
//			}else{
//				contractsTemplate = new ContractsTemplate();
//			}
		}else{
			contractsTemplate = new ContractsTemplate();
		}
		contractsTemplate.setOrgLocationId(orgLocation);
		
		contractsTemplate.setTemplateName(contractTemplateDTO.getTemplateName());
		
		for(String templateType : contractTemplateDTO.getContractType())
		contractsTemplate.setTemplateType(templateType);
		
		if(StringUtils.isNotBlank(contractTemplateDTO.getLetterHead())) {
			LetterHead letterHead = letterHeadService.getLetterheadByUUID(contractTemplateDTO.getLetterHead());
			contractsTemplate.setLetterHead(letterHead);
		}
		
		
		//This Is Not Using.
//		if(jsonObj.has("letterHead") && !isNullOrEmpty(jsonObj.getString("letterHead"))){
//			contractsTemplate.setLetterHead(jsonObj.getString("letterHead"));
//		}
		
		if(StringUtils.isNotBlank(contractTemplateDTO.getDescription())){
			contractsTemplate.setDescription(contractTemplateDTO.getDescription().trim());
		}
		
		if(StringUtils.isNotBlank(contractTemplateDTO.getTemplateContent())){
			String content = contractTemplateDTO.getTemplateContent();
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
	public ResponseList<ContractsTemplate> getAllContractsTemplatesByOrgLocationid(OrgLocations orgLocation, ContractTemplateDTO contractTemplateDTO) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByOrgLocationid(orgLocation, contractTemplateDTO);
	}

	@Override
	public List<ContractsTemplate> getAllContractsTemplatesByContractTypeLandLocationId(Long orgLocationId, String contractType) throws Exception
	{
		return contractsTemplateDao.getAllContractsTemplatesByContractTypeLandLocationId(orgLocationId,contractType);
	}

	@Override
	public Contracts saveNewContract(ContractsDTO contractsDTO, OrgLocations orgLocation, String saveType,User user,Org org,String contractType) throws Exception
	{
		Contracts contracts;
		Contracts vendorContracts = null;
		if(StringUtils.isNotBlank(contractsDTO.getContrId())){
			contracts = contractsTemplateDao.getContractById(Long.parseLong(contractsDTO.getContrId()));
			if(null != contracts && null != contracts.getParentContractId()){
				vendorContracts = contractsTemplateDao.getChildContractById(contracts.getParentContractId().getId());
			}else{
				if(!Constants.PENDING.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()))
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
			contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
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
		
		
		if(null != contractsDTO.getTypeOfContract() && !"".equalsIgnoreCase(contractsDTO.getTypeOfContract()) && Constants.CUSTOM_TYPE.equalsIgnoreCase(contractsDTO.getTypeOfContract())){
			contracts.setTypeOfContract(Constants.CUSTOM_TYPE);
		}else{
			contracts.setTypeOfContract(Constants.NEW_TYPE);
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getSubmissionOffersList())){
			RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(contractsDTO.getSubmissionOffersList()));
			contracts.setRequirementSubmission(rs);
			//contracts.setCandidate(rs.getCandidate());			
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
		
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotBlank(contractsDTO.getStartDate())){
			Date startTime =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getStartDate());
			String currentTimeStr = Constants.TZ_DF.format(startTime);  
			String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setStartDate(convertedDate);
		}
		
		
		if(StringUtils.isNotBlank(contractsDTO.getEndDate())){
			Date endTimeStr = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getEndDate());  
			String endDateStr = Constants.TZ_DF.format(endTimeStr);  
			String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDateStr);
			Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
			contracts.setEndDate(convertedEndDate);
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
		
		if (StringUtils.isNotBlank(contractsDTO.getTaxPercentageReward())) {
			contracts.setBillingAmount(contractsDTO.getTaxPercentageReward());
			contracts.setBillingPer("Percentage");
		}
		
		if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())){
			if(StringUtils.isNotBlank(contractsDTO.getCandiFlatReward())){
				contracts.setBillingAmount(contractsDTO.getCandiFlatReward());
				//jsonObj.getString("flatRateType")
				if(StringUtils.isNotBlank(contractsDTO.getTaxFlatRateType()) ){
					contracts.setBillingPer(contractsDTO.getTaxFlatRateType());
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getCandiRateType()) ){
				contracts.setBillingRateType(contractsDTO.getCandiRateType());
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getCandiRatePer())){
				contracts.setBillingRatePer(contractsDTO.getCandiRatePer());
			}
		}
		else{
			if(StringUtils.isNotBlank(contractsDTO.getFlatReward())){
				contracts.setBillingAmount(contractsDTO.getFlatReward());
				//jsonObj.getString("flatRateType")
				if(StringUtils.isNotBlank(contractsDTO.getTaxFlatRateType()) ){
					contracts.setBillingPer(contractsDTO.getTaxFlatRateType());
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRateType())){
				contracts.setBillingRateType(contractsDTO.getBillingRateType());
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRatePer()) ){
				contracts.setBillingRatePer(contractsDTO.getBillingRatePer());
			}
		}
		if(StringUtils.isNotBlank(contractsDTO.getInvoiceFrequency())){
			contracts.setInvoiceFrequency(contractsDTO.getInvoiceFrequency());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getPaymentFrequency())){
			contracts.setPaymentTerm(contractsDTO.getPaymentFrequency());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTaxPercentageReward())){
			contracts.setTax(contractsDTO.getTaxPercentageReward());
			contracts.setTaxType("Percentage");
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
			contracts.setTaxType(contractsDTO.getTaxFlatRateType());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getDescription())){
			contracts.setDescription(contractsDTO.getDescription().trim());
		}
		if(StringUtils.isNotBlank(contractsDTO.getJurisdiction())){
			contracts.setStateJurisdiction(contractsDTO.getJurisdiction());
		}
		contracts.setStatus("0");
		
		if("WTVStage".equalsIgnoreCase(contractsDTO.getSaveType())){
			contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_VERIFY);
		}else if(StringUtils.isNotBlank(contractsDTO.getSaveType()) && !Constants.DRAFT.equalsIgnoreCase(contractsDTO.getSaveType()) && !Constants.PENDING.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())){
			if(StringUtils.isNotBlank(contractsDTO.getOwnersType())){
				Long userId = user.getId().longValue();
				if(Constants.PARALLEL.equalsIgnoreCase(contractsDTO.getOwnersType())){
					if(StringUtils.isNotBlank(contractsDTO.getApprover())){
						if(userId != Long.parseLong(contractsDTO.getApprover()) && !contractsDTO.getParallelOwner().contains(user.getId())){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
						}else if(userId == Long.parseLong(contractsDTO.getApprover()) && !contractsDTO.getParallelOwner().contains(user.getId())){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						}else if(userId == Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getParallelOwner().contains(user.getId())){
							contracts.setTemplateSubmissionStatus(Constants.PENDING);
						}else if(userId != Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getParallelOwner().contains(user.getId())){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
						}
					}else{
						if(contractsDTO.getParallelOwner().contains(user.getId())){
							contracts.setTemplateSubmissionStatus(Constants.PENDING);
						}else{
							contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						}
					}
				}else if(Constants.SEQUENTIAL.equalsIgnoreCase(contractsDTO.getOwnersType())){
					if(StringUtils.isNotBlank(contractsDTO.getApprover())){
						if(userId != Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getSequentialOwner1() != userId && contractsDTO.getSequentialOwner2() != userId){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
						}else if(userId == Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getSequentialOwner1() != userId && contractsDTO.getSequentialOwner2() != userId){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						}else if(userId != Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getSequentialOwner1() == userId && contractsDTO.getSequentialOwner2() != userId){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
						}else if(userId == Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getSequentialOwner1() != userId && contractsDTO.getSequentialOwner2() == userId){
							contracts.setTemplateSubmissionStatus(Constants.PENDING);
						}else if(userId != Long.parseLong(contractsDTO.getApprover()) && contractsDTO.getSequentialOwner1() != userId && contractsDTO.getSequentialOwner2() == userId){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_FOR_APPROVER);
						}
					}else{
						if(contractsDTO.getSequentialOwner1() != userId && contractsDTO.getSequentialOwner2() == userId){
							contracts.setTemplateSubmissionStatus(Constants.PENDING);
						}else if(contractsDTO.getSequentialOwner1() == userId && contractsDTO.getSequentialOwner2() != userId){
							contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						}else{
							contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						}
					}
				}
			}
		}else if(Constants.DRAFT.equalsIgnoreCase(contractsDTO.getSaveType())){
			contracts.setTemplateSubmissionStatus(Constants.DRAFT);
		}else{
			contracts.setTemplateSubmissionStatus(Constants.PENDING);
		}
		
		//owners
		if(contractsDTO.getParallelOwner() != null || (contractsDTO.getSequentialOwner1() != null && contractsDTO.getSequentialOwner2() != null)){
			contracts.setOwnerHierarchyList(getOwnersList(contractsDTO, contracts, user));
		}
		
		if(StringUtils.isBlank(contractsDTO.getContrId()))
			contracts.setAutoGenId(getAutoGeneratedId(contracts,orgLocation));
		
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		
		if(contractsDTO.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:contractsDTO.getCustomFormData()) {
				if(customFields != null && customFields.getCustomFormFieldId() > 0) {
					NewContractCustomFieldData contractsCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						contractsCustomFields = newContractCustomFieldDataService.getNewContractCustomFieldDataById(customFields.getEntityId());
					}else {
						contractsCustomFields = new NewContractCustomFieldData();
					}
				contractsCustomFields.setContracts(savedContracts);
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				contractsCustomFields.setOrglocationsFormFields(orgFormField);
				contractsCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				newContractCustomFieldDataService.saveNewContractCustomFieldData(contractsCustomFields);
				}
				
			}	
		}
		
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
		if("".equalsIgnoreCase(contractsDTO.getContrId())){
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
		

		saveEditHistory(savedContracts);
		String redirectURL = "/contractListbyStatus-params-"+"contractType="+contracts.getContractType()+"-and-status=waiting";
		Long redirectOrgLocationId = orgLocation.getId();
		
		if(null != user && !Constants.DRAFT.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())){
				sendMailToOwner(savedContracts,user,org,orgLocation);
		}
		
		if(Constants.WAITING_FOR_APPROVER.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())){
			sendMailToContractApprover(user, approverUser, savedContracts, orgLocation,redirectURL,redirectOrgLocationId);
		}
		
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())) {
            sendContractForSignature(savedContracts,orgLocation,user);
        }
		
		if (!Constants.WAITING_FOR_APPROVER.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())
				&& !Constants.WAITING_TO_SEND.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())
				&& !Constants.DRAFT.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())
				&& !Constants.VENDOR_NDA.equalsIgnoreCase(savedContracts.getContractType())
				&& !Constants.VENDOR_NSA.equalsIgnoreCase(savedContracts.getContractType())
				&& !Constants.FULL_TIME_OFFER.equalsIgnoreCase(savedContracts.getContractType())) {
			saveClientOrVendorContract(savedContracts,vendorContracts,user,orgLocation);
		}
		
		return savedContracts;
		
		
	}
	
	private String getAutoGeneratedId(Contracts contracts, OrgLocations orgLocation)
	{
		if(!Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())){
		StringBuilder autoGenId = new StringBuilder();
		if(StringUtils.isNotBlank(orgLocation.getGeneratedUserId())) {
			autoGenId.append(orgLocation.getGeneratedUserId().substring(0, 3));
		}
		if("Client MSA".equalsIgnoreCase(contracts.getContractType()) || "Client SOW".equalsIgnoreCase(contracts.getContractType()))
			autoGenId.append("-CL");
		if("Vendor MSA".equalsIgnoreCase(contracts.getContractType()) || "Vendor SOW".equalsIgnoreCase(contracts.getContractType()))
			autoGenId.append("-VN");
		if(contracts.getContractType().contains("MSA"))
			autoGenId.append("-MSA");
		if(contracts.getContractType().contains("NDA"))
			autoGenId.append("-NDA");
		if(contracts.getContractType().contains("NSA"))
			autoGenId.append("-NSA");
		if(contracts.getContractType().contains("SOW"))
			autoGenId.append("-SOW");
		if(StringUtils.isNotBlank(contracts.getContractOrgLocationId().getGeneratedUserId())) {
			autoGenId.append("-").append(contracts.getContractOrgLocationId().getGeneratedUserId().substring(0, 3));
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(contracts.getStartDate());
		int year = calendar.get(Calendar.YEAR);
		autoGenId.append("-").append(year);
		
		Contracts contract = getMaxNoOfContract(orgLocation.getId());
		if(contract != null && StringUtils.isNotBlank(contract.getAutoGenId())){
			String[] genId = null;
			genId = contract.getAutoGenId().split("-");
			int num = Integer.parseInt(genId[genId.length-1]);
			autoGenId.append("-").append(num+1);
		}
		else
			autoGenId.append("-1");
		return autoGenId.toString();
		}else{
			return "";
		}
	}

	private void saveEditHistory(Contracts savedContracts)
	{
		ContractEditHistory contract = new ContractEditHistory();
		if(savedContracts.getContractType() != null)
			contract.setContractType(savedContracts.getContractType());
		if(savedContracts.getContractType() != null)
			contract.setContractTitle(savedContracts.getContractTitle());
		if(savedContracts.getTypeOfContract() != null)
			contract.setTypeOfContract(savedContracts.getTypeOfContract());
		if(savedContracts.getContractsTemplate() != null)
			contract.setContractsTemplate(savedContracts.getContractsTemplate().getId());
		if(savedContracts.getStartDate() != null)
			contract.setStartDate(savedContracts.getStartDate());
		if(savedContracts.getEndDate() != null)
			contract.setEndDate(savedContracts.getEndDate());
		if(savedContracts.getJoinDate() != null)
			contract.setJoinDate(savedContracts.getJoinDate());
		if(savedContracts.getWorkLocation() != null)
			contract.setWorkLocation(savedContracts.getWorkLocation());
		if(savedContracts.getInitiatorType() != null)
			contract.setInitiatorType(savedContracts.getInitiatorType());
		if(savedContracts.getOrgLocationId() != null)
			contract.setOrgLocationId(savedContracts.getOrgLocationId().getId());
		if(savedContracts.getParentContractId() != null)
			contract.setParentContractId(savedContracts.getParentContractId().getId());
		if(savedContracts.getContractOrgLocationId() != null)
			contract.setContractOrgLocationId(savedContracts.getContractOrgLocationId().getId());
		if(savedContracts.getContactUser() != null)
			contract.setContactUser(savedContracts.getContactUser().getId());
		if(savedContracts.getOrgRelations() != null)
			contract.setOrgRelations(savedContracts.getOrgRelations().getId());
		if(savedContracts.getApproverUser() != null)
			contract.setApproverUser(savedContracts.getApproverUser().getId());
		if(savedContracts.getContractStatus() != null)
			contract.setContractStatus(savedContracts.getContractStatus());
		if(savedContracts.getDocId() != null)
			contract.setDocId(savedContracts.getDocId());
		if(savedContracts.getDescription() != null)
			contract.setDescription(savedContracts.getDescription());
		if(savedContracts.getBillingAmount() != null)
			contract.setBillingAmount(savedContracts.getBillingAmount());
		if(savedContracts.getBillingRateType() != null)
			contract.setBillingRateType(savedContracts.getBillingRateType());
		if(savedContracts.getBillingRatePer() != null)
			contract.setBillingRateType(savedContracts.getBillingRatePer());
		if(savedContracts.getBillingPer() != null)
			contract.setBillingPer(savedContracts.getBillingPer());
		if(savedContracts.getInvoiceFrequency() != null)
			contract.setInvoiceFrequency(savedContracts.getInvoiceFrequency());
		if(savedContracts.getPaymentTerm() != null)
			contract.setPaymentTerm(savedContracts.getPaymentTerm());
		if(savedContracts.getTax() != null)
			contract.setTax(savedContracts.getTax());
		if(savedContracts.getTaxType() != null)
			contract.setTaxType(savedContracts.getTaxType());
		if(savedContracts.getRequirementSubmission() != null)
			contract.setRequirementSubmission(savedContracts.getRequirementSubmission().getId());
		if(savedContracts.getCandidate() != null)
			contract.setCandidate(savedContracts.getCandidate().getId());
		if(savedContracts.getHoursPerWeek() != null)
			contract.setHoursPerWeek(savedContracts.getHoursPerWeek());
		if(savedContracts.getOverTimeHours() != null)
			contract.setOverTimeHours(savedContracts.getOverTimeHours());
		if(savedContracts.getTotalHours() != null)
			contract.setTotalHours(savedContracts.getTotalHours());
		if(savedContracts.getOvertimeRate() != null)
			contract.setOvertimeRate(savedContracts.getOvertimeRate());
		if(savedContracts.getOvertimeRateType() != null)
			contract.setOvertimeRate(savedContracts.getOvertimeRateType());
		if(savedContracts.getStatus() != null)
			contract.setStatus(savedContracts.getStatus());
		if(savedContracts.getTemplateSubmissionStatus() != null)
			contract.setTemplateSubmissionStatus(savedContracts.getTemplateSubmissionStatus());
		if(savedContracts.getCreatedDate() != null)
			contract.setCreatedDate(savedContracts.getCreatedDate());
		if(savedContracts.getCreatedBy() != null)
			contract.setCreatedBy(savedContracts.getCreatedBy());
		if(savedContracts.getUpdatedBy() != null)
			contract.setUpdatedBy(savedContracts.getUpdatedBy());
		if(savedContracts.getUpdatedDate() != null)
			contract.setUpdatedDate(savedContracts.getUpdatedDate());
		if(savedContracts.getContractUUID() != null)
			contract.setContractUUID(savedContracts.getContractUUID());
		if(savedContracts.getStateJurisdiction() != null)
			contract.setStateJurisdiction(savedContracts.getStateJurisdiction());
		if(savedContracts.getFileName() != null)
			contract.setFileName(savedContracts.getFileName());
		if(savedContracts.getFileType() != null)
			contract.setFileType(savedContracts.getFileType());
		if(savedContracts.getFileURL() != null)
			contract.setFileURL(savedContracts.getFileURL());
		if(savedContracts.getRejectedReason() != null)
			contract.setRejectedReason(savedContracts.getRejectedReason());
		if(savedContracts.getRevisedReason() != null)
			contract.setRevisedReason(savedContracts.getRevisedReason());
		if(savedContracts.getTemplateFileURL() != null)
			contract.setTemplateFileURL(savedContracts.getTemplateFileURL());
		
		StringBuilder sb = new StringBuilder();
		if(savedContracts.getParentContractId() == null){
			if(savedContracts.getOwnerHierarchyList() != null) {
				for(ContractOwnerHierarchy coh : savedContracts.getOwnerHierarchyList()){
					sb.append(coh.getOwnerUserId().getId().toString()).append(",");
				}
				contract.setOwnerUserId(sb.toString().replaceAll("\\s*,\\s*$", ""));
			}
		}else{
			contract.setOwnerUserId(savedContracts.getOwnerUserId().getId().toString());
		}
		
		
		saveContractEditHistory(contract);
	}

	private void saveClientOrVendorContract(Contracts savedContracts, Contracts vendorContracts, User user, OrgLocations orgLocation) throws Exception
	{
		if (null != vendorContracts) {
			vendorContracts.setCreatedDate(new Date());
			if (null != user) {
				vendorContracts.setCreatedBy(user.getUserCode());
				vendorContracts.setUpdatedBy(user.getUserCode());
			}
			vendorContracts.setUpdatedDate(new Date());
			vendorContracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			vendorContracts.setContractsTemplate(savedContracts.getContractsTemplate());
			if (null != savedContracts.getTypeOfContract() && !"".equalsIgnoreCase(savedContracts.getTypeOfContract())
					&& Constants.CUSTOM_TYPE.equalsIgnoreCase(savedContracts.getTypeOfContract())) {
				vendorContracts.setTypeOfContract(Constants.CUSTOM_TYPE);
			}
			else {
				vendorContracts.setTypeOfContract(Constants.NEW_TYPE);
			}
			vendorContracts.setContractTitle(savedContracts.getContractTitle().trim());
			vendorContracts.setStartDate(savedContracts.getStartDate());
			if (savedContracts.getEndDate() != null)
				vendorContracts.setEndDate(savedContracts.getEndDate());
			if (savedContracts.getBillingAmount() != null)
				vendorContracts.setBillingAmount(savedContracts.getBillingAmount());
			if (savedContracts.getBillingPer() != null)
				vendorContracts.setBillingPer(savedContracts.getBillingPer());
			if (savedContracts.getBillingRateType() != null)
				vendorContracts.setBillingRateType(savedContracts.getBillingRateType());
			if (savedContracts.getBillingRatePer() != null)
				vendorContracts.setBillingRatePer(savedContracts.getBillingRatePer());
			if (savedContracts.getInvoiceFrequency() != null)
				vendorContracts.setInvoiceFrequency(savedContracts.getInvoiceFrequency());
			if (savedContracts.getPaymentTerm() != null)
				vendorContracts.setPaymentTerm(savedContracts.getPaymentTerm());
			if (savedContracts.getTax() != null)
				vendorContracts.setTax(savedContracts.getTax());
			if (savedContracts.getTaxType() != null)
				vendorContracts.setTaxType(savedContracts.getTaxType());
			if (savedContracts.getDescription() != null)
				vendorContracts.setDescription(savedContracts.getDescription().trim());
			vendorContracts.setParentContractId(savedContracts);
			if (Constants.FULL_TIME_OFFER.equalsIgnoreCase(savedContracts.getContractType())) {
				if (null != vendorContracts) {
					vendorContracts.setContractType(savedContracts.getContractType());
					vendorContracts.setContractOrgLocationId(orgLocation);
					// vendorContracts.setContactUser(savedContracts.getOwnerUserId());
				}
			}
			if (Constants.VENDOR_MSA.equalsIgnoreCase(savedContracts.getContractType())
					|| Constants.Vendor_SOW.equalsIgnoreCase(savedContracts.getContractType())
					|| Constants.NDA.equalsIgnoreCase(savedContracts.getContractType())
					|| Constants.NSA.equalsIgnoreCase(savedContracts.getContractType())) {
				if (Constants.VENDOR_MSA.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.CLIENT_MSA);
				}
				else if (Constants.Vendor_SOW.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.Client_SOW);
				}
				else if (Constants.NDA.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.CLIENT_NDA);
				}
				else if (Constants.NSA.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.CLIENT_NSA);
				}
				vendorContracts.setInitiatorType(Constants.CLIENT);
				vendorContracts.setContractOrgLocationId(orgLocation);
				vendorContracts.setParentContractId(savedContracts);
				OrgRelations orgRelations = orgRelationsService.getOrRelationsById(savedContracts.getOrgRelations().getId());
				vendorContracts.setOrgRelations(orgRelations);
				if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}
				else if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				User vendorUser = savedContracts.getContactUser();
				vendorContracts.setOwnerUserId(vendorUser);
				// vendorContracts.setContactUser(ownerUser);
			}
			if (Constants.CLIENT_MSA.equalsIgnoreCase(savedContracts.getContractType())
					|| Constants.Client_SOW.equalsIgnoreCase(savedContracts.getContractType())) {
				if (Constants.CLIENT_MSA.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.VENDOR_MSA);
				}
				else {
					vendorContracts.setContractType(Constants.Vendor_SOW);
				}
				vendorContracts.setInitiatorType(Constants.VENDOR);
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractOrgLocationId(orgLocation);
				OrgRelations orgRelations = savedContracts.getOrgRelations();
				vendorContracts.setOrgRelations(orgRelations);
				if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}
				if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				User vendorUser = savedContracts.getContactUser();
				vendorContracts.setOwnerUserId(vendorUser);
				// vendorContracts.setContactUser(ownerUser);
			}else if(Constants.VENDOR_MSA.equalsIgnoreCase(savedContracts.getContractType())
					|| Constants.VENDOR_SOW.equalsIgnoreCase(savedContracts.getContractType())) {
				if (Constants.VENDOR_MSA.equalsIgnoreCase(savedContracts.getContractType())) {
					vendorContracts.setContractType(Constants.CLIENT_MSA);
				}else {
					vendorContracts.setContractType(Constants.CLIENT_SOW);
				}
				
				vendorContracts.setInitiatorType(Constants.VENDOR);
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractOrgLocationId(orgLocation);
				OrgRelations orgRelations = savedContracts.getOrgRelations();
				vendorContracts.setOrgRelations(orgRelations);
				if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}
				if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				User vendorUser = savedContracts.getContactUser();
				vendorContracts.setOwnerUserId(vendorUser);
				
			}
			
			
			
			
			
			// vendorContracts.setContractUUID(contracts.getContractUUID());
			if(Constants.WAITING_TO_BE_VERIFY.equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())){
				vendorContracts.setTemplateSubmissionStatus(Constants.SEND_FOR_VERIFICATION);
			}else{
				vendorContracts.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_SIGNED);
			}
			
			vendorContracts.setAutoGenId(getAutoGeneratedId(vendorContracts,vendorContracts.getOrgLocationId()));
			
			vendorContracts.setStatus("0");
			Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
			if (vendorContracts.getId() == null) {
				ContractsHistory childContractsHistory = new ContractsHistory();
				childContractsHistory.setContractsId(savedChildContracts);
				childContractsHistory.setFromStage("NEW");
				childContractsHistory.setToStage(savedChildContracts.getTemplateSubmissionStatus());
				childContractsHistory.setCreatedDate(new Date());
				if (null != user) {
					childContractsHistory.setCreatedBy(user.getUserCode());
					childContractsHistory.setUpdatedBy(user.getUserCode());
				}
				childContractsHistory.setUpdatedDate(new Date());
				contractsTemplateDao.saveContractsHistory(childContractsHistory);
			}
			
//			if(!Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())) {
                sendContractForSignature(savedContracts,orgLocation,user);
//            }
			
		}
	}

	private Set<ContractOwnerHierarchy> getOwnersList(ContractsDTO contractDTO, Contracts contract, User user) throws Exception {
		Integer status = 0;
		if(contract.getId() != null)
			status = deleteContractOwnerHierarchyListByContractId(contract.getId());
		
		Set<ContractOwnerHierarchy> ownersList = new HashSet<>();
		if("Parallel".equalsIgnoreCase(contractDTO.getOwnersType())){
			for(Long userId : contractDTO.getParallelOwner()){
				ContractOwnerHierarchy cah = new ContractOwnerHierarchy();
				cah.setLevel(1);
				cah.setContract(contract);
				User appUser = userService.getUserById(userId);
				cah.setOwnerUserId(appUser);
				cah.setHierarchyType(HIERARCHY_TYPE.PARALLEL);
				cah.setTemplateSubmissionStatus(contract.getTemplateSubmissionStatus());
				cah.setStatus(Constants.WAITING);
				cah.setCreatedBy(user.getUserCode());
				cah.setCreatedDate(new Date());
				cah.setUpdatedBy(user.getUserCode());
				cah.setUpdatedDate(new Date());
				ownersList.add(cah);
			}
		}else{
			for(int i=1;i<=2;i++){
				ContractOwnerHierarchy cah = new ContractOwnerHierarchy();
				cah.setLevel(i);
				cah.setContract(contract);
				if(i == 1){
					User appUser = userService.getUserById(contractDTO.getSequentialOwner1());
					cah.setOwnerUserId(appUser);
				}else{
					User appUser = userService.getUserById(contractDTO.getSequentialOwner2());
					cah.setOwnerUserId(appUser);
				}
				cah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
				cah.setTemplateSubmissionStatus(contract.getTemplateSubmissionStatus());
				cah.setStatus(Constants.WAITING);
				cah.setCreatedBy(user.getUserCode());
				cah.setCreatedDate(new Date());
				cah.setUpdatedBy(user.getUserCode());
				cah.setUpdatedDate(new Date());
				ownersList.add(cah);
			}
		}
		return ownersList;
	}

	@Override
	public ResponseList<VendorContractsDTO> getAllContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName, String searchValue) throws Exception
	{
		ResponseList<VendorContractsDTO> responseList = new ResponseList<>();
//		Contracts contractsForList = new Contracts();
		ContractsDTO contractsDTO = new ContractsDTO();
		contractsDTO.setContractSubmissionType(contractSubmissionType);
		contractsDTO.setTabType(Constants.VENDOR);
		contractsDTO.setContractsType(contractsType);
		contractsDTO.setContractStatus(contractStatus);
		contractsDTO.setUserRoleName(userRoleName);
		contractsDTO.setSearchValue(searchValue);
		
//		if(tabType.equalsIgnoreCase(Constants.VENDOR)){
		
		
		
			List<Contracts> vendorcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation, user, contractsDTO);
			
			List<VendorContractsDTO> venContractsList = new ArrayList<>();
			for(Contracts contracts :vendorcontractsList ){
				VendorContractsDTO venContractsDto = new VendorContractsDTO();
				StringBuilder ownerName = new StringBuilder();
				StringBuilder vendorName = new StringBuilder();
				List<Long> ownerUserIds = new ArrayList<>();
				if(null != contracts.getOwnerHierarchyList() && contracts.getOwnerHierarchyList().size() > 0){
					for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
						ownerUserIds.add(coh.getOwnerUserId().getId());
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							ownerName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							ownerName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
					
				}else{
					if(contracts.getOwnerUserId() != null) {
						ownerUserIds.add(contracts.getOwnerUserId().getId());
						if(StringUtils.isNotBlank(contracts.getOwnerUserId().getLastName())){
							ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
						}else{
							ownerName.append(contracts.getOwnerUserId().getFirstName());
						}
					}
				}
				venContractsDto.setOwnerName(ownerName.toString().replaceAll("\\s*,\\s*$", ""));
				
				if(ownerUserIds.contains(user.getId())){
					venContractsDto.setIsOwner(Constants.YES);
				}
				
				if(contracts.getContractsTemplate() != null){
				venContractsDto.setTemplateId(contracts.getContractsTemplate().getId().toString());
				}
				
				if(StringUtils.isNotBlank(contracts.getRevisedReason()))
					venContractsDto.setRevisedReason(contracts.getRevisedReason());
				
				if(null != contracts.getContactUser()){
					if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getLastName()){
						vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
					}else{
						vendorName.append(contracts.getContactUser().getFirstName());
					}						
			}else{
				for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
					if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
						vendorName.append(coh.getOwnerUserId().getFirstName()).append(" ").append((coh.getOwnerUserId().getLastName())).append(" , ");
					}else{
						vendorName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
					}
				}
			}
			venContractsDto.setVendorContactName(vendorName.toString().replaceAll("\\s*,\\s*$", ""));
			
			
				
				if(null != contracts.getOrgRelations()){
				 	OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contracts.getOrgRelations().getId());
				 	if(null != orgRelations){
				 		if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				 			venContractsDto.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
						}	
						else if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							venContractsDto.setContractWith(orgRelations.getLocationId().getOrgLocationName());
						}
				 	}
				 	
				 	venContractsDto.setOrgRelationUUID(contracts.getOrgRelations().getUUID());
				}
				
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
				//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
				if(null != contracts && null != contracts.getStartDate()){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					venContractsDto.setStartDateStr(currentDateInTimezone);
				}
				if(null != contracts && null != contracts.getEndDate()){
					String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
					String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
					Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
					endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
					venContractsDto.setEndDateStr(endDateInTimezone);
				}
				
				/*contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
				if(null != contracts.getEndDate()){
					contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
				}*/
				
				if(contracts.getTemplateFileURL() != null){
					venContractsDto.setTemplateFileURL(contracts.getTemplateFileURL());
				}
				
				if(contracts.getId() != null){
					venContractsDto.setId(contracts.getId());
				}
				
				if(contracts.getContractUUID() != null){
					venContractsDto.setContractUUID(contracts.getContractUUID());
				}
				
				if(contracts.getAutoGenId() != null){
					venContractsDto.setAutoGenId(contracts.getAutoGenId());
				}
				
				if(contracts.getContractTitle() != null){
					venContractsDto.setContractTitle(contracts.getContractTitle());
				}
				
				if(contracts.getContractType() != null){
					venContractsDto.setContractType(contracts.getContractType());
				}
				
				if(contracts.getTemplateSubmissionStatus() != null){
					venContractsDto.setTemplateSubmissionStatus(contracts.getTemplateSubmissionStatus());
				}
				
				if(contracts.getTypeOfContract() != null){
					venContractsDto.setTypeOfContract(contracts.getTypeOfContract());
				}
				
				if(contracts.getFileURL() != null){
					venContractsDto.setFileURL(contracts.getFileURL());
				}
				
				if(contracts.getContractStatus() != null){
					venContractsDto.setContractStatus(contracts.getContractStatus());
				}
				
				if(contracts.getFileType() != null){
					venContractsDto.setFileType(contracts.getFileType());
				}
				
				if(contracts.getFileName() != null){
					venContractsDto.setFileName(contracts.getFileName());
				}
				
				if(contracts.getInvoiceFrequency() != null){
					venContractsDto.setBillingCycle(contracts.getInvoiceFrequency());
				}
				
				if(null != contracts.getParentContractId()){
					venContractsDto.setParentContractId(contracts.getParentContractId().getId());
					venContractsDto.setParentId(contracts.getParentContractId().getId().toString());
				}
				else{
					venContractsDto.setParentId("--");
				}

				if(null != contracts.getApproverUser() ){
					venContractsDto.setApproverUserId(contracts.getApproverUser().getId());
					if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
						StringBuilder sb = new StringBuilder();
						venContractsDto.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
					}else{
						venContractsDto.setApproverUserName(contracts.getApproverUser().getFirstName());
					}
				}

				//Contracts childContarct  = contractsTemplateService.getChildContractById(contracts.getId());
				Integer countOfDoc = 0;
	    		if(null != contracts.getParentContractId()){
	    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
	    			venContractsDto.setReqDocCount(countOfDoc);
	    		}else{
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
	    			venContractsDto.setReqDocCount(countOfDoc);
	    		}
	    		venContractsList.add(venContractsDto);
				}
			responseList.setRecordsFiltered(venContractsList.size());
	        responseList.setRecordsTotal(venContractsList.size());
			responseList.setData(venContractsList);
//		}
		      return responseList;
	}
	
	
	@Override
	public ResponseList<ClientContractsDTO> getAllClientContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName, String searchValue) throws Exception
	{
		ResponseList<ClientContractsDTO> responseList = new ResponseList<>();
		ContractsDTO contractsDTO = new ContractsDTO();
		contractsDTO.setContractSubmissionType(contractSubmissionType);
		contractsDTO.setTabType(Constants.CLIENT);
		contractsDTO.setContractsType(contractsType);
		contractsDTO.setContractStatus(contractStatus);
		contractsDTO.setUserRoleName(userRoleName);
		contractsDTO.setSearchValue(searchValue);
	
//		 if(tabType.equalsIgnoreCase(Constants.CLIENT)){
			List<Contracts> clientcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation, user, contractsDTO);
		
			List<ClientContractsDTO> clientContractsList = new ArrayList<>();
			for(Contracts contracts :clientcontractsList ){
				Set<ContractOwnerHierarchy> signingAuthlist = contracts.getOwnerHierarchyList();
				ClientContractsDTO clientConDto = new ClientContractsDTO();
				StringBuilder ownerName = new StringBuilder();
				StringBuilder vendorName = new StringBuilder();
				List<Long> ownerUserIds = new ArrayList<>();
				if(null != contracts.getOwnerHierarchyList() && contracts.getOwnerHierarchyList().size() > 0){
					for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
						ownerUserIds.add(coh.getOwnerUserId().getId());
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							ownerName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							ownerName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
					

				}else{
					if(contracts.getOwnerUserId() != null) {
						ownerUserIds.add(contracts.getOwnerUserId().getId());
						if(StringUtils.isNotBlank(contracts.getOwnerUserId().getLastName())){
							ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
						}else{
							ownerName.append(contracts.getOwnerUserId().getFirstName());
						}
					}
				}

				clientConDto.setOwnerName(ownerName.toString().replaceAll("\\s*,\\s*$", ""));
				
				if(ownerUserIds.contains(user.getId())){
					clientConDto.setIsOwner(Constants.YES);
				}
				
				if(StringUtils.isNotBlank(contracts.getRevisedReason()))
					clientConDto.setRevisedReason(contracts.getRevisedReason());
				
				if(null != contracts.getContactUser()){
					if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getLastName()){
						vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
					}else{
						vendorName.append("---");
					}
				}else{
					for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							vendorName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							vendorName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
				}
				clientConDto.setVendorContactName(vendorName.toString().replaceAll("\\s*,\\s*$", ""));
				
				
				

				
				if(contracts.getContractsTemplate() != null){
				clientConDto.setTemplateId(contracts.getContractsTemplate().getId().toString());
				}
				
				if(null != contracts.getOrgRelations()){
				 	OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(orgLocation.getId(), contracts.getOrgRelations().getId());
				 	if(null != orgRelations){
				 		if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				 			clientConDto.setContractWith(orgRelations.getResponderLocationId().getOrgLocationName());
						}	
						else if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
							clientConDto.setContractWith(orgRelations.getLocationId().getOrgLocationName());
						}
				 	}
				 	
				 	clientConDto.setOrgRelationUUID(contracts.getOrgRelations().getUUID());
				}
				
				contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
				if(null != contracts.getEndDate()){
					contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
				}
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
				//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
				
				if(null != contracts && null != contracts.getStartDate()){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					clientConDto.setStartDateStr(currentDateInTimezone);
				}
				if(null != contracts && null != contracts.getEndDate()){
					String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
					String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
					Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
					endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
					clientConDto.setEndDateStr(endDateInTimezone);
				}
				
				if(contracts.getTemplateFileURL() != null){
					clientConDto.setTemplateFileURL(contracts.getTemplateFileURL());
				}
				
				if(contracts.getId() != null){
					clientConDto.setId(contracts.getId());
				}
				
				if(contracts.getContractUUID() != null){
					clientConDto.setContractUUID(contracts.getContractUUID());
				}
				
				if(contracts.getAutoGenId() != null){
					clientConDto.setAutoGenId(contracts.getAutoGenId());
				}
				
				if(contracts.getContractTitle() != null){
					clientConDto.setContractTitle(contracts.getContractTitle());
				}
				
				if(contracts.getContractType() != null){
					clientConDto.setContractType(contracts.getContractType());
				}
				
				if(contracts.getContractStatus() != null){
					clientConDto.setContractStatus(contracts.getContractStatus());
				}
				
				if(contracts.getTemplateSubmissionStatus() != null){
					clientConDto.setTemplateSubmissionStatus(contracts.getTemplateSubmissionStatus());
				}
				
				if(contracts.getTypeOfContract() != null){
					clientConDto.setTypeOfContract(contracts.getTypeOfContract());
				}
				
				if(contracts.getFileURL() != null){
					clientConDto.setFileURL(contracts.getFileURL());
				}
				
				if(contracts.getFileType() != null){
					clientConDto.setFileType(contracts.getFileType());
				}
				
				if(contracts.getFileName() != null){
					clientConDto.setFileName(contracts.getFileName());
				}
				
				if(contracts.getInvoiceFrequency() != null){
					clientConDto.setBillingCycle(contracts.getInvoiceFrequency());
				}
				
				if(null != contracts.getParentContractId()){
					clientConDto.setParentContractId(contracts.getParentContractId().getId());
					clientConDto.setParentId(contracts.getParentContractId().getId().toString());
				}
				else{
					clientConDto.setParentId("--");
				}
				
				if(null != contracts.getApproverUser()){
					clientConDto.setApproverUserId(contracts.getApproverUser().getId());
					if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
						StringBuilder sb = new StringBuilder();
						clientConDto.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
					}else{
						clientConDto.setApproverUserName(contracts.getApproverUser().getFirstName());
					}
				}
				Integer countOfDoc = 0;
	    		if(null != contracts.getParentContractId()){
	    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
	    			clientConDto.setReqDocCount(countOfDoc);
	    		}else{
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
	    			clientConDto.setReqDocCount(countOfDoc);
	    		}
	    		if(userRoleName.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
	    			clientContractsList.add(clientConDto);	
	    		}else if(ownerUserIds.contains(user.getId()) && (Constants.WAITING_TO_SEND.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) || Constants.WAITING_TO_BE_VERIFY.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) || Constants.FINALIZED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()))) {
	    			if(!Constants.WAITING_TO_BE_SIGNED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
		    			clientContractsList.add(clientConDto);
		    		}
		    	}else if(user.getUserCode().equalsIgnoreCase(contracts.getCreatedBy())) {
		    		if(!Constants.WAITING_TO_SEND.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) && !Constants.WAITING_TO_BE_SIGNED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
		    			clientContractsList.add(clientConDto);
		    		}
		    	}else if(contracts.getApproverUser() != null && user.getId().longValue() == contracts.getApproverUser().getId().longValue()) {
		    		if(!Constants.WAITING_TO_SEND.equalsIgnoreCase(contracts.getTemplateSubmissionStatus()) && !Constants.WAITING_TO_BE_SIGNED.equalsIgnoreCase(contracts.getTemplateSubmissionStatus())) {
		    			clientContractsList.add(clientConDto);
		    		}
		    	}
	    		
			}
			responseList.setRecordsFiltered(clientContractsList.size());
			responseList.setRecordsTotal(clientContractsList.size());
			responseList.setData(clientContractsList);
			return responseList;
		}
		 
@Override
public ResponseList<FullTimeContractsDTO> getAllApplicantContractsByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName, String searchValue) throws Exception
	{
		ResponseList<FullTimeContractsDTO> responseList = new ResponseList<>();
		ContractsDTO contractsDTO = new ContractsDTO();
		contractsDTO.setContractSubmissionType(contractSubmissionType);
		contractsDTO.setTabType(Constants.FULL_TIME_OFFER);
		contractsDTO.setContractsType(contractsType);
		contractsDTO.setContractStatus(contractStatus);
		contractsDTO.setUserRoleName(userRoleName);
		contractsDTO.setSearchValue(searchValue);
	
//	else if(tabType.equalsIgnoreCase(Constants.FULL_TIME_OFFER)){
			List<Contracts> fullTimeoffercontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation, user, contractsDTO);

			//fullTimeoffercontractsList 
			List<FullTimeContractsDTO> fullTimeContractsList = new ArrayList<>();
			for(Contracts contracts : fullTimeoffercontractsList ){
				FullTimeContractsDTO fullTimeContract = new FullTimeContractsDTO();
				
				StringBuilder ownerName = new StringBuilder();
				if(null != contracts.getOwnerHierarchyList()){
					List<Long> ownerUserIds = new ArrayList<>();
					for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
						ownerUserIds.add(coh.getOwnerUserId().getId());
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							ownerName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							ownerName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
					
				}else{
					if(StringUtils.isNotBlank(contracts.getOwnerUserId().getLastName())){
						ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
					}else{
						ownerName.append(contracts.getOwnerUserId().getFirstName());
					}
				}
				fullTimeContract.setOwnerName(ownerName.toString().replaceAll("\\s*,\\s*$", ""));
				
				if(contracts.getOwnerHierarchyList().contains(user.getId())){
					fullTimeContract.setIsOwner(Constants.YES);
				}
				
				if(contracts.getContractsTemplate() != null){
				fullTimeContract.setTemplateId(contracts.getContractsTemplate().getId().toString());
				}
				
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
			//	SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
				
				if(null != contracts && null != contracts.getJoinDate()){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getJoinDate());  
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
					fullTimeContract.setJoinedDateStr(currentDateInTimezone);
				}
				else{
					fullTimeContract.setJoinedDateStr("--");
				}
				
				if(contracts.getId() != null){
					fullTimeContract.setId(contracts.getId());
				}
				
				if(contracts.getContractUUID() != null){
					fullTimeContract.setContractUUID(contracts.getContractUUID());
				}
				
				if(contracts.getContractTitle() != null){
					fullTimeContract.setContractTitle(contracts.getContractTitle());
				}
				
				if(contracts.getContractType() != null){
					fullTimeContract.setContractType(contracts.getContractType());
				}
				
				if(contracts.getTemplateSubmissionStatus() != null){
					fullTimeContract.setTemplateSubmissionStatus(contracts.getTemplateSubmissionStatus());
				}
				
				if(contracts.getTypeOfContract() != null){
					fullTimeContract.setTypeOfContract(contracts.getTypeOfContract());
				}
				
				if(contracts.getContractStatus() != null){
					fullTimeContract.setContractStatus(contracts.getContractStatus());
				}
				
				if(contracts.getBillingAmount() != null){
					fullTimeContract.setBillingAmount(contracts.getBillingAmount());
				}
				
				if(contracts.getFileURL() != null){
					fullTimeContract.setFileURL(contracts.getFileURL());
				}
				
				if(contracts.getFileType() != null){
					fullTimeContract.setFileType(contracts.getFileType());
				}
				
				if(contracts.getFileName() != null){
					fullTimeContract.setFileName(contracts.getFileName());
				}
				
				if(null != contracts.getParentContractId()){
					fullTimeContract.setParentContractId(contracts.getParentContractId().getId());
					fullTimeContract.setParentId(contracts.getParentContractId().getId().toString());
				}
				else{
					fullTimeContract.setParentId("--");
				}
				RequirementSubmission reqSub = requirementSubmissionService.getRequirementSubmissionById(contracts.getRequirementSubmission().getId());
	        	if(null != reqSub.getCandidate().getLastName()){
	        		fullTimeContract.setEmpName(reqSub.getCandidate().getFirstName()+" "+reqSub.getCandidate().getLastName());	
				}else{
					fullTimeContract.setEmpName(reqSub.getCandidate().getFirstName());
				}
				
				if(null != contracts.getApproverUser()){
					fullTimeContract.setApproverUserId(contracts.getApproverUser().getId());
					if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
						StringBuilder sb = new StringBuilder();
						fullTimeContract.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
					}else{
						fullTimeContract.setApproverUserName(contracts.getApproverUser().getFirstName());
					}
				}
				Integer countOfDoc = 0;
	    		if(null != contracts.getParentContractId()){
	    			Contracts parentcontracts  = contractsTemplateDao.getContractById(contracts.getParentContractId().getId());
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(parentcontracts.getId());
	    			fullTimeContract.setReqDocCount(countOfDoc);
	    		}else{
	    			countOfDoc  = contractRequestDocumentsService.getCountOfDocByContractedId(contracts.getId());
	    			fullTimeContract.setReqDocCount(countOfDoc);
	    		}
	    		fullTimeContractsList.add(fullTimeContract);
			}
			responseList.setRecordsFiltered(fullTimeContractsList.size());
			responseList.setRecordsTotal(fullTimeContractsList.size());
			responseList.setData(fullTimeContractsList);
			
//		}
		
//		contractsForList.setVendorcontractsList(vendorcontractsList);
//		contractsForList.setClientcontractsList(clientcontractsList);
//		contractsForList.setFullTimeoffercontractsList(fullTimeoffercontractsList);
		return responseList;
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
		
//		Integer totalDraftVendorCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.VENDOR, contractsType, Constants.DRAFT, user, userRoleName);
//		Integer totalDraftClientCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.CLIENT, contractsType, Constants.DRAFT, user, userRoleName);
//		Integer totalDraftApplicantCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,Constants.APPLICANT, contractsType, Constants.APPLICANT, user, userRoleName);
		
		Contracts contractsForList = new Contracts();
		
		contractsForList.setTotalDraftCount(totalDraftCount);
		contractsForList.setTotalWTACount(totalWTACount);
		contractsForList.setTotalWTSCount(totalWTSCount);
		contractsForList.setTotalWTVCount(totalWTVCount);
		contractsForList.setTotalFinalizedCount(totalFinalizedCount);
		contractsForList.setTotalExpiredCount(totalExpiredCount);
		contractsForList.setTotalRejectedCount(totalRejectedCount);
		
//		contractsForList.setTotalDraftVendorCount(totalDraftVendorCount);
//		contractsForList.setTotalDraftClientCount(totalDraftClientCount);
//		contractsForList.setTotalDraftApplicantCount(totalDraftApplicantCount);
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
	public Contracts approveContracts(long recordId, String status, OrgLocations orgLocation, User user, Org org, String rejectReason) throws Exception
	{
		Contracts contracts = contractsTemplateDao.getContractById(recordId);
		Contracts upadatedContracts = null;
		if (!Constants.REJECTED.equalsIgnoreCase(status)) {
			contracts.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
			// upadatedContracts.setTypeOfContract(Constants.NEW_TYPE);
			upadatedContracts = contractsTemplateDao.saveNewContract(contracts);
			ContractsHistory contractsHistory = new ContractsHistory();
			contractsHistory.setFromStage(Constants.WAITING_FOR_APPROVER);
			contractsHistory.setContractsId(contracts);
			contractsHistory.setToStage(Constants.WAITING_TO_SEND);
			contractsHistory.setCreatedDate(new Date());
			contractsHistory.setUpdatedDate(new Date());
			contractsHistory.setCreatedBy(user.getUserCode());
			contractsHistory.setUpdatedBy(user.getUserCode());
			contractsTemplateDao.saveContractsHistory(contractsHistory);
			contractApproveEmail(contracts,user,org,orgLocation);
			sendMailToOwner(contracts,user,org,orgLocation);
			// if(!Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType()))
			// {
			// sendContractForSignature(contracts, orgLocation, user);
			// }
		}
		else {
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
		if(null != orgLocation.getTimeZoneId()){
	    	 timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		 }
		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(createdUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 
		//Url for redirection
		String redirectURL = "/contractListbyStatus-params-"+"contractType="+contracts.getContractType()+"-and-status=rejected";		
		Long redirectOrgLocationId = orgLocation.getId();	
		 
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
		 
		StringBuilder ownerSb = new StringBuilder();
		for (ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()) {
			if (StringUtils.isNotBlank(coh.getOwnerUserId().getFirstName())
					&& StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())) {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
			}
			else {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(", ");
			}
		}
		globalVarsMap.put("OwnerName", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
			
			 if(contracts.getRejectedReason()!= null && !"".equalsIgnoreCase(contracts.getRejectedReason())){
				 globalVarsMap.put("rejectReason", contracts.getRejectedReason());
				 }else{
					 globalVarsMap.put("rejectReason","--");
				 }
			 
			 if(contracts.getContactUser() != null){
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 
				 StringBuilder builder=new StringBuilder();
				 globalVarsMap.put("clientContact",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString()); 
			 }
			 else{
			 globalVarsMap.put("clientContact",contracts.getContactUser().getFirstName());
			 }
			 }
			 StringBuilder linkBuild = new StringBuilder();

			 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +vmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Rejected Contract</div></a>").toString());
		
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
			globalVarsMap.put("contractCreator", creatorSb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
		}else if(createdUser.getFirstName()!= null && !"".equalsIgnoreCase(createdUser.getFirstName())){
			globalVarsMap.put("contractCreator",createdUser.getFirstName());
		}else{
			globalVarsMap.put("contractCreator","--");
		}
		
		 if(loginUser.getFirstName() != null && loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("approverUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("approverUser",loginUser.getFirstName());
			}
		 
			 globalVarsMap.put("LoginOrg",org.getOrgName());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, null, orgLocation, Constants.REJECT_CONTRACT,null);
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 /*//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;*/
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 /*//construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);*/
		
//		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(vmsUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=rejected").toString();

//		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());

		 emailScenerioUtil.sendNotification(actionLink, loginUser,createdUser, orgLocation, notificationScenario,new HashMap<String,String>());

	}

	private void contractApproveEmail(Contracts contracts, User loginUser, Org org, OrgLocations orgLocation) throws Exception
	{
		Timezone timezone = null;
		if (null != orgLocation.getTimeZoneId()) {
			timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		}
		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(createdUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		//Url for redirection
		String redirectURL = "/contractListbyStatus-params-"+"contractType="+contracts.getContractType()+"-and-status=waiting";
		Long redirectOrgLocationId = orgLocation.getId();	
		
		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder msgBuild = new StringBuilder();
			emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			emailMessage.setFromName(loginUser.getFirstName());
		}
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (contracts.getContractTitle() != null
				&& !"".equalsIgnoreCase(contracts.getContractTitle())) {
			globalVarsMap.put("contractTitle", contracts.getContractTitle());
		}
		else {
			globalVarsMap.put("contractTitle", "--");
		}
		if (createdUser.getFirstName() != null && !"".equalsIgnoreCase(createdUser.getFirstName())
				&& createdUser.getLastName() != null
				&& !"".equalsIgnoreCase(createdUser.getLastName())) {
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("contractCreator", creatorSb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
		}
		else if (createdUser.getFirstName() != null
				&& !"".equalsIgnoreCase(createdUser.getFirstName())) {
			globalVarsMap.put("contractCreator", createdUser.getFirstName());
		}
		else {
			globalVarsMap.put("contractCreator", "--");
		}
		
		StringBuilder ownerSb = new StringBuilder();
		for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
			if (StringUtils.isNotBlank(coh.getOwnerUserId().getFirstName()) && StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())) {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
			}
			else {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(", ");
			}
		}
		globalVarsMap.put("OwnerName", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
		
		if(contracts.getContactUser() != null) {
			if (contracts.getContactUser().getLastName() != null
					&& !"".equalsIgnoreCase(contracts.getContactUser().getLastName())) {
				StringBuilder builder = new StringBuilder();
				globalVarsMap.put("clientContact", builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
			}
			else {
				globalVarsMap.put("clientContact", contracts.getContactUser().getFirstName());
			}
		}
	
		StringBuilder linkBuild = new StringBuilder();

		globalVarsMap.put("loginLink", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +vmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Approved Contract</div></a>").toString());		

		// SimpleDateFormat
		// dateFormat=orgLocation.getLocFullFormDateFormatter();
		if (contracts.getStartDate() != null) {
			String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
			if (null != timezone && null != timezone.getTimeZoneId()) {
				String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), converteDate);
				globalVarsMap.put("StartDate", currentDateInTimezone);
			}
		}
		else {
			globalVarsMap.put("StartDate", "--");
		}
		if (contracts.getEndDate() != null) {
			String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
			if (null != timezone && null != timezone.getTimeZoneId()) {
				String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), converteDate);
				globalVarsMap.put("EndDate", currentDateInTimezone);
			}
		}

		else {
			globalVarsMap.put("EndDate", "--");
		}
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName())
				&& !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("approverUser", userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("approverUser", loginUser.getFirstName());
		}
		globalVarsMap.put("LoginOrg", org.getOrgName());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, null, orgLocation, Constants.APPROVE_CONTRACT,null);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_CONTRACT, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		/*String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;*/
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		/*String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocation.getOrg(), orgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);*/
//		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		String actionLink = "";
//		emailScenerioUtil.sendEmail(emailMessage, orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		emailScenerioUtil.sendNotification(actionLink, loginUser, createdUser, orgLocation, notificationScenario,new HashMap<String,String>());

	}

	private void sendMailToContractApprover(User loginUser, User approverUser, Contracts contracts, OrgLocations orgLocation,String redirectURL
			,Long redirectOrgLocationId) throws Exception
	{
		Timezone timezone = null;
		if(null != orgLocation.getTimeZoneId()){
	    	 timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
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
		
		
		StringBuilder ownerSb = new StringBuilder();
		for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
			if (StringUtils.isNotBlank(coh.getOwnerUserId().getFirstName()) && StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())) {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
			}
			else {
				ownerSb.append(coh.getOwnerUserId().getFirstName()).append(", ");
			}
		}
		globalVarsMap.put("OwnerName", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
		
		 StringBuilder linkBuild = new StringBuilder();

		 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +vmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Apporve Contract</div></a>").toString());

	
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
		 
		 
//		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, null, orgLocation, Constants.APPROVER_UPDATED_CONTRACT,null);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 /*//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;*/
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 /*//construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);*/
		
	
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(vmsUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=waiting").toString();
//		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario,new HashMap<String,String>());
		
	}
	
	private void sendMailToOwner(Contracts contracts, User loginUser, Org org, OrgLocations orgLocation) throws Exception
	{
		if(contracts.getOwnerHierarchyList() != null) {
		for (ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()) {
			User owner = coh.getOwnerUserId();
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(loginUser.getFirstName());
			emailMessage.setLastName(loginUser.getLastName());
			emailMessage.setToEmail(owner.getEmail());
			emailMessage.setFromEmail(loginUser.getEmail());
			emailMessage.setCreatedBy(loginUser.getUserCode());
			// SimpleDateFormat
			// dateFormat=orgLocation.getLocFullFormDateFormatter();
			if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
				StringBuilder msgBuild = new StringBuilder();
				emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			}
			else {
				emailMessage.setFromName(loginUser.getFirstName());
			}
			// setting the class filed values
			Map<String, String> globalVarsMap = new HashMap<String, String>();
			if (contracts.getContractTitle() != null
					&& !"".equalsIgnoreCase(contracts.getContractTitle())) {
				globalVarsMap.put("contractTitle", contracts.getContractTitle());
			}
			else {
				globalVarsMap.put("contractTitle", "--");
			}
			if (contracts.getContractType() != null
					&& !"".equalsIgnoreCase(contracts.getContractType())) {
				globalVarsMap.put("contractType", contracts.getContractType());
			}
			else {
				globalVarsMap.put("contractType", "--");
			}
			if (contracts.getEndDate() != null
					&& !"".equalsIgnoreCase(contracts.getEndDate().toString())) {
				String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimeZoneName(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), interviewConverteDate);
				globalVarsMap.put("endDate", interviewDateInTimezone);
			}
			else {
				globalVarsMap.put("endDate", "--");
			}
			if (contracts.getStartDate() != null
					&& !"".equalsIgnoreCase(contracts.getStartDate().toString())) {
				String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String interviewDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimeZoneName(), currentTimeStr);
				Date interviewConverteDate = TZ_DF_TS.parse(interviewDateInTimezone);
				interviewDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), interviewConverteDate);
				globalVarsMap.put("startDate", interviewDateInTimezone);
			}
			else {
				globalVarsMap.put("startDate", "--");
			}
			if (owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName())
					&& owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())) {
				StringBuilder creatorSb = new StringBuilder();
				globalVarsMap.put("ownerUser", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
			}
			else if (owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName())) {
				globalVarsMap.put("ownerUser", owner.getFirstName());
			}
			else {
				globalVarsMap.put("ownerUser", "--");
			}
			if (loginUser.getFirstName() != null && loginUser.getLastName() != null
					&& !"".equalsIgnoreCase(loginUser.getFirstName())
					&& !"".equalsIgnoreCase(loginUser.getLastName())) {
				StringBuilder userBuild = new StringBuilder();
				globalVarsMap.put("contractCreator", userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			}
			else {
				globalVarsMap.put("contractCreator", loginUser.getFirstName());
			}
			globalVarsMap.put("loginOrg", org.getOrgName());
			// getting the current EmailScenario by EmailScenario unique name
			// and entity type
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, null, orgLocation, Constants.CONTRACT_MAIL_TO_OWNER,null);
//			EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_MAIL_TO_OWNER, Constants.ENTITY_TYPE_EMAIL);
			EmailScenario notificationScenario =
			 emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);
			// covert subject,body and signature
			/*String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
			String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap);
			String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
			String url = sourceleadUrl;*/
//			 String notificationbody =
//			 emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),
//			 globalVarsMap); notificationScenario.setBody(notificationbody);
			
			// construct HTML template using required data
			/*String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocation.getOrg(), orgLocation);
			emailMessage.setSubject(subject);
			emailMessage.setMessageBody(emailTemplate);*/
			InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			String actionLink = "";
//			emailScenerioUtil.sendEmail(emailMessage, orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
			// emailScenerioUtil.sendNotification(actionLink,
			// loginUser,approverUser, orgLocation, notificationScenario);
		}
		}
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
	public Contracts saveExistigContract(ContractsDTO contractsDTO, OrgLocations orgLocation, User user, Org org) throws Exception {
		Contracts contracts = new Contracts();
		Contracts vendorContracts = new Contracts();
        
        Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		
		if(Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType())){
			contracts.setContractType("Vendor "+contractsDTO.getContractType());
		}
		else{
			contracts.setContractType(contractsDTO.getContractType());
        }
		
		if(StringUtils.isNotBlank(contractsDTO.getSubmissionOffersList())){
			if(Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType()) ||  Constants.Client_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
				Candidate can = candidateService.getCandidateByCandidateId(Long.parseLong(contractsDTO.getSubmissionOffersList()));
				contracts.setCandidate(can);
			}else{
				RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(contractsDTO.getSubmissionOffersList()));
				contracts.setRequirementSubmission(rs);
				//contracts.setCandidate(rs.getCandidate());	
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getHoursPerWeek())){
			contracts.setHoursPerWeek(contractsDTO.getHoursPerWeek());
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
		
		/*if(jsonObj.has("overtimeRateType") && null != jsonObj.getString("overtimeRateType") && !"".equals(jsonObj.getString("overtimeRateType"))){
			contracts.setOvertimeRateType(jsonObj.getString("overtimeRateType"));
		}*/
		if(StringUtils.isNotBlank(contractsDTO.getOvertimeRateType())){
			contracts.setOvertimeRateType(contractsDTO.getOvertimeRateType());
		}
		
		contracts.setTypeOfContract(Constants.EXISTING_TYPE);
		vendorContracts.setTypeOfContract(Constants.EXISTING_TYPE);
//		contracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		contracts.setContractTitle(contractsDTO.getContractTitle());
		vendorContracts.setContractTitle(contractsDTO.getContractTitle().trim());
//		User ownerUser = userService.getUserById(Long.parseLong(jsonObj.getString("owner")));
		
		contracts.setOwnerHierarchyList(getOwnersList(contractsDTO, contracts, user));
		
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
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
		if (StringUtils.isNotBlank(contractsDTO.getWorkLocation())) {
			contracts.setWorkLocation(contractsDTO.getWorkLocation());
			if(null != vendorContracts){
				vendorContracts.setWorkLocation(contractsDTO.getWorkLocation());
			}
		}
		
		if (StringUtils.isNotBlank(contractsDTO.getJoinDate())) {
			Date joinTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getJoinDate());
			String currentTimeStr = Constants.TZ_DF.format(joinTime);
			String startTimeUTC = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			contracts.setJoinDate(convertedDate);
			if(null != vendorContracts){
				vendorContracts.setJoinDate(convertedDate);
			}
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getEndDate())){
				Date endTimeStr = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),contractsDTO.getEndDate());  
				String endDateStr = Constants.TZ_DF.format(endTimeStr);  
				String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", endDateStr);
				Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
				contracts.setEndDate(convertedEndDate);
				vendorContracts.setEndDate(convertedEndDate);
		}
		User approverUser = null;
		User vendorContactsUser = null;
		if((Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType()))){
			contracts.setInitiatorType(Constants.CLIENT);
			contracts.setOrgLocationId(orgLocation);
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
		if(Constants.CLIENT_MSA.equalsIgnoreCase(contractsDTO.getContractType())||Constants.Client_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
			contracts.setInitiatorType(Constants.VENDOR);
			contracts.setOrgLocationId(orgLocation);
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
		
		if(StringUtils.isNotBlank(contractsDTO.getPercentageReward())){
			contracts.setBillingAmount(contractsDTO.getPercentageReward());
			vendorContracts.setBillingAmount(contractsDTO.getPercentageReward());
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
			if(StringUtils.isNotBlank(contractsDTO.getFlatReward())){
				contracts.setBillingAmount(contractsDTO.getFlatReward());
				if(null != vendorContracts){
					vendorContracts.setBillingAmount(contractsDTO.getFlatReward());
				}
				
				/*if(jsonObj.has("flatRateType") && null != jsonObj.getString("flatRateType") && !"".equals(jsonObj.getString("flatRateType")) ){
					contracts.setBillingPer(jsonObj.getString("flatRateType"));
					if(null != vendorContracts){
						vendorContracts.setBillingPer(jsonObj.getString("flatRateType"));
					}
				}*/
				if(StringUtils.isNotBlank(contractsDTO.getFlatRateType())){
					contracts.setBillingPer(contractsDTO.getFlatRateType());
					if(null != vendorContracts){
						vendorContracts.setBillingPer(contractsDTO.getFlatRateType());
					}
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRateType())){
				contracts.setBillingRateType(contractsDTO.getBillingRateType());
				if(null != vendorContracts){
					vendorContracts.setBillingRateType(contractsDTO.getBillingRateType());
				}
			}
			
			if(StringUtils.isNotBlank(contractsDTO.getBillingRatePer())){
				contracts.setBillingRatePer(contractsDTO.getBillingRatePer());
				if(null != vendorContracts){
					vendorContracts.setBillingRatePer(contractsDTO.getBillingRatePer());
				}
			}
//		}
		
		/*if(multiFile != null && multiFile.getBytes() != null && !"".equalsIgnoreCase(multiFile.getOriginalFilename())){
			contracts.setDocFile(multiFile.getBytes());
			contracts.setFileName(multiFile.getOriginalFilename());
			contracts.setFileType(multiFile.getContentType());
		 }*/
			FileInfo info = null;
		if (contractsDTO.getFileInfo() != null) {
				info = contractsDTO.getFileInfo().get(0);
				if(info != null){
				contracts.setFileURL(info.getFileURL());
				contracts.setFileName(info.getFileName());
				contracts.setFileType(info.getContentType());
				
				vendorContracts.setFileURL(info.getFileURL());
				vendorContracts.setFileName(info.getFileName());
				vendorContracts.setFileType(info.getContentType());
				
				}
				//move the document file temp to your(ContractDocs here) folder
				FileUploadUtil.moveFile(contractsDTO.getStaticPath(), info, "ContractDocs");
			}
		
		contracts.setInvoiceFrequency(contractsDTO.getInvoiceFrequency());
		vendorContracts.setInvoiceFrequency(contractsDTO.getInvoiceFrequency());
		
		if(StringUtils.isNotBlank(contractsDTO.getPaymentFrequency())){
			contracts.setPaymentTerm(contractsDTO.getPaymentFrequency());
			vendorContracts.setPaymentTerm(contractsDTO.getPaymentFrequency());
		}
		
		if(StringUtils.isNotBlank(contractsDTO.getTaxPercentageReward())){
			contracts.setTax(contractsDTO.getTaxPercentageReward());
			vendorContracts.setTax(contractsDTO.getTaxPercentageReward());
			contracts.setTaxType("Percentage");
			vendorContracts.setTaxType("Percentage");
		}
		if(StringUtils.isNotBlank(contractsDTO.getTaxFlatReward())){
			contracts.setTax(contractsDTO.getTaxFlatReward());
			vendorContracts.setTax(contractsDTO.getTaxFlatReward());
			contracts.setTaxType(contractsDTO.getTaxFlatRateType());
			vendorContracts.setTaxType(contractsDTO.getTaxFlatRateType());
		}
		
		
		if(StringUtils.isNotBlank(contractsDTO.getDescription())){
			contracts.setDescription(contractsDTO.getDescription().trim());
			vendorContracts.setDescription(contractsDTO.getDescription().trim());
		}
		if(StringUtils.isNotBlank(contractsDTO.getJurisdiction())){
			contracts.setStateJurisdiction(contractsDTO.getJurisdiction());
		}
		contracts.setStatus("0");
		contracts.setOrgLocationId(orgLocation);
		
		Date date = new Date();
		String currentTimeStr = TZ_DF_TS.format(date);
		Date curDate = TZ_DF_TS.parse(currentTimeStr);
		if(contracts.getEndDate() != null && (contracts.getEndDate().before(curDate)|| contracts.getEndDate().equals(curDate))){
			vendorContracts.setTemplateSubmissionStatus(Constants.EXPIRED);
			contracts.setTemplateSubmissionStatus(Constants.EXPIRED);
		}else{
			vendorContracts.setTemplateSubmissionStatus(Constants.FINALIZED);
			contracts.setTemplateSubmissionStatus(Constants.FINALIZED);
		}
		
		vendorContracts.setStatus("0");
		contracts.setCreatedDate(new Date());
		contracts.setCreatedBy(user.getUserCode());
		contracts.setUpdatedDate(new Date());
		contracts.setUpdatedBy(user.getUserCode());
		contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		contracts.setAutoGenId(getAutoGeneratedId(contracts,orgLocation));
		
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contractsDTO.getContractType())){
			if(null != vendorContracts){
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractType(contractsDTO.getContractType());	
				vendorContracts.setContractOrgLocationId(orgLocation);
				vendorContracts.setContactUser(savedContracts.getOwnerUserId());
			}
		}
			//if(Constants.VENDOR_MSA.equalsIgnoreCase(jsonObj.getString("contractType")) || Constants.Vendor_SOW.equalsIgnoreCase(jsonObj.getString("contractType"))){
		if(Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contractsDTO.getContractType())|| Constants.NDA.equalsIgnoreCase(contractsDTO.getContractType()) || Constants.NSA.equalsIgnoreCase(contractsDTO.getContractType())){
			if(Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.CLIENT_MSA);	
			}else if(Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.Client_SOW);
			}
			else if(Constants.VENDOR_NDA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.CLIENT_NDA);
			}
			else if(Constants.VENDOR_NSA.equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType(Constants.CLIENT_NSA);
			}
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
			}
			if(Constants.CLIENT_MSA.equalsIgnoreCase(contractsDTO.getContractType())||Constants.Client_SOW.equalsIgnoreCase(contractsDTO.getContractType())){
				if(Constants.VENDOR_MSA.equalsIgnoreCase(contractsDTO.getContractType())){
					vendorContracts.setContractType(Constants.VENDOR_MSA);
				}
				else{
					vendorContracts.setContractType(Constants.Vendor_SOW);
				}
				vendorContracts.setInitiatorType(Constants.VENDOR);
				vendorContracts.setParentContractId(savedContracts);
				vendorContracts.setContractOrgLocationId(orgLocation);
				OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(contractsDTO.getClientOrgRelationId()));
				vendorContracts.setOrgRelations(orgRelations);
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getLocationId());
				}else if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
				}
				User vendorUser = userService.getUserById(Long.parseLong(contractsDTO.getClientContact()));
				vendorContracts.setOwnerUserId(vendorUser);
			}
			vendorContracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			vendorContracts.setAutoGenId(getAutoGeneratedId(vendorContracts,orgLocation));
			Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
			
			if(contractsDTO.getDocument() !=null && contractsDTO.getDocument().size() >0) {
				for (ContractDocumentsDTO doc : contractsDTO.getDocument()) {
					for(FileInfo file : contractsDTO.getReqDocs()){
						if(doc.getDocFileName() != null && doc.getDocFileName().equalsIgnoreCase(file.getFileName())){
							ContractRequestDocuments contractRequestDocuments = new ContractRequestDocuments();
							contractRequestDocuments.setFileURL(file.getFileURL());
							contractRequestDocuments.setDocFileName(file.getFileName());
							contractRequestDocuments.setFileType(file.getContentType());
							contractRequestDocuments.setFileName(doc.getDocumentName());
							contractRequestDocuments.setDocType(doc.getCandiDoc());
							contractRequestDocuments.setDescription(doc.getDescription());
							
							FileUploadUtil.moveFile(contractsDTO.getStaticPath(), file, "RequestDocuments");
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

			}
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
		globalVarsMap.put("loginUrl",loginBuilder.append("<a href='").append(vmsUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		 if(contracts.getContactUser().getFirstName() != null && contracts.getContactUser().getLastName()!=null && !"".equalsIgnoreCase(contracts.getContactUser().getFirstName()) && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("receiverParty",userBuild.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString());
			 }else{
				 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
			}
		 
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.CONTRACT_DOCUMENTS_RECEIVED,null);
//		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_DOCUMENTS_RECEIVED,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_DOCUMENTS_RECEIVED,Constants.ENTITY_TYPE_NOTIFICATION);

		
		/* //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;*/
		 
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		/* //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);*/
		
//		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
//		 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(actionLink, loginUser,owner, contracts.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
		
	}

	@Override
	public List<Contracts> getAllClientOrVendorContractsBasedOnLocationId(OrgLocations orgLocations, String responderType, User user, String userRoleName,String contractStatus,Long orgRelationId) throws Exception
	{
		List<Contracts> contractList = contractsTemplateDao.getAllClientOrVendorContractsBasedOnLocationId(orgLocations, responderType, user, userRoleName, contractStatus,orgRelationId);
		for (Contracts contracts : contractList) {
			if (Constants.VENDOR.equalsIgnoreCase(responderType)) {
				StringBuilder ownerName = new StringBuilder();
				StringBuilder vendorName = new StringBuilder();
				List<Long> ownerUserIds = new ArrayList<>();
				//if (null != contracts.getOwnerUserId()) {
				if(null != contracts.getOwnerHierarchyList() && !contracts.getOwnerHierarchyList().isEmpty()){
					for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
						ownerUserIds.add(coh.getOwnerUserId().getId());
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							ownerName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							ownerName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
					
				}else{
					ownerUserIds.add(contracts.getOwnerUserId().getId());
					if(StringUtils.isNotBlank(contracts.getOwnerUserId().getLastName())){
						ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
					}else{
						ownerName.append(contracts.getOwnerUserId().getFirstName());
					}
				}
				contracts.setOwnerName(ownerName.toString().replaceAll("\\s*,\\s*$", ""));
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
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
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
				List<Long> ownerUserIds = new ArrayList<>();
				if(null != contracts.getOwnerHierarchyList() && !contracts.getOwnerHierarchyList().isEmpty()){
					for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
						ownerUserIds.add(coh.getOwnerUserId().getId());
						if(null != coh.getOwnerUserId().getFirstName() && null != coh.getOwnerUserId().getLastName()){
							ownerName.append(coh.getOwnerUserId().getFirstName()).append((coh.getOwnerUserId().getLastName())).append(" , ");
						}else{
							ownerName.append(coh.getOwnerUserId().getFirstName()).append(" , ");
						}
					}
					
				}else{
					ownerUserIds.add(contracts.getOwnerUserId().getId());
					if(StringUtils.isNotBlank(contracts.getOwnerUserId().getLastName())){
						ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
					}else{
						ownerName.append(contracts.getOwnerUserId().getFirstName());
					}
				}
				contracts.setOwnerName(ownerName.toString().replaceAll("\\s*,\\s*$", ""));
				
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
				Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
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
		// URL for redirection
		String redirectURL = "/viewContract-params-"+"contractUUID="+contracts.getContractUUID();
		Long redirectOrgLocationId = contracts.getOrgLocationId().getId();
		
		if(null != orgLocation.getTimeZoneId()){
	    	 timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		 }
//		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())) {
			 emailMessage.setToEmail(contracts.getCandidate().getConsultantUser().getEmail());
		 }else {
			 emailMessage.setToEmail(contracts.getContactUser().getEmail());
		 }
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
		 
		 if(contracts.getContactUser() != null) {
			 if(contracts.getContactUser().getLastName() !=null && !"".equalsIgnoreCase(contracts.getContactUser().getLastName())){
				 StringBuilder builder=new StringBuilder();
				 globalVarsMap.put("receiverParty",builder.append(contracts.getContactUser().getFirstName()).append(" ").append(contracts.getContactUser().getLastName()).toString()); 
			 }
			 else{
				 globalVarsMap.put("receiverParty",contracts.getContactUser().getFirstName());
			 }
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
			
		if(!Constants.NDA.equalsIgnoreCase(contracts.getContractType()) && !Constants.NSA.equalsIgnoreCase(contracts.getContractType()) && !Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType())
				&& !Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
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
				}else if(contracts.getCandidate() != null && contracts.getCandidate().getFirstName() != null && !"".equalsIgnoreCase(contracts.getCandidate().getFirstName())){
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
		StringBuilder linkBuild = new StringBuilder();
		if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){

		globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +vmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>MSA Finalised</div></a>")
				.toString());
		}else if(Constants.Client_SOW.equalsIgnoreCase(contracts.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType()))
		{

			globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +vmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>SOW Finalised</div></a>")
					.toString());
		}
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		EmailScenario  emailScenario = null;
		EmailScenario  notificationScenario = null;
		if(Constants.CLIENT_MSA.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_MSA.equalsIgnoreCase(contracts.getContractType())){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.MSA_CONTRACT_FINALIZED,null);
//			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MSA_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
		    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MSA_FINALIZED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		}else if(Constants.Client_SOW.equalsIgnoreCase(contracts.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.SOW_CONTRACT_FINALIZED,null);
//			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SOW_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SOW_FINALIZED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		}else if(Constants.VENDOR_NDA.equalsIgnoreCase(contracts.getContractType()) || Constants.VENDOR_NSA.equalsIgnoreCase(contracts.getContractType())){
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.NDA_NSA_CONTRACT_FINALIZED,null);
//			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NDA_NSA_CONTRACT_FINALIZED,Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NDA_NSA_CONTRACT_FINALIZED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		}
		 
		 /*//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;*/
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 /*//construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);*/
		
//		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 StringBuilder notificationLink = new StringBuilder();
		 String notificationActionLink="";
	     notificationActionLink = notificationLink.append(vmsUrl).append("/").append("viewContract?"+"contractUUID="+contracts.getContractUUID()).toString();

//		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 emailScenerioUtil.sendNotification(notificationActionLink, loginUser,contracts.getContactUser(), contracts.getContractOrgLocationId(), notificationScenario,new HashMap<String,String>());
		

	}

	@Override
	public Boolean getVendorMsaCreatedOrNot(Long vendorLocationId, Long loginLocatonId)
	{
		return contractsTemplateDao.getVendorMsaCreatedOrNot(vendorLocationId, loginLocatonId);
	}

	@Override
	public void sendMSAEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation, Date previousEndDate, User ownerUser) throws Exception
	{
		
		List<User> userList = new ArrayList<>();
		if(contracts.getContactUser() != null){
			userList.add(contracts.getContactUser());
		}else{
			for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
				userList.add(coh.getOwnerUserId());
			}
		}
//		User contactUser = userService.getUserById(contracts.getContactUser().getId());
		
		for(User contactUser : userList){
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
			
			if(contracts.getOwnerUserId() != null){
				if (contracts.getOwnerUserId().getLastName() != null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())) {
					StringBuilder ownerBuilder = new StringBuilder();
					globalVarsMap.put("contractOwner", ownerBuilder.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
				}
				else {
					globalVarsMap.put("contractOwner", contracts.getOwnerUserId().getFirstName());
				}
			}else{
				StringBuilder ownerSb = new StringBuilder();
				for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
					if(StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())){
						ownerSb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
					}else{
						ownerSb.append(coh.getOwnerUserId().getFirstName()).append(", ");
					}
				}
				globalVarsMap.put("contractOwner", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
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
				String contractPreviousEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimeZoneName(), previousEndTimeStr);
				Date contractConvertPreviousEndDate = TZ_DF_TS.parse(contractPreviousEndDate);
				String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertPreviousEndDate);
				globalVarsMap.put("previousEndDate", contractConvertDateInTimezone);
			}
			if (contracts.getStartDate() != null) {
				String startDate = Constants.TZ_DF.format(contracts.getStartDate());
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String contractStartDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimeZoneName(), startDate);
				Date contractConvertStartDate = TZ_DF_TS.parse(contractStartDate);
				String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertStartDate);
				globalVarsMap.put("startDate", contractConvertDateInTimezone);
			}
			if (contracts.getEndDate() != null) {
				String presentEndDate = Constants.TZ_DF.format(contracts.getEndDate());
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String contractEndDate = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, orgLocation.getTimeZoneName(), presentEndDate);
				Date contractConvertEndDate = TZ_DF_TS.parse(contractEndDate);
				String contractConvertDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contractConvertEndDate);
				globalVarsMap.put("presentEndDate", contractConvertDateInTimezone);
			}
			globalVarsMap.put("receiverOrg", contracts.getContractOrgLocationId().getOrgLocationName());
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginUrl", loginBuilder.append("<a href='").append(vmsUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			// setting the Recipient information
//			InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			// getting the current EmailScenario by EmailScenario unique name and
			// entity type
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.MODIFY_CONTRACT_ENDDATE,null);
//			EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFY_CONTRACT_ENDDATE, Constants.ENTITY_TYPE_EMAIL);
			
	        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFY_CONTRACT_ENDDATE_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationbody);
			
			/*// covert subject,body and signature
			String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
			String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap);
			String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
			String url = sourceleadUrl;
			// construct HTML template using required data
			String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId());
			emailMessage.setSubject(subject);
			emailMessage.setMessageBody(emailTemplate);*/
			// calling the sendEmail method of emailScenerioUtil
//			emailScenerioUtil.sendEmail(emailMessage, contracts.getOrgLocationId().getId(), iAdressArray, new ArrayList<EmailAttachment>());
			 StringBuilder notificationLink = new StringBuilder();
			 String actionLink="";
		     actionLink = notificationLink.append(vmsUrl).append("/").append("viewContract?"+"contractUUID="+contracts.getContractUUID()).toString();

			emailScenerioUtil.sendNotification(actionLink,ownerUser,contactUser, contracts.getContractOrgLocationId(), notificationScenario,new HashMap<String,String>());
		}
	}

	@Override
	public void sendSOWEndDateModifiedMail(Contracts contracts, OrgLocations orgLocation, User ownerUser) throws Exception
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
		
		
		List<User> userList = new ArrayList<>();
		if(contracts.getContactUser() != null){
			userList.add(contracts.getContactUser());
		}else{
			for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
				userList.add(coh.getOwnerUserId());
			}
		}
		
		for(User contactUser : userList){
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

			if(contracts.getOwnerUserId() != null){
				if (contracts.getOwnerUserId().getLastName() != null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())) {
					StringBuilder ownerBuilder = new StringBuilder();
					globalVarsMap.put("contractOwner", ownerBuilder.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
				}
				else {
					globalVarsMap.put("contractOwner", contracts.getOwnerUserId().getFirstName());
				}
			}else{
				StringBuilder ownerSb = new StringBuilder();
				for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
					if(StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())){
						ownerSb.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).append(", ");
					}else{
						ownerSb.append(coh.getOwnerUserId().getFirstName()).append(", ");
					}
				}
				globalVarsMap.put("contractOwner", ownerSb.toString().replaceAll("\\s*,\\s*$", ""));
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
			globalVarsMap.put("loginUrl", loginBuilder.append("<a href='").append(vmsUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
			// setting the Recipient information
//			InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			// getting the current EmailScenario by EmailScenario unique name and
			// entity type
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.MODIFY_SOW_CONTRACT_ENDDATE,null);
//			EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MODIFY_SOW_CONTRACT_ENDDATE, Constants.ENTITY_TYPE_EMAIL);
			/*// covert subject,body and signature
			String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
			String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap);
			String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
			String url = sourceleadUrl;
			// construct HTML template using required data
			String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId());
			emailMessage.setSubject(subject);
			emailMessage.setMessageBody(emailTemplate);*/
			// calling the sendEmail method of emailScenerioUtil
//			emailScenerioUtil.sendEmail(emailMessage, contracts.getOrgLocationId().getId(), iAdressArray, new ArrayList<EmailAttachment>());
		}
		
	}

	@Override
	public List<Contracts> getContractByLocationId(Long orgLocationId) {
		return contractsTemplateDao.getContractByLocationId(orgLocationId);
	}
	
	private void sendContractForSignature(Contracts contracts, OrgLocations orgLocation, User user) throws Exception {
		
		User owner = user;
		User receiver = null;
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())){
			 receiver = userService.getUserByUserCode(contracts.getCandidate().getConsultantUser().getUserCode());
		}else {
			 receiver = userService.getUserByUserCode(contracts.getContactUser().getUserCode());
		}
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(owner.getFirstName());
		 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
			 emailMessage.setLastName(owner.getLastName());
		 }else{
			 emailMessage.setLastName("");
		 }
		 emailMessage.setFromEmail(owner.getEmail());
		 emailMessage.setToEmail(receiver.getEmail());
		 emailMessage.setCreatedBy("sladmin");
		 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
			 StringBuilder sb = new StringBuilder();
			 emailMessage.setFromName(sb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		 }else{
			 emailMessage.setFromName(owner.getFirstName());
		 }
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
			 globalVarsMap.put("contractTitle", contracts.getContractTitle());
			 }else{
				 globalVarsMap.put("contractTitle","--");
			 }
		 
		 if(owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())){
			StringBuilder ownersb = new StringBuilder();
			 globalVarsMap.put("contractCreator", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
			 }else{
				 globalVarsMap.put("contractCreator",owner.getFirstName());
			 }
		 
		 if(receiver.getLastName() != null && !"".equalsIgnoreCase(receiver.getLastName())){
				StringBuilder receiversb = new StringBuilder();
				 globalVarsMap.put("receiverUser", receiversb.append(receiver.getFirstName()).append(" ").append(receiver.getLastName()).toString());
				 }else{
					 globalVarsMap.put("receiverUser",receiver.getFirstName());
				 }
		
		 if(contracts.getCandidate() != null){
			 if(contracts.getCandidate().getLastName() != null && !"".equalsIgnoreCase(contracts.getCandidate().getLastName())) {
				 StringBuilder applicant = new StringBuilder();
				 globalVarsMap.put("Applicant", applicant.append(contracts.getCandidate().getFirstName()).append(" ").append(contracts.getCandidate().getLastName()).toString());
			 }else{
				 globalVarsMap.put("Applicant",contracts.getCandidate().getFirstName());
			}
		 }
		 
		 if(contracts.getContractType() != null){
			 globalVarsMap.put("contractType", contracts.getContractType());
			 }else{
				 globalVarsMap.put("contractType","--");
			 }
		 
		 if(orgLocation != null){
			 globalVarsMap.put("loginOrg", orgLocation.getOrgLocationName());
			 }else{
				 globalVarsMap.put("loginOrg","--");
			 }
		 
		 if(contracts.getStartDate() != null) {
			 if(contracts.getEndDate() != null){
				 StringBuilder contractDur = new StringBuilder();
				 globalVarsMap.put("contractDuration", contractDur.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), contracts.getStartDate())).append(" - ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),contracts.getEndDate())).toString());
				 }else{
					 globalVarsMap.put("contractDuration",commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), contracts.getStartDate()).toString());
				 }
		 }
		 
		 if(user.getLastName() != null){
			 StringBuilder logUser = new StringBuilder();
			 globalVarsMap.put("loginUser", logUser.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUser",user.getFirstName().toString());
			 }
		 
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px; float:left;'>Login</div></a>").toString());
		 
		 StringBuilder signatureBuild = new StringBuilder();
		 Contracts childContract = null;
		 if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())){
			 childContract = contracts;
		 }else {
			 childContract = contractsTemplateDao.getChildContractById(contracts.getId());
		 }
		 globalVarsMap.put("signatureLink",signatureBuild.append("<a href='").append(vmsUrl).append("/").append("ws/docusign?contractUUID=").append(childContract.getContractUUID()).append("&type=public").append("'style='text-decoration:none;float:left; margin-left:55px;'><div style=' width:220px; border-radius:30px; background-color:#5cb85c; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px; float:left;'>Sign Here</div></a>").toString());
		 
		 EmailScenario  emailScenario = null;
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 if(Constants.Client_SOW.equalsIgnoreCase(contracts.getContractType()) || Constants.Vendor_SOW.equalsIgnoreCase(contracts.getContractType())) {
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation, Constants.SEND_SOW_CONTRACT,null);  
//			 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SEND_SOW_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 }else {
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, null, orgLocation, Constants.SEND_CONTRACT,null);
//			   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SEND_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 }
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.SEND_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);
		 
		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);

		
		 /*//covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);*/
		
//		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(vmsUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=sign").toString();

//		 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
	     if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())) {
	    	 emailScenerioUtil.sendNotification(actionLink,owner,receiver, contracts.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
	     }else {
	    	 emailScenerioUtil.sendNotification(actionLink,owner,receiver, contracts.getContractOrgLocationId(), notificationScenario,new HashMap<String,String>());
	     }
		
	}

	@Override
	public List<User> getSigningAuthoritiesForContract(Long orgLocationsId, Long tabId) throws Exception
	{
		List<User> userList = null;
		List<User> user = new ArrayList<>();
		userList = contractsTemplateDao.getSigningAuthoritiesForContract(orgLocationsId, tabId);
//		if(null != userList && !userList.isEmpty()){
//			
//			
//			
//			user = (List<User>)(Object)userList; 
//				
//			}
			
			/*for (User user : userList) {
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(),orgLocationsId);
				if(null != userRole){
					user.setUserType(userRole.getUserType());
				}
				}*/
		return userList;
	}

	@Override
	public boolean sendContractFromOwnertoReceiver(ContractsDTO contractsDTO, User loginUser, OrgLocations loginOrgLocation, String roleName) throws Exception
	{
		boolean flag = false;
		Contracts contract = getContractByContractUUID(contractsDTO.getContractUUID());
		String previousStage = contract.getTemplateSubmissionStatus();
		Contracts vendorContracts = null;
		for (ContractOwnerHierarchy coh : contract.getOwnerHierarchyList()) {
			if (Constants.PARALLEL.equalsIgnoreCase(coh.getHierarchyType().getValue())) {
				if (loginUser.getId().longValue() == coh.getOwnerUserId().getId().longValue() || "Admin".equalsIgnoreCase(roleName)) {
					Set<ContractOwnerHierarchy> ownersList = new HashSet<>();
					vendorContracts = new Contracts();
					contract.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_SIGNED);
					coh.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_SIGNED);
					coh.setStatus(Constants.APPROVED);
					ownersList.add(coh);
					contract.setOwnerHierarchyList(ownersList);
					contract = saveNewContract(contract);
//					ContractsHistory contractsHistory = contractsTemplateDao.getContractsHistoryByContractId(contract.getId());
//					if (contractsHistory != null) {
//						if (!contractsHistory.getToStage().equalsIgnoreCase(contract.getTemplateSubmissionStatus())) {
							ContractsHistory ch = new ContractsHistory();
							ch.setContractsId(contract);
							ch.setFromStage(previousStage);
							ch.setToStage(contract.getTemplateSubmissionStatus());
							ch.setCreatedDate(new Date());
							if (null != loginUser) {
								ch.setCreatedBy(loginUser.getUserCode());
								ch.setUpdatedBy(loginUser.getUserCode());
							}
							ch.setUpdatedDate(new Date());
							contractsTemplateDao.saveContractsHistory(ch);
//						}
//					}
//					saveClientOrVendorContract(contract, vendorContracts, loginUser, loginOrgLocation);
					flag = true;
				}
			}
			else if (Constants.SEQUENTIAL.equalsIgnoreCase(coh.getHierarchyType().getValue())) {
				if (loginUser.getId().longValue() == coh.getOwnerUserId().getId().longValue() || "Admin".equalsIgnoreCase(roleName)) {
					if(coh.getLevel() == 1){
						contract.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						coh.setTemplateSubmissionStatus(Constants.WAITING_TO_SEND);
						coh.setStatus(Constants.APPROVED);
					}else{
						contract.setTemplateSubmissionStatus(Constants.PENDING);
						coh.setTemplateSubmissionStatus(Constants.PENDING);
						coh.setStatus(Constants.APPROVED);
					}
					Set<ContractOwnerHierarchy> ownersList = new HashSet<>();
					vendorContracts = new Contracts();
					ownersList.add(coh);
					contract.setOwnerHierarchyList(ownersList);
					contract = saveNewContract(contract);
					if(Constants.PENDING.equalsIgnoreCase(contract.getTemplateSubmissionStatus())){
						ContractsHistory contractsHistory = contractsTemplateDao.getContractsHistoryByContractId(contract.getId());
						if (contractsHistory != null) {
							if (!contractsHistory.getToStage().equalsIgnoreCase(contract.getTemplateSubmissionStatus())) {
								ContractsHistory ch = new ContractsHistory();
								ch.setContractsId(contract);
								ch.setFromStage(contractsHistory.getToStage());
								ch.setToStage(contract.getTemplateSubmissionStatus());
								ch.setCreatedDate(new Date());
								if (null != loginUser) {
									ch.setCreatedBy(loginUser.getUserCode());
									ch.setUpdatedBy(loginUser.getUserCode());
								}
								ch.setUpdatedDate(new Date());
								contractsTemplateDao.saveContractsHistory(ch);
							}
						}
					}
					flag = true;
				}
			}
		}
		if(!Constants.FULL_TIME_OFFER.equalsIgnoreCase(contract.getContractType())){
			saveClientOrVendorContract(contract, vendorContracts, loginUser, loginOrgLocation);
		}else {
			sendContractForSignature(contract,loginOrgLocation,loginUser);
		}
		return flag;
	}

	@Override
	public List<Contracts> validateAlreadyGeneratedContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation) throws Exception
	{
		List<Contracts> contractList = contractsTemplateDao.validateAlreadyGeneratedContract(contractsDTO, loginOrgLocation);
		if(contractList != null){
			for(Contracts contracts : contractList){
				contracts.setContractWith(contracts.getContractOrgLocationId().getOrgLocationName());
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Timezone timezone = timeZoneService.getTimezone(contracts.getOrgLocationId().getTimezone().getId());
				//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
				if(null != contracts && null != contracts.getStartDate()){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());  
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(contracts.getOrgLocationId().getDateFormat(),converteDate);
					contracts.setStartDateStr(currentDateInTimezone);
				}
				if(null != contracts && null != contracts.getEndDate()){
					String endTimeStr = Constants.TZ_DF.format(contracts.getEndDate());  
					String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
					Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
					endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(contracts.getOrgLocationId().getDateFormat(),endconverteDate);
					contracts.setEndDateStr(endDateInTimezone);
				}
				if(contracts.getCandidate() != null)
				contracts.setApplicantName(contracts.getCandidate().getFirstName());
			}
		}
		return contractList;
	}

	@Override
	public boolean reviseContract(ContractsDTO contractsDTO, OrgLocations loginOrgLocation, User loginUser) throws Exception
	{
		boolean flag = false;
		Contracts childcontract = getContractByContractUUID(contractsDTO.getContractUUID());
		if(childcontract.getParentContractId() != null){
			Contracts parentContract = childcontract.getParentContractId();
			parentContract.setRevisedReason(contractsDTO.getRevisedReason());
			parentContract.setUpdatedDate(new Date());
			parentContract.setUpdatedBy(loginUser.getUserCode());
			parentContract = saveNewContract(parentContract);
			sendReviseContractEmailToOwner(parentContract,loginOrgLocation,loginUser);
			if(parentContract != null){
				flag = true;
			}
		}
		return flag;
	}

	private void sendReviseContractEmailToOwner(Contracts contracts,OrgLocations orgLocation, User loginUser) throws Exception
	{
		Timezone timezone = null;
		if(null != orgLocation.getTimezone()){
	    	 timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		 }
//		User createdUser = userService.getUserByUserCode(contracts.getCreatedBy());
		for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(loginUser.getFirstName());
			 emailMessage.setLastName(loginUser.getLastName());
			 emailMessage.setToEmail(coh.getOwnerUserId().getEmail());
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
			 
			 globalVarsMap.put("loginUser",emailMessage.getFromName());
			 
			 StringBuilder durationSb = new StringBuilder();
				if(contracts.getStartDate() != null){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getStartDate());
					if(null != timezone && null != timezone.getTimeZoneId()){
						String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
						globalVarsMap.put("contractStartDate",currentDateInTimezone);	
						durationSb.append(currentDateInTimezone);
					}
				}
				
				if(contracts.getEndDate() != null){
					String currentTimeStr = Constants.TZ_DF.format(contracts.getEndDate());
					if(null != timezone && null != timezone.getTimeZoneId()){
						String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
						globalVarsMap.put("contractEndDate",currentDateInTimezone);
						durationSb.append(" - ").append(currentDateInTimezone);
					}
			    }
			
				globalVarsMap.put("contractDuration",durationSb.toString());
				globalVarsMap.put("contractType",contracts.getContractType());
				globalVarsMap.put("loginOrg",orgLocation.getOrgLocationName());
				
				if(StringUtils.isNotBlank(coh.getOwnerUserId().getLastName())){
					 StringBuilder msgBuild = new StringBuilder();
					 globalVarsMap.put("contractOwner",msgBuild.append(coh.getOwnerUserId().getFirstName()).append(" ").append(coh.getOwnerUserId().getLastName()).toString()); 
				 }else{
					 globalVarsMap.put("contractOwner",coh.getOwnerUserId().getFirstName()); 
				 }
				StringBuilder urlBuild = new StringBuilder();
				 globalVarsMap.put("reviseLink",urlBuild.append("<a href='").append(sourceleadUrl).append("/addNewContract?contractUUID=").append(contracts.getContractUUID()).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Revise</div></a>").toString());
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			EmailScenario  emailScenario = null;
			EmailScenario  notificationScenario = null;
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, contracts.getOrgLocationId().getOrg(), contracts.getOrgLocationId(), Constants.REVISE_CONTRACT_EMAIL,null);
//			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REVISE_CONTRACT_EMAIL,Constants.ENTITY_TYPE_EMAIL);
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NDA_NSA_CONTRACT_FINALIZED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
			 
			 /*//covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;*/
			 
			 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			 notificationScenario.setBody(notificationbody);
			 
			 /*//construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);*/
			
//			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 StringBuilder notificationLink = new StringBuilder();
			 String notificationActionLink="";
		     notificationActionLink = notificationLink.append(vmsUrl).append("/").append("viewContract?"+"contractUUID="+contracts.getContractUUID()).toString();

//			 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			 emailScenerioUtil.sendNotification(notificationActionLink, loginUser,contracts.getContactUser(), contracts.getContractOrgLocationId(), notificationScenario,new HashMap<String,String>());
		}
		
	}

	@Override
	public ContractEditHistory saveContractEditHistory(ContractEditHistory contractEditHistory)
	{
		return contractsTemplateDao.saveContractEditHistory(contractEditHistory);
	}

	@Override
	public Contracts getMaxNoOfContract(Long locationId)
	{
		return contractsTemplateDao.getMaxNoOfContract(locationId);
	}
	
	@Override
	public List<Contracts> getContractsBylocationIdForReminder(Long locationId, String status, Date date)
	{
		return contractsTemplateDao.getContractsBylocationIdForReminder(locationId,status,date);
	}

	@Override
	public Contracts updateContractSigner(ContractsDTO contractsDTO, OrgLocations loginOrgLocation, User loginUser) throws NumberFormatException, Exception
	{
		Contracts contract = getContractByContractUUID(contractsDTO.getContractUUID());
		Contracts parentContract = contract.getParentContractId();
		User contactUser = userService.getUserById(Long.parseLong(contractsDTO.getClientContact()));
		contract.setOwnerUserId(contactUser);
		contract.setUpdatedBy(loginUser.getUserCode());
		contract.setUpdatedDate(new Date());
		StringBuilder owner = new StringBuilder();
		if (StringUtils.isNotBlank(contactUser.getLastName())){
			contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).append(" ").append(contract.getOwnerUserId().getLastName()).toString());
		}
		else {
			contract.setOwnerName(owner.append(contract.getOwnerUserId().getFirstName()).toString());
		}
		
		contract = saveNewContract(contract);
		saveEditHistory(contract);
		parentContract.setContactUser(contactUser);
		saveNewContract(parentContract);
		return contract;
	}

	@Override
	public Integer deleteContractOwnerHierarchyListByContractId(Long id)
	{
		return contractsTemplateDao.deleteContractOwnerHierarchyListByContractId(id);
	}

	@Override
	public Contracts getAllVendorClientAndApplicantCountByLocationIdAndStatus(OrgLocations orgLocation,String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user,String userRoleName) throws Exception {
		
		Contracts individualCounts = new Contracts();
		
		Integer totalVendorCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"Vendor", contractsType, contractStatus, user, userRoleName);
		Integer totalClientCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"Client", contractsType, contractStatus, user, userRoleName);
		Integer totalApplicantCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"Full Time", contractsType, contractStatus, user, userRoleName);
		
//		if()
		individualCounts.setTotalDraftVendorCount(totalVendorCount);
		individualCounts.setTotalDraftClientCount(totalClientCount);
		individualCounts.setTotalDraftApplicantCount(totalApplicantCount);
		
		return individualCounts;
	}
	
	@Override
	public List<Contracts> getAllContractsBasedOnConfigureDate(Date updatedDate) throws Exception
	{
		return contractsTemplateDao.getAllContractsBasedOnConfigureDate(updatedDate);
	}
	
}