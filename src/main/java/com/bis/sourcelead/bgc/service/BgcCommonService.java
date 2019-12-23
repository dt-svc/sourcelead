package com.bis.sourcelead.bgc.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface BgcCommonService {

	BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetailsDTO bgcCandidateDetailsDto,User user, OrgLocations orgLocations );

	ResponseList<BgcCandidateDetailsDTO>  getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO, String searchValue,
			OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal);

	String deleteBgcCandidateDetailsbyUuiId(String uuid);
	
	public String getBgcGeneratedId(OrgLocations orgLocations);
	
	BgcCandidateDetailsDTO getBgcCandidateDetailsByBgcUuid(String bgcUuid)throws Exception;
	
	List<CandidateEducation> getBgcCandidateEducationSummaryByCandidateUuid(String candidateUuid)throws Exception;
	
	List<CandidateEmployers> getBgcCandidateEmploymentSummary(String dateFormat, String candidateUuid)throws Exception;
	
	BgcCandidateDetails saveEditBgcCandidate(BgcCandidateDetailsDTO bgcCandidateDetailsDTO)throws Exception;
}
