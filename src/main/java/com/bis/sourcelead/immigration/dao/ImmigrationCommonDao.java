package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonDao {

	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);
	
	Long savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
			CandidateResumes candidateResume, List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceEntityLst) throws Exception;

	Immigration getImmigrationViewList(String immigrationId);

	String getPetitionTypeById(Long petitionType, String petitionTypeConstant);

	CandidateLcaI94Summary getCandidateLCADetails(Long id, String yes);
	
	
}
