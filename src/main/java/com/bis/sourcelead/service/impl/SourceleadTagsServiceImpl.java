package com.bis.sourcelead.service.impl;

import java.util.List;
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

	@Override
	public List<SourceleadTags> SourceleadTagsListByLocationId(Long orgLocationId)throws Exception  {
		return sourceleadTagsDao.SourceleadTagsListByLocationId(orgLocationId);
	}

	@Override
	public Integer individualTagsCount(String entiteyTpe, String tagName) throws Exception {
		return sourceleadTagsDao.individualTagsCount(entiteyTpe, tagName);
	}

	@Override
	public void deleteTag(Long orgLocationId, String tagName,String entitype) throws Exception {
		sourceleadTagsDao.deleteTag(orgLocationId, tagName,entitype);
	}

	@Override
	public List<String> sourceleadTagsListByEntitypeAndOrgloactionId(String entityType, Long orgLocationId) throws Exception {
		return sourceleadTagsDao.sourceleadTagsListByEntitypeAndOrgloactionId(entityType, orgLocationId);
	}

	@Override
	public List<SourceleadTags> getAllSourceleadTagsListByEntitypeAndOrgloactionId(String entityType,
			Long orgLocationId) throws Exception {
		return sourceleadTagsDao.getAllSourceleadTagsListByEntitypeAndOrgloactionId(entityType,orgLocationId);
	}
}
