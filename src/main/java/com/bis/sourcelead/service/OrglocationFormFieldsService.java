package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.User;

public interface OrglocationFormFieldsService {

	public OrglocationFormFields saveNewCustomFormFields(OrglocationFormFieldsDTO orgLocFormFieldsDTO, OrgLocations orgLocation, Org org, User loginUser) throws Exception;

	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId, Long orgLocation);

	public String deleteCustomFieldById(Long customID);

	public List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation);

}
