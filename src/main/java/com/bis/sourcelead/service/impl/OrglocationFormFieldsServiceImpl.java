package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrglocationFormFieldsDao;
import com.bis.sourcelead.dao.OrglocationFormSubFieldsDao;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.OrglocationFormSubFieldsDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationFormFieldsService;

@Service
public class OrglocationFormFieldsServiceImpl implements OrglocationFormFieldsService {

	@Autowired
	private OrglocationFormFieldsDao orglocationFormFieldsDao;
	
	@Autowired
	private OrglocationFormSubFieldsDao orglocationFormSubFieldsDao;
	
	private OrglocationFormFields getOrglocationFormFieldsById(Long id) throws Exception {
		return orglocationFormFieldsDao.getOrglocationFormFieldsById(id);
	}

	
	@Override
	public OrglocationFormFields saveNewCustomFormFields(OrglocationFormFieldsDTO orgLocFormFieldsDTO, OrgLocations orgLocation,Org org,User loginUser) throws Exception {
		OrglocationFormFields savedOrGLocFormField = null;
		if(orgLocFormFieldsDTO.getId() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getId().toString())) {
			savedOrGLocFormField = getOrglocationFormFieldsById(orgLocFormFieldsDTO.getId());
		}else {
			OrglocationFormFields orgLocFormFields = new OrglocationFormFields();
		
		if(orgLocFormFieldsDTO.getSectionId() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getSectionId().toString())) {
			SourceleadFormSections sourceleadFormSections = new SourceleadFormSections();
			sourceleadFormSections.setId(orgLocFormFieldsDTO.getSectionId());
			orgLocFormFields.setSourceleadFormSections(sourceleadFormSections );
		}
		orgLocFormFields.setOrgLocations(orgLocation);
		orgLocFormFields.setOrg(org);
		if(orgLocFormFieldsDTO.getLabelName() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getLabelName())) {
			orgLocFormFields.setLabelName(orgLocFormFieldsDTO.getLabelName());
		}
		orgLocFormFields.setLabelInfo(orgLocFormFieldsDTO.getLabelinfo());
		orgLocFormFields.setFieldName(orgLocFormFieldsDTO.getLabelName().toLowerCase().replaceAll("\\s",""));
		orgLocFormFields.setFieldType(orgLocFormFieldsDTO.getFieldType());
		orgLocFormFields.setFieldDesc(orgLocFormFieldsDTO.getFieldDescription());
		orgLocFormFields.setPlaceHolder(orgLocFormFieldsDTO.getPlaceHolder());
		
		if(orgLocFormFieldsDTO.getSlDependedntField() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getSlDependedntField().toString())) {
			SourceleadFormFields sourceLeadDependantField = new SourceleadFormFields();
			sourceLeadDependantField.setId(orgLocFormFieldsDTO.getSlDependedntField());
			orgLocFormFields.setSourceLeadDependantField(sourceLeadDependantField);
		}
		if(orgLocFormFieldsDTO.getCustomDependentField() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getCustomDependentField().toString())) {
			OrglocationFormFields customDependantField = new OrglocationFormFields();
			customDependantField.setId(orgLocFormFieldsDTO.getCustomDependentField());
			orgLocFormFields.setCustomDependantField(customDependantField);
		}
		
		if(orgLocFormFieldsDTO.getMinDate() != null && StringUtils.isNotBlank(orgLocFormFieldsDTO.getMinDate())) {
			orgLocFormFields.setMinDate(orgLocFormFieldsDTO.getMinDate());
		}
		if(orgLocFormFieldsDTO.getMaxDate() != null && StringUtils.isNotBlank(orgLocFormFieldsDTO.getMaxDate())) {
			orgLocFormFields.setMaxDate(orgLocFormFieldsDTO.getMaxDate());
		}
		
		orgLocFormFields.setStatus(1);
		
		orgLocFormFields.setCreatedBy(loginUser);
		orgLocFormFields.setCreatedDate(new Date());
		orgLocFormFields.setUpdatedBy(loginUser);
		orgLocFormFields.setUpdatedDate(new Date());
		
		savedOrGLocFormField =  orglocationFormFieldsDao.saveOrglocationFormField(orgLocFormFields);
		if(orgLocFormFieldsDTO.getOptionData() != null && !orgLocFormFieldsDTO.getOptionData().isEmpty()) {
			for(OrglocationFormSubFieldsDTO optionData:orgLocFormFieldsDTO.getOptionData()) {
				if(optionData != null && StringUtils.isNotBlank(optionData.getOption())) {
				OrglocationFormSubFields subFields = new OrglocationFormSubFields();
				subFields.setFormField(savedOrGLocFormField);
				subFields.setLabelName(optionData.getOption());
				subFields.setStatus(1);
				orglocationFormSubFieldsDao.saveOrglocationFormSubField(subFields);
				}
			}
			
		}
		if(savedOrGLocFormField != null) {
			return savedOrGLocFormField;
		}else {
			return null;
		}
		}
		return savedOrGLocFormField;
	  }

	@Override
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId,Long orgLocation) {
		return orglocationFormFieldsDao.getAllCustomFormFieldsBySectionIdandOrgLocationId(sectionId,orgLocation);
	}


	@Override
	public String deleteCustomFieldById(Long customID) {
		return orglocationFormFieldsDao.deleteCustomFieldById(customID);
	}
	
	


}
