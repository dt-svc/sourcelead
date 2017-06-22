package com.bis.sourcelead.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.service.SourceleadNotesService;

public interface SourceleadNotesDao {
	
	public SourceleadNotes storeComments(SourceleadNotes sourceleadNotes) throws JSONException,Exception;

	
	
}