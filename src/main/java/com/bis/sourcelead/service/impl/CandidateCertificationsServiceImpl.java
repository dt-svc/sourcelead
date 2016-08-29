package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.service.CandidateCertificationsService;

/**
 * @author shivayogi
 * @Date 25-August-2016
 */
@Service
public class CandidateCertificationsServiceImpl implements CandidateCertificationsService {

	
	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsServiceImpl.class);
	@Autowired
	private CandidateCertificationsDao candidateCertificationsDao;

	CandidateCertifications candidatecertificate;

	@Override
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonobject) throws Exception {

		if (jsonobject.has("name")) {
			candidatecertificate.setName(jsonobject.getString("name"));
		}
		if (jsonobject.has("provider")) {
			candidatecertificate.setProvider(jsonobject.getString("provider"));
		}
		if (jsonobject.has("SCORE")) {
			candidatecertificate.setScore(jsonobject.getString("SCORE"));
		}
		if (jsonobject.has("scoreType")) {
			candidatecertificate.setScoreType(jsonobject.getString("scoreType"));
		}
		if (jsonobject.has("validFrom")) {
			candidatecertificate.setValidFrom((Date) jsonobject.get("validFrom"));
		}
		if (jsonobject.has("validTo")) {
			candidatecertificate.setValidTo((Date) jsonobject.get("validTo"));
		}
		if (jsonobject.has("CREATED_DATE")) {
			candidatecertificate.setCreatedDate((Date) jsonobject.get("CREATED_DATE"));
		}
		if (jsonobject.has("CREATED_BY")) {
			candidatecertificate.setCreatedBy(jsonobject.getString("CREATED_BY"));
		}
		if (jsonobject.has("UPDATED_DATE")) {
			candidatecertificate.setUpdatedDate((Date) jsonobject.get("UPDATED_DATE"));
		}
		if (jsonobject.has("UPDATED_BY")) {
			candidatecertificate.setUpdatedBy(jsonobject.getString("UPDATED_BY"));
		}

		
		logger.debug("In CandidateCertificationsServiceImpl class save method");
		return candidateCertificationsDao.storeCandidateCertifications(jsonobject);
	}
		@Override
		public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId,
				Long orgLocationId) throws Exception {
			 return candidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId);
	}

}










