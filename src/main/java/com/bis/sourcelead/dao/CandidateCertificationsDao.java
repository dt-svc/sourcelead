package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateCertifications;

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
	 * @param candidateCertifications
	 * @return CandidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications storeCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception;

	/**
	 * This method is used to Update candidate certifications
	 * 
	 * @param candidateCertifications
	 * @return CandidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications updateCandidateCertifications(CandidateCertifications candidateCertifications)
			throws Exception;

	/**
	 * This method is used to Delete candidate certifications
	 * 
	 * @param id
	 * @return Boolean
	 * @throws Exception
	 */
	public Boolean deleteCandidateCertifications(Long id) throws Exception;

	/**
	 * This method is used to get all candidate certifications
	 * 
	 * @return List<CandidateCertifications>
	 * @throws Exception
	 */
	public List<CandidateCertifications> getAllCandidateCertifications() throws Exception;

}
