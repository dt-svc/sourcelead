package com.bis.sourcelead.service.impl;

import java.text.DecimalFormat;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.MasterCandidateDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.MasterCandidateService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class MasterCandidateServiceImpl implements MasterCandidateService{

	@Autowired
	MasterCandidateDao masterCandidateDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private CandidateDao candidateDao;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public MasterCandidate saveMasterCandidate(MasterCandidate masterCandidate) throws Exception
	{
		return masterCandidateDao.saveMasterCandidate(masterCandidate);
	}

	@Override
	public MasterCandidate getMasterCandidateByInteligence(JSONObject jsonObj) throws Exception
	{
		String email = null;
		String phoneNumber = null;
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			email = jsonObj.getString("email");
		}
		
		if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
			phoneNumber = jsonObj.getString("mobilePhone");
		}
		
		if(jsonObj.has("phoneNumber") && !isNullOrEmpty(jsonObj.getString("phoneNumber"))){
			phoneNumber = jsonObj.getString("phoneNumber");
		}
		
		if(jsonObj.has("userEmail") && !isNullOrEmpty(jsonObj.getString("userEmail"))){
			email = jsonObj.getString("userEmail");
		}
		
		
		// Tihs for add applicant
		if(jsonObj.has("EmailId") && !isNullOrEmpty(jsonObj.getString("EmailId"))){
			email = jsonObj.getString("EmailId");
		}
		
		if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
			phoneNumber = jsonObj.getString("PhoneNumber");
		}
		
		if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
			phoneNumber = jsonObj.getString("AlternateNumber");
		}
		
		MasterCandidate masterCandidate = masterCandidateDao.getMasterCandidateByEmailOrPhoneNumber(email, phoneNumber);
		
		return masterCandidate;
	}

	@Override
	public MasterCandidate storeMasterCandidateByJsonObject(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception
	{
		MasterCandidate masterCandidate = new MasterCandidate();
		
		if(jsonObj.has("firstName")){
			masterCandidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName")){
			masterCandidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email")){
			masterCandidate.setEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("phoneNumber") && !isNullOrEmpty(jsonObj.getString("phoneNumber"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("phoneNumber"));
		}
		
		if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("mobilePhone"));
		}
		
		if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("mobilePhone"));
		}
		
		if(jsonObj.has("alternateMobilePhone") && !isNullOrEmpty(jsonObj.getString("alternateMobilePhone"))){
			masterCandidate.setAlternatePhoneNumber(jsonObj.getString("alternateMobilePhone"));
		}
		
		
		// Tihs for add applicant
		
		if(jsonObj.has("FirstName")){
			masterCandidate.setFirstName(jsonObj.getString("FirstName"));
		}
		
		if(jsonObj.has("LastName")){
			masterCandidate.setLastName(jsonObj.getString("LastName"));
		}
		
		if(jsonObj.has("EmailId")){
			masterCandidate.setEmail(jsonObj.getString("EmailId"));
		}
		
		if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("PhoneNumber"));
		}
		
		if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("AlternateNumber"));
		}
		
		
		
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		if(user != null){
			masterCandidate.setCreatedBy(user.getUserCode());
			masterCandidate.setUpdatedBy(user.getUserCode());
		}
		else{
			masterCandidate.setCreatedBy(null);
			masterCandidate.setUpdatedBy(null);
		}
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		masterCandidate.setGeneratedId(getCandidateId(orgLocations));
		masterCandidateDao.saveMasterCandidate(masterCandidate);
		
		return masterCandidate;
	}

	private String getCandidateId(OrgLocations orgLocations) {
		String companyShortCode = null;
    	Long number = 1L;
		String candidateId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.CANDIDATE_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for(int i=0;i< generatedUsreId.length();i++){
			char ch = generatedUsreId.charAt(i);
			if(Character.isAlphabetic(ch)){
				generatedIdcount++;
			}
		}
		// in dao to count from next character
		generatedIdcount = generatedIdcount+1;
		 Long checkNumber = candidateDao.getCandidateGeneratedIdByOrgLocationId(orgLocations.getId(),generatedIdcount);
		if(checkNumber == null){
			candidateId = number.toString();
		}else{
		Long canId = checkNumber+1;
		candidateId = canId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");                
        String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
        StringBuffer sb = new StringBuffer();
        if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
        	 companyShortCode = orgLocations.getGeneratedUserId();
        }
		return sb.append(companyShortCode).append(Constants.CANDIDATE_ENTITY).append(convetredCandidateId).toString();
	}
	@Override
	public MasterCandidate storeMasterCandidateByCandidateDTO(CandidateDTO candidateDTO, User user) throws Exception {
		MasterCandidate masterCandidate = new MasterCandidate();
		
		if(StringUtils.isNotBlank(candidateDTO.getFirstName())){
			masterCandidate.setFirstName(candidateDTO.getFirstName());
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getLastName())){
			masterCandidate.setLastName(candidateDTO.getLastName());
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getEmail())){
			masterCandidate.setEmail(candidateDTO.getEmail());
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getPhoneNumber())){
			masterCandidate.setPhoneNumber(candidateDTO.getPhoneNumber());
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getMobilePhone())){
			masterCandidate.setPhoneNumber(candidateDTO.getMobilePhone());
		}
		
		/*if(jsonObj.has("mobilePhone") && !isNullOrEmpty(jsonObj.getString("mobilePhone"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("mobilePhone"));
		}*/
		
		if(StringUtils.isNotBlank(candidateDTO.getAlternateMobilePhone())){
			masterCandidate.setAlternatePhoneNumber(candidateDTO.getAlternateMobilePhone());
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getEmailId())){
			masterCandidate.setEmail(candidateDTO.getEmailId());
		}
		
		// Tihs for add applicant
		
		/*if(jsonObj.has("FirstName")){
			masterCandidate.setFirstName(jsonObj.getString("FirstName"));
		}
		
		if(jsonObj.has("LastName")){
			masterCandidate.setLastName(jsonObj.getString("LastName"));
		}
		
		
		if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("PhoneNumber"));
		}
		
		if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
			masterCandidate.setPhoneNumber(jsonObj.getString("AlternateNumber"));
		}*/
		
		
		
		masterCandidate.setStatus(Constants.RECORD_ACTIVE);
		masterCandidate.setCreatedBy(user.getUserCode());
		masterCandidate.setCreatedDate(commonUtil.currentDate());
		masterCandidate.setUpdatedBy(user.getUserCode());
		masterCandidate.setUpdatedDate(commonUtil.currentDate());
		masterCandidateDao.saveMasterCandidate(masterCandidate);
		
		return masterCandidate;
	}

	@Override
	public MasterCandidate getMasterCandidateByInteligenceCandidateDTO(CandidateDTO candidateDTO) throws Exception {
		String email = null;
		String phoneNumber = null;
		MasterCandidate masterCandidate = null;
		if(StringUtils.isNotBlank(candidateDTO.getEmail())){
			email = candidateDTO.getEmail();
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getMobilePhone())){
			phoneNumber = candidateDTO.getMobilePhone();
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getPhoneNumber())){
			phoneNumber = candidateDTO.getPhoneNumber();
		}
		
		if(StringUtils.isNotBlank(candidateDTO.getUserEmail())){
			email = candidateDTO.getUserEmail();
		}
		
		
		// Tihs for add applicant
		if(StringUtils.isNotBlank(candidateDTO.getEmailId())){
			email = candidateDTO.getEmailId();
		}
		/*
		if(jsonObj.has("PhoneNumber") && !isNullOrEmpty(jsonObj.getString("PhoneNumber"))){
			phoneNumber = jsonObj.getString("PhoneNumber");
		}
		
		if(jsonObj.has("AlternateNumber") && !isNullOrEmpty(jsonObj.getString("AlternateNumber"))){
			phoneNumber = jsonObj.getString("AlternateNumber");
		}*/
		
		Long masterCandidateId = masterCandidateDao.getMasterCandidateIdByEmail(email);
		
		if(masterCandidateId != null) {
			masterCandidate = new MasterCandidate();
			masterCandidate.setId(masterCandidateId);
		}
		
		return masterCandidate;
	}

	@Override
	public Long getMasterCandidateIdByEmail(String email) throws Exception {
		return masterCandidateDao.getMasterCandidateIdByEmail(email);
	}
}
