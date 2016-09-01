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
	 * @author prasad.k
	 * @param jsonObj
	 * @param orgLocations
	 * @param user
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception;
	
	/**
	 * @author BIS105
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate addCandidate(Candidate candidate) throws Exception;

	/**
	 * @author David Raju Sammeta
	 * @param candidateId
	 * @param orgId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception;
	
	/**
	 * @author Venkat Kesav
	 * @param candidateId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	/**
	 * @author David Sammeta
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId) throws Exception;
	
	/**
	 * @author  prasad.k
	 * @param candidateId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	/**
	 * @author  prasad.k
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Candidate findByCandidateEmail(String email) throws Exception;
	
}
