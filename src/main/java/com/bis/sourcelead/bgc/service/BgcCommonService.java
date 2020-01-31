package com.bis.sourcelead.bgc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.dto.BgcStatusDTO;
import com.bis.sourcelead.bgc.dto.BgcTemplateDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.entity.BgcComponent;
import com.bis.sourcelead.bgc.entity.BgcStatus;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.Response;
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
	
	List<CandidateEducation> getBgcCandidateEducationSummaryByCandidateId(Long candidateId)throws Exception;
	
	List<CandidateEmployers> getBgcCandidateEmploymentSummary(String dateFormat, Long candidateId)throws Exception;
	
	BgcCandidateDetails saveEditBgcCandidate(BgcCandidateDetailsDTO bgcCandidateDetailsDTO)throws Exception;

	void exportBgcToPdfOrExcel(BgcCandidateDetailsDTO bgcCandidateDetailsDTO, ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList,
			OrgLocations orgLocations, HttpServletResponse response);

	List<BgcStatus> getBgcStatusListByOrgLocationId(Long orgLocationId);

	List<BgcComponent> getBgcComponentListByOrgLocationId(Long orgLocationId);

	List<BgcTemplateDTO> getBgcOrgcTemplateListByOrgLocationId(Long orgLocationId);

	String saveOrUpdateBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO,  User usr, OrgLocations orglocations);

	String deleteBgcStatusListById(Long statusId);

	ResponseList<BgcStatusDTO> getBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO, String searchValue,
			OrgLocations orgLocations);

	BgcStatusDTO getBgcStatusDetailsById(Long statusId);
}
