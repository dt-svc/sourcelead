package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ContractsTemplateDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SignatureRequestUtil;
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
	private SignatureRequestUtil signatureRequestUtil;
	
	@Override
	public ContractsTemplate saveContractTemplate(JSONObject jsonObj, User user, OrgLocations orgLocation) throws Exception {
		
		
		ContractsTemplate contractsTemplate = new ContractsTemplate();
		contractsTemplate.setOrgLocationId(orgLocation);
		
		contractsTemplate.setTemplateName(jsonObj.getString("templateName"));
		
		contractsTemplate.setTemplateType(jsonObj.getString("contractType"));
		
		if(jsonObj.has("letterHead") && !isNullOrEmpty(jsonObj.getString("letterHead"))){
			contractsTemplate.setLetterHead(jsonObj.getString("letterHead"));
		}
		
		if(jsonObj.has("description") && !isNullOrEmpty(jsonObj.getString("description"))){
			contractsTemplate.setDescription(jsonObj.getString("description"));
		}
		
		if(jsonObj.has("templateContent") && !isNullOrEmpty(jsonObj.getString("templateContent"))){
			String content = jsonObj.getString("templateContent");
			String header = "<div style='text-align: center;'>"+jsonObj.getString("templateName")+"<br></div><br>";
			String totalBody = header + content;
			contractsTemplate.setTemplateContent(totalBody);
		}
		contractsTemplate.setCreatedDate(new Date());
		contractsTemplate.setCreatedBy(user.getUserCode());
		contractsTemplate.setUpdatedDate(new Date());
		contractsTemplate.setUpdatedBy(user.getUserCode());
		// for active templates status is 0
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
	public ContractsTemplate getContractTemplateById(Long contractTemplateId) {
		return contractsTemplateDao.saveContractTemplate(contractTemplateId);
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
	public Contracts saveNewContract(JSONObject jsonObj, OrgLocations orgLocation, String saveType,User user,Org org) throws Exception
	{
		Contracts contracts = new Contracts();
		Contracts vendorContracts = new Contracts();
		String pattern = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
		contracts.setContractType(jsonObj.getString("contractType"));
		ContractsTemplate contractsTemplate = contractsTemplateDao.getContractTemplateById(Long.parseLong(jsonObj.getString("template")));
		if(null != contractsTemplate){
			contracts.setContractsTemplate(contractsTemplate);
		}
		contracts.setContractTitle(jsonObj.getString("contractTitle"));
		vendorContracts.setContractTitle(jsonObj.getString("contractTitle"));
		User ownerUser = userService.getUserById(Long.parseLong(jsonObj.getString("owner")));
		contracts.setOwnerUserId(ownerUser);
        Date startDate = format.parse(jsonObj.getString("startDate"));
		contracts.setStartDate(startDate);
		vendorContracts.setStartDate(startDate);
		if(jsonObj.has("endDate")){
			if(null != jsonObj.getString("endDate") && !"".equals(jsonObj.getString("endDate"))){
				Date endDate = format.parse(jsonObj.getString("endDate"));
				contracts.setEndDate(endDate);
				vendorContracts.setEndDate(endDate);
			}
		}
		User approverUser = null;
		User vendorContactsUser = null;
		if(jsonObj.has("approver") && null != jsonObj.getString("approver") && !"".equals(jsonObj.getString("approver"))){
			 approverUser = userService.getUserById(Long.parseLong(jsonObj.getString("approver")));
			contracts.setApproverUser(approverUser);
		}
		if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setInitiatorType("Client");
			contracts.setOrgLocationId(orgLocation);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
//			if(null != vendorUser && null != approverUser && !"draft".equalsIgnoreCase(saveType)){
//				signatureRequestUtil.sendDocumentForSignatureRequest(vendorUser,contractsTemplate,orgLocation,user);
//			}
			contracts.setContactUser(vendorContactsUser);
		}
		if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			contracts.setInitiatorType("Vendor");
			contracts.setOrgLocationId(orgLocation);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("clientOrgRelationId")));
			contracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getLocationId());
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				contracts.setContractOrgLocationId(orgRelations.getResponderLocationId());
			}
			vendorContactsUser = userService.getUserById(Long.parseLong(jsonObj.getString("clientContact")));
			/*if(null != vendorUser && null != approverUser && !"draft".equalsIgnoreCase(saveType)){
				signatureRequestUtil.sendDocumentForSignatureRequest(vendorUser,contractsTemplate,orgLocation,user);
			}*/
			contracts.setContactUser(vendorContactsUser);
		}
		
		if(jsonObj.has("endDate") && null != jsonObj.getString("billingAmount") && !"".equals(jsonObj.getString("billingAmount")) ){
			contracts.setBillingAmount(jsonObj.getString("billingAmount"));
			vendorContracts.setBillingAmount(jsonObj.getString("billingAmount"));
		}
		if(jsonObj.has("billingPer") && null != jsonObj.getString("billingPer") && !"".equals(jsonObj.getString("billingPer")) ){
			contracts.setBillingPer(jsonObj.getString("billingPer"));
			vendorContracts.setBillingPer(jsonObj.getString("billingPer"));
		}
		contracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		vendorContracts.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
		
		if(jsonObj.has("paymentFrequency") && null != jsonObj.getString("paymentFrequency") && !"".equals(jsonObj.getString("paymentFrequency")) ){
			contracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
			vendorContracts.setPaymentTerm(jsonObj.getString("paymentFrequency"));
		}
		if(jsonObj.has("tax") && null != jsonObj.getString("tax") && !"".equals(jsonObj.getString("tax")) ){
			contracts.setTax(jsonObj.getString("tax"));
			vendorContracts.setTax(jsonObj.getString("tax"));
		}
		if(jsonObj.has("taxType") && null != jsonObj.getString("taxType") && !"".equals(jsonObj.getString("taxType")) ){
			contracts.setTaxType(jsonObj.getString("taxType"));
			vendorContracts.setTaxType(jsonObj.getString("taxType"));
		}
		if(jsonObj.has("description") && null != jsonObj.getString("description") && !"".equals(jsonObj.getString("description"))){
			contracts.setDescription(jsonObj.getString("description"));
			vendorContracts.setDescription(jsonObj.getString("description"));
		}
		contracts.setStatus("0");
//		contracts.setTemplateSubmissionStatus("pending");
		vendorContracts.setTemplateSubmissionStatus("Waiting to be signed");
		vendorContracts.setStatus("0");
		contracts.setTemplateSubmissionStatus(saveType);
		contracts.setCreatedDate(new Date());
		contracts.setCreatedBy(user.getUserCode());
		contracts.setUpdatedDate(new Date());
		contracts.setUpdatedBy(user.getUserCode());
		Contracts savedContracts = contractsTemplateDao.saveNewContract(contracts);
		
		if("Vendor MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			vendorContracts.setContractType("Client MSA");
			vendorContracts.setInitiatorType("Client");
			vendorContracts.setContractOrgLocationId(orgLocation);
			vendorContracts.setParentContractId(savedContracts);
			OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
			vendorContracts.setOrgRelations(orgRelations);
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				vendorContracts.setOrgLocationId(orgRelations.getLocationId());
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				vendorContracts.setOrgLocationId(orgRelations.getResponderLocationId());
			}
			
			User vendorUser = userService.getUserById(Long.parseLong(jsonObj.getString("vendorContact")));
			/*if(null != vendorUser && !"draft".equalsIgnoreCase(saveType)){
				signatureRequestUtil.sendDocumentForSignatureRequest(vendorUser,contractsTemplate,orgLocation,user);
			}*/
			vendorContracts.setOwnerUserId(vendorUser);
			vendorContracts.setContactUser(ownerUser);
		}
		
		if("Client MSA".equalsIgnoreCase(jsonObj.getString("contractType"))){
			vendorContracts.setContractType("Vendor MSA");
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
		Contracts savedChildContracts = contractsTemplateDao.saveNewContract(vendorContracts);
		  if(null != vendorContactsUser && null != approverUser && !"draft".equalsIgnoreCase(saveType)){
	            signatureRequestUtil.sendDocumentForSignatureRequest(vendorContactsUser,contractsTemplate,orgLocation,user,savedContracts,org);
	        }
		return savedContracts;
		
		
	}

	@Override
	public Contracts getAllContractsByLocationIdAndStatus(OrgLocations orgLocation, String contractSubmissionType, String tabType) throws Exception
	{
		List<Contracts> vendorcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,"Vendor");
		List<Contracts> clientcontractsList = contractsTemplateDao.getAllContractsByLocationIdAndStatus(orgLocation,contractSubmissionType,"Client");
		
		Contracts contractsForList = new Contracts();
		
		for(Contracts contracts :vendorcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId().getFirstName() && null != contracts.getOwnerUserId().getFirstName()){
				ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
			}else{
				ownerName.append(contracts.getOwnerUserId().getFirstName());	
			}
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getFirstName()){
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
		}
		for(Contracts contracts :clientcontractsList ){
			StringBuilder ownerName = new StringBuilder();
			StringBuilder vendorName = new StringBuilder();
			if(null != contracts.getOwnerUserId().getFirstName() && null != contracts.getOwnerUserId().getFirstName()){
				ownerName.append(contracts.getOwnerUserId().getFirstName()).append((contracts.getOwnerUserId().getLastName()));
			}else{
				ownerName.append(contracts.getOwnerUserId().getFirstName());	
			}
			contracts.setOwnerName(ownerName.toString());
			if(null != contracts.getContactUser().getFirstName() && null != contracts.getContactUser().getFirstName()){
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
		}
		
		contractsForList.setVendorcontractsList(vendorcontractsList);
		contractsForList.setClientcontractsList(clientcontractsList);
		return contractsForList;
	}

	@Override
	public Contracts getContractById(Long contractId)
	{
		return contractsTemplateDao.getContractById(contractId);
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

	
}
