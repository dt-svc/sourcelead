package com.bis.sourcelead.bgc.dao;

import java.util.Map;

import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface BgcCommonDao {
	BgcCandidateDetails saveBgcCandidateDetails(BgcCandidateDetails BgcCandidateDetails);

	ResponseList<BgcCandidateDetailsDTO> saveBgcCandidateDetails(BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			String searchValue, OrgLocations orgLocations, Map<String, Long[]> filterVal, String orgTypeVal);

	String deleteBgcCandidateDetailsbyUuiId(String uuid);
	
	BgcCandidateDetails getBgcCandidateDetailsByBgcUuid(String bgcUuid)throws Exception;
}
