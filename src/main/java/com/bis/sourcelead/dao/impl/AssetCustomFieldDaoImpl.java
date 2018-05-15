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

import com.bis.sourcelead.dao.AssetCustomFieldDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.entity.AssetCustomFieldData;

@Repository
public class AssetCustomFieldDaoImpl implements AssetCustomFieldDao {
	private static final Logger logger = LoggerFactory.getLogger(UserCustomFieldDataDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public AssetCustomFieldData saveAssetCustomFieldData(AssetCustomFieldData assetCustomFields) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assetCustomFields != null) {
				session.saveOrUpdate(assetCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return assetCustomFields;
	}


	@Override
	@Transactional
	public Map<Long, String[]> getAssetCustomFieldDataByAssetId(Long orgId, List<CustomFormFieldDTO> formFieldList,Long assetId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> formIds = new ArrayList<>();
		for(CustomFormFieldDTO form:formFieldList) {
			formIds.add(form.getCustomFormFieldId());
		}
		String query = "from AssetCustomFieldData assetData left join fetch assetData.orglocationsFormFields formField left join fetch formField.org org left join fetch assetData.assets ast where ast.id =:assetId and formField.id IN(:formFieldList) and org.id =:orgId";
		List<AssetCustomFieldData> assetFieldData  = session.createQuery(query).setLong("assetId", assetId).setLong("orgId", orgId).setParameterList("formFieldList", formIds).list();
		Map<Long,String[]> assetCustomFieldDataMap = new HashMap<Long,String[]>();
		if(assetFieldData != null && !assetFieldData.isEmpty()) {
			for(AssetCustomFieldData assetData:assetFieldData) {
				String assetDataArray[] =  new String[4];
				assetDataArray[0] = assetData.getOrglocationsFormFields().getLabelName();
				assetDataArray[1] = assetData.getCustomFormFieldData();
				assetDataArray[2] = assetData.getId().toString();
				assetCustomFieldDataMap.put(assetData.getOrglocationsFormFields().getId(), assetDataArray);
			}
		}
		return assetCustomFieldDataMap;
	}
		

	@Override
	@Transactional
	public AssetCustomFieldData getAssetCustomFieldDataById(Long entityId) {
		Session session = sessionFactory.getCurrentSession();
		AssetCustomFieldData assetCustomFields = null;
		try {
			assetCustomFields = (AssetCustomFieldData) session.get(AssetCustomFieldData.class, entityId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return assetCustomFields;
	}
}


