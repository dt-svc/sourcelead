package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.SourceleadTags;

public interface SourceleadTagsService {

	SourceleadTags saveSourceleadTags(SourceleadTags sourceleadTags) throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationId(Long orgLocationId) throws Exception;
	
	List<SourceleadTags> SourceleadTagsListByLocationId(Long orgLocationId)throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationIdAndEntityName(Long orgLocationId,String entityType,Long entityId) throws Exception;
	
	SourceleadTags getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(Long orgLocationId,String entityType,Long entityId,String tagName) throws Exception;
	
	Integer individualTagsCount(String entiteyTpe,String tagName)throws Exception;
	
	List<String> sourceleadTagsListByEntitypeAndOrgloactionId(String entityType,Long orgLocationId)throws Exception;
	
	List<SourceleadTags> getAllSourceleadTagsListByEntitypeAndOrgloactionId(String entityType,Long orgLocationId)throws Exception;

	void deleteTag(Long orgLocationId, String tagName, String entitype)throws Exception;
}
