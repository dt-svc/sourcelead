package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OffboardCheckList;

public interface OffboardCheckListDao {
	
	OffboardCheckList getOffboardCheckListByTemplateId(Long templateId) throws Exception;
	
	OffboardCheckList saveOffboardCheckList(OffboardCheckList offboardCheckList) throws Exception;
	
	Integer getOffboardCheckListCountByOffboardId(Long candidateOffBoardingId) throws Exception;
	
	OffboardCheckList getOffboardCheckListByOffboardingId(Long candidateOffBoardingId) throws Exception;

}
