package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.CandidateCustomFieldData;

public interface CandidateCustomFieldDataService {
	
	public Map<Long, String[]> getCandidateCustomFieldDataMapByLocationId(Long id, List<CustomFormFieldDTO> formFieldId,Long candidateId) throws Exception;

	public CandidateCustomFieldData getCandidateCustomFieldDataById(Long entityId);

	public CandidateCustomFieldData savejobCustomFieldData(CandidateCustomFieldData candidateCustomFields);
	
	public Map<Long, String[]> getCandidateGlobalCustomFieldDataMapByLocationId(Long id, List<CustomFormFieldDTO> formFieldId,Long candidateId) throws Exception;
	
	List<CandidateCustomFieldData> getCandidateCustomFieldDataByCandidateGlobalId(Long candidateGlobalId) throws Exception;
}
