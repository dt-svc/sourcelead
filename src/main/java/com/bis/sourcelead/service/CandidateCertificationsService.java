package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.CandidateCertifications;

/**
 * @author Shivayogi
 * @Date 25th August 2016
 */
public interface CandidateCertificationsService {

	/**
	 * This method is used to save candidate certifications
	 * @param candidateCertifications
	 * @return CandidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications storeCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception;

	/**
	 * This method is used to update candidate certifications
	 * @param candidateCertifications
	 * @return CandidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications updateCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception;

	/**
	 * This method is used to delete candidate certifications
	 * @param id
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean deleteCandidateCertifications(Long id) throws Exception;

	/**
	 * This method is used to get all candidate certifications
	 * @return List<CandidateCertifications>
	 * @throws Exception
	 */
	public List<CandidateCertifications> getAllCandidateCertifications() throws Exception;

}
