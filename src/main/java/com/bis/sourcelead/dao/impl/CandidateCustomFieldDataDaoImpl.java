package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.CandidateCustomFieldData;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.JobCustomFieldData;

@Repository
public class CandidateCustomFieldDataDaoImpl implements CandidateCustomFieldDataDao {
	private static final Logger logger = LoggerFactory.getLogger(CandidateCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<Long,String[]> getCandidateCustomFieldDataMapByLocationId(Long orgId,List<CustomFormFieldDTO> formFieldId,Long candidateId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from CandidateCustomFieldData candidateData left join fetch candidateData.orglocationsFormFields formField left join fetch formField.org org left join fetch candidateData.candidate can where org.id =:orgId and can.id =:candidateId and formField.id IN(:formFieldId)";
		List<CandidateCustomFieldData> candidateCustomFieldData  = session.createQuery(query).setLong("orgId", orgId).setLong("candidateId", candidateId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> candidateCustomFieldDataMap = new HashMap<Long,String[]>();
		if(candidateCustomFieldData != null && !candidateCustomFieldData.isEmpty()) {
			for(CandidateCustomFieldData candidateData:candidateCustomFieldData) {
				String candidateDataArray[] =  new String[4];
				candidateDataArray[0] = candidateData.getOrglocationsFormFields().getFieldName();
				candidateDataArray[1] = candidateData.getCustomFormFieldData();
				candidateDataArray[2] = candidateData.getId().toString();
				candidateCustomFieldDataMap.put(candidateData.getOrglocationsFormFields().getId(), candidateDataArray);
			}
		}
		return candidateCustomFieldDataMap;
	}

	@Override
	@Transactional
	public CandidateCustomFieldData getCandidateCustomFieldDataById(Long entityId) {
		Session session = sessionFactory.getCurrentSession();
		CandidateCustomFieldData candidateCustomFields = null;
		try {
			candidateCustomFields = (CandidateCustomFieldData) session.get(CandidateCustomFieldData.class, entityId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return candidateCustomFields;
	}

	@Override
	@Transactional
	public CandidateCustomFieldData savejobCustomFieldData(CandidateCustomFieldData candidateCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (candidateCustomFields != null) {
				session.saveOrUpdate(candidateCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return candidateCustomFields;
	}

	@Override
	@Transactional
	public Map<Long, String[]> getCandidateGlobalCustomFieldDataMapByLocationId(Long orgId,
			List<CustomFormFieldDTO> formFieldId, Long candidateId) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from CandidateCustomFieldData candidateData left join fetch candidateData.orglocationsFormFields formField left join fetch formField.org org left join fetch candidateData.candidateGlobalId can where org.id =:orgId and can.id =:candidateId and formField.id IN(:formFieldId)";
		List<CandidateCustomFieldData> candidateCustomFieldData  = session.createQuery(query).setLong("orgId", orgId).setLong("candidateId", candidateId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> candidateCustomFieldDataMap = new HashMap<Long,String[]>();
		if(candidateCustomFieldData != null && !candidateCustomFieldData.isEmpty()) {
			for(CandidateCustomFieldData candidateData:candidateCustomFieldData) {
				String candidateDataArray[] =  new String[4];
				candidateDataArray[0] = candidateData.getOrglocationsFormFields().getFieldName();
				candidateDataArray[1] = candidateData.getCustomFormFieldData();
				candidateDataArray[2] = candidateData.getId().toString();
				candidateCustomFieldDataMap.put(candidateData.getOrglocationsFormFields().getId(), candidateDataArray);
			}
		}
		return candidateCustomFieldDataMap;
	}

	@Override
	@Transactional
	public List<CandidateCustomFieldData> getCandidateCustomFieldDataByCandidateGlobalId(Long candidateGlobalId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CandidateCustomFieldData cfd left join fetch cfd.candidateGlobalId where cfd.candidateGlobalId.id =:candidateGlobalId ";
		List<CandidateCustomFieldData> candidateCustomFieldDataList = session.createQuery(query).setLong("candidateGlobalId", candidateGlobalId).list();
		return candidateCustomFieldDataList;
	}
}
