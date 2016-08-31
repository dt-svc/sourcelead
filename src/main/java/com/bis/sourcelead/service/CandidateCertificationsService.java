package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

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
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonObject, User user, OrgLocations orgLocationId,Long candidateId)throws Exception;
	
	public List<CandidateCertifications> getCandidateCertificationListByCandidateIdAndOrgLocationID(Long candidateId ,Long orgLocationId) throws Exception;
	
	
	public CandidateCertifications removeCandidateCertificateBycandidateIdandcandidateCertificateId(Long candidateId,Long candidateCertificateId)  throws Exception;

	public CandidateCertifications getCandidateCertificateBycandidateIdandCertificateId(Long candidateId,Long candidateCertificateId)  throws Exception;
	
}