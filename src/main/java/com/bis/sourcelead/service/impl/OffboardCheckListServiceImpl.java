package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OffboardCheckListDao;
import com.bis.sourcelead.entity.OffboardCheckList;
import com.bis.sourcelead.service.OffboardCheckListService;

@Service
public class OffboardCheckListServiceImpl implements OffboardCheckListService {
	
	@Autowired
	OffboardCheckListDao offboardCheckListDao;

	@Override
	public OffboardCheckList getOffboardCheckListByTemplateId(Long templateId) throws Exception {
		return offboardCheckListDao.getOffboardCheckListByTemplateId(templateId);
	}

	@Override
	public OffboardCheckList saveOffboardCheckList(OffboardCheckList offboardCheckList) throws Exception {
		return offboardCheckListDao.saveOffboardCheckList(offboardCheckList);
	}

}
