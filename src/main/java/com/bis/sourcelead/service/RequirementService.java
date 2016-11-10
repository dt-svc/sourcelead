package com.bis.sourcelead.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;

/**
 * @author Prasad K
 *
*/
public interface RequirementService {
	
	 
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception;
 
	public Requirement addRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,Long  userId) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId) throws Exception;
	
	public void  approverEmailScenario(User user,User approverUser,Requirement requirement, StringBuilder location) throws Exception;

	public void  approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location) throws Exception;
	
	public void acceptEmailScenario(User loginUser, User approverUser, Requirement requirement) throws Exception;
	
	public void mailRequirementtoManager(User loginUser, User intiatorUser, Requirement requirement,StringBuilder location) throws Exception;
	
	public void AssignToTeamMembersEmail(User loginUser, User managerUser, Requirement requirement,StringBuilder location) throws Exception;

	void rejectedEmailScenario(User loginUser, User initiatorUser, Requirement requirement,
			StringBuilder location,String rejectedReason) throws Exception;

	void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc, OrgLocations loginOrgLocation,String releasetype,StringBuilder location) throws Exception;

	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;

	public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement)throws Exception;

	public List<Requirement> getRequirementListById(Long requirementId) throws Exception;
	
	Integer validateJobId(Long id) throws Exception;

	public File getExportRequirementFile(List<Requirement> requirementList, String orgName, String exportType)throws Exception;




	
}
