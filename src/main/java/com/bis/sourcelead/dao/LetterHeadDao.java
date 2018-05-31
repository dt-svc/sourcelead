package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;

public interface LetterHeadDao {

	LetterHead storeLetterHeadData(LetterHead letterHead);

	ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO, Long orgLocationId);

	List<LetterHead> getLetterheadBylocationId(Long orgLocationId);

	LetterHead getLetterheadByUUID(String UUID);

	LetterHead getLetterheadById(Long letterHeadId);

	LetterHead updateLetterhead(LetterHead letter);

	Boolean validateLetterHeadName(String letterHeadName, OrgLocations orgLocation);

	LetterHead validateNameInEdit(String letterHeadName, Long letterHeadId, Long orgLocationId);

}
