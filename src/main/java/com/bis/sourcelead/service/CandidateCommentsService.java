package com.bis.sourcelead.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Venkat Kesav
 *
 */
public interface CandidateCommentsService {
 
	/**
	 * @param jsonObj
	 * @param user
	 * @param candidate
	 * @param orgLocation
	 * @param multiFile
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public CandidateComments storeCandidateComments(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocation) throws JSONException,Exception;
	
}
