package com.bis.sourcelead.service;

import java.util.LinkedHashSet;
import java.util.Set;

import com.bis.sourcelead.entity.SourceleadTags;

public interface SourceleadTagsService {

	SourceleadTags saveSourceleadTags(SourceleadTags sourceleadTags) throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationId(Long orgLocationId) throws Exception;
	
	Set<String> getSourceleadTagsByOrgLocationIdAndEntityName(Long orgLocationId,String entityType,Long entityId) throws Exception;
	
	SourceleadTags getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(Long orgLocationId,String entityType,Long entityId,String tagName) throws Exception;
}
