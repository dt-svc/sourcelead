package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.SourceleadFormFields;
import com.bis.sourcelead.entity.SourceleadFormSections;

public interface SourceleadFormSectionsService {
	
	List<SourceleadSectionDTO> getSourceleadFormSectionsByActionId(Long actionId,Long locationId) throws Exception;

	OrglocationFormFieldsDTO CustomFormFieldsDTOConverter(OrglocationFormFields customList, List<OrglocationFormSubFields> customFieldsubList);

	List<SourceleadFormFields> getSourceleadSectionDetailsById(Long section, Long sourceleadSectionForm, Long orglocationId);

	SourceleadSectionDTO sourceleadFormSectionsDTOConverter(SourceleadFormSections slFormSections,List<SourceleadFormFields> fieldsList, List<OrglocationFormFields> customFieldsList);
}
