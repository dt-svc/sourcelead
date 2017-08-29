package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadNotesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

/**
 * @author Ajith Matta
 *
 */
@Service
public class SourceleadNotesServiceImpl implements SourceleadNotesService {
	@Autowired
	private SourceleadNotesDao sourceleadNotesDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesServiceImpl.class);
	
	
	@Override
	public SourceleadNotes storeComments(JSONObject jsonObj, User user, OrgLocations orgLocations) throws Exception
	{
		SourceleadNotes sourceleadNotes = new SourceleadNotes();
		
		
		if (jsonObj.has("invoiceUUID") && jsonObj.getString("invoiceUUID") != null) {
			Invoice invoice=invoiceService.getInvoicetByInvoiceUUID(jsonObj.getString("invoiceUUID"));
			sourceleadNotes.setInvoicdeId(invoice);
		}
		
		if (jsonObj.has("requirementId") && jsonObj.getString("requirementId") != null) {
			Requirement req=requirementService.getRequirementByRequirementId(jsonObj.getLong("requirementId"));
			sourceleadNotes.setRequirementId(req);
		}
		
		if(jsonObj.has("contractUUID")&& jsonObj.getString("contractUUID") != null){
			Contracts contracts = contractsTemplateService.getContractByContractUUID(jsonObj.getString("contractUUID"));
			sourceleadNotes.setContractId(contracts);
		}
		
		if (jsonObj.has("noteDescription") && jsonObj.getString("noteDescription") != null) {
			sourceleadNotes.setComment(jsonObj.getString("noteDescription"));
		}
		if (jsonObj.has("noteType") && jsonObj.getString("noteType") != null) {
			sourceleadNotes.setNoteType(jsonObj.getString("noteType"));
		}
		if(orgLocations != null){
			sourceleadNotes.setOrgLocationID(orgLocations);
		}
		if (jsonObj.has("orgRelationId")
				&& !"".equalsIgnoreCase(jsonObj.getString("orgRelationId"))) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(jsonObj.getString("orgRelationId")));
			sourceleadNotes.setOrgRelationID(orgRelation);
		}
		else {
			sourceleadNotes.setOrgRelationID(null);
		}
		if (jsonObj.has("categoryType")
				&& !"".equalsIgnoreCase(jsonObj.getString("categoryType"))) {
			sourceleadNotes.setCategoryType(jsonObj.getString("categoryType"));
			if (Constants.NOTES_JOBS.equalsIgnoreCase(jsonObj.getString("categoryType"))) {
				Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(jsonObj.getString("vendorcontact")));
				sourceleadNotes.setRequirementId(requirement);
			}
			else {
				sourceleadNotes.setRequirementId(null);
			}
			if (Constants.NOTES_CONTRACTS.equalsIgnoreCase(jsonObj.getString("categoryType"))) {
				Contracts contracts = contractsTemplateService.getContractById(Long.parseLong(jsonObj.getString("vendorcontact")));
				sourceleadNotes.setContractId(contracts);
			}
			else {
				sourceleadNotes.setContractId(null);
			}
			if (Constants.NOTES_BILLING.equalsIgnoreCase(jsonObj.getString("categoryType"))) {
				Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(jsonObj.getString("vendorcontact")));
				sourceleadNotes.setInvoicdeId(invoice);
			}
			else {
				sourceleadNotes.setInvoicdeId(null);
			}
			if (Constants.NOTES_CONTACTS.equalsIgnoreCase(jsonObj.getString("categoryType"))) {
				OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(jsonObj.getString("vendorcontact")));
				sourceleadNotes.setSpocId(orgSpoc);
			}
			else {
				sourceleadNotes.setSpocId(null);
			}
		}
		else {
			sourceleadNotes.setCategoryType(null);
		}
		if (jsonObj.has("allowAll")) {
			sourceleadNotes.setCheckBox(jsonObj.getString("allowAll"));
		}
		else {
			sourceleadNotes.setCheckBox(Constants.NO);
		}
		sourceleadNotes.setUserId(user);
		sourceleadNotes.setCreatedDate(commonUtil.currentDate());
		sourceleadNotes.setUpdatedDate(commonUtil.currentDate());
		sourceleadNotes.setCreatedBy(user.getUserCode());
		sourceleadNotes.setUpdatedBy(user.getUserCode());
		return sourceleadNotesDao.storeComments(sourceleadNotes);
	}
	
	@Override
	public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelationId,Long orgLocationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByOrglocationIdAndType(orgrelationId,orgLocationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			// for image display
			User user = userService.getUserByUserCode(notes.getCreatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
			}
			
			if (notes.getRequirementId() != null) {
				notes.setRequirementTitle(notes.getRequirementId().getJobTitle());
			}
			if (notes.getContractId() != null) {
				notes.setContract(notes.getContractId().getContractTitle());
			}
			if (notes.getInvoicdeId() != null) {
				notes.setInvoiceId(notes.getInvoicdeId().getInvoiceId());
			}
			
			if (notes.getSpocId() != null) {
				notes.setContactName(notes.getSpocId().getClientUser().getFirstName());
			}
			notes.setCheckBox(notes.getCheckBox());
			notes.setUserIdStr(notes.getUserId().getId());
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - notes.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays < 7) {
				notes.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(notes.getCreatedDate());
				notes.setCreatedDateString(commonDateFormat.toString());
			}
		}
		return notesList;
	}
	
	@Override
	public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId,Long orgLocationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByRequirementId(requirementId,orgLocationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			// for image display
			User user = userService.getUserByUserCode(notes.getCreatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
			}
			if (notes.getRequirementId() != null) {
				notes.setRequirementTitle(notes.getRequirementId().getJobTitle());
			}
			notes.setCheckBox(notes.getCheckBox());
			notes.setUserIdStr(notes.getUserId().getId());
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - notes.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays < 7) {
				notes.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(notes.getCreatedDate());
				notes.setCreatedDateString(commonDateFormat.toString());
			}
		}
		return notesList;
	}
	
	@Override
	public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId,Long orgLocationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByInvoiceID(invoiceId,orgLocationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			// for image display
			User user = userService.getUserByUserCode(notes.getCreatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
			}
			if (notes.getInvoicdeId() != null) {
				notes.setInvoiceId(notes.getInvoicdeId().getInvoiceId());
			}
			notes.setCheckBox(notes.getCheckBox());
			notes.setUserIdStr(notes.getUserId().getId());
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - notes.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays < 7) {
				notes.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(notes.getCreatedDate());
				notes.setCreatedDateString(commonDateFormat.toString());
			}
		}
		return notesList;
	}
	
	
	
	@Override
	public List<SourceleadNotes> getNotesListByContractId(Long contractId,Long orgLocationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByContractId(contractId,orgLocationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			// for image display
			User user = userService.getUserByUserCode(notes.getCreatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
			}
			if (notes.getContractId() != null) {
				notes.setContract(notes.getContractId().getContractTitle());
			}
			notes.setCheckBox(notes.getCheckBox());
			notes.setUserIdStr(notes.getUserId().getId());
			// date time format conversion.
			long diff = commonUtil.currentDate().getTime() - notes.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}
			else if (diffDays == 0 && diffHours == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays == 0) {
				notes.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}
			else if (diffDays < 7) {
				notes.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
			}
			else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(notes.getCreatedDate());
				notes.setCreatedDateString(commonDateFormat.toString());
			}
		}
		return notesList;
	}
	
}
