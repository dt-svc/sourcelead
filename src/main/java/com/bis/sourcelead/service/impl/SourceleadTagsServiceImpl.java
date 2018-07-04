package com.bis.sourcelead.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadTagsDao;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.service.SourceleadTagsService;

@Service
public class SourceleadTagsServiceImpl implements SourceleadTagsService{
	
	@Autowired
	SourceleadTagsDao sourceleadTagsDao;

	@Override
	public SourceleadTags saveSourceleadTags(SourceleadTags sourceleadTags) throws Exception {
		return sourceleadTagsDao.saveSourceleadTags(sourceleadTags);
	}

	@Override
	public Set<String> getSourceleadTagsByOrgLocationId(Long orgLocationId) throws Exception {
		return sourceleadTagsDao.getSourceleadTagsByOrgLocationId(orgLocationId);
	}

	@Override
	public Set<String> getSourceleadTagsByOrgLocationIdAndEntityName(Long orgLocationId, String entityType,
			Long entityId) throws Exception {
		return sourceleadTagsDao.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocationId, entityType, entityId);
	}

	@Override
	public SourceleadTags getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(Long orgLocationId, String entityType,
			Long entityId,String tagName) throws Exception {
		return sourceleadTagsDao.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocationId, entityType, entityId,tagName);
	}

	
}
