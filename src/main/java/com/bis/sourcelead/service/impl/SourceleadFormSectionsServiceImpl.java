package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrglocationFormFieldsDao;
import com.bis.sourcelead.dao.OrglocationFormSubFieldsDao;
import com.bis.sourcelead.dao.SourceleadFormFieldsDao;
import com.bis.sourcelead.dao.SourceleadFormSectionsDao;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.OrglocationFormSubFieldsDTO;
import com.bis.sourcelead.dto.SourceleadFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;
import com.bis.sourcelead.service.SourceleadFormSectionsService;

@Service
public class SourceleadFormSectionsServiceImpl  implements SourceleadFormSectionsService{

	@Autowired
	private SourceleadFormSectionsDao sourceleadFormSectionsDao;
	
	@Autowired
	private SourceleadFormFieldsDao sourceleadFormFieldsDao;
	
	@Autowired
	private OrglocationFormFieldsDao orglocationFormFieldsDao;

	@Autowired
	private OrglocationFormSubFieldsDao orglocationFormSubFieldsDao;
	
	@Override
	public List<SourceleadSectionDTO> getSourceleadFormSectionsByActionId(Long actionId,Long locationId) throws Exception {
		List<SourceleadFormSections> slFormSections = sourceleadFormSectionsDao.getSourceleadFormSectionsByActionId(actionId);
		SourceleadSectionDTO sectionDTO = null;
		List<SourceleadSectionDTO> sectionDTOlist = new ArrayList<>();
		for(SourceleadFormSections section:slFormSections) {
			List<SourceleadFormFields> fieldsList = sourceleadFormFieldsDao.getAllFormFieldsBySectionId(section.getId());
			List<OrglocationFormFields> customFieldsList = orglocationFormFieldsDao.getAllCustomFormFieldsBySectionIdandOrgLocationId(section.getId(), locationId);
			sectionDTO  = sourceleadFormSectionsDTOConverter(section,fieldsList,customFieldsList);
			sectionDTOlist.add(sectionDTO);
		}
		return sectionDTOlist;
	}

	private SourceleadSectionDTO sourceleadFormSectionsDTOConverter(SourceleadFormSections slFormSections,List<SourceleadFormFields> fieldsList,List<OrglocationFormFields> customFieldsList) {
		SourceleadSectionDTO sectionDTO = new SourceleadSectionDTO();
		sectionDTO.setId(slFormSections.getId());
		sectionDTO.setSourceleadActionId(slFormSections.getSourceleadActions().getId());
		sectionDTO.setSourceleadActionName(slFormSections.getSourceleadActions().getActionName());
		sectionDTO.setTabID(slFormSections.getTabs().getId());
		sectionDTO.setTabName(slFormSections.getTabs().getTabName());
		sectionDTO.setSectionName(slFormSections.getSectionName());
		sectionDTO.setSectionDescription(slFormSections.getSectionDesc());
		sectionDTO.setStatus(slFormSections.getStatus());
		List<SourceleadFormFieldsDTO> fieldsDTOList = new ArrayList<>();
		if(fieldsList != null && !fieldsList.isEmpty()) {
			for(SourceleadFormFields formFields :fieldsList) {
				fieldsDTOList.add(sourceleadFormFieldsDTOConverter(formFields));
			}
			
		}
		List<OrglocationFormFieldsDTO> customFieldsListDTO = new ArrayList<>();
		if(fieldsList != null && !fieldsList.isEmpty()) {
			for(OrglocationFormFields customList :customFieldsList) {
				List<OrglocationFormSubFields> customFieldsubList = orglocationFormSubFieldsDao.getOrgCustomSufieldsBySectionId(customList.getId());
				customFieldsListDTO.add(CustomFormFieldsDTOConverter(customList,customFieldsubList));
			}
			
		}
		sectionDTO.setCustomFieldsList(customFieldsListDTO);
		sectionDTO.setFormFieldsList(fieldsDTOList);
		return sectionDTO;
	}
	
	@Override
	public OrglocationFormFieldsDTO CustomFormFieldsDTOConverter(OrglocationFormFields customList, List<OrglocationFormSubFields> customFieldsubList) {
		OrglocationFormFieldsDTO customDTO = new OrglocationFormFieldsDTO();
		if(customList.getSourceleadFormSections() != null && StringUtils.isNotBlank(customList.getSourceleadFormSections().toString())) {
			customDTO.setSectionId(customList.getSourceleadFormSections().getId());
		}
		if(customList.getCustomDependantField() != null) {
			customDTO.setCustomDependentField(customList.getCustomDependantField().getId());
		}
		customDTO.setId(customList.getId());
		customDTO.setFieldDescription(customList.getFieldDesc());
		customDTO.setFieldName(customList.getFieldName());
		customDTO.setFieldType(customList.getFieldType());
		if((Integer)customList.getIsFieldRequired()!= null) {
			customDTO.setIsRequired(customList.getIsFieldRequired());
		}
		customDTO.setLabelinfo(customList.getLabelInfo());
		customDTO.setLabelName(customList.getLabelName());
		customDTO.setPlaceHolder(customList.getPlaceHolder());
		customDTO.setOrglocationId(customList.getOrgLocations().getId());
		customDTO.setOrgId(customList.getOrg().getId());
		customDTO.setStatus(customList.getStatus());
		if(customFieldsubList != null && !customFieldsubList.isEmpty()) {
			List<OrglocationFormSubFieldsDTO> orglocationFormSubfieldDTO = getorgLocationCustomSubFields(customFieldsubList);
			customDTO.setOptionData(orglocationFormSubfieldDTO);
		}
		return customDTO;
	}

	private List<OrglocationFormSubFieldsDTO> getorgLocationCustomSubFields(List<OrglocationFormSubFields> customFieldsubList) {
		List<OrglocationFormSubFieldsDTO> orglocationFormSubfieldDTO = new ArrayList<>();
		for( OrglocationFormSubFields customSubList:customFieldsubList) {
			OrglocationFormSubFieldsDTO orgSubDTO = new OrglocationFormSubFieldsDTO();
			orgSubDTO.setId(customSubList.getId());
			orgSubDTO.setOption(customSubList.getLabelName());
			orgSubDTO.setFieldName(customSubList.getFieldName());
			orgSubDTO.setFieldDesc(customSubList.getFieldDesc());
			orgSubDTO.setFormField(customSubList.getFormField().getId());
			orgSubDTO.setStatus(customSubList.getStatus());
			orglocationFormSubfieldDTO.add(orgSubDTO);
		}
		return orglocationFormSubfieldDTO;
	}

	private SourceleadFormFieldsDTO sourceleadFormFieldsDTOConverter(SourceleadFormFields formFields) {
		SourceleadFormFieldsDTO formfieldDTO = new SourceleadFormFieldsDTO();
		formfieldDTO.setFieldName(formFields.getFieldName());
		formfieldDTO.setFieldDesc(formFields.getFieldDesc());
		formfieldDTO.setFieldType(formFields.getFieldType());
		formfieldDTO.setFieldstatus(formFields.getStatus());
		formfieldDTO.setLabelInfo(formFields.getLabelInfo());
		formfieldDTO.setLabelName(formFields.getLabelName());
		formfieldDTO.setPlaceHolder(formFields.getPlaceHolder());
		formfieldDTO.setSectionId(formFields.getSourceleadFormSections().getId());
		formfieldDTO.setSourceLeadlDependantField(formFields.getSlDependantField());
		if(((Integer)formFields.getIsFieldRequired()!= null)) {
			formfieldDTO.setIsFieldRequired(formFields.getIsFieldRequired());
		}
		return formfieldDTO;
	}
}
