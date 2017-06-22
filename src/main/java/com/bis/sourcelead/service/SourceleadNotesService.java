package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;

public interface SourceleadNotesService {

public SourceleadNotes storeComments(JSONObject jsonObj,User user) throws Exception;
	
	
}
