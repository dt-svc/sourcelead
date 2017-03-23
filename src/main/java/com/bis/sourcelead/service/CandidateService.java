package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange,String candidateType) throws Exception;
	
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
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception;
	
	/**
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate saveOrUpdateCandidate(Candidate candidate) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception;

	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId)throws Exception;

	public File getCandidateExcelFile(List<Candidate> candidatesList, String orgName)throws Exception;

	public File getCandidatePdf(List<Candidate> candidatesList, String orgName) throws Exception;
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId)throws Exception;
	
	public void saveOrUpdateCandidateElkDocument(Candidate candidate) throws Exception;
	
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId, String candidateStatus);
	
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId,Long orgLocationId)throws Exception;
	
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId,String type)throws Exception;
	
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId)throws Exception;
	
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId,String type,Long orgLocationId)throws Exception;

	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId,Integer datRange)throws Exception;

	List<Object> getAllUniversalSearchCandidateList(OrgLocations orgLocation, String searchValue) throws Exception;

	Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId,String candidateType) throws Exception;
	
	Candidate getCandidateByEmailAndRecruiterUserId(String email,Long recruiterUserId) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId,Long requirementId, String candidateType,String searchApplicant) throws Exception;
	
	public Candidate storeProfessionalParseCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile,Long candidateId) throws Exception;

	/*
	 * This Method is to  get the consolidated reports which candidates are created on current Date for individual  user 
	 */
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,User user) throws Exception;

	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long orgLocationId,String candidateType);
	
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	List<Candidate> getCandidateReports(Long orgLocationId,String candidateType,String filterDate, Date startDateUTC, Date endDateUTC);

}
