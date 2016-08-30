package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author Shivayogi
 * @date 25th August 2016
 *
 */
public interface CandidateCertificationsDao {

	/**
	 * This method is used to save candidate certifications
	 * 
	 * @param jsonObject 
	 * @return candidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications storeCandidateCertifications(CandidateCertifications candidateCertifications)throws Exception;

	
	public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId ,Long orgLocationId) throws Exception;
	}
