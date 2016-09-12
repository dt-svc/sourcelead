package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCertificationsService;
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
			 if(candidateId!= null){
				 if(jsonObject.has("candidatecertificateID")){
		       		 if(jsonObject.getString("candidatecertificateID")!=null && !jsonObject.getString("candidatecertificateID").isEmpty()){
		       			candidatecertificate.setId(Long.valueOf(jsonObject.getString("candidatecertificateID")));
		       	     }
	       	    }
			if (jsonObject.has("name")) {
				candidatecertificate.setName(jsonObject.getString("name"));
			}
			if (jsonObject.has("provider")) {
				candidatecertificate.setProvider(jsonObject.getString("provider"));
			}
			if (jsonObject.has("score")) {
				candidatecertificate.setScore(jsonObject.getString("score"));
			}
			if (jsonObject.has("scoreType")) {
				candidatecertificate.setScoreType(jsonObject.getString("scoreType"));
			}
			if (jsonObject.has("description")) {
				candidatecertificate.setDescription(jsonObject.getString("description"));
			}
				if (jsonObject.has("validFrom") && !(jsonObject.getString("validFrom").isEmpty())) {
					try {
						
						String pattern = "yyyy-MM-dd";
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        
                        Date validFrom = format.parse(jsonObject.getString("validFrom"));
                        candidatecertificate.setValidFrom(validFrom);
						
						/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
						if(jsonObject.getString("validFrom") != null && jsonObject.getString("validFrom") != ""){
						candidatecertificate.setValidFrom(sdf.parse(jsonObject.getString("validFrom")));
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (jsonObject.has("validTo") && !(jsonObject.getString("validTo").isEmpty())) {
					try {
						String pattern = "yyyy-MM-dd";
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        
                        Date validTo = format.parse(jsonObject.getString("validTo"));
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
				Candidate can = new Candidate();
				can.setId(candidateId);
				candidatecertificate.setCandidateId(can);
			
		}
			logger.debug("In CandidateCertificationsServiceImpl class save method");
			return candidateCertificationsDao.storeCandidateCertifications(candidatecertificate);
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
			 List<CandidateCertifications> CandidateCertificationsList = candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId);
			 for (CandidateCertifications candidateCertifications :CandidateCertificationsList ){
				 if(candidateCertifications.getValidFrom() != null){
					 candidateCertifications.setCandidatevalidFromDateToString(Constants.DF_DMY.format(candidateCertifications.getValidFrom())); 
				 }
				 if(candidateCertifications.getValidTo() != null){
					 candidateCertifications.setCandidatevalidToDateToString(Constants.DF_DMY.format(candidateCertifications.getValidTo())); 
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
			Long candidateId, Long candidateCertificateId) throws Exception {

	     CandidateCertifications can = candidateCertificationsDao.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificateId);
	     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
	     return candidateCertificationsDao.storeCandidateCertifications(can);

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
		

}