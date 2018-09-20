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
	
	@Override
	public OrglocationFormFields getOrglocationFormFieldsById(Long id) throws Exception {
		return orglocationFormFieldsDao.getOrglocationFormFieldsById(id);
	}

	
	@Override
	public OrglocationFormFields saveNewCustomFormFields(OrglocationFormFieldsDTO orgLocFormFieldsDTO, OrgLocations orgLocation,Org org,User loginUser) throws Exception {
		OrglocationFormFields orgLocFormFields = null;
		if(orgLocFormFieldsDTO != null && orgLocFormFieldsDTO.getId() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getId().toString())) {
			orgLocFormFields = getOrglocationFormFieldsById(orgLocFormFieldsDTO.getId());
		}else {
			 orgLocFormFields = new OrglocationFormFields();
			 orgLocFormFields.setStatus(1);
			 orgLocFormFields.setCreatedBy(loginUser);
		  	 orgLocFormFields.setCreatedDate(new Date());
		}
		if(orgLocFormFieldsDTO.getSectionId() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getSectionId().toString())) {
			SourceleadFormSections sourceleadFormSections = new SourceleadFormSections();
			sourceleadFormSections.setId(orgLocFormFieldsDTO.getSectionId());
			orgLocFormFields.setSourceleadFormSections(sourceleadFormSections );
		}
		orgLocFormFields.setOrgLocations(orgLocation);
		orgLocFormFields.setOrg(org);
		if(orgLocFormFieldsDTO.getLabelName() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getLabelName())) {
			orgLocFormFields.setLabelName(StringUtils.capitalize(orgLocFormFieldsDTO.getLabelName()));
		}
		orgLocFormFields.setLabelInfo(orgLocFormFieldsDTO.getLabelinfo());
		if(orgLocFormFieldsDTO.getLabelName() != null && StringUtils.isNotEmpty(orgLocFormFieldsDTO.getLabelName()) && StringUtils.isNotBlank(orgLocFormFieldsDTO.getLabelName())) {
			orgLocFormFields.setFieldName(orgLocFormFieldsDTO.getLabelName().toLowerCase().replaceAll("\\s",""));
		}
		orgLocFormFields.setFieldType(orgLocFormFieldsDTO.getFieldType());
		orgLocFormFields.setFieldDesc(orgLocFormFieldsDTO.getFieldDescription());
		orgLocFormFields.setPlaceHolder(StringUtils.capitalize(orgLocFormFieldsDTO.getPlaceHolder()));
		orgLocFormFields.setIsFieldRequired(orgLocFormFieldsDTO.getIsRequired());
		orgLocFormFields.setStatus(orgLocFormFieldsDTO.getStatus());
		
		if(orgLocFormFieldsDTO.getValidationType()!= null && orgLocFormFieldsDTO.getValidationRange()!= null) {
			StringBuilder validationBuilder = new StringBuilder();
				switch (orgLocFormFieldsDTO.getValidationRange()) {
				case "Length2-250":
					validationBuilder.append("validate_length length2-250 ");
					break;
				case "Length2-500":
					validationBuilder.append("validate_length length2-500 ");
					break;
				case "Length 500 Above":
					validationBuilder.append("Length 500 and Above ");
					break;
				}
				
				switch (orgLocFormFieldsDTO.getValidationType()) {
				case "Alphabets":
					validationBuilder.append("validate_Alphabet ");
					break;
				case "Aplhanumeric":
					validationBuilder.append("validate_AlphaNumaric ");
					break;
				case "Numbers":
					validationBuilder.append("validate_int ");
					break;
				}
				
			orgLocFormFields.setDataValidationType(validationBuilder.toString());
		}
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
		
		
		orgLocFormFields.setUpdatedBy(loginUser);
		orgLocFormFields.setUpdatedDate(new Date());
		
		OrglocationFormFields orgLocFormFieldSavedData = orglocationFormFieldsDao.saveOrglocationFormField(orgLocFormFields);
		if(orgLocFormFieldsDTO.getOptionData() != null && !orgLocFormFieldsDTO.getOptionData().isEmpty()) {
			for(OrglocationFormSubFieldsDTO optionData:orgLocFormFieldsDTO.getOptionData()) {
				if(optionData != null && StringUtils.isNotBlank(optionData.getOption())) {
				OrglocationFormSubFields subFields = new OrglocationFormSubFields();
				subFields.setId(optionData.getId());
				subFields.setFormField(orgLocFormFieldSavedData);
				subFields.setLabelName(optionData.getOption());
				subFields.setStatus(1);
				orglocationFormSubFieldsDao.saveOrglocationFormSubField(subFields);
				}
			}
			
		  }
		return orgLocFormFields;
	  }

	@Override
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId,Long orgLocation) {
		return orglocationFormFieldsDao.getAllCustomFormFieldsBySectionIdandOrgLocationId(sectionId,orgLocation);
	}


	@Override
	public String deleteCustomFieldById(Long customID,String status) {
		return orglocationFormFieldsDao.deleteCustomFieldById(customID,status);
	}


	@Override
	public List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation) {
		return orglocationFormFieldsDao.getAllSectionByActionId(actionId,orgLocation);
		
	}


	@Override
	public Long getCurrentIndexBySourceleadActionIdAndOrders(Long actionId, List<Long> orders) {
		return orglocationFormFieldsDao.getCurrentIndexBySourceleadActionIdAndOrders(actionId, orders);
	}


	@Override
	public List<OrglocationFormSubFields> getAllCustomFormSubFieldsBySectionIdandOrgLocationId(Long customFieldId) {
		return orglocationFormFieldsDao.getAllCustomFormSubFieldsBySectionIdandOrgLocationId(customFieldId);
	}


	@Override
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationIdForCustomList(Long sectionId,Long orgLocation) {
		return orglocationFormFieldsDao.getAllCustomFormFieldsBySectionIdandOrgLocationIdForCustomList(sectionId,orgLocation);
	}
	
	


}
