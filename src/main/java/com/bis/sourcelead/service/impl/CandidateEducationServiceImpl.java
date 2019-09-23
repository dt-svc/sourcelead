package com.bis.sourcelead.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEducationHistory;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;

@Service
public class CandidateEducationServiceImpl implements CandidateEducationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceImpl.class);
	
	
	@Autowired
	private CandidateEducationDao candidateEducationDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
 
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	
	/**
	 * @author ajith kumar
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save/store the candidate education details.
	 * this method is called at onload when click on timesheet left menu
	 * 
	 * @param jsonObject This is the first paramter to storeCandidateEducation method
	 *  
	 * @return CandidateEducation 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public CandidateEducation storeCandidateEducation(CandidateEducationDTO candidateEducationDTO,Long candidateId, Long orgLocationId,User user) throws Exception {
		
		CandidateEducation candidateEducation = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if(candidateId !=null){
        	if(StringUtils.isNotBlank(candidateEducationDTO.getCandidateEduId())){
	       		 if(candidateEducationDTO.getCandidateEduId() !=null){
	       			//candidateEducation.setId(Long.valueOf(jsonObject.getString("candidateEduId")));
	       			candidateEducation = candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, Long.valueOf(candidateEducationDTO.getCandidateEduId()));
	       	     }
       	    }else{
       	    	candidateEducation =new CandidateEducation();
       	     }
		
		candidate.setId(candidateId);
		candidateEducation.setCandidateId(candidate);
		
		orgLocations.setId(orgLocationId);
		candidateEducation.setOrglocationId(orgLocations);
		OrgLocations locations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = locations.getLocFullFormDateFormatter();
		if(StringUtils.isNotBlank(candidateEducationDTO.getName())){
			if(candidateEducation != null && candidateEducation.getName() != null ){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidateEducation.getId() == null){
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setName(candidateEducationDTO.getName());
			//String educationName = jsonObject.getString("name");
			String[] educations = candidateEducationDTO.getName().split(",");
			for(String edu : educations){
				candidateEducation.setName(edu);
			}
		}
		if(StringUtils.isNotBlank(candidateEducationDTO.getSpecialization())){
			if(candidateEducation != null && candidateEducation.getSpecialization() != null ){
				activitySummary.append("Specialization").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getSpecialization()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getSpecialization()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setSpecialization(candidateEducationDTO.getSpecialization());
		}else{
			candidateEducation.setSpecialization("");
		}
		if(candidateEducationDTO.getStartDate() != null && candidateEducationDTO.getStartDate() != ""){
			try {
				
				//String pattern = "dd-MM-yyyy";
				//DateFormat format = LoginSessionController.DATEFORMAT;
                Date startDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),candidateEducationDTO.getStartDate());
                candidateEducation.setStartDate(startDate);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			candidateEducation.setStartDate(null);
		}
		if (candidateEducationDTO.getEndDate() != null && candidateEducationDTO.getEndDate() != "") {
			try {
				//String pattern = "dd-MM-yyyy";
				//DateFormat format = LoginSessionController.DATEFORMAT;
                Date validTo = commonUtil.orgLocationDateFormat(locations.getDateFormat(),candidateEducationDTO.getEndDate());
                candidateEducation.setEndDate(validTo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			candidateEducation.setEndDate(null);
		}
		
		
		if(StringUtils.isNotBlank(candidateEducationDTO.getGrade())){
			if(candidateEducation != null && candidateEducation.getGrade() != null){
				activitySummary.append("Grade").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getGrade()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getGrade()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setGrade(candidateEducationDTO.getGrade());
		}else{
			candidateEducation.setGrade("");
		}
		if(StringUtils.isNotBlank(candidateEducationDTO.getGradeType())){
			if(candidateEducation != null && candidateEducation.getGradeType() != null){
				activitySummary.append("Grade Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getGradeType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getGradeType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setGradeType(candidateEducationDTO.getGradeType());
		}else{
			candidateEducation.setGradeType("");
		}
		
		// this for only immigration purpose
		
		if(candidateEducationDTO.getFileInfo() != null){
			FileInfo fileinfo = candidateEducationDTO.getFileInfo().get(0);
			candidateEducation.setEducationFileUrl(fileinfo.getFileURL());
			candidateEducation.setEducationFileName(fileinfo.getFileName());
			candidateEducation.setEducationFileContentType(fileinfo.getContentType());
			FileUploadUtil.moveFile(candidateEducationDTO.getStaticPath(), fileinfo, "candidateDocuments");
			
		}
		
		if(StringUtils.isNotBlank(candidateEducationDTO.getInstitute())){
			if(candidateEducation != null && candidateEducation.getInstitute() != null){
				activitySummary.append("Institute").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getInstitute()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getInstitute()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setInstitute(candidateEducationDTO.getInstitute());
		}else{
			candidateEducation.setInstitute("");
		}
		if(StringUtils.isNotBlank(candidateEducationDTO.getAddress())){
			if(candidateEducation != null && candidateEducation.getAddress() != null ){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEducationDTO.getAddress()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setAddress(candidateEducationDTO.getAddress());
		}else{
			candidateEducation.setAddress("");
		}
		candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateEducation.setCreatedBy(user.getUserCode());
		candidateEducation.setCreatedDate(commonUtil.currentDate());
		candidateEducation.setUpdatedBy(user.getUserCode());
		candidateEducation.setUpdatedDate(commonUtil.currentDate());
		}
		candidateEducation = candidateEducationDao.storeCandidateEducation(candidateEducation);
		
		candidateActivityCommonUtil.storeCandidateEducationHistory(candidateEducation, user);
		
		candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_EDUCATION_UPDATED, activitySummary.toString(), "CANDIDATE");
		
		/*
		 * updating Candidate Education DB data in existing candidateindex
		 */
		saveOrUpdateCandidateElkDocumentWithEducationDetails(candidateEducation.getCandidateId(),orgLocationId);
		
		return candidateEducation;
	}
	
	
	/**
	 * This Method is used to get candidate education details 
	 * @author Ajith Matta
	 * 
	 * @param candidateId
	 * 
	 * @return CandidateEducation list
	 * 
	 * @throws Exception
	 */

	@Override
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception{
      
		List<CandidateEducation> edulist = candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		OrgLocations locations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = locations.getLocFullFormDateFormatter();
		for(CandidateEducation education :edulist){
			if(education.getStartDate() != null){
			education.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),education.getStartDate()));
			}
			if(education.getEndDate() != null){
			education.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),education.getEndDate()));
			}
		}
		return edulist;
	}
	
	@Override
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, Long orgLocationId, String searchVal) throws Exception{
      
		List<CandidateEducation> edulist = candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(candidateId, orgLocationId, searchVal);
		OrgLocations locations = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = locations.getLocFullFormDateFormatter();
		for(CandidateEducation education :edulist){
			if(education.getStartDate() != null){
			education.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),education.getStartDate()));
			}
			if(education.getEndDate() != null){
			education.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),education.getEndDate()));
			}
		}
		return edulist;
	}

	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,String userCode) throws Exception {
		    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
		    if(StringUtils.isNotBlank(candidateDTO.getCandidateEducation())){
		  	      String[] educations = candidateDTO.getCandidateEducation().split(",");
		  	    Set<String> courses = new HashSet();
		    	  for (String edu : educations) {
		    		  CandidateEducation ce = null;
		    		  edu = edu.replace("\"", "");
		    		  String eduTrim = edu.trim();
		    		  if(null != candidate.getId()){
		    		  List<CandidateEducation> eduList= candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidate.getId(),orgLocations.getId());
		    			if(courses.size() != eduList.size()){
		    				for(CandidateEducation candidateEducation :eduList){
			    				String DbEdu = candidateEducation.getName().replace("\"", "");
			    				courses.add(DbEdu.trim());
			    			}
		    			}
	    			  if(courses.add(eduTrim)){
	    				  ce = new  CandidateEducation();
	    				  if(null !=ce){
			    			  ce.setName(edu);
				    		  ce.setCandidateId(candidate);
				    		  ce.setOrglocationId(orgLocations);
				    		  ce.setStatus(Constants.CANDIDATE_ACTIVE);
				    		  candidateEducationList.add(ce);
			    		  }
	    			  }
		    		  }else{
		    			  ce = new  CandidateEducation();
		    			  if(null !=ce){
			    			  ce.setName(edu);
				    		  ce.setCandidateId(candidate);
				    		  ce.setOrglocationId(orgLocations);
				    		  ce.setStatus(Constants.CANDIDATE_ACTIVE);
				    		  candidateEducationList.add(ce);
			    		  }
		    		  }
		    		  
		    		
		    	  }
		    	  
		    }
			return  candidateEducationList;
	  }

	
	/**
	 * @author Sammeta David Raju
	 * This Method is for removing  CandidateEducation By candidateId and candidateEducationId
	 * 
	 * @param candidateId
	 * 
	 * @param  candidateEducationId
	 * 
	 * @return {@link CandidateEducation}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEducation removeCandidateEducationBycandidateIdandcandidateEducationId(Long candidateId,
			Long candidateEducationId, User user, OrgLocations orgLocations) throws Exception {
			CandidateEducation can = candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		     can =  candidateEducationDao.storeCandidateEducation(can);
		     StringBuffer activitySummary = new StringBuffer();
		     activitySummary.append("Education").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(can.getName()).append(Constants.NEW_VALUE_TAG_END);
			 candidateActivityCommonUtil.storeCandidateEducationHistory(can, user);
			 candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can.getCandidateId(), "", Constants.CANDIDATE_DELETED_EDUCATION, activitySummary.toString(), "CANDIDATE");
				
		        /*
				 * updating Candidate Skills DB data in existing candidateindex
				 */
				saveOrUpdateCandidateElkDocumentWithEducationDetails(can.getCandidateId(),can.getOrglocationId().getId());
		     
		     return can;
	}
	
	/**
	 * @author Prasad Salina
	 * @param candidateId
	 * @throws Exception 
	 */
	@Override
	public CandidateEducation getCandidateEducationByCandidateId(Long candidateId) throws Exception {
		return candidateEducationDao.getCandidateEducationByCandidateId(candidateId);
	}
	

	/**
	 * @author Sammeta David Raju
	 * This Method is for getting  CandidateEducation By candidateId and candidateEducationId
	 * 
	 * @param candidateId
	 * 
	 * @param  candidateEducationId
	 * 
	 * @return {@link CandidateEducation}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,
			Long candidateEducationId) throws Exception {
		  return candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
	}
	
	@Override
	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception {
		return candidateEducationDao.validateEducationName(candidateId, candidateEducation);
	}

	
	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithEducationDetails(Candidate candidate,Long locationId) throws Exception {
		if(null != candidate && null != candidate.getId()){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidate.getId());
			ArrayList<CandidateEducation> educationList =(ArrayList<CandidateEducation>) getCandidateEducationListByCandidateIdAndOrgLocationID(candidate.getId(),locationId);
			if(null != educationList && !educationList.isEmpty()){
				ArrayList<String> eduList = new ArrayList<>();
				for(CandidateEducation edu :educationList){
					eduList.add(edu.getName());
					
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
				candidateIndex.setEducation(eduList);
			}
			
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		}
	}


	@Override
	public List<String> getCandidateEducationNamesByCandidateId(Long candidateId) throws Exception {
		return candidateEducationDao.getCandidateEducationNamesByCandidateId(candidateId);
	}

	
	@Override
	public String removeCandidateEucationrDocById(Long candidateEmployerId) throws Exception {
		CandidateEducation candidateEducation = candidateEducationDao.getCandidateEducationById(candidateEmployerId);
		if(candidateEducation != null){
		try {
			String rootPath = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			File file = new File(rootPath + candidateEducation.getEducationFileUrl());
			
			if (file.delete()) {
				logger.debug(file.getName() + " is deleted!");
			} else {
				logger.error(file.getName() + " is deleted!");
			}
			
			
				candidateEducation.setEducationFileName(null);
				candidateEducation.setEducationFileUrl(null);
				candidateEducation.setEducationFileContentType(null);
		
			candidateEducationDao.storeCandidateEducation(candidateEducation);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
		return "success";
	}


	@Override
	public Integer getCandidateActiveEducationsCountByCandidateId(Long candidateId) throws Exception {
		return candidateEducationDao.getCandidateActiveEducationsCountByCandidateId(candidateId);
	}


	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetailsList(CandidateDTO candidateDTO,
			OrgLocations orgLocations, Candidate candidate, User user) throws Exception {
		
	    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
	    if(StringUtils.isNotBlank(candidateDTO.getCandidateEducation())){
	  	      String[] educations = candidateDTO.getCandidateEducation().split(",");
	  	      if(educations.length > 0) {
	  	    	  for(int i=0; i<educations.length; i++) {
	  	    		  CandidateEducation ce = new CandidateEducation();
	  	    		  ce.setName(educations[i].trim());
		    		  ce.setCandidateId(candidate);
		    		  ce.setOrglocationId(orgLocations);
		    		  ce.setStatus(Constants.CANDIDATE_ACTIVE);
		    		  
		    		HashSet<CandidateEducationHistory> candidateEducationHistorySet = new HashSet<CandidateEducationHistory>();
		    		CandidateEducationHistory candidateEducationHistory = new CandidateEducationHistory();
		  			candidateEducationHistory.setCandidateEducation(ce);
		  			candidateEducationHistory.setName(educations[i].trim());
		  			candidateEducationHistory.setCreatedUser(user);
		  			candidateEducationHistory.setCreatedBy(user.getUserCode());
		  			candidateEducationHistory.setCreatedDate(commonUtil.currentDate());
		  			candidateEducationHistorySet.add(candidateEducationHistory);
		  			ce.setCandidateEducationHistoryList(candidateEducationHistorySet);
		  			candidateEducationList.add(ce);
	  	    	  }
	  	      }
	    }
		return  candidateEducationList;
	}
}