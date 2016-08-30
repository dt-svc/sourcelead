package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	

	@Override
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonObject ,User user, OrgLocations orgLocationId,Long candidateId) throws Exception {
		CandidateCertifications candidatecertificate  = new CandidateCertifications();
		 if(candidateId!= null){
			 
		if (jsonObject.has("name")) {
			candidatecertificate.setName(jsonObject.getString("name"));
		}
		if (jsonObject.has("provider")) {
			candidatecertificate.setProvider(jsonObject.getString("provider"));
		}
		if (jsonObject.has("score")) {
			candidatecertificate.setScore(jsonObject.getString("score"));
		}
		if (jsonObject.has("description")) {
			candidatecertificate.setScoreType(jsonObject.getString("description"));
		}
			if (jsonObject.has("validFrom") && !(jsonObject.getString("validFrom").isEmpty())) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					candidatecertificate.setValidFrom(sdf.parse(jsonObject.getString("validFrom")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (jsonObject.has("validTo") && !(jsonObject.getString("validTo").isEmpty())) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					candidatecertificate.setValidTo(sdf.parse(jsonObject.getString("validTo")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
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
	
	
	
	
		@Override
		public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId,
				Long orgLocationId) throws Exception {
			 return candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId);
	}

}










