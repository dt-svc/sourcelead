package com.bis.sourcelead.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.CandidateEducation;

@Service
public interface CandidateEducationService {

	/**
	 * This method is used to save candidate education
	 * 
	 * @param candidateEducation
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception;

	/**
	 * This method is used to Update candidate certifications
	 * 
	 * @param candidateEducation
	 * @return CandidateEducation
	 * @throws Exception
	 */
	public CandidateEducation updateCandidateEducation(CandidateEducation candidateEducation) throws Exception;

	/**
	 * This method is used to Delete candidate certifications
	 * 
	 * @param id
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean deleteCandidateEducation(Long id) throws Exception;

	/**
	 * This method is used to get all candidate certifications
	 * 
	 * @return List<CandidateCertifications>
	 * @throws Exception
	 */
	public List<CandidateEducation> getAllCandidateEducation() throws Exception;

}
