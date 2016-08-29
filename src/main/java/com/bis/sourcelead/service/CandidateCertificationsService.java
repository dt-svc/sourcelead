package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateCertifications;

/**
 * @author Shivayogi
 * @Date 25th August 2016
 */
public interface CandidateCertificationsService {

	/**
	 * This method is used to save candidate certifications
	 * @param JSONObject
	 * @return CandidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonobject)throws Exception;
	
	public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId ,Long orgLocationId) throws Exception;
			
}