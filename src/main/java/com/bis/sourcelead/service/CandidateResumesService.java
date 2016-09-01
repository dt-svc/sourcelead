package com.bis.sourcelead.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateResumesService {
	
	
	/**
	 * @author Venkat Kesav
	 * @param jsonObj
	 * @param user
	 * @param candidate
	 * @param orgLocation
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocation, MultipartFile multiFile) throws Exception;
	
	/**
	 * @author Venkat Kesav
	 * @param orgLocations
	 * @param userCode
	 * @param candidate
	 * @param multiFile
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public HashSet<CandidateResumes> getCandidateResumesDetails(OrgLocations orgLocations,String userCode,Candidate candidate,MultipartFile multiFile)throws JSONException, IOException;
	
	/**
	 * @author Venkat Kesav
	 * @param candidateId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, String status) throws Exception;
	
	/**
	 * @author Venkat Kesav
	 * @param candidateResumes
	 * @return
	 * @throws Exception
	 */
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception;
	
	/**
	 * @author Venkat Kesav
	 * @param resumeId
	 * @return
	 * @throws Exception
	 */
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception;
}
