package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.CandidateCustomFieldData;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;

@Service
public class CandidateCustomFieldDataServiceImpl implements CandidateCustomFieldDataService {

	@Autowired
	private CandidateCustomFieldDataDao candidateCustomFieldDataDao;
	
	@Override
	public Map<Long, String[]> getCandidateCustomFieldDataMapByLocationId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long candidateId) throws Exception {
		return candidateCustomFieldDataDao.getCandidateCustomFieldDataMapByLocationId(orgId,formFieldId,candidateId);
	}

	@Override
	public CandidateCustomFieldData getCandidateCustomFieldDataById(Long entityId) {
		return candidateCustomFieldDataDao.getCandidateCustomFieldDataById(entityId);
	}

	@Override
	public CandidateCustomFieldData savejobCustomFieldData(CandidateCustomFieldData candidateCustomFields) {
		return candidateCustomFieldDataDao.savejobCustomFieldData(candidateCustomFields);
	}

	@Override
	public Map<Long, String[]> getCandidateGlobalCustomFieldDataMapByLocationId(Long id,
			List<CustomFormFieldDTO> formFieldId, Long candidateId) throws Exception {
		return candidateCustomFieldDataDao.getCandidateGlobalCustomFieldDataMapByLocationId(id, formFieldId, candidateId);
	}

}
