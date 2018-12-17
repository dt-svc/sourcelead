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
			List<OrglocationFormFields> customFieldsList = orglocationFormFieldsDao.getAllCustomFormFieldsBySectionIdandOrgLocationIdForCustomList(section.getId(), locationId);
			sectionDTO  = sourceleadFormSectionsDTOConverter(section,fieldsList,customFieldsList);
			sectionDTOlist.add(sectionDTO);
		}
		return sectionDTOlist;
	}

	@Override
	public SourceleadSectionDTO sourceleadFormSectionsDTOConverter(SourceleadFormSections slFormSections,List<SourceleadFormFields> fieldsList,List<OrglocationFormFields> customFieldsList) {
		SourceleadSectionDTO sectionDTO = new SourceleadSectionDTO();
		sectionDTO.setId(slFormSections.getId());
		if(slFormSections != null && slFormSections.getSourceleadActions() != null) {
			sectionDTO.setSourceleadActionId(slFormSections.getSourceleadActions().getId());
			sectionDTO.setSourceleadActionName(slFormSections.getSourceleadActions().getActionName());
			sectionDTO.setTabID(slFormSections.getTabs().getId());
			sectionDTO.setTabName(slFormSections.getTabs().getTabName());
			sectionDTO.setIsDefaultVisible(slFormSections.getIsDefaultVisible());
			sectionDTO.setSectionName(slFormSections.getSectionName());
			sectionDTO.setSectionDescription(slFormSections.getSectionDesc());
			sectionDTO.setStatus(slFormSections.getStatus());
			
		}
		List<SourceleadFormFieldsDTO> fieldsDTOList = new ArrayList<>();
		if(fieldsList != null && !fieldsList.isEmpty()) {
			for(SourceleadFormFields formFields :fieldsList) {
				fieldsDTOList.add(sourceleadFormFieldsDTOConverter(formFields));
			}
			
		}
		List<OrglocationFormFieldsDTO> customFieldsListDTO = new ArrayList<>();
		if(customFieldsList != null && !customFieldsList.isEmpty()) {
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
			customDTO.setSectionData(sourceleadFormSectionsDTOConverter(customList.getSourceleadFormSections(),null,null));
		}
		if(customList.getCustomDependantField() != null) {
			customDTO.setCustomDependentField(customList.getCustomDependantField().getId());
		}
		if(customList.getSourceLeadDependantField()!= null) {
			customDTO.setSlDependedntField(customList.getSourceLeadDependantField().getId());
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
		
		if(StringUtils.isEmpty(customList.getPlaceHolder())) {
			
			if(customList.getFieldType().equalsIgnoreCase("TextBox")){
				customDTO.setPlaceHolder("Enter Text");
			}else if(customList.getFieldType().equalsIgnoreCase("Date")){
				customDTO.setPlaceHolder("Enter Date");
			}else if(customList.getFieldType().equalsIgnoreCase("selectBox")){
				customDTO.setPlaceHolder("Select");
			}else if(customList.getFieldType().equalsIgnoreCase("checkBox")){
				customDTO.setPlaceHolder("Select");
			}else if(customList.getFieldType().equalsIgnoreCase("radioButton")){
				customDTO.setPlaceHolder("Select");
			}else if(customList.getFieldType().equalsIgnoreCase("textArea")){
				customDTO.setPlaceHolder("Enter TextArea");
			}else if(customList.getFieldType().equalsIgnoreCase("email")){
				customDTO.setPlaceHolder("Enter Email");
			}
		}else{
				customDTO.setPlaceHolder(customList.getPlaceHolder());
			}
	
		customDTO.setOrglocationId(customList.getOrgLocations().getId());
		customDTO.setOrgId(customList.getOrg().getId());
		customDTO.setMinDate(customList.getMinDate());
		customDTO.setMaxDate(customList.getMaxDate());
		customDTO.setStatus(customList.getStatus());
		if(customDTO.getFieldType().equalsIgnoreCase("TextBox")) {
			customDTO.setImagePathUrl("/resources/images/textfield.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("Date")) {
			customDTO.setImagePathUrl("/resources/images/datefield.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("SelectBox")) {
			customDTO.setImagePathUrl("/resources/images/selectfeild.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("Check Box")) {
			customDTO.setImagePathUrl("/resources/images/checkbox.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("radioButton")) {
			customDTO.setImagePathUrl("/resources/images/radio.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("textArea")) {
			customDTO.setImagePathUrl("/resources/images/text area.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("email")) {
			customDTO.setImagePathUrl("/resources/images/mailfield.jpg");
		}else if(customDTO.getFieldType().equalsIgnoreCase("location")) {
			customDTO.setImagePathUrl("/resources/images/location.jpg");
		}
		
		if(customList.getDataValidationType() != null) {
			String length = "";
			String Type = "";
			StringBuilder validation = new StringBuilder();
			Boolean length2To250 = customList.getDataValidationType().contains("validate_length length2-250 ");
			Boolean length2To500 = customList.getDataValidationType().contains("validate_length length2-500 ");
			Boolean length500AndAbove = customList.getDataValidationType().contains("Length 500 and Above ");
			Boolean Alphabets = customList.getDataValidationType().contains("validate_Alphabet ");
			Boolean Numbers = customList.getDataValidationType().contains("validate_int ");
			Boolean Alphanumeric = customList.getDataValidationType().contains("validate_AlphaNumaric ");
			if(length2To250 == true) {
				length = "Length 2-250";
			}else if(length2To500 == true) {
				length = "Length 2-500";
			}else if(length500AndAbove == true) {
				length = "Length 500 Above";
			}
			customDTO.setValidationRange(length);
			if(Alphabets == true) {
				Type = "Alphabets";
			}else if(Numbers == true) {
				Type = "Numbers";
			}else if(Alphanumeric == true) {
				Type = "Aplhanumeric";
			}
			customDTO.setValidationType(Type);
			customDTO.setValidation(customList.getDataValidationType());
		}
		
		if(customFieldsubList != null && !customFieldsubList.isEmpty()) {
			List<OrglocationFormSubFieldsDTO> orglocationFormSubfieldDTO = getorgLocationCustomSubFields(customFieldsubList);
			customDTO.setOptionData(orglocationFormSubfieldDTO);
		}
		return customDTO;
	}

	private void elseif() {
		// TODO Auto-generated method stub
		
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
		formfieldDTO.setId(formFields.getId());
		formfieldDTO.setFieldName(formFields.getFieldName());
		formfieldDTO.setFieldDesc(formFields.getFieldDesc());
		formfieldDTO.setFieldType(formFields.getFieldType());
		formfieldDTO.setFieldstatus(formFields.getStatus());
		formfieldDTO.setLabelInfo(formFields.getLabelInfo());
		formfieldDTO.setLabelName(formFields.getLabelName());
		formfieldDTO.setPlaceHolder(formFields.getPlaceHolder());
		
		if(formfieldDTO.getFieldType() != null && StringUtils.isNotEmpty(formfieldDTO.getFieldType())) {
			if(formfieldDTO.getFieldType().equalsIgnoreCase("TextBox")) {
				formfieldDTO.setImagePathUrl("/resources/images/textfield.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("Date")) {
				formfieldDTO.setImagePathUrl("/resources/images/datefield.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("SelectBox")) {
				formfieldDTO.setImagePathUrl("/resources/images/selectfeild.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("Check Box")) {
				formfieldDTO.setImagePathUrl("/resources/images/checkbox.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("radioButton")) {
				formfieldDTO.setImagePathUrl("/resources/images/radio.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("textArea")) {
				formfieldDTO.setImagePathUrl("/resources/images/text area.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("email")) {
				formfieldDTO.setImagePathUrl("/resources/images/mailfield.jpg");
			}else if(formfieldDTO.getFieldType().equalsIgnoreCase("location")) {
				formfieldDTO.setImagePathUrl("/resources/images/location.jpg");
			}
		}
		
		formfieldDTO.setSectionId(formFields.getSourceleadFormSections().getId());
		formfieldDTO.setSourceLeadlDependantField(formFields.getSlDependantField());
		if(((Integer)formFields.getIsFieldRequired()!= null)) {
			formfieldDTO.setIsFieldRequired(formFields.getIsFieldRequired());
		}
		return formfieldDTO;
	}

	@Override
	public List<SourceleadFormFields> getSourceleadSectionDetailsById(Long section,Long sourceleadFormId, Long locationId) {
		return sourceleadFormFieldsDao.getSourceleadSectionDetailsById(section,sourceleadFormId,locationId);
	}
}
