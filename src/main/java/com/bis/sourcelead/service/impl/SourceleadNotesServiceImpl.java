package com.bis.sourcelead.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.service.SourceleadNotesService;

@Service
public class SourceleadNotesServiceImpl implements SourceleadNotesService {
	
	@Autowired
	private SourceleadNotesDao sourceleadNotesDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesServiceImpl.class);
	
	
	
	@Override
	public SourceleadNotesService storeComments(JSONObject jsonObj) throws JSONException, Exception
	{
		SourceleadNotes sourceleadNotes = new SourceleadNotes();
		
		return sourceleadNotesDao.storeComments(sourceleadNotes);
	}
	
	
}
