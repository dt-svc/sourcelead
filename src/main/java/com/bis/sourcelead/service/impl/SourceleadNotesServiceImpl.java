package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.dto.SourceleadNotesDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.CandidateService;
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
	private ImmigrationCommonService immigrationCommonService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private AssetService assetService;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesServiceImpl.class);
	
	
	@Override
	public SourceleadNotes storeComments(SourceleadNotesDTO sourceleadNotesDTO, User user, OrgLocations orgLocations) throws Exception
	{
		SourceleadNotes sourceleadNotes = new SourceleadNotes();
		
		
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getInvoiceUUID())) {
			Invoice invoice=invoiceService.getInvoicetByInvoiceUUID(sourceleadNotesDTO.getInvoiceUUID());
			sourceleadNotes.setInvoicdeId(invoice);
		}
		
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getRequirementId())) {
			Requirement req=requirementService.getRequirementByRequirementId(Long.parseLong(sourceleadNotesDTO.getRequirementId()));
			sourceleadNotes.setRequirementId(req);
		}
		
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getImmigrationId())) {
			Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(sourceleadNotesDTO.getCandidateId()));
			Immigration img=immigrationCommonService.getImmigrationByImmigrationId(Long.parseLong(sourceleadNotesDTO.getImmigrationId()));
			sourceleadNotes.setImmigration(img);
			sourceleadNotes.setCandidate(candidate);
		}
		
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getAssetId())) {
			Asset asset =assetService.getAssetById(Long.parseLong(sourceleadNotesDTO.getAssetId()));
			sourceleadNotes.setAssetId(asset);
		}
		
		if(StringUtils.isNotBlank(sourceleadNotesDTO.getContractUUID())){
			Contracts contracts = contractsTemplateService.getContractByContractUUID(sourceleadNotesDTO.getContractUUID());
			sourceleadNotes.setContractId(contracts);
		}
		
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getNoteDescription())) {
			sourceleadNotes.setComment(sourceleadNotesDTO.getNoteDescription());
		}
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getNoteType())) {
			sourceleadNotes.setNoteType(sourceleadNotesDTO.getNoteType());
		}
		if(orgLocations != null){
			sourceleadNotes.setOrgLocationID(orgLocations);
		}
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getOrgRelationId())) {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(sourceleadNotesDTO.getOrgRelationId()));
			sourceleadNotes.setOrgRelationID(orgRelation);
		}
		else {
			sourceleadNotes.setOrgRelationID(null);
		}
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getCategoryType())) {
			sourceleadNotes.setCategoryType(sourceleadNotesDTO.getCategoryType());
			if (Constants.NOTES_JOBS.equalsIgnoreCase(sourceleadNotesDTO.getCategoryType()) && (sourceleadNotesDTO.getVendorcontact()!=null)) {
				
				Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(sourceleadNotesDTO.getVendorcontact()));
				sourceleadNotes.setRequirementId(requirement);
			}
			else {
				sourceleadNotes.setRequirementId(null);
			}
			if (Constants.NOTES_CONTRACTS.equalsIgnoreCase(sourceleadNotesDTO.getCategoryType())) {
				Contracts contracts = contractsTemplateService.getContractById(Long.parseLong(sourceleadNotesDTO.getVendorcontact()));
				sourceleadNotes.setContractId(contracts);
			}
			else {
				sourceleadNotes.setContractId(null);
			}
			if (Constants.NOTES_BILLING.equalsIgnoreCase(sourceleadNotesDTO.getCategoryType())) {
				Invoice invoice = invoiceService.getInvoicetById(Long.parseLong(sourceleadNotesDTO.getVendorcontact()));
				sourceleadNotes.setInvoicdeId(invoice);
			}
			else {
				sourceleadNotes.setInvoicdeId(null);
			}
			if (Constants.NOTES_CONTACTS.equalsIgnoreCase(sourceleadNotesDTO.getCategoryType())) {
				OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(sourceleadNotesDTO.getVendorcontact()));
				sourceleadNotes.setSpocId(orgSpoc);
			}
			else {
				sourceleadNotes.setSpocId(null);
			}
		}
		else {
			sourceleadNotes.setCategoryType(null);
		}
		if (StringUtils.isNotBlank(sourceleadNotesDTO.getAllowAll())) {
			sourceleadNotes.setCheckBox(sourceleadNotesDTO.getAllowAll());
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
				if(StringUtils.isNotBlank(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
				notes.setCommentAuthorImage(user.getFileUrl());
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
				StringBuilder nameBuilder1 = new StringBuilder();
				if(StringUtils.isNotBlank(notes.getSpocId().getClientUser().getLastName())){
					notes.setContactName(nameBuilder1.append(notes.getSpocId().getClientUser().getFirstName()).append(" ").append(notes.getSpocId().getClientUser().getLastName()).toString());
				}else{
					notes.setContactName(notes.getSpocId().getClientUser().getFirstName());
				}
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
				notes.setCommentAuthorImage(user.getFileUrl());
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
				notes.setCommentAuthorImage(user.getFileUrl());
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
				if(StringUtils.isNotEmpty(user.getLastName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
				notes.setCommentAuthorImage(user.getFileUrl());
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

	@Override
	public List<SourceleadNotes> getNotesListByAssetId(Long assetId, Long orgLocationId) throws Exception {
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByAssetId(assetId,orgLocationId);
		for (SourceleadNotes notes : notesList) {
			notes.setComment(notes.getComment());
			// for image display
			User user = userService.getUserByUserCode(notes.getCreatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getLastName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				notes.setUserName(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				notes.setCommentAuthorUserId(user.getId());
				notes.setCommentAuthorImage(user.getFileUrl());
			}
			if (notes.getAssetId() != null) {
				notes.setAssetName(notes.getAssetId().getSlno());
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
	public List<SourceleadNotes> getNotesListByImmigrationId(Long immigrationId,Long orgLocationId) throws Exception
	{
		List<SourceleadNotes> notesList = sourceleadNotesDao.getNotesListByImmigrationId(immigrationId,orgLocationId);
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
				notes.setCommentAuthorImage(user.getFileUrl());
			}
			if (notes.getImmigration() != null) {
				notes.setImmigrationId(notes.getImmigration().getReceiptNo());
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
