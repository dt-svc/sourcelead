package com.bis.sourcelead.bgc.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.dto.BgcComponentDTO;
import com.bis.sourcelead.bgc.dto.BgcStatusDTO;
import com.bis.sourcelead.bgc.dto.BgcTemplateDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.entity.BgcComponent;
import com.bis.sourcelead.bgc.entity.BgcStatus;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface BgcCommonDao {
	BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetails BgcCandidateDetails);

	ResponseList<BgcCandidateDetailsDTO> getBgcCadidateListByOrgLocationId(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal);

	String deleteBgcCandidateDetailsbyUuiId(String uuid);
	
	Long getBgcIdByOrgLocationId(OrgLocations orgLocations, Integer generatedIdCount);
	
	BgcCandidateDetails getBgcCandidateDetailsByBgcUuid(String bgcUuid)throws Exception;
	
	List<CandidateEducation> getBgcCandidateEducationSummaryListByCandidateId(Long candidateId)throws Exception;
	
	List<CandidateEmployers> getBgcCandidateEmploymentSummaryListByCandidateId(Long candidateId)throws Exception;

	List<BgcStatus> getBgcStatusListByOrgLocationId(Long orgLocationId);

	ResponseList<BgcComponentDTO> getBgcComponentListByOrgLocationId(BgcComponentDTO bgcComponentDTO, String searchValue,
			OrgLocations orgLocations);

	ResponseList<BgcTemplateDTO> getBgcOrgcTemplateListByOrgLocationId(BgcTemplateDTO bgcTemplateDTO,
			String searchValue, OrgLocations orgLocations);

	String saveOrUpdateBgcStatusListByOrgLocationId(BgcStatus bgcStatus);

	String deleteBgcStatusListByOrgLocationId(Long statusId);

	ResponseList<BgcStatusDTO> getBgcStatusListByOrgLocationId(BgcStatusDTO bgcStatusDTO, String searchValue,
			OrgLocations orgLocations);

	BgcStatusDTO getBgcStatusDetailsById(Long statusId);

	String saveOrUpdateBgcComponentListByOrgLocationId(BgcComponent bgcComponent);

	BgcComponentDTO getBgcComponentDetailsById(Long componentId);

	String deleteBgcComponentById(Long componentId);
}
