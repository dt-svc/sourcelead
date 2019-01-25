package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;
import com.bis.sourcelead.entity.User;

public interface OrglocationFormFieldsService {

	public OrglocationFormFields saveNewCustomFormFields(OrglocationFormFieldsDTO orgLocFormFieldsDTO, OrgLocations orgLocation, Org org, User loginUser) throws Exception;

	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId, Long orgLocation);
	
	public Long getCurrentIndexBySourceleadActionIdAndOrders(Long actionId,List<Long> orders);

	public String deleteCustomFieldById(Long customID, String status);

	public List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation);

	public OrglocationFormFields getOrglocationFormFieldsById(Long id) throws Exception;

	public List<OrglocationFormSubFields> getAllCustomFormSubFieldsBySectionIdandOrgLocationId(Long customFieldId);
	
	public List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationIdForCustomList(Long sectionId,Long orgLocation);

}
