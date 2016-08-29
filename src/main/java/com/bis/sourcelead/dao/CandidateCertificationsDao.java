package com.bis.sourcelead.dao;

import org.json.JSONObject;

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
	 * @param jsonObject 
	 * @return candidateCertifications
	 * @throws Exception
	 */
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonObject)throws Exception;

	}
