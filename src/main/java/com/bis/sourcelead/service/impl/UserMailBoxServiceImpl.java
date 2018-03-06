package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserMailBoxDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.helper.CampaignIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class UserMailBoxServiceImpl implements UserMailBoxService{
	
	@Autowired
	UserMailBoxDao userMailBoxDao;
	
	@Autowired
	CampaignIndexHelper campaignIndexHelper;
	
	@Autowired
    private SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public UserMailBox saveUserMailBox(UserMailBox userMailBox) throws Exception {
		userMailBox.setUpdatedDate(commonUtil.currentDate());
		return userMailBoxDao.saveUserMailBox(userMailBox);
	}

	@Override
	public List<UserMailBox> getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(Long userId, Long orgLocationId,
			String mailStatus) throws Exception {
		return userMailBoxDao.getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(userId, orgLocationId, mailStatus);
	}

	@Override
	public UserMailBox getUserMailBoxByUUID(String UUID) {
		return userMailBoxDao.getUserMailBoxByUUID(UUID);
	}

	@Override
	public void setRequestForResumeDetails(HotListEmailCampaignDTO hotListEmailCampaignDTO,String candidatesIds,String UUID) throws Exception {
		
		UserMailBox mailBox = userMailBoxDao.getUserMailBoxByUUID(UUID);
		
		if(null != mailBox){
			
			UserMailBox userMailBox = new UserMailBox();
			userMailBox.setToMail(mailBox.getFromMail());
			userMailBox.setFromMail(hotListEmailCampaignDTO.getEmail());
			userMailBox.setMailStatus(Constants.MAIL_REQUESTED);
			userMailBox.setCandidateId(candidatesIds);
			userMailBox.setUserId(mailBox.getUserId());
			userMailBox.setOrgLocationId(mailBox.getOrgLocationId());
			userMailBox.setIsFavourite(0);
			userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
			StringBuilder sb = new StringBuilder();
			userMailBox.setSubject(sb.append("Rep: ").append(mailBox.getSubject()).toString());
			
			if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getPhone())){
				userMailBox.setPhno(hotListEmailCampaignDTO.getPhone());
			}
			
			if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getComment())){
				userMailBox.setComments(hotListEmailCampaignDTO.getComment());
			}
			
			userMailBox.setIsMailRead(0);
			userMailBox.setStatus(1);
			userMailBox.setMailType(2);
			userMailBox.setCreatedDate(new Date());
			userMailBox.setUpdatedDate(new Date());
			
			if(hotListEmailCampaignDTO.getFileInfo() != null){
			 FileInfo fileInfo = hotListEmailCampaignDTO.getFileInfo().get(0);
			 if(fileInfo != null) {
				 userMailBox.setFileContentType(fileInfo.getContentType());
				 userMailBox.setFileUrl(fileInfo.getFileURL());
			   	 userMailBox.setFileName(fileInfo.getFileName());
			   	FileUploadUtil.moveFile(hotListEmailCampaignDTO.getStaticPath(), fileInfo, "Hotlist Campaign Documents");
			 }
			}
			 
			 
			String table = getRequestedHotListCandidatesTable(candidatesIds);
			userMailBox.setBody(table);
			
			userMailBoxDao.saveUserMailBox(userMailBox);
			
			mailBox.setIsMailRead(1);
			userMailBoxDao.saveUserMailBox(mailBox);
			saveOrUpdateUserMailBoxELK(userMailBox);
		}
		
	}
	
	
	private String getRequestedHotListCandidatesTable(String candidateIds) throws Exception{
		
		
		 String tempHTML = null;
		 
		 tempHTML = "<div class='clearfix project_wrap_table mail-table  mb'>";
		 tempHTML = tempHTML+"<table>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Email<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Phno<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Experience<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Position Type<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
	    List<Long> candidateId = new ArrayList<Long>();
		 for (String s : candidateIds.split(",")){
			 candidateId.add(Long.parseLong(s));
		 }
		 
		 List<Candidate> candidatesList = candidateService.getAllRequestedCandidatesForCampaign(candidateId);
		 for(Candidate candidate : candidatesList){
			
			 String candidateName = null;
			 String experienceInYears = null;
			 String experienceInMonths = null;
			 String totalExp = null;
			 String phno = null;
			 String designation = null;
			 
			 if(!isNullOrEmpty(candidate.getLastName())){
				 StringBuilder name = new StringBuilder();
				 candidateName = name.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
		   	 }else{
		   		candidateName = candidate.getFirstName();
		   	 }
			
			 if(!isNullOrEmpty(candidate.getExperienceYears())){
				 experienceInYears = candidate.getExperienceYears();
			 }else{
				 experienceInYears = "0";
			 }
			 
			 if(!isNullOrEmpty(candidate.getExperienceMonths())){
				 experienceInMonths = candidate.getExperienceMonths();
			 }else{
				 experienceInMonths = "0";
			 }
			 
			 if(isNullOrEmpty(candidate.getExperienceMonths()) && isNullOrEmpty(candidate.getExperienceMonths())){
				 totalExp = "-----";
			 }else{
				 StringBuilder exp = new StringBuilder();
				 totalExp = exp.append(experienceInYears).append(" Years ").append(experienceInMonths).append(" Months").toString();
			 }
			 
			 if(!isNullOrEmpty(candidate.getMobilePhone())){
				 phno = candidate.getMobilePhone();
			 }else{
				 phno = "-----";
			 }
			 
			 if(!isNullOrEmpty(candidate.getDesignation())){
				 designation = candidate.getDesignation();
			 }else{
				 designation = "-----";
			 }
			 
			 candidate.setCandidateName(candidateName);
			 candidate.setTotalExp(totalExp);
			 
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+candidateName.toString()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+candidate.getEmail()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+phno+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+totalExp+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+designation+"</td>";
			 
			 tempHTML = tempHTML+"</tr>";
			 
		 }
		 tempHTML = tempHTML+"</table>";
		 tempHTML = tempHTML+"</div>";
		 
		 return tempHTML;
	}

	@Override
	public UserMailBox getUserMailBoxById(String Id) throws Exception{
		return userMailBoxDao.getUserMailBoxById(Id);
	}

	@Override
	public List<UserMailBox> getUserFavouriteMailsByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		return userMailBoxDao.getUserFavouriteMailsByUserIdAndOrgLocationId(userId, orgLocationId);
	}

	@Override
	public Integer getUnReadMessagesByUserIdAndLocationIdAndType(Long userId, Long orgLocationId, String type)
			throws Exception {
		return userMailBoxDao.getUnReadMessagesByUserIdAndLocationIdAndType(userId, orgLocationId, type);
	}

	@Override
	public UserMailBox getUserPreviousMail(Long Id, Long userId, Long orgLocationId,String type,String mailType) throws Exception {
		return userMailBoxDao.getUserPreviousMail(Id, userId, orgLocationId,type,mailType);
	}
	
	@Override
	public UserMailBox getUserNextMail(Long Id, Long userId, Long orgLocationId,String type,String mailType) throws Exception {
		return userMailBoxDao.getUserNextMail(Id, userId, orgLocationId,type,mailType);
	}

	@Override
	public void saveOrUpdateUserMailBoxELK(UserMailBox userMailBox) throws Exception {
		
		 //Elastic Search Document Saving
		CampaignIndex campaignIndex = campaignIndexHelper.getCampaignIndexFromUserMailBox(userMailBox);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(campaignIndex, Constants.ELK_CAMPAIGN_INDEX, Constants.ELK_CAMPAIGN_TYPE,userMailBox.getId().toString());
	}

	@Override
	public Long getMailBoxCountByUserIdAndOrgLocationid(Long userId, Long orgLocationId, String type) throws Exception {
		return userMailBoxDao.getMailBoxCountByUserIdAndOrgLocationid(userId, orgLocationId, type);
	}
	
}
