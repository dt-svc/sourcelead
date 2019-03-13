package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ClientCustomFieldDataDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.ClientCustomFieldData;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ClientCustomFieldDataDaoImpl implements ClientCustomFieldDataDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	
	@Override
	@Transactional
	public ClientCustomFieldData saveClientCustomFieldData(ClientCustomFieldData clientCustomFields) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		try {
		if(clientCustomFields != null) {
			session.saveOrUpdate(clientCustomFields);
		}
		}catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ClientCustomFieldData"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return clientCustomFields;
	}


	@Override
	@Transactional
	public List<ClientCustomFieldData> getClientCustomFieldDataByUserId(Long orgLocation,List<CustomFormFieldDTO> formFieldId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from ClientCustomFieldData clientData left join fetch clientData.orglocationsFormFields formField left join fetch clientData.org org where org.id =:orgId and formField.id IN(:formFieldId)";
		List<ClientCustomFieldData> clientFieldData  = session.createQuery(query).setLong("orgId", orgLocation).setParameterList("formFieldId", formIds).list();
		return clientFieldData;
	}


	@Override
	@Transactional
	public Map<Long, String[]> getClientAndVendorCustomFieldDataByClientOrgIdAndClientLocId(Long clientVendorOrgId,List<CustomFormFieldDTO> formFieldId,Long orgId) {
		Session session = sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldId) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from ClientCustomFieldData clientVendorData left join fetch clientVendorData.orglocationsFormFields formField left join fetch formField.org org left join fetch clientVendorData.org clientVendorOrg where clientVendorOrg.id =:clientVendorOrgId and formField.id IN(:formFieldId) and org.id =:orgId";
		List<ClientCustomFieldData> userFieldData  = session.createQuery(query).setLong("clientVendorOrgId", clientVendorOrgId).setLong("orgId", orgId).setParameterList("formFieldId", formIds).list();
		Map<Long,String[]> userCustomFieldDataMap = new HashMap<Long,String[]>();
		if(userFieldData != null && !userFieldData.isEmpty()) {
			for(ClientCustomFieldData userData:userFieldData) {
				String userDataArray[] =  new String[4];
				userDataArray[0] = userData.getOrglocationsFormFields().getLabelName();
				userDataArray[1] = userData.getCustomFormFieldData();
				userDataArray[2] = userData.getId().toString();
				userCustomFieldDataMap.put(userData.getOrglocationsFormFields().getId(), userDataArray);
			}
		}
		
		return userCustomFieldDataMap;
	}


	@Override
	@Transactional
	public ClientCustomFieldData getClientCustomFieldDataById(Long entityId) {
		Session session = sessionFactory.getCurrentSession();
		ClientCustomFieldData clientCustomFieldData = null;
		try {
			 clientCustomFieldData = (ClientCustomFieldData) session.get(ClientCustomFieldData.class, entityId);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clientCustomFieldData;
	}

}
