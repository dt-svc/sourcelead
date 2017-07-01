package com.bis.sourcelead.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;

/**
 * @author shivayogi
 * @Date 25-August-2016
 */
@Service
public class CandidateCertificationsServiceImpl implements CandidateCertificationsService {

	
	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsServiceImpl.class);
	@Autowired
	private CandidateCertificationsDao candidateCertificationsDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;

	
	/**
	 * 
	 * @author Shivayogi
	 * This Method is for Storing candidateCertifications
	 * 
	 * @param jsonObject
	 * 
	 * @param user
	 * 
	 * @param orgLocationId
	 * 
	 * @param candidateId
	 * 
	 * @return {@link CandidateCertifications}
	 * 
	 * @exception NullPointerException
	 * 
	 * 
	 */
	 @Override
	 public CandidateCertifications storeCandidateCertifications(JSONObject jsonObject ,User user, OrgLocations orgLocationId,Long candidateId) throws Exception {
			CandidateCertifications candidatecertificate  = new CandidateCertifications();
			Candidate can = new Candidate();
			SimpleDateFormat simpleDateFormat = orgLocationId.getLocFullFormDateFormatter();
			StringBuffer activitySummary = new StringBuffer();
			 if(candidateId!= null){
				 if(jsonObject.has("candidatecertificateID")){
		       		 if(jsonObject.getString("candidatecertificateID")!=null && !jsonObject.getString("candidatecertificateID").isEmpty()){
		       			//candidatecertificate.setId(Long.valueOf(jsonObject.getString("candidatecertificateID")));
		       			candidatecertificate = candidateCertificationsDao.getCandidateCertificateBycandidateIdandCertificateId(candidateId, Long.valueOf(jsonObject.getString("candidatecertificateID")));
		       	     }
	       	    }
			if (jsonObject.has("name")) {
				if(candidatecertificate != null && candidatecertificate.getName() != null && candidatecertificate.getName() != "" && !candidatecertificate.getName().equalsIgnoreCase(jsonObject.getString("name"))){
					activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}else if(candidatecertificate != null && candidatecertificate.getId() == null){
					activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END);
				}
				candidatecertificate.setName(jsonObject.getString("name"));
			}
			if (jsonObject.has("provider")) {
				if(candidatecertificate != null && candidatecertificate.getProvider() != null && candidatecertificate.getProvider() != "" && !candidatecertificate.getProvider().equalsIgnoreCase(jsonObject.getString("provider"))){
					activitySummary.append("Provider").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getProvider()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("provider")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setProvider(jsonObject.getString("provider"));
			}
			if (jsonObject.has("score")) {
				if(candidatecertificate != null && candidatecertificate.getScore() != null && candidatecertificate.getScore() != "" && !candidatecertificate.getScore().equalsIgnoreCase(jsonObject.getString("score"))){
					activitySummary.append("Score").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getScore()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("score")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setScore(jsonObject.getString("score"));
			}
			if (jsonObject.has("scoreType")) {
				if(candidatecertificate != null && candidatecertificate.getScoreType() != null && candidatecertificate.getScoreType() != "" && !candidatecertificate.getScoreType().equalsIgnoreCase(jsonObject.getString("scoreType"))){
					activitySummary.append("Score Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getScoreType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("scoreType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setScoreType(jsonObject.getString("scoreType"));
			}
			if (jsonObject.has("description")) {
				if(candidatecertificate != null && candidatecertificate.getDescription() != null && candidatecertificate.getDescription() != "" && !candidatecertificate.getDescription().equalsIgnoreCase(jsonObject.getString("description"))){
					activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("description")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setDescription(jsonObject.getString("description"));
			}
				if (jsonObject.has("validFrom") && !(jsonObject.getString("validFrom").isEmpty()) && !" ".equals(jsonObject.get("validFrom"))) {
					try {
						
						//String pattern = "dd-MM-yyyy";
                        //DateFormat format = LoginSessionController.DATEFORMAT;
                        
                        Date validFrom = simpleDateFormat.parse(jsonObject.getString("validFrom"));
                        candidatecertificate.setValidFrom(validFrom);
						
						/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
						if(jsonObject.getString("validFrom") != null && jsonObject.getString("validFrom") != ""){
						candidatecertificate.setValidFrom(sdf.parse(jsonObject.getString("validFrom")));
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (jsonObject.has("validTo") && !(jsonObject.getString("validTo").isEmpty()) && !" ".equals(jsonObject.get("validTo"))) {
					try {
						//String pattern = "dd-MM-yyyy";
						//DateFormat format = LoginSessionController.DATEFORMAT;
                        
                        Date validTo = simpleDateFormat.parse(jsonObject.getString("validTo"));
                        candidatecertificate.setValidTo(validTo);
						
						/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
						if(jsonObject.getString("validTo") != null && jsonObject.getString("validTo") != ""){
						candidatecertificate.setValidTo(sdf.parse(jsonObject.getString("validTo")));
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				candidatecertificate.setStatus(Constants.CANDIDATE_ACTIVE);
				candidatecertificate.setCreatedBy(user.getUserCode());
				candidatecertificate.setCreatedDate(commonUtil.currentDate());
				candidatecertificate.setUpdatedBy(user.getUserCode());
				candidatecertificate.setUpdatedDate(commonUtil.currentDate());
				candidatecertificate.setOrgLocationId(orgLocationId);
				can.setId(candidateId);
				candidatecertificate.setCandidateId(can);
			
		}
			logger.debug("In CandidateCertificationsServiceImpl class save method");
			candidatecertificate = candidateCertificationsDao.storeCandidateCertifications(candidatecertificate);
			
			candidateActivityCommonUtil.storeCandidateCertificationsHistory(candidatecertificate, user);
			
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocationId, can, "", Constants.CANDIDATE_CERTIFICATION_UPDATED, activitySummary.toString(), "CANDIDATE");
			
			/*
			 * updating Candidate Certification DB data in existing candidateindex
			 */
			saveOrUpdateCandidateElkDocumentWithCertificationDetails(candidateId, orgLocationId.getId());
			
			return candidatecertificate;
		}
		
	
	
	/**
	 * 
	 * @author root Shivayogi
	 * 
	 * This method is for getting CandidateCertificationList By CandidateId And OrgLocationID
	 * 
	 * @param candidateId
	 * 
	 * @param orgLocationId
	 * 
	 * @return List<CandidateCertifications>
	 * 
	 * @exception NullPointerException
	 * 
	 */
	 @Override
	 public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId,
				Long orgLocationId) throws Exception {
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		 SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter(); 
			 List<CandidateCertifications> CandidateCertificationsList = candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId);
			 for (CandidateCertifications candidateCertifications :CandidateCertificationsList ){
				 if(candidateCertifications.getValidFrom() != null){
					 candidateCertifications.setCandidatevalidFromDateToString(simpleDateFormat.format(candidateCertifications.getValidFrom())); 
				 }
				 if(candidateCertifications.getValidTo() != null){
					 candidateCertifications.setCandidatevalidToDateToString(simpleDateFormat.format(candidateCertifications.getValidTo())); 
				 }
				    }
			 return CandidateCertificationsList;
	}
	
	
	/**
	 * @author Sammeta David Raju
	 * 
	 * This Method is for removing  the  Candidate Certificate By candidateId and candidateCertificateId
	 *   
	 * @param candidateId
	 * 
	 * @param candidateCertificateId
	 * 
	 * @exception  Exception
	 * @return CandidateCertifications
	 */
	@Override
	public CandidateCertifications removeCandidateCertificateBycandidateIdandcandidateCertificateId(
			Long candidateId, Long candidateCertificateId, User user, OrgLocations orgLocation) throws Exception {

	     CandidateCertifications can = candidateCertificationsDao.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificateId);
	     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	     can = candidateCertificationsDao.storeCandidateCertifications(can);
	     StringBuffer activitySummary = new StringBuffer();
	     activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(can.getName()).append(Constants.NEW_VALUE_TAG_END);
		 candidateActivityCommonUtil.storeCandidateCertificationsHistory(can, user);
		 candidateActivityCommonUtil.storeCandidateActivity(user, orgLocation, can.getCandidateId(), "", Constants.CANDIDATE_DELETED_CERTIFICATION, activitySummary.toString(), "CANDIDATE");

	     /*
		 * updating Candidate Employer DB data in existing candidateindex
		 */
	     saveOrUpdateCandidateElkDocumentWithCertificationDetails(candidateId,can.getOrgLocationId().getId());
	     
	     return can;

	}



	/**
	 * @author Sammeta David Raju
	 * 
	 * This Method is for get CandidateCertificate By candidateId and CertificateId
	 *   
	 * @param candidateId
	 *  
	 * @param candidateCertificateId
	 * 
	 * @exception  Exception
	 * @return CandidateCertifications
	 */
	@Override
	public CandidateCertifications getCandidateCertificateBycandidateIdandCertificateId(Long candidateId,
			Long candidateCertificateId) throws Exception {

		  return candidateCertificationsDao.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificateId);
	}
	
	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithCertificationDetails(Long candidateId,Long locationId) throws Exception {
		if(null != candidateId){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidateId);
			ArrayList<CandidateCertifications> certificationList =(ArrayList<CandidateCertifications>) getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,locationId);
			if(null != certificationList && !certificationList.isEmpty()){
				ArrayList<String> certList = new ArrayList<>();
				for(CandidateCertifications cert :certificationList){
					certList.add(cert.getName());
				}
				candidateIndex.setCertifications(certList);
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateId.toString());
		}
	}
		

}