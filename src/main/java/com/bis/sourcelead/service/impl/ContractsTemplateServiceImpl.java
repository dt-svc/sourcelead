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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SignatureRequestUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ContractsTemplateServiceImpl implements ContractsTemplateService{

	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
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
	public Contracts saveNewContract(JSONObject jsonObj, OrgLocations orgLocation, String saveType,User user,Org org,String contractType, MultipartFile multiFile) throws Exception
	{
		Contracts contracts;
		Contracts vendorContracts = null;
		
		if(jsonObj.has("contrId") && null != jsonObj.getString("contrId") && !"".equals(jsonObj.getString("contrId"))){
			contracts = contractsTemplateDao.getContractById(Long.parseLong(jsonObj.getString("contrId")));
			if(null != contracts && null != contracts.getParentContractId()){
				vendorContracts = contractsTemplateDao.getChildContractById(contracts.getParentContractId().getId());
			}else if("PENDING".equalsIgnoreCase(saveType)){
				vendorContracts = new Contracts();
			}
		}else{
			contracts = new Contracts();
			vendorContracts = new Contracts();
		}
		
		//Contracts contracts = new Contracts();
		//Contracts vendorContracts = new Contracts();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
        Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		if("NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setContractType("Vendor "+jsonObj.getString("contractType"));
		}
		else{
			contracts.setContractType(jsonObj.getString("contractType"));
        }
		ContractsTemplate contractsTemplate = null;
		if(jsonObj.has("template")){
			contractsTemplate = contractsTemplateDao.getContractTemplateById(Long.parseLong(jsonObj.getString("template")));
			if(null != contractsTemplate){
				contracts.setContractsTemplate(contractsTemplate);
				if(null != vendorContracts){
					vendorContracts.setContractsTemplate(contractsTemplate);
				}
			}
		}
		
		if(multiFile != null){
			contracts.setDocFile(multiFile.getBytes());
			contracts.setFileName(multiFile.getOriginalFilename());
			contracts.setFileType(multiFile.getContentType());
		 }
		
		if(null != contractType && !"".equalsIgnoreCase(contractType) && "custom".equalsIgnoreCase(contractType)){
			contracts.setTypeOfContract("Custom");
			vendorContracts.setTypeOfContract("Custom");
		}else{
			contracts.setTypeOfContract("New");
			vendorContracts.setTypeOfContract("New");
		}
		
		if(null != vendorContracts){
			vendorContracts.setTypeOfContract("New");
		}
		
		if(jsonObj.has("submissionOffersList") && null != jsonObj.getString("submissionOffersList") && !"".equals(jsonObj.getString("submissionOffersList"))){
			RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(jsonObj.getString("submissionOffersList")));
			contracts.setRequirementSubmission(rs);
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
		
		contracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		if(null != vendorContracts){
			vendorContracts.setContractTitle(jsonObj.getString("contractTitle").trim());
			vendorContracts.setContractsTemplate(contractsTemplate);
		}
		
		User ownerUser = userService.getUserById(Long.parseLong(jsonObj.getString("owner")));
		contracts.setOwnerUserId(ownerUser);

		Date startTime =  commonUtil.getDateFormat(jsonObj.getString("startDate"));
		String currentTimeStr = Constants.TZ_DF.format(startTime);  

		
		
		//String currentTimeStr = Constants.TZ_DF.format(jsonObj.getString("startDate"));  

		String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		contracts.setStartDate(convertedDate);
		if(null != vendorContracts){
			vendorContracts.setStartDate(convertedDate);
		}
		
		if(jsonObj.has("endDate")){
			if(null != jsonObj.getString("endDate") && !"".equals(jsonObj.getString("endDate"))){
				Date endTimeStr = commonUtil.getDateFormat(jsonObj.getString("endDate"));  
				String endDateStr = Constants.TZ_DF.format(endTimeStr);  
				String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", endDateStr);
				Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
				contracts.setEndDate(convertedEndDate);
				if(null != vendorContracts){
					vendorContracts.setEndDate(convertedEndDate);
				}
			}

		}
		
		
		
      /* Date startDate = format.parse(jsonObj.getString("startDate"));
		contracts.setStartDate(startDate);
		vendorContracts.setStartDate(startDate);
		if(jsonObj.has("endDate")){
			if(null != jsonObj.getString("endDate") && !"".equals(jsonObj.getString("endDate"))){
				Date endDate = format.parse(jsonObj.getString("endDate"));
				contracts.setEndDate(endDate);
				vendorContracts.setEndDate(endDate);
			}

		}*/
		User approverUser = null;
		User vendorContactsUser = null;
		if(jsonObj.has("approver") && null != jsonObj.getString("approver") && !"".equals(jsonObj.getString("approver"))){
			 approverUser = userService.getUserById(Long.parseLong(jsonObj.getString("approver")));
			contracts.setApproverUser(approverUser);
		}

		if(("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))|| "Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))|| "NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType")))){
			contracts.setInitiatorType("Client");
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
		if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType")) ||  "Client SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setInitiatorType("Vendor");
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
			if(null != vendorContracts){
				vendorContracts.setBillingAmount(jsonObj.getString("percentageReward"));
			}
				contracts.setBillingPer("Percentage");
				if(null != vendorContracts){
					vendorContracts.setBillingPer("Percentage");
				}
		}
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
		
		contracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		if(null != vendorContracts){
			vendorContracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		}
		
		if(jsonObj.has("paymentFrequency") && null != jsonObj.getString("paymentFrequency") && !"".equals(jsonObj.getString("paymentFrequency")) ){
			contracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
			if(null != vendorContracts){
				vendorContracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
			}
		}
		
		if(jsonObj.has("taxPercentageReward") && null != jsonObj.getString("taxPercentageReward") && !"".equals(jsonObj.getString("taxPercentageReward")) ){
			contracts.setTax(jsonObj.getString("taxPercentageReward"));
			if(null != vendorContracts){
				vendorContracts.setTax(jsonObj.getString("taxPercentageReward"));
			}
			contracts.setTaxType("Percentage");
			if(null != vendorContracts){
				vendorContracts.setTaxType("Percentage");
			}
		}
		if(jsonObj.has("taxFlatReward") && null != jsonObj.getString("taxFlatReward") && !"".equals(jsonObj.getString("taxFlatReward")) ){
			contracts.setTax(jsonObj.getString("taxFlatReward"));
			if(null != vendorContracts){
				vendorContracts.setTax(jsonObj.getString("taxFlatReward"));
			}
			contracts.setTaxType(jsonObj.getString("taxFlatRateType"));
			if(null != vendorContracts){
				vendorContracts.setTaxType(jsonObj.getString("taxFlatRateType"));
			}
		}
		
		
		
		if(jsonObj.has("description") && null != jsonObj.getString("description") && !"".equals(jsonObj.getString("description"))){
			contracts.setDescription(jsonObj.getString("description").trim());
			if(null != vendorContracts){
				vendorContracts.setDescription(jsonObj.getString("description").trim());
			}
		}
		if(jsonObj.has("jurisdiction") && null != jsonObj.getString("jurisdiction") && !"".equals(jsonObj.getString("jurisdiction"))){
			contracts.setStateJurisdiction(jsonObj.getString("jurisdiction"));
		}
		contracts.setStatus("0");
		if(null != vendorContracts){
			vendorContracts.setTemplateSubmissionStatus("WAITING_TO_BE_SIGNED");
			vendorContracts.setStatus("0");
		}
		
		if(null != approverUser &&! "DRAFT".equalsIgnoreCase(saveType)){
			contracts.setTemplateSubmissionStatus("WAITING_FOR_APPROVER");
			sendMailToContractApprover(user,approverUser,contracts,orgLocation);
		}
		else if("DRAFT".equalsIgnoreCase(saveType)){
			contracts.setTemplateSubmissionStatus("DRAFT");
		}
		else{
			contracts.setTemplateSubmissionStatus(saveType);
		}
		contracts.setCreatedDate(new Date());
		contracts.setCreatedBy(user.getUserCode());
		contracts.setUpdatedDate(new Date());
		contracts.setUpdatedBy(user.getUserCode());
		contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		vendorContracts.setContractUUID(contracts.getContractUUID());
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		
		if(null != approverUser &&! "DRAFT".equalsIgnoreCase(saveType)){
			if(!jsonObj.has("approver") && ownerUser.getId().longValue() != user.getId().longValue()){
				sendMailToOwner(savedContracts,user,org,orgLocation);
			}
		}
		
		if(null == approverUser && !"DRAFT".equalsIgnoreCase(savedContracts.getTemplateSubmissionStatus())){
			if(null != vendorContracts){
				vendorContracts.setParentContractId(savedContracts);
			}
			
			if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType")) || "Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))|| "NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
				if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
					if(null != vendorContracts){
						vendorContracts.setContractType("Client MSA");	
					}
				}else if("Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
					if(null != vendorContracts){
						vendorContracts.setContractType("Client SOW");
					}
				}
				else if("NDA".equalsIgnoreCase(jsonObj.getString("contractType"))){
					if(null != vendorContracts){
						vendorContracts.setContractType("Client NDA");
					}
				}
				else if("NSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
					if(null != vendorContracts){
						vendorContracts.setContractType("Client NSA");
					}
				}
				if(null != vendorContracts){
					vendorContracts.setInitiatorType("Client");
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
			}
			
			if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType")) || "Client SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
				if(null != vendorContracts){
					if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
						vendorContracts.setContractType("Vendor MSA");	
					}else{
						vendorContracts.setContractType("Vendor SOW");
					}
					
					vendorContracts.setInitiatorType("Vendor");
					vendorContracts.setParentContractId(savedContracts);
					vendorContracts.setContractOrgLocationId(orgLocation);
					OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
					vendorContracts.setOrgRelations(orgRelations);
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getLocationId());
					}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
					}
					User vendorUser = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
					vendorContracts.setOwnerUserId(vendorUser);
					vendorContracts.setContactUser(ownerUser);
				}
				
			}
			if(null != vendorContracts){
				vendorContracts.setContractUUID(contracts.getContractUUID());
				Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
			}
			
			
			  if(null != vendorContactsUser && null == approverUser && !"DRAFT".equalsIgnoreCase(saveType)){
				  Contracts con = null;
				  if(null != contractType && !"".equalsIgnoreCase(contractType) && "custom".equalsIgnoreCase(contractType)){
					  con = signatureRequestUtil.sendDocumentForSignatureRequestForCustom(vendorContactsUser,contracts,orgLocation,user,savedContracts,org);
				  }else{
					  con = signatureRequestUtil.sendDocumentForSignatureRequest(vendorContactsUser,contractsTemplate,orgLocation,user,savedContracts,org);
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
		List<Contracts> vendorcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,"Vendor", contractsType, contractStatus, user, userRoleName);
		List<Contracts> clientcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,"Client", contractsType, contractStatus, user, userRoleName);
		
		Contracts contractsForList = new Contracts();
		
		for(Contracts contracts :vendorcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId().getLastName() && null != contracts.getOwnerUserId().getLastName()){
				ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
			}else{
				ownerName.append(contracts.getOwnerUserId().getFirstName());	
			}
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser().getLastName() && null != contracts.getContactUser().getLastName()){
				vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
			}else{
				vendorName.append(contracts.getContactUser().getFirstName());	
			}
			contracts.setVendorContactName(vendorName.toString());
			contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
			if(null != contracts.getEndDate()){
				contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
			}
			if(null != contracts.getParentContractId()){
			contracts.setParentId(contracts.getParentContractId().getId().toString());
			}
			else{
				contracts.setParentId("");
			}

			if(null != contracts.getApproverUser() ){
				if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
					StringBuilder sb = new StringBuilder();
					contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
				}else{
					contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
				}
			}
			}
		for(Contracts contracts :clientcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId().getLastName() && null != contracts.getOwnerUserId().getLastName()){
				ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
			}else{
				ownerName.append(contracts.getOwnerUserId().getFirstName());	
			}
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser().getLastName() && null != contracts.getContactUser().getLastName()){
				vendorName.append(contracts.getContactUser().getFirstName()).append((contracts.getContactUser().getLastName()));
			}else{
				vendorName.append(contracts.getContactUser().getFirstName());	
			}
			contracts.setVendorContactName(vendorName.toString());
			contracts.setStartDateStr(Constants.DF_DMY.format(contracts.getStartDate()));
			if(null != contracts.getEndDate()){
				contracts.setEndDateStr(Constants.DF_DMY.format(contracts.getEndDate()));	
			}
			if(null != contracts.getParentContractId()){
			contracts.setParentId(contracts.getParentContractId().getId().toString());
			}
			else{
				contracts.setParentId("");
			}
			
			if(null != contracts.getApproverUser()){
				if(null != contracts.getApproverUser().getFirstName() && null != contracts.getApproverUser().getLastName()){
					StringBuilder sb = new StringBuilder();
					contracts.setApproverUserName(sb.append(contracts.getApproverUser().getFirstName()).append(" ").append(contracts.getApproverUser().getLastName()).toString());	
				}else{
					contracts.setApproverUserName(contracts.getApproverUser().getFirstName());
				}
			}
			
		}
		
		contractsForList.setVendorcontractsList(vendorcontractsList);
		contractsForList.setClientcontractsList(clientcontractsList);
		return contractsForList;
	}
	
	@Override
	public Contracts getAllContractsCountByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType, String contractsType, String contractStatus, User user, String userRoleName) throws Exception
	{
		Integer totalDraftCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "DRAFT", user, userRoleName);
		Integer totalWTACount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "WAITING_FOR_APPROVER", user, userRoleName);
		Integer totalWTSCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "WAITING_TO_BE_SIGNED", user, userRoleName);
		Integer totalWTVCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "WAITING_TO_BE_VERIFY", user, userRoleName);
		Integer totalFinalizedCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "Finalized", user, userRoleName);
		Integer totalExpiredCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "EXPIRED", user, userRoleName);
		Integer totalRejectedCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"", contractsType, "REJECTED", user, userRoleName);
		
		Integer totalDraftVendorCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"Vendor", contractsType, "DRAFT", user, userRoleName);
		Integer totalDraftClientCount = contractsTemplateDao.getAllContractsCountByLocationIdAndStatus(orgLocation,contractSubmissionType,"Client", contractsType, "DRAFT", user, userRoleName);
		
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
	public Contracts approveContracts(long recordId, String status, OrgLocations orgLocation,User user,Org org) throws Exception
	{
		Contracts contracts  = contractsTemplateDao.getContractById(recordId);
		Contracts upadatedContracts = null;
		if(!"REJECTED".equalsIgnoreCase(status)){
			contracts.setTemplateSubmissionStatus("PENDING");
			//upadatedContracts.setTypeOfContract("New");
			 upadatedContracts = contractsTemplateDao.saveNewContract(contracts);
			Contracts childContract = new Contracts();
			childContract.setContractTitle(contracts.getContractTitle());
			childContract.setStartDate(contracts.getStartDate());
			childContract.setEndDate(contracts.getEndDate());
			
			childContract.setBillingAmount(contracts.getBillingAmount());
			childContract.setBillingPer(contracts.getBillingPer());
			childContract.setInvoiceFrequency(contracts.getInvoiceFrequency());
			childContract.setPaymentTerm(contracts.getPaymentTerm());
			
			childContract.setTax(contracts.getTax());
			childContract.setTaxType(contracts.getTaxType());
			childContract.setDescription(contracts.getDescription());
			childContract.setTemplateSubmissionStatus("WAITING_TO_BE_SIGNED");
			childContract.setStatus("0");
			childContract.setTypeOfContract("New");
			childContract.setContractUUID(contracts.getContractUUID());
				if("Vendor MSA".equalsIgnoreCase(contracts.getContractType()) || "Vendor SOW".equalsIgnoreCase(contracts.getContractType())|| "Vendor NDA".equalsIgnoreCase(contracts.getContractType()) || "Vendor NSA".equalsIgnoreCase(contracts.getContractType())){
					if("Vendor MSA".equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType("Client MSA");	
					}else if("Vendor SOW".equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType("Client SOW");
					}
					else if("Vendor NDA".equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType("Client NDA");
					}
					else if("Vendor NSA".equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType("Client NSA");
					}
					childContract.setInitiatorType("Client");
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
				
				if("Client MSA".equalsIgnoreCase(contracts.getContractType()) || "Client SOW".equalsIgnoreCase(contracts.getContractType())){
					if("Client MSA".equalsIgnoreCase(contracts.getContractType())){
						childContract.setContractType("Vendor MSA");	
					}else{
						childContract.setContractType("Vendor SOW");
					}
					childContract.setInitiatorType("Vendor");
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
				  if(null != upadatedContracts.getContactUser()){
					  Contracts con = signatureRequestUtil.sendDocumentForSignatureRequest(upadatedContracts.getContactUser(),upadatedContracts.getContractsTemplate(),orgLocation,user,upadatedContracts,org);
					  upadatedContracts.setDocId(con.getTempdocId());
					  upadatedContracts.setEnvelopId(con.getTempEnvId());
					  upadatedContracts.setContractStatus(con.getContractStatus());
					  savedChildContracts.setDocId(con.getTempdocId());
					  savedChildContracts.setEnvelopId(con.getTempEnvId());
					  savedChildContracts.setContractStatus(con.getContractStatus());
					  contractsTemplateDao.saveNewContract(upadatedContracts);
			        	Contracts updateEnvContracts = contractsTemplateDao.saveNewContract(savedChildContracts);
				  }
				contractApproveEmail(contracts,user,org,orgLocation);
				sendMailToOwner(contracts,user,org,orgLocation);
		}else{
			contracts.setTemplateSubmissionStatus("REJECTED");
			 upadatedContracts = contractsTemplateDao.saveNewContract(contracts);
			 contractRejectEmail(contracts,user,org,orgLocation);
		}
		
		return upadatedContracts;
	}
	
	private void contractRejectEmail(Contracts contracts, User loginUser, Org org, OrgLocations orgLocation) throws Exception {
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
		}else if(createdUser.getLastName()!= null && !"".equalsIgnoreCase(createdUser.getLastName())){
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
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		/* String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
//		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario);
		
		
	}

	private void contractApproveEmail(Contracts contracts, User loginUser, Org org,OrgLocations orgLocation) throws Exception {
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
		}else if(createdUser.getLastName()!= null && !"".equalsIgnoreCase(createdUser.getLastName())){
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
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVE_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
//		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		/* String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 String actionLink="";
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
//		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocation, notificationScenario);
		
	}

	private void sendMailToContractApprover(User loginUser, User approverUser, Contracts contracts, OrgLocations orgLocation) throws Exception
	{
		
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
		}else if(approverUser.getLastName()!= null && !"".equalsIgnoreCase(approverUser.getLastName())){
			globalVarsMap.put("ApproverName",approverUser.getFirstName());
		}else{
			globalVarsMap.put("ApproverName","--");
		}
		
		if(contracts.getOwnerUserId().getFirstName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getFirstName()) && contracts.getOwnerUserId().getLastName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())){
			StringBuilder ownerSb = new StringBuilder();
			globalVarsMap.put("OwnerName", ownerSb.append(contracts.getOwnerUserId().getFirstName()).append(" ").append(contracts.getOwnerUserId().getLastName()).toString());
		}else if(contracts.getOwnerUserId().getLastName()!= null && !"".equalsIgnoreCase(contracts.getOwnerUserId().getLastName())){
			globalVarsMap.put("OwnerName",contracts.getOwnerUserId().getFirstName());
		}else{
			globalVarsMap.put("OwnerName","--");
		}
		
		if(contracts.getStartDate()!=null && !"".equalsIgnoreCase(contracts.getStartDate().toString())){
			globalVarsMap.put("StartDate",contracts.getStartDate().toString());	
		}else{
			globalVarsMap.put("StartDate","--");
		}

		if(contracts.getEndDate()!=null && !"".equalsIgnoreCase(contracts.getEndDate().toString())){
			globalVarsMap.put("EndDate",contracts.getEndDate().toString());	
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
		 
			
			if("Client MSA".equalsIgnoreCase(contracts.getContractType())){
				List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
				for (OrgRelations orgRelations : clientList) {
				String orgName = "";
				if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
					orgName = orgRelations.getLocationId().getOrg().getOrgName();
				}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
					orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
				}
				orgRelations.setOrgName(orgName);

			 if(orgRelations.getResponderLocationId().getOrg().getOrgName() != null && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrg().getOrgName()) && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrg().getOrgName())){
					 globalVarsMap.put("ClientVendorName",orgRelations.getResponderLocationId().getOrg().getOrgName());
					 }else{
						 globalVarsMap.put("ClientVendorName","--");
					}
			}	
			}
			if("Vendor MSA".equalsIgnoreCase(contracts.getContractType())){
				List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
				for (OrgRelations orgRelations : vendorList) {
					String orgName = "";
					if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
						orgName = orgRelations.getLocationId().getOrg().getOrgName();
					}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
						orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
					}
					orgRelations.setOrgName(orgName);

					if(orgRelations.getResponderLocationId().getOrg().getOrgName() != null && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrg().getOrgName()) && !"".equalsIgnoreCase(orgRelations.getResponderLocationId().getOrg().getOrgName())){
						 globalVarsMap.put("ClientVendorName",orgRelations.getResponderLocationId().getOrg().getOrgName());
						 }else{
							 globalVarsMap.put("ClientVendorName","--");
						}
			}
			}
		 
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_UPDATED_CONTRACT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg());
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
		
		 if(contracts.getEndDate()!= null && !"".equalsIgnoreCase(contracts.getEndDate().toString())){
			 globalVarsMap.put("EndDate", contracts.getEndDate().toString());
			 }else{
				 globalVarsMap.put("EndDate","--");
			 }
		 
		 if(contracts.getStartDate()!= null && !"".equalsIgnoreCase(contracts.getStartDate().toString())){
			 globalVarsMap.put("StartDate", contracts.getStartDate().toString());
			 }else{
				 globalVarsMap.put("StartDate","--");
			 }
		 
		if(owner.getFirstName()!= null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
			StringBuilder creatorSb = new StringBuilder();
			globalVarsMap.put("OwnerUser", creatorSb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
		}else if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
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
		 String url = "";
		 
		/* String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);*/
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg());
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
			Long orgLocationId) {
		return contractsTemplateDao.findContractTitleByOrgLocationAndContractType(contractTitle, contractType, orgLocationId);
			}
			
	@Override
	public Contracts saveExistigContract(JSONObject jsonObj,MultipartFile multiFile, OrgLocations orgLocation, User user, Org org) throws Exception {
		Contracts contracts = new Contracts();
		Contracts vendorContracts = new Contracts();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        
        Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		
		if("NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setContractType("Vendor "+jsonObj.getString("contractType"));
		}
		else{
			contracts.setContractType(jsonObj.getString("contractType"));
        }
		
		if(jsonObj.has("submissionOffersList") && null != jsonObj.getString("submissionOffersList") && !"".equals(jsonObj.getString("submissionOffersList"))){
			RequirementSubmission rs = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(jsonObj.getString("submissionOffersList")));
			contracts.setRequirementSubmission(rs);
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
		
		contracts.setTypeOfContract("Existing");
		vendorContracts.setTypeOfContract("Existing");
		contracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		vendorContracts.setContractTitle(jsonObj.getString("contractTitle").trim());
		User ownerUser = userService.getUserById(Long.parseLong(jsonObj.getString("owner")));
		contracts.setOwnerUserId(ownerUser);
		Date startTime =  commonUtil.getDateFormat(jsonObj.getString("startDate"));
		String currentTimeStr = Constants.TZ_DF.format(startTime);  
		String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
		contracts.setStartDate(convertedDate);
		if(jsonObj.has("endDate")){
			if(null != jsonObj.getString("endDate") && !"".equals(jsonObj.getString("endDate"))){
				Date endTimeStr = commonUtil.getDateFormat(jsonObj.getString("endDate"));  
				String endDateStr = Constants.TZ_DF.format(endTimeStr);  
				String endTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", endDateStr);
				Date convertedEndDate = TZ_DF_TS.parse(endTimeUTC);
				contracts.setEndDate(convertedEndDate);
				vendorContracts.setEndDate(convertedEndDate);
			}
		}
		User approverUser = null;
		User vendorContactsUser = null;
		if(("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))|| "Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))|| "NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType")))){
			contracts.setInitiatorType("Client");
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
		if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType"))||"Client SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setInitiatorType("Vendor");
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
		if(jsonObj.has("flatReward") && null != jsonObj.getString("flatReward") && !"".equals(jsonObj.getString("flatReward")) ){
			contracts.setBillingAmount(jsonObj.getString("flatReward"));
			vendorContracts.setBillingAmount(jsonObj.getString("flatReward"));
			
			if(jsonObj.has("flatRateType") && null != jsonObj.getString("flatRateType") && !"".equals(jsonObj.getString("flatRateType")) ){
				contracts.setBillingPer(jsonObj.getString("flatRateType"));
				vendorContracts.setBillingPer(jsonObj.getString("flatRateType"));
			}
		}
		
		if(multiFile != null){
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
		vendorContracts.setTemplateSubmissionStatus("FINALIZED");
		contracts.setTemplateSubmissionStatus("FINALIZED");
		vendorContracts.setStatus("0");
		contracts.setCreatedDate(new Date());
		contracts.setCreatedBy(user.getUserCode());
		contracts.setUpdatedDate(new Date());
		contracts.setUpdatedBy(user.getUserCode());
		contracts.setContractUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
			//if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType")) || "Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
		if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType")) || "Vendor SOW".equalsIgnoreCase(jsonObj.getString("contractType"))|| "NDA".equalsIgnoreCase(jsonObj.getString("contractType")) || "NSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			if("Vendor MSA".equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType("Client MSA");	
			}else if("Vendor SOW".equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType("Client SOW");
			}
			else if("NDA".equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType("Vendor NDA");
			}
			else if("NSA".equalsIgnoreCase(contracts.getContractType())){
				vendorContracts.setContractType("Vendor NSA");
			}
				vendorContracts.setContractType("Client MSA");
				vendorContracts.setInitiatorType("Client");
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
			if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType"))||"Client SOW".equalsIgnoreCase(jsonObj.getString("contractType"))){
				if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
					vendorContracts.setContractType("Vendor MSA");
				}
				else{
					vendorContracts.setContractType("Vendor SOW");
				}
				vendorContracts.setInitiatorType("Vendor");
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
	
}
