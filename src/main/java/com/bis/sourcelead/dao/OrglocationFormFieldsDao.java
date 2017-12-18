package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;

public interface OrglocationFormFieldsDao {

	OrglocationFormFields saveOrglocationFormField(OrglocationFormFields orgLocFormFields);

	List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId, Long orgLocation);

	String deleteCustomFieldById(Long customID);

	OrglocationFormFields getOrglocationFormFieldsById(Long id) throws Exception;

	List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation);

}
