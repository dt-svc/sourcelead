package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OrglocationFormSubFields;

public interface OrglocationFormSubFieldsService {
	
	List<OrglocationFormSubFields> getOrgCustomSufieldsBySectionId(Long customFieldId);
}
