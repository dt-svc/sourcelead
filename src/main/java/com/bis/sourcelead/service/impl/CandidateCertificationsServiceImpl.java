package com.bis.sourcelead.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

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

	 @Autowired
	 CandidateDao candidateDao;
	 
	 @Autowired
	 CandidateIndexHelper candidateIndexHelper;

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
	 public CandidateCertifications storeCandidateCertifications(CandidateCertificationDTO candidateCertificationDTO ,User user, OrgLocations orgLocationId,Long candidateId) throws Exception {
			CandidateCertifications candidatecertificate  = new CandidateCertifications();
			Candidate can = new Candidate();
			CandidateGlobal candidateGlobal = new CandidateGlobal();
			StringBuffer activitySummary = new StringBuffer();
			 if(candidateId!= null){
				 if(StringUtils.isNotBlank(candidateCertificationDTO.getCandidatecertificateID())){
					 if(StringUtils.isNotBlank(candidateCertificationDTO.getCandidateType()) && candidateCertificationDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
						 candidatecertificate = candidateCertificationsDao.getCandidateGlobalCertificateBycandidateIdandCertificateId(candidateId, Long.valueOf(candidateCertificationDTO.getCandidatecertificateID()));
					 }else {
						 candidatecertificate = candidateCertificationsDao.getCandidateCertificateBycandidateIdandCertificateId(candidateId, Long.valueOf(candidateCertificationDTO.getCandidatecertificateID()));
					 }
		       			
	       	    }
			if (StringUtils.isNotBlank(candidateCertificationDTO.getName())) {
				if(candidatecertificate != null && candidatecertificate.getName() != null){
					activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}else if(candidatecertificate != null && candidatecertificate.getId() == null){
					activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getName()).append(Constants.NEW_VALUE_TAG_END);
				}
				candidatecertificate.setName(candidateCertificationDTO.getName());
			}
			if (StringUtils.isNotBlank(candidateCertificationDTO.getProvider())) {
				if(candidatecertificate != null && candidatecertificate.getProvider() != null ){
					activitySummary.append("Provider").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getProvider()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getProvider()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setProvider(candidateCertificationDTO.getProvider());
			}
			if (StringUtils.isNotBlank(candidateCertificationDTO.getScore())) {
				if(candidatecertificate != null && candidatecertificate.getScore() != null){
					activitySummary.append("Score").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getScore()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getScore()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setScore(candidateCertificationDTO.getScore());
			}else{
				candidatecertificate.setScore("");
			}
			if (StringUtils.isNotBlank(candidateCertificationDTO.getScoreType())) {
				if(candidatecertificate != null && candidatecertificate.getScoreType() != null){
					activitySummary.append("Score Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getScoreType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getScoreType()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setScoreType(candidateCertificationDTO.getScoreType());
			}else{
				candidatecertificate.setScoreType("");
	
			}
			if (StringUtils.isNotBlank(candidateCertificationDTO.getDescription())) {
				if(candidatecertificate != null && candidatecertificate.getDescription() != null ){
					activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidatecertificate.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCertificationDTO.getDescription()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidatecertificate.setDescription(candidateCertificationDTO.getDescription());
			}else{
				candidatecertificate.setDescription("");

			}
				if (StringUtils.isNotBlank(candidateCertificationDTO.getValidFrom())) {
					try {
						
                        Date validFrom = commonUtil.orgLocationDateFormat(orgLocationId.getDateFormat(),candidateCertificationDTO.getValidFrom());
                        candidatecertificate.setValidFrom(validFrom);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					candidatecertificate.setValidFrom(null);
				}
				if (StringUtils.isNotBlank(candidateCertificationDTO.getValidTo())) {
					try {
                        
                        Date validTo = commonUtil.orgLocationDateFormat(orgLocationId.getDateFormat(),candidateCertificationDTO.getValidTo());
                        candidatecertificate.setValidTo(validTo);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					candidatecertificate.setValidTo(null);
				}
				
				 if(StringUtils.isNotBlank(candidateCertificationDTO.getCandidateType()) && candidateCertificationDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
					    candidateGlobal.setId(candidateId);
						candidatecertificate.setCandidateGlobalId(candidateGlobal);
				 }else {
					    can.setId(candidateId);
						candidatecertificate.setCandidateId(can);
				 }
				
				candidatecertificate.setStatus(Constants.CANDIDATE_ACTIVE);
				candidatecertificate.setCreatedBy(user.getUserCode());
				candidatecertificate.setCreatedDate(commonUtil.currentDate());
				candidatecertificate.setUpdatedBy(user.getUserCode());
				candidatecertificate.setUpdatedDate(commonUtil.currentDate());
				candidatecertificate.setOrgLocationId(orgLocationId);
			
			
		}
			logger.debug("In CandidateCertificationsServiceImpl class save method");
			candidatecertificate = candidateCertificationsDao.storeCandidateCertifications(candidatecertificate);
			
			 if(StringUtils.isNotBlank(candidateCertificationDTO.getCandidateType()) && candidateCertificationDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
				    candidateActivityCommonUtil.storeCandidateCertificationsHistory(candidatecertificate, user);
					candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocationId, candidateGlobal, "", Constants.CANDIDATE_CERTIFICATION_UPDATED, activitySummary.toString(), "CANDIDATE");
					
			 }else {
				    candidateActivityCommonUtil.storeCandidateCertificationsHistory(candidatecertificate, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocationId, can, "", Constants.CANDIDATE_CERTIFICATION_UPDATED, activitySummary.toString(), "CANDIDATE");
			 }
			
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
		 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter(); 
			 List<CandidateCertifications> CandidateCertificationsList = candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId);
			 for (CandidateCertifications candidateCertifications :CandidateCertificationsList ){
				 if(candidateCertifications.getValidFrom() != null){
					 candidateCertifications.setCandidatevalidFromDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateCertifications.getValidFrom())); 
				 }
				 if(candidateCertifications.getValidTo() != null){
					 candidateCertifications.setCandidatevalidToDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateCertifications.getValidTo())); 
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

			 Candidate candidate = candidateDao.getCandidateByCandidateId(candidateId);
		    CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
			ArrayList<CandidateCertifications> certificationList =(ArrayList<CandidateCertifications>) getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,locationId);
			if(null != certificationList && !certificationList.isEmpty()){
				ArrayList<String> certList = new ArrayList<>();
				for(CandidateCertifications cert :certificationList){
					certList.add(cert.getName());
				}
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
				candidateIndex.setCertifications(certList);
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateId.toString());
		}
	}



	@Override
	public List<CandidateCertifications> getCandidateGlobalCertificationListByCandidateIdAndOrgLocationID(
			Long candidateId, Long orgLocationId) throws Exception {
		return candidateCertificationsDao.getCandidateGlobalCertificationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}



	@Override
	public CandidateCertifications getCandidateGlobalCertificateBycandidateIdandCertificateId(Long candidateId,
			Long candidateCertificateId) throws Exception {
		return candidateCertificationsDao.getCandidateGlobalCertificateBycandidateIdandCertificateId(candidateId, candidateCertificateId);
	}



	@Override
	public CandidateCertifications removeCandidateGlobalCertificateBycandidateIdandcandidateCertificateId(
			Long candidateId, Long candidateCertificateId, User user, OrgLocations orgLocation) throws Exception {

	     CandidateCertifications can = candidateCertificationsDao.getCandidateGlobalCertificateBycandidateIdandCertificateId(candidateId, candidateCertificateId);
	     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	     can = candidateCertificationsDao.storeCandidateCertifications(can);
	     StringBuffer activitySummary = new StringBuffer();
	     activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(can.getName()).append(Constants.NEW_VALUE_TAG_END);
		 candidateActivityCommonUtil.storeCandidateCertificationsHistory(can, user);
		 candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocation, can.getCandidateGlobalId(), "", Constants.CANDIDATE_DELETED_CERTIFICATION, activitySummary.toString(), "CANDIDATE");
	     
	     return can;

	}
		

}