package com.bis.sourcelead.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;

import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.CandidateResumesDTO;
import com.bis.sourcelead.dto.FileInfo;
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
	 * @param jsonObj
	 * @param user
	 * @param candidate
	 * @param resumeId
	 * @param orgLocation
	 * @param fileURL
	 * @param fileName
	 * @param contentType
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public CandidateResumes storeCandidateResume(CandidateResumeDTO candidateResumeDTO, User user, Candidate candidate, String resumeId, OrgLocations orgLocation) throws JSONException,Exception;
	
	
	/**
	 * @param orgLocations
	 * @param userCode
	 * @param candidate
	 * @param multiFile
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public HashSet<CandidateResumes> getCandidateResumesDetails(OrgLocations orgLocations,String userCode,Candidate candidate,FileInfo fileInfo)throws Exception;
	 
	/**
	 * @param candidateId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, Integer status) throws Exception;
	
	
	/**
	 * @author Prasad Salina
	 * @param submissionResumeIds
	 * @return
	 * To get List of CandidateResumes by resumeIds
	 */
	public List<CandidateResumes> getCandidateResumesByIds(List<Long> submissionResumeIds);
	
	/**
	 * @param candidateResumes
	 * @return
	 * @throws Exception
	 */
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception;
	
	/**
	 * @param resumeId
	 * @return
	 * @throws Exception
	 */
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception;


	public CandidateResumes getCandidateLatesResumes(Long candidateId, int status) throws Exception;
	
	public void saveOrUpdateCandidateElkDocumentWithResumeDetails(Candidate candidate) throws Exception;
	
	public CandidateResumes getCandidateResumeByResumeUUID(String resumeUUID) throws Exception;
	
	
	Integer getCandidateResumeCountByResumeId(Long resumeId) throws Exception;
	
	Integer getCandidateResumeCountByCandidateId(Long candidateId) throws Exception;

	public CandidateResumes getCandidateResumesListByCandidateId(Long candidateId);
	
	public CandidateResumes getCandidateResumeByCandidateId(Long candidateId) throws Exception;
	
	Integer getCandidateActiveResumeCountByCandidateId(Long candidateId) throws Exception;
	
	public CandidateResumesDTO  getCandidateLatesResumeDetails(Long candidateId) throws Exception;
	
}
