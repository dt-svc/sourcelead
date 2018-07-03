package com.bis.sourcelead.dao;

import java.util.Set;

import com.bis.sourcelead.entity.SourceleadTags;

public interface SourceleadTagsDao {

	SourceleadTags saveSourceleadTags(SourceleadTags sourceleadTags) throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationId(Long orgLocationId) throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationIdAndEntityName(Long orgLocationId,String entityType,Long entityId) throws Exception;
	
	SourceleadTags getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(Long orgLocationId,String entityType,Long entityId,String tagName) throws Exception;
}
