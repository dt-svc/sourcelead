package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 *
*/
public interface CandidateService {
	
	/**
	 * @param jsonObj
	 * @param orgLocations
	 * @param user
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception;
	
	/**
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate addCandidate(Candidate candidate) throws Exception;

	
	/**
	 * @param candidateId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	/**
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId) throws Exception;
	
	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	/**
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Candidate findByCandidateEmail(String email) throws Exception;
	
	/**
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate saveOrUpdateCandidate(Candidate candidate) throws Exception;
	
}
