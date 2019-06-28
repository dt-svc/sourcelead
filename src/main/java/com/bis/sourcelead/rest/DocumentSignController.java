package com.bis.sourcelead.rest;

import java.awt.FontFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsHistory;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.CoordinatesInfo;
import com.bis.sourcelead.model.SignedImageInfo;
import com.bis.sourcelead.model.UserInfo;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.impl.PdfToImageService;
import com.bis.sourcelead.service.impl.SignDocumentService;
import com.bis.sourcelead.service.impl.SignImageService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.DocuSignUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.Html2PdfUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DocumentSignController {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentSignController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private SignImageService signImageService;
	
	@Autowired
	private PdfToImageService pdfToImageService;
	
	@Autowired 
	private DocuSignUtil docuSignUtil;
	
	@Autowired 
	private SignDocumentService signDocumentService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private ContractsTemplateDao contractsTemplateDao;
	
	@Autowired
	private Html2PdfUtil html2PdfUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenarioService emailScenarioService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = {"/signer","/ws/signer"},method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest req,@RequestParam(value="contractId", required=false) Long contractId) throws Exception {
		UserInfo userInfo =  new UserInfo();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(req, "org");
		Contracts contract = contractsTemplateService.getContractById(contractId);
		String fileName = null;
		if(Constants.CUSTOM_TYPE.equalsIgnoreCase(contract.getTypeOfContract())) {
		 fileName = contract.getTypeOfContract();
		}else {
		 fileName = contract.getContractsTemplate().getTemplateName();
		}
		String userData = null;
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contract.getContractType())) {
			 userData = docuSignUtil.createDir(staticPath,req,fileName,"full01","full02",true);
		} else {
			 userData = docuSignUtil.createDir(staticPath,req,fileName,String.valueOf(contract.getContractOrgLocationId().getId()),String.valueOf(contract.getContractOrgLocationId().getOrg().getId()),true);
		}
		logger.info("folder creation path in our local system : "+userData);
		userInfo.setFileName(fileName);
		userInfo.setFileURL(userData);
		//TODO : Null check for dirPath
		String SRC = userInfo.getFileURL()+"/unsigned.pdf";
		String returnPath = SRC.substring(staticPath.lastIndexOf("/"));
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(SRC.toString()));

			byte[] fileBytes = null;
			if(Constants.CUSTOM_TYPE.equalsIgnoreCase(contract.getTypeOfContract())) {
				String path =StringUtils.removeEnd(staticPath, "/slead-static");
				fileBytes = CommonUtil.readBytesFromFile(path+contract.getParentContractId().getFileURL());
			}else{
				fileBytes = html2PdfUtil.getPdfBytes(contract.getContractsTemplate(),contract,contract.getOrgLocationId(),contract.getOrgLocationId().getOrg());
			}
			out.write(fileBytes);
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int pageCount = pdfToImageService.convertPdfToImage(SRC);
		return "{\"uploadStatus\" : \"done\", \"UserId\" : \""+returnPath+"\",\"pageCount\" : "+pageCount+"}";
	}

	
	@RequestMapping(value ={"/saveSignatureCoordinates","/ws/saveSignatureCoordinates"}, method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String saveSignatureCoordinates(@RequestBody CoordinatesInfo coordinatesInfo,HttpServletRequest req) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String result = staticPath.substring(0,staticPath.lastIndexOf("/"));
			String dirPath = result + coordinatesInfo.getUserId();
			if(dirPath.contains("/unsigned.pdf")) {
				dirPath = dirPath.replace("/unsigned.pdf", "");
			}
			mapper.writeValue(new File(dirPath+"/signatureCoordinates.json"),coordinatesInfo);
		} catch (IOException e) {

			e.printStackTrace();
			return"{\"Error\":\"Failed to create signatureCoordinates.json file\"}";
		}
		return "{\"coordinatsFileName\" : \"signatureCoordinates.json\"}";
		
	}

	@RequestMapping(value = {"/signImage", "/ws/signImage"}, method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String signUploadedImage(@RequestBody SignedImageInfo signedImageInfo,  HttpServletRequest req) throws FontFormatException {
		try {
			signImageService.signImage(signedImageInfo , docuSignUtil.getRealPath(req));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return"{\"Error\":\"Failed to create signatureCoordinates.json file\"}";
		}
		return "{\"SignatureFileName\" : \"signedImage.jpeg\",\"InitialFileName\" : \"initialsImage.jpeg\"}";
	}

	@RequestMapping(value = {"/signDocument", "/ws/signDocument"}, method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String signUploadedDocument(@RequestBody CoordinatesInfo coordinatesInfo, HttpServletRequest req,@RequestParam(value="contractId", required=false) Long contractId) throws Exception {
		
		Contracts contracts =null;
		String result = staticPath.substring(0,staticPath.lastIndexOf("/"));
		String url = signDocumentService.addSignaturetoPdf(coordinatesInfo, result);
		if(contractId != null) {
			contracts  = contractsTemplateService.getContractById(contractId);
			Contracts parentContract  = null;
			if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())) {
				 parentContract  = contracts;
				 
				 	String parentFromStage = parentContract.getTemplateSubmissionStatus();
				    parentContract.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_VERIFY);
					if(url.contains(result)) {
						url = url.replace(result, "");
					}
					parentContract.setTemplateFileURL(url);
					contractsTemplateService.saveNewContract(parentContract);
					contractSignedByReceiver(parentContract);
					
					ContractsHistory parentContractsHistory = new ContractsHistory();
					parentContractsHistory.setFromStage(parentFromStage);
					parentContractsHistory.setContractsId(contracts);
					parentContractsHistory.setToStage(parentContract.getTemplateSubmissionStatus());
					parentContractsHistory.setCreatedDate(new Date());
					parentContractsHistory.setUpdatedDate(new Date());
					contractsTemplateDao.saveContractsHistory(parentContractsHistory);
				 
				 
			}else {
				 parentContract  = contracts.getParentContractId();
				 
				 if(null != parentContract.getId()){
		    			String parentFromStage = contracts.getTemplateSubmissionStatus();
		    			String childFromStage = parentContract.getTemplateSubmissionStatus();
//						childContarct.setId(childContarct.getId());
		    			contracts.setTemplateSubmissionStatus(Constants.SEND_FOR_VERIFICATION);
//						contracts.setId(contracts.getId());
						if(url.contains(result)) {
							url = url.replace(result, "");
						}
						parentContract.setTemplateFileURL(url);
						contracts.setTemplateFileURL(url);
						parentContract.setTemplateSubmissionStatus(Constants.WAITING_TO_BE_VERIFY);
						contractsTemplateService.saveNewContract(contracts);
						contractsTemplateService.saveNewContract(parentContract);
						contractSignedByReceiver(contracts);
						
						ContractsHistory parentContractsHistory = new ContractsHistory();
						parentContractsHistory.setFromStage(parentFromStage);
						parentContractsHistory.setContractsId(contracts);
						parentContractsHistory.setToStage(contracts.getTemplateSubmissionStatus());
						parentContractsHistory.setCreatedDate(new Date());
						parentContractsHistory.setUpdatedDate(new Date());
						contractsTemplateDao.saveContractsHistory(parentContractsHistory);
						
						ContractsHistory childContractsHistory = new ContractsHistory();
		    			childContractsHistory.setFromStage(childFromStage);
						childContractsHistory.setContractsId(parentContract);
						childContractsHistory.setToStage(parentContract.getTemplateSubmissionStatus());
						childContractsHistory.setCreatedDate(new Date());
						childContractsHistory.setUpdatedDate(new Date());
						contractsTemplateDao.saveContractsHistory(childContractsHistory);
						
					}
			}
    		
		}
		return "{\"FileName\" : \"signed.pdf\"}";
	}

	
	private void contractSignedByReceiver(Contracts contracts) throws Exception
	{
		if(Constants.FULL_TIME_OFFER.equalsIgnoreCase(contracts.getContractType())) {
			
			for(ContractOwnerHierarchy coh : contracts.getOwnerHierarchyList()){
				User owner = userService.getUserByUserCode(coh.getOwnerUserId().getUserCode());
//				User receiver = coh.getOwnerUserId();
				User sender = contracts.getCandidate().getConsultantUser();
				EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(owner.getFirstName());
				 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
					 emailMessage.setLastName(owner.getLastName());
				 }else{
					 emailMessage.setLastName("");
				 }
				 emailMessage.setToEmail(owner.getEmail());
				 emailMessage.setFromEmail(sender.getEmail());
				 emailMessage.setCreatedBy("sladmin");
				 if(sender.getLastName()!= null && !"".equalsIgnoreCase(sender.getLastName())){
					 StringBuilder sb = new StringBuilder();
					 emailMessage.setFromName(sb.append(sender.getFirstName()).append(" ").append(sender.getLastName()).toString());
				 }else{
					 emailMessage.setFromName(sender.getFirstName());
				 }
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 
				 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
					 globalVarsMap.put("contractTitle", contracts.getContractTitle());
					 }else{
						 globalVarsMap.put("contractTitle","--");
					 }
				 
				 if(owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())){
					StringBuilder ownersb = new StringBuilder();
					 globalVarsMap.put("ownerUser", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
					 }else{
						 globalVarsMap.put("ownerUser",owner.getFirstName());
					 }
				 
				 if(sender.getFirstName() != null && !"".equalsIgnoreCase(sender.getFirstName()) && sender.getLastName() != null && !"".equalsIgnoreCase(sender.getLastName())){
						StringBuilder receiversb = new StringBuilder();
						 globalVarsMap.put("receiverUser", receiversb.append(sender.getFirstName()).append(" ").append(sender.getLastName()).toString());
						 }else{
							 globalVarsMap.put("receiverUser",sender.getFirstName());
						 }
				
				 if(contracts.getOrgLocationId() != null){
					 globalVarsMap.put("receiverOrg", contracts.getOrgLocationId().getOrgLocationName());
					 }else{
						 globalVarsMap.put("receiverOrg","--");
					 }
				 
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_EMAIL);
				 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_NOTIFICATION);
				 
				 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
				 notificationScenario.setBody(notificationbody);

				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;
				 
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 StringBuilder notificationLink = new StringBuilder();
				 String actionLink="";
			     actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=verify").toString();

				 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 
			 emailScenerioUtil.sendNotification(actionLink,sender,owner, contracts.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
			}
			
		}else {
			
			for(ContractOwnerHierarchy coh : contracts.getParentContractId().getOwnerHierarchyList()){
				User owner = userService.getUserByUserCode(contracts.getOwnerUserId().getUserCode());
				User receiver = coh.getOwnerUserId();
				EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(owner.getFirstName());
				 if(owner.getLastName()!= null && !"".equalsIgnoreCase(owner.getLastName())){
					 emailMessage.setLastName(owner.getLastName());
				 }else{
					 emailMessage.setLastName("");
				 }
				 emailMessage.setToEmail(owner.getEmail());
				 emailMessage.setFromEmail(receiver.getEmail());
				 emailMessage.setCreatedBy("sladmin");
				 if(receiver.getLastName()!= null && !"".equalsIgnoreCase(receiver.getLastName())){
					 StringBuilder sb = new StringBuilder();
					 emailMessage.setFromName(sb.append(receiver.getFirstName()).append(" ").append(receiver.getLastName()).toString());
				 }else{
					 emailMessage.setFromName(receiver.getFirstName());
				 }
				 
				//setting the class filed values
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 
				 if(contracts.getContractTitle()!= null && !"".equalsIgnoreCase(contracts.getContractTitle())){
					 globalVarsMap.put("contractTitle", contracts.getContractTitle());
					 }else{
						 globalVarsMap.put("contractTitle","--");
					 }
				 
				 if(owner.getFirstName() != null && !"".equalsIgnoreCase(owner.getFirstName()) && owner.getLastName() != null && !"".equalsIgnoreCase(owner.getLastName())){
					StringBuilder ownersb = new StringBuilder();
					 globalVarsMap.put("ownerUser", ownersb.append(owner.getFirstName()).append(" ").append(owner.getLastName()).toString());
					 }else{
						 globalVarsMap.put("ownerUser",owner.getFirstName());
					 }
				 
				 if(receiver.getFirstName() != null && !"".equalsIgnoreCase(receiver.getFirstName()) && receiver.getLastName() != null && !"".equalsIgnoreCase(receiver.getLastName())){
						StringBuilder receiversb = new StringBuilder();
						 globalVarsMap.put("receiverUser", receiversb.append(receiver.getFirstName()).append(" ").append(receiver.getLastName()).toString());
						 }else{
							 globalVarsMap.put("receiverUser",receiver.getFirstName());
						 }
				
				 if(contracts.getContractOrgLocationId() != null){
					 globalVarsMap.put("receiverOrg", contracts.getContractOrgLocationId().getOrg().getOrgName());
					 }else{
						 globalVarsMap.put("receiverOrg","--");
					 }
				 
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_EMAIL);
				 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CONTRACT_SIGNED,Constants.ENTITY_TYPE_NOTIFICATION);
				 
				 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
				 notificationScenario.setBody(notificationbody);

				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;
				 
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, contracts.getOrgLocationId().getOrg(),contracts.getOrgLocationId());
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 StringBuilder notificationLink = new StringBuilder();
				 String actionLink="";
			     actionLink = notificationLink.append(sourceleadUrl).append("/").append("contractListbyStatus?"+"contractType="+contracts.getContractType()+"&status=verify").toString();

				 emailScenerioUtil.sendEmail(emailMessage,contracts.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
				 
			 emailScenerioUtil.sendNotification(actionLink,receiver,owner, contracts.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
			}
			
		}
	}

}
