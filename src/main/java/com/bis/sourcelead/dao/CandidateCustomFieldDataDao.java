package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.CandidateCustomFieldData;

public interface CandidateCustomFieldDataDao {

	Map<Long, String[]> getCandidateCustomFieldDataMapByLocationId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long candidateId);

	CandidateCustomFieldData getCandidateCustomFieldDataById(Long entityId);

	CandidateCustomFieldData savejobCustomFieldData(CandidateCustomFieldData candidateCustomFields);
	
	public Map<Long, String[]> getCandidateGlobalCustomFieldDataMapByLocationId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long candidateId) throws Exception;

}
