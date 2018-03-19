package com.bis.sourcelead.immigration.dao;

import java.util.List;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaI94Summary;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.ImmigrationTemplateDocs;
import com.bis.sourcelead.entity.ImmigrationTemplateForms;
import com.bis.sourcelead.entity.ImmigrationTemplateTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonDao {

	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);
	
	void savePetitionDetails(Immigration immigration, Candidate candidate, CandidateIdendities candidateIdendities,
			CandidateResumes candidateResume, List<CandidateLcaI94Summary> lcaSummaryLst, List<CandidateResidence> residenceEntityLst) throws Exception;
	
	
}
