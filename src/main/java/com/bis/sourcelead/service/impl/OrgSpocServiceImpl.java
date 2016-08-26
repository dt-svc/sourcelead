package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.service.OrgSpocService;

@Service
public class OrgSpocServiceImpl implements OrgSpocService{
	@Autowired
	OrgSpocDao orgSpocDao;
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSpocServiceImpl.class);

	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws Exception {
		return orgSpocDao.addOrgSpoc(orgSpoc);
	}
	
	

}
