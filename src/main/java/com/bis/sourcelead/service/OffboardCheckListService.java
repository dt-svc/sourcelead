package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OffboardCheckList;

public interface OffboardCheckListService {
	
	OffboardCheckList getOffboardCheckListByTemplateId(Long templateId) throws Exception;
	
	OffboardCheckList saveOffboardCheckList(OffboardCheckList offboardCheckList) throws Exception;

}
