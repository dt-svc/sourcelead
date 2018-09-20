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

import com.bis.sourcelead.dao.NewContractCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.JobCustomFieldData;
import com.bis.sourcelead.entity.NewContractCustomFieldData;
import com.bis.sourcelead.entity.NewContractCustomFieldData;

@Repository
public class NewContractCustomFieldDataDaoImpl implements NewContractCustomFieldDataDao {

	private static final Logger logger = LoggerFactory.getLogger(NewContractCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public NewContractCustomFieldData saveNewContractCustomFieldData(NewContractCustomFieldData contractCustomFields) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(contractCustomFields);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return contractCustomFields;
	}

	@Override
	@Transactional
	public Map<Long, String[]> getNewContractCustomFieldDataByContractId(Long orgId, List<CustomFormFieldDTO> formFieldId,Long contractId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from NewContractCustomFieldData contractData left join fetch contractData.orglocationsFormFields formField left join fetch formField.org org left join fetch contractData.contracts cont where cont.id =:contractId and formField.id IN(:formFieldId) and org.id =:orgId";
		List<NewContractCustomFieldData> contractFieldData  = session.createQuery(query).setLong("contractId", contractId).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> newContracCustomFieldDataMap = new HashMap<Long,String[]>();
		if(contractFieldData != null && !contractFieldData.isEmpty()) {
			for(NewContractCustomFieldData contractData:contractFieldData) {
				String contractDataArray[] =  new String[4];
				contractDataArray[0] = contractData.getOrglocationsFormFields().getLabelName();
				contractDataArray[1] = contractData.getCustomFormFieldData();
				contractDataArray[2] = contractData.getId().toString();
				newContracCustomFieldDataMap.put(contractData.getOrglocationsFormFields().getId(), contractDataArray);
			}
		}
		return newContracCustomFieldDataMap;
	}

	@Override
	@Transactional
	public NewContractCustomFieldData getNewContractCustomFieldDataById(Long contractId) {
		Session session = sessionFactory.getCurrentSession();
		NewContractCustomFieldData contractsCustomFields = null;
		try {
			contractsCustomFields = (NewContractCustomFieldData) session.get(NewContractCustomFieldData.class, contractId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return contractsCustomFields;
	}
}
