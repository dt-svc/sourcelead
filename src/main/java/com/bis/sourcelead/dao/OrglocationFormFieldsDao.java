package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.OrglocationFormSubFields;

public interface OrglocationFormFieldsDao {

	OrglocationFormFields saveOrglocationFormField(OrglocationFormFields orgLocFormFields);

	List<OrglocationFormFields> getAllCustomFormFieldsBySectionIdandOrgLocationId(Long sectionId, Long orgLocation);
	
    Long getCurrentIndexBySourceleadActionIdAndOrders(Long actionId,List<Long> orders);

	String deleteCustomFieldById(Long customID);

	OrglocationFormFields getOrglocationFormFieldsById(Long id) throws Exception;

	List<OrglocationFormFields> getAllSectionByActionId(Long actionId, OrgLocations orgLocation);

	List<OrglocationFormSubFields> getAllCustomFormSubFieldsBySectionIdandOrgLocationId(Long formFieldID);

}
