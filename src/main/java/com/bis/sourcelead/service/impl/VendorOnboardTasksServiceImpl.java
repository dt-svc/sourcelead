package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.VendorOnboardTasksDao;
import com.bis.sourcelead.entity.VendorOnboardTasks;
import com.bis.sourcelead.service.VendorOnboardTasksService;

@Service
public class VendorOnboardTasksServiceImpl implements VendorOnboardTasksService{
	
	@Autowired
	VendorOnboardTasksDao vendorOnboardTasksDao;

	@Override
	public List<VendorOnboardTasks> getVendorOnboardTasksByTemplateId(Long templateId) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByTemplateId(templateId);
	}

	@Override
	public VendorOnboardTasks saveVendorOnboardTasks(VendorOnboardTasks vendorOnboardTasks) throws Exception {
		return vendorOnboardTasksDao.saveVendorOnboardTasks(vendorOnboardTasks);
	}

	@Override
	public VendorOnboardTasks getVendorOnboardTasksByTaskId(Long Id) throws Exception {
		return vendorOnboardTasksDao.getVendorOnboardTasksByTaskId(Id);
	}

}
