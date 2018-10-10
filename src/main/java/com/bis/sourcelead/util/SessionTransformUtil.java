package com.bis.sourcelead.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.bis.sourcelead.entity.Tabs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * LEAST PREFERRED : ULTIMATELY GET RID OF JAVA OBJECTS/CLASSES/ENTITIES FRM THE SESSION
 * Utility using Google Gson to serialize java objects to and from JSON
 * @author Bis51
 *
 */
public class SessionTransformUtil {
	/**
	 * Session Key String (used in the app across all places) vs Java Object 
	 */
	static HashMap ObjectMappings = new HashMap();
	static Gson gson = new Gson();
	
	static {
		ObjectMappings.put("candidate", com.bis.sourcelead.entity.Candidate.class);
		ObjectMappings.put("tabsList", new TypeToken<ArrayList<Tabs>>(){}.getType());
		ObjectMappings.put("orgLocations", com.bis.sourcelead.entity.OrgLocations.class);
	}
	
	/**
	 * Transform the Java object to String.
	 * Call this before setting an object to the session store
	 * @param sessionKey
	 * @param sessionValue
	 */
	public static String keyValueToSession(String sessionKey, Object sessionValueObject){
		
		
	/*	switch(sessionKey) {
		case "candidate":
			try {
				CandidateSessionDTO candiate = mapper.map((Candidate)sessionValueObject, CandidateSessionDTO.class);
				jse = gson.toJson(candiate);
			}catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "orgLocations":
			try {
				OrgLocationsSessionDTO orgLocations = mapper.map((OrgLocations)sessionValueObject, OrgLocationsSessionDTO.class);
				jse = gson.toJson(orgLocations);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
	}*/
		
		String jse = gson.toJson(sessionValueObject);
		return jse;
	}

	/**
	 * 
	 * @param sessionKey
	 * @param sessionValueObject
	 * @return
	 */
	public static Object objectValueFromSession(String sessionKey, String sessionValueString){
		Type t= (Type)ObjectMappings.get(sessionKey);
		if(t==null)
			return sessionValueString;
		
		Object ob   = gson.fromJson(sessionValueString, t);
		return ob;
	}
}
