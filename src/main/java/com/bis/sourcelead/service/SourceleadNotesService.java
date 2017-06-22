package com.bis.sourcelead.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.SourceleadNotes;

public interface SourceleadNotesService {

public SourceleadNotes storeComments(JSONObject jsonObj) throws JSONException,Exception;
	
	
}
