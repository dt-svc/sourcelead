package com.bis.sourcelead.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;

public interface LetterHeadService {

LetterHead storeLetterHeadData(LetterHeadDTO letterHeadDTO, OrgLocations orgLocation) throws Exception;

ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO, Long orgLocationId);

List<LetterHead> getLetterheadBylocationId(Long orgLocationId);

LetterHead getLetterheadByUUID(String UUID);

LetterHead getLetterheadById(Long letterHeadId);

void letterHeadData(LetterHead letterHead, HttpServletResponse response) throws Exception;

Boolean validateLetterHeadName(String letterHeadName, OrgLocations orgLocation);

LetterHead validateNameInEdit(String letterHeadName, Long letterHeadId, Long orgLocationId);

}
